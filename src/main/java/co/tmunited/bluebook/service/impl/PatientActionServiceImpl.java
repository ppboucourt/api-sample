package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.domain.enumeration.PatientActionStatus;
import co.tmunited.bluebook.domain.enumeration.PatientActionTakeStatus;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientActionTakeVO;
import co.tmunited.bluebook.domain.vo.PatientActionVO;
import co.tmunited.bluebook.repository.PatientActionPreRepository;
import co.tmunited.bluebook.repository.PatientActionRepository;
import co.tmunited.bluebook.repository.PatientActionTakeRepository;
import co.tmunited.bluebook.repository.search.PatientActionSearchRepository;
import co.tmunited.bluebook.repository.search.PatientActionTakeSearchRepository;
import co.tmunited.bluebook.service.PatientActionPreService;
import co.tmunited.bluebook.service.PatientActionService;
import co.tmunited.bluebook.service.PatientActionTakeService;
import co.tmunited.bluebook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.time.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing PatientAction.
 */
@Service
@Transactional
public class PatientActionServiceImpl implements PatientActionService {

    private final Logger log = LoggerFactory.getLogger(PatientActionServiceImpl.class);

    @Inject
    private PatientActionRepository patientActionRepository;

    @Inject
    private PatientActionSearchRepository patientActionSearchRepository;

    @Inject
    private PatientActionPreService patientActionPreService;

