package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.config.Constants;
import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.domain.enumeration.EvaluationStatus;
import co.tmunited.bluebook.domain.enumeration.FormStatus;
import co.tmunited.bluebook.domain.enumeration.Progress;
import co.tmunited.bluebook.domain.enumeration.SignTypeValues;
import co.tmunited.bluebook.domain.vo.ChartVO;
import co.tmunited.bluebook.domain.vo.DashBoardVO;
import co.tmunited.bluebook.repository.*;
import co.tmunited.bluebook.service.DashboardReportService;
import co.tmunited.bluebook.service.GroupSessionService;
import co.tmunited.bluebook.service.PatientResultService;
import co.tmunited.bluebook.service.UserService;
import co.tmunited.bluebook.web.rest.GroupSessionDetailsChartResource;
import co.tmunited.bluebook.web.rest.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DashboardReport.
 */
@Service
@Transactional
public class DashboardReportServiceImpl implements DashboardReportService {

    public static final String PHYSICIAN = "PHYSICIAN";
    private final Logger log = LoggerFactory.getLogger(GroupSessionDetailsChartResource.class);

    @Inject
    private GroupSessionRepository groupSessionRepository;

    @Inject
    private GroupSessionDetailsRepository groupSessionDetailsRepository;

    @Inject
    private GroupSessionService groupSessionService;

    @Inject
    private PatientOrderRepository patientOrderRepository;

    @Inject
    private PatientResultService patientResultService;

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private ChartToFormRepository chartToFormRepository;

    @Inject
    private EvaluationRepository evaluationRepository;

    @Inject
    private EvaluationSignatureRepository evaluationSignatureRepository;

    @Inject
    private UserService userService;

    @Inject
    private ConcurrentReviewsRepository concurrentReviewsRepository;

    @Inject
    BedRepository bedRepository;

    @Inject
    ChartToLevelCareRepository chartToLevelCareRepository;

    @Inject
    TypePaymentMethodsRepository typePaymentMethodsRepository;

    @Inject
    TypeLevelCareRepository typeLevelCareRepository;


