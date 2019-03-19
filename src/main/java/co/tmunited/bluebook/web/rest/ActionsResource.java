package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Actions;
import co.tmunited.bluebook.service.ActionsService;
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
 * REST controller for managing Actions.
 */
@RestController
@RequestMapping("/api")
public class ActionsResource {

    private final Logger log = LoggerFactory.getLogger(ActionsResource.class);
        
    @Inject
    private ActionsService actionsService;

    /**
     * POST  /actions : Create a new actions.
     *
     * @param actions the actions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actions, or with status 400 (Bad Request) if the actions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actions")
    @Timed
    public ResponseEntity<Actions> createActions(@Valid @RequestBody Actions actions) throws URISyntaxException {
        log.debug("REST request to save Actions : {}", actions);
        if (actions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("actions", "idexists", "A new actions cannot already have an ID")).body(null);
        }
        Actions result = actionsService.save(actions);
        return ResponseEntity.created(new URI("/api/actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("actions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actions : Updates an existing actions.
     *
     * @param actions the actions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actions,
     * or with status 400 (Bad Request) if the actions is not valid,
     * or with status 500 (Internal Server Error) if the actions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actions")
    @Timed
    public ResponseEntity<Actions> updateActions(@Valid @RequestBody Actions actions) throws URISyntaxException {
        log.debug("REST request to update Actions : {}", actions);
        if (actions.getId() == null) {
            return createActions(actions);
        }
        Actions result = actionsService.save(actions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("actions", actions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actions : get all the actions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actions in body
     */
    @GetMapping("/actions")
    @Timed
    public List<Actions> getAllActions() {
        log.debug("REST request to get all Actions");
        return actionsService.findAll();
    }

    /**
     * GET  /actions/:id : get the "id" actions.
     *
     * @param id the id of the actions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actions, or with status 404 (Not Found)
     */
    @GetMapping("/actions/{id}")
    @Timed
    public ResponseEntity<Actions> getActions(@PathVariable Long id) {
        log.debug("REST request to get Actions : {}", id);
        Actions actions = actionsService.findOne(id);
        return Optional.ofNullable(actions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /actions/:id : delete the "id" actions.
     *
     * @param id the id of the actions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actions/{id}")
    @Timed
    public ResponseEntity<Void> deleteActions(@PathVariable Long id) {
        log.debug("REST request to delete Actions : {}", id);
        actionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("actions", id.toString())).build();
    }

    /**
     * SEARCH  /_search/actions?query=:query : search for the actions corresponding
     * to the query.
     *
     * @param query the query of the actions search 
     * @return the result of the search
     */
    @GetMapping("/_search/actions")
    @Timed
    public List<Actions> searchActions(@RequestParam String query) {
        log.debug("REST request to search Actions for query {}", query);
        return actionsService.search(query);
    }


}
