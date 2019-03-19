package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CareTeam.
 */
@Entity
@Table(name = "bar_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "barcode")
public class BarCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="bar_code_id_seq")
    @SequenceGenerator(
        name="bar_code_id_seq",
        sequenceName="bar_code_id_seq",
        allocationSize=20
    )
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BarCode careTeam = (BarCode) o;
        if(careTeam.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, careTeam.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BarCode{" +
            "id=" + id +
            '}';
    }
}
