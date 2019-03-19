package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.domain.vo.GroupSessionDetailsChartVO;
import co.tmunited.bluebook.repository.*;
import co.tmunited.bluebook.repository.search.GroupSessionSearchRepository;
import co.tmunited.bluebook.service.GroupSessionDetailsService;
import co.tmunited.bluebook.repository.search.GroupSessionDetailsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GroupSessionDetails.
 */
@Service
@Transactional
public class GroupSessionDetailsServiceImpl implements GroupSessionDetailsService {

    public static final String GROUP_SESSION_STATE_INA = "INA";
    private final Logger log = LoggerFactory.getLogger(GroupSessionDetailsServiceImpl.class);


    @Inject
    private GroupSessionRepository groupSessionRepository;

    @Inject
    private GroupSessionSearchRepository groupSessionSearchRepository;

    @Inject
    private GroupSessionDetailsRepository groupSessionDetailsRepository;

    @Inject
    private GroupSessionDetailsSearchRepository groupSessionDetailsSearchRepository;

    @Inject
    private SignatureRepository signatureRepository;

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private GroupSessionDetailsChartRepository groupSessionDetailsChartRepository;

    /**
     * Save a groupSessionDetails.
     *
     * @param groupSessionDetails the entity to save
     * @return the persisted entity
     */
    public GroupSessionDetails save(GroupSessionDetails groupSessionDetails) {
        log.debug("Request to save GroupSessionDetails : {}", groupSessionDetails);

        Set<GroupSessionDetailsChart> groupSessionDetailsCharts = groupSessionDetails.getGroupSessionDetailsCharts();

        if(groupSessionDetailsCharts != null && groupSessionDetailsCharts.size() > 0){
            groupSessionDetails.getGroupSessionDetailsCharts().stream().map(x -> {
                x.setGroupSessionDetails(groupSessionDetails);
                return x;
            }).collect(Collectors.toList());
        }

//        if (groupSessionDetails.getGroupSession().getStatus() == null) {
//            groupSessionDetails.getGroupSession().setStatus(GROUP_SESSION_STATE_INA);
//        }

        GroupSession groupSession = groupSessionRepository.save(groupSessionDetails.getGroupSession());
        groupSessionSearchRepository.save(groupSession);

        groupSessionDetails.setGroupSession(groupSession);

        GroupSessionDetails result = groupSessionDetailsRepository.save(groupSessionDetails);
        groupSessionDetailsSearchRepository.save(result);
        return result;
    }


    /**
     * Update a groupSessionDetails.
     *
     * @param groupSessionDetails the entity to update
     * @param ip                  from request machine
     * @return the persisted entity
     */
    public GroupSessionDetails update(GroupSessionDetails groupSessionDetails, String ip) {
        log.debug("Request to save GroupSessionDetails : {}", groupSessionDetails);

        Set<GroupSessionDetailsChart> groupSessionDetailsCharts = groupSessionDetails.getGroupSessionDetailsCharts();

        if(groupSessionDetailsCharts != null && groupSessionDetailsCharts.size() > 0){
            groupSessionDetails.getGroupSessionDetailsCharts().stream().map(x -> {
                x.setGroupSessionDetails(groupSessionDetails);
                return x;
            }).collect(Collectors.toList());
        }

        //Analise LeaderSignature
        if (groupSessionDetails.getLeaderSignature() != null && groupSessionDetails.getLeaderSignature().getId() == null) { //If
            groupSessionDetails.getLeaderSignature().setIp(ip);
            Signature sign = signatureRepository.save(groupSessionDetails.getLeaderSignature());
            groupSessionDetails.setLeaderSignature(sign);
        }

        //Analise ReviewSignature
        if (groupSessionDetails.getReviserSignature() != null && groupSessionDetails.getReviserSignature().getId() == null) { //If
            groupSessionDetails.getReviserSignature().setIp(ip);
            Signature sign = signatureRepository.save(groupSessionDetails.getReviserSignature());
            groupSessionDetails.setReviserSignature(sign);
        }

//        //If
//        if (groupSessionDetails.getGroupSession().getStatus() == null) {
//            groupSessionDetails.getGroupSession().setStatus(GROUP_SESSION_STATE_INA);
//        }

        GroupSession groupSession = groupSessionRepository.save(groupSessionDetails.getGroupSession());
        groupSessionSearchRepository.save(groupSession);
        groupSessionDetails.setGroupSession(groupSession);

        GroupSessionDetails result = groupSessionDetailsRepository.save(groupSessionDetails);
        groupSessionDetailsSearchRepository.save(result);
        return result;
    }


