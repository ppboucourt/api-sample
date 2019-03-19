package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.service.FileService;
import co.tmunited.bluebook.domain.File;
import co.tmunited.bluebook.repository.FileRepository;
import co.tmunited.bluebook.repository.search.FileSearchRepository;
import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.bluebook.service.util.StorageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing File.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Inject
    private FileRepository fileRepository;

    @Inject
    private FileSearchRepository fileSearchRepository;

    @Inject
    private JHipsterProperties jHipsterProperties;

    /**
     * Save a file.
     *
     * @param file the entity to save
     * @return the persisted entity
     */
    public File save(File file) {
        log.debug("Request to save File : {}", file);
        File result = fileRepository.save(file);
        fileSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the files.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<File> findAll() {
        log.debug("Request to get all Files");
        List<File> result = fileRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one file by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public File findOne(Long id) {
        log.debug("Request to get File : {}", id);
        File file = fileRepository.findOne(id);
        return file;
    }

    /**
     * Delete the  file by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete File : {}", id);
        File file = fileRepository.findOne(id);
        file.setDelStatus(true);
        File result = fileRepository.save(file);

        fileSearchRepository.save(result);
    }

    /**
     * Search for the file corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<File> search(String query) {
        log.debug("Request to search Files for query {}", query);
        return StreamSupport
            .stream(fileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Get all the files by formId.
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public List<File> findAllByForm(Long id) {
        log.debug("Request to get all Files");
        return fileRepository.findAllByDelStatusIsFalseAndOwnerId(id);
    }


    @Override
    public File attachFile(FileDTO fileDTO) {
        try {
            String uuid = UUID.randomUUID().toString();

            File file = new File();
            file.setUuid(uuid);
            file.setOwnerId(Long.valueOf(fileDTO.getId()));
            file.setName(fileDTO.getName());
            file.setType(fileDTO.getFileContentType());
            file.setSize(fileDTO.getSize());
            fileDTO.setId(null);
            fileDTO.setUuid(uuid);

            try {
                switch (jHipsterProperties.getFileStorage().getType()) {
                    case "fileSystem":
                        if (!StorageUtil.storeFileToLocal(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                            jHipsterProperties.getFileStorage().getFileSystem().getProcessedResult() +
                            StorageUtil.generateFolderName(), fileDTO)) {
                            //Can not upload file to FS
                            log.error("Failed to upload file to local FileSystem.");
                        }
                        break;
                    case "remote":
                        if (!StorageUtil.storeFileToServer(new URL(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                            jHipsterProperties.getFileStorage().getRemote().getUrlFiles()), fileDTO)) {
                            //Can not upload file to FS
                            log.error("Failed to upload file to FS.");
                        }
                        break;
                    default:
                        if (!StorageUtil.storeFileToLocal(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                            jHipsterProperties.getFileStorage().getFileSystem().getProcessedResult() +
                            StorageUtil.generateFolderName(), fileDTO)) {
                            //Can not upload file to FS
                            log.error("Failed to upload file to local FileSystem.");
                        }
                        break;
                }
                fileRepository.save(file);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
