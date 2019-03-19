package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeFoodDiets;
import co.tmunited.bluebook.service.TypeFoodDietsService;
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
 * REST controller for managing TypeFoodDiets.
 */
@RestController
@RequestMapping("/api")
public class TypeFoodDietsResource {

    private final Logger log = LoggerFactory.getLogger(TypeFoodDietsResource.class);
        
    @Inject
    private TypeFoodDietsService typeFoodDietsService;

    /**
     * POST  /type-food-diets : Create a new typeFoodDiets.
     *
     * @param typeFoodDiets the typeFoodDiets to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeFoodDiets, or with status 400 (Bad Request) if the typeFoodDiets has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-food-diets")
    @Timed
    public ResponseEntity<TypeFoodDiets> createTypeFoodDiets(@RequestBody TypeFoodDiets typeFoodDiets) throws URISyntaxException {
        log.debug("REST request to save TypeFoodDiets : {}", typeFoodDiets);
        if (typeFoodDiets.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeFoodDiets", "idexists", "A new typeFoodDiets cannot already have an ID")).body(null);
        }
        TypeFoodDiets result = typeFoodDietsService.save(typeFoodDiets);
        return ResponseEntity.created(new URI("/api/type-food-diets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeFoodDiets", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-food-diets : Updates an existing typeFoodDiets.
     *
     * @param typeFoodDiets the typeFoodDiets to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeFoodDiets,
     * or with status 400 (Bad Request) if the typeFoodDiets is not valid,
     * or with status 500 (Internal Server Error) if the typeFoodDiets couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-food-diets")
    @Timed
    public ResponseEntity<TypeFoodDiets> updateTypeFoodDiets(@RequestBody TypeFoodDiets typeFoodDiets) throws URISyntaxException {
        log.debug("REST request to update TypeFoodDiets : {}", typeFoodDiets);
        if (typeFoodDiets.getId() == null) {
            return createTypeFoodDiets(typeFoodDiets);
        }
        TypeFoodDiets result = typeFoodDietsService.save(typeFoodDiets);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeFoodDiets", typeFoodDiets.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-food-diets : get all the typeFoodDiets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeFoodDiets in body
     */
    @GetMapping("/type-food-diets")
    @Timed
    public List<TypeFoodDiets> getAllTypeFoodDiets() {
        log.debug("REST request to get all TypeFoodDiets");
        return typeFoodDietsService.findAll();
    }

    /**
     * GET  /type-food-diets/:id : get the "id" typeFoodDiets.
     *
     * @param id the id of the typeFoodDiets to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeFoodDiets, or with status 404 (Not Found)
     */
    @GetMapping("/type-food-diets/{id}")
    @Timed
    public ResponseEntity<TypeFoodDiets> getTypeFoodDiets(@PathVariable Long id) {
        log.debug("REST request to get TypeFoodDiets : {}", id);
        TypeFoodDiets typeFoodDiets = typeFoodDietsService.findOne(id);
        return Optional.ofNullable(typeFoodDiets)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-food-diets/:id : delete the "id" typeFoodDiets.
     *
     * @param id the id of the typeFoodDiets to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-food-diets/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeFoodDiets(@PathVariable Long id) {
        log.debug("REST request to delete TypeFoodDiets : {}", id);
        typeFoodDietsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeFoodDiets", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-food-diets?query=:query : search for the typeFoodDiets corresponding
     * to the query.
     *
     * @param query the query of the typeFoodDiets search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-food-diets")
    @Timed
    public List<TypeFoodDiets> searchTypeFoodDiets(@RequestParam String query) {
        log.debug("REST request to search TypeFoodDiets for query {}", query);
        return typeFoodDietsService.search(query);
    }


}
