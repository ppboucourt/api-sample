package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Routes;
import co.tmunited.bluebook.service.RoutesService;
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
 * REST controller for managing Routes.
 */
@RestController
@RequestMapping("/api")
public class RoutesResource {

    private final Logger log = LoggerFactory.getLogger(RoutesResource.class);
        
    @Inject
    private RoutesService routesService;

    /**
     * POST  /routes : Create a new routes.
     *
     * @param routes the routes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new routes, or with status 400 (Bad Request) if the routes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/routes")
    @Timed
    public ResponseEntity<Routes> createRoutes(@Valid @RequestBody Routes routes) throws URISyntaxException {
        log.debug("REST request to save Routes : {}", routes);
        if (routes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("routes", "idexists", "A new routes cannot already have an ID")).body(null);
        }
        Routes result = routesService.save(routes);
        return ResponseEntity.created(new URI("/api/routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("routes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /routes : Updates an existing routes.
     *
     * @param routes the routes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated routes,
     * or with status 400 (Bad Request) if the routes is not valid,
     * or with status 500 (Internal Server Error) if the routes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/routes")
    @Timed
    public ResponseEntity<Routes> updateRoutes(@Valid @RequestBody Routes routes) throws URISyntaxException {
        log.debug("REST request to update Routes : {}", routes);
        if (routes.getId() == null) {
            return createRoutes(routes);
        }
        Routes result = routesService.save(routes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("routes", routes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /routes : get all the routes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of routes in body
     */
    @GetMapping("/routes")
    @Timed
    public List<Routes> getAllRoutes() {
        log.debug("REST request to get all Routes");
        return routesService.findAll();
    }

    /**
     * GET  /routes/:id : get the "id" routes.
     *
     * @param id the id of the routes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the routes, or with status 404 (Not Found)
     */
    @GetMapping("/routes/{id}")
    @Timed
    public ResponseEntity<Routes> getRoutes(@PathVariable Long id) {
        log.debug("REST request to get Routes : {}", id);
        Routes routes = routesService.findOne(id);
        return Optional.ofNullable(routes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /routes/:id : delete the "id" routes.
     *
     * @param id the id of the routes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/routes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoutes(@PathVariable Long id) {
        log.debug("REST request to delete Routes : {}", id);
        routesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("routes", id.toString())).build();
    }

    /**
     * SEARCH  /_search/routes?query=:query : search for the routes corresponding
     * to the query.
     *
     * @param query the query of the routes search 
     * @return the result of the search
     */
    @GetMapping("/_search/routes")
    @Timed
    public List<Routes> searchRoutes(@RequestParam String query) {
        log.debug("REST request to search Routes for query {}", query);
        return routesService.search(query);
    }


}
