package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.VendorsService;
import co.tmunited.bluebook.domain.Vendors;
import co.tmunited.bluebook.repository.VendorsRepository;
import co.tmunited.bluebook.repository.search.VendorsSearchRepository;
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
 * Service Implementation for managing Vendors.
 */
@Service
@Transactional
public class VendorsServiceImpl implements VendorsService{

    private final Logger log = LoggerFactory.getLogger(VendorsServiceImpl.class);
    
    @Inject
    private VendorsRepository vendorsRepository;

    @Inject
    private VendorsSearchRepository vendorsSearchRepository;

    /**
     * Save a vendors.
     *
     * @param vendors the entity to save
     * @return the persisted entity
     */
    public Vendors save(Vendors vendors) {
        log.debug("Request to save Vendors : {}", vendors);
        Vendors result = vendorsRepository.save(vendors);
        vendorsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the vendors.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Vendors> findAll() {
        log.debug("Request to get all Vendors");
        List<Vendors> result = vendorsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one vendors by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Vendors findOne(Long id) {
        log.debug("Request to get Vendors : {}", id);
        Vendors vendors = vendorsRepository.findOne(id);
        return vendors;
    }

    /**
     *  Delete the  vendors by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Vendors : {}", id);
      Vendors vendors = vendorsRepository.findOne(id);
      vendors.setDelStatus(true);
      Vendors result = vendorsRepository.save(vendors);
      
      vendorsSearchRepository.save(result);
    }

    /**
     * Search for the vendors corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Vendors> search(String query) {
        log.debug("Request to search Vendors for query {}", query);
        return StreamSupport
            .stream(vendorsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
