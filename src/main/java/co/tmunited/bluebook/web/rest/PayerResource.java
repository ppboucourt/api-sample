package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Payer;
import co.tmunited.bluebook.service.PayerService;
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
 * REST controller for managing Payer.
 */
@RestController
@RequestMapping("/api")
public class PayerResource {

    private final Logger log = LoggerFactory.getLogger(PayerResource.class);
        
    @Inject
    private PayerService payerService;

    /**
     * POST  /payers : Create a new payer.
     *
     * @param payer the payer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payer, or with status 400 (Bad Request) if the payer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payers")
    @Timed
    public ResponseEntity<Payer> createPayer(@Valid @RequestBody Payer payer) throws URISyntaxException {
        log.debug("REST request to save Payer : {}", payer);
        if (payer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("payer", "idexists", "A new payer cannot already have an ID")).body(null);
        }
        Payer result = payerService.save(payer);
        return ResponseEntity.created(new URI("/api/payers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("payer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payers : Updates an existing payer.
     *
     * @param payer the payer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payer,
     * or with status 400 (Bad Request) if the payer is not valid,
     * or with status 500 (Internal Server Error) if the payer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payers")
    @Timed
    public ResponseEntity<Payer> updatePayer(@Valid @RequestBody Payer payer) throws URISyntaxException {
        log.debug("REST request to update Payer : {}", payer);
        if (payer.getId() == null) {
            return createPayer(payer);
        }
        Payer result = payerService.save(payer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("payer", payer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payers : get all the payers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payers in body
     */
    @GetMapping("/payers")
    @Timed
    public List<Payer> getAllPayers() {
        log.debug("REST request to get all Payers");
        return payerService.findAll();
    }

    /**
     * GET  /payers/:id : get the "id" payer.
     *
     * @param id the id of the payer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payer, or with status 404 (Not Found)
     */
    @GetMapping("/payers/{id}")
    @Timed
    public ResponseEntity<Payer> getPayer(@PathVariable Long id) {
        log.debug("REST request to get Payer : {}", id);
        Payer payer = payerService.findOne(id);
        return Optional.ofNullable(payer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payers/:id : delete the "id" payer.
     *
     * @param id the id of the payer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payers/{id}")
    @Timed
    public ResponseEntity<Void> deletePayer(@PathVariable Long id) {
        log.debug("REST request to delete Payer : {}", id);
        payerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("payer", id.toString())).build();
    }

    /**
     * SEARCH  /_search/payers?query=:query : search for the payer corresponding
     * to the query.
     *
     * @param query the query of the payer search 
     * @return the result of the search
     */
    @GetMapping("/_search/payers")
    @Timed
    public List<Payer> searchPayers(@RequestParam String query) {
        log.debug("REST request to search Payers for query {}", query);
        return payerService.search(query);
    }


}
