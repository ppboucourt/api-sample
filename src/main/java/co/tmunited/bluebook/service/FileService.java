package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.File;
import co.tmunited.bluebook.service.dto.FileDTO;

import java.util.List;

/**
 * Service Interface for managing File.
 */
public interface FileService {

    /**
     * Save a file.
     *
     * @param file the entity to save
     * @return the persisted entity
     */
    File save(File file);

    /**
     *  Get all the files.
     *
     *  @return the list of entities
     */
    List<File> findAll();

    /**
     *  Get the "id" file.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    File findOne(Long id);

    /**
     *  Delete the "id" file.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the file corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<File> search(String query);

    /**
     * Get all Files by form
     * @param id
     * @return
     */
    List<File> findAllByForm(Long id);

    /**
     *
     * @param fileDTO
     * @return
     */
    File attachFile(FileDTO fileDTO);
}
