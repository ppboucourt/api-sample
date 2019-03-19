package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.Evaluation;
import co.tmunited.bluebook.domain.Signature;
import co.tmunited.bluebook.domain.enumeration.EvaluationStatus;
import co.tmunited.bluebook.domain.enumeration.SignTypeValues;
import co.tmunited.bluebook.domain.vo.EvaluationSignatureVO;
import co.tmunited.bluebook.repository.EvaluationRepository;
import co.tmunited.bluebook.repository.SignatureRepository;
import co.tmunited.bluebook.repository.search.EvaluationSearchRepository;
import co.tmunited.bluebook.repository.search.SignatureSearchRepository;
import co.tmunited.bluebook.service.EvaluationSignatureService;
import co.tmunited.bluebook.domain.EvaluationSignature;
import co.tmunited.bluebook.repository.EvaluationSignatureRepository;
import co.tmunited.bluebook.repository.search.EvaluationSignatureSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EvaluationSignature.
 */
@Service
@Transactional
public class EvaluationSignatureServiceImpl implements EvaluationSignatureService {

    private final Logger log = LoggerFactory.getLogger(EvaluationSignatureServiceImpl.class);

    @Inject
    private EvaluationSignatureRepository evaluationSignatureRepository;

    @Inject
    private EvaluationSignatureSearchRepository evaluationSignatureSearchRepository;

    @Inject
    private EvaluationRepository evaluationRepository;

    @Inject
    private EvaluationSearchRepository evaluationSearchRepository;

    @Inject
    private SignatureRepository signatureRepository;

    @Inject
    private SignatureSearchRepository signatureSearchRepository;

    /**
     * Save a evaluationSignature.
     *
     * @param evaluationSignature the entity to save
     * @return the persisted entity
     */
    public EvaluationSignature save(EvaluationSignature evaluationSignature, String ip) {
        log.debug("Request to save EvaluationSignature : {}", evaluationSignature);

        if (evaluationSignature.getSignType().equals(SignTypeValues.Reviewed)) {
            evaluationSignature.getEvaluation().setStatus(EvaluationStatus.Finalized);
        }

        if (evaluationSignature.getSignature() != null) {// Employee Signature
            Signature signature = signatureRepository.save(evaluationSignature.getSignature().ip(ip));
            signatureSearchRepository.save(signature);
            evaluationSignature.setSignature(signature);
        }

        if (evaluationSignature.getPatientSignature() != null) {// Patient Signature
            Signature signature = signatureRepository.save(evaluationSignature.getPatientSignature().ip(ip));
            signatureSearchRepository.save(signature);
            evaluationSignature.setSignature(signature);
        }

        EvaluationSignature result = evaluationSignatureRepository.save(evaluationSignature);
        evaluationSignatureSearchRepository.save(result);

        Evaluation evaluation = result.getEvaluation();

        evaluation = evaluationRepository.save(evaluation);

        evaluationSearchRepository.save(evaluation);

        return result;
    }

    /**
     * Get all the evaluationSignatures.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EvaluationSignature> findAll() {
        log.debug("Request to get all EvaluationSignatures");
        List<EvaluationSignature> result = evaluationSignatureRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one evaluationSignature by id.v
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EvaluationSignature findOne(Long id) {
        log.debug("Request to get EvaluationSignature : {}", id);
        EvaluationSignature evaluationSignature = evaluationSignatureRepository.findOne(id);
        return evaluationSignature;
    }

    /**
     * Delete the  evaluationSignature by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EvaluationSignature : {}", id);
        EvaluationSignature evaluationSignature = evaluationSignatureRepository.findOne(id);
        evaluationSignature.setDelStatus(true);
        EvaluationSignature result = evaluationSignatureRepository.save(evaluationSignature);

        evaluationSignatureSearchRepository.save(result);
    }

    /**
     * Search for the evaluationSignature corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EvaluationSignature> search(String query) {
        log.debug("Request to search EvaluationSignatures for query {}", query);
        return StreamSupport
            .stream(evaluationSignatureSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Get List evaluationSignature by Evaluation id.
     *
     * @param id the id of the evaluation
     * @return the List
     */
    @Transactional(readOnly = true)
    public List<EvaluationSignature> findByEvaluationId(Long id) {
        return evaluationSignatureRepository.findAllByDelStatusIsFalseAndEvaluationId(id);
    }

    /**
     * Bring a EvaluationSignatureVO
     *
     * @param id of the evaluation
     * @return
     */
    public EvaluationSignatureVO findByEvaluation(Long id) {
        EvaluationSignatureVO evaluationSignatureVO = evaluationSignatureRepository.findByEvaluation(id);
        return evaluationSignatureVO;
    }
}
