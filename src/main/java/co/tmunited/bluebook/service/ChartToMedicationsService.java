package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ChartToMedications;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing ChartToMedications.
 */
public interface ChartToMedicationsService {

    /**
     * Save a chartToMedications.
     *
     * @param chartToMedications the entity to save
     * @return the persisted entity
     */
    ChartToMedications save(ChartToMedications chartToMedications);

    /**
     *  Get all the chartToMedications.
     *
     *  @return the list of entities
     */
    List<ChartToMedications> findAll();

    /**
     *  Get the "id" chartToMedications.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChartToMedications findOne(Long id);

    /**
     *  Delete the "id" chartToMedications.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chartToMedications corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<ChartToMedications> search(String query);

    /**
     * Get all charts-medications by the charts who belong to a facility
     *
     * @param id form the current facility
     * @return List of ChartToMedications
     */
    List<ChartToMedications> findAllMedicationsByChartsBelongToFacility(Long id);

    List<ZonedDateTime> findAllMedicationsByPatientInCurrentFacilityDistinctPrescription(Long id);
}
