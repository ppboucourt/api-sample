package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.File;
import co.tmunited.bluebook.domain.PatientResultFile;
import co.tmunited.bluebook.domain.enumeration.FormStatus;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.util.FormJson;
import co.tmunited.bluebook.domain.vo.ChartToFormVO;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.service.ChartService;
import co.tmunited.bluebook.service.ContactsService;
import co.tmunited.bluebook.service.FileService;
import co.tmunited.bluebook.service.util.CollectRequestDetails;
import co.tmunited.bluebook.service.util.RandomUtil;
import co.tmunited.bluebook.web.rest.client.FileRestClient;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.ChartToForm;
import co.tmunited.bluebook.service.ChartToFormService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ChartToForm.
 */
@RestController
@RequestMapping("/api")
public class ChartToFormResource {

    private final Logger log = LoggerFactory.getLogger(ChartToFormResource.class);

    @Inject
    private ChartToFormService chartToFormService;

    @Inject
    private ContactsService contactsService;

    @Inject
    private FileService fileService;

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private ChartService chartService;

    /**
     * POST  /chart-to-forms : Create a new chartToForm.
     *
     * @param chartToForm the chartToForm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chartToForm, or with status 400 (Bad Request) if the chartToForm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chart-to-forms")
    @Timed
    public ResponseEntity<ChartToForm> createChartToForm(@RequestBody ChartToForm chartToForm) throws URISyntaxException {
        log.debug("REST request to save ChartToForm : {}", chartToForm);
        if (chartToForm.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chartToForm", "idexists", "A new chartToForm cannot already have an ID")).body(null);
        }
        ChartToForm result = chartToFormService.save(chartToForm);
        return ResponseEntity.created(new URI("/api/chart-to-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chartToForm", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chart-to-forms : Updates an existing chartToForm.
     *
     * @param chartToForm the chartToForm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chartToForm,
     * or with status 400 (Bad Request) if the chartToForm is not valid,
     * or with status 500 (Internal Server Error) if the chartToForm couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chart-to-forms")
    @Timed
    public ResponseEntity<ChartToForm> updateChartToForm(@RequestBody ChartToForm chartToForm) throws URISyntaxException {
        log.debug("REST request to update ChartToForm : {}", chartToForm);
        if (chartToForm.getId() == null) {
            return createChartToForm(chartToForm);
        }
        ChartToForm result = chartToFormService.save(chartToForm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chartToForm", chartToForm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chart-to-forms : get all the chartToForms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chartToForms in body
     */
    @GetMapping("/chart-to-forms")
    @Timed
    public List<ChartToForm> getAllChartToForms() {
        log.debug("REST request to get all ChartToForms");
        return chartToFormService.findAll();
    }

