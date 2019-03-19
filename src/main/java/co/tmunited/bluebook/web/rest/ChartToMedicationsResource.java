package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.ChartToMedications;
import co.tmunited.bluebook.service.ChartToMedicationsService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ChartToMedications.
 */
@RestController
@RequestMapping("/api")
public class ChartToMedicationsResource {

    private final Logger log = LoggerFactory.getLogger(ChartToMedicationsResource.class);

    @Inject
    private ChartToMedicationsService chartToMedicationsService;

    /**
     * POST  /chart-to-medications : Create a new chartToMedications.
     *
     * @param chartToMedications the chartToMedications to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chartToMedications, or with status 400 (Bad Request) if the chartToMedications has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chart-to-medications")
    @Timed
    public ResponseEntity<ChartToMedications> createChartToMedications(@RequestBody ChartToMedications chartToMedications) throws URISyntaxException {
        log.debug("REST request to save ChartToMedications : {}", chartToMedications);
        if (chartToMedications.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chartToMedications", "idexists", "A new chartToMedications cannot already have an ID")).body(null);
        }
        ChartToMedications result = chartToMedicationsService.save(chartToMedications);
        return ResponseEntity.created(new URI("/api/chart-to-medications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chartToMedications", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chart-to-medications : Updates an existing chartToMedications.
     *
     * @param chartToMedications the chartToMedications to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chartToMedications,
     * or with status 400 (Bad Request) if the chartToMedications is not valid,
     * or with status 500 (Internal Server Error) if the chartToMedications couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-to-medications")
    @Timed
    public ResponseEntity<ChartToMedications> updateChartToMedications(@RequestBody ChartToMedications chartToMedications) throws URISyntaxException {
        log.debug("REST request to update ChartToMedications : {}", chartToMedications);
        if (chartToMedications.getId() == null) {
            return createChartToMedications(chartToMedications);
        }
        ChartToMedications result = chartToMedicationsService.save(chartToMedications);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chartToMedications", chartToMedications.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chart-to-medications : get all the chartToMedications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chartToMedications in body
     */
    @GetMapping("/chart-to-medications")
    @Timed
    public List<ChartToMedications> getAllChartToMedications() {
        log.debug("REST request to get all ChartToMedications");
        return chartToMedicationsService.findAll();
    }

    /**
     * GET  /chart-to-medications/:id : get the "id" chartToMedications.
     *
     * @param id the id of the chartToMedications to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chartToMedications, or with status 404 (Not Found)
     */
    @GetMapping("/chart-to-medications/{id}")
    @Timed
    public ResponseEntity<ChartToMedications> getChartToMedications(@PathVariable Long id) {
        log.debug("REST request to get ChartToMedications : {}", id);
        ChartToMedications chartToMedications = chartToMedicationsService.findOne(id);
        return Optional.ofNullable(chartToMedications)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chart-to-medications/:id : delete the "id" chartToMedications.
     *
     * @param id the id of the chartToMedications to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chart-to-medications/{id}")
    @Timed
    public ResponseEntity<Void> deleteChartToMedications(@PathVariable Long id) {
        log.debug("REST request to delete ChartToMedications : {}", id);
        chartToMedicationsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chartToMedications", id.toString())).build();
    }

    /**
     * SEARCH  /_search/chart-to-medications?query=:query : search for the chartToMedications corresponding
     * to the query.
     *
     * @param query the query of the chartToMedications search
     * @return the result of the search
     */
    @GetMapping("/_search/chart-to-medications")
    @Timed
    public List<ChartToMedications> searchChartToMedications(@RequestParam String query) {
        log.debug("REST request to search ChartToMedications for query {}", query);
        return chartToMedicationsService.search(query);
    }

    @GetMapping("/charts-medications-facility/{id}")
    @Timed
    public List<ChartToMedications> getAllMedicationsByChartInCurrentFacility(@PathVariable Long id){
        log.debug("REST request to get ChartToMedications filtered vy charts whoe belong to the current facility : {}", id);
        return chartToMedicationsService.findAllMedicationsByChartsBelongToFacility(id);
    }

    @GetMapping("/prescription-distinct/{id}")
    @Timed
    public List<ZonedDateTime> getAllMedicationsPrescriptionDistinct(@PathVariable Long id){
        log.debug("REST request to get ChartToMedications filtered vy charts whoe belong to the current facility : {}", id);
        return chartToMedicationsService.findAllMedicationsByPatientInCurrentFacilityDistinctPrescription(id);
    }

}
