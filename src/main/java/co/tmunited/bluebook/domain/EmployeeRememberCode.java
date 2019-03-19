package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.FaxState;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A FaxSendLog.
 */
@Entity
@Table(name = "employee_remember_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employeeremembercode")
public class EmployeeRememberCode extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "send_date")
    private ZonedDateTime sendDate;

    @Column(name = "code_remember")
    private Boolean codeRemember;

    @Column(name = "sid")
    private String sid;

    @Column(name = "browser_uuid")
    private String browserUuid;

    @ManyToOne
    private Employee employee;

    public EmployeeRememberCode(){}

    public EmployeeRememberCode(Long id, String code, ZonedDateTime sendDate, Boolean codeRemember, String sid, String browserUuid) {
        this.id = id;
        this.code = code;
        this.sendDate = sendDate;
        this.codeRemember = codeRemember;
        this.sid = sid;
        this.browserUuid = browserUuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(ZonedDateTime sendDate) {
        this.sendDate = sendDate;
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

    public String getBrowserUuid() {
        return browserUuid;
    }

    public void setBrowserUuid(String browserUuid) {
        this.browserUuid = browserUuid;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
