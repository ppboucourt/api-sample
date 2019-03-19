package co.tmunited.bluebook.domain.vo;

import java.time.ZonedDateTime;

/**
 * Created by adriel on 7/11/17.
 */
public class GroupSessionDetailsChartVO {
    private Long id;
    private String notes;
    private Long chartId;

    private String firstName;
    private String lastName;

    private String bed;
    private String mrNo;
    private String phone;

    private String name;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private String employeeName;
    private String topic;
    private String signatureLeaderIp;
    private String signatureReviewerIp;
    private ZonedDateTime signatureLeaderCreatedDate;
    private ZonedDateTime signatureReviewerCreatedDate;

    public GroupSessionDetailsChartVO(Long id, String notes, Long chartId, String name, ZonedDateTime startDateTime,
                                      ZonedDateTime endDateTime, String employeeName, String topic, String signatureLeaderIp,
                                      ZonedDateTime signatureLeaderCreatedDate, String signatureReviewerIp,
                                      ZonedDateTime signatureReviewerCreatedDate) {
        this.id = id;
        this.notes = notes;
        this.chartId = chartId;
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.employeeName = employeeName;
        this.topic = topic;
        this.signatureLeaderIp = signatureLeaderIp;
        this.signatureLeaderCreatedDate = signatureLeaderCreatedDate;
        this.signatureReviewerIp = signatureReviewerIp;
        this.signatureReviewerCreatedDate = signatureReviewerCreatedDate;

    }


    public GroupSessionDetailsChartVO(Long id, String notes, Long chartId, String firstName, String lastName, String bed, String mrNo, String phone) {
        this.id = id;
        this.notes = notes;
        this.chartId = chartId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bed = bed;
        this.mrNo = mrNo;
        this.phone = phone;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSignatureLeaderIp() {
        return signatureLeaderIp;
    }

    public void setSignatureLeaderIp(String signatureLeaderIp) {
        this.signatureLeaderIp = signatureLeaderIp;
    }

    public ZonedDateTime getSignatureReviewerCreatedDate() {
        return signatureReviewerCreatedDate;
    }

    public void setSignatureReviewerCreatedDate(ZonedDateTime signatureReviewerCreatedDate) {
        this.signatureReviewerCreatedDate = signatureReviewerCreatedDate;
    }

    public ZonedDateTime getSignatureLeaderCreatedDate() {
        return signatureLeaderCreatedDate;
    }

    public void setSignatureLeaderCreatedDate(ZonedDateTime signatureLeaderCreatedDate) {
        this.signatureLeaderCreatedDate = signatureLeaderCreatedDate;
    }

    public String getSignatureReviewerIp() {
        return signatureReviewerIp;
    }

    public void setSignatureReviewerIp(String signatureReviewerIp) {
        this.signatureReviewerIp = signatureReviewerIp;
    }
}
