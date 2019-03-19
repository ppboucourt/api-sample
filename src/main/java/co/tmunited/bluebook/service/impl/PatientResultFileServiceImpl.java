package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.domain.PatientResultFile;
import co.tmunited.bluebook.repository.PatientResultFileRepository;
import co.tmunited.bluebook.repository.search.PatientResultFileSearchRepository;
import co.tmunited.bluebook.service.PatientResultFileService;
import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.bluebook.web.rest.client.FileRestClient;
import co.tmunited.tools.rest.exception.NotAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing PatientResultFile.
 */
@Service
@Transactional
public class PatientResultFileServiceImpl implements PatientResultFileService {

    private final Logger log = LoggerFactory.getLogger(PatientResultFileServiceImpl.class);

    @Inject
    private PatientResultFileRepository patientResultFileRepository;

    @Inject
    private PatientResultFileSearchRepository patientResultFileSearchRepository;

    @Inject
    private JHipsterProperties jHipsterProperties;

    /**
     * Save a patientResultFile.
     *
     * @param patientResultFile the entity to save
     * @return the persisted entity
     */
    public PatientResultFile save(PatientResultFile patientResultFile) {
        log.debug("Request to save PatientResultFile : {}", patientResultFile);
        PatientResultFile result = patientResultFileRepository.save(patientResultFile);
        patientResultFileSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the patientResultFiles.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientResultFile> findAll() {
        log.debug("Request to get all PatientResultFiles");
        List<PatientResultFile> result = patientResultFileRepository.findAll();

        return result;
    }

    /**
     * Get one patientResultFile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PatientResultFile findOne(Long id) {
        log.debug("Request to get PatientResultFile : {}", id);
        PatientResultFile patientResultFile = patientResultFileRepository.findOne(id);
        return patientResultFile;
    }

    /**
     * Delete the  patientResultFile by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientResultFile : {}", id);
        patientResultFileRepository.delete(id);
        patientResultFileSearchRepository.delete(id);
    }

    /**
     * Search for the patientResultFile corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientResultFile> search(String query) {
        log.debug("Request to search PatientResultFiles for query {}", query);
        return StreamSupport
            .stream(patientResultFileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @Override
    public ResponseEntity<Resource> findLastByPatientResultId(Long id) throws IOException {
        log.debug("Calling findLastByPatientResultId");
        PatientResultFile patientResultFile = patientResultFileRepository.findLastByPatientResultId(id);
        log.debug("Calling findLastByPatientResultId End");

        File tmp = null;
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Resource> responseEntity = null;
        if (patientResultFile != null) {

            try {
                tmp = createTmpFile(patientResultFile.getUuid());
            } catch (Exception e) {
                //Send to client a error pdf file.
                tmp = createTmpErrorFile();
                e.printStackTrace();
            }

            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(tmp.toPath()));

            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "file_result.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            headers.setContentLength(resource.getByteArray().length);

            responseEntity = ResponseEntity.ok().headers(headers).body(resource);
        } else {
            headers.setContentType(MediaType.APPLICATION_JSON);
            responseEntity = ResponseEntity.ok().headers(headers).body(null);
        }

        return responseEntity;

    }

    @Override
    public List<PatientResultFile> findAllByPatientResultId(Long id) {
        return patientResultFileRepository.findAllByPatientResultIdOrderByCreatedDateDesc(id);
    }

    private File createTmpFile(String uuid) throws IOException, NotAuthenticatedException {

        File tmp = null;
        tmp = File.createTempFile("temp_", ".pdf");
        tmp.deleteOnExit();

        FileDTO fileDTO = FileRestClient.getInstance().getFile(new URL(
            jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                jHipsterProperties.getFileStorage().getRemote().getUrlGetFile() + '/' + uuid));

        FileOutputStream fout = new FileOutputStream(tmp);
        fout.write(Base64.getDecoder().decode(fileDTO.getFile().getBytes()));
        fout.flush();
        fout.close();
        return tmp;
    }

    private File createTmpErrorFile() {

        File tmp = null;
        try {
            tmp = File.createTempFile("pdf-template", ".pdf");
            tmp.deleteOnExit();
            InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("report/pdfNotFound.pdf");
            Files.copy(inputStream, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    @Override
    public ResponseEntity<Resource> download(Long id) throws IOException {
        PatientResultFile patientResultFile = patientResultFileRepository.findOne(id);

        File tmp = null;
        try {
            tmp = FileRestClient.getInstance().downloadFile(jHipsterProperties, patientResultFile.getUuid());
        } catch (Exception e) {
            //Send to client a error pdf file.
            tmp = createTmpErrorFile();
            log.info(e.toString());
            e.printStackTrace();
        }

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(tmp.toPath()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "file_result.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        headers.setContentLength(resource.getByteArray().length);

        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
