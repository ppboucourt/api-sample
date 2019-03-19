package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.LabProfile;
import co.tmunited.bluebook.service.LabProfileService;
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
 * REST controller for managing LabProfile.
 */
@RestController
@RequestMapping("/api")
public class LabProfileResource {

    private final Logger log = LoggerFactory.getLogger(LabProfileResource.class);

    @Inject
    private LabProfileService labProfileService;

    /**
     * POST  /lab-profiles : Create a new labProfile.
     *
     * @param labProfile the labProfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new labProfile, or with status 400 (Bad Request) if the labProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lab-profiles")
    @Timed
    public ResponseEntity<LabProfile> createLabProfile(@RequestBody LabProfile labProfile) throws URISyntaxException {
        log.debug("REST request to save LabProfile : {}", labProfile);
        if (labProfile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("labProfile", "idexists", "A new labProfile cannot already have an ID")).body(null);
        }
        LabProfile result = labProfileService.save(labProfile);
        return ResponseEntity.created(new URI("/api/lab-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("labProfile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lab-profiles : Updates an existing labProfile.
     *
     * @param labProfile the labProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated labProfile,
     * or with status 400 (Bad Request) if the labProfile is not valid,
     * or with status 500 (Internal Server Error) if the labProfile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lab-profiles")
    @Timed
    public ResponseEntity<LabProfile> updateLabProfile(@RequestBody LabProfile labProfile) throws URISyntaxException {
        log.debug("REST request to update LabProfile : {}", labProfile);
        if (labProfile.getId() == null) {
            return createLabProfile(labProfile);
        }
        LabProfile result = labProfileService.save(labProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("labProfile", labProfile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lab-profiles : get all the labProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of labProfiles in body
     */
    @GetMapping("/lab-profiles")
    @Timed
    public List<LabProfile> getAllLabProfiles() {
        log.debug("REST request to get all LabProfiles");
        return labProfileService.findAll();
    }

    /**
     * GET  /lab-profiles/:id : get the "id" labProfile.
     *
     * @param id the id of the labProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the labProfile, or with status 404 (Not Found)
     */
    @GetMapping("/lab-profiles/{id}")
    @Timed
    public ResponseEntity<LabProfile> getLabProfile(@PathVariable Long id) {
        log.debug("REST request to get LabProfile : {}", id);
        LabProfile labProfile = labProfileService.findOne(id);
        return Optional.ofNullable(labProfile)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lab-profiles/:id : delete the "id" labProfile.
     *
     * @param id the id of the labProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lab-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteLabProfile(@PathVariable Long id) {
        log.debug("REST request to delete LabProfile : {}", id);
        labProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("labProfile", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lab-profiles?query=:query : search for the labProfile corresponding
     * to the query.
     *
     * @param query the query of the labProfile search
     * @return the result of the search
     */
    @GetMapping("/_search/lab-profiles")
    @Timed
    public List<LabProfile> searchLabProfiles(@RequestParam String query) {
        log.debug("REST request to search LabProfiles for query {}", query);
        return labProfileService.search(query);
    }

    /**
     * GET  /lab-profiles : get all the labProfiles filtering by facility.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of labProfiles in body
     */
    @GetMapping("/lab-profiles-facility/{id}")
    @Timed
    public List<LabProfile> getAllLabProfilesByFacility(@PathVariable Long id) {
        log.debug("REST request to get all LabProfiles");
        return labProfileService.findAllByFacility(id);
    }


}
