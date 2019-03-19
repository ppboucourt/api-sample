package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import co.tmunited.bluebook.domain.enumeration.Progress;

/**
 * A GroupSession.
 */
@Entity
@Table(name = "group_session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "groupsession")
public class GroupSession extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 3)
    @Column(name = "status", length = 3)
    private String status;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "billable")
    private Boolean billable;

    @Column(name = "name")
    private String name;

    @Column(name = "day_week")
    private String dayWeek;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @OneToMany(mappedBy = "groupSession")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChartToGroupSession> chartToGroupSessions = new HashSet<>();

    @ManyToOne
    private GroupType groupType;

    @ManyToOne
    private Facility facility;

    @OneToMany(mappedBy = "groupSession")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GroupSessionDetails> groupSessionDetails = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public GroupSession status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public GroupSession enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isBillable() {
        return billable;
    }

    public GroupSession billable(Boolean billable) {
        this.billable = billable;
        return this;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public String getName() {
        return name;
    }

    public GroupSession name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public GroupSession dayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
        return this;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public GroupSession startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public GroupSession endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Set<ChartToGroupSession> getChartToGroupSessions() {
        return chartToGroupSessions;
    }

    public GroupSession chartToGroupSessions(Set<ChartToGroupSession> chartToGroupSessions) {
        this.chartToGroupSessions = chartToGroupSessions;
        return this;
    }

    public GroupSession addChartToGroupSessions(ChartToGroupSession chartToGroupSession) {
        chartToGroupSessions.add(chartToGroupSession);
        chartToGroupSession.setGroupSession(this);
        return this;
    }

    public GroupSession removeChartToGroupSessions(ChartToGroupSession chartToGroupSession) {
        chartToGroupSessions.remove(chartToGroupSession);
        chartToGroupSession.setGroupSession(null);
        return this;
    }

    public void setChartToGroupSessions(Set<ChartToGroupSession> chartToGroupSessions) {
        this.chartToGroupSessions = chartToGroupSessions;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public GroupSession groupType(GroupType groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public Facility getFacility() {
        return facility;
    }

    public GroupSession facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Set<GroupSessionDetails> getGroupSessionDetails() {
        return groupSessionDetails;
    }

    public GroupSession groupSessionDetails(Set<GroupSessionDetails> groupSessionDetails) {
        this.groupSessionDetails = groupSessionDetails;
        return this;
    }

//    public GroupSession addGroupSessionDetails(GroupSessionDetails groupSessionDetails) {
//        groupSessionDetails.add(groupSessionDetails);
//        groupSessionDetails.setGroupSession(this);
//        return this;
//    }
//
//    public GroupSession removeGroupSessionDetails(GroupSessionDetails groupSessionDetails) {
//        groupSessionDetails.remove(groupSessionDetails);
//        groupSessionDetails.setGroupSession(null);
//        return this;
//    }

    public void setGroupSessionDetails(Set<GroupSessionDetails> groupSessionDetails) {
        this.groupSessionDetails = groupSessionDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupSession groupSession = (GroupSession) o;
        if(groupSession.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, groupSession.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GroupSession{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", enabled='" + enabled + "'" +
            ", billable='" + billable + "'" +
            ", name='" + name + "'" +
            ", dayWeek='" + dayWeek + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            '}';
    }
}
