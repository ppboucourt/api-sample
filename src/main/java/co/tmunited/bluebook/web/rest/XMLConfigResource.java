package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.XMLConfig;
import co.tmunited.bluebook.service.XMLConfigService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing XMLConfig.
 */
@RestController
@RequestMapping("/api")
public class XMLConfigResource {

    private final Logger log = LoggerFactory.getLogger(XMLConfigResource.class);

    @Inject
    private XMLConfigService xMLConfigService;

    /**
     * POST  /x-ml-configs : Create a new xMLConfig.
     *
     * @param xMLConfig the xMLConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new xMLConfig, or with status 400 (Bad Request) if the xMLConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/x-ml-configs")
    @Timed
    public ResponseEntity<XMLConfig> createXMLConfig(@Valid @RequestBody XMLConfig xMLConfig) throws URISyntaxException {
        log.debug("REST request to save XMLConfig : {}", xMLConfig);
        if (xMLConfig.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("xMLConfig", "idexists", "A new xMLConfig cannot already have an ID")).body(null);
        }
        XMLConfig result = xMLConfigService.save(xMLConfig);
        return ResponseEntity.created(new URI("/api/x-ml-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("xMLConfig", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /x-ml-configs : Updates an existing xMLConfig.
     *
     * @param xMLConfig the xMLConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated xMLConfig,
     * or with status 400 (Bad Request) if the xMLConfig is not valid,
     * or with status 500 (Internal Server Error) if the xMLConfig couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/x-ml-configs")
    @Timed
    public ResponseEntity<XMLConfig> updateXMLConfig(@Valid @RequestBody XMLConfig xMLConfig) throws URISyntaxException {
        log.debug("REST request to update XMLConfig : {}", xMLConfig);
        if (xMLConfig.getId() == null) {
            return createXMLConfig(xMLConfig);
        }
        XMLConfig result = xMLConfigService.save(xMLConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("xMLConfig", xMLConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /x-ml-configs : get all the xMLConfigs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of xMLConfigs in body
     */
    @GetMapping("/x-ml-configs")
    @Timed
    public List<XMLConfig> getAllXMLConfigs() {
        log.debug("REST request to get all XMLConfigs");
        return xMLConfigService.findAll();
    }

    /**
     * GET  /x-ml-configs/:id : get the "id" xMLConfig.
     *
     * @param id the id of the xMLConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the xMLConfig, or with status 404 (Not Found)
     */
    @GetMapping("/x-ml-configs/{id}")
    @Timed
    public ResponseEntity<XMLConfig> getXMLConfig(@PathVariable Long id) {
        log.debug("REST request to get XMLConfig : {}", id);
        XMLConfig xMLConfig = xMLConfigService.findOne(id);
        return Optional.ofNullable(xMLConfig)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /x-ml-configs/:id : delete the "id" xMLConfig.
     *
     * @param id the id of the xMLConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/x-ml-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteXMLConfig(@PathVariable Long id) {
        log.debug("REST request to delete XMLConfig : {}", id);
        xMLConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("xMLConfig", id.toString())).build();
    }

    /**
     * SEARCH  /_search/x-ml-configs?query=:query : search for the xMLConfig corresponding
     * to the query.
     *
     * @param query the query of the xMLConfig search
     * @return the result of the search
     */
    @GetMapping("/_search/x-ml-configs")
    @Timed
    public List<XMLConfig> searchXMLConfigs(@RequestParam String query) {
        log.debug("REST request to search XMLConfigs for query {}", query);
        return xMLConfigService.search(query);
    }

    /**
     * GET  /x-ml-configs/patient/default : get the "id" xMLConfig.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the xMLConfig, or with status 404 (Not Found)
     */
    @GetMapping("/x-ml-configs/patient/default")
    @Timed
    public ResponseEntity<XMLConfig> getDefaultPatientXMLConfig() {
        log.debug("REST request to get XML Default Config : {}");
        XMLConfig xMLConfig = xMLConfigService.getDefaultPatientXMLConfig();

        return Optional.ofNullable(xMLConfig)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
