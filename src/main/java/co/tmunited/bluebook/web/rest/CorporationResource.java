package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Corporation;
import co.tmunited.bluebook.service.CorporationService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Corporation.
 */
@RestController
@RequestMapping("/api")
public class CorporationResource {

    private final Logger log = LoggerFactory.getLogger(CorporationResource.class);
        
    @Inject
    private CorporationService corporationService;

    /**
     * POST  /corporations : Create a new corporation.
     *
     * @param corporation the corporation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corporation, or with status 400 (Bad Request) if the corporation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/corporations")
    @Timed
    public ResponseEntity<Corporation> createCorporation(@RequestBody Corporation corporation) throws URISyntaxException {
        log.debug("REST request to save Corporation : {}", corporation);
        if (corporation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("corporation", "idexists", "A new corporation cannot already have an ID")).body(null);
        }
        Corporation result = corporationService.save(corporation);
        return ResponseEntity.created(new URI("/api/corporations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("corporation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /corporations : Updates an existing corporation.
     *
     * @param corporation the corporation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corporation,
     * or with status 400 (Bad Request) if the corporation is not valid,
     * or with status 500 (Internal Server Error) if the corporation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/corporations")
    @Timed
    public ResponseEntity<Corporation> updateCorporation(@RequestBody Corporation corporation) throws URISyntaxException {
        log.debug("REST request to update Corporation : {}", corporation);
        if (corporation.getId() == null) {
            return createCorporation(corporation);
        }
        Corporation result = corporationService.save(corporation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("corporation", corporation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /corporations : get all the corporations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of corporations in body
     */
    @GetMapping("/corporations")
    @Timed
    public List<Corporation> getAllCorporations() {
        log.debug("REST request to get all Corporations");
        return corporationService.findAll();
    }

    /**
     * GET  /corporations/:id : get the "id" corporation.
     *
     * @param id the id of the corporation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corporation, or with status 404 (Not Found)
     */
    @GetMapping("/corporations/{id}")
    @Timed
    public ResponseEntity<Corporation> getCorporation(@PathVariable Long id) {
        log.debug("REST request to get Corporation : {}", id);
        Corporation corporation = corporationService.findOne(id);
        return Optional.ofNullable(corporation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /corporations/:id : delete the "id" corporation.
     *
     * @param id the id of the corporation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/corporations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorporation(@PathVariable Long id) {
        log.debug("REST request to delete Corporation : {}", id);
        corporationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("corporation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/corporations?query=:query : search for the corporation corresponding
     * to the query.
     *
     * @param query the query of the corporation search 
     * @return the result of the search
     */
    @GetMapping("/_search/corporations")
    @Timed
    public List<Corporation> searchCorporations(@RequestParam String query) {
        log.debug("REST request to search Corporations for query {}", query);
        return corporationService.search(query);
    }


}
