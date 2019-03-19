package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.Insurance;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.domain.vo.ResponseFormParsed;
import co.tmunited.bluebook.repository.ChartRepository;
import co.tmunited.bluebook.repository.InsuranceRepository;
import co.tmunited.bluebook.repository.PatientRepository;
import co.tmunited.bluebook.service.FormsService;
import co.tmunited.bluebook.domain.Forms;
import co.tmunited.bluebook.repository.FormsRepository;
import co.tmunited.bluebook.repository.search.FormsSearchRepository;
import co.tmunited.bluebook.service.PatientService;
import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.bluebook.web.rest.PatientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Forms.
 */
@Service
@Transactional
public class FormsServiceImpl implements FormsService{

    private final Logger log = LoggerFactory.getLogger(FormsServiceImpl.class);

    @Inject
    private FormsRepository formsRepository;

    @Inject
    private FormsSearchRepository formsSearchRepository;

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private PatientService patientService;

    @Inject
    private InsuranceRepository insuranceRepository;

    /**
     * Save a forms.
     *
     * @param forms the entity to save
     * @return the persisted entity
     */
    public Forms save(Forms forms) {
        log.debug("Request to save Forms : {}", forms);
        Forms result = formsRepository.save(forms);
        formsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the forms.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Forms> findAll() {
        log.debug("Request to get all Forms");
        List<Forms> result = formsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one forms by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Forms findOne(Long id) {
        log.debug("Request to get Forms : {}", id);
        Forms forms = formsRepository.findOne(id);
        forms.getTypeLevelCares().size();
        return forms;
    }

    /**
     *  Delete the  forms by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Forms : {}", id);
        Forms forms = formsRepository.findOne(id);
        forms.setDelStatus(true);
        Forms result = formsRepository.save(forms);

        formsSearchRepository.save(result);
    }

    /**
     * Search for the forms corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Forms> search(String query) {
        log.debug("Request to search Forms for query {}", query);
        return StreamSupport
            .stream(formsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Forms> findAllByFacilityId(Long id) {
        log.debug("Request to get all Forms Belonging to a one Facility");
        List<Forms> result = formsRepository.findAllByDelStatusIsFalseAndFacilityId(id);

//        result.stream().map(form -> {
//            if (form.getTypeLevelCares() != null)
//                form.getTypeLevelCares().size();
//            return form;
//        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public ResponseEntity<ResponseFormParsed> parserForm(Long chartId, Long formId) {
        String newFormatText;

        Chart chart = chartRepository.findOneWithEagerRelationships(chartId);
        Forms form = formsRepository.findOne(formId);

        String fullName = chart.getPatient().getFirstName() + " " + chart.getPatient().getLastName();
        String patientAddress = chart.getAddress() + " " + chart.getAddressTwo() + " " + chart.getCity() + " " + chart.getZip();
        String facilityAddress = chart.getFacility().getStreet() + " " + chart.getFacility().getStreet_two() + " " + chart.getFacility().getCity() + " " +
            chart.getFacility().getState() + " " + chart.getFacility().getZip();
        Insurance insurance = insuranceRepository.findByChartIdAndInsuranceOrder(chart.getId(), 1);

        /**
         * MessageFormat. format funtion:
         * the first parameter(content) is the text it going to be parsed, the content have the numbers({1}, {2}, ...etc) into the text
         * for be replaced with the rest of the parameter in the order we put it next to the first parameter.
         * The first value next to the first parameter is the number 1(position 0) and so sucesive.
         *
         */
        newFormatText = MessageFormat.format(form.getContentHtml(), fullName, chart.getPatient().getDateBirth(), chart.getPatient().getSex(),
            patientService.getPatientAge(chart.getPatient().getDateBirth()), chart.getPatient().getSocial(), chart.getAdmissionDate(),
            patientAddress, chart.getEmail(), chart.getPhone(),
            chart.getTypePatientPrograms().getName(), chart.getBed().getName(), insurance.getInsuranceCarrier().getName(),
            insurance.getInsuranceCarrier().getPhone(), chart.getFacility().getCounty(), facilityAddress, chart.getFacility().getPhone(),
            chart.getFacility().getFax(), chart.getFacility().getName());

        String formatTextBase = Base64.getEncoder().encodeToString(newFormatText.getBytes());
        ResponseFormParsed formParsed = new ResponseFormParsed();
        formParsed.setBody(formatTextBase);

        return new ResponseEntity<>(formParsed, HttpStatus.OK);
    }

    @Override
    public List<FormVO> getFormsByPatientProcessAndFacility(Long ppId, Long facId) {
        List<Forms> result = formsRepository.findAllByDelStatusIsFalseAndEnabledIsTrueAndTypePatientProcessIdAndFacilityId(ppId, facId);List<FormVO> forms = new ArrayList<>();
        List<FormVO> formsVO = new ArrayList<>();

        result.stream().map(form -> {
            formsVO.add(form.formToFormVO(form));
            return form;
        }).collect(Collectors.toList());

        return formsVO;
    }

    /**
     *
     * @param ppId patientProcess
     * @param facId facility
     * @param lcId levelCare
     * @return
     */
    public List<FormVO> findAllByPatientProcessFacilityAndLevelCare(Long ppId, Long facId, Long lcId) {
        List<FormVO> result = formsRepository.findAllByPatientProcessFacilityAndLevelCare(ppId, facId, lcId);
        return result;
    }

}
