package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.PatientResultFile;
import co.tmunited.bluebook.service.PatientResultFileService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PatientResultFile.
 */
@RestController
@RequestMapping("/api")
public class PatientResultFileResource {

    private final Logger log = LoggerFactory.getLogger(PatientResultFileResource.class);

    @Inject
    private PatientResultFileService patientResultFileService;

    /**
     * POST  /patient-result-files : Create a new patientResultFile.
     *
     * @param patientResultFile the patientResultFile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientResultFile, or with status 400 (Bad Request) if the patientResultFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-result-files")
    @Timed
    public ResponseEntity<PatientResultFile> createPatientResultFile(@Valid @RequestBody PatientResultFile patientResultFile) throws URISyntaxException {
        log.debug("REST request to save PatientResultFile : {}", patientResultFile);
        if (patientResultFile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("patientResultFile", "idexists", "A new patientResultFile cannot already have an ID")).body(null);
        }
        PatientResultFile result = patientResultFileService.save(patientResultFile);
        return ResponseEntity.created(new URI("/api/patient-result-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("patientResultFile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-result-files : Updates an existing patientResultFile.
     *
     * @param patientResultFile the patientResultFile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientResultFile,
     * or with status 400 (Bad Request) if the patientResultFile is not valid,
     * or with status 500 (Internal Server Error) if the patientResultFile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-result-files")
    @Timed
    public ResponseEntity<PatientResultFile> updatePatientResultFile(@Valid @RequestBody PatientResultFile patientResultFile) throws URISyntaxException {
        log.debug("REST request to update PatientResultFile : {}", patientResultFile);
        if (patientResultFile.getId() == null) {
            return createPatientResultFile(patientResultFile);
        }
        PatientResultFile result = patientResultFileService.save(patientResultFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("patientResultFile", patientResultFile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-result-files : get all the patientResultFiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientResultFiles in body
     */
    @GetMapping("/patient-result-files")
    @Timed
    public List<PatientResultFile> getAllPatientResultFiles() {
        log.debug("REST request to get all PatientResultFiles");
        return patientResultFileService.findAll();
    }

    /**
     * GET  /patient-result-files/:id : get the "id" patientResultFile.
     *
     * @param id the id of the patientResultFile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientResultFile, or with status 404 (Not Found)
     */
    @GetMapping("/patient-result-files/{id}")
    @Timed
    public ResponseEntity<PatientResultFile> getPatientResultFile(@PathVariable Long id) {
        log.debug("REST request to get PatientResultFile : {}", id);
        PatientResultFile patientResultFile = patientResultFileService.findOne(id);
        return Optional.ofNullable(patientResultFile)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /patient-result-files/:id : delete the "id" patientResultFile.
     *
     * @param id the id of the patientResultFile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-result-files/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientResultFile(@PathVariable Long id) {
        log.debug("REST request to delete PatientResultFile : {}", id);
        patientResultFileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("patientResultFile", id.toString())).build();
    }

    /**
     * SEARCH  /_search/patient-result-files?query=:query : search for the patientResultFile corresponding
     * to the query.
     *
     * @param query the query of the patientResultFile search
     * @return the result of the search
     */
    @GetMapping("/_search/patient-result-files")
    @Timed
    public List<PatientResultFile> searchPatientResultFiles(@RequestParam String query) {
        log.debug("REST request to search PatientResultFiles for query {}", query);
        return patientResultFileService.search(query);
    }

    //Get PDF
    @PostMapping(path = "/patient-result-files/by-patient-result/getpdf")
    public ResponseEntity<Resource> findLastByPatientResultId(@RequestBody Long id) throws IOException {
        return patientResultFileService.findLastByPatientResultId(id);
    }

    @PostMapping(path = "/patient-result-files/get-pdf")
    public ResponseEntity<Resource> download(@RequestBody Long id) throws IOException {
        return patientResultFileService.download(id);
    }

}
