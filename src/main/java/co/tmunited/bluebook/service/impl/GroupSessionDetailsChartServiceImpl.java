package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.ChartToGroupSession;
import co.tmunited.bluebook.domain.vo.GroupSessionDetailsChartVO;
import co.tmunited.bluebook.service.GroupSessionDetailsChartService;
import co.tmunited.bluebook.domain.GroupSessionDetailsChart;
import co.tmunited.bluebook.repository.GroupSessionDetailsChartRepository;
import co.tmunited.bluebook.repository.search.GroupSessionDetailsChartSearchRepository;
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
 * Service Implementation for managing GroupSessionDetailsChart.
 */
@Service
@Transactional
public class GroupSessionDetailsChartServiceImpl implements GroupSessionDetailsChartService{

    private final Logger log = LoggerFactory.getLogger(GroupSessionDetailsChartServiceImpl.class);

    @Inject
    private GroupSessionDetailsChartRepository groupSessionDetailsChartRepository;

    @Inject
    private GroupSessionDetailsChartSearchRepository groupSessionDetailsChartSearchRepository;

    /**
     * Save a groupSessionDetailsChart.
     *
     * @param groupSessionDetailsChart the entity to save
     * @return the persisted entity
     */
    public GroupSessionDetailsChart save(GroupSessionDetailsChart groupSessionDetailsChart) {
        log.debug("Request to save GroupSessionDetailsChart : {}", groupSessionDetailsChart);

        if(groupSessionDetailsChart.getId()!=null && groupSessionDetailsChart.getGroupSessionDetails()==null){
            GroupSessionDetailsChart fdb = groupSessionDetailsChartRepository.getOne(groupSessionDetailsChart.getId());
            groupSessionDetailsChart.groupSessionDetails(fdb.getGroupSessionDetails()).chart(fdb.getChart());
        }

        GroupSessionDetailsChart result = groupSessionDetailsChartRepository.save(groupSessionDetailsChart);
        groupSessionDetailsChartSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the groupSessionDetailsCharts.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GroupSessionDetailsChart> findAll() {
        log.debug("Request to get all GroupSessionDetailsCharts");
        List<GroupSessionDetailsChart> result = groupSessionDetailsChartRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     *  Get one groupSessionDetailsChart by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GroupSessionDetailsChart findOne(Long id) {
        log.debug("Request to get GroupSessionDetailsChart : {}", id);
        GroupSessionDetailsChart groupSessionDetailsChart = groupSessionDetailsChartRepository.findOne(id);
        return groupSessionDetailsChart;
    }

    /**
     *  Delete the  groupSessionDetailsChart by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupSessionDetailsChart : {}", id);
      GroupSessionDetailsChart groupSessionDetailsChart = groupSessionDetailsChartRepository.findOne(id);
      groupSessionDetailsChart.setDelStatus(true);
      GroupSessionDetailsChart result = groupSessionDetailsChartRepository.save(groupSessionDetailsChart);

      groupSessionDetailsChartSearchRepository.save(result);
    }

    /**
     * Search for the groupSessionDetailsChart corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GroupSessionDetailsChart> search(String query) {
        log.debug("Request to search GroupSessionDetailsCharts for query {}", query);
        return StreamSupport
            .stream(groupSessionDetailsChartSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public List<GroupSessionDetailsChart> findAllGroupSessionByPatientInCurrentFacility(Long gsId) {
        log.debug("Request to search ChartToGroupSessions filtered by GroupSessionId");
        return groupSessionDetailsChartRepository.findAllGroupSessionDetailsByPatientInCurrentFacility(gsId);

    }

    @Override
    public  List<GroupSessionDetailsChart> getGroupSessionDetailsChartByChart(Long chartId){
       return  groupSessionDetailsChartRepository.findAllGroupSessionDetailsChartByChartId(chartId);
    }

    @Override
    public  List<GroupSessionDetailsChartVO> findAllByDelStatusIsFalseAndChartId(Long chartId){
       return  groupSessionDetailsChartRepository.findAllByDelStatusIsFalseAndChartId(chartId);
    }


}
