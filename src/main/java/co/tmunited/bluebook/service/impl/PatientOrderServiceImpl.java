package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.domain.enumeration.OrderStatus;
import co.tmunited.bluebook.domain.util.CollectedBody;
import co.tmunited.bluebook.domain.vo.PatientOrderVO;
import co.tmunited.bluebook.domain.vo.PatientScheduleDataVO;
import co.tmunited.bluebook.domain.vo.PrintBarcode;
import co.tmunited.bluebook.repository.BarCodeRepository;
import co.tmunited.bluebook.repository.ChartRepository;
import co.tmunited.bluebook.repository.Icd10Repository;
import co.tmunited.bluebook.repository.PatientOrderRepository;
import co.tmunited.bluebook.repository.search.PatientOrderSearchRepository;
import co.tmunited.bluebook.service.*;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing PatientOrder.
 */
@Service
@Transactional
public class PatientOrderServiceImpl implements PatientOrderService {

    private final Logger log = LoggerFactory.getLogger(PatientOrderServiceImpl.class);

    @Inject
    private PatientOrderRepository patientOrderRepository;

    @Inject
    private PatientOrderSearchRepository patientOrderSearchRepository;

    @Inject
    private BarCodeRepository barCodeRepository;

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private UserService userService;

    @Inject
    private PatientMedicationPresService patientMedicationPresService;

    @Inject
    private PatientOrderTestService patientOrderTestService;

    @Inject
    private PatientOrderItemService patientOrderItemService;

    @Inject
    private Icd10Repository icd10Repository;


    public PatientOrderTestService getPatientOrderTestService() {
        return patientOrderTestService;
    }

    public void setPatientOrderTestService(PatientOrderTestService patientOrderTestService) {
        this.patientOrderTestService = patientOrderTestService;
    }

    public PatientOrderItemService getPatientOrderItemService() {
        return patientOrderItemService;
    }

    public void setPatientOrderItemService(PatientOrderItemService patientOrderItemService) {
        this.patientOrderItemService = patientOrderItemService;
    }

    public Logger getLog() {
        return log;
    }

    /**
     * Save a patientOrder.
     *
     * @param patientOrder the entity to save
     * @return the persisted entity
     */
    public PatientOrder save(PatientOrder patientOrder) {
        log.debug("Request to save PatientOrder : {}", patientOrder);


        try {
            if (patientOrder.getId() == null) {
                log.info("Request to save PatientOrder");

                if (!CollectionUtils.isEmpty(patientOrder.getPatientOrderTests())) {
                    patientOrder.getPatientOrderTests().stream().map(test -> {
                        test.setPatientOrder(patientOrder);

                        if (!CollectionUtils.isEmpty(test.getPatientOrderItems())) {
                            test.getPatientOrderItems().stream().map(item -> {
                                item.setPatientOrderTest(test);

                                return item;
                            }).collect(Collectors.toList());
                        }

                        return test;
                    }).collect(Collectors.toList());
                }

            }


            if (!CollectionUtils.isEmpty(patientOrder.getPatientOrderTests())) {
                patientOrder.getPatientOrderTests().stream().forEach(pt -> {
                    pt.setPatientOrder(patientOrder);
                    pt.setPatientOrderId(patientOrder.getId());

                    if (!CollectionUtils.isEmpty(pt.getPatientOrderItems())) {

                        PatientOrderTest patientOrderTest = patientOrderTestService.save(pt);

                        pt.getPatientOrderItems().stream().forEach(pti -> {
                            pti.setPatientOrderTestId(patientOrderTest.getId());
                            patientOrderItemService.save(pti);
                        });
                    }

                });
            }
            //Save PressTake
            final PatientOrder result = patientOrderRepository.save(patientOrder);

            patientOrderSearchRepository.save(result);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PatientOrder();
    }

    /**
     * Get all the patientOrders.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientOrder> findAll() {
        log.debug("Request to get all PatientOrders");
        List<PatientOrder> result = patientOrderRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one patientOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PatientOrder findOne(Long id) {
        log.debug("Request to get PatientOrder : {}", id);
        PatientOrder patientOrder = patientOrderRepository.findOne(id);

        return patientOrder;
    }

    /**
     * Delete the  patientOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientOrder : {}", id);
        PatientOrder patientOrder = patientOrderRepository.findOne(id);
        patientOrder.setDelStatus(true);
        PatientOrder result = patientOrderRepository.save(patientOrder);

        patientOrderSearchRepository.save(result);
    }

    /**
     * Search for the patientOrder corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientOrder> search(String query) {
        log.debug("Request to search PatientOrders for query {}", query);
        return StreamSupport
            .stream(patientOrderSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Find all one time order by petient
     *
     * @param id patient id
     * @return
     */
    @Transactional(readOnly = true)
    public List<PatientOrder> findAllByChart(Long id) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime nextThreeWeek = now.plusDays(21);
        log.debug("Request to search findAllByChart for query {}", id);
        List<PatientOrder> patientOrders = patientOrderRepository.findAllByChartId(id);
        //Chart with MEdications
        Chart chart = chartRepository.findOneWithMedications(id);
        patientOrders.stream().forEach(patientOrder -> {
            patientOrder.setChart(chart);

        });
        log.debug("Request to search findAllByChart for query end {}", id);
        return patientOrders.stream().map(po -> {
            po.getSignedBy().getId();
            return po;
        }).collect(Collectors.toList());
    }

