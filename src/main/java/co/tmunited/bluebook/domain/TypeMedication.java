package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TypeMedication.
 */
@Entity
@Table(name = "type_medication")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typemedication")
public class TypeMedication extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "chemical_medication")
    private String chemical_medication;

    @Column(name = "trade_name")
    private String trade_name;

    @Column(name = "status")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChemical_medication() {
        return chemical_medication;
    }

    public TypeMedication chemical_medication(String chemical_medication) {
        this.chemical_medication = chemical_medication;
        return this;
    }

    public void setChemical_medication(String chemical_medication) {
        this.chemical_medication = chemical_medication;
    }

    public String getTrade_name() {
        return trade_name;
    }

    public TypeMedication trade_name(String trade_name) {
        this.trade_name = trade_name;
        return this;
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getStatus() {
        return status;
    }

    public TypeMedication status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeMedication typeMedication = (TypeMedication) o;
        if(typeMedication.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, typeMedication.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TypeMedication{" +
            "id=" + id +
            ", chemical_medication='" + chemical_medication + "'" +
            ", trade_name='" + trade_name + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