    /**
     * GET  /dashboard-report/:FId/:eId : get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
//    public DashboardReport getDashboardReport(Long fId) {
//
//        DashboardReport dashboardReport = new DashboardReport();
//        log.debug("REST Service to get DashboardReport : {}", fId);
//
//        ZonedDateTime now = ZonedDateTime.now();
//        now = now.withHour(0);
//        now = now.withMinute(0);
//        now = now.withSecond(0);
//        now = now.withNano(0);
//
//        ZonedDateTime passTomorrow = now.plusDays(2);
//
//        passTomorrow = passTomorrow.withHour(23);
//        passTomorrow = passTomorrow.withMinute(59);
//        passTomorrow = passTomorrow.withSecond(0);
//        passTomorrow = passTomorrow.withNano(0);
//
//        ZonedDateTime nowEnd = now.plusHours(23);
//        nowEnd = nowEnd.withHour(23);
//        nowEnd = nowEnd.withMinute(59);
//        nowEnd = nowEnd.withSecond(0);
//        nowEnd = nowEnd.withNano(0);
//
//
//        Employee employee = userService.getEmployeeWithAuthorities();
//
//        if (employee != null && employee.getTypeEmployee() != null && employee.getTypeEmployee().getName().equals(PHYSICIAN)) {
//            dashboardReport.setUnsignedOrdersByDoctor(patientOrderRepository.countByUnsignedOrdersAndPhysician(fId, employee.getId()).intValue());
//        }
//
//        //1 Chart
//        dashboardReport.setUnsignedOrders(patientOrderRepository.countByUnsignedOrders(fId).intValue());
//
//        // 2 Chart
//        List<PatientResult> patientResults = patientResultService.getUnassignedResultByClinic(fId); //By Facility
//        dashboardReport.setUnasignedLabResults(patientResults.size());
//
//        //3 Chart
//        List<ChartToForm> chartToFormsUnsignedCandidates = chartToFormRepository.findAllChartToFormUnsigned(fId, FormStatus.Pending, FormStatus.InProcess);//
//        log.debug(chartToFormsUnsignedCandidates.size() + "");
//        chartToFormsUnsignedCandidates.stream().forEach(cp -> System.out.print(cp.getName()));
//
//        List<Evaluation> evaluationsUnsignedCandidades = evaluationRepository.findAllByDelStatusIsFalseAndChartFacilityIdAndStatusNot(fId, EvaluationStatus.Finalized);
//
//
//        dashboardReport.setChartToFormsUnsigned(chartToFormsUnsignedCandidates);
//        dashboardReport.setEvaluationsUnsigned(this.getUnsignedEvaluations(fId));
//
//
//        // 4 Chart Current and Archive Month
//        List<Chart> chartList = chartRepository.findAllChartByFacilityId(fId);
//        dashboardReport.setMosthlyCountOfChart(chartList.size());
//
//        //ConcurrentReview for Today
//        List<ConcurrentReviews> concurrentReviews = concurrentReviewsRepository.findAllChartByFacilityIdAndAdmissionDateBetween(fId, now, nowEnd);
//        dashboardReport.setConcurrentReviewToday(concurrentReviews.size());
//
//        //5 Chart
//        List<Chart> chartListToCurrentNext3Day = chartRepository.findAllChartByFacilityIdAndAdmissionDateBetween(fId, now, passTomorrow);
//
//        chartListToCurrentNext3Day.forEach(x ->
//        {
//            System.out.print("******NAME: " + x.getPatient().getFirstName());
//        });
//        chartListToCurrentNext3Day.stream().
//
//            map(x -> x.getPatient().
//
//                getIdentification()).
//
//            collect(Collectors.toList());
//        dashboardReport.setIncomingChartForThe3NextDays(chartListToCurrentNext3Day);
//        //
//        //6 Chart
//        List<Chart> chartListToDischargeNext3Day = chartRepository.findAllChartByFacilityIdAndDischargeDateBetween(fId, now, passTomorrow);
//        chartListToDischargeNext3Day.stream().
//
//            map(x -> x.getPatient().
//
//                getIdentification()).
//
//            collect(Collectors.toList());
//        dashboardReport.setDischargedForThe3NextDays(chartListToDischargeNext3Day);
//
//
//        //pendingGroupsForTheDay;
//        //   ZonedDateTime localDate = ZonedDateTime.now();
//        //        List<GroupSession> result = groupSessionRepository.findAllByDelStatusIsFalseAndFacilityIdAndEndTimeLessThan(fId, localDate);
//        //        dashboardReport.setInProgressGroupSessionDetails(result !=null ? result.size(): 0);
//
//        // 7 groupSessionDetails InProgress
//        List<GroupSessionDetails> groupSessionDetailsInProgress = groupSessionDetailsRepository.findAllByDelStatusIsFalseAndGroupSessionFacilityIdAndProgressEquals(fId, Progress.InProcess);
//        dashboardReport.setInProgressGroupSessionDetails(groupSessionDetailsInProgress != null ? groupSessionDetailsInProgress.size() : 0);
//
//
//        // 8 groupSessionDetails PendingReview
//        List<GroupSessionDetails> groupSessionDetailsPendingReview = groupSessionDetailsRepository.findAllByDelStatusIsFalseAndGroupSessionFacilityIdAndProgressEquals(fId, Progress.PendingReview);
//        dashboardReport.setPendingReviewGroupSessionDetails(groupSessionDetailsPendingReview != null ? groupSessionDetailsPendingReview.size() : 0);
//
//        // 9 groupSessionDetails Completed
//        List<GroupSessionDetails> groupSessionDetailsCompleted = groupSessionDetailsRepository.findAllByDelStatusIsFalseAndGroupSessionFacilityIdAndProgressEquals(fId, Progress.Completed);
//        dashboardReport.setCompletedGroupSessionDetails(groupSessionDetailsCompleted != null ? groupSessionDetailsCompleted.size() : 0);
//
//        // 10 groupSession Total Today
//        LocalDate localDate = LocalDate.now();
//        List<GroupSession> groupSessionTotals = groupSessionService.findAllByFacilityAndDayForReport(fId, localDate);
//        dashboardReport.setTotalGroupsForTheDay(groupSessionTotals != null ? groupSessionTotals.size() : 0);
//
//
//        return dashboardReport;
//    }
    public List<ChartVO> getUnsignedForms(Long fId) {
        //3 Chart
        return chartToFormRepository.findAllChartToFormUnsignedVO(fId, FormStatus.Pending, FormStatus.InProcess);//
    }


    public UnsignedOrdersWrapper getPhysicianReview(Long fId) {

        Employee employee = userService.getEmployeeWithAuthorities();
        UnsignedOrdersWrapper wrapper = new UnsignedOrdersWrapper();

        if (employee != null && employee.getTypeEmployee() != null && employee.getTypeEmployee().getName().equals(PHYSICIAN)) {
            wrapper.setUnsignedOrdersByDoctor(patientOrderRepository.countByUnsignedOrdersAndPhysician(fId, employee.getId()).intValue());
        }

        //1 Chart
        wrapper.setUnsignedOrders(patientOrderRepository.countByUnsignedOrders(fId).intValue());

        return wrapper;
    }


    public UnassignedLabResultWrapper getUnassignedLabResult(Long fId) {

        // 2 Chart
        UnassignedLabResultWrapper wrapper = new UnassignedLabResultWrapper();
        wrapper.setUnasignedLabResults(patientResultService.getUnassignedResultByClinic(fId).size()); //By Facility

        return wrapper;
    }


    public UnsignedFormsWrapper getUnsignedFormsCount(Long fId) {

        //3 Chart
        UnsignedFormsWrapper wrapper = new UnsignedFormsWrapper();

        wrapper.setChartToFormsUnsigned(chartToFormRepository.findAllChartToFormUnsigned(fId, FormStatus.Pending, FormStatus.InProcess).size());//
        wrapper.setEvaluationsUnsigned(this.getUnsignedEvaluations(fId).size());

        return wrapper;
    }

    public ConcurrentsReviewWrapper getConcurrentsReviewCount(Long fId) {

        // 4 Chart
        ZonedDateTime now = ZonedDateTime.now();
        now = now.withHour(0);
        now = now.withMinute(0);
        now = now.withSecond(0);
        now = now.withNano(0);

        ZonedDateTime nowEnd = now.plusHours(23);
        nowEnd = nowEnd.withHour(23);
        nowEnd = nowEnd.withMinute(59);
        nowEnd = nowEnd.withSecond(0);
        nowEnd = nowEnd.withNano(0);

        //ConcurrentReview for Today
        ConcurrentsReviewWrapper wrapper = new ConcurrentsReviewWrapper();

        wrapper.setConcurrentsReviewToday(concurrentReviewsRepository.findAllChartByFacilityIdAndAdmissionDateBetween(fId, now, nowEnd).size());

        return wrapper;
    }


    public List<ChartVO> getIncomingPatients(Long fId) {

        // 5 Chart
        ZonedDateTime now = ZonedDateTime.now();
        now = now.withHour(0);
        now = now.withMinute(0);
        now = now.withSecond(0);
        now = now.withNano(0);

        ZonedDateTime passTomorrow = now.plusDays(2);
        passTomorrow = passTomorrow.withHour(23);
        passTomorrow = passTomorrow.withMinute(59);
        passTomorrow = passTomorrow.withSecond(0);
        passTomorrow = passTomorrow.withNano(0);

        //ChartVO for 3 Days
        return chartRepository.findAllChartByFacilityIdAndAdmissionDateBetweenVO(fId, now, passTomorrow);
    }


    public List<ChartVO> getDischargePatients(Long fId) {

        // 6 Chart
        ZonedDateTime now = ZonedDateTime.now();
        now = now.withHour(0);
        now = now.withMinute(0);
        now = now.withSecond(0);
        now = now.withNano(0);

        ZonedDateTime passTomorrow = now.plusDays(2);
        passTomorrow = passTomorrow.withHour(23);
        passTomorrow = passTomorrow.withMinute(59);
        passTomorrow = passTomorrow.withSecond(0);
        passTomorrow = passTomorrow.withNano(0);

        //ChartVO for Today
        return chartRepository.findAllChartByFacilityIdAndDischargeBetweenVO(fId, now, passTomorrow);
    }


    public LongWrapper getGroupSessionInProcessCount(Long fId) {

        // 7 groupSessionDetails InProgress
        LongWrapper wrapper = new LongWrapper();
        wrapper.setValue(new Long(groupSessionDetailsRepository.findAllByDelStatusIsFalseAndGroupSessionFacilityIdAndProgressEquals(fId, Progress.InProcess).size()));

        return wrapper;
    }

    public LongWrapper getGroupSessionPendingReviewCount(Long fId) {

        // 7 groupSessionDetails InProgress
        LongWrapper wrapper = new LongWrapper();
        wrapper.setValue(new Long(groupSessionDetailsRepository.findAllByDelStatusIsFalseAndGroupSessionFacilityIdAndProgressEquals(fId, Progress.PendingReview).size()));

        return wrapper;
    }

    public LongWrapper getGroupSessionCompletedCount(Long fId) {

        // 7 groupSessionDetails InProgress
        LongWrapper wrapper = new LongWrapper();
        wrapper.setValue(new Long(groupSessionDetailsRepository.findAllByDelStatusIsFalseAndGroupSessionFacilityIdAndProgressEquals(fId, Progress.Completed).size()));

        return wrapper;
    }


    public LongWrapper getGroupSessionTotalForToday(Long fId) {

        // 10 groupSession Total Today
        LocalDate localDate = LocalDate.now();
        LongWrapper wrapper = new LongWrapper();
        wrapper.setValue(new Long(groupSessionService.findAllByFacilityAndDayForReport(fId, localDate).size()));

        return wrapper;
    }

    @Override
    public LongWrapper needUpgrades(Long version) {

        String currentVersion = Constants.BUILD_VERSION;

        if (currentVersion == null) {
            return new LongWrapper(0L);
        } else {
            return new LongWrapper(Long.parseLong(currentVersion));
        }

    }

    @Override
    public LongWrapper currentVersion() {

        String currentVersion = Constants.BUILD_VERSION;
        if (currentVersion != null) {
            return new LongWrapper(Long.parseLong(currentVersion));
        } else {
            return new LongWrapper(-1L);
        }


    }

    /**
     * Dashboard data
     *
     * @param facilityId
     * @return
     */
    public DashBoardVO getDashboardData(Long facilityId) {
        DashBoardVO dashBoardVO = new DashBoardVO();
        ZonedDateTime now = ZonedDateTime.now();
        //Free beds
        List<Bed> beds = bedRepository.findAllFreeBedsByBuilding(facilityId);
        dashBoardVO.setBedAvailability(processBeds(beds));
        //Level care
        List<ChartToLevelCare> chartToLevelCares = chartToLevelCareRepository.findAllLevelCaresByFacility(facilityId, now);
        dashBoardVO.setTypeLevelCare(processChartToLevelCare(chartToLevelCares, facilityId));

        //Payments types
        List<Chart> charts = chartRepository.findAllWithPaymentTypes(facilityId, now);
        dashBoardVO.setTypePaymentMethod(processChartPaymentTypes(charts));

        //Concurrent Reviews
        List<Chart> chartsCR = chartRepository.findAllWithConcurrentReviews(facilityId, now);
        dashBoardVO.setConcurrentReviews(processChartConcurrentReviews(chartsCR));

        return dashBoardVO;
    }

