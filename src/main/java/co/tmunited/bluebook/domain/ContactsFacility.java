package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ContactsFacility.
 */
@Entity
@Table(name = "contacts_facility")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contactsfacility")
public class ContactsFacility extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_code")
    private String shortCode;

    @Column(name = "street")
    private String street;

    @Column(name = "street_two")
    private String streetTwo;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private String zip;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "website")
    private String website;

    @Column(name = "notes")
    private String notes;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    @Column(name = "email")
    private String email;

    @Column(name = "cellphone")
    private String cellphone;

//    @Lob
//    @Column(name = "picture")
//    private byte[] picture;
//
//    @Column(name = "picture_content_type")
//    private String pictureContentType;

    @ManyToOne
    private Facility facility;

    @ManyToOne
    private TypeContactFacilityType typeContactFacilityType;

    @ManyToOne
    private CountryState countryState;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private Picture pictureId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ContactsFacility name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public ContactsFacility shortCode(String shortCode) {
        this.shortCode = shortCode;
        return this;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getStreet() {
        return street;
    }

    public ContactsFacility street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetTwo() {
        return streetTwo;
    }

    public ContactsFacility streetTwo(String streetTwo) {
        this.streetTwo = streetTwo;
        return this;
    }

    public void setStreetTwo(String streetTwo) {
        this.streetTwo = streetTwo;
    }

    public String getCity() {
        return city;
    }

    public ContactsFacility city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public ContactsFacility zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public ContactsFacility phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public ContactsFacility fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public ContactsFacility website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNotes() {
        return notes;
    }

    public ContactsFacility notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public ContactsFacility status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public ContactsFacility email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public ContactsFacility cellphone(String cellphone) {
        this.cellphone = cellphone;
        return this;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Facility getFacility() {
        return facility;
    }

    public ContactsFacility facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public TypeContactFacilityType getTypeContactFacilityType() {
        return typeContactFacilityType;
    }

    public ContactsFacility typeContactFacilityType(TypeContactFacilityType typeContactFacilityType) {
        this.typeContactFacilityType = typeContactFacilityType;
        return this;
    }

    public void setTypeContactFacilityType(TypeContactFacilityType typeContactFacilityType) {
        this.typeContactFacilityType = typeContactFacilityType;
    }

    public CountryState getCountryState() {
        return countryState;
    }

    public ContactsFacility countryState(CountryState countryState) {
        this.countryState = countryState;
        return this;
    }

    public void setCountryState(CountryState countryState) {
        this.countryState = countryState;
    }

    public Picture getPictureId() {
        return pictureId;
    }

    public void setPictureId(Picture pictureId) {
        this.pictureId = pictureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactsFacility contactsFacility = (ContactsFacility) o;
        if(contactsFacility.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, contactsFacility.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ContactsFacility{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", shortCode='" + shortCode + "'" +
            ", street='" + street + "'" +
            ", streetTwo='" + streetTwo + "'" +
            ", city='" + city + "'" +
            ", zip='" + zip + "'" +
            ", phone='" + phone + "'" +
            ", fax='" + fax + "'" +
            ", website='" + website + "'" +
            ", notes='" + notes + "'" +
            ", status='" + status + "'" +
            ", email='" + email + "'" +
            ", cellphone='" + cellphone + "'" +
            '}';
    }
}
