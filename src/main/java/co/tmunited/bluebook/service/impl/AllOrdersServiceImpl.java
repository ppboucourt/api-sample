package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.service.AllOrdersService;
import co.tmunited.bluebook.domain.AllOrders;
import co.tmunited.bluebook.repository.AllOrdersRepository;
import co.tmunited.bluebook.repository.search.AllOrdersSearchRepository;
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
 * Service Implementation for managing AllOrders.
 */
@Service
@Transactional
public class AllOrdersServiceImpl implements AllOrdersService{

    private final Logger log = LoggerFactory.getLogger(AllOrdersServiceImpl.class);

    @Inject
    private AllOrdersRepository allOrdersRepository;

    @Inject
    private AllOrdersSearchRepository allOrdersSearchRepository;

    /**
     * Save a allOrders.
     *
     * @param allOrders the entity to save
     * @return the persisted entity
     */
    public AllOrders save(AllOrders allOrders) {
        log.debug("Request to save AllOrders : {}", allOrders);

        allOrders.getOrderComponents().stream().map(x -> {
            x.setAllOrders(allOrders);
            return x;
        }).collect(Collectors.toList());

        AllOrders result = allOrdersRepository.save(allOrders);
        allOrdersSearchRepository.save(result);

        return result;
    }

    /**
     *  Get all the allOrders.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AllOrders> findAll() {
        log.debug("Request to get all AllOrders");
        List<AllOrders> result = allOrdersRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one allOrders by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AllOrders findOne(Long id) {
        log.debug("Request to get AllOrders : {}", id);
        AllOrders allOrders = allOrdersRepository.findOne(id);
        allOrders.getOrderComponents().size();
        return allOrders;
    }

    /**
     *  Delete the  allOrders by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AllOrders : {}", id);
      AllOrders allOrders = allOrdersRepository.findOne(id);
      allOrders.setDelStatus(true);
      AllOrders result = allOrdersRepository.save(allOrders);

      allOrdersSearchRepository.save(result);
    }

    /**
     * Search for the allOrders corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AllOrders> search(String query) {
        log.debug("Request to search AllOrders for query {}", query);
        return StreamSupport
            .stream(allOrdersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
