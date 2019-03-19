package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.ServiceProvider;
import co.tmunited.bluebook.service.ServiceProviderService;
import co.tmunited.bluebook.web.rest.util.HeaderUtil;
import co.tmunited.bluebook.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing ServiceProvider.
 */
@RestController
@RequestMapping("/api")
public class ServiceProviderResource {

    private final Logger log = LoggerFactory.getLogger(ServiceProviderResource.class);
        
    @Inject
    private ServiceProviderService serviceProviderService;

    /**
     * POST  /service-providers : Create a new serviceProvider.
     *
     * @param serviceProvider the serviceProvider to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceProvider, or with status 400 (Bad Request) if the serviceProvider has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-providers")
    @Timed
    public ResponseEntity<ServiceProvider> createServiceProvider(@RequestBody ServiceProvider serviceProvider) throws URISyntaxException {
        log.debug("REST request to save ServiceProvider : {}", serviceProvider);
        if (serviceProvider.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("serviceProvider", "idexists", "A new serviceProvider cannot already have an ID")).body(null);
        }
        ServiceProvider result = serviceProviderService.save(serviceProvider);
        return ResponseEntity.created(new URI("/api/service-providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("serviceProvider", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-providers : Updates an existing serviceProvider.
     *
     * @param serviceProvider the serviceProvider to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceProvider,
     * or with status 400 (Bad Request) if the serviceProvider is not valid,
     * or with status 500 (Internal Server Error) if the serviceProvider couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-providers")
    @Timed
    public ResponseEntity<ServiceProvider> updateServiceProvider(@RequestBody ServiceProvider serviceProvider) throws URISyntaxException {
        log.debug("REST request to update ServiceProvider : {}", serviceProvider);
        if (serviceProvider.getId() == null) {
            return createServiceProvider(serviceProvider);
        }
        ServiceProvider result = serviceProviderService.save(serviceProvider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("serviceProvider", serviceProvider.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-providers : get all the serviceProviders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceProviders in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/service-providers")
    @Timed
    public ResponseEntity<List<ServiceProvider>> getAllServiceProviders(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ServiceProviders");
        Page<ServiceProvider> page = serviceProviderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service-providers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /service-providers/:id : get the "id" serviceProvider.
     *
     * @param id the id of the serviceProvider to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceProvider, or with status 404 (Not Found)
     */
    @GetMapping("/service-providers/{id}")
    @Timed
    public ResponseEntity<ServiceProvider> getServiceProvider(@PathVariable Long id) {
        log.debug("REST request to get ServiceProvider : {}", id);
        ServiceProvider serviceProvider = serviceProviderService.findOne(id);
        return Optional.ofNullable(serviceProvider)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /service-providers/:id : delete the "id" serviceProvider.
     *
     * @param id the id of the serviceProvider to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-providers/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable Long id) {
        log.debug("REST request to delete ServiceProvider : {}", id);
        serviceProviderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("serviceProvider", id.toString())).build();
    }

    /**
     * SEARCH  /_search/service-providers?query=:query : search for the serviceProvider corresponding
     * to the query.
     *
     * @param query the query of the serviceProvider search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/service-providers")
    @Timed
    public ResponseEntity<List<ServiceProvider>> searchServiceProviders(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of ServiceProviders for query {}", query);
        Page<ServiceProvider> page = serviceProviderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/service-providers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
