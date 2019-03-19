package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Contacts.
 */
@Entity
@Table(name = "contacts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contacts")
public class Contacts extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "notes")
    private String notes;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "support_system")
    private Boolean supportSystem;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "alt_phone")
    private String altPhone;

    @Column(name = "address_two")
    private String addressTwo;

    @JoinColumn(name = "chart_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Chart chart;

    @Column(name = "chart_id")
    private Long chartId;

    @ManyToOne
    private TypePatientContactTypes typePatientContactTypes;

    @ManyToOne
    private TypePatientContactsRelationship typePatientContactsRelationship;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public Contacts phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public Contacts email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public Contacts address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public Contacts notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public Contacts status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public Contacts city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Contacts state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public Contacts zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Boolean isSupportSystem() {
        return supportSystem;
    }

    public Contacts supportSystem(Boolean supportSystem) {
        this.supportSystem = supportSystem;
        return this;
    }

    public void setSupportSystem(Boolean supportSystem) {
        this.supportSystem = supportSystem;
    }

    public String getFullName() {
        return fullName;
    }

    public Contacts fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAltPhone() {
        return altPhone;
    }

    public Contacts altPhone(String altPhone) {
        this.altPhone = altPhone;
        return this;
    }

    public void setAltPhone(String altPhone) {
        this.altPhone = altPhone;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public Contacts addressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
        return this;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public Chart getChart() {
        return chart;
    }

    public Contacts chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public TypePatientContactTypes getTypePatientContactTypes() {
        return typePatientContactTypes;
    }

    public Contacts typePatientContactTypes(TypePatientContactTypes typePatientContactTypes) {
        this.typePatientContactTypes = typePatientContactTypes;
        return this;
    }

    public Boolean getSupportSystem() {
        return supportSystem;
    }

    public TypePatientContactsRelationship getTypePatientContactsRelationship() {
        return typePatientContactsRelationship;
    }

    public void setTypePatientContactsRelationship(TypePatientContactsRelationship typePatientContactsRelationship) {
        this.typePatientContactsRelationship = typePatientContactsRelationship;
    }

    public void setTypePatientContactTypes(TypePatientContactTypes typePatientContactTypes) {
        this.typePatientContactTypes = typePatientContactTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contacts contacts = (Contacts) o;
        if(contacts.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, contacts.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Contacts{" +
            "id=" + id +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            ", address='" + address + "'" +
            ", notes='" + notes + "'" +
            ", status='" + status + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zip='" + zip + "'" +
            ", supportSystem='" + supportSystem + "'" +
            ", fullName='" + fullName + "'" +
            ", altPhone='" + altPhone + "'" +
            ", addressTwo='" + addressTwo + "'" +
            '}';
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }
}
