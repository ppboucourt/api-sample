package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.domain.Authority;
import co.tmunited.bluebook.domain.EvaluationTemplate;
import co.tmunited.bluebook.domain.File;
import co.tmunited.bluebook.domain.enumeration.EvaluationStatus;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.util.EvaluationJson;
import co.tmunited.bluebook.domain.vo.EvaluationSignatureVO;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.repository.ChartRepository;
import co.tmunited.bluebook.repository.EvaluationTemplateRepository;
import co.tmunited.bluebook.repository.FileRepository;
import co.tmunited.bluebook.service.EvaluationService;
import co.tmunited.bluebook.domain.Evaluation;
import co.tmunited.bluebook.repository.EvaluationRepository;
import co.tmunited.bluebook.repository.search.EvaluationSearchRepository;
import co.tmunited.bluebook.service.EvaluationSignatureService;
import co.tmunited.bluebook.service.FileService;
import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.bluebook.service.util.Base64Utils;
import co.tmunited.bluebook.service.util.StorageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Evaluation.
 */
@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    private final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    @Inject
    private EvaluationRepository evaluationRepository;

    @Inject
    private EvaluationSearchRepository evaluationSearchRepository;

    @Inject
    private EvaluationTemplateRepository evaluationTemplateRepository;

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private FileRepository fileRepository;

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private FileService fileService;

    @Inject
    private EvaluationSignatureService evaluationSignatureService;



    /**
     * Save a evaluation.
     *
     * @param evaluation the entity to save
     * @return the persisted entity
     */
    public Evaluation save(Evaluation evaluation) {
        log.debug("Request to save Evaluation : {}", evaluation);

        Evaluation result = evaluationRepository.save(evaluation);
        evaluationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the evaluations.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Evaluation> findAll() {
        log.debug("Request to get all Evaluations");
        List<Evaluation> result = evaluationRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one evaluation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Evaluation findOne(Long id) {
        log.debug("Request to get Evaluation : {}", id);
        Evaluation evaluation = evaluationRepository.findOne(id);

        return evaluation;
    }

    /**
     * Delete the  evaluation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Evaluation : {}", id);
        Evaluation evaluation = evaluationRepository.findOne(id);
        evaluation.setDelStatus(true);
        Evaluation result = evaluationRepository.save(evaluation);

        evaluationSearchRepository.save(result);
    }

    /**
     * Search for the evaluation corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Evaluation> search(String query) {
        log.debug("Request to search Evaluations for query {}", query);
        return StreamSupport
            .stream(evaluationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    public List<Evaluation> findAllByPatientProcess(Long ppId) {
        log.debug("Request to search evaluations by patientProcess{}", ppId);
        List<Evaluation> result = evaluationRepository.findAllByDelStatusIsFalseAndTypePatientProcess(ppId);
        return result;
    }

    public List<FormVO> findAllByPatientProcessAndChart(Long chId, Long ppId) {
        log.debug("Request evaluation by patientProcess and Chart {}, {}", ppId, chId);
        List<Evaluation> result = evaluationRepository.findAllByDelStatusIsFalseAndChartIdAndTypePatientProcessId(chId, ppId);
        List<FormVO> forms = new ArrayList<>();
        result.stream().map(eva -> {
            EvaluationSignatureVO evaluationSignature = evaluationSignatureService.findByEvaluation(eva.getId());
            forms.add(eva.evaluationToFormVO(eva, evaluationSignature));
            return eva;
        }).collect(Collectors.toList());
        return forms;
    }

    public List<Evaluation> findAllPatientProcessInJsonEvaluationTemplate(Long chId, Long ppId) {
        List<Evaluation> result = evaluationRepository.findAllByDelStatusIsFalseAndChartId(chId);
        ObjectMapper mapper = new ObjectMapper();
        result.removeIf(evaluation -> {
//            try {
//                if(evaluation.get != null && !chToForm.getJsonData().equals("")){
//                    FormJson formJson = mapper.readValue(chToForm.getJsonData(), FormJson.class);
//                    Forms form = formsRepository.findOne(formJson.getId());
//                    if(form.getTypePatientProcess().getId() != ppId){
//                        return true;
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return false;
        });
        return result;
    }

    @Override
    public void assignForms(CollectedBody collectedBody) {
        collectedBody.getIds().stream().forEach(
            formId -> {

                Evaluation evaluation = new Evaluation();
                EvaluationTemplate template = evaluationTemplateRepository.findOne(formId);
                evaluation.setChartId(collectedBody.getChartId());
                evaluation.setName(template.getName());
                evaluation.setBillable(template.isBillable());
                evaluation.setEnabled(template.isEnabled());
                evaluation.setEvaluationContent(template.getEvaluationContent());
                evaluation.setEvaluationTemplateId(template.getId());
                evaluation.setTypeEvaluation(template.getTypeEvaluation());
                evaluation.setTypePatientProcess(template.getTypePatientProcess());

                evaluation.setJsonTemplate(new String(Base64.getEncoder().encode(template.getJsonTemplate().getBytes(StandardCharsets.UTF_8))));
                evaluation.setJsonData(new String(Base64.getEncoder().encode("{}".getBytes(StandardCharsets.UTF_8))));

                evaluation.setOnlyOne(template.isOnlyOne());
                evaluation.setStatus(EvaluationStatus.Pending);
                evaluation.setPatientSignature(template.isPatientSignature());

                template.getStaffSignatureAuthority().size();
                template.getStaffReviewSignatureAuthorities().size();

                String staffSignatureAuthority = "";
                for (Authority ssa : template.getStaffSignatureAuthority()) {
                    if (staffSignatureAuthority.equals("")) {
                        staffSignatureAuthority = staffSignatureAuthority + ssa.getName();
                    } else {
                        staffSignatureAuthority = staffSignatureAuthority + ", " + ssa.getName();
                    }
                }

                evaluation.setRolesSign(staffSignatureAuthority);


                String reviewSignatureAuthority = "";
                for (Authority sra : template.getStaffReviewSignatureAuthorities()) {
                    if (reviewSignatureAuthority.equals("")) {
                        reviewSignatureAuthority = reviewSignatureAuthority + sra.getName();
                    } else {
                        reviewSignatureAuthority = reviewSignatureAuthority + ", " + sra.getName();
                    }
                }

                evaluation.setRolesReview(reviewSignatureAuthority);

                evaluationRepository.save(evaluation);
            }
        );
    }


    @Override
    public File attachEvaluationFile(FileDTO fileDTO) {
        return fileService.attachFile(fileDTO);
    }

}