    /**
     * Save a patientOrder.
     *
     * @param id the PatientOrder id
     * @return the persisted entity
     */
    @Transactional
    public PatientOrder cancelPatientOrder(Long id) {
        log.info("Request to cancel PatientOrder : {}", id);

        PatientOrder patientOrder = patientOrderRepository.findOne(id);

        try {

            if (!CollectionUtils.isEmpty(patientOrder.getPatientOrderTests())) {
                patientOrder.getPatientOrderTests().stream().map(test -> {

                    if (!CollectionUtils.isEmpty(test.getPatientOrderItems())) {
                        test.getPatientOrderItems().stream().map(item -> {
                            if (!item.isCollected()) {
                                item.setCanceled(true);
                            }

                            return item;
                        }).collect(Collectors.toList());
                    }

                    return test;
                }).collect(Collectors.toList());
            }

            patientOrder.setOrdStatus(OrderStatus.CANCELED);
            patientOrder.setEndDate(LocalDate.now());

            PatientOrder result = patientOrderRepository.save(patientOrder);
            patientOrderSearchRepository.save(result);

            return result;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return new PatientOrder();
        }

    }

    @Transactional
    public PatientOrder finishPatientOrder(Long id) {
        log.debug("Request to finalize PatientOrder : {}", id);

        PatientOrder patientOrder = patientOrderRepository.findOne(id);
        LocalDate now = LocalDate.now();

        patientOrder.getPatientOrderTests().stream().map(test -> {
            test.setEndDate(now);
            test.getPatientOrderItems().stream().map(item -> {
                if (!item.isCollected()) {
                    item.setScheduleDate(now);
                }

                return item;
            }).collect(Collectors.toList());

            return test;
        }).collect(Collectors.toList());
        patientOrder.setOrdStatus(OrderStatus.FINISHED);
        patientOrder.setEndDate(now);

        PatientOrder result = patientOrderRepository.save(patientOrder);
        patientOrderSearchRepository.save(result);

        return result;
    }

    @Transactional(readOnly = true)
    public List<PatientOrder> findAllTodayOrders(Long id) {
        return patientOrderRepository.findAllTodayOrders(id, LocalDate.now());
    }

    @Override
    public List<PatientOrder> findAllOrdersSchedulesByDate(Long id, LocalDate date) {
        List<PatientOrder> orders = patientOrderRepository.findAllTodayOrders(id, date);
        return orders;
    }


    @Override
    public List<PatientScheduleDataVO> findAllPatientScheduleDataByFacility(Long id, LocalDate date) {

//        List<PatientScheduleDataVO> scheduleds = patientOrderRepository.findAllPatientScheduleDataByFacilityScheduledVO(id, date);
//        List<PatientScheduleDataVO> collecteds = patientOrderRepository.findAllPatientScheduleDataByFacilityCollectedVO(id, date);
//        scheduleds.addAll(collecteds);
//
//        Set<PatientScheduleDataVO> scheduleDataVOS = new HashSet<PatientScheduleDataVO>(scheduleds);

        return patientOrderRepository.findAllPatientScheduleDataByFacilityVO(id, date);
    }

