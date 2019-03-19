package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Allergies.
 */
@Entity
@Table(name = "allergies")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "allergies")
public class Allergies extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "allergen")
    private String allergen;

    @Column(name = "allergen_type")
    private String allergenType;

    @Column(name = "reaction")
    private String reaction;

    @Column(name = "treatment")
    private String treatment;

    @Column(name = "status")
    private String status;

    @JoinColumn(name = "chart_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Chart chart;

    @Column(name = "chart_id")
    private Long chartId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllergen() {
        return allergen;
    }

    public Allergies allergen(String allergen) {
        this.allergen = allergen;
        return this;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    public String getAllergenType() {
        return allergenType;
    }

    public Allergies allergenType(String allergenType) {
        this.allergenType = allergenType;
        return this;
    }

    public void setAllergenType(String allergenType) {
        this.allergenType = allergenType;
    }

    public String getReaction() {
        return reaction;
    }

    public Allergies reaction(String reaction) {
        this.reaction = reaction;
        return this;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getTreatment() {
        return treatment;
    }

    public Allergies treatment(String treatment) {
        this.treatment = treatment;
        return this;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getStatus() {
        return status;
    }

    public Allergies status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Chart getChart() {
        return chart;
    }

    public Allergies chart(Chart chart) {
        this.chart = chart;
        return this;
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
        Allergies allergies = (Allergies) o;
        if(allergies.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, allergies.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Allergies{" +
            "id=" + id +
            ", allergen='" + allergen + "'" +
            ", allergenType='" + allergenType + "'" +
            ", reaction='" + reaction + "'" +
            ", treatment='" + treatment + "'" +
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
