package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Facility;
import co.tmunited.bluebook.service.FacilityService;
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
 * REST controller for managing Facility.
 */
@RestController
@RequestMapping("/api")
public class FacilityResource {

    private final Logger log = LoggerFactory.getLogger(FacilityResource.class);

    @Inject
    private FacilityService facilityService;

    /**
     * POST  /facilities : Create a new facility.
     *
     * @param facility the facility to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facility, or with status 400 (Bad Request) if the facility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facilities")
    @Timed
    public ResponseEntity<Facility> createFacility(@RequestBody Facility facility) throws URISyntaxException {
        log.debug("REST request to save Facility : {}", facility);
        if (facility.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("facility", "idexists", "A new facility cannot already have an ID")).body(null);
        }
        Facility result = facilityService.save(facility);

        facilityService.initFacility(result);

        return ResponseEntity.created(new URI("/api/facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("facility", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facilities : Updates an existing facility.
     *
     * @param facility the facility to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facility,
     * or with status 400 (Bad Request) if the facility is not valid,
     * or with status 500 (Internal Server Error) if the facility couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facilities")
    @Timed
    public ResponseEntity<Facility> updateFacility(@RequestBody Facility facility) throws URISyntaxException {
        log.debug("REST request to update Facility : {}", facility);
        if (facility.getId() == null) {
            return createFacility(facility);
        }
        Facility result = facilityService.save(facility);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("facility", facility.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facilities : get all the facilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of facilities in body
     */
    @GetMapping("/facilities")
    @Timed
    public List<Facility> getAllFacilities() {
        log.debug("REST request to get all Facilities");
        return facilityService.findAll();
    }

    /**
     * GET  /facilities/:id : get the "id" facility.
     *
     * @param id the id of the facility to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facility, or with status 404 (Not Found)
     */
    @GetMapping("/facilities/{id}")
    @Timed
    public ResponseEntity<Facility> getFacility(@PathVariable Long id) {
        log.debug("REST request to get Facility : {}", id);
        Facility facility = facilityService.findOne(id);
        return Optional.ofNullable(facility)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facilities/:id : delete the "id" facility.
     *
     * @param id the id of the facility to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        log.debug("REST request to delete Facility : {}", id);
        facilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("facility", id.toString())).build();
    }

    /**
     * SEARCH  /_search/facilities?query=:query : search for the facility corresponding
     * to the query.
     *
     * @param query the query of the facility search
     * @return the result of the search
     */
    @GetMapping("/_search/facilities")
    @Timed
    public List<Facility> searchFacilities(@RequestParam String query) {
        log.debug("REST request to search Facilities for query {}", query);
        return facilityService.search(query);
    }


}
