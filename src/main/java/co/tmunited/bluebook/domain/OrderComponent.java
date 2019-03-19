package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import co.tmunited.bluebook.domain.enumeration.Frequency;

/**
 * A OrderComponent.
 */
@Entity
@Table(name = "order_component")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ordercomponent")
public class OrderComponent extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "days")
    private Integer days;

    @Column(name = "medication")
    private String medication;

    @Column(name = "dosage_form")
    private String dosage_form;

    @Column(name = "dose")
    private String dose;

    @Column(name = "administration_time")
    private ZonedDateTime administration_time;

    @NotNull
    @Size(max = 3)
    @Column(name = "status", length = 3, nullable = false)
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency")
    private Frequency frequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "all_orders_id", referencedColumnName="id")
    private AllOrders allOrders;

    @ManyToOne
    private Routes routes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDays() {
        return days;
    }

    public OrderComponent days(Integer days) {
        this.days = days;
        return this;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getMedication() {
        return medication;
    }

    public OrderComponent medication(String medication) {
        this.medication = medication;
        return this;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage_form() {
        return dosage_form;
    }

    public OrderComponent dosage_form(String dosage_form) {
        this.dosage_form = dosage_form;
        return this;
    }

    public void setDosage_form(String dosage_form) {
        this.dosage_form = dosage_form;
    }

    public String getDose() {
        return dose;
    }

    public OrderComponent dose(String dose) {
        this.dose = dose;
        return this;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public ZonedDateTime getAdministration_time() {
        return administration_time;
    }

    public OrderComponent administration_time(ZonedDateTime administration_time) {
        this.administration_time = administration_time;
        return this;
    }

    public void setAdministration_time(ZonedDateTime administration_time) {
        this.administration_time = administration_time;
    }

    public String getStatus() {
        return status;
    }

    public OrderComponent status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public OrderComponent frequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public AllOrders getAllOrders() {
        return allOrders;
    }

    public OrderComponent allOrders(AllOrders allOrders) {
        this.allOrders = allOrders;
        return this;
    }

    public void setAllOrders(AllOrders allOrders) {
        this.allOrders = allOrders;
    }

    public Routes getRoutes() {
        return routes;
    }

    public OrderComponent routes(Routes routes) {
        this.routes = routes;
        return this;
    }

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderComponent orderComponent = (OrderComponent) o;
        if(orderComponent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderComponent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderComponent{" +
            "id=" + id +
            ", days='" + days + "'" +
            ", medication='" + medication + "'" +
            ", dosage_form='" + dosage_form + "'" +
            ", dose='" + dose + "'" +
            ", administration_time='" + administration_time + "'" +
            ", status='" + status + "'" +
            ", frequency='" + frequency + "'" +
            '}';
    }
}
