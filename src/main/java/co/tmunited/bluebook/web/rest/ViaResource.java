package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.Via;
import co.tmunited.bluebook.repository.ViaRepository;
import co.tmunited.bluebook.repository.search.ViaSearchRepository;
import co.tmunited.bluebook.service.UserService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Via.
 */
@RestController
@RequestMapping("/api")
public class ViaResource {

    private final Logger log = LoggerFactory.getLogger(ViaResource.class);

    @Inject
    private ViaRepository viaRepository;

    @Inject
    private ViaSearchRepository viaSearchRepository;

    @Inject
    private UserService userService;

    /**
     * POST  /vias : Create a new via.
     *
     * @param via the via to create
     * @return the ResponseEntity with status 201 (Created) and with body the new via, or with status 400 (Bad Request) if the via has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vias")
    @Timed
    public ResponseEntity<Via> createVia(@RequestBody Via via) throws URISyntaxException {
        log.debug("REST request to save Via : {}", via);
        if (via.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("via", "idexists", "A new via cannot already have an ID")).body(null);
        }

//        Employee employee = userService.getEmployeeWithAuthorities();
//        via.setCorporation(employee.getCorporation());

        Via result = viaRepository.save(via);
        viaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/vias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("via", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vias : Updates an existing via.
     *
     * @param via the via to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated via,
     * or with status 400 (Bad Request) if the via is not valid,
     * or with status 500 (Internal Server Error) if the via couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vias")
    @Timed
    public ResponseEntity<Via> updateVia(@RequestBody Via via) throws URISyntaxException {
        log.debug("REST request to update Via : {}", via);
        if (via.getId() == null) {
            return createVia(via);
        }
        Via result = viaRepository.save(via);
        viaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("via", via.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vias : get all the vias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vias in body
     */
    @GetMapping("/vias")
    @Timed
    public List<Via> getAllVias() {
        log.debug("REST request to get all Vias");

        List<Via> vias = viaRepository.findAllByDelStatusIsFalse();
        return vias;
    }

    /**
     * GET  /vias/:id : get the "id" via.
     *
     * @param id the id of the via to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the via, or with status 404 (Not Found)
     */
    @GetMapping("/vias/{id}")
    @Timed
    public ResponseEntity<Via> getVia(@PathVariable Long id) {
        log.debug("REST request to get Via : {}", id);
        Via via = viaRepository.findOne(id);
        return Optional.ofNullable(via)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vias/:id : delete the "id" via.
     *
     * @param id the id of the via to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vias/{id}")
    @Timed
    public ResponseEntity<Void> deleteVia(@PathVariable Long id) {
        log.debug("REST request to delete Via : {}", id);
        viaRepository.delete(id);
        viaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("via", id.toString())).build();
    }

    /**
     * SEARCH  /_search/vias?query=:query : search for the via corresponding
     * to the query.
     *
     * @param query the query of the via search
     * @return the result of the search
     */
    @GetMapping("/_search/vias")
    @Timed
    public List<Via> searchVias(@RequestParam String query) {
        log.debug("REST request to search Vias for query {}", query);
        return StreamSupport
            .stream(viaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
