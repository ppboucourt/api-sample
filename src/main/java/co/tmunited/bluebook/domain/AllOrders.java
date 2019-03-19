package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AllOrders.
 */
@Entity
@Table(name = "all_orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "allorders")
public class AllOrders extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "prn")
    private Boolean prn;

    @Column(name = "indication")
    private String indication;

    @Column(name = "duration_days")
    private Integer duration_days;

    @Column(name = "note")
    private String note;

    @NotNull
    @Size(max = 3)
    @Column(name = "status", length = 3, nullable = false)
    private String status;

    @ManyToOne
    private TypeDosage typeDosage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Facility facility;

    @OneToMany(mappedBy = "allOrders")
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderComponent> orderComponents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AllOrders name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public AllOrders enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isPrn() {
        return prn;
    }

    public AllOrders prn(Boolean prn) {
        this.prn = prn;
        return this;
    }

    public void setPrn(Boolean prn) {
        this.prn = prn;
    }

    public String getIndication() {
        return indication;
    }

    public AllOrders indication(String indication) {
        this.indication = indication;
        return this;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public Integer getDuration_days() {
        return duration_days;
    }

    public AllOrders duration_days(Integer duration_days) {
        this.duration_days = duration_days;
        return this;
    }

    public void setDuration_days(Integer duration_days) {
        this.duration_days = duration_days;
    }

    public String getNote() {
        return note;
    }

    public AllOrders note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public AllOrders status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TypeDosage getTypeDosage() {
        return typeDosage;
    }

    public AllOrders typeDosage(TypeDosage typeDosage) {
        this.typeDosage = typeDosage;
        return this;
    }

    public void setTypeDosage(TypeDosage typeDosage) {
        this.typeDosage = typeDosage;
    }

    public Facility getFacility() {
        return facility;
    }

    public AllOrders facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Set<OrderComponent> getOrderComponents() {
        return orderComponents;
    }

    public AllOrders orderComponents(Set<OrderComponent> orderComponents) {
        this.orderComponents = orderComponents;
        return this;
    }

    public AllOrders addOrderComponent(OrderComponent orderComponent) {
        orderComponents.add(orderComponent);
        orderComponent.setAllOrders(this);
        return this;
    }

    public AllOrders removeOrderComponent(OrderComponent orderComponent) {
        orderComponents.remove(orderComponent);
        orderComponent.setAllOrders(null);
        return this;
    }

    public void setOrderComponents(Set<OrderComponent> orderComponents) {
        this.orderComponents = orderComponents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AllOrders allOrders = (AllOrders) o;
        if(allOrders.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, allOrders.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AllOrders{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", enabled='" + enabled + "'" +
            ", prn='" + prn + "'" +
            ", indication='" + indication + "'" +
            ", duration_days='" + duration_days + "'" +
            ", note='" + note + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
