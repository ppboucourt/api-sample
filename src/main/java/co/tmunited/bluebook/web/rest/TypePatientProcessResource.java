package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypePatientProcess;
import co.tmunited.bluebook.service.TypePatientProcessService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TypePatientProcess.
 */
@RestController
@RequestMapping("/api")
public class TypePatientProcessResource {

    private final Logger log = LoggerFactory.getLogger(TypePatientProcessResource.class);

    @Inject
    private TypePatientProcessService typePatientProcessService;

    /**
     * POST  /type-patient-processes : Create a new typePatientProcess.
     *
     * @param typePatientProcess the typePatientProcess to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePatientProcess, or with status 400 (Bad Request) if the typePatientProcess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-patient-processes")
    @Timed
    public ResponseEntity<TypePatientProcess> createTypePatientProcess(@Valid @RequestBody TypePatientProcess typePatientProcess) throws URISyntaxException {
        log.debug("REST request to save TypePatientProcess : {}", typePatientProcess);
        if (typePatientProcess.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typePatientProcess", "idexists", "A new typePatientProcess cannot already have an ID")).body(null);
        }
        TypePatientProcess result = typePatientProcessService.save(typePatientProcess);
        return ResponseEntity.created(new URI("/api/type-patient-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typePatientProcess", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-patient-processes : Updates an existing typePatientProcess.
     *
     * @param typePatientProcess the typePatientProcess to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePatientProcess,
     * or with status 400 (Bad Request) if the typePatientProcess is not valid,
     * or with status 500 (Internal Server Error) if the typePatientProcess couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-patient-processes")
    @Timed
    public ResponseEntity<TypePatientProcess> updateTypePatientProcess(@Valid @RequestBody TypePatientProcess typePatientProcess) throws URISyntaxException {
        log.debug("REST request to update TypePatientProcess : {}", typePatientProcess);
        if (typePatientProcess.getId() == null) {
            return createTypePatientProcess(typePatientProcess);
        }
        TypePatientProcess result = typePatientProcessService.save(typePatientProcess);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typePatientProcess", typePatientProcess.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-patient-processes : get all the typePatientProcesses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePatientProcesses in body
     */
    @GetMapping("/type-patient-processes")
    @Timed
    public List<TypePatientProcess> getAllTypePatientProcesses() {
        log.debug("REST request to get all TypePatientProcesses");
        return typePatientProcessService.findAll();
    }

    /**
     * GET  /type-patient-processes/:id : get the "id" typePatientProcess.
     *
     * @param id the id of the typePatientProcess to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePatientProcess, or with status 404 (Not Found)
     */
    @GetMapping("/type-patient-processes/{id}")
    @Timed
    public ResponseEntity<TypePatientProcess> getTypePatientProcess(@PathVariable Long id) {
        log.debug("REST request to get TypePatientProcess : {}", id);
        TypePatientProcess typePatientProcess = typePatientProcessService.findOne(id);
        return Optional.ofNullable(typePatientProcess)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-patient-processes/:id : delete the "id" typePatientProcess.
     *
     * @param id the id of the typePatientProcess to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-patient-processes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePatientProcess(@PathVariable Long id) {
        log.debug("REST request to delete TypePatientProcess : {}", id);
        typePatientProcessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typePatientProcess", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-patient-processes?query=:query : search for the typePatientProcess corresponding
     * to the query.
     *
     * @param query the query of the typePatientProcess search
     * @return the result of the search
     */
    @GetMapping("/_search/type-patient-processes")
    @Timed
    public List<TypePatientProcess> searchTypePatientProcesses(@RequestParam String query) {
        log.debug("REST request to search TypePatientProcesses for query {}", query);
        return typePatientProcessService.search(query);
    }

    /**
     * GET  /type-patient-processes/by-type-page : get all the typePatientProcesses filtered by typePage.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePatientProcesses in body
     *
     */
    @GetMapping("/type-patient-processes/by-type-page/{pagId}/{facId}")
    @Timed
    public List<TypePatientProcess> getAllTypePatientProcessesByTypePage(@PathVariable Long pagId, @PathVariable Long facId) {
        log.debug("REST request to get all TypePatientProcesses filtered by TypePage");
        return typePatientProcessService.findAllByTypePage(pagId, facId);
    }

    /**
     * GET  /type-patient-processes/by-type-page : get all the typePatientProcesses filtered by typePage.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePatientProcesses in body
     *
     */
//    @GetMapping("/type-patient-processes/by-type-page/{pagId}/{facId}")
//    @Timed
//    public List<TypePatientProcess> getAllTypePatientProcessesVOByTypePage(@PathVariable Long pagId, @PathVariable Long facId) {
//        log.debug("REST request to get all TypePatientProcesses filtered by TypePage");
//        return typePatientProcessService.findAllVOByTypePage(pagId, facId);
//    }


}
