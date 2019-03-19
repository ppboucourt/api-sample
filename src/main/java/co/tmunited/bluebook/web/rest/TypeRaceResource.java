package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypeRace;
import co.tmunited.bluebook.service.TypeRaceService;
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
 * REST controller for managing TypeRace.
 */
@RestController
@RequestMapping("/api")
public class TypeRaceResource {

    private final Logger log = LoggerFactory.getLogger(TypeRaceResource.class);
        
    @Inject
    private TypeRaceService typeRaceService;

    /**
     * POST  /type-races : Create a new typeRace.
     *
     * @param typeRace the typeRace to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeRace, or with status 400 (Bad Request) if the typeRace has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-races")
    @Timed
    public ResponseEntity<TypeRace> createTypeRace(@RequestBody TypeRace typeRace) throws URISyntaxException {
        log.debug("REST request to save TypeRace : {}", typeRace);
        if (typeRace.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typeRace", "idexists", "A new typeRace cannot already have an ID")).body(null);
        }
        TypeRace result = typeRaceService.save(typeRace);
        return ResponseEntity.created(new URI("/api/type-races/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typeRace", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-races : Updates an existing typeRace.
     *
     * @param typeRace the typeRace to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeRace,
     * or with status 400 (Bad Request) if the typeRace is not valid,
     * or with status 500 (Internal Server Error) if the typeRace couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-races")
    @Timed
    public ResponseEntity<TypeRace> updateTypeRace(@RequestBody TypeRace typeRace) throws URISyntaxException {
        log.debug("REST request to update TypeRace : {}", typeRace);
        if (typeRace.getId() == null) {
            return createTypeRace(typeRace);
        }
        TypeRace result = typeRaceService.save(typeRace);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typeRace", typeRace.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-races : get all the typeRaces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeRaces in body
     */
    @GetMapping("/type-races")
    @Timed
    public List<TypeRace> getAllTypeRaces() {
        log.debug("REST request to get all TypeRaces");
        return typeRaceService.findAll();
    }

    /**
     * GET  /type-races/:id : get the "id" typeRace.
     *
     * @param id the id of the typeRace to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeRace, or with status 404 (Not Found)
     */
    @GetMapping("/type-races/{id}")
    @Timed
    public ResponseEntity<TypeRace> getTypeRace(@PathVariable Long id) {
        log.debug("REST request to get TypeRace : {}", id);
        TypeRace typeRace = typeRaceService.findOne(id);
        return Optional.ofNullable(typeRace)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-races/:id : delete the "id" typeRace.
     *
     * @param id the id of the typeRace to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-races/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeRace(@PathVariable Long id) {
        log.debug("REST request to delete TypeRace : {}", id);
        typeRaceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typeRace", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-races?query=:query : search for the typeRace corresponding
     * to the query.
     *
     * @param query the query of the typeRace search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-races")
    @Timed
    public List<TypeRace> searchTypeRaces(@RequestParam String query) {
        log.debug("REST request to search TypeRaces for query {}", query);
        return typeRaceService.search(query);
    }


}
