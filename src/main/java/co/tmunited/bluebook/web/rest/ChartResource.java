package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.domain.vo.ChartVO;
import co.tmunited.bluebook.domain.vo.PictureVO;
import co.tmunited.bluebook.repository.ChartToLevelCareRepository;
import co.tmunited.bluebook.repository.search.ChartSearchRepository;
import co.tmunited.bluebook.repository.search.ChartToLevelCareSearchRepository;
import co.tmunited.bluebook.service.BedService;
import co.tmunited.bluebook.service.ChartToLevelCareService;
import co.tmunited.bluebook.web.rest.util.BooleanWrapper;
import co.tmunited.bluebook.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.service.ChartService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Chart.
 */
@RestController
@RequestMapping("/api")
public class ChartResource {

    private final Logger log = LoggerFactory.getLogger(ChartResource.class);

    @Inject
    private ChartService chartService;

    @Inject
    private BedService bedService;

    @Inject
    private ChartSearchRepository chartSearchRepository;

    @Inject
    private ChartToLevelCareService chartToLevelCareService;

    @Inject
    private PatientResource patientResource;

    @Inject
    private ChartToLevelCareRepository chartToLevelCareRepository;

    /**
     * POST  /charts : Create a new chart.
     *
     * @param chart the chart to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chart, or with status 400 (Bad Request) if the chart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/charts")
    @Timed
    public ResponseEntity<Chart> createChart(@RequestBody Chart chart) throws URISyntaxException {
        log.info("REST request to save Chart : {}", chart);
        Chart result = new Chart();
        try {
            if (chart.getId() != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chart", "idexists", "A new chart cannot already have an ID")).body(null);
            }
            result = chartService.save(chart);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return ResponseEntity.created(new URI("/api/charts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chart", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /charts : Updates an existing chart.
     *
     * @param chart the chart to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chart,
     * or with status 400 (Bad Request) if the chart is not valid,
     * or with status 500 (Internal Server Error) if the chart couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/charts")
    @Timed
    public ResponseEntity<Chart> updateChart(@RequestBody Chart chart) throws URISyntaxException {
        log.info("REST request to update Chart : {}", chart);
        Chart result = new Chart();
        try {
            Patient patient = chart.getPatient();

            if (chart.getId() == null) {
                return createChart(chart);
            }
            result = chartService.save(chart);
            if (chart.getBed() != null && chart.getBed().getActualChart() != null) {
                Bed resultBed = bedService.save(chart.getBed());
                result.setBed(resultBed);
            }
            if (patient != null) {
                patientResource.updatePatient(patient);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chart", chart.getId().toString()))
            .body(result);
    }

    private void startLevelCare(Long id) {
        ZonedDateTime now = ZonedDateTime.now();
        ChartToLevelCare chartToLevelCare = chartToLevelCareRepository.findLastLevelCaresByChart(id);
        if (chartToLevelCare != null) {
            chartToLevelCare.setStartDate(now);
            chartToLevelCare.setChartId(id);
        }
        try {
            chartToLevelCareService.save(chartToLevelCare);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    /**
     * This WS put in false the waiting room and ChartToLevelCare's startDate field.
     *
     * @param id the chart to update
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/update-waiting/{id}")
    @Timed
    public ResponseEntity<Chart> updateWaitingChart(@PathVariable Long id) throws URISyntaxException {
        log.info("REST request to update Chart's waiting field : {}", id);
        Chart result = new Chart();
        try {
            Chart chart = chartService.findOne(id);
            chart.setWaitingRoom(false);
            result = chartService.save(chart);
            startLevelCare(chart.getId());
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("chart", e.getCause().toString(), e.getMessage()))
                .body(null);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chart", id.toString()))
            .body(result);
    }

    /**
     * This WS put in false the waiting room and ChartToLevelCare's startDate field.
     *
     * @param chart the chart to update
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/chart/preload-forms")
    @Timed
    public ResponseEntity<Chart> updateWaitingChartAndPreloadForms(@RequestBody Chart chart) throws URISyntaxException {
        log.debug("REST request to update Chart's waiting field : {}", chart);
        chart.setWaitingRoom(false);
        startLevelCare(chart.getId());
        chartService.preloadForms(chart);
        Chart result = chartService.save(chart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chart", chart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /charts : get all the charts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of charts in body
     */
    @GetMapping("/charts")
    @Timed
    public List<Chart> getAllCharts() {
        log.debug("REST request to get all Charts");
        return chartService.findAll();
    }

