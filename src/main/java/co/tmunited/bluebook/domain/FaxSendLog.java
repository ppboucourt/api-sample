package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import co.tmunited.bluebook.domain.enumeration.FaxState;

/**
 * A FaxSendLog.
 */
@Entity
@Table(name = "fax_send_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "faxsendlog")
public class FaxSendLog extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sid")
    private String sid;

    @Column(name = "send_date")
    private ZonedDateTime sendDate;

    @Column(name = "fax_to_number")
    private String faxToNumber;

    @Column(name = "fax_from_number")
    private String faxFromNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "fax_state")
    private FaxState faxState;

    @Column(name = "pdf_uuid")
    private String pdfUuid;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "to_Name")
    private String toName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_by_id")
    private Employee sendBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_id")
    private Chart chart;

    @Column(name = "final_status")
    private Boolean finalStatus = false;


    public Boolean getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(Boolean finalStatus) {
        this.finalStatus = finalStatus;
    }

    public Employee getSendBy() {
        return sendBy;
    }

    public void setSendBy(Employee sendBy) {
        this.sendBy = sendBy;
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

    public FaxSendLog sid(String sid) {
        this.sid = sid;
        return this;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public ZonedDateTime getSendDate() {
        return sendDate;
    }

    public FaxSendLog sendDate(ZonedDateTime sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    public void setSendDate(ZonedDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public String getFaxToNumber() {
        return faxToNumber;
    }

    public FaxSendLog faxToNumber(String faxToNumber) {
        this.faxToNumber = faxToNumber;
        return this;
    }

    public void setFaxToNumber(String faxToNumber) {
        this.faxToNumber = faxToNumber;
    }

    public String getFaxFromNumber() {
        return faxFromNumber;
    }

    public FaxSendLog faxFromNumber(String faxFromNumber) {
        this.faxFromNumber = faxFromNumber;
        return this;
    }

    public void setFaxFromNumber(String faxFromNumber) {
        this.faxFromNumber = faxFromNumber;
    }

    public FaxState getFaxState() {
        return faxState;
    }

    public FaxSendLog faxState(FaxState faxState) {
        this.faxState = faxState;
        return this;
    }

    public void setFaxState(FaxState faxState) {
        this.faxState = faxState;
    }

    public String getPdfUuid() {
        return pdfUuid;
    }

    public FaxSendLog pdfUuid(String pdfUuid) {
        this.pdfUuid = pdfUuid;
        return this;
    }

    public void setPdfUuid(String pdfUuid) {
        this.pdfUuid = pdfUuid;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public FaxSendLog mediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
        return this;
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

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FaxSendLog faxSendLog = (FaxSendLog) o;
        if (faxSendLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, faxSendLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FaxSendLog{" +
            "id=" + id +
            ", sid='" + sid + "'" +
            ", sendDate='" + sendDate + "'" +
            ", faxToNumber='" + faxToNumber + "'" +
            ", faxFromNumber='" + faxFromNumber + "'" +
            ", faxState='" + faxState + "'" +
            ", pdfUuid='" + pdfUuid + "'" +
            ", mediaUrl='" + mediaUrl + "'" +
            '}';
    }
}
