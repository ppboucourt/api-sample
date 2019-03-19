package co.tmunited.bluebook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Vendors.
 */
@Entity
@Table(name = "vendors")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vendors")
public class Vendors extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_name")
    private String company_name;

    @Column(name = "use_this_vendor")
    private String use_this_vendor;

    @Column(name = "contact_name")
    private String contact_name;

    @Column(name = "contact_phone")
    private String contact_phone;

    @Column(name = "address")
    private String address;

    @Column(name = "address_two")
    private String address_two;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "company_phone")
    private String company_phone;

    @Column(name = "company_fax")
    private String company_fax;

    @Column(name = "notes")
    private String notes;

    @Column(name = "fax_order_number")
    private String fax_order_number;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public Vendors company_name(String company_name) {
        this.company_name = company_name;
        return this;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getUse_this_vendor() {
        return use_this_vendor;
    }

    public Vendors use_this_vendor(String use_this_vendor) {
        this.use_this_vendor = use_this_vendor;
        return this;
    }

    public void setUse_this_vendor(String use_this_vendor) {
        this.use_this_vendor = use_this_vendor;
    }

    public String getContact_name() {
        return contact_name;
    }

    public Vendors contact_name(String contact_name) {
        this.contact_name = contact_name;
        return this;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public Vendors contact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
        return this;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getAddress() {
        return address;
    }

    public Vendors address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_two() {
        return address_two;
    }

    public Vendors address_two(String address_two) {
        this.address_two = address_two;
        return this;
    }

    public void setAddress_two(String address_two) {
        this.address_two = address_two;
    }

    public String getCity() {
        return city;
    }

    public Vendors city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Vendors state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public Vendors zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCompany_phone() {
        return company_phone;
    }

    public Vendors company_phone(String company_phone) {
        this.company_phone = company_phone;
        return this;
    }

    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone;
    }

    public String getCompany_fax() {
        return company_fax;
    }

    public Vendors company_fax(String company_fax) {
        this.company_fax = company_fax;
        return this;
    }

    public void setCompany_fax(String company_fax) {
        this.company_fax = company_fax;
    }

    public String getNotes() {
        return notes;
    }

    public Vendors notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFax_order_number() {
        return fax_order_number;
    }

    public Vendors fax_order_number(String fax_order_number) {
        this.fax_order_number = fax_order_number;
        return this;
    }

    public void setFax_order_number(String fax_order_number) {
        this.fax_order_number = fax_order_number;
    }

    public String getStatus() {
        return status;
    }

    public Vendors status(String status) {
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
        Vendors vendors = (Vendors) o;
        if(vendors.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, vendors.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Vendors{" +
            "id=" + id +
            ", company_name='" + company_name + "'" +
            ", use_this_vendor='" + use_this_vendor + "'" +
            ", contact_name='" + contact_name + "'" +
            ", contact_phone='" + contact_phone + "'" +
            ", address='" + address + "'" +
            ", address_two='" + address_two + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zip='" + zip + "'" +
            ", company_phone='" + company_phone + "'" +
            ", company_fax='" + company_fax + "'" +
            ", notes='" + notes + "'" +
            ", fax_order_number='" + fax_order_number + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
