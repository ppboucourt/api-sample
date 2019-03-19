package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.TypePatientProcess;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.service.TypePatientProcessService;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.EvaluationTemplate;
import co.tmunited.bluebook.service.EvaluationTemplateService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing EvaluationTemplate.
 */
@RestController
@RequestMapping("/api")
public class EvaluationTemplateResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationTemplateResource.class);

    @Inject
    private EvaluationTemplateService evaluationTemplateService;

    @Inject
    private TypePatientProcessService typePatientProcessService;

    /**
     * POST  /evaluation-templates : Create a new evaluationTemplate.
     *
     * @param evaluationTemplate the evaluationTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluationTemplate, or with status 400 (Bad Request) if the evaluationTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluation-templates")
    @Timed
    public ResponseEntity<EvaluationTemplate> createEvaluationTemplate(@RequestBody EvaluationTemplate evaluationTemplate) throws URISyntaxException {
        log.debug("REST request to save EvaluationTemplate : {}", evaluationTemplate);
        if (evaluationTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("evaluationTemplate", "idexists", "A new evaluationTemplate cannot already have an ID")).body(null);
        }
        EvaluationTemplate result = evaluationTemplateService.save(evaluationTemplate);
        return ResponseEntity.created(new URI("/api/evaluation-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("evaluationTemplate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluation-templates : Updates an existing evaluationTemplate.
     *
     * @param evaluationTemplate the evaluationTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluationTemplate,
     * or with status 400 (Bad Request) if the evaluationTemplate is not valid,
     * or with status 500 (Internal Server Error) if the evaluationTemplate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluation-templates")
    @Timed
    public ResponseEntity<EvaluationTemplate> updateEvaluationTemplate(@RequestBody EvaluationTemplate evaluationTemplate) throws URISyntaxException {
        log.debug("REST request to update EvaluationTemplate : {}", evaluationTemplate);
        if (evaluationTemplate.getId() == null) {
            return createEvaluationTemplate(evaluationTemplate);
        }
        EvaluationTemplate result = evaluationTemplateService.save(evaluationTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("evaluationTemplate", evaluationTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluation-templates : get all the evaluationTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evaluationTemplates in body
     */
    @GetMapping("/evaluation-templates")
    @Timed
    public List<EvaluationTemplate> getAllEvaluationTemplates() {
        log.debug("REST request to get all EvaluationTemplates");
        return evaluationTemplateService.findAll();
    }

    /**
     * GET  /evaluation-templates/:id : get the "id" evaluationTemplate.
     *
     * @param id the id of the evaluationTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluationTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/evaluation-templates/{id}")
    @Timed
    public ResponseEntity<EvaluationTemplate> getEvaluationTemplate(@PathVariable Long id) {
        log.debug("REST request to get EvaluationTemplate : {}", id);
        EvaluationTemplate evaluationTemplate = evaluationTemplateService.findOne(id);
        return Optional.ofNullable(evaluationTemplate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /evaluation-templates/:id : delete the "id" evaluationTemplate.
     *
     * @param id the id of the evaluationTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluation-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvaluationTemplate(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationTemplate : {}", id);
        evaluationTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evaluationTemplate", id.toString())).build();
    }

    /**
     * SEARCH  /_search/evaluation-templates?query=:query : search for the evaluationTemplate corresponding
     * to the query.
     *
     * @param query the query of the evaluationTemplate search
     * @return the result of the search
     */
    @GetMapping("/_search/evaluation-templates")
    @Timed
    public List<EvaluationTemplate> searchEvaluationTemplates(@RequestParam String query) {
        log.debug("REST request to search EvaluationTemplates for query {}", query);
        return evaluationTemplateService.search(query);
    }

    @GetMapping("/evaluation-template/patient-process/{ppId}/{facId}")
    @Timed
    public List<FormVO> findAllByPatientProcess(@PathVariable Long ppId, @PathVariable Long facId) {
        log.debug("REST request to get a list of EvaluationTemplate filtered by patientProcess and facilityId : {}, {}", ppId, facId);
        return evaluationTemplateService.findAllByPatientProcess(ppId, facId);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/evaluation-template/facility/{id}")
    @Timed
    public List<EvaluationTemplate> getAllEvaluationTemplatesByFacility(@PathVariable Long id) {
        log.debug("REST request to get all EvaluationTemplates, {}", id);
        return evaluationTemplateService.findAllByFacility(id);
    }

    /**
     *
     * @param ppId
     * @param facId
     * @param lcId
     * @return
     */
    @GetMapping("/evaluation-template/level-care/{ppId}/{facId}/{lcId}")
    public List<FormVO> getAllByPatientProcessLevelCareFacility(@PathVariable Long ppId, @PathVariable Long facId, @PathVariable Long lcId) {
        log.debug("REST request to get all EvaluationTemplates filtered by patientProcess, facility and levelCare, {} {} {}", ppId, facId, lcId);
        return evaluationTemplateService.findAllByPatientProcessLevelCareFacility(ppId, facId, lcId);
    }

}
