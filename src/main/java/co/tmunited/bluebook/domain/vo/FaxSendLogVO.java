package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.enumeration.FaxState;

import java.time.ZonedDateTime;

/**
 * Created by adriel on 8/4/17.
 */
public class FaxSendLogVO {
    private Long id;

    private String sid;

    private ZonedDateTime sendDate;

    private String faxToNumber;

    private String faxFromNumber;

    private FaxState faxState;

    private String pdfUuid;

    private String mediaUrl;

    private String toName;

    private String sendByFullName;

    private Boolean finalStatus = false;

    private String chartFullName;

    public FaxSendLogVO(Long id, String sid, ZonedDateTime sendDate, String faxToNumber, String faxFromNumber, FaxState faxState, String pdfUuid, String mediaUrl, String toName, String sendByFullName, Boolean finalStatus, String chartFullName) {
        this.id = id;
        this.sid = sid;
        this.sendDate = sendDate;
        this.faxToNumber = faxToNumber;
        this.faxFromNumber = faxFromNumber;
        this.faxState = faxState;
        this.pdfUuid = pdfUuid;
        this.mediaUrl = mediaUrl;
        this.toName = toName;
        this.sendByFullName = sendByFullName;
        this.finalStatus = finalStatus;
        this.chartFullName = chartFullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public ZonedDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(ZonedDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public String getFaxToNumber() {
        return faxToNumber;
    }

    public void setFaxToNumber(String faxToNumber) {
        this.faxToNumber = faxToNumber;
    }

    public String getFaxFromNumber() {
        return faxFromNumber;
    }

    public void setFaxFromNumber(String faxFromNumber) {
        this.faxFromNumber = faxFromNumber;
    }

    public FaxState getFaxState() {
        return faxState;
    }

    public void setFaxState(FaxState faxState) {
        this.faxState = faxState;
    }

    public String getPdfUuid() {
        return pdfUuid;
    }

    public void setPdfUuid(String pdfUuid) {
        this.pdfUuid = pdfUuid;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getSendByFullName() {
        return sendByFullName;
    }

    public void setSendByFullName(String sendByFullName) {
        this.sendByFullName = sendByFullName;
    }

    public Boolean getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(Boolean finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getChartFullName() {
        return chartFullName;
    }

    public void setChartFullName(String chartFullName) {
        this.chartFullName = chartFullName;
    }
}
