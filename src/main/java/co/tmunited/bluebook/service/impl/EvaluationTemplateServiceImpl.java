package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.service.EvaluationTemplateService;
import co.tmunited.bluebook.domain.EvaluationTemplate;
import co.tmunited.bluebook.repository.EvaluationTemplateRepository;
import co.tmunited.bluebook.repository.search.EvaluationTemplateSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EvaluationTemplate.
 */
@Service
@Transactional
public class EvaluationTemplateServiceImpl implements EvaluationTemplateService{

    private final Logger log = LoggerFactory.getLogger(EvaluationTemplateServiceImpl.class);

    @Inject
    private EvaluationTemplateRepository evaluationTemplateRepository;

    @Inject
    private EvaluationTemplateSearchRepository evaluationTemplateSearchRepository;

    /**
     * Save a evaluationTemplate.
     *
     * @param evaluationTemplate the entity to save
     * @return the persisted entity
     */
    public EvaluationTemplate save(EvaluationTemplate evaluationTemplate) {
        log.debug("Request to save EvaluationTemplate : {}", evaluationTemplate);
        EvaluationTemplate result = evaluationTemplateRepository.save(evaluationTemplate);
        evaluationTemplateSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the evaluationTemplates.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EvaluationTemplate> findAll() {
        log.debug("Request to get all EvaluationTemplates");
        List<EvaluationTemplate> result = evaluationTemplateRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one evaluationTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public EvaluationTemplate findOne(Long id) {
        log.debug("Request to get EvaluationTemplate : {}", id);
        EvaluationTemplate evaluationTemplate = evaluationTemplateRepository.findOne(id);

        if(evaluationTemplate.getStaffReviewSignatureAuthorities() != null){
            evaluationTemplate.getStaffReviewSignatureAuthorities().size();
        }

        if(evaluationTemplate.getStaffSignatureAuthority() != null){
            evaluationTemplate.getStaffSignatureAuthority().size();
        }

        evaluationTemplate.getTypeLevelCares().size();

        return evaluationTemplate;
    }

    /**
     *  Delete the  evaluationTemplate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EvaluationTemplate : {}", id);
        EvaluationTemplate evaluationTemplate = evaluationTemplateRepository.findOne(id);
        evaluationTemplate.setDelStatus(true);
        EvaluationTemplate result = evaluationTemplateRepository.save(evaluationTemplate);

        evaluationTemplateSearchRepository.save(result);
    }

    /**
     * Search for the evaluationTemplate corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EvaluationTemplate> search(String query) {
        log.debug("Request to search EvaluationTemplates for query {}", query);
        return StreamSupport
            .stream(evaluationTemplateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public List<FormVO> findAllByPatientProcess(Long ppId, Long facId) {
        log.debug("Searchin all EvaluationsTemplates by PatientProces and Facility {}, {}", ppId, facId);
        List<EvaluationTemplate> result = evaluationTemplateRepository.findAllByDelStatusIsFalseAndEnabledIsTrueAndTypePatientProcessIdAndFacilityId(ppId, facId);
        List<FormVO> evaluationTemplates = new ArrayList<>();

        result.stream().map(evaluation -> {
            evaluationTemplates.add(evaluation.evaluationTemplateToFormVO(evaluation));
            return evaluation;
        }).collect(Collectors.toList());

        return evaluationTemplates;
    }

    @Override
    public List<EvaluationTemplate> findAllByFacility(Long id) {
        log.debug("Searchin all EvaluationsTemplates by PatientProces and Facility {}, {}", id);
        List<EvaluationTemplate> result = evaluationTemplateRepository.findAllByDelStatusIsFalseAndFacilityId(id);

//        result.stream().map(evaluation -> {
//            if (evaluation.getTypeLevelCares() != null)
//                evaluation.getTypeLevelCares().size();
//            return evaluation;
//        }).collect(Collectors.toList());

        return result;
    }

    public List<FormVO> findAllByPatientProcessLevelCareFacility(Long ppId, Long facId, Long lcId) {
        List<FormVO> result = evaluationTemplateRepository.findAllByPatientProcessFacilityAndLevelCare(ppId, facId, lcId);
        return result;
    }
}
