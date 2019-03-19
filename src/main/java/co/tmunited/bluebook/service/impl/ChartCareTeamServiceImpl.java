package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.ChartCareTeam;
import co.tmunited.bluebook.domain.vo.ChartCareTeamVO;
import co.tmunited.bluebook.repository.ChartCareTeamRepository;
import co.tmunited.bluebook.repository.search.ChartCareTeamSearchRepository;
import co.tmunited.bluebook.service.ChartCareTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ChartCareTeam.
 */
@Service
@Transactional
public class ChartCareTeamServiceImpl implements ChartCareTeamService {

    private final Logger log = LoggerFactory.getLogger(ChartCareTeamServiceImpl.class);

    @Inject
    private ChartCareTeamRepository chartCareTeamRepository;

    @Inject
    private ChartCareTeamSearchRepository chartCareTeamSearchRepository;

    /**
     * Save a ChartCareTeam.
     *
     * @param ChartCareTeam the entity to save
     * @return the persisted entity
     */
    public ChartCareTeam save(ChartCareTeam ChartCareTeam) {
        log.debug("Request to save ChartCareTeam : {}", ChartCareTeam);
        ChartCareTeam result = chartCareTeamRepository.save(ChartCareTeam);
        chartCareTeamSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the ChartCareTeams.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChartCareTeam> findAll() {
        log.debug("Request to get all ChartCareTeams");
        List<ChartCareTeam> result = chartCareTeamRepository.findAllByDelStatusIsFalse();
        return result;
    }

    /**
     * Get one ChartCareTeam by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ChartCareTeam findOne(Long id) {
        log.debug("Request to get ChartCareTeam : {}", id);
        ChartCareTeam ChartCareTeam = chartCareTeamRepository.findOne(id);
        return ChartCareTeam;
    }

    /**
     * Delete the  ChartCareTeam by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChartCareTeam : {}", id);
        ChartCareTeam ChartCareTeam = chartCareTeamRepository.findOne(id);
        ChartCareTeam.setDelStatus(true);
        ChartCareTeam result = chartCareTeamRepository.save(ChartCareTeam);

        chartCareTeamSearchRepository.save(result);
    }

//    /**
//     * Search for the ChartCareTeam corresponding to the query.
//     *
//     *  @param query the query of the search
//     *  @return the list of entities
//     */
//    @Transactional(readOnly = true)
//    public List<ChartCareTeam> search(String query) {
//        log.debug("Request to search ChartCareTeams for query {}", query);
//        return StreamSupport
//            .stream(chartCareTeamSearchRepository.search(queryStringQuery(query)).spliterator(), false)
//            .collect(Collectors.toList());
//    }

    @Override
    public List<ChartCareTeam> findChartCareTeamByChart(Long id) {
        log.debug("Request to find ChartCareTeam By Chart Id : {}", id);
        List<ChartCareTeam> chartCareTeams = chartCareTeamRepository.findByChartIdAndDelStatusIsFalse(id);
//        chartCareTeams.stream().map(x -> {
//            x.getEmployee().getId();
//            return x;
//        }).collect(Collectors.toList());
        return chartCareTeams;
    }

    @Override
    public List<ChartCareTeam> findChartCareTeamByEmployee(Long id) {
        log.debug("Request to find ChartCareTeam By Employee Id : {}", id);
        List<ChartCareTeam> chartCareTeams = chartCareTeamRepository.findAllByEmployeeId(id);
        return chartCareTeams;
    }

    @Override
    public List<ChartCareTeamVO> findChartCareTeamVOByChart(Long id) {
        log.debug("Request to find ChartCareTeamVO By Chart Id : {}", id);
        List<ChartCareTeamVO> chartCareTeams = chartCareTeamRepository.findAllVOByChartId(id);
        return chartCareTeams;
    }


}