    /**
     * GET  /chart-to-forms/:id : get the "id" chartToForm.
     *
     * @param id the id of the chartToForm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chartToForm, or with status 404 (Not Found)
     */
    @GetMapping("/chart-to-forms/{id}")
    @Timed
    public ResponseEntity<ChartToForm> getChartToForm(@PathVariable Long id) {
        log.debug("REST request to get ChartToForm : {}", id);
        ChartToForm chartToForm = chartToFormService.findOne(id);
        return Optional.ofNullable(chartToForm)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chart-to-forms/:id : delete the "id" chartToForm.
     *
     * @param id the id of the chartToForm to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chart-to-forms/{id}")
    @Timed
    public ResponseEntity<Void> deleteChartToForm(@PathVariable Long id) {
        log.debug("REST request to delete ChartToForm : {}", id);
        chartToFormService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chartToForm", id.toString())).build();
    }

    /**
     * SEARCH  /_search/chart-to-forms?query=:query : search for the chartToForm corresponding
     * to the query.
     *
     * @param query the query of the chartToForm search
     * @return the result of the search
     */
    @GetMapping("/_search/chart-to-forms")
    @Timed
    public List<ChartToForm> searchChartToForms(@RequestParam String query) {
        log.debug("REST request to search ChartToForms for query {}", query);
        return chartToFormService.search(query);
    }

    @GetMapping("/chart-to-forms/chart/{id}")
    @Timed
    public List<ChartToForm> findByChart(@PathVariable Long id){
        return chartToFormService.findAllByChart(id);
    }

    @GetMapping("/chart-to-forms/chart-patient-process/{chId}/{ppId}")
    public List<FormVO> findByChartAndPatientProcess(@PathVariable Long chId, @PathVariable Long ppId){
        return chartToFormService.findAllPatientProcessInJsonForm(chId, ppId);
    }

    @PostMapping("/chart-to-forms/assign")
    public ResponseEntity<Void> assignForms(@RequestBody CollectedBody collectedBody) {
        log.debug("REST request to assign forms to a chart ");
        chartToFormService.assignForms(collectedBody);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/chart-to-forms/save-sign")
    @Timed
    public ResponseEntity<ChartToForm> saveSignConsent(@RequestBody ChartToFormVO chartToFormVO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save the signs of ChartToForm  : {}", chartToFormVO);

//        ObjectMapper mapper = new ObjectMapper();
//        FormJson formJson = new FormJson();
        ChartToForm chartToForm = chartToFormService.findOne(chartToFormVO.getId());
//        chartToForm.setHtmlData(Base64.getEncoder().encodeToString(chartToFormVO.getHtmlData().getBytes()));
        Chart chart = new Chart();
        if (chartToFormVO.getChart() != null) {
            chart = chartService.findOne(chartToFormVO.getChart());
        }
        chartToForm.setChart(chart);

//        try {
////            formJson = mapper.readValue(chartToForm.getJsonData(), FormJson.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String ip = CollectRequestDetails.getClientIpAddr(request);

        if(chartToFormVO.getSignaturePatient() != null && chartToFormVO.getSignaturePatient().getId() == null){
            chartToForm.setSignaturePatient(chartToFormVO.getSignaturePatient());
            chartToForm.getSignaturePatient().setIp(ip);
            chartToForm.getSignaturePatient().setDate(ZonedDateTime.now());
        }

        if(chartToFormVO.getSignatureGuarantor() != null && chartToFormVO.getSignatureGuarantor().getId() == null){
            chartToForm.setSignatureGuarantor(chartToFormVO.getSignatureGuarantor());
            chartToForm.getSignatureGuarantor().setIp(ip);
            chartToForm.getSignatureGuarantor().setDate(ZonedDateTime.now());
            if(chartToForm.getGuarantor() == null)
                chartToForm.setGuarantor(contactsService.getGuarantor(chartToForm.getChart().getId()));
        }

        if(chartToFormVO.getRevocationPatient() != null && chartToFormVO.getRevocationPatient().getId() == null){
            chartToForm.setRevocationPatient(chartToFormVO.getRevocationPatient());
            chartToForm.getRevocationPatient().setIp(ip);
            chartToForm.getRevocationPatient().setDate(ZonedDateTime.now());
        }

        if(chartToFormVO.getRevocationGuarantor() != null && chartToFormVO.getRevocationGuarantor().getId() == null){
            chartToForm.setRevocationGuarantor(chartToFormVO.getRevocationGuarantor());
            chartToForm.getRevocationGuarantor().setIp(ip);
            chartToForm.getRevocationGuarantor().setDate(ZonedDateTime.now());
            if(chartToForm.getGuarantor() == null)
                chartToForm.setGuarantor(contactsService.getGuarantor(chartToForm.getChart().getId()));
        }

        if(chartToForm.getPatientSignatureRequired() && chartToForm.getGuarantorSignatureRequired()){
            if(chartToForm.getSignaturePatient() != null || chartToForm.getSignatureGuarantor() != null){
                chartToForm.setStatus(FormStatus.InProcess);
            }if(chartToForm.getSignaturePatient() != null && chartToForm.getSignatureGuarantor() != null){
                chartToForm.setStatus(FormStatus.Finalized);
            }
        }else{
            if(chartToForm.getPatientSignatureRequired()){
                if(chartToForm.getSignaturePatient() != null)
                    chartToForm.setStatus(FormStatus.Finalized);
            }
            if(chartToForm.getGuarantorSignatureRequired()){
                if(chartToForm.getSignatureGuarantor() != null)
                    chartToForm.setStatus(FormStatus.Finalized);
            }
        }
        if(chartToForm.getAllowRevocation()){
            if(chartToForm.getPatientSignatureRequired() && chartToForm.getGuarantorSignatureRequired()){
                if(chartToForm.getRevocationPatient() != null && chartToForm.getRevocationGuarantor() != null){
                    chartToForm.setStatus(FormStatus.Revoked);
                }
            }else{
                if(chartToForm.getPatientSignatureRequired()){
                    if(chartToForm.getRevocationPatient() != null)
                        chartToForm.setStatus(FormStatus.Revoked);
                }
                if(chartToForm.getGuarantorSignatureRequired()){
                    if(chartToForm.getRevocationGuarantor() != null)
                        chartToForm.setStatus(FormStatus.Revoked);
                }
            }
        }

        if(chartToForm.getHtmlData() == null){
            chartToForm.setHtmlData(Base64.getEncoder().encodeToString(chartToFormVO.getHtmlData().getBytes()));
        }
        ChartToForm result = chartToFormService.save(chartToForm);

        return ResponseEntity.created(new URI("/api/chart-to-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chartToForm", result.getId().toString()))
            .body(result);
    }

    @RequestMapping(path = "/chart-to-form/get-attachments/{id}", method = RequestMethod.GET)
    public List<Resource> download(@PathVariable Long id) throws IOException {

        List<File> files = fileService.findAllByForm(id);
        List<Resource> results = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            results.add((Resource) FileRestClient.getInstance().getDownloadFileEntityResponse(
                jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                    jHipsterProperties.getFileStorage().getRemote().getUrlGetFile() + '/' + files.get(i).getUuid()));
        }

        return results;
    }

    @GetMapping("/chart-to-forms/vo/{id}")
    public ChartToFormVO getChartToFormVO(@PathVariable Long id) {
        log.info("REST request to find a ChartToFormVO {}", id);
        return chartToFormService.findOneVO(id);
    }

    @GetMapping("/chart-to-forms/migrate-json")
    public ResponseEntity<Void> migrateJsonToFields() {
        log.info("REST request to migrate json's data to the currents fields");
        try {
            chartToFormService.migrateJsonToFields();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chartToForm", e.getCause().toString(), e.getMessage())).body(null);
        }
        return ResponseEntity.ok().build();
    }

}
