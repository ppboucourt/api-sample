package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.vo.FaxSendLogVO;
import co.tmunited.bluebook.web.rest.util.BooleanWrapper;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.FaxSendLog;
import co.tmunited.bluebook.service.FaxSendLogService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import co.tmunited.bluebook.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing FaxSendLog.
 */
@RestController
@RequestMapping("/api")
public class FaxSendLogResource {

    private final Logger log = LoggerFactory.getLogger(FaxSendLogResource.class);

    @Inject
    private FaxSendLogService faxSendLogService;

    /**
     * POST  /fax-send-logs : Create a new faxSendLog.
     *
     * @param faxSendLog the faxSendLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faxSendLog, or with status 400 (Bad Request) if the faxSendLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fax-send-logs")
    @Timed
    public ResponseEntity<FaxSendLog> createFaxSendLog(@RequestBody FaxSendLog faxSendLog) throws URISyntaxException {
        log.debug("REST request to save FaxSendLog : {}", faxSendLog);
        if (faxSendLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("faxSendLog", "idexists", "A new faxSendLog cannot already have an ID")).body(null);
        }
        FaxSendLog result = faxSendLogService.save(faxSendLog);
        return ResponseEntity.created(new URI("/api/fax-send-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("faxSendLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fax-send-logs : Updates an existing faxSendLog.
     *
     * @param faxSendLog the faxSendLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faxSendLog,
     * or with status 400 (Bad Request) if the faxSendLog is not valid,
     * or with status 500 (Internal Server Error) if the faxSendLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fax-send-logs")
    @Timed
    public ResponseEntity<FaxSendLog> updateFaxSendLog(@RequestBody FaxSendLog faxSendLog) throws URISyntaxException {
        log.debug("REST request to update FaxSendLog : {}", faxSendLog);
        if (faxSendLog.getId() == null) {
            return createFaxSendLog(faxSendLog);
        }
        FaxSendLog result = faxSendLogService.save(faxSendLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("faxSendLog", faxSendLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fax-send-logs : get all the faxSendLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of faxSendLogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/fax-send-logs-vo/{fId}")
    @Timed
    public ResponseEntity<List<FaxSendLogVO>> getAllFaxSendLogs(@PathVariable Long fId)
        throws URISyntaxException {
        log.debug("REST request to get a page of FaxSendLogs: " + fId);
        List<FaxSendLogVO> pages = faxSendLogService.findAllByFacilityVO(fId);
        return Optional.ofNullable(pages)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /fax-send-logs/:id : get the "id" faxSendLog.
     *
     * @param id the id of the faxSendLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faxSendLog, or with status 404 (Not Found)
     */
    @GetMapping("/fax-send-logs/{id}")
    @Timed
    public ResponseEntity<FaxSendLog> getFaxSendLog(@PathVariable Long id) {
        log.debug("REST request to get FaxSendLog : {}", id);
        FaxSendLog faxSendLog = faxSendLogService.findOne(id);
        return Optional.ofNullable(faxSendLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fax-send-logs/:id : delete the "id" faxSendLog.
     *
     * @param id the id of the faxSendLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fax-send-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteFaxSendLog(@PathVariable Long id) {
        log.debug("REST request to delete FaxSendLog : {}", id);
        faxSendLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("faxSendLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/fax-send-logs?query=:query : search for the faxSendLog corresponding
     * to the query.
     *
     * @param query the query of the faxSendLog search
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/fax-send-logs")
    @Timed
    public ResponseEntity<List<FaxSendLog>> searchFaxSendLogs(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of FaxSendLogs for query {}", query);
        Page<FaxSendLog> page = faxSendLogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fax-send-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/fax-send-logs-update")
    @Timed
    public ResponseEntity<BooleanWrapper> faxSendLogsUpdate() {
        Boolean updated = faxSendLogService.updateFaxState();
        BooleanWrapper booleanWrapper = new BooleanWrapper(updated);

        return Optional.ofNullable(booleanWrapper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


//    @RequestMapping("/fax-pdf-download/{id}/{token}")
//    public void downloadPDFResource(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    @PathVariable("id") Long id, @PathVariable("token") String token) throws IOException {
//
//        String fileStore = faxSendLogService.downloadFaxPdf(id);
//        File tempFile = File.createTempFile("fax", ".pdf");
//        FileOutputStream fos = new FileOutputStream(tempFile);
//
//        fos.write(fileStore.getBytes());
//        fos.close();
//
//        response.setContentType("application/pdf");
//        Files.copy(tempFile.toPath(), response.getOutputStream());
//        response.getOutputStream().flush();
//    }

}
