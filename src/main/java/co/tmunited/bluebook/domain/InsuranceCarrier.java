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
 * A InsuranceCarrier.
 */
@Entity
@Table(name = "insurance_carrier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "insurancecarrier")
public class InsuranceCarrier extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "acctno", nullable = false)
    private String acctno;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "phone")
    private String phone;

    @Column(name = "i_type")
    private String iType;

    @Column(name = "etin")
    private String etin;

    @OneToMany(mappedBy = "insuranceCarrier")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Insurance> insurances = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcctno() {
        return acctno;
    }

    public InsuranceCarrier acctno(String acctno) {
        this.acctno = acctno;
        return this;
    }

    public void setAcctno(String acctno) {
        this.acctno = acctno;
    }

    public String getName() {
        return name;
    }

    public InsuranceCarrier name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public InsuranceCarrier address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public InsuranceCarrier address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public InsuranceCarrier city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public InsuranceCarrier state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public InsuranceCarrier zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public InsuranceCarrier phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getiType() {
        return iType;
    }

    public InsuranceCarrier iType(String iType) {
        this.iType = iType;
        return this;
    }

    public void setiType(String iType) {
        this.iType = iType;
    }

    public String getEtin() {
        return etin;
    }

    public InsuranceCarrier etin(String etin) {
        this.etin = etin;
        return this;
    }

    public void setEtin(String etin) {
        this.etin = etin;
    }

    public Set<Insurance> getInsurances() {
        return insurances;
    }

    public InsuranceCarrier insurances(Set<Insurance> insurances) {
        this.insurances = insurances;
        return this;
    }

//    public InsuranceCarrier addInsurance(Insurance insurance) {
//        insurances.add(insurance);
//        insurance.setInsuranceCarrier(this);
//        return this;
//    }
//
//    public InsuranceCarrier removeInsurance(Insurance insurance) {
//        insurances.remove(insurance);
//        insurance.setInsuranceCarrier(null);
//        return this;
//    }

    public void setInsurances(Set<Insurance> insurances) {
        this.insurances = insurances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsuranceCarrier insuranceCarrier = (InsuranceCarrier) o;
        if(insuranceCarrier.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, insuranceCarrier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InsuranceCarrier{" +
            "id=" + id +
            ", acctno='" + acctno + "'" +
            ", name='" + name + "'" +
            ", address='" + address + "'" +
            ", address2='" + address2 + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zip='" + zip + "'" +
            ", phone='" + phone + "'" +
            ", iType='" + iType + "'" +
            ", etin='" + etin + "'" +
            '}';
    }
}
