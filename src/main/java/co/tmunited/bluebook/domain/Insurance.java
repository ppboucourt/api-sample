package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Insurance.
 */
@Entity
@Table(name = "insurance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "insurance")
public class Insurance extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "zip_code")
    private String zipCode;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "group_number")
    private String groupNumber;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "plan_number")
    private String planNumber;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Size(max = 1)
    @Column(name = "middle_initial", length = 1)
    private String middleInitial;

    @Column(name = "dob")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Size(min = 10, max = 10)
    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "employer")
    private String employer;

    @Column(name = "insurance_order")
    private Integer insuranceOrder;

    @Column(name = "state")
    private String state;

    @Column(name = "social_security")
    private String socialSecurity;

    @Column(name = "pre_cert_phone")
    private String preCertPhone;

    @ManyToOne
    private InsuranceCarrier insuranceCarrier;

    @ManyToOne
    private InsuranceType insuranceType;

    @ManyToOne
    private InsuranceLevel insuranceLevel;

    @ManyToOne
    private InsuranceRelation insuranceRelation;

    @ManyToOne
    private CountryState countryState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "chart_id", referencedColumnName = "id")
    private Chart chart;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "front_picture_id", referencedColumnName = "id")
    private Picture frontPicture;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "back_picture_id", referencedColumnName = "id")
    private Picture backPicture;

    @Column(name = "rxbin")
    private String rxBin;

    @Column(name = "pcn")
    private String pcn;

    @Column(name = "member_id")
    private String memberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public Insurance address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public Insurance address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Insurance zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public Insurance city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public Insurance policyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
        return this;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public Insurance groupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
        return this;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public Insurance groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public Insurance planNumber(String planNumber) {
        this.planNumber = planNumber;
        return this;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public Insurance lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Insurance firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public Insurance middleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
        return this;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Insurance dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public Insurance gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public Insurance phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmployer() {
        return employer;
    }

    public Insurance employer(String employer) {
        this.employer = employer;
        return this;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public Integer getInsuranceOrder() {
        return insuranceOrder;
    }

    public Insurance insuranceOrder(Integer insuranceOrder) {
        this.insuranceOrder = insuranceOrder;
        return this;
    }

    public void setInsuranceOrder(Integer insuranceOrder) {
        this.insuranceOrder = insuranceOrder;
    }

    public InsuranceCarrier getInsuranceCarrier() {
        return insuranceCarrier;
    }

    public Insurance insuranceCarrier(InsuranceCarrier insuranceCarrier) {
        this.insuranceCarrier = insuranceCarrier;
        return this;
    }

    public void setInsuranceCarrier(InsuranceCarrier insuranceCarrier) {
        this.insuranceCarrier = insuranceCarrier;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public Insurance insuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
        return this;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    public InsuranceLevel getInsuranceLevel() {
        return insuranceLevel;
    }

    public Insurance insuranceLevel(InsuranceLevel insuranceLevel) {
        this.insuranceLevel = insuranceLevel;
        return this;
    }

    public void setInsuranceLevel(InsuranceLevel insuranceLevel) {
        this.insuranceLevel = insuranceLevel;
    }

    public InsuranceRelation getInsuranceRelation() {
        return insuranceRelation;
    }

    public Insurance insuranceRelation(InsuranceRelation insuranceRelation) {
        this.insuranceRelation = insuranceRelation;
        return this;
    }

    public void setInsuranceRelation(InsuranceRelation insuranceRelation) {
        this.insuranceRelation = insuranceRelation;
    }

    public CountryState getCountryState() {
        return countryState;
    }

    public Insurance countryState(CountryState countryState) {
        this.countryState = countryState;
        return this;
    }

    public void setCountryState(CountryState countryState) {
        this.countryState = countryState;
    }

    public Chart getChart() {
        return chart;
    }

    public Insurance chart(Chart chart) {
        this.chart = chart;
        return this;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getPreCertPhone() {
        return preCertPhone;
    }

    public void setPreCertPhone(String preCertPhone) {
        this.preCertPhone = preCertPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Insurance insurance = (Insurance) o;
        if (insurance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, insurance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Insurance{" +
            "id=" + id +
            ", address='" + address + "'" +
            ", address2='" + address2 + "'" +
            ", zipCode='" + zipCode + "'" +
            ", city='" + city + "'" +
            ", policyNumber='" + policyNumber + "'" +
            ", groupNumber='" + groupNumber + "'" +
            ", groupName='" + groupName + "'" +
            ", planNumber='" + planNumber + "'" +
            ", lastName='" + lastName + "'" +
            ", firstName='" + firstName + "'" +
            ", middleInitial='" + middleInitial + "'" +
            ", dob='" + dob + "'" +
            ", gender='" + gender + "'" +
            ", phone='" + phone + "'" +
            ", employer='" + employer + "'" +
            ", insuranceOrder='" + insuranceOrder + "'" +
            ", socialSecurity='" + socialSecurity + "'" +
            ", preCertPhone='" + preCertPhone + "'" +
            '}';
    }

    public Picture getBackPicture() {
        return backPicture;
    }

    public void setBackPicture(Picture backPicture) {
        this.backPicture = backPicture;
    }

    public Picture getFrontPicture() {
        return frontPicture;
    }

    public void setFrontPicture(Picture frontPicture) {
        this.frontPicture = frontPicture;
    }

    public String getRxBin() {
        return rxBin;
    }

    public void setRxBin(String rxBin) {
        this.rxBin = rxBin;
    }

    public String getPcn() {
        return pcn;
    }

    public void setPcn(String pcn) {
        this.pcn = pcn;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
