package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.domain.PatientResult;
import co.tmunited.bluebook.domain.PatientResultExtended;
import co.tmunited.bluebook.domain.PatientResultFile;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientResultVO;
import co.tmunited.bluebook.service.PatientResultFileService;
import co.tmunited.bluebook.service.PatientResultService;
import co.tmunited.bluebook.web.rest.client.FileRestClient;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing PatientResult.
 */
@RestController
@RequestMapping("/api")
public class PatientResultResource {

    private final Logger log = LoggerFactory.getLogger(PatientResultResource.class);

    @Inject
    private PatientResultService patientResultService;

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private PatientResultFileService patientResultFileService;

    /**
     * POST  /patient-results : Create a new patientResult.
     *
     * @param patientResult the patientResult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientResult, or with status 400 (Bad Request) if the patientResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-results")
    @Timed
    public ResponseEntity<PatientResult> createPatientResult(@RequestBody PatientResult patientResult) throws URISyntaxException {
        log.debug("REST request to save PatientResult : {}", patientResult);
        if (patientResult.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientResult", "idexists", "A new patientResult cannot already have an ID")).body(null);
        }
        PatientResult result = patientResultService.save(patientResult);
        return ResponseEntity.created(new URI("/api/patient-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientResult", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-results : Updates an existing patientResult.
     *
     * @param patientResult the patientResult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientResult,
     * or with status 400 (Bad Request) if the patientResult is not valid,
     * or with status 500 (Internal Server Error) if the patientResult couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-results")
    @Timed
    public ResponseEntity<PatientResult> updatePatientResult(@RequestBody PatientResult patientResult) throws URISyntaxException {
        log.debug("REST request to update PatientResult : {}", patientResult);
        if (patientResult.getId() == null) {
            return createPatientResult(patientResult);
        }
        PatientResult result = patientResultService.save(patientResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientResult", patientResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patients : get all the patients results.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patients result in body
     */
    @GetMapping("/patient-results-by-clinic/{id}")
    @Timed
    public List<PatientResult> getAllPatientResultsByClinic(@PathVariable Long id) {
        log.debug("REST request to get all Patients Result by clinic " + id);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime first = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.of(0, 0, 0));

        return patientResultService.findAllByClinic(id, first, now).stream().map(r -> {
            if (r.getPatientResultFiles().size() == 0) {
                PatientResultFile file = new PatientResultFile();
                file.setId((long) 1);
                file.setName("Default PDF");
                file.uuid("1");
                file.setCreatedDate(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()));

                r.getPatientResultFiles().add(file);
            }

            return r;
        }).collect(Collectors.toList());

