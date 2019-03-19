package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.ConcurrentReviewFrequency;
import co.tmunited.bluebook.domain.enumeration.EvaluationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ConcurrentReviews.
 */
@Entity
@Table(name = "concurrent_reviews")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "concurrentreviews")
public class ConcurrentReviews extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "authorization_date")
    private ZonedDateTime authorizationDate;

    @Column(name = "number_dates")
    private Integer numberDates;

    @Column(name = "frequency")
    @Enumerated(EnumType.STRING)
    private ConcurrentReviewFrequency frequency;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "last_coverage_date")
    private ZonedDateTime lastCoverageDate;

    @Column(name = "authorization_number")
    private String authorizationNumber;

    @Column(name = "next_review_date")
    private ZonedDateTime nextReviewDate;

    @Column(name = "insurance_company")
    private String insuranceCompany;

    @Column(name = "notes")
    private String notes;

    @NotNull
    @Size(max = 3)
    @Column(name = "status", length = 3, nullable = false)
    private String status;

    @JoinColumn(name = "chart_concurrent_reviews_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Chart chartConcurrentReviews;

    @Column(name = "chart_concurrent_reviews_id")
    private Long chartId;

    @ManyToOne
    private TypeLevelCare typeLevelCare;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getAuthorizationDate() {
        return authorizationDate;
    }

    public ConcurrentReviews authorizationDate(ZonedDateTime authorizationDate) {
        this.authorizationDate = authorizationDate;
        return this;
    }

    public void setAuthorizationDate(ZonedDateTime authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public Integer getNumberDates() {
        return numberDates;
    }

    public ConcurrentReviews numberDates(Integer numberDates) {
        this.numberDates = numberDates;
        return this;
    }

    public void setNumberDates(Integer numberDates) {
        this.numberDates = numberDates;
    }

    public ConcurrentReviewFrequency getFrequency() {
        return frequency;
    }

    public ConcurrentReviews frequency(ConcurrentReviewFrequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(ConcurrentReviewFrequency frequency) {
        this.frequency = frequency;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ConcurrentReviews startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public ConcurrentReviews endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public ZonedDateTime getLastCoverageDate() {
        return lastCoverageDate;
    }

    public ConcurrentReviews lastCoverageDate(ZonedDateTime lastCoverageDate) {
        this.lastCoverageDate = lastCoverageDate;
        return this;
    }

    public void setLastCoverageDate(ZonedDateTime lastCoverageDate) {
        this.lastCoverageDate = lastCoverageDate;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public ConcurrentReviews authorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
        return this;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public ZonedDateTime getNextReviewDate() {
        return nextReviewDate;
    }

    public ConcurrentReviews nextReviewDate(ZonedDateTime nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
        return this;
    }

    public void setNextReviewDate(ZonedDateTime nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public ConcurrentReviews insuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
        return this;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getNotes() {
        return notes;
    }

    public ConcurrentReviews notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public ConcurrentReviews status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Chart getChartConcurrentReviews() {
        return chartConcurrentReviews;
    }

    public ConcurrentReviews chartConcurrentReviews(Chart chart) {
        this.chartConcurrentReviews = chart;
        return this;
    }

    public void setChartConcurrentReviews(Chart chart) {
        this.chartConcurrentReviews = chart;
    }

    public TypeLevelCare getTypeLevelCare() {
        return typeLevelCare;
    }

    public ConcurrentReviews typeLevelCare(TypeLevelCare typeLevelCare) {
        this.typeLevelCare = typeLevelCare;
        return this;
    }

    public void setTypeLevelCare(TypeLevelCare typeLevelCare) {
        this.typeLevelCare = typeLevelCare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConcurrentReviews concurrentReviews = (ConcurrentReviews) o;
        if(concurrentReviews.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, concurrentReviews.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConcurrentReviews{" +
            "id=" + id +
            ", authorizationDate='" + authorizationDate + "'" +
            ", numberDates='" + numberDates + "'" +
            ", frequency='" + frequency + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", lastCoverageDate='" + lastCoverageDate + "'" +
            ", authorizationNumber='" + authorizationNumber + "'" +
            ", nextReviewDate='" + nextReviewDate + "'" +
            ", insuranceCompany='" + insuranceCompany + "'" +
            ", notes='" + notes + "'" +
            ", status='" + status + "'" +
            '}';
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }
}
