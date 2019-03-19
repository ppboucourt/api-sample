package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.config.JHipsterProperties;
import co.tmunited.bluebook.service.dto.FileDTO;
import co.tmunited.bluebook.web.rest.client.FileRestClient;
import co.tmunited.tools.rest.exception.NotAuthenticatedException;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.File;
import co.tmunited.bluebook.service.FileService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing File.
 */
@RestController
@RequestMapping("/api")
public class FileResource {

    private final Logger log = LoggerFactory.getLogger(FileResource.class);

    @Inject
    private FileService fileService;

    @Inject
    private JHipsterProperties jHipsterProperties;

    /**
     * POST  /files : Create a new file.
     *
     * @param file the file to create
     * @return the ResponseEntity with status 201 (Created) and with body the new file, or with status 400 (Bad Request) if the file has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/files")
    @Timed
    public ResponseEntity<File> createFile(@RequestBody File file) throws URISyntaxException {
        log.debug("REST request to save File : {}", file);
        if (file.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("file", "idexists", "A new file cannot already have an ID")).body(null);
        }
        File result = fileService.save(file);
        return ResponseEntity.created(new URI("/api/files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("file", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /files : Updates an existing file.
     *
     * @param file the file to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated file,
     * or with status 400 (Bad Request) if the file is not valid,
     * or with status 500 (Internal Server Error) if the file couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/files")
    @Timed
    public ResponseEntity<File> updateFile(@RequestBody File file) throws URISyntaxException {
        log.debug("REST request to update File : {}", file);
        if (file.getId() == null) {
            return createFile(file);
        }
        File result = fileService.save(file);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("file", file.getId().toString()))
            .body(result);
    }

    /**
     * GET  /files : get all the files.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of files in body
     */
    @GetMapping("/files")
    @Timed
    public List<File> getAllFiles() {
        log.debug("REST request to get all Files");
        return fileService.findAll();
    }

    /**
     * GET  /files/:id : get the "id" file.
     *
     * @param id the id of the file to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the file, or with status 404 (Not Found)
     */
    @GetMapping("/files/{id}")
    @Timed
    public ResponseEntity<File> getFile(@PathVariable Long id) {
        log.debug("REST request to get File : {}", id);
        File file = fileService.findOne(id);
        return Optional.ofNullable(file)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /files/:id : delete the "id" file.
     *
     * @param id the id of the file to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/files/{id}")
    @Timed
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        log.debug("REST request to delete File : {}", id);
        fileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("file", id.toString())).build();
    }

    /**
     * SEARCH  /_search/files?query=:query : search for the file corresponding
     * to the query.
     *
     * @param query the query of the file search
     * @return the result of the search
     */
    @GetMapping("/_search/files")
    @Timed
    public List<File> searchFiles(@RequestParam String query) {
        log.debug("REST request to search Files for query {}", query);
        return fileService.search(query);
    }

    /**
     * SEARCH  all files by owner(consent, evaluation, etc...)
     *
     * @param id the of the owner search
     * @return the result of the search
     */
    @GetMapping("/files/owner/{id}")
    @Timed
    public List<File> findByOwner(@PathVariable Long id) {
        log.debug("REST request to search Files for query {}", id);
        List<File> list = fileService.findAllByForm(id);
        return list;
    }

    @RequestMapping(path = "/file/system/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException, RuntimeException {
        ResponseEntity<Resource> result = null;
        try {

            File file = fileService.findOne(id);

            result = FileRestClient.getInstance().getDownloadFileEntityResponse(
                jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                    jHipsterProperties.getFileStorage().getRemote().getUrlGetFile() + '/' + file.getUuid());
        } catch (RuntimeException e) {

        }
        return result;
    }


    @RequestMapping(path = "/file/dto/{id}", method = RequestMethod.GET)
    public FileDTO getFileById(@PathVariable Long id) throws IOException, RuntimeException {
        FileDTO result = null;
        try {

            File file = fileService.findOne(id);

            result = FileRestClient.getInstance().getFile(new URL(jHipsterProperties.getFileStorage().getRemote().getBasePath() +
                jHipsterProperties.getFileStorage().getRemote().getUrlGetFile() + '/' + file.getUuid()));
        } catch (RuntimeException e) {

        } catch (NotAuthenticatedException e) {
            e.printStackTrace();
        }
        return result;
    }

}