    @Override
    public void collect(CollectedBody collectedBody) {
//        LocalDate collected = LocalDate.now();

        collectedBody.getIds().stream().forEach(
            order -> {
                PatientOrder porder = patientOrderRepository.findOne(Long.valueOf(order));

                if (porder.getOrdStatus() == OrderStatus.SCHEDULED) {
                    BarCode barCode = new BarCode();
                    barCodeRepository.save(barCode);

                    porder.getPatientOrderTests().stream().forEach(patientOrderTest ->
                            patientOrderTest.getPatientOrderItems().stream().forEach(
                                patientOrderItem -> {
                                    if (patientOrderItem.getScheduleDate().isEqual(collectedBody.getDate()) && !patientOrderItem.isCollected()) {
                                        patientOrderItem.setCollected(true);
//                                    patientOrderItem.setCollectedDate(collected);
                                        patientOrderItem.setBarCode(barCode);
                                    }
                                }
                            )
                    );

                    patientOrderRepository.save(porder);
                }
            }
        );
    }

    /*
    patientOrderRepository.collect(collectedBody.getIds(), collectedBody.getDate())
        .stream()
    .collect(Collectors.groupingBy(item -> item.getPatientOrderTest().getPatientOrder().getId()))
        .forEach((id, patientOrderItems) -> {
        BarCode barCode = new BarCode();
        barCodeRepository.save(barCode);

        patientOrderItems.stream().forEach(patientOrderItem -> {
            patientOrderItem.setCollected(true);
            patientOrderItem.setBarCode(barCode);
            patientOrderItemService.save(patientOrderItem);
        });
    });
    */

    @Override
    public PatientOrder schedulePatientOrder(PatientOrder patientOrder) {
        LocalDate now = LocalDate.now();

        for (PatientOrderTest test : patientOrder.getPatientOrderTests()) {
            test.getPatientOrderItems().clear();

//            if (test.getStaringDate().compareTo(now) >= 0) {
            if (test.getEndDate().compareTo(test.getStaringDate()) >= 0) {
                test.setPatientOrderItems(getScheduledItems(test, patientOrder).getPatientOrderItems());
            }
//            }
        }


        return patientOrder;
    }

    private PatientOrderTest getScheduledItems(PatientOrderTest test, PatientOrder order) {
        int i = 0;
        PatientOrderItem item = null;

        LocalDate plusDays = test.getStaringDate().plusDays(test.getOrderFrequency().getDays() * i);

        while (plusDays.compareTo(test.getEndDate()) <= 0) {
            item = new PatientOrderItem();
            item.setPatientOrderTest(test);
            item.setScheduleDate(plusDays);
            item.setCollectedDate(plusDays);
            test.addPatientOrderItem(item);

            i++;
            plusDays = test.getStaringDate().plusDays(test.getOrderFrequency().getDays() * i);
        }

        test.setEndDate(item.getScheduleDate());

        test.setPatientOrderItems( test.getPatientOrderItems().stream().sorted(Comparator.comparing(PatientOrderItem::getScheduleDate))
            .collect(Collectors.toCollection(TreeSet::new)));

        return test;
    }

    @Transactional
    public void checkOrderStatus(PatientOrder order) {
        boolean toFinish = order.getPatientOrderTests().stream().allMatch(patientOrderTest ->
            patientOrderTest.getPatientOrderItems().stream().allMatch(
                item -> item.isCollected() || item.isCanceled()
            )
        );

        if (toFinish) {
            order.setOrdStatus(OrderStatus.FINISHED);
        }

        patientOrderRepository.save(order);
    }

    @Override
    public Long countOrdersNotCollectedByDate(Long id, LocalDate date) {
        return patientOrderRepository.countOrdersNotCollectedByDate(id, date);
    }

    @Transactional
    public PatientOrder findOrderByBarcode(String barcode) {
        return patientOrderRepository.findOrderByBarcode(barcode);
    }

    @Transactional(readOnly = true)
    public List<PatientOrder> getUnsignedOrders(Long id) {
        Employee employee = userService.getEmployeeWithAuthorities();

        if (employee.getUser().getAuthorities().stream().anyMatch(
            aut ->
                aut.getName().equalsIgnoreCase("ROLE_CLINICAL_DIRECTOR") ||
                    aut.getName().equalsIgnoreCase("ROLE_MD") ||
                    aut.getName().equalsIgnoreCase("ROLE_PHYSICIAN_ASSISTANCE")
        )) {
            return patientOrderRepository.findAllBySignedIsFalseAndDelStatusIsFalseAndSignedById(id, employee.getId());
        }

        return new ArrayList<>();
    }

