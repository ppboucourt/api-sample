package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orders")
public class Orders extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type_order")
    private String type_order;

    @Column(name = "status")
    private String status;

    @Column(name = "start_date")
    private ZonedDateTime start_date;

    @Column(name = "end_date")
    private ZonedDateTime end_date;

    @Column(name = "order_by")
    private String order_by;

    @Column(name = "order_via")
    private String order_via;

    @Column(name = "doctor_signature")
    private String doctor_signature;

    @Column(name = "reviewed_by")
    private String reviewed_by;

    @Column(name = "review_date")
    private ZonedDateTime review_date;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "on_admission")
    private String on_admission;

    @Column(name = "until_discharge")
    private String until_discharge;

    @ManyToOne
    private Chart chart;

    @ManyToOne
    private LabCompendium labCompendium;

    @ManyToOne
    private Actions action;

    @ManyToOne
    private Medications medication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType_order() {
        return type_order;
    }

    public Orders type_order(String type_order) {
        this.type_order = type_order;
        return this;
    }

    public void setType_order(String type_order) {
        this.type_order = type_order;
    }

    public String getStatus() {
        return status;
    }

    public Orders status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getStart_date() {
        return start_date;
    }

    public Orders start_date(ZonedDateTime start_date) {
        this.start_date = start_date;
        return this;
    }

    public void setStart_date(ZonedDateTime start_date) {
        this.start_date = start_date;
    }

    public ZonedDateTime getEnd_date() {
        return end_date;
    }

    public Orders end_date(ZonedDateTime end_date) {
        this.end_date = end_date;
        return this;
    }

    public void setEnd_date(ZonedDateTime end_date) {
        this.end_date = end_date;
    }

    public String getOrder_by() {
        return order_by;
    }

    public Orders order_by(String order_by) {
        this.order_by = order_by;
        return this;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public String getOrder_via() {
        return order_via;
    }

    public Orders order_via(String order_via) {
        this.order_via = order_via;
        return this;
    }

    public void setOrder_via(String order_via) {
        this.order_via = order_via;
    }

    public String getDoctor_signature() {
        return doctor_signature;
    }

    public Orders doctor_signature(String doctor_signature) {
        this.doctor_signature = doctor_signature;
        return this;
    }

    public void setDoctor_signature(String doctor_signature) {
        this.doctor_signature = doctor_signature;
    }

    public String getReviewed_by() {
        return reviewed_by;
    }

    public Orders reviewed_by(String reviewed_by) {
        this.reviewed_by = reviewed_by;
        return this;
    }

    public void setReviewed_by(String reviewed_by) {
        this.reviewed_by = reviewed_by;
    }

    public ZonedDateTime getReview_date() {
        return review_date;
    }

    public Orders review_date(ZonedDateTime review_date) {
        this.review_date = review_date;
        return this;
    }

    public void setReview_date(ZonedDateTime review_date) {
        this.review_date = review_date;
    }

    public String getFrequency() {
        return frequency;
    }

    public Orders frequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getOn_admission() {
        return on_admission;
    }

    public Orders on_admission(String on_admission) {
        this.on_admission = on_admission;
        return this;
    }

    public void setOn_admission(String on_admission) {
        this.on_admission = on_admission;
    }

    public String getUntil_discharge() {
        return until_discharge;
    }

    public Orders until_discharge(String until_discharge) {
        this.until_discharge = until_discharge;
        return this;
    }

    public void setUntil_discharge(String until_discharge) {
        this.until_discharge = until_discharge;
    }

    public Chart getChart() {
        return chart;
    }

    public Orders chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public LabCompendium getLabCompendium() {
        return labCompendium;
    }

    public Orders labCompendium(LabCompendium labCompendium) {
        this.labCompendium = labCompendium;
        return this;
    }

    public void setLabCompendium(LabCompendium labCompendium) {
        this.labCompendium = labCompendium;
    }

    public Actions getAction() {
        return action;
    }

    public Orders action(Actions actions) {
        this.action = actions;
        return this;
    }

    public void setAction(Actions actions) {
        this.action = actions;
    }

    public Medications getMedication() {
        return medication;
    }

    public Orders medication(Medications medications) {
        this.medication = medications;
        return this;
    }

    public void setMedication(Medications medications) {
        this.medication = medications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orders orders = (Orders) o;
        if(orders.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orders.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + id +
            ", type_order='" + type_order + "'" +
            ", status='" + status + "'" +
            ", start_date='" + start_date + "'" +
            ", end_date='" + end_date + "'" +
            ", order_by='" + order_by + "'" +
            ", order_via='" + order_via + "'" +
            ", doctor_signature='" + doctor_signature + "'" +
            ", reviewed_by='" + reviewed_by + "'" +
            ", review_date='" + review_date + "'" +
            ", frequency='" + frequency + "'" +
            ", on_admission='" + on_admission + "'" +
            ", until_discharge='" + until_discharge + "'" +
            '}';
    }
}