    /**
     * Process chart payment types
     *
     * @param charts
     * @return
     */
    public Map<String, Integer> processChartConcurrentReviews(List<Chart> charts) {
        Map<String, Integer> mapConcurrentRev = new HashMap<>();
        mapConcurrentRev.put("concurrent_review", 0);
        mapConcurrentRev.put("no_concurrent_review", 0);

        charts.stream()
            .forEach(chart -> {
                //Check if chart has an actual concurrent review
                Long actualCR = chart.getConcurrentReviews().stream()
                    .filter(concurrentReviews1 -> (concurrentReviews1.getStartDate() != null && concurrentReviews1.getEndDate() != null &&
                        ZonedDateTime.now().compareTo(concurrentReviews1.getStartDate()) > 0
                        && ZonedDateTime.now().compareTo(concurrentReviews1.getEndDate()) < 0))
                    .count();
                if (actualCR > 0) {
                    mapConcurrentRev.put("concurrent_review", mapConcurrentRev.get("concurrent_review") + 1);
                } else {
                    mapConcurrentRev.put("no_concurrent_review", mapConcurrentRev.get("no_concurrent_review") + 1);
                }
            });

        return mapConcurrentRev;
    }

    /**
     * Process chart payment types
     *
     * @param charts
     * @return
     */
    public Map<String, Object> processChartPaymentTypes(List<Chart> charts) {
        List<TypePaymentMethods> paymentMethods = typePaymentMethodsRepository.findAllByDelStatusIsFalse();

        Map<String, Object> map = new HashMap<>();
        paymentMethods.stream()
            .forEach(paymentMethod -> {
                Long count = charts.stream()
                    .filter(chart -> chart.getTypePaymentMethods().getName().equals(paymentMethod.getName()))
                    .count();
                map.put(paymentMethod.getName(), count);
            });
        return map;
    }

