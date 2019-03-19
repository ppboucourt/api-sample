package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.ContactsFacility;
import co.tmunited.bluebook.service.ContactsFacilityService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ContactsFacility.
 */
@RestController
@RequestMapping("/api")
public class ContactsFacilityResource {

    private final Logger log = LoggerFactory.getLogger(ContactsFacilityResource.class);

    @Inject
    private ContactsFacilityService contactsFacilityService;

    /**
     * POST  /contacts-facilities : Create a new contactsFacility.
     *
     * @param contactsFacility the contactsFacility to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactsFacility, or with status 400 (Bad Request) if the contactsFacility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts-facilities")
    @Timed
    public ResponseEntity<ContactsFacility> createContactsFacility(@Valid @RequestBody ContactsFacility contactsFacility) throws URISyntaxException {
        log.debug("REST request to save ContactsFacility : {}", contactsFacility);
        if (contactsFacility.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contactsFacility", "idexists", "A new contactsFacility cannot already have an ID")).body(null);
        }
        ContactsFacility result = contactsFacilityService.save(contactsFacility);
        return ResponseEntity.created(new URI("/api/contacts-facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contactsFacility", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacts-facilities : Updates an existing contactsFacility.
     *
     * @param contactsFacility the contactsFacility to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactsFacility,
     * or with status 400 (Bad Request) if the contactsFacility is not valid,
     * or with status 500 (Internal Server Error) if the contactsFacility couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contacts-facilities")
    @Timed
    public ResponseEntity<ContactsFacility> updateContactsFacility(@Valid @RequestBody ContactsFacility contactsFacility) throws URISyntaxException {
        log.debug("REST request to update ContactsFacility : {}", contactsFacility);
        if (contactsFacility.getId() == null) {
            return createContactsFacility(contactsFacility);
        }
        ContactsFacility result = contactsFacilityService.save(contactsFacility);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contactsFacility", contactsFacility.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacts-facilities : get all the contactsFacilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactsFacilities in body
     */
    @GetMapping("/contacts-facilities")
    @Timed
    public List<ContactsFacility> getAllContactsFacilities() {
        log.debug("REST request to get all ContactsFacilities");
        return contactsFacilityService.findAll();
    }

    /**
     * GET  /contacts-facilities/:id : get the "id" contactsFacility.
     *
     * @param id the id of the contactsFacility to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactsFacility, or with status 404 (Not Found)
     */
    @GetMapping("/contacts-facilities/{id}")
    @Timed
    public ResponseEntity<ContactsFacility> getContactsFacility(@PathVariable Long id) {
        log.debug("REST request to get ContactsFacility : {}", id);
        ContactsFacility contactsFacility = contactsFacilityService.findOne(id);
        return Optional.ofNullable(contactsFacility)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contacts-facilities/:id : delete the "id" contactsFacility.
     *
     * @param id the id of the contactsFacility to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contacts-facilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactsFacility(@PathVariable Long id) {
        log.debug("REST request to delete ContactsFacility : {}", id);
        contactsFacilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contactsFacility", id.toString())).build();
    }

    /**
     * SEARCH  /_search/contacts-facilities?query=:query : search for the contactsFacility corresponding
     * to the query.
     *
     * @param query the query of the contactsFacility search
     * @return the result of the search
     */
    @GetMapping("/_search/contacts-facilities")
    @Timed
    public List<ContactsFacility> searchContactsFacilities(@RequestParam String query) {
        log.debug("REST request to search ContactsFacilities for query {}", query);
        return contactsFacilityService.search(query);
    }

    /**
     * GET  /contacts-facilities : get all the contactsFacilities by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactsFacilities in body
     */
    @GetMapping("/contacts-by-facility/{id}")
    @Timed
    public List<ContactsFacility> getAllContactsFacilitiesByFacility(@PathVariable Long id) {
        log.debug("REST request to get all ContactsFacilities By Facility");
        return contactsFacilityService.findAllByDelStatusIsFalseAndFacilityId(id);
    }


}
