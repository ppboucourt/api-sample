package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.Forms;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.domain.vo.ResponseFormParsed;
import co.tmunited.bluebook.service.dto.FileDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Service Interface for managing Forms.
 */
public interface FormsService {

    /**
     * Save a forms.
     *
     * @param forms the entity to save
     * @return the persisted entity
     */
    Forms save(Forms forms);

    /**
     *  Get all the forms.
     *
     *  @return the list of entities
     */
    List<Forms> findAll();

    /**
     *  Get the "id" forms.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Forms findOne(Long id);

    /**
     *  Delete the "id" forms.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the forms corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Forms> search(String query);

    /**
     *  Get all the forms filtered by facilityId.
     *
     *  @return the list of entities filtered by facility
     */
    List<Forms> findAllByFacilityId(Long id);

    /**
     * Method for parse the forms assigned to a patient and
     * bring the data filled with the patient's data.
     *
     * @param chartId
     * @param formId
     * @return
     */
    ResponseEntity<ResponseFormParsed> parserForm(Long chartId, Long formId);

    List<FormVO> getFormsByPatientProcessAndFacility(Long ppId, Long facId);

    List<FormVO> findAllByPatientProcessFacilityAndLevelCare(Long ppId, Long facId, Long lcId);

}
