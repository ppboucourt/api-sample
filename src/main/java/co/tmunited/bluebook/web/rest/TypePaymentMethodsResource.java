package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.TypePaymentMethods;
import co.tmunited.bluebook.service.TypePaymentMethodsService;
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
 * REST controller for managing TypePaymentMethods.
 */
@RestController
@RequestMapping("/api")
public class TypePaymentMethodsResource {

    private final Logger log = LoggerFactory.getLogger(TypePaymentMethodsResource.class);
        
    @Inject
    private TypePaymentMethodsService typePaymentMethodsService;

    /**
     * POST  /type-payment-methods : Create a new typePaymentMethods.
     *
     * @param typePaymentMethods the typePaymentMethods to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePaymentMethods, or with status 400 (Bad Request) if the typePaymentMethods has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-payment-methods")
    @Timed
    public ResponseEntity<TypePaymentMethods> createTypePaymentMethods(@RequestBody TypePaymentMethods typePaymentMethods) throws URISyntaxException {
        log.debug("REST request to save TypePaymentMethods : {}", typePaymentMethods);
        if (typePaymentMethods.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("typePaymentMethods", "idexists", "A new typePaymentMethods cannot already have an ID")).body(null);
        }
        TypePaymentMethods result = typePaymentMethodsService.save(typePaymentMethods);
        return ResponseEntity.created(new URI("/api/type-payment-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("typePaymentMethods", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-payment-methods : Updates an existing typePaymentMethods.
     *
     * @param typePaymentMethods the typePaymentMethods to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePaymentMethods,
     * or with status 400 (Bad Request) if the typePaymentMethods is not valid,
     * or with status 500 (Internal Server Error) if the typePaymentMethods couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-payment-methods")
    @Timed
    public ResponseEntity<TypePaymentMethods> updateTypePaymentMethods(@RequestBody TypePaymentMethods typePaymentMethods) throws URISyntaxException {
        log.debug("REST request to update TypePaymentMethods : {}", typePaymentMethods);
        if (typePaymentMethods.getId() == null) {
            return createTypePaymentMethods(typePaymentMethods);
        }
        TypePaymentMethods result = typePaymentMethodsService.save(typePaymentMethods);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("typePaymentMethods", typePaymentMethods.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-payment-methods : get all the typePaymentMethods.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typePaymentMethods in body
     */
    @GetMapping("/type-payment-methods")
    @Timed
    public List<TypePaymentMethods> getAllTypePaymentMethods() {
        log.debug("REST request to get all TypePaymentMethods");
        return typePaymentMethodsService.findAll();
    }

    /**
     * GET  /type-payment-methods/:id : get the "id" typePaymentMethods.
     *
     * @param id the id of the typePaymentMethods to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePaymentMethods, or with status 404 (Not Found)
     */
    @GetMapping("/type-payment-methods/{id}")
    @Timed
    public ResponseEntity<TypePaymentMethods> getTypePaymentMethods(@PathVariable Long id) {
        log.debug("REST request to get TypePaymentMethods : {}", id);
        TypePaymentMethods typePaymentMethods = typePaymentMethodsService.findOne(id);
        return Optional.ofNullable(typePaymentMethods)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-payment-methods/:id : delete the "id" typePaymentMethods.
     *
     * @param id the id of the typePaymentMethods to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-payment-methods/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypePaymentMethods(@PathVariable Long id) {
        log.debug("REST request to delete TypePaymentMethods : {}", id);
        typePaymentMethodsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("typePaymentMethods", id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-payment-methods?query=:query : search for the typePaymentMethods corresponding
     * to the query.
     *
     * @param query the query of the typePaymentMethods search 
     * @return the result of the search
     */
    @GetMapping("/_search/type-payment-methods")
    @Timed
    public List<TypePaymentMethods> searchTypePaymentMethods(@RequestParam String query) {
        log.debug("REST request to search TypePaymentMethods for query {}", query);
        return typePaymentMethodsService.search(query);
    }


}