    /**
     * GET  /charts/:id : get the "id" chart.
     *
     * @param id the id of the chart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chart, or with status 404 (Not Found)
     */
    @GetMapping("/charts/{id}")
    @Timed
    public ResponseEntity<Chart> getChart(@PathVariable Long id) {
        log.debug("REST request to get Chart : {}", id);
        Chart chart = chartService.findOne(id);
        return Optional.ofNullable(chart)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /charts/:id : delete the "id" chart.
     *
     * @param id the id of the chart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/charts/{id}")
    @Timed
    public ResponseEntity<Void> deleteChart(@PathVariable Long id) {
        log.debug("REST request to delete Chart : {}", id);
        chartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chart", id.toString())).build();
    }

    /**
     * SEARCH  /_search/charts?query=:query : search for the chart corresponding
     * to the query.
     *
     * @param query the query of the chart search
     * @return the result of the search
     */
    @GetMapping("/_search/charts")
    @Timed
    public List<Chart> searchCharts(@RequestParam String query) {
        log.debug("REST request to search Charts for query {}", query);
        List<Chart> list = StreamSupport
            .stream(chartSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
        return list;
    }


    /**
     * SEARCH  /_search/charts?query=:query : search for the chart corresponding
     * to the query.
     *
     * @param query the query of the chart search
     * @return the result of the search
     */
    @GetMapping("/_search/all-charts")
    @Timed
    public ResponseEntity<List<ChartVO>> searchAllCharts(@RequestParam String query, Pageable pageable) throws URISyntaxException {
        log.debug("REST request to search Charts for query {}", query);
        Page<Chart> charts = chartSearchRepository.search(wrapperQuery(query), pageable);
        List<ChartVO> chartVOS = charts.getContent().stream()
            .map(chart -> {
                String bed = (chart.getBed() != null) ? chart.getBed().getName() : "";
                String patientProgram = (chart.getTypePatientPrograms() != null) ? chart.getTypePatientPrograms().getName() : "";
                String picture = (chart.getPictureRef() != null) ? chart.getPictureRef().getPicture() : "";
                String pictureCT = (chart.getPictureRef() != null) ? chart.getPictureRef().getPictureContentType() : "";
                ChartVO chartVO = new ChartVO(chart.getId(), chart.getPatient().getFirstName(), chart.getPatient().getLastName(),
                    chart.getMrNo(), bed, patientProgram, chart.getTypePaymentMethods().getName(), chart.getFacility().getName(),
                    picture, pictureCT
                );
                chartVO.setAdmissionDate(chart.getAdmissionDate());
                chartVO.setDischargeDate(chart.getDischargeDate());
                chartVO.setWaitingRoom(chart.isWaitingRoom());
                return chartVO;
            })
//            .sorted(Comparator.comparing(ChartVO::getFirstName))
            .collect(Collectors.toList());
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(charts, "api/_search/all-charts");
        return new ResponseEntity<>(chartVOS, httpHeaders, HttpStatus.OK);
    }

    /**
     * GET  /charts : get all the charts filtered by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of charts in body
     */
    @GetMapping("/charts-facility/{id}")
    @Timed
    public List<Chart> getAllChartsByFacility(@PathVariable Long id) {
        log.debug("REST request to get all Charts By Facility");
        return chartService.findAllByFacility(id);
    }

    /**
     * GET  /charts : get all the charts filtered by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of charts in body
     */
    @GetMapping("/charts-facility/unassigned-bed/{id}")
    @Timed
    public List<Chart> getAllChartsByFacilityWithoutBed(@PathVariable Long id) {
        log.debug("REST request to get all Charts By Facility and Without Bed assigned");
        ZonedDateTime now = ZonedDateTime.now();
        return chartService.findAllByFacilityWithoutBed(id, now);
    }

    /**
     * PUT  /charts : Updates an existing chart.
     *
     * @param chart the chart and bed to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chart,
     * or with status 400 (Bad Request) if the chart is not valid,
     * or with status 500 (Internal Server Error) if the chart couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-occupancy")
    @Timed
    public ResponseEntity<Chart> updateChartAndBed(@RequestBody Chart chart) throws URISyntaxException {
        log.debug("REST request to update Chart : {}", chart);
        if (chart.getId() == null) {
            return createChart(chart);
        }
        Chart result = chartService.save(chart);
        Bed resultBed = bedService.save(chart.getBed());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chart", chart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /charts : get all the charts filtered by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of charts in body
     */
    @GetMapping("/charts-current-patients/{id}")
    @Timed
    @Deprecated
    public List<Chart> getAllChartsCurrentPatients(@PathVariable Long id) {
        log.debug("REST request to get all Charts By Facility");
        ZonedDateTime now = ZonedDateTime.now();
        return chartService.findAllByFacilityWaitingRoomFalseAndDischargeDate(id, now);
    }


    /**
     * GET  /chartsVO : get all the chartsVO filtered by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of charts in body
     */
    @GetMapping("/charts-current-patients-vo/{id}")
    @Timed
    public List<ChartVO> getAllChartsCurrentPatientsVO(@PathVariable Long id) {
        log.debug("REST request to get all ChartsVO By Facility");
        ZonedDateTime now = ZonedDateTime.now();
        return chartService.findAllByFacilityWaitingRoomFalseAndDischargeDateVO(id, now);
    }

    /**
     * GET  /chartsVO : get all the chartsVO filtered by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of charts in body
     */
    @GetMapping("/charts-current-patients-vo-for-group-session/{id}")
    @Timed
    public List<ChartVO> getAllChartsCurrentPatientsVOForGroupSession(@PathVariable Long id) {
        log.debug("REST request to get all ChartsVO By Facility");
        ZonedDateTime now = ZonedDateTime.now();
        return chartService.findAllByFacilityWaitingRoomFalseAndDischargeDateVOForGroupSession(id, now);
    }

    /**
     * GET  /charts : get all the charts filtered by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of charts in body
     */
    @GetMapping("/charts-waiting-room/{id}")
    @Timed
    public List<ChartVO> getAllWaitingRoom(@PathVariable Long id) {
        log.debug("REST request to get all Charts By Facility and are in WaitingRoom");
        return chartService.findAllByFacilityWaitingRoomTrue(id);
    }

    /**
     * GET  /charts : get all the charts filtered by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of charts in body
     */
    @GetMapping("/charts-archive/{id}")
    @Timed
    public List<ChartVO> getAllArchive(@PathVariable Long id) {
        log.debug("REST request to get all Charts By Facility");
        ZonedDateTime now = ZonedDateTime.now();
        return chartService.findAllArchive(id, now);
    }


    /**
     * GET  /charts : get all the charts filtered by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of charts in body
     */
    @GetMapping("/all-charts/{id}")
    @Timed
    public ResponseEntity<List<ChartVO>> getAllCharts(@PathVariable Long id,
                                                      @RequestParam(value = "page", required = false) Integer offset,
                                                      @RequestParam(value = "size", required = false) Integer size,
                                                      @RequestParam(value = "state", required = false) String state,
                                                      @RequestParam(value = "query", required = false) String query
    ) throws URISyntaxException {
        log.debug("REST request to get all Charts By Facility");
        Pageable pageable = new PageRequest(offset, size);
        //Query Map
        Map<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("state", state);
        mapQuery.put("query", query);

        Page<ChartVO> chartVOS = chartService.findAllCharts(id, pageable, mapQuery);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(chartVOS, "api/all-charts/" + id);
        return new ResponseEntity<>(chartVOS.getContent(), httpHeaders, HttpStatus.OK);
    }

    /**
     * Get charts filtered by facility and by a date of medications
     *
     * @param id of Facility
     * @return
     */
    @GetMapping("/charts-medications/{id}")
    @Timed
    public List<Chart> getAllChartMedicationsByDate(@PathVariable Long id) {
        log.debug("REST request to get all Charts By Facility and with medication by date");
        return chartService.findAllMedicationsByDay(id);
    }

    /**
     * Get the last chart belong to a patient
     *
     * @param facId
     * @param patId
     * @return Chart of the patient
     */
    @GetMapping("/last-chart-by-patient/{facId}/{patId}")
    public Chart getLastChartByPatient(@PathVariable Long facId, @PathVariable Long patId) {
        log.debug("Finding the list of chart by patient");
        List<Chart> list = chartService.findAllByPatient(facId, patId);
        if (list.size() > 0)
            return list.get(0);
        else
            return new Chart();
    }


    @PutMapping("/update-chart-picture")
    @Timed
    public ResponseEntity<Picture> updateChartPicture(@RequestBody PictureVO pictureVO) throws URISyntaxException {
        log.debug("REST request to update Images Chart : {}");

        Picture result = chartService.updatePicture(pictureVO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("picture", result.getId().toString()))
            .body(result);
    }


//    @GetMapping("/update-all-chart-picture/{fId}")
//    @Timed
//    public ResponseEntity<BooleanWrapper> updateAllChartPictures(@PathVariable Long fId) {
//        log.debug("REST request to update Picture Chart : {}", fId);
//        BooleanWrapper bw = new BooleanWrapper( chartService.updateAllPicture(fId));
//        return Optional.ofNullable(bw)
//            .map(result -> new ResponseEntity<>(
//                result,
//                HttpStatus.OK))
//            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @GetMapping("/chart-find-by-chart-to-form/{id}")
    @Timed
    public ResponseEntity<Chart> findByChartToForm(@PathVariable Long id) {
        log.debug("REST request to update findByChartToForm : {}", id);
        Chart c = chartService.findByChartToForm(id);
        return Optional.ofNullable(c)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /charts/:id : get the "id" chart.
     *
     * @param id the id of the chart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chart, or with status 404 (Not Found)
     */
    @GetMapping("/charts/patient/{id}")
    @Timed
    public Patient getPatientByChart(@PathVariable Long id) {
        log.debug("REST request to get Chart : {}", id);
        Patient patient = chartService.findPatientByChart(id);
        return patient;
    }


}
