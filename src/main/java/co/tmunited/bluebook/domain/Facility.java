package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Facility.
 */
@Entity
@Table(name = "facility")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "facility")
public class Facility extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "name")
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "street_two")
    private String street_two;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "county")
    private String county;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "website")
    private String website;

//    @Lob
    @Column(name = "logo")
    private String logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "facility")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Building> buildings = new HashSet<>();

    @OneToMany(mappedBy = "facility", fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactsFacility> contactsFacilitys = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private Corporation corporation;

//    @Column(name = "corporation_id", updatable = false, insertable = false)
//    private Long corporationId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Facility name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public Facility street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet_two() {
        return street_two;
    }

    public Facility street_two(String street_two) {
        this.street_two = street_two;
        return this;
    }

    public void setStreet_two(String street_two) {
        this.street_two = street_two;
    }

    public String getCity() {
        return city;
    }

    public Facility city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Facility state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public Facility zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCounty() {
        return county;
    }

    public Facility county(String county) {
        this.county = county;
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPhone() {
        return phone;
    }

    public Facility phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public Facility fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public Facility website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogo() {
        return logo;
    }

    public Facility logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Facility logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getStatus() {
        return status;
    }

    public Facility status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Building> getBuildings() {
        return buildings;
    }

    public Facility buildings(Set<Building> buildings) {
        this.buildings = buildings;
        return this;
    }

    public Facility addBuildings(Building building) {
        buildings.add(building);
        building.setFacility(this);
        return this;
    }

    public Facility removeBuildings(Building building) {
        buildings.remove(building);
        building.setFacility(null);
        return this;
    }

    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
    }

    public Set<ContactsFacility> getContactsFacilitys() {
        return contactsFacilitys;
    }

    public Facility contactsFacilitys(Set<ContactsFacility> contactsFacilities) {
        this.contactsFacilitys = contactsFacilities;
        return this;
    }

    public Facility addContactsFacilitys(ContactsFacility contactsFacility) {
        contactsFacilitys.add(contactsFacility);
        contactsFacility.setFacility(this);
        return this;
    }

    public Facility removeContactsFacilitys(ContactsFacility contactsFacility) {
        contactsFacilitys.remove(contactsFacility);
        contactsFacility.setFacility(null);
        return this;
    }

    public void setContactsFacilitys(Set<ContactsFacility> contactsFacilities) {
        this.contactsFacilitys = contactsFacilities;
    }

    public Corporation getCorporation() {
        return corporation;
    }

    public Facility corporation(Corporation corporation) {
        this.corporation = corporation;
        return this;
    }

    public void setCorporation(Corporation corporation) {
        this.corporation = corporation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Facility facility = (Facility) o;
        if(facility.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facility.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Facility{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", street='" + street + "'" +
            ", street_two='" + street_two + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zip='" + zip + "'" +
            ", county='" + county + "'" +
            ", phone='" + phone + "'" +
            ", fax='" + fax + "'" +
            ", website='" + website + "'" +
            ", logo='" + logo + "'" +
            ", logoContentType='" + logoContentType + "'" +
            ", status='" + status + "'" +
            '}';
    }

}
