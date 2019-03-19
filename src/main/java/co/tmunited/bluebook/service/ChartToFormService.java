package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ChartToForm;
import co.tmunited.bluebook.domain.File;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.ChartToFormVO;
import co.tmunited.bluebook.domain.vo.FormVO;
import co.tmunited.bluebook.service.dto.FileDTO;

import java.util.List;

/**
 * Service Interface for managing ChartToForm.
 */
public interface ChartToFormService {

    /**
     * Save a chartToForm.
     *
     * @param chartToForm the entity to save
     * @return the persisted entity
     */
    ChartToForm save(ChartToForm chartToForm);

    /**
     *  Get all the chartToForms.
     *
     *  @return the list of entities
     */
    List<ChartToForm> findAll();

    /**
     *  Get the "id" chartToForm.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChartToForm findOne(Long id);

    /**
     *  Delete the "id" chartToForm.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chartToForm corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<ChartToForm> search(String query);

    List<ChartToForm> findAllByChart(Long id);

    List<FormVO> findAllPatientProcessInJsonForm(Long chId, Long ppId);

    void assignForms(CollectedBody collectedBody);

    File attachConsentFile(FileDTO file);

    ChartToFormVO findOneVO(Long id);

    ChartToFormVO findOneVOChart(Long id);

    /**
     * Migrate the informationstored into json field to the current fields.
     */
    void migrateJsonToFields();

}
