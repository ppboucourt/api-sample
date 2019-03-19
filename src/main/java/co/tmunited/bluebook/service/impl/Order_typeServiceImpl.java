package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.Order_typeService;
import co.tmunited.bluebook.domain.Order_type;
import co.tmunited.bluebook.repository.Order_typeRepository;
import co.tmunited.bluebook.repository.search.Order_typeSearchRepository;
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
 * Service Implementation for managing Order_type.
 */
@Service
@Transactional
public class Order_typeServiceImpl implements Order_typeService{

    private final Logger log = LoggerFactory.getLogger(Order_typeServiceImpl.class);
    
    @Inject
    private Order_typeRepository order_typeRepository;

    @Inject
    private Order_typeSearchRepository order_typeSearchRepository;

    /**
     * Save a order_type.
     *
     * @param order_type the entity to save
     * @return the persisted entity
     */
    public Order_type save(Order_type order_type) {
        log.debug("Request to save Order_type : {}", order_type);
        Order_type result = order_typeRepository.save(order_type);
        order_typeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the order_types.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Order_type> findAll() {
        log.debug("Request to get all Order_types");
        List<Order_type> result = order_typeRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one order_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Order_type findOne(Long id) {
        log.debug("Request to get Order_type : {}", id);
        Order_type order_type = order_typeRepository.findOne(id);
        return order_type;
    }

    /**
     *  Delete the  order_type by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Order_type : {}", id);
      Order_type order_type = order_typeRepository.findOne(id);
      order_type.setDelStatus(true);
      Order_type result = order_typeRepository.save(order_type);
      
      order_typeSearchRepository.save(result);
    }

    /**
     * Search for the order_type corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Order_type> search(String query) {
        log.debug("Request to search Order_types for query {}", query);
        return StreamSupport
            .stream(order_typeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
