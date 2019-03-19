package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Signature;
import co.tmunited.bluebook.service.SignatureService;
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
 * REST controller for managing Signature.
 */
@RestController
@RequestMapping("/api")
public class SignatureResource {

    private final Logger log = LoggerFactory.getLogger(SignatureResource.class);
        
    @Inject
    private SignatureService signatureService;

    /**
     * POST  /signatures : Create a new signature.
     *
     * @param signature the signature to create
     * @return the ResponseEntity with status 201 (Created) and with body the new signature, or with status 400 (Bad Request) if the signature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/signatures")
    @Timed
    public ResponseEntity<Signature> createSignature(@RequestBody Signature signature) throws URISyntaxException {
        log.debug("REST request to save Signature : {}", signature);
        if (signature.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("signature", "idexists", "A new signature cannot already have an ID")).body(null);
        }
        Signature result = signatureService.save(signature);
        return ResponseEntity.created(new URI("/api/signatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("signature", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /signatures : Updates an existing signature.
     *
     * @param signature the signature to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated signature,
     * or with status 400 (Bad Request) if the signature is not valid,
     * or with status 500 (Internal Server Error) if the signature couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/signatures")
    @Timed
    public ResponseEntity<Signature> updateSignature(@RequestBody Signature signature) throws URISyntaxException {
        log.debug("REST request to update Signature : {}", signature);
        if (signature.getId() == null) {
            return createSignature(signature);
        }
        Signature result = signatureService.save(signature);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("signature", signature.getId().toString()))
            .body(result);
    }

    /**
     * GET  /signatures : get all the signatures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of signatures in body
     */
    @GetMapping("/signatures")
    @Timed
    public List<Signature> getAllSignatures() {
        log.debug("REST request to get all Signatures");
        return signatureService.findAll();
    }

    /**
     * GET  /signatures/:id : get the "id" signature.
     *
     * @param id the id of the signature to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the signature, or with status 404 (Not Found)
     */
    @GetMapping("/signatures/{id}")
    @Timed
    public ResponseEntity<Signature> getSignature(@PathVariable Long id) {
        log.debug("REST request to get Signature : {}", id);
        Signature signature = signatureService.findOne(id);
        return Optional.ofNullable(signature)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /signatures/:id : delete the "id" signature.
     *
     * @param id the id of the signature to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/signatures/{id}")
    @Timed
    public ResponseEntity<Void> deleteSignature(@PathVariable Long id) {
        log.debug("REST request to delete Signature : {}", id);
        signatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("signature", id.toString())).build();
    }

    /**
     * SEARCH  /_search/signatures?query=:query : search for the signature corresponding
     * to the query.
     *
     * @param query the query of the signature search 
     * @return the result of the search
     */
    @GetMapping("/_search/signatures")
    @Timed
    public List<Signature> searchSignatures(@RequestParam String query) {
        log.debug("REST request to search Signatures for query {}", query);
        return signatureService.search(query);
    }


}
