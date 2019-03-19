package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.vo.ShiftVO;
import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Shifts;
import co.tmunited.bluebook.service.ShiftsService;
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
 * REST controller for managing Shifts.
 */
@RestController
@RequestMapping("/api")
public class ShiftsResource {

    private final Logger log = LoggerFactory.getLogger(ShiftsResource.class);

    @Inject
    private ShiftsService shiftsService;

    /**
     * POST  /shifts : Create a new shifts.
     *
     * @param shifts the shifts to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shifts, or with status 400 (Bad Request) if the shifts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shifts")
    @Timed
    public ResponseEntity<Shifts> createShifts(@RequestBody Shifts shifts) throws URISyntaxException {
        log.debug("REST request to save Shifts : {}", shifts);
        if (shifts.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("shifts", "idexists", "A new shifts cannot already have an ID")).body(null);
        }
        Shifts result = shiftsService.save(shifts);
        return ResponseEntity.created(new URI("/api/shifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("shifts", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shifts : Updates an existing shifts.
     *
     * @param shifts the shifts to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shifts,
     * or with status 400 (Bad Request) if the shifts is not valid,
     * or with status 500 (Internal Server Error) if the shifts couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shifts")
    @Timed
    public ResponseEntity<Shifts> updateShifts(@RequestBody Shifts shifts) throws URISyntaxException {
        log.debug("REST request to update Shifts : {}", shifts);
        if (shifts.getId() == null) {
            return createShifts(shifts);
        }
        Shifts result = shiftsService.save(shifts);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("shifts", shifts.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shifts : get all the shifts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shifts in body
     */
    @GetMapping("/shifts")
    @Timed
    public List<ShiftVO> getAllShifts() {
        log.debug("REST request to get all Shifts");
        return shiftsService.findAllShifts();
    }

    /**
     * GET  /shifts/:id : get the "id" shifts.
     *
     * @param id the id of the shifts to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shifts, or with status 404 (Not Found)
     */
    @GetMapping("/shifts/{id}")
    @Timed
    public ResponseEntity<Shifts> getShifts(@PathVariable Long id) {
        log.debug("REST request to get Shifts : {}", id);
        Shifts shifts = shiftsService.findOne(id);
        return Optional.ofNullable(shifts)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shifts/:id : delete the "id" shifts.
     *
     * @param id the id of the shifts to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shifts/{id}")
    @Timed
    public ResponseEntity<Void> deleteShifts(@PathVariable Long id) {
        log.debug("REST request to delete Shifts : {}", id);
        shiftsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("shifts", id.toString())).build();
    }

    /**
     * SEARCH  /_search/shifts?query=:query : search for the shifts corresponding
     * to the query.
     *
     * @param query the query of the shifts search
     * @return the result of the search
     */
    @GetMapping("/_search/shifts")
    @Timed
    public List<Shifts> searchShifts(@RequestParam String query) {
        log.debug("REST request to search Shifts for query {}", query);
        return shiftsService.search(query);
    }


}