    /**
     * Get all the groupSessionDetails.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GroupSessionDetails> findAll() {
        log.debug("Request to get all GroupSessionDetails");
        List<GroupSessionDetails> result = groupSessionDetailsRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one groupSessionDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public GroupSessionDetails findOne(Long id) {
        log.debug("Request to get GroupSessionDetails : {}", id);
        GroupSessionDetails groupSessionDetails = groupSessionDetailsRepository.findOne(id);

        return groupSessionDetails;
    }

    /**
     * Delete the  groupSessionDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupSessionDetails : {}", id);
        GroupSessionDetails groupSessionDetails = groupSessionDetailsRepository.findOne(id);
        groupSessionDetails.setDelStatus(true);
        GroupSessionDetails result = groupSessionDetailsRepository.save(groupSessionDetails);

        groupSessionDetailsSearchRepository.save(result);
    }

    /**
     * Search for the groupSessionDetails corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GroupSessionDetails> search(String query) {
        log.debug("Request to search GroupSessionDetails for query {}", query);
        return StreamSupport
            .stream(groupSessionDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupSessionDetails> searchByDate(Long id, Date dateTime) {

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault());

        ZonedDateTime startDateTime = ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth(), 0, 0, 0, 0, zonedDateTime.getZone());
        ZonedDateTime endDateTime = ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth(), 23, 59, 59, 0, zonedDateTime.getZone());

        log.debug("Request to search searchByDate dateTime : {}", dateTime.toString());
        log.debug("Request to search searchByDate start : {}", startDateTime.toString());
        log.debug("Request to search searchByDate end : {}", endDateTime.toString());

        List<GroupSessionDetails> groupSessionDetails = groupSessionDetailsRepository.findAllByDelStatusIsFalseAndGroupSessionFacilityIdAndStartBetween(id, startDateTime, endDateTime);
        return groupSessionDetails;
    }


    @Transactional
    public GroupSessionDetails assignChartsToGroupSessionDetails(Long id, Set<Long> listCharts) {

        log.debug("Request to assignChartsToGroupSessionDetails : {}", id);

        // List for set Chart to GroupSessionDetails
        List<GroupSessionDetailsChart> groupSessionDetailsChartsList = new ArrayList<>();

        //Recovery GroupSessionDetails
        GroupSessionDetails groupSessionDetails = groupSessionDetailsRepository.findOne(id);
        groupSessionDetails.getGroupSessionDetailsCharts().size();

        //Build each chart, save and  add to list
        for (Long cId : listCharts) {
            Chart chart = chartRepository.findOne(cId); //Recovery chart

            GroupSessionDetailsChart tmpGroupSessionDetailsChart = new GroupSessionDetailsChart();
            tmpGroupSessionDetailsChart.chart(chart).groupSessionDetails(groupSessionDetails);//Set chart and gsd

            groupSessionDetailsChartsList.add(groupSessionDetailsChartRepository.save(tmpGroupSessionDetailsChart));//save and add
        }

        groupSessionDetails.getGroupSessionDetailsCharts().addAll(groupSessionDetailsChartsList);
        groupSessionDetails = groupSessionDetailsRepository.save(groupSessionDetails);

       // groupSessionDetails.getGroupSessionDetailsCharts().size();
        return groupSessionDetails;
    }


    /**
     * Get list of Chart in groupSessionDetails by id.
     *
     * @param id the id of the entity
     * @return the ;list
     */
    @Transactional(readOnly = true)
    public List<GroupSessionDetailsChartVO> findChartList(Long id) {
        log.debug("Request to get GroupSessionDetails : {}", id);

        List<GroupSessionDetailsChartVO> groupSessionDetailsCharts = groupSessionDetailsChartRepository.findAllByDelStatusIsFalseAndGroupSessionDetailsId(id);

        return groupSessionDetailsCharts;
    }

}
