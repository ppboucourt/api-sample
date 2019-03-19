package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.ContactsFacilityService;
import co.tmunited.bluebook.domain.ContactsFacility;
import co.tmunited.bluebook.repository.ContactsFacilityRepository;
import co.tmunited.bluebook.repository.search.ContactsFacilitySearchRepository;
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
 * Service Implementation for managing ContactsFacility.
 */
@Service
@Transactional
public class ContactsFacilityServiceImpl implements ContactsFacilityService{

    private final Logger log = LoggerFactory.getLogger(ContactsFacilityServiceImpl.class);

    @Inject
    private ContactsFacilityRepository contactsFacilityRepository;

    @Inject
    private ContactsFacilitySearchRepository contactsFacilitySearchRepository;

    /**
     * Save a contactsFacility.
     *
     * @param contactsFacility the entity to save
     * @return the persisted entity
     */
    public ContactsFacility save(ContactsFacility contactsFacility) {
        log.debug("Request to save ContactsFacility : {}", contactsFacility);
        ContactsFacility result = contactsFacilityRepository.save(contactsFacility);
        contactsFacilitySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the contactsFacilities.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ContactsFacility> findAll() {
        log.debug("Request to get all ContactsFacilities");
        List<ContactsFacility> result = contactsFacilityRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one contactsFacility by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ContactsFacility findOne(Long id) {
        log.debug("Request to get ContactsFacility : {}", id);
        ContactsFacility contactsFacility = contactsFacilityRepository.findOne(id);
        return contactsFacility;
    }

    /**
     *  Delete the  contactsFacility by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactsFacility : {}", id);
      ContactsFacility contactsFacility = contactsFacilityRepository.findOne(id);
      contactsFacility.setDelStatus(true);
      ContactsFacility result = contactsFacilityRepository.save(contactsFacility);

      contactsFacilitySearchRepository.save(result);
    }

    /**
     * Search for the contactsFacility corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ContactsFacility> search(String query) {
        log.debug("Request to search ContactsFacilities for query {}", query);
        query = query + "*";
        return StreamSupport
            .stream(contactsFacilitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the contactsFacilities getting a facility.
     *
     *  @param facilityId
     *  @return the list of entities
     */
    public List<ContactsFacility> findAllByDelStatusIsFalseAndFacilityId(Long facilityId) {
        log.debug("Request to get all ContactsFacilities by facilityId");
        List<ContactsFacility> result = contactsFacilityRepository.findAllByDelStatusIsFalseAndFacilityId(facilityId);

        return result;
    }
}
