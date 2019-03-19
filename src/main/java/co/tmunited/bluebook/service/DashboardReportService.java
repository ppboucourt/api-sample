package co.tmunited.bluebook.service;

import co.tmunited.bluebook.domain.ChartToForm;
import co.tmunited.bluebook.domain.ConcurrentReviews;
import co.tmunited.bluebook.domain.Evaluation;
import co.tmunited.bluebook.domain.vo.ChartVO;
import co.tmunited.bluebook.domain.vo.DashBoardVO;
import co.tmunited.bluebook.web.rest.util.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing DashboardReport.
 */
public interface DashboardReportService {

    /**
     * GET  /dashboard-report/:FId: get the "id" Facility and Employee.
     *
     * @param fId the id of the facility to filter
     *
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
   // DashboardReport getDashboardReport(Long fId);
    List<ChartVO> getUnsignedForms(Long fId);
    List<ChartVO> getUnsignedEvaluations(Long fId);
    List<ConcurrentReviews> getConcurrentReviewsByDate(Long fId, ZonedDateTime date);
    List<ChartVO> getConcurrentReviewsByDateVO(Long fId, ZonedDateTime date);

    LongWrapper needUpgrades(Long version);

    /** Tuning **/
    UnsignedOrdersWrapper getPhysicianReview(Long fId);
    UnassignedLabResultWrapper getUnassignedLabResult(Long fId);
    UnsignedFormsWrapper getUnsignedFormsCount(Long fId);
    ConcurrentsReviewWrapper getConcurrentsReviewCount(Long fId);
    LongWrapper getGroupSessionInProcessCount(Long fId);
    LongWrapper getGroupSessionPendingReviewCount(Long fId);
    LongWrapper getGroupSessionCompletedCount(Long fId);
    LongWrapper getGroupSessionTotalForToday(Long fId);
    List<ChartVO> getIncomingPatients(Long fId);
    List<ChartVO> getDischargePatients(Long fId);
    LongWrapper currentVersion();

    DashBoardVO getDashboardData(Long facilityId);



}
