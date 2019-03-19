package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.PatientResultFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

/**
 * Service Interface for managing PatientResultFile.
 */
public interface PatientResultFileService {

    /**
     * Save a patientResultFile.
     *
     * @param patientResultFile the entity to save
     * @return the persisted entity
     */
    PatientResultFile save(PatientResultFile patientResultFile);

    /**
     *  Get all the patientResultFiles.
     *
     *  @return the list of entities
     */
    List<PatientResultFile> findAll();

    /**
     *  Get the "id" patientResultFile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PatientResultFile findOne(Long id);

    /**
     *  Delete the "id" patientResultFile.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the patientResultFile corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<PatientResultFile> search(String query);

    /**
     *  Get the "id" patientResultFile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ResponseEntity<Resource> findLastByPatientResultId(Long id) throws IOException;

    List<PatientResultFile> findAllByPatientResultId(Long id);

    ResponseEntity<Resource> download(Long id) throws IOException;
}
