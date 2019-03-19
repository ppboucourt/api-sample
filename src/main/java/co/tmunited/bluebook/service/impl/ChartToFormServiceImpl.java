package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.ChartToForm;
import co.tmunited.bluebook.domain.File;
import co.tmunited.bluebook.domain.Forms;
import co.tmunited.bluebook.domain.enumeration.FormStatus;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.util.FormJson;
import co.tmunited.bluebook.domain.vo.ChartToFormVO;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.repository.ChartToFormRepository;
import co.tmunited.bluebook.repository.FormsRepository;
import co.tmunited.bluebook.repository.search.ChartToFormSearchRepository;
import co.tmunited.bluebook.service.ChartToFormService;
import co.tmunited.bluebook.service.FileService;
import co.tmunited.bluebook.service.dto.FileDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing ChartToForm.
 */
@Service
@Transactional
public class ChartToFormServiceImpl implements ChartToFormService {

    private final Logger log = LoggerFactory.getLogger(ChartToFormServiceImpl.class);

    @Inject
    private ChartToFormRepository chartToFormRepository;

    @Inject
    private ChartToFormSearchRepository chartToFormSearchRepository;

    @Inject
    private FormsRepository formsRepository;

    @Inject
    private FileService fileService;


    /**
     * Save a chartToForm.
     *
     * @param chartToForm the entity to save
     * @return the persisted entity
     */
    public ChartToForm save(ChartToForm chartToForm) {
        log.debug("Request to save ChartToForm : {}", chartToForm);
        ChartToForm result = chartToFormRepository.save(chartToForm);
        chartToFormSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the chartToForms.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToForm> findAll() {
        log.debug("Request to get all ChartToForms");
        List<ChartToForm> result = chartToFormRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one chartToForm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ChartToForm findOne(Long id) {
        log.debug("Request to get ChartToForm : {}", id);
        ChartToForm chartToForm = chartToFormRepository.findOne(id);
        return chartToForm;
    }

    /**
     * Delete the  chartToForm by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChartToForm : {}", id);
        ChartToForm chartToForm = chartToFormRepository.findOne(id);
        chartToForm.setDelStatus(true);
        ChartToForm result = chartToFormRepository.save(chartToForm);

        chartToFormSearchRepository.save(result);
    }

    /**
     * Search for the chartToForm corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartToForm> search(String query) {
        log.debug("Request to search ChartToForms for query {}", query);
        return StreamSupport
            .stream(chartToFormSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    public List<ChartToForm> findAllByChart(Long id) {
        List<ChartToForm> result = chartToFormRepository.findAllByDelStatusIsFalseAndChartIdOrderByIdAsc(id);
        return result;
    }

    public List<FormVO> findAllPatientProcessInJsonForm(Long chId, Long ppId) {
        List<ChartToForm> result = chartToFormRepository.findAllByDelStatusIsFalseAndChartIdOrderByIdAsc(chId);
        List<FormVO> forms = new ArrayList<>();
//        ObjectMapper mapper = new ObjectMapper();
        result.removeIf(chToForm -> {
            try {//No borrar hasta realizar el cambio de poner los fields de formJson en ChartToForm table
                if (chToForm.getTypePatientProcessId() == null || !chToForm.getTypePatientProcessId().equals(ppId)) {
                    return true;
                } else
                if(chToForm != null) {
                    forms.add(chToForm.chartFormToFormVO(chToForm));
                }
            } catch (Exception e) {
                log.info(e.toString());
                log.debug(e.toString());
                e.printStackTrace();
            }
            return false;
        });

        return forms;
    }

    @Override
    public void assignForms(CollectedBody collectedBody) {
        ObjectMapper mapper = new ObjectMapper();
        collectedBody.getIds().stream().forEach(
            formId -> {

                Forms form = formsRepository.findOne(formId);
//                FormJson formJson = prepareFormToJson(form);

                ChartToForm chartToForm = prepareChartToForm(form);

                chartToForm.setChartId(collectedBody.getChartId());

                if(chartToForm.getGuarantorSignatureRequired() || chartToForm.getPatientSignatureRequired()) {
                    chartToForm.setStatus( FormStatus.Pending );
                }
                else {
                    chartToForm.setStatus( FormStatus.Finalized );
                }

                chartToFormRepository.save( chartToForm );
            }
        );
    }

    private ChartToForm prepareChartToForm(Forms form) {

        ChartToForm chartToForm = new ChartToForm();
        chartToForm.setName(form.getName());
        chartToForm.setFormId(form.getId());
        chartToForm.setPatientSignatureRequired(form.isPatientSignatureRequired()!=null ? form.isPatientSignatureRequired(): false);
        chartToForm.setGuarantorSignatureRequired(form.isGuarantorSignatureRequired()!=null ? form.isGuarantorSignatureRequired(): false);
        chartToForm.setAllowAttachment(form.isAllowAttachment()!=null ? form.isAllowAttachment() : false);
        chartToForm.setAllowRevocation(form.isAllowRevocation() != null ? form.isAllowRevocation(): false);
        chartToForm.setExpiresDays(form.getExpiresDays() != null ? form.getExpiresDays(): 0);
        chartToForm.setExpire(form.isExpires() != null ? form.isExpires(): false);
        chartToForm.setOnlyOne(form.isOnlyOnePerpatient() != null ? form.isOnlyOnePerpatient(): false);
        chartToForm.setTypePatientProcessId(form.getTypePatientProcess().getId());
        chartToForm.setLoadAutomatic(form.isLoadAutomatic() != null ? form.isLoadAutomatic(): false);
        chartToForm.setContentHtml(form.getContentHtml());

        return chartToForm;
    }

    private FormJson prepareFormToJson(Forms form) {
//        ObjectMapper mapper = new ObjectMapper();
        FormJson formJson = new FormJson();
        try {

            formJson.setId(form.getId());
            formJson.setName(form.getName());
            formJson.setAllowAttachment(form.isAllowAttachment());
            formJson.setAllowRevocation(form.isAllowRevocation());
            formJson.setContentHtml(form.getContentHtml());
            formJson.setEnabled(form.isEnabled());
            formJson.setExpires(form.isExpires());
            formJson.setExpiresDays(form.getExpiresDays());
            formJson.setFacilityId(form.getFacility().getId());
            formJson.setFacilityName(form.getFacility().getName());
            formJson.setGuarantorSignatureRequired(form.isGuarantorSignatureRequired());
//            formJson.setLaboratoriesId(form.getLaboratories().getId());
//            formJson.setLaboratoriesName(form.getLaboratories().getName());
//            formJson.setLoadManually(form.isLoadManually());
            formJson.setOnlyOnePerpatient(form.isOnlyOnePerpatient());
            formJson.setPatientSignatureRequired(form.isPatientSignatureRequired());
//            formJson.setRequiredLabRequisitions(form.isRequiredLabRequisitions());
//            formJson.setTypeFormRulesId(form.getTypeFormRules().getId());
//            formJson.setTypeFormRulesName(form.getTypeFormRules().getName());
            formJson.setTypePatientProcessId(form.getTypePatientProcess().getId());
            formJson.setTypePatientProcessName(form.getTypePatientProcess().getName());
            formJson.setLoadAutomatic(form.isLoadAutomatic());

//            json = mapper.writeValueAsString(formJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formJson;
    }

    @Override
    public File attachConsentFile(FileDTO fileDTO) {
        return fileService.attachFile(fileDTO);
    }

    public ChartToFormVO findOneVO(Long id) {
        ChartToFormVO chartToFormVO = chartToFormRepository.findOneVO(id);
        return chartToFormVO;
    }

    public ChartToFormVO findOneVOChart(Long id) {
        ChartToFormVO chartToFormVO = chartToFormRepository.findOneVOChart(id);
        return chartToFormVO;
    }

    public void migrateJsonToFields() {
        List<ChartToForm> chartToForms = chartToFormRepository.findAll();
        try {
            ObjectMapper mapper = new ObjectMapper();
            chartToForms.stream().map(chartForm -> {
                if (!chartForm.getMigrated() && chartForm.getFormId() == null) {
                    FormJson formJson = null;
                    try {
                        formJson = mapper.readValue(chartForm.getJsonData(), FormJson.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.info(e.getMessage());
                    }
                    chartForm.setPatientSignatureRequired(formJson.getPatientSignatureRequired() != null ? formJson.getPatientSignatureRequired() : false);
                    chartForm.setGuarantorSignatureRequired(formJson.getGuarantorSignatureRequired() != null ? formJson.getGuarantorSignatureRequired() : false);
                    chartForm.setAllowAttachment(formJson.getAllowAttachment() != null ? formJson.getAllowAttachment() : false);
                    chartForm.setAllowRevocation(formJson.getAllowRevocation() != null ? formJson.getAllowRevocation() : false);
                    chartForm.setExpiresDays(formJson.getExpiresDays() != null ? formJson.getExpiresDays() : 0);
                    chartForm.setExpire(formJson.getExpires() != null ? formJson.getExpires() : false);
                    chartForm.setOnlyOne(formJson.getOnlyOnePerpatient() != null ? formJson.getOnlyOnePerpatient() : false);
                    chartForm.setTypePatientProcessId(formJson.getTypePatientProcessId());
                    chartForm.setLoadAutomatic(formJson.getLoadAutomatic() != null ? formJson.getLoadAutomatic() : false);
                    chartForm.setContentHtml(formJson.getContent());
                    chartForm.setFormId(formJson.getId());

                    chartForm.setMigrated(true);

                    chartToFormRepository.save(chartForm);
                    chartToFormSearchRepository.save(chartForm);
                }


                return chartForm;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }

}
