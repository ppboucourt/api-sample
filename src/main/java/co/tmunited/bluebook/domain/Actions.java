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
 * A Actions.
 */
@Entity
@Table(name = "actions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "actions")
public class Actions extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "action")
    private String action;

    @Column(name = "show_in_mars")
    private String show_in_mars;

    @Column(name = "prn")
    private String prn;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    @OneToMany(mappedBy = "actions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChartToAction> chartToActions = new HashSet<>();

    @OneToMany(mappedBy = "action")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Orders> orders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public Actions action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getShow_in_mars() {
        return show_in_mars;
    }

    public Actions show_in_mars(String show_in_mars) {
        this.show_in_mars = show_in_mars;
        return this;
    }

    public void setShow_in_mars(String show_in_mars) {
        this.show_in_mars = show_in_mars;
    }

    public String getPrn() {
        return prn;
    }

    public Actions prn(String prn) {
        this.prn = prn;
        return this;
    }

    public void setPrn(String prn) {
        this.prn = prn;
    }

    public String getStatus() {
        return status;
    }

    public Actions status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<ChartToAction> getChartToActions() {
        return chartToActions;
    }

    public Actions chartToActions(Set<ChartToAction> chartToActions) {
        this.chartToActions = chartToActions;
        return this;
    }

    public Actions addChartToActions(ChartToAction chartToAction) {
        chartToActions.add(chartToAction);
        chartToAction.setActions(this);
        return this;
    }

    public Actions removeChartToActions(ChartToAction chartToAction) {
        chartToActions.remove(chartToAction);
        chartToAction.setActions(null);
        return this;
    }

    public void setChartToActions(Set<ChartToAction> chartToActions) {
        this.chartToActions = chartToActions;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public Actions orders(Set<Orders> orders) {
        this.orders = orders;
        return this;
    }

//    public Actions addOrders(Orders orders) {
//        orders.add(orders);
//        orders.setAction(this);
//        return this;
//    }
//
//    public Actions removeOrders(Orders orders) {
//        orders.remove(orders);
//        orders.setAction(null);
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
        Actions actions = (Actions) o;
        if(actions.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, actions.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Actions{" +
            "id=" + id +
            ", action='" + action + "'" +
            ", show_in_mars='" + show_in_mars + "'" +
            ", prn='" + prn + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
