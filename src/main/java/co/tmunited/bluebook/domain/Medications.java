package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Medications.
 */
@Entity
@Table(name = "medications")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "medications")
public class Medications extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "medication")
    private String medication;

    @Column(name = "route")
    private String route;

    @Column(name = "dose")
    private String dose;

    @Column(name = "dosage_form")
    private String dosage_form;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "duration_in_days")
    private String duration_in_days;

    @Column(name = "prn")
    private String prn;

    @Column(name = "continue_on_discharge")
    private String continue_on_discharge;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "medication")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Orders> orders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedication() {
        return medication;
    }

    public Medications medication(String medication) {
        this.medication = medication;
        return this;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getRoute() {
        return route;
    }

    public Medications route(String route) {
        this.route = route;
        return this;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDose() {
        return dose;
    }

    public Medications dose(String dose) {
        this.dose = dose;
        return this;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDosage_form() {
        return dosage_form;
    }

    public Medications dosage_form(String dosage_form) {
        this.dosage_form = dosage_form;
        return this;
    }

    public void setDosage_form(String dosage_form) {
        this.dosage_form = dosage_form;
    }

    public String getFrequency() {
        return frequency;
    }

    public Medications frequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration_in_days() {
        return duration_in_days;
    }

    public Medications duration_in_days(String duration_in_days) {
        this.duration_in_days = duration_in_days;
        return this;
    }

    public void setDuration_in_days(String duration_in_days) {
        this.duration_in_days = duration_in_days;
    }

    public String getPrn() {
        return prn;
    }

    public Medications prn(String prn) {
        this.prn = prn;
        return this;
    }

    public void setPrn(String prn) {
        this.prn = prn;
    }

    public String getContinue_on_discharge() {
        return continue_on_discharge;
    }

    public Medications continue_on_discharge(String continue_on_discharge) {
        this.continue_on_discharge = continue_on_discharge;
        return this;
    }

    public void setContinue_on_discharge(String continue_on_discharge) {
        this.continue_on_discharge = continue_on_discharge;
    }

    public String getStatus() {
        return status;
    }

    public Medications status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public Medications orders(Set<Orders> orders) {
        this.orders = orders;
        return this;
    }

//    public Medications addOrders(Orders orders) {
//        orders.add(orders);
//        orders.setMedication(this);
//        return this;
//    }
//
//    public Medications removeOrders(Orders orders) {
//        orders.remove(orders);
//        orders.setMedication(null);
//        return this;
//    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medications medications = (Medications) o;
        if(medications.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, medications.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Medications{" +
            "id=" + id +
            ", medication='" + medication + "'" +
            ", route='" + route + "'" +
            ", dose='" + dose + "'" +
            ", dosage_form='" + dosage_form + "'" +
            ", frequency='" + frequency + "'" +
            ", duration_in_days='" + duration_in_days + "'" +
            ", prn='" + prn + "'" +
            ", continue_on_discharge='" + continue_on_discharge + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
