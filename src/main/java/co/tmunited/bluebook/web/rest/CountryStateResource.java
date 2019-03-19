package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.CountryState;
import co.tmunited.bluebook.service.CountryStateService;
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
 * REST controller for managing CountryState.
 */
@RestController
@RequestMapping("/api")
public class CountryStateResource {

    private final Logger log = LoggerFactory.getLogger(CountryStateResource.class);
        
    @Inject
    private CountryStateService countryStateService;

    /**
     * POST  /country-states : Create a new countryState.
     *
     * @param countryState the countryState to create
     * @return the ResponseEntity with status 201 (Created) and with body the new countryState, or with status 400 (Bad Request) if the countryState has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/country-states")
    @Timed
    public ResponseEntity<CountryState> createCountryState(@Valid @RequestBody CountryState countryState) throws URISyntaxException {
        log.debug("REST request to save CountryState : {}", countryState);
        if (countryState.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("countryState", "idexists", "A new countryState cannot already have an ID")).body(null);
        }
        CountryState result = countryStateService.save(countryState);
        return ResponseEntity.created(new URI("/api/country-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("countryState", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /country-states : Updates an existing countryState.
     *
     * @param countryState the countryState to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated countryState,
     * or with status 400 (Bad Request) if the countryState is not valid,
     * or with status 500 (Internal Server Error) if the countryState couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/country-states")
    @Timed
    public ResponseEntity<CountryState> updateCountryState(@Valid @RequestBody CountryState countryState) throws URISyntaxException {
        log.debug("REST request to update CountryState : {}", countryState);
        if (countryState.getId() == null) {
            return createCountryState(countryState);
        }
        CountryState result = countryStateService.save(countryState);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("countryState", countryState.getId().toString()))
            .body(result);
    }

    /**
     * GET  /country-states : get all the countryStates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of countryStates in body
     */
    @GetMapping("/country-states")
    @Timed
    public List<CountryState> getAllCountryStates() {
        log.debug("REST request to get all CountryStates");
        return countryStateService.findAll();
    }

    /**
     * GET  /country-states/:id : get the "id" countryState.
     *
     * @param id the id of the countryState to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the countryState, or with status 404 (Not Found)
     */
    @GetMapping("/country-states/{id}")
    @Timed
    public ResponseEntity<CountryState> getCountryState(@PathVariable Long id) {
        log.debug("REST request to get CountryState : {}", id);
        CountryState countryState = countryStateService.findOne(id);
        return Optional.ofNullable(countryState)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /country-states/:id : delete the "id" countryState.
     *
     * @param id the id of the countryState to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/country-states/{id}")
    @Timed
    public ResponseEntity<Void> deleteCountryState(@PathVariable Long id) {
        log.debug("REST request to delete CountryState : {}", id);
        countryStateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("countryState", id.toString())).build();
    }

    /**
     * SEARCH  /_search/country-states?query=:query : search for the countryState corresponding
     * to the query.
     *
     * @param query the query of the countryState search 
     * @return the result of the search
     */
    @GetMapping("/_search/country-states")
    @Timed
    public List<CountryState> searchCountryStates(@RequestParam String query) {
        log.debug("REST request to search CountryStates for query {}", query);
        return countryStateService.search(query);
    }


}
