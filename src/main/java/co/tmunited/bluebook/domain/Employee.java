package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.Gender;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employee")
public class Employee extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "npi_number")
    private String npiNumber;

    @Column(name = "dea_number")
    private String deaNumber;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "pin", length = 4)
    private String pin;

    @Column(name = "signature")
    private String signature;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Shifts> shifts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employees")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chart> charts = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Corporation corporation;

    @OneToMany(mappedBy = "reviewBy")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GroupSessionDetails> groupSessionDetails = new HashSet<>();

    @ManyToOne
    private TypeEmployee typeEmployee;

    @ManyToMany(mappedBy = "employees")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UrgentIssues> urgentIssues = new HashSet<>();

    @Column(name = "code", length = 255)
    private String code;

    @Column(name = "code_date")
    private ZonedDateTime codeDate;

    @Column(name = "code_remember")
    private Boolean codeRemember;

    @Column(name = "sid", length = 255)
    private String sid;



    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmployeeRememberCode> employeeRememberCodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public Employee mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNpiNumber() {
        return npiNumber;
    }

    public Employee npiNumber(String npiNumber) {
        this.npiNumber = npiNumber;
        return this;
    }

    public void setNpiNumber(String npiNumber) {
        this.npiNumber = npiNumber;
    }

    public String getDeaNumber() {
        return deaNumber;
    }

    public Employee deaNumber(String deaNumber) {
        this.deaNumber = deaNumber;
        return this;
    }

    public void setDeaNumber(String deaNumber) {
        this.deaNumber = deaNumber;
    }

    public String getStatus() {
        return status;
    }

    public Employee status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Shifts> getShifts() {
        return shifts;
    }

    public Employee shifts(Set<Shifts> shifts) {
        this.shifts = shifts;
        return this;
    }

    public Employee addShifts(Shifts shifts) {
        this.shifts.add(shifts);
        shifts.setEmployee(this);
        return this;
    }

    public Employee removeShifts(Shifts shifts) {
        this.shifts.remove(shifts);
        shifts.setEmployee(null);
        return this;
    }

    public void setShifts(Set<Shifts> shifts) {
        this.shifts = shifts;
    }

    public Set<Chart> getCharts() {
        return charts;
    }

    public Employee charts(Set<Chart> charts) {
        this.charts = charts;
        return this;
    }

    public Employee addCharts(Chart chart) {
        charts.add(chart);
        chart.setEmployees(this);
        return this;
    }

    public Employee removeCharts(Chart chart) {
        charts.remove(chart);
        chart.setEmployees(null);
        return this;
    }

    public void setCharts(Set<Chart> charts) {
        this.charts = charts;
    }

    public User getUser() {
        return user;
    }

    public Employee user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Corporation getCorporation() {
        return corporation;
    }

    public Employee corporation(Corporation corporation) {
        this.corporation = corporation;
        return this;
    }

    public void setCorporation(Corporation corporation) {
        this.corporation = corporation;
    }

    public Set<GroupSessionDetails> getGroupSessionDetails() {
        return groupSessionDetails;
    }

    public Employee groupSessionDetails(Set<GroupSessionDetails> groupSessionDetails) {
        this.groupSessionDetails = groupSessionDetails;
        return this;
    }

    public Employee addGroupSessionDetails(GroupSessionDetails groupSessionDetails) {
        this.groupSessionDetails.add(groupSessionDetails);
        groupSessionDetails.setReviewBy(this);
        return this;
    }

    public Employee removeGroupSessionDetails(GroupSessionDetails groupSessionDetails) {
        this.groupSessionDetails.remove(groupSessionDetails);
        groupSessionDetails.setReviewBy(null);
        return this;
    }

    public void setGroupSessionDetails(Set<GroupSessionDetails> groupSessionDetails) {
        this.groupSessionDetails = groupSessionDetails;
    }

    public TypeEmployee getTypeEmployee() {
        return typeEmployee;
    }

    public Employee typeEmployee(TypeEmployee typeEmployee) {
        this.typeEmployee = typeEmployee;
        return this;
    }

    public void setTypeEmployee(TypeEmployee typeEmployee) {
        this.typeEmployee = typeEmployee;
    }

    public Set<UrgentIssues> getUrgentIssues() {
        return urgentIssues;
    }

    public Employee urgentIssues(Set<UrgentIssues> urgentIssues) {
        this.urgentIssues = urgentIssues;
        return this;
    }

    public Employee addUrgentIssues(UrgentIssues urgentIssues) {
        this.urgentIssues.add(urgentIssues);
        urgentIssues.getEmployees().add(this);
        return this;
    }

    public Employee removeUrgentIssues(UrgentIssues urgentIssues) {
        this.urgentIssues.remove(urgentIssues);
        urgentIssues.getEmployees().remove(this);
        return this;
    }

    public Set<EmployeeRememberCode> getEmployeeRememberCodes() {
        return employeeRememberCodes;
    }

    public void setEmployeeRememberCodes(Set<EmployeeRememberCode> employeeRememberCodes) {
        this.employeeRememberCodes = employeeRememberCodes;
    }

    public void setUrgentIssues(Set<UrgentIssues> urgentIssues) {
        this.urgentIssues = urgentIssues;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getCodeDate() {
        return codeDate;
    }

    public void setCodeDate(ZonedDateTime codeDate) {
        this.codeDate = codeDate;
    }

    public Boolean getCodeRemember() {
        return codeRemember;
    }

    public void setCodeRemember(Boolean codeRemember) {
        this.codeRemember = codeRemember;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        if (employee.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "code=" + code +
            "id=" + id +
            ", email='" + email + "'" +
            ", mobile='" + mobile + "'" +
            ", npiNumber='" + npiNumber + "'" +
            ", deaNumber='" + deaNumber + "'" +
            ", status='" + status + "'" +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            '}';
    }
}