//        return patientResultService.findAllByClinic(id, startDate, endDate);
    }

    @GetMapping("/patient-results-by-patient/{id}")
    @Timed
    public List<PatientResultVO> getAllPatientResultByPatient(@PathVariable Long id) {
        log.debug("REST request to get all Patients Result by Patient " + id);
        return patientResultService.findAllByPatientId(id);
    }

    /**
     * GET  /patient-results/:id : get the "id" patientResult.
     *
     * @param id the id of the patientResult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientResult, or with status 404 (Not Found)
     */
    @GetMapping("/patient-results/{id}")
    @Timed
    public ResponseEntity<PatientResult> getPatientResult(@PathVariable Long id) {
        log.debug("REST request to get PatientResult : {}", id);
        PatientResult patientResult = patientResultService.findOne(id);

        if (patientResult.getPatientResultFiles().size() == 0) {
            PatientResultFile file = new PatientResultFile();
            file.setId((long) 1);
            file.setName("Default PDF");
            file.uuid("1");
//            file.setCreatedDate(ZonedDateTime.of(LocalDateTime.now(),ZoneId.systemDefault()));

            patientResult.getPatientResultFiles().add(file);
        }

        return Optional.ofNullable(patientResult)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-results/:id : delete the "id" patientResult.
     *
     * @param id the id of the patientResult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-results/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientResult(@PathVariable Long id) {
        log.debug("REST request to delete PatientResult : {}", id);
        patientResultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientResult", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-results?query=:query : search for the patientResult corresponding
     * to the query.
     *
     * @param query the query of the patientResult search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-results")
    @Timed
    public List<PatientResult> searchPatientResults(@RequestParam String query) {
        log.debug("REST request to search PatientResults for query {}", query);
        return patientResultService.search(query);
    }

    /**
     * POST  /process-patient-result : Process a patientResult.
     *
     * @param patientResultExtended the patientResult to process
     * @return the ResponseEntity with status 201 (Created) and with body the new patientResult, or with status 400 (Bad Request) if the patientResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/process-patient-result")
    @Timed
    public ResponseEntity<PatientResult> processPatientResult(@RequestBody PatientResultExtended patientResultExtended) throws URISyntaxException {
        log.debug("REST request to process PatientResult from Hl7 file : {}", patientResultExtended.getParent());
        if (patientResultExtended.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientResult", "idexists", "A new patientResult already have an ID")).body(null);
        }
        PatientResult patientResult = patientResultService.processPatientResult(patientResultExtended);

        return Optional.ofNullable(patientResult)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/presult/getpdf/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {

        PatientResultFile patientResultFile = patientResultFileService.findOne(id);

        return FileRestClient.getInstance().getDownloadFileEntityResponse(
            jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                jHipsterProperties.getFileStorage().getRemote().getUrlGetFile() + '/' + patientResultFile.getUuid());
    }

    @GetMapping("/dfresults/{id}")
    @Timed
    public List<PatientResult> getMonthlyFinalResultByClinic(@PathVariable Long id) {
        return patientResultService.getMonthlyResultByClinic(id, "FINAL").stream().map(r -> {
            if (r.getPatientResultFiles().size() == 0) {
                PatientResultFile file = new PatientResultFile();
                file.setId((long) 1);
                file.setName("Default PDF");
                file.uuid("1");
                file.setCreatedDate(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()));

                r.getPatientResultFiles().add(file);
            }

            return r;
        }).collect(Collectors.toList());
    }

    @GetMapping("/dpresults/{id}")
    @Timed
    public List<PatientResult> getMonthlyPartialResultByClinic(@PathVariable Long id) {
        return patientResultService.getMonthlyResultByClinic(id, "PARTIAL").stream().map(r -> {
            if (r.getPatientResultFiles().size() == 0) {
                PatientResultFile file = new PatientResultFile();
                file.setId((long) 1);
                file.setName("Default PDF");
                file.uuid("1");
                file.setCreatedDate(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()));

                r.getPatientResultFiles().add(file);
            }

            return r;
        }).collect(Collectors.toList());
    }

    @GetMapping("/unassigned/{id}")
    @Timed
    public List<PatientResult> getUnassignedResultByClinic(@PathVariable Long id) {
        return patientResultService.getUnassignedResultByClinic(id).stream().map(r -> {
            if (r.getPatientResultFiles().size() == 0) {
                PatientResultFile file = new PatientResultFile();
                file.setId((long) 1);
                file.setName("Default PDF");
                file.uuid("1");
                file.setCreatedDate(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()));

                r.getPatientResultFiles().add(file);
            }

            return r;
        }).collect(Collectors.toList());
    }

    @PostMapping("/assign")
    @Timed
    public ResponseEntity<Void> collect(@RequestBody CollectedBody collectedBody) {
        patientResultService.assign(collectedBody);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/patient-results-by-clinic/critical/{id}")
    @Timed
    public List<PatientResult> getCriticalByClinic(@PathVariable Long id) {
        return patientResultService.getCriticalResultByClinic(id).stream().map(r -> {
            if (r.getPatientResultFiles().size() == 0) {
                PatientResultFile file = new PatientResultFile();
                file.setId((long) 1);
                file.setName("Default PDF");
                file.uuid("1");
                file.setCreatedDate(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()));

                r.getPatientResultFiles().add(file);
            }

            return r;
        }).collect(Collectors.toList());
    }

    @GetMapping("/patient-results-by-clinic/nonperform/{id}")
    @Timed
    public List<PatientResult> getNonPerformTestByClinic(@PathVariable Long id) {
        return patientResultService.getNonPerformTestByClinic(id).stream().map(r -> {
            if (r.getPatientResultFiles().size() == 0) {
                PatientResultFile file = new PatientResultFile();
                file.setId((long) 1);
                file.setName("Default PDF");
                file.uuid("1");
                file.setCreatedDate(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()));

                r.getPatientResultFiles().add(file);
            }

            return r;
        }).collect(Collectors.toList());
    }
}
