package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.Forms;
import co.tmunited.bluebook.domain.TypeLevelCare;
import co.tmunited.bluebook.service.TypeLevelCareService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TypeLevelCare.
 */
@RestController
@RequestMapping("/api")
public class TypeLevelCareResource {

    private final Logger log = LoggerFactory.getLogger(TypeLevelCareResource.class);

    @Inject
    private TypeLevelCareService typeLevelCareService;

    /**
     * POST  /type-level-cares : Create a new typeLevelCare.
     *
     * @param typeLevelCare the typeLevelCare to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeLevelCare, or with status 400 (Bad Request) if the typeLevelCare has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-level-cares")
    @Timed
    public ResponseEntity<TypeLevelCare> createTypeLevelCare(@RequestBody TypeLevelCare typeLevelCare) throws URISyntaxException {
        log.debug("REST request to save TypeLevelCare : {}", typeLevelCare);
        if (typeLevelCare.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeLevelCare", "idexists", "A new typeLevelCare cannot already have an ID")).body(null);
        }
        TypeLevelCare result = typeLevelCareService.save(typeLevelCare);
        return ResponseEntity.created(new URI("/api/type-level-cares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeLevelCare", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-level-cares : Updates an existing typeLevelCare.
     *
     * @param typeLevelCare the typeLevelCare to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeLevelCare,
     * or with status 400 (Bad Request) if the typeLevelCare is not valid,
     * or with status 500 (Internal Server Error) if the typeLevelCare couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-level-cares")
    @Timed
    public ResponseEntity<TypeLevelCare> updateTypeLevelCare(@RequestBody TypeLevelCare typeLevelCare) throws URISyntaxException {
        log.debug("REST request to update TypeLevelCare : {}", typeLevelCare);
        if (typeLevelCare.getId() == null) {
            return createTypeLevelCare(typeLevelCare);
        }
        TypeLevelCare result = typeLevelCareService.save(typeLevelCare);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeLevelCare", typeLevelCare.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-level-cares : get all the typeLevelCares.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeLevelCares in body
     */
    @GetMapping("/type-level-cares")
    @Timed
    public List<TypeLevelCare> getAllTypeLevelCares() {
        log.debug("REST request to get all TypeLevelCares");
        return typeLevelCareService.findAll();
    }

    /**
     * GET  /type-level-cares/:id : get the "id" typeLevelCare.
     *
     * @param id the id of the typeLevelCare to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeLevelCare, or with status 404 (Not Found)
     */
    @GetMapping("/type-level-cares/{id}")
    @Timed
    public ResponseEntity<TypeLevelCare> getTypeLevelCare(@PathVariable Long id) {
        log.debug("REST request to get TypeLevelCare : {}", id);
        TypeLevelCare typeLevelCare = typeLevelCareService.findOne(id);
        return Optional.ofNullable(typeLevelCare)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-level-cares/:id : delete the "id" typeLevelCare.
     *
     * @param id the id of the typeLevelCare to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-level-cares/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeLevelCare(@PathVariable Long id) {
        log.debug("REST request to delete TypeLevelCare : {}", id);
        typeLevelCareService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeLevelCare", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-level-cares?query=:query : search for the typeLevelCare corresponding
     * to the query.
     *
     * @param query the query of the typeLevelCare search
     * @return the result of the search
     */
    @GetMapping("/_search/type-level-cares")
    @Timed
    public List<TypeLevelCare> searchTypeLevelCares(@RequestParam String query) {
        log.debug("REST request to search TypeLevelCares for query {}", query);
        return typeLevelCareService.search(query);
    }

    /**
     * Get all forms belonging to a levelCare
     * @param facId
     * @param lcId
     * @return
     */
    @GetMapping("/type-level-cares/forms/{facId}/{lcId}")
    @Timed
    public List<Forms> getAllFormsByFacilityAndLevelCareLoadedAutomatic(@PathVariable Long facId, @PathVariable Long lcId) {
        log.debug("REST request to get Forms throw LevelCare : {} {}", facId, lcId);
        return typeLevelCareService.findAllByFacilityAndLevelCareLoadedAutomatic(facId, lcId);
    }

    /**
     * Get all forms belonging to a one facility
     * @param id
     * @return
     */
    @GetMapping("/type-level-cares/facility/{id}")
    @Timed
    public List<TypeLevelCare> getAllByFacility(@PathVariable Long id) {
        log.debug("REST request to get Forms throw LevelCare : {} {}", id);
        return typeLevelCareService.findAllByFacility(id);
    }



}
