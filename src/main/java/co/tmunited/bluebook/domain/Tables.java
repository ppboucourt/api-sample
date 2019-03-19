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
 * A Tables.
 */
@Entity
@Table(name = "tables")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tables")
public class Tables extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "table_name")
    private String table_name;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    @OneToMany(mappedBy = "table")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Fields> fields = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTable_name() {
        return table_name;
    }

    public Tables table_name(String table_name) {
        this.table_name = table_name;
        return this;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getStatus() {
        return status;
    }

    public Tables status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Fields> getFields() {
        return fields;
    }

    public Tables fields(Set<Fields> fields) {
        this.fields = fields;
        return this;
    }

//    public Tables addFields(Fields fields) {
//        fields.add(fields);
//        fields.setTable(this);
//        return this;
//    }
//
//    public Tables removeFields(Fields fields) {
//        fields.remove(fields);
//        fields.setTable(null);
//        return this;
//    }

    public void setFields(Set<Fields> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tables tables = (Tables) o;
        if(tables.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tables.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tables{" +
            "id=" + id +
            ", table_name='" + table_name + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
