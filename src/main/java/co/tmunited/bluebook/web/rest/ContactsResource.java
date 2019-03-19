package co.tmunited.bluebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.Contacts;
import co.tmunited.bluebook.service.ContactsService;
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
 * REST controller for managing Contacts.
 */
@RestController
@RequestMapping("/api")
public class ContactsResource {

    private final Logger log = LoggerFactory.getLogger(ContactsResource.class);

    @Inject
    private ContactsService contactsService;

    /**
     * POST  /contacts : Create a new contacts.
     *
     * @param contacts the contacts to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contacts, or with status 400 (Bad Request) if the contacts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacts")
    @Timed
    public ResponseEntity<Contacts> createContacts(@Valid @RequestBody Contacts contacts) throws URISyntaxException {
        log.debug("REST request to save Contacts : {}", contacts);
        if (contacts.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contacts", "idexists", "A new contacts cannot already have an ID")).body(null);
        }
        Contacts result = contactsService.save(contacts);
        return ResponseEntity.created(new URI("/api/contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contacts", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacts : Updates an existing contacts.
     *
     * @param contacts the contacts to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contacts,
     * or with status 400 (Bad Request) if the contacts is not valid,
     * or with status 500 (Internal Server Error) if the contacts couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contacts")
    @Timed
    public ResponseEntity<Contacts> updateContacts(@Valid @RequestBody Contacts contacts) throws URISyntaxException {
        log.debug("REST request to update Contacts : {}", contacts);
        if (contacts.getId() == null) {
            return createContacts(contacts);
        }
        Contacts result = contactsService.save(contacts);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contacts", contacts.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacts : get all the contacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contacts in body
     */
    @GetMapping("/contacts")
    @Timed
    public List<Contacts> getAllContacts() {
        log.debug("REST request to get all Contacts");
        return contactsService.findAll();
    }

    /**
     * GET  /contacts/:id : get the "id" contacts.
     *
     * @param id the id of the contacts to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contacts, or with status 404 (Not Found)
     */
    @GetMapping("/contacts/{id}")
    @Timed
    public ResponseEntity<Contacts> getContacts(@PathVariable Long id) {
        log.debug("REST request to get Contacts : {}", id);
        Contacts contacts = contactsService.findOne(id);
        return Optional.ofNullable(contacts)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contacts/:id : delete the "id" contacts.
     *
     * @param id the id of the contacts to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteContacts(@PathVariable Long id) {
        log.debug("REST request to delete Contacts : {}", id);
        contactsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contacts", id.toString())).build();
    }

    /**
     * SEARCH  /_search/contacts?query=:query : search for the contacts corresponding
     * to the query.
     *
     * @param query the query of the contacts search
     * @return the result of the search
     */
    @GetMapping("/_search/contacts")
    @Timed
    public List<Contacts> searchContacts(@RequestParam String query) {
        log.debug("REST request to search Contacts for query {}", query);
        return contactsService.search(query);
    }

    /**
     * Search the contact by chart and relationship
     *
     * @param cid of the chart
     * @param rid of the relationship
     * @return Contact
     */
    @GetMapping("/contacts/relationship/{cid}/{rid}")
    public List<Contacts> searchContactByRelationship(@PathVariable Long cid, @PathVariable Long rid){
        log.debug("REST request to search Contact for a chart and a relationship {} {}", cid, rid);
        return contactsService.findByRelationOfChart(cid, rid);
    }

    /**
     * Get the chart's guarantor contact
     * @param id of the chart
     * @return
     */
    @GetMapping("/contacts/guarantor/{id}")
    public Contacts getGuarantorOfChart(@PathVariable Long id) {
        log.debug("REST request to search Contact for a chart and a have to be a guarantor {}", id);
        return contactsService.getGuarantor(id);
    }


}
