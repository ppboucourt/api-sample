package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Vitals.
 */
@Entity
@Table(name = "vitals")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vitals")
public class Vitals extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "time")
    private ZonedDateTime time;

    @Column(name = "bp_systolic")
    private String bp_systolic;

    @Column(name = "bp_diastolic")
    private String bp_diastolic;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "pulse")
    private String pulse;

    @Column(name = "respiration")
    private String respiration;

    @Column(name = "o_2_saturation")
    private String o2_saturation;

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

    public ZonedDateTime getDate() {
        return date;
    }

    public Vitals date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Vitals time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getBp_systolic() {
        return bp_systolic;
    }

    public Vitals bp_systolic(String bp_systolic) {
        this.bp_systolic = bp_systolic;
        return this;
    }

    public void setBp_systolic(String bp_systolic) {
        this.bp_systolic = bp_systolic;
    }

    public String getBp_diastolic() {
        return bp_diastolic;
    }

    public Vitals bp_diastolic(String bp_diastolic) {
        this.bp_diastolic = bp_diastolic;
        return this;
    }

    public void setBp_diastolic(String bp_diastolic) {
        this.bp_diastolic = bp_diastolic;
    }

    public String getTemperature() {
        return temperature;
    }

    public Vitals temperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPulse() {
        return pulse;
    }

    public Vitals pulse(String pulse) {
        this.pulse = pulse;
        return this;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getRespiration() {
        return respiration;
    }

    public Vitals respiration(String respiration) {
        this.respiration = respiration;
        return this;
    }

    public void setRespiration(String respiration) {
        this.respiration = respiration;
    }

    public String geto2_saturation() {
        return o2_saturation;
    }

    public Vitals o2_saturation(String o2_saturation) {
        this.o2_saturation = o2_saturation;
        return this;
    }

    public void seto2_saturation(String o2_saturation) {
        this.o2_saturation = o2_saturation;
    }

    public String getStatus() {
        return status;
    }

    public Vitals status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Chart getChart() {
        return chart;
    }

    public Vitals chart(Chart chart) {
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
        Vitals vitals = (Vitals) o;
        if(vitals.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, vitals.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Vitals{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", time='" + time + "'" +
            ", bp_systolic='" + bp_systolic + "'" +
            ", bp_diastolic='" + bp_diastolic + "'" +
            ", temperature='" + temperature + "'" +
            ", pulse='" + pulse + "'" +
            ", respiration='" + respiration + "'" +
            ", o2_saturation='" + o2_saturation + "'" +
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