    @Override
    public List<PatientOrder> getAllUnsignedOrders(Long id) {
        List<PatientOrder> result = patientOrderRepository.findAllBySignedIsFalseAndDelStatusIsFalse(id);

        if (!CollectionUtils.isEmpty(result)) {
            result.stream().map(r -> {
                r.getChart().getPatient().getId();
                return r;
            }).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    @Transactional
    public void signOrders(CollectedBody collectedBody) {
        Employee employee = userService.getEmployeeWithAuthorities();
        if (employee.getUser().getAuthorities().stream().anyMatch(
            aut ->
                aut.getName().equalsIgnoreCase("ROLE_CLINICAL_DIRECTOR") ||
                    aut.getName().equalsIgnoreCase("ROLE_MD") ||
                    aut.getName().equalsIgnoreCase("ROLE_PHYSICIAN_ASSISTANCE")
        )) {
            collectedBody.getIds().stream().forEach(
                orderId -> {
                    PatientOrder order = findOne(orderId);
                    if (order.getSignedBy().getId() == employee.getId()) {
                        order.setSigned(true);
                        order.setSignatureDate(LocalDate.now());

                        save(order);
                    }
                }
            );
        }
    }


    /**
     * Get one patientOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PatientOrder findOneWithAll(Long id) {
        log.debug("Request to get PatientOrder findOneWithAll: {}", id);
        PatientOrder patientOrder = patientOrderRepository.findOne(id);
        patientOrder.getChart().getPatient();

        if (patientOrder.getSignedBy() != null) {
            patientOrder.getSignedBy().getId();
        }

        log.debug("findOneWithAll {}", patientOrder.getChart().getPatient().getId());

        if (patientOrder.getPatientOrderTests() != null) {

            Set<PatientOrderTest> patientOrderTests = patientOrder.getPatientOrderTests();
            patientOrderTests.stream().forEach(oTest -> {
                oTest.getLabCompendium();
            });
        }

        if (patientOrder.getChart().getInsurances() != null) {
            patientOrder.getChart().getInsurances().size();
        }

        return patientOrder;
    }


    @Override
    public void changeDrawDay(CollectedBody collectedBody) {
        collectedBody.getIds().stream().forEach(id -> {
            PatientOrder order = patientOrderRepository.findOne(id);

            if (!CollectionUtils.isEmpty(order.getPatientOrderTests())) {
                log.info("PatientOrderTests: " + order.getPatientOrderTests().size());
                order.getPatientOrderTests().stream().forEach(test -> {

                        if (!CollectionUtils.isEmpty(test.getPatientOrderItems())) {
                            log.info("PatientOrderItems: " + test.getPatientOrderItems().size());

                            test.getPatientOrderItems().stream().forEach(item -> {
                                log.info("getScheduleDate: " + item.getScheduleDate());
                                log.info("collectedBody.getDate(): " + collectedBody.getDate());


                                if (item.getScheduleDate().compareTo(collectedBody.getDate()) == 0) {
                                    log.info("ENCONTRADO: " + item.getId());

                                    item.setScheduleDate(collectedBody.getNewDate());
                                    item.setCollectedDate(collectedBody.getNewDate());
                                }
                            });
                        }
                    }

                );
            }


            order.updateStartEndDate();
            save(order);
        });
    }


    @Override
    public Employee getSignedBy(@PathVariable Long id) {

        PatientOrder po = patientOrderRepository.findOne(id);
        try {
            if (po != null && po.getSignedBy().getId() != null) {
                return po.getSignedBy();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Employee();
    }

    @Override
    public Set<PatientOrderTest> getPatientOrderTests(@PathVariable Long id) {
        return patientOrderRepository.findOne(id).getPatientOrderTests();
    }


    @Override
    public List<PrintBarcode> getBarcodes(CollectedBody collectedBody) {

        List<PrintBarcode> barcodes = new ArrayList<>();

        List<PrintBarcode> barcodesWithoutTubes = patientOrderRepository.printBarcodesWithoutTubes(new ArrayList<Long>(collectedBody.getIds()), collectedBody.getDate());
        List<PrintBarcode> barcodesWithTubes = patientOrderRepository.printBarcodesWithTubes(new ArrayList<Long>(collectedBody.getIds()), collectedBody.getDate());

        barcodes.addAll(barcodesWithoutTubes);
        barcodes.addAll(barcodesWithTubes);

        List<PrintBarcode> barcodesSorted = barcodes.stream().sorted((b1, b2) -> {
            return b1.getBarcode().compareTo(b2.getBarcode());
        }).collect(Collectors.toList());

        return barcodesSorted;
    }

    @Override
    public CollectedBody getBarcode(Long orderId, LocalDate date) {
        CollectedBody c = new CollectedBody();
        c.setBarcode(patientOrderRepository.getBarcode(orderId, date));

        return c;
    }


}

