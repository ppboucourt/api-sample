package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GroupType.
 */
@Entity
@Table(name = "group_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "grouptype")
public class GroupType extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "groupType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GroupSession> groupSessions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GroupType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GroupSession> getGroupSessions() {
        return groupSessions;
    }

    public GroupType groupSessions(Set<GroupSession> groupSessions) {
        this.groupSessions = groupSessions;
        return this;
    }

    public GroupType addGroupSession(GroupSession groupSession) {
        groupSessions.add(groupSession);
        groupSession.setGroupType(this);
        return this;
    }

    public GroupType removeGroupSession(GroupSession groupSession) {
        groupSessions.remove(groupSession);
        groupSession.setGroupType(null);
        return this;
    }

    public void setGroupSessions(Set<GroupSession> groupSessions) {
        this.groupSessions = groupSessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupType groupType = (GroupType) o;
        if(groupType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, groupType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GroupType{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
