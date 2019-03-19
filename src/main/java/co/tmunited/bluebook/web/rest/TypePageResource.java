package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypePage;
import co.tmunited.bluebook.service.TypePageService;
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
 * REST controller for managing TypePage.
 */
@RestController
@RequestMapping("/api")
public class TypePageResource {

    private final Logger log = LoggerFactory.getLogger(TypePageResource.class);
        
    @Inject
    private TypePageService typePageService;

    /**
     * POST  /type-pages : Create a new typePage.
     *
     * @param typePage the typePage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePage, or with status 400 (Bad Request) if the typePage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-pages")
    @Timed
    public ResponseEntity<TypePage> createTypePage(@RequestBody TypePage typePage) throws URISyntaxException {
        log.debug("REST request to save TypePage : {}", typePage);
        if (typePage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typePage", "idexists", "A new typePage cannot already have an ID")).body(null);
        }
        TypePage result = typePageService.save(typePage);
        return ResponseEntity.created(new URI("/api/type-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typePage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-pages : Updates an existing typePage.
     *
     * @param typePage the typePage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePage,
     * or with status 400 (Bad Request) if the typePage is not valid,
     * or with status 500 (Internal Server Error) if the typePage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-pages")
    @Timed
    public ResponseEntity<TypePage> updateTypePage(@RequestBody TypePage typePage) throws URISyntaxException {
        log.debug("REST request to update TypePage : {}", typePage);
        if (typePage.getId() == null) {
            return createTypePage(typePage);
        }
        TypePage result = typePageService.save(typePage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typePage", typePage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-pages : get all the typePages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePages in body
     */
    @GetMapping("/type-pages")
    @Timed
    public List<TypePage> getAllTypePages() {
        log.debug("REST request to get all TypePages");
        return typePageService.findAll();
    }

    /**
     * GET  /type-pages/:id : get the "id" typePage.
     *
     * @param id the id of the typePage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePage, or with status 404 (Not Found)
     */
    @GetMapping("/type-pages/{id}")
    @Timed
    public ResponseEntity<TypePage> getTypePage(@PathVariable Long id) {
        log.debug("REST request to get TypePage : {}", id);
        TypePage typePage = typePageService.findOne(id);
        return Optional.ofNullable(typePage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-pages/:id : delete the "id" typePage.
     *
     * @param id the id of the typePage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-pages/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePage(@PathVariable Long id) {
        log.debug("REST request to delete TypePage : {}", id);
        typePageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typePage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-pages?query=:query : search for the typePage corresponding
     * to the query.
     *
     * @param query the query of the typePage search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-pages")
    @Timed
    public List<TypePage> searchTypePages(@RequestParam String query) {
        log.debug("REST request to search TypePages for query {}", query);
        return typePageService.search(query);
    }


}