    /**
     * Process level of care data
     *
     * @param chartToLevelCares
     * @return
     */
    public Map<String, Object> processChartToLevelCare(List<ChartToLevelCare> chartToLevelCares, Long facilityId) {
        List<TypeLevelCare> typeLevelCares = typeLevelCareRepository.findAllByFacilityIdAndDelStatusIsFalse(facilityId);
        Map<String, Object> map = new HashMap<>();
        typeLevelCares.stream().forEach(typeLevelCare -> {
            //Filter by level care
            Long chartsWithLevelCareCount = chartToLevelCares.stream()
                .filter(chartToLevelCare -> chartToLevelCare.getTypeLevelCare().getName().equals(typeLevelCare.getName()))
                .count();
            map.put(typeLevelCare.getName(), chartsWithLevelCareCount);
        });
        return map;
    }

    /**
     * Process beds data
     *
     * @param beds
     * @return
     */
    public Map<String, Object> processBeds(List<Bed> beds) {
        long femaleFreeBeds = beds.stream()
            .filter(bed -> bed.getRoom().getSex().equals("FEMALE"))
            .count();
        long maleFreeBeds = beds.stream()
            .filter(bed -> bed.getRoom().getSex().equals("MALE"))
            .count();

        Map<String, Object> mapGender = new HashMap<>();
        mapGender.put("female", femaleFreeBeds);
        mapGender.put("male", maleFreeBeds);
        return mapGender;
    }

