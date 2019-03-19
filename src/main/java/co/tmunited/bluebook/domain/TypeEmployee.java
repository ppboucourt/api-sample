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
 * A TypeEmployee.
 */
@Entity
@Table(name = "type_employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typeemployee")
public class TypeEmployee extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Size(max = 800)
    @Column(name = "description", length = 800)
    private String description;

    @OneToMany(mappedBy = "typeEmployee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "type_employee_authority",
        joinColumns = @JoinColumn(name = "type_employees_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "authorities_name", referencedColumnName = "name"))
    private Set<Authority> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public TypeEmployee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TypeEmployee description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public TypeEmployee employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public TypeEmployee addEmployee(Employee employee) {
        employees.add(employee);
        employee.setTypeEmployee(this);
        return this;
    }

    public TypeEmployee removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setTypeEmployee(null);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }


    public TypeEmployee authorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeEmployee typeEmployee = (TypeEmployee) o;
        if (typeEmployee.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, typeEmployee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TypeEmployee{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