    /**
     * Save a patientAction.
     *
     * @param patientAction the entity to save
     * @return the persisted entity
     */
    public PatientAction save(PatientAction patientAction) {
        log.debug("Request to save PatientAction : {}", patientAction);

        if (patientAction.getId() == null) {
            patientAction.getPatientActionPres().stream().map(pre -> {
                pre.setPatientAction(patientAction);
                pre.getPatientActionTakes().stream().map(item -> {
                    item.setPatientActionPre(pre);
                    item.setActionTakeStatus(PatientActionTakeStatus.SCHEDULED);

                    return item;
                }).collect(Collectors.toList());

                return pre;
            }).collect(Collectors.toList());
        }


        if (!CollectionUtils.isEmpty(patientAction.getPatientActionPres())) {
            patientAction.getPatientActionPres().stream().forEach(pp -> {
                pp.setPatientAction(patientAction);
                pp.setPatientActionId(patientAction.getId());

                if (!CollectionUtils.isEmpty(pp.getPatientActionTakes())) {

                    PatientActionPre patientActionPre = patientActionPreService.save(pp);

                    pp.getPatientActionTakes().stream().forEach(ppt -> {
                        ppt.setPatientActionPreId(patientActionPre.getId());
//                        ppt.setScheduleDate(patientActionPre.getStaringDate());
//                        ppt.setCollectedDate(LocalDateTime.now());
                        patientActionTakeService.save(ppt);
                    });
                }

            });
        }

        PatientAction result = patientActionRepository.save(patientAction);
        patientActionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the patientActions.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientAction> findAll() {
        log.debug("Request to get all PatientActions");
        List<PatientAction> result = patientActionRepository.findAll();

        return result;
    }

    /**
     * Get one patientAction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PatientAction findOne(Long id) {
        log.debug("Request to get PatientAction : {}", id);
        PatientAction patientAction = patientActionRepository.findOne(id);
        return patientAction;
    }

    /**
     * Delete the  patientAction by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientAction : {}", id);
        patientActionRepository.delete(id);
        patientActionSearchRepository.delete(id);

        PatientAction patientAction = patientActionRepository.findOne(id);
        patientAction.setDelStatus(true);
        PatientAction result = patientActionRepository.save(patientAction);

        patientActionSearchRepository.save(result);
    }

    /**
     * Search for the patientAction corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientAction> search(String query) {
        log.debug("Request to search PatientActions for query {}", query);
        return StreamSupport
            .stream(patientActionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @Override
    public List<PatientAction> findAllByChart(Long id) {
        return patientActionRepository.findAllByChartId(id);
    }

    @Override
    public PatientAction schedulePatientAction(PatientAction patientAction) {
//        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime now = ZonedDateTime.now();

        for (PatientActionPre test : patientAction.getPatientActionPres()) {
            test.getPatientActionTakes().clear();

            if (!test.getAsNeeded()) {
                if (test.getStaringDate().compareTo(now) >= 0) {
                    if (test.getEndDate().compareTo(test.getStaringDate()) >= 0) {
                        test.setPatientActionTakes(getScheduled(test).getPatientActionTakes());
                    }
                }
            }
        }

        return patientAction;
    }

    private PatientActionPre getScheduled(PatientActionPre test) {
        int i = 0;
        PatientActionTake item = null;
//        LocalDateTime plusDay = test.getStaringDate().plusHours(test.getHours() * i);
        ZonedDateTime plusDay = test.getStaringDate().plusHours(test.getHours() * i);
        while (plusDay.withZoneSameInstant(ZoneId.of("UTC")).compareTo(test.getEndDate()) < 0) {
            item = new PatientActionTake();
            item.setPatientActionPre(test);
            item.setScheduleDate(plusDay);
            item.setCollectedDate(plusDay);
            test.addPatientActionTake(item);

            i++;
            plusDay = test.getStaringDate().plusHours(test.getHours() * i);
        }
//        test.setEndDate(item.getScheduleDate());

        test.setPatientActionTakes(test.getPatientActionTakes().stream().sorted(Comparator.comparing(PatientActionTake::getScheduleDate))
            .collect(Collectors.toCollection(TreeSet::new)));
        return test;
    }

    @Inject
    PatientActionTakeRepository patientActionTakeRepository;

    @Inject
    PatientActionTakeService patientActionTakeService;

    @Override
    public List<PatientActionTake> findAllActionsByDate(Long id, LocalDate date) {
        return patientActionTakeRepository.findAllActionsByDate(id, date.atStartOfDay(), date.atTime(23, 59, 59)).stream().map(
            take -> {
                take.setAction(take.getPatientActionPre().getAction());
                take.setActionId(take.getPatientActionPre().getPatientAction().getId());

                return take;
            }
        ).collect(Collectors.toList());
    }

    @Override
    public List<PatientActionTakeVO> findAllActionsByDateVO(Long id, LocalDate date) {
        return patientActionTakeRepository.findAllActionsByDateVO(id, date.atStartOfDay(ZoneOffset.UTC),
            ZonedDateTime.of(date.atTime(23, 59, 59), ZoneOffset.UTC));

    }

    @Override
    public void take(CollectedBody collectedBody) {
        ZonedDateTime collected = ZonedDateTime.now();
        collectedBody.getIds().stream().forEach(
            order -> {
                PatientActionTake action = patientActionTakeRepository.findOne(Long.valueOf(order));

                if (action.getPatientActionPre().getPatientAction().getActionStatus() == PatientActionStatus.SCHEDULED) {
                    action.setCollected(true);
                    action.setCollectedDate(collected);

                    patientActionTakeRepository.save(action);
                }
            }
        );
    }

    @Inject
    PatientActionPreRepository patientActionPreRepository;

    @Override
    public List<PatientActionPre> findAllActionsAsNeededByDate(long id) {
        return patientActionPreRepository.findAllActionsAsNeededByDate(id);
    }

    @Inject
    UserService userService;

    @Inject
    PatientActionTakeSearchRepository patientActionTakeSearchRepository;

    @Override
    public void addPatientActionTakeAsNeeded(long id, ZonedDateTime date) {
        Employee employee = userService.getEmployeeWithAuthorities();

        PatientActionPre presc = patientActionPreRepository.findOne(id);

        if (presc != null) {
            PatientActionTake take = new PatientActionTake();
            take.setPatientActionPre(presc);
            take.setPatientActionPreId(presc.getId());
            take.setAdministerBy(employee);
            take.setCollected(true);
            take.setActionTakeStatus(PatientActionTakeStatus.OCCURRED);
            take.setCanceled(false);
            take.setScheduleDate(date);
            take.setCollectedDate(date);

            patientActionTakeRepository.save(take);
            patientActionTakeSearchRepository.save(take);
        }
    }

    @Transactional(readOnly = true)
    public List<PatientAction> getUnsignedActions(Long id) {
        Employee employee = userService.getEmployeeWithAuthorities();

        if (employee.getUser().getAuthorities().stream().anyMatch(
            aut ->
                aut.getName().equalsIgnoreCase("ROLE_CLINICAL_DIRECTOR") ||
                    aut.getName().equalsIgnoreCase("ROLE_MD") ||
                    aut.getName().equalsIgnoreCase("ROLE_PHYSICIAN_ASSISTANCE")
        )) {
            return patientActionRepository.findAllBySignedIsFalseAndDelStatusIsFalseAndSignedById(id, employee.getId());
        }

        return new ArrayList<>();
    }

    @Override
    public List<PatientAction> getAllUnsignedActions(Long id) {
        return patientActionRepository.findAllBySignedIsFalseAndDelStatusIsFalse(id);
    }

    @Override
    @Transactional
    public void signActions(CollectedBody collectedBody) {
        Employee employee = userService.getEmployeeWithAuthorities();
        if (employee.getUser().getAuthorities().stream().anyMatch(
            aut ->
                aut.getName().equalsIgnoreCase("ROLE_CLINICAL_DIRECTOR") ||
                    aut.getName().equalsIgnoreCase("ROLE_MD") ||
                    aut.getName().equalsIgnoreCase("ROLE_PHYSICIAN_ASSISTANCE")
        )) {
            collectedBody.getIds().stream().forEach(
                actionId -> {
                    PatientAction action = findOne(actionId);
                    if (action.getSignedBy().getId() == employee.getId()) {
                        action.setSigned(true);
                        action.setSignatureDate(LocalDate.now());

                        save(action);
                    }
                }
            );
        }
    }

    @Transactional
    public PatientAction cancelPatientAction(@Param("id") Long id) {
        PatientAction patientAction = patientActionRepository.findOne(id);

        try {
            if (patientAction != null) {

                if (!CollectionUtils.isEmpty(patientAction.getPatientActionPres())) {
                    patientAction.getPatientActionPres().stream().map(press -> {

                        if (CollectionUtils.isEmpty(press.getPatientActionTakes())) {
                            press.getPatientActionTakes().stream().map(take -> {
                                take.setCanceled(true);
                                return take;
                            }).collect(Collectors.toList());
                        }

                        return press;
                    }).collect(Collectors.toList());

                    patientAction.setActionStatus(PatientActionStatus.CANCELED);
                    patientAction.setEndDate(LocalDate.now());
                }

                return patientAction;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new PatientAction();
        }
        return new PatientAction();
    }


    @Override
    public List<PatientActionVO> getAllPatientActionByChartVO(Long chartId) {
        return patientActionRepository.getAllPatientActionByChartVO(chartId);
    }

    public PatientActionRepository getPatientActionRepository() {
        return patientActionRepository;
    }

    public void setPatientActionRepository(PatientActionRepository patientActionRepository) {
        this.patientActionRepository = patientActionRepository;
    }

    public PatientActionSearchRepository getPatientActionSearchRepository() {
        return patientActionSearchRepository;
    }

    public void setPatientActionSearchRepository(PatientActionSearchRepository patientActionSearchRepository) {
        this.patientActionSearchRepository = patientActionSearchRepository;
    }

    public PatientActionPreService getPatientActionPreService() {
        return patientActionPreService;
    }

    public void setPatientActionPreService(PatientActionPreService patientActionPreService) {
        this.patientActionPreService = patientActionPreService;
    }

    public PatientActionTakeRepository getPatientActionTakeRepository() {
        return patientActionTakeRepository;
    }

    public void setPatientActionTakeRepository(PatientActionTakeRepository patientActionTakeRepository) {
        this.patientActionTakeRepository = patientActionTakeRepository;
    }

    public PatientActionPreRepository getPatientActionPreRepository() {
        return patientActionPreRepository;
    }

    public void setPatientActionPreRepository(PatientActionPreRepository patientActionPreRepository) {
        this.patientActionPreRepository = patientActionPreRepository;
    }

    public PatientActionTakeSearchRepository getPatientActionTakeSearchRepository() {
        return patientActionTakeSearchRepository;
    }

    public void setPatientActionTakeSearchRepository(PatientActionTakeSearchRepository patientActionTakeSearchRepository) {
        this.patientActionTakeSearchRepository = patientActionTakeSearchRepository;
    }
}