    public List<ChartVO> getUnsignedEvaluations(Long fId) {

        List<Evaluation> evaluationsUnsignedCandidades = evaluationRepository.findAllByDelStatusIsFalseAndChartFacilityIdAndStatusNot(fId, EvaluationStatus.Finalized);

        Set<Evaluation> evaluationUnsigned = new HashSet<>();
        Set<Authority> authoritySets = userService.getUserWithAuthorities().getAuthorities();

        evaluationsUnsignedCandidades.stream().forEach(eUC -> {

            List<EvaluationSignature> evaluationSignatures = evaluationSignatureRepository.findAllByDelStatusIsFalseAndEvaluationId(eUC.getId());


            try {

                //Patient Signature Validate
                if (eUC.isPatientSignature() != null && eUC.isPatientSignature() == true && !isSignByPatient(evaluationSignatures)) {
                    evaluationUnsigned.add(eUC);
                }

                //Sign Signature Validate
                if (eUC.getRolesSign() != null && !eUC.getRolesSign().trim().equals("")) {
                    authoritySets.stream().forEach(a -> {
                        if (isInRolesString(eUC.getRolesSign(), a.getName()) && !isSign(evaluationSignatures)) {
                            evaluationUnsigned.add(eUC);
                        }
                    });
                }


                //Review Signature Validate
                if (eUC.getRolesReview() != null && !eUC.getRolesReview().trim().equals("")) {
                    authoritySets.stream().forEach(a -> {
                        if (isInRolesString(eUC.getRolesReview(), a.getName()) && isReview(evaluationSignatures)) {
                            evaluationUnsigned.add(eUC);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });


        List<ChartVO> evaluationsVO = new ArrayList<>();
        evaluationUnsigned.stream().forEach(e -> {
            ChartVO vo = new ChartVO(e.getChart().getId(), e.getChart().getPatient().getFirstName(), e.getChart().getPatient().getLastName(), e.getChart().getMrNo(),
                "", "", "", "", "", "", e.getChart().getAdmissionDate(),
                e.getChart().getDischargeDate(), e.getId(), e.getName());

            evaluationsVO.add(vo);
        });


        return evaluationsVO;
    }

    public List<ConcurrentReviews> getConcurrentReviewsByDate(Long fId, ZonedDateTime date) {

        date = date.withHour(0);
        date = date.withMinute(0);
        date = date.withSecond(0);
        date = date.withNano(0);

        ZonedDateTime nowEnd = date.plusHours(23);
        nowEnd = nowEnd.withHour(23);
        nowEnd = nowEnd.withMinute(59);
        nowEnd = nowEnd.withSecond(0);
        nowEnd = nowEnd.withNano(0);

        List<ConcurrentReviews> concurrentReviews = concurrentReviewsRepository.findAllChartByFacilityIdAndAdmissionDateBetween(fId, date, nowEnd);

        return concurrentReviews;
    }

    public List<ChartVO> getConcurrentReviewsByDateVO(Long fId, ZonedDateTime date) {

        date = date.withHour(0);
        date = date.withMinute(0);
        date = date.withSecond(0);
        date = date.withNano(0);

        ZonedDateTime nowEnd = date.plusHours(23);
        nowEnd = nowEnd.withHour(23);
        nowEnd = nowEnd.withMinute(59);
        nowEnd = nowEnd.withSecond(0);
        nowEnd = nowEnd.withNano(0);

        return concurrentReviewsRepository.findAllChartByFacilityIdAndAdmissionDateBetweenVO(fId, date, nowEnd);

    }


    private boolean isSignByPatient(List<EvaluationSignature> evaluationSignatures) {
        List<EvaluationSignature> patientSignatures = new ArrayList();
        patientSignatures = evaluationSignatures.stream().filter(eS -> eS.getPatientSignature() != null).collect(Collectors.toList());
        return patientSignatures.size() > 0;
    }


    private boolean isSign(List<EvaluationSignature> evaluationSignatures) {

        for (EvaluationSignature e : evaluationSignatures) {
            if (e.getSignType().equals(SignTypeValues.Approved)) {
                return true;
            }
        }

        return false;
    }

    private boolean isReview(List<EvaluationSignature> evaluationSignatures) {

        for (EvaluationSignature e : evaluationSignatures) {
            if (e.getSignType().equals(SignTypeValues.Reviewed)) {
                return true;
            }
        }

        return false;
    }


    private boolean isSignByRole(Authority a, List<EvaluationSignature> evaluationSignatures) {
        List<EvaluationSignature> patientSignatures = new ArrayList();
        patientSignatures = evaluationSignatures.stream().filter(eS -> eS.getSignature() != null &&
            eS.getRole().contains(a.getName()) &&
            eS.getSignType().equals(SignTypeValues.Approved)

        ).collect(Collectors.toList());
        return patientSignatures.size() > 0;
    }

    private boolean isInRolesString(String rolesByComma, String role) {
        String[] byComma = rolesByComma.split(",");

        return Arrays.asList(byComma).stream().filter(x -> x.trim().equals(role)).collect(Collectors.toList()).size() > 0;
    }

    private boolean isReviewByRole(Authority a, List<EvaluationSignature> evaluationSignatures) {
        List<EvaluationSignature> patientSignatures = new ArrayList();
        patientSignatures = evaluationSignatures.stream().filter(eS -> eS.getSignature() != null &&
            eS.getRole().contains(a.getName()) &&
            eS.getSignType().equals(SignTypeValues.Reviewed)

        ).collect(Collectors.toList());
        return patientSignatures.size() > 0;
    }


    public GroupSessionService getGroupSessionService() {
        return groupSessionService;
    }

    public void setGroupSessionService(GroupSessionService groupSessionService) {
        this.groupSessionService = groupSessionService;
    }

    public PatientOrderRepository getPatientOrderRepository() {
        return patientOrderRepository;
    }

    public void setPatientOrderRepository(PatientOrderRepository patientOrderRepository) {
        this.patientOrderRepository = patientOrderRepository;
    }

    public PatientResultService getPatientResultService() {
        return patientResultService;
    }

    public void setPatientResultService(PatientResultService patientResultService) {
        this.patientResultService = patientResultService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public GroupSessionRepository getGroupSessionRepository() {
        return groupSessionRepository;
    }

    public void setGroupSessionRepository(GroupSessionRepository groupSessionRepository) {
        this.groupSessionRepository = groupSessionRepository;
    }

    public GroupSessionDetailsRepository getGroupSessionDetailsRepository() {
        return groupSessionDetailsRepository;
    }

    public void setGroupSessionDetailsRepository(GroupSessionDetailsRepository groupSessionDetailsRepository) {
        this.groupSessionDetailsRepository = groupSessionDetailsRepository;
    }

    public ChartRepository getChartRepository() {
        return chartRepository;
    }

    public void setChartRepository(ChartRepository chartRepository) {
        this.chartRepository = chartRepository;
    }

}
