package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.vo.EvaluationSignatureVO;
import co.tmunited.bluebook.service.util.CollectRequestDetails;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.EvaluationSignature;
import co.tmunited.bluebook.service.EvaluationSignatureService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing EvaluationSignature.
 */
@RestController
@RequestMapping("/api")
public class EvaluationSignatureResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationSignatureResource.class);

    @Inject
    private EvaluationSignatureService evaluationSignatureService;

    /**
     * POST  /evaluation-signatures : Create a new evaluationSignature.
     *
     * @param evaluationSignature the evaluationSignature to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluationSignature, or with status 400 (Bad Request) if the evaluationSignature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluation-signatures")
    @Timed
    public ResponseEntity<EvaluationSignature> createEvaluationSignature(@RequestBody EvaluationSignature evaluationSignature, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save EvaluationSignature : {}", evaluationSignature);
        if (evaluationSignature.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("evaluationSignature", "idexists", "A new evaluationSignature cannot already have an ID")).body(null);
        }
        EvaluationSignature result = evaluationSignatureService.save(evaluationSignature, CollectRequestDetails.getClientIpAddr(request));
        return ResponseEntity.created(new URI("/api/evaluation-signatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("evaluationSignature", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluation-signatures : Updates an existing evaluationSignature.
     *
     * @param evaluationSignature the evaluationSignature to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluationSignature,
     * or with status 400 (Bad Request) if the evaluationSignature is not valid,
     * or with status 500 (Internal Server Error) if the evaluationSignature couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluation-signatures")
    @Timed
    public ResponseEntity<EvaluationSignature> updateEvaluationSignature(@RequestBody EvaluationSignature evaluationSignature, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update EvaluationSignature : {}", evaluationSignature);
        if (evaluationSignature.getId() == null) {
            return createEvaluationSignature(evaluationSignature, request);
        }
        EvaluationSignature result = evaluationSignatureService.save(evaluationSignature, CollectRequestDetails.getClientIpAddr(request));
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("evaluationSignature", evaluationSignature.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluation-signatures : get all the evaluationSignatures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evaluationSignatures in body
     */
    @GetMapping("/evaluation-signatures")
    @Timed
    public List<EvaluationSignature> getAllEvaluationSignatures() {
        log.debug("REST request to get all EvaluationSignatures");
        return evaluationSignatureService.findAll();
    }

    /**
     * GET  /evaluation-signatures/:id : get the "id" evaluationSignature.
     *
     * @param id the id of the evaluationSignature to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluationSignature, or with status 404 (Not Found)
     */
    @GetMapping("/evaluation-signatures/{id}")
    @Timed
    public ResponseEntity<EvaluationSignature> getEvaluationSignature(@PathVariable Long id) {
        log.debug("REST request to get EvaluationSignature : {}", id);
        EvaluationSignature evaluationSignature = evaluationSignatureService.findOne(id);
        return Optional.ofNullable(evaluationSignature)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /evaluation-signatures/:id : delete the "id" evaluationSignature.
     *
     * @param id the id of the evaluationSignature to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluation-signatures/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvaluationSignature(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationSignature : {}", id);
        evaluationSignatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evaluationSignature", id.toString())).build();
    }

    /**
     * SEARCH  /_search/evaluation-signatures?query=:query : search for the evaluationSignature corresponding
     * to the query.
     *
     * @param query the query of the evaluationSignature search
     * @return the result of the search
     */
    @GetMapping("/_search/evaluation-signatures")
    @Timed
    public List<EvaluationSignature> searchEvaluationSignatures(@RequestParam String query) {
        log.debug("REST request to search EvaluationSignatures for query {}", query);
        return evaluationSignatureService.search(query);
    }

    /**
     * GET  /evaluation-signatures-by-evaluation/:id : get the "id" Evaluation.
     *
     * @param id the id of the Evaluation to retrieve
     * @return the List with status 200 (OK) and with body the evaluationSignature, or with status 404 (Not Found)
     */
    @GetMapping("/evaluation-signatures-by-evaluation/{id}")
    @Timed
    public List<EvaluationSignature> getEvaluationSignaturesByEvaluation(@PathVariable Long id) {
        List<EvaluationSignature> evaluationSignatures = evaluationSignatureService.findByEvaluationId(id);
        return evaluationSignatures;
    }

    /**
     * Get EvaluationSignature by de Evaluation
     *
     * @param id
     * @return
     */
    @GetMapping("/evaluation-signatures/evaluation/{id}")
    public EvaluationSignatureVO getByEvaluation(@PathVariable Long id) {
        return evaluationSignatureService.findByEvaluation(id);
    }

}
