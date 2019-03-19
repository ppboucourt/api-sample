package co.tmunited.bluebook.web.rest.util;

import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.ChartToForm;
import co.tmunited.bluebook.domain.Evaluation;

import java.util.List;
import java.util.Set;

/**
 * Created by adriel on 5/11/17.
 */
public class DashboardReport {

    private int unsignedOrdersByDoctor = 0;
    private int unsignedOrders= 0;

    private int unasignedLabResults= 0;
    private List<ChartToForm> chartToFormsUnsigned;//By user role logged
    private Set<Evaluation> evaluationsUnsigned;
    private int mosthlyCountOfChart = 0;//Not include waiting room

    private int concurrentReviewToday = 0;

    private List<Chart> incomingChartForThe3NextDays;
    private List<Chart> dischargedForThe3NextDays;

    private int inProgressGroupSessionDetails = 0;
    private int pendingReviewGroupSessionDetails= 0;
    private int completedGroupSessionDetails= 0;
    private int totalGroupsForTheDay= 0;

    public int getConcurrentReviewToday() {
        return concurrentReviewToday;
    }

    public void setConcurrentReviewToday(int concurrentReviewToday) {
        this.concurrentReviewToday = concurrentReviewToday;
    }

    public Set<Evaluation> getEvaluationsUnsigned() {
        return evaluationsUnsigned;
    }

    public void setEvaluationsUnsigned(Set<Evaluation> evaluation) {
        this.evaluationsUnsigned = evaluation;
    }

    public int getUnsignedOrdersByDoctor() {
        return unsignedOrdersByDoctor;
    }

    public void setUnsignedOrdersByDoctor(int unsignedOrdersByDoctor) {
        this.unsignedOrdersByDoctor = unsignedOrdersByDoctor;
    }

    public int getUnasignedLabResults() {
        return unasignedLabResults;
    }

    public void setUnasignedLabResults(int unasignedLabResults) {
        this.unasignedLabResults = unasignedLabResults;
    }

    public List<ChartToForm> getChartToFormsUnsigned() {
        return chartToFormsUnsigned;
    }

    public void setChartToFormsUnsigned(List<ChartToForm> chartToFormsUnsigned) {
        this.chartToFormsUnsigned = chartToFormsUnsigned;
    }

    public int getMosthlyCountOfChart() {
        return mosthlyCountOfChart;
    }

    public void setMosthlyCountOfChart(int mosthlyCountOfChart) {
        this.mosthlyCountOfChart = mosthlyCountOfChart;
    }

    public List<Chart> getIncomingChartForThe3NextDays() {
        return incomingChartForThe3NextDays;
    }

    public void setIncomingChartForThe3NextDays(List<Chart> incomingChartForThe3NextDays) {
        this.incomingChartForThe3NextDays = incomingChartForThe3NextDays;
    }

    public List<Chart> getDischargedForThe3NextDays() {
        return dischargedForThe3NextDays;
    }

    public void setDischargedForThe3NextDays(List<Chart> dischargedForThe3NextDays) {
        this.dischargedForThe3NextDays = dischargedForThe3NextDays;
    }

    public int getInProgressGroupSessionDetails() {
        return inProgressGroupSessionDetails;
    }

    public void setInProgressGroupSessionDetails(int inProgressGroupSessionDetails) {
        this.inProgressGroupSessionDetails = inProgressGroupSessionDetails;
    }

    public int getPendingReviewGroupSessionDetails() {
        return pendingReviewGroupSessionDetails;
    }

    public void setPendingReviewGroupSessionDetails(int pendingReviewGroupSessionDetails) {
        this.pendingReviewGroupSessionDetails = pendingReviewGroupSessionDetails;
    }

    public int getCompletedGroupSessionDetails() {
        return completedGroupSessionDetails;
    }

    public void setCompletedGroupSessionDetails(int completedGroupSessionDetails) {
        this.completedGroupSessionDetails = completedGroupSessionDetails;
    }

    public int getTotalGroupsForTheDay() {
        return totalGroupsForTheDay;
    }

    public void setTotalGroupsForTheDay(int totalGroupsForTheDay) {
        this.totalGroupsForTheDay = totalGroupsForTheDay;
    }

    public int getUnsignedOrders() {
        return unsignedOrders;
    }

    public void setUnsignedOrders(int unsignedOrders) {
        this.unsignedOrders = unsignedOrders;
    }
}
