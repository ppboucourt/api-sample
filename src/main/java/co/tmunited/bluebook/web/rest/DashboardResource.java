package co.tmunited.bluebook.web.rest;

import co.tmunited.bluebook.domain.ChartToForm;
import co.tmunited.bluebook.domain.ConcurrentReviews;
import co.tmunited.bluebook.domain.Evaluation;
import co.tmunited.bluebook.domain.vo.ChartVO;
import co.tmunited.bluebook.domain.vo.DashBoardVO;
import co.tmunited.bluebook.service.ConcurrentReviewsService;
import co.tmunited.bluebook.service.DashboardReportService;
import co.tmunited.bluebook.web.rest.util.*;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Building.
 */
@RestController
@RequestMapping("/api")
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);

    @Inject
    private DashboardReportService dashboardReportService;




    /**
     * GET  /dashboard-report/:FId/:eId : get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
//    @GetMapping("/dashboard-report/{fId}")
//    @Timed
//    @Deprecated
//    public ResponseEntity<DashboardReport> getDashboardReport(@PathVariable Long fId) {
//
//        log.debug("REST request to get DashboardReport : {}", fId);
//        DashboardReport dashboardReport = dashboardReportService.getDashboardReport(fId);
//
//
//        return Optional.ofNullable(dashboardReport)
//            .map(result -> new ResponseEntity<>(
//                result,
//                HttpStatus.OK))
//            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//
//    }

    /**
     * GET  /unsigned-forms/:FId : get the "id" Facility.
     *
     * @param fId the id of the facility to filter
     *
     * @return the List<ChartToForm>
     */
    @GetMapping("/unsigned-forms/{fId}")
    @Timed
    public List<ChartVO> getUnsignedForms(@PathVariable Long fId) {
        log.debug("REST request to get UnsignedForms : {}", fId);
        List<ChartVO>  chartToForms  = dashboardReportService.getUnsignedForms(fId);
        return (chartToForms);
    }



    /**
     * GET  /unsigned-evaluations/:FId get the "id" Facility.
     *
     * @param fId the id of the facility to filter
     *
     * @return the Set<Evaluation>
     */
    @GetMapping("/unsigned-evaluations/{fId}")
    @Timed
    public List<ChartVO> getUnsignedEvaluations(@PathVariable Long fId) {
        log.debug("REST request to get UnsignedForms : {}", fId);
        List<ChartVO> evaluations = dashboardReportService.getUnsignedEvaluations(fId);
        return evaluations;
    }


    /**
     * GET  /unsigned-evaluations/:FId get the "id" Facility.
     *
     * @param fId the id of the facility to filter
     *
     * @return the Set<Evaluation>
     */
    @GetMapping("/dconcurrents-review-by-date/{fId}/{date}")
    @Timed
    public List<ConcurrentReviews> getConcurrentReviews(@PathVariable Long fId, @PathVariable LocalDate date) {
        log.debug("REST request to get getConcurrentReviews : {}", fId);
        List<ConcurrentReviews> concurrentReviews = dashboardReportService.getConcurrentReviewsByDate(fId, date.atStartOfDay(ZoneId.systemDefault()));
        return concurrentReviews;
    }


    /**
     * GET  /unsigned-evaluations/:FId get the "id" Facility.
     *
     * @param fId the id of the facility to filter
     *
     * @return the Set<Evaluation>
     */
    @GetMapping("/dconcurrents-review-vo-by-date/{fId}/{date}")
    @Timed
    public List<ChartVO> getConcurrentReviewsVO(@PathVariable Long fId, @PathVariable LocalDate date) {
        log.debug("REST request to get getConcurrentReviews : {}", fId);
        List<ChartVO> chartsVO = dashboardReportService.getConcurrentReviewsByDateVO(fId, date.atStartOfDay(ZoneId.systemDefault()));
        return chartsVO;
    }


    /**
     * GET  /need-upgrade-version/:version get the "version" in Browser.
     *
     * @param version the "version" in Browser.
     *
     * @return the List<BooleanWrapper>
     */
    @GetMapping("/need-upgrade-version/{version}")
    @Timed
    public LongWrapper needUpgrades(@PathVariable Long version) {
        log.debug("REST request to validate BlueBook Version : {}", version);
        LongWrapper needUpgrades = dashboardReportService.needUpgrades(version);
        return needUpgrades;
    }




    /**
     * GET  /current-version get the "version" in Browser.
     *
     * @return the LongWrapper
     */
    @GetMapping("/current-version")
    @Timed
    public LongWrapper currentVersion() {
        log.debug("REST request to validate BlueBook Version : {}");
        LongWrapper currentVersion = dashboardReportService.currentVersion();
        return currentVersion;
    }



    /** Tuning Dashboard **/

    /**
     * GET  /dashboard/physician-review/:fId/: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/physician-review/{fId}")
    @Timed
    public ResponseEntity<UnsignedOrdersWrapper> getPhysicianReview(@PathVariable Long fId) {
        log.debug("REST request to get getPhysicianReview : {}", fId);
        UnsignedOrdersWrapper wrapper = dashboardReportService.getPhysicianReview(fId);
        return Optional.ofNullable(wrapper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /dashboard/unassigned-lab-result/:fId/: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/unassigned-lab-result/{fId}")
    @Timed
    public ResponseEntity<UnassignedLabResultWrapper> getUnassignedLabResult(@PathVariable Long fId) {
        log.debug("REST request to get getUnassignedLabResult : {}", fId);
        UnassignedLabResultWrapper wrapper = dashboardReportService.getUnassignedLabResult(fId);
        return Optional.ofNullable(wrapper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /dashboard/unsigned-forms-count/:fId/: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/unsigned-forms-count/{fId}")
    @Timed
    public ResponseEntity<UnsignedFormsWrapper> getUnsignedFormsCount(@PathVariable Long fId) {
        log.debug("REST request to get getUnsignedFormsCount : {}", fId);
        UnsignedFormsWrapper wrapper = dashboardReportService.getUnsignedFormsCount(fId);
        return Optional.ofNullable(wrapper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /dashboard/concurrents-review-count/:fId/: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/concurrents-review-count/{fId}")
    @Timed
    public ResponseEntity<ConcurrentsReviewWrapper> getConcurrentsReviewCount(@PathVariable Long fId) {
        log.debug("REST request to get getConcurrentsReviewCount : {}", fId);
        ConcurrentsReviewWrapper wrapper = dashboardReportService.getConcurrentsReviewCount(fId);
        return Optional.ofNullable(wrapper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /dashboard/group-session-in-process-count/:fId/: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/group-session-in-process-count/{fId}")
    @Timed
    public ResponseEntity<LongWrapper> getGroupSessionInProcessCount(@PathVariable Long fId) {
        log.debug("REST request to get getGroupSessionInProcessCount : {}", fId);
        LongWrapper wrapper = dashboardReportService.getGroupSessionInProcessCount(fId);
        return Optional.ofNullable(wrapper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /dashboard/group-session-pending-review-count/:fId/: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/group-session-pending-review-count/{fId}")
    @Timed
    public ResponseEntity<LongWrapper> getGroupSessionPendingReviewCount(@PathVariable Long fId) {
        log.debug("REST request to get getGroupSessionPendingReviewCount : {}", fId);
        LongWrapper wrapper = dashboardReportService.getGroupSessionPendingReviewCount(fId);
        return Optional.ofNullable(wrapper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /dashboard/group-session-completed-count/:fId/: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/group-session-completed-count/{fId}")
    @Timed
    public ResponseEntity<LongWrapper> getGroupSessionCompletedCount(@PathVariable Long fId) {
        log.debug("REST request to get getGroupSessionCompletedCount : {}", fId);
        LongWrapper wrapper = dashboardReportService.getGroupSessionCompletedCount(fId);
        return Optional.ofNullable(wrapper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /dashboard/group-session-completed-count/:fId/: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/group-session-total-for-today/{fId}")
    @Timed
    public ResponseEntity<LongWrapper> getGroupSessionTotalForToday(@PathVariable Long fId) {
        log.debug("REST request to get getGroupSessionTotalForToday : {}", fId);
        LongWrapper wrapper = dashboardReportService.getGroupSessionTotalForToday(fId);
        return Optional.ofNullable(wrapper)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /dashboard/incoming-patients-3day-next/:fId/: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/incoming-patients-3day-next/{fId}")
    @Timed
    public ResponseEntity<List<ChartVO>> getIncomingPatients3DayNext(@PathVariable Long fId) {
        log.debug("REST request to get getGroupSessionTotalForToday : {}", fId);
        List<ChartVO>  chartsVO = dashboardReportService.getIncomingPatients(fId);
        return Optional.ofNullable(chartsVO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /dashboard/discharging-patients-3day-next/:fId/: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/discharging-patients-3day-next/{fId}")
    @Timed
    public ResponseEntity<List<ChartVO>> getDischargingPatients3DayNext(@PathVariable Long fId) {
        log.debug("REST request to get getGroupSessionTotalForToday : {}", fId);
        List<ChartVO>  chartsVO = dashboardReportService.getDischargePatients(fId);
        return Optional.ofNullable(chartsVO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /dashboard/all-data/:fId/: get the "id" Facility.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/dashboard/all-data/{fId}")
    @Timed
    public ResponseEntity<DashBoardVO> getDashboardData(@PathVariable Long fId) {
        log.debug("REST request to get getGroupSessionTotalForToday : {}", fId);
        DashBoardVO dashBoardVO = dashboardReportService.getDashboardData(fId);
        return Optional.ofNullable(dashBoardVO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
