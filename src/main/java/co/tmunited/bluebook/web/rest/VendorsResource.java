package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Vendors;
import co.tmunited.bluebook.service.VendorsService;
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
 * REST controller for managing Vendors.
 */
@RestController
@RequestMapping("/api")
public class VendorsResource {

    private final Logger log = LoggerFactory.getLogger(VendorsResource.class);
        
    @Inject
    private VendorsService vendorsService;

    /**
     * POST  /vendors : Create a new vendors.
     *
     * @param vendors the vendors to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vendors, or with status 400 (Bad Request) if the vendors has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vendors")
    @Timed
    public ResponseEntity<Vendors> createVendors(@Valid @RequestBody Vendors vendors) throws URISyntaxException {
        log.debug("REST request to save Vendors : {}", vendors);
        if (vendors.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vendors", "idexists", "A new vendors cannot already have an ID")).body(null);
        }
        Vendors result = vendorsService.save(vendors);
        return ResponseEntity.created(new URI("/api/vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vendors", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vendors : Updates an existing vendors.
     *
     * @param vendors the vendors to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vendors,
     * or with status 400 (Bad Request) if the vendors is not valid,
     * or with status 500 (Internal Server Error) if the vendors couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vendors")
    @Timed
    public ResponseEntity<Vendors> updateVendors(@Valid @RequestBody Vendors vendors) throws URISyntaxException {
        log.debug("REST request to update Vendors : {}", vendors);
        if (vendors.getId() == null) {
            return createVendors(vendors);
        }
        Vendors result = vendorsService.save(vendors);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vendors", vendors.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vendors : get all the vendors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vendors in body
     */
    @GetMapping("/vendors")
    @Timed
    public List<Vendors> getAllVendors() {
        log.debug("REST request to get all Vendors");
        return vendorsService.findAll();
    }

    /**
     * GET  /vendors/:id : get the "id" vendors.
     *
     * @param id the id of the vendors to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vendors, or with status 404 (Not Found)
     */
    @GetMapping("/vendors/{id}")
    @Timed
    public ResponseEntity<Vendors> getVendors(@PathVariable Long id) {
        log.debug("REST request to get Vendors : {}", id);
        Vendors vendors = vendorsService.findOne(id);
        return Optional.ofNullable(vendors)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vendors/:id : delete the "id" vendors.
     *
     * @param id the id of the vendors to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vendors/{id}")
    @Timed
    public ResponseEntity<Void> deleteVendors(@PathVariable Long id) {
        log.debug("REST request to delete Vendors : {}", id);
        vendorsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vendors", id.toString())).build();
    }

    /**
     * SEARCH  /_search/vendors?query=:query : search for the vendors corresponding
     * to the query.
     *
     * @param query the query of the vendors search 
     * @return the result of the search
     */
    @GetMapping("/_search/vendors")
    @Timed
    public List<Vendors> searchVendors(@RequestParam String query) {
        log.debug("REST request to search Vendors for query {}", query);
        return vendorsService.search(query);
    }


}
