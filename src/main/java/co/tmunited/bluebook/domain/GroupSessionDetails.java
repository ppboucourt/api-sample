package co.tmunited.bluebook.domain;

import co.tmunited.bluebook.domain.enumeration.Progress;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * A GroupSessionDetails.
 */
@Entity
@Table(name = "group_session_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "groupsessiondetails")
public class GroupSessionDetails extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start")
    private ZonedDateTime start;

    @Column(name = "finalized")
    private ZonedDateTime finalized;

    @Size(max = 800)
    @Column(name = "topic", length = 800)
    private String topic;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress")
    private Progress progress;

    @ManyToOne
    private Employee reviewBy;


    @OneToMany(mappedBy = "groupSessionDetails", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<GroupSessionDetailsChart> groupSessionDetailsCharts = new HashSet<>();

    @ManyToOne
    private GroupSession groupSession;

    @ManyToOne
    private Employee leaderEmployee;


    @OneToOne
    @JoinColumn(unique = true)
    private Signature leaderSignature;

    @OneToOne
    @JoinColumn(unique = true)
    private Signature reviserSignature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public GroupSessionDetails start(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getFinalized() {
        return finalized;
    }

    public GroupSessionDetails finalized(ZonedDateTime finalized) {
        this.finalized = finalized;
        return this;
    }

    public void setFinalized(ZonedDateTime finalized) {
        this.finalized = finalized;
    }

    public String getTopic() {
        return topic;
    }

    public GroupSessionDetails topic(String topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Progress getProgress() {
        return progress;
    }

    public GroupSessionDetails progress(Progress progress) {
        this.progress = progress;
        return this;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public Employee getReviewBy() {
        return reviewBy;
    }

    public GroupSessionDetails reviewBy(Employee employee) {
        this.reviewBy = employee;
        return this;
    }

    public void setReviewBy(Employee employee) {
        this.reviewBy = employee;
    }

    public Set<GroupSessionDetailsChart> getGroupSessionDetailsCharts() {
        return groupSessionDetailsCharts;
    }

    public GroupSessionDetails groupSessionDetailsCharts(Set<GroupSessionDetailsChart> groupSessionDetailsCharts) {
        this.groupSessionDetailsCharts = groupSessionDetailsCharts;
        return this;
    }

    public GroupSessionDetails addGroupSessionDetailsChart(GroupSessionDetailsChart groupSessionDetailsChart) {
        groupSessionDetailsCharts.add(groupSessionDetailsChart);
        groupSessionDetailsChart.setGroupSessionDetails(this);
        return this;
    }

    public GroupSessionDetails removeGroupSessionDetailsChart(GroupSessionDetailsChart groupSessionDetailsChart) {
        groupSessionDetailsCharts.remove(groupSessionDetailsChart);
        groupSessionDetailsChart.setGroupSessionDetails(null);
        return this;
    }

    public void setGroupSessionDetailsCharts(Set<GroupSessionDetailsChart> groupSessionDetailsCharts) {
        this.groupSessionDetailsCharts = groupSessionDetailsCharts;
    }

    public GroupSession getGroupSession() {
        return groupSession;
    }

    public GroupSessionDetails groupSession(GroupSession groupSession) {
        this.groupSession = groupSession;
        return this;
    }

    public void setGroupSession(GroupSession groupSession) {
        this.groupSession = groupSession;
    }

    public Employee getLeaderEmployee() {
        return leaderEmployee;
    }

    public GroupSessionDetails leaderEmployee(Employee employee) {
        this.leaderEmployee = employee;
        return this;
    }

    public void setLeaderEmployee(Employee employee) {
        this.leaderEmployee = employee;
    }


    public Signature getLeaderSignature() {
        return leaderSignature;
    }

    public GroupSessionDetails leaderSignature(Signature signature) {
        this.leaderSignature = signature;
        return this;
    }

    public void setLeaderSignature(Signature signature) {
        this.leaderSignature = signature;
    }

    public Signature getReviserSignature() {
        return reviserSignature;
    }

    public GroupSessionDetails reviserSignature(Signature signature) {
        this.reviserSignature = signature;
        return this;
    }

    public void setReviserSignature(Signature signature) {
        this.reviserSignature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupSessionDetails groupSessionDetails = (GroupSessionDetails) o;
        if(groupSessionDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, groupSessionDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GroupSessionDetails{" +
            "id=" + id +
            ", start='" + start + "'" +
            ", finalized='" + finalized + "'" +
            ", topic='" + topic + "'" +
            ", progress='" + progress + "'" +
            '}';
    }
}
