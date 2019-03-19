package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Tables;
import co.tmunited.bluebook.service.TablesService;
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
 * REST controller for managing Tables.
 */
@RestController
@RequestMapping("/api")
public class TablesResource {

    private final Logger log = LoggerFactory.getLogger(TablesResource.class);
        
    @Inject
    private TablesService tablesService;

    /**
     * POST  /tables : Create a new tables.
     *
     * @param tables the tables to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tables, or with status 400 (Bad Request) if the tables has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tables")
    @Timed
    public ResponseEntity<Tables> createTables(@Valid @RequestBody Tables tables) throws URISyntaxException {
        log.debug("REST request to save Tables : {}", tables);
        if (tables.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tables", "idexists", "A new tables cannot already have an ID")).body(null);
        }
        Tables result = tablesService.save(tables);
        return ResponseEntity.created(new URI("/api/tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tables", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tables : Updates an existing tables.
     *
     * @param tables the tables to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tables,
     * or with status 400 (Bad Request) if the tables is not valid,
     * or with status 500 (Internal Server Error) if the tables couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tables")
    @Timed
    public ResponseEntity<Tables> updateTables(@Valid @RequestBody Tables tables) throws URISyntaxException {
        log.debug("REST request to update Tables : {}", tables);
        if (tables.getId() == null) {
            return createTables(tables);
        }
        Tables result = tablesService.save(tables);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tables", tables.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tables : get all the tables.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tables in body
     */
    @GetMapping("/tables")
    @Timed
    public List<Tables> getAllTables() {
        log.debug("REST request to get all Tables");
        return tablesService.findAll();
    }

    /**
     * GET  /tables/:id : get the "id" tables.
     *
     * @param id the id of the tables to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tables, or with status 404 (Not Found)
     */
    @GetMapping("/tables/{id}")
    @Timed
    public ResponseEntity<Tables> getTables(@PathVariable Long id) {
        log.debug("REST request to get Tables : {}", id);
        Tables tables = tablesService.findOne(id);
        return Optional.ofNullable(tables)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tables/:id : delete the "id" tables.
     *
     * @param id the id of the tables to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tables/{id}")
    @Timed
    public ResponseEntity<Void> deleteTables(@PathVariable Long id) {
        log.debug("REST request to delete Tables : {}", id);
        tablesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tables", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tables?query=:query : search for the tables corresponding
     * to the query.
     *
     * @param query the query of the tables search 
     * @return the result of the search
     */
    @GetMapping("/_search/tables")
    @Timed
    public List<Tables> searchTables(@RequestParam String query) {
        log.debug("REST request to search Tables for query {}", query);
        return tablesService.search(query);
    }


}
