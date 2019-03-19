package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.OrderComponentService;
import co.tmunited.bluebook.domain.OrderComponent;
import co.tmunited.bluebook.repository.OrderComponentRepository;
import co.tmunited.bluebook.repository.search.OrderComponentSearchRepository;
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
 * Service Implementation for managing OrderComponent.
 */
@Service
@Transactional
public class OrderComponentServiceImpl implements OrderComponentService{

    private final Logger log = LoggerFactory.getLogger(OrderComponentServiceImpl.class);

    @Inject
    private OrderComponentRepository orderComponentRepository;

    @Inject
    private OrderComponentSearchRepository orderComponentSearchRepository;

    /**
     * Save a orderComponent.
     *
     * @param orderComponent the entity to save
     * @return the persisted entity
     */
    public OrderComponent save(OrderComponent orderComponent) {
        log.debug("Request to save OrderComponent : {}", orderComponent);
        OrderComponent result = orderComponentRepository.save(orderComponent);
        orderComponentSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the orderComponents.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrderComponent> findAll() {
        log.debug("Request to get all OrderComponents");
        List<OrderComponent> result = orderComponentRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one orderComponent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public OrderComponent findOne(Long id) {
        log.debug("Request to get OrderComponent : {}", id);
        OrderComponent orderComponent = orderComponentRepository.findOne(id);
        return orderComponent;
    }

    /**
     *  Delete the  orderComponent by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderComponent : {}", id);
      OrderComponent orderComponent = orderComponentRepository.findOne(id);
      orderComponent.setDelStatus(true);
      OrderComponent result = orderComponentRepository.save(orderComponent);

      orderComponentSearchRepository.save(result);
    }

    /**
     * Search for the orderComponent corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrderComponent> search(String query) {
        log.debug("Request to search OrderComponents for query {}", query);
        return StreamSupport
            .stream(orderComponentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the orderComponents filtered by AllOrders.
     *
     *  @return the list of entities
     */
    @Override
    public List<OrderComponent> findAllByAllOrders(Long id) {
        log.debug("Request to get all OrderComponents");
        List<OrderComponent> result = orderComponentRepository.findAllByDelStatusIsFalse();

        return result;
    }
}
