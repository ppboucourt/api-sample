package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.FormVO;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Evaluation;
import co.tmunited.bluebook.service.EvaluationService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.itextpdf.kernel.xmp.impl.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Evaluation.
 */
@RestController
@RequestMapping("/api")
public class EvaluationResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationResource.class);

    @Inject
    private EvaluationService evaluationService;

    /**
     * POST  /evaluations : Create a new evaluation.
     *
     * @param evaluation the evaluation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluation, or with status 400 (Bad Request) if the evaluation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluations")
    @Timed
    public ResponseEntity<Evaluation> createEvaluation(@RequestBody Evaluation evaluation) throws URISyntaxException {
        log.debug("REST request to save Evaluation : {}", evaluation);
        if (evaluation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("evaluation", "idexists", "A new evaluation cannot already have an ID")).body(null);
        }
        Evaluation result = evaluationService.save(evaluation);
        return ResponseEntity.created(new URI("/api/evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("evaluation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluations : Updates an existing evaluation.
     *
     * @param evaluation the evaluation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluation,
     * or with status 400 (Bad Request) if the evaluation is not valid,
     * or with status 500 (Internal Server Error) if the evaluation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluations")
    @Timed
    public ResponseEntity<Evaluation> updateEvaluation(@RequestBody Evaluation evaluation) throws URISyntaxException {
        log.debug("REST request to update Evaluation : {}", evaluation);
        if (evaluation.getId() == null) {
            return createEvaluation(evaluation);
        }
        Evaluation result = evaluationService.save(evaluation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("evaluation", evaluation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluations : get all the evaluations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evaluations in body
     */
    @GetMapping("/evaluations")
    @Timed
    public List<Evaluation> getAllEvaluations() {
        log.debug("REST request to get all Evaluations");
        return evaluationService.findAll();
    }

    /**
     * GET  /evaluations/:id : get the "id" evaluation.
     *
     * @param id the id of the evaluation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluation, or with status 404 (Not Found)
     */
    @GetMapping("/evaluations/{id}")
    @Timed
    public ResponseEntity<Evaluation> getEvaluation(@PathVariable Long id) {
        log.debug("REST request to get Evaluation : {}", id);
        Evaluation evaluation = evaluationService.findOne(id);
        return Optional.ofNullable(evaluation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /evaluations/:id : delete the "id" evaluation.
     *
     * @param id the id of the evaluation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluations/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete Evaluation : {}", id);
        evaluationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evaluation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/evaluations?query=:query : search for the evaluation corresponding
     * to the query.
     *
     * @param query the query of the evaluation search
     * @return the result of the search
     */
    @GetMapping("/_search/evaluations")
    @Timed
    public List<Evaluation> searchEvaluations(@RequestParam String query) {
        log.debug("REST request to search Evaluations for query {}", query);
        return evaluationService.search(query);
    }

    @GetMapping("/evaluations/patient-forms/{chId}/{ppId}")
    public List<FormVO> findAllByChartAndPatientProcess(@PathVariable Long chId, @PathVariable Long ppId){
        log.debug("REST request to get a list of evaluation filtered by Chart and PatientProcess : {}{}", chId, ppId);
        return evaluationService.findAllByPatientProcessAndChart(chId, ppId);
    }

    @PostMapping("/evaluations/assign")
    public ResponseEntity<Void> assignEvaluation(@RequestBody CollectedBody collectedBody) {
        log.debug("REST request to assign forms to a chart ");
        evaluationService.assignForms(collectedBody);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/evaluation/migrate-base64-to-json-string")
    public ResponseEntity<Boolean> migrateJsonBase64ToJson() {
        log.debug("REST request to migrate data from base64 to json ");
        Boolean result = false;
        try {
            List<Evaluation> evaluations = evaluationService.findAll();
            evaluations.forEach(evaluation -> {
                try {
                    evaluation.setJsonDataTmp(Base64.decode(evaluation.getJsonData()));
                    evaluation.setJsonTemplateTmp(Base64.decode(evaluation.getJsonTemplate()));
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(evaluation.getId().toString());
                }
            });
            result =  new Boolean(true);
        }catch (Exception e) {
            e.printStackTrace();
//            return new ResponseEntity<>(new Boolean(false), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Boolean(result), HttpStatus.OK);
    }


}
