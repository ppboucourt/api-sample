package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.ContactsService;
import co.tmunited.bluebook.domain.Contacts;
import co.tmunited.bluebook.repository.ContactsRepository;
import co.tmunited.bluebook.repository.search.ContactsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Contacts.
 */
@Service
@Transactional
public class ContactsServiceImpl implements ContactsService{

    private final Logger log = LoggerFactory.getLogger(ContactsServiceImpl.class);

    @Inject
    private ContactsRepository contactsRepository;

    @Inject
    private ContactsSearchRepository contactsSearchRepository;

    /**
     * Save a contacts.
     *
     * @param contacts the entity to save
     * @return the persisted entity
     */
    public Contacts save(Contacts contacts) {
        log.debug("Request to save Contacts : {}", contacts);
        Contacts result = contactsRepository.save(contacts);
        contactsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the contacts.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Contacts> findAll() {
        log.debug("Request to get all Contacts");
        List<Contacts> result = contactsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one contacts by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Contacts findOne(Long id) {
        log.debug("Request to get Contacts : {}", id);
        Contacts contacts = contactsRepository.findOne(id);
        return contacts;
    }

    /**
     *  Delete the  contacts by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Contacts : {}", id);
        Contacts contacts = contactsRepository.findOne(id);
        contacts.setDelStatus(true);
        Contacts result = contactsRepository.save(contacts);

        contactsSearchRepository.save(result);
    }

    /**
     * Search for the contacts corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Contacts> search(String query) {
        log.debug("Request to search Contacts for query {}", query);
        return StreamSupport
            .stream(contactsSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     *
     * @param cid of the patient
     * @param rid of the type of relationship
     * @return
     */
    @Override
    public List<Contacts> findByRelationOfChart(Long cid, Long rid) {
        log.debug("Request to search the Contact of the patient by his relationship");
        List<Contacts> contact = contactsRepository.findAllByDelStatusIsFalseAndChartIdAndTypePatientContactsRelationshipId(cid, rid);
        return contact;
    }

    public Contacts getGuarantor(Long chartId){
        List<Contacts> list = contactsRepository.findAllByDelStatusIsFalseAndChartIdAndTypePatientContactsRelationshipId(chartId, Long.valueOf(5));
        Contacts contacts = new Contacts();
        if(list.size() > 0){
            contacts = list.get(0);
        }
        return contacts;
    }

    public List<Contacts> findByChart(Long id){
        List<Contacts> result = contactsRepository.findAllByDelStatusIsFalseAndChartId(id);
        return result;
    }
}
