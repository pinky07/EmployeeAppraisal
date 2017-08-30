package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Entity persistent class that describes an ApplicationRole table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "ApplicationRole", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class ApplicationRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;

    @OneToMany(mappedBy = "applicationRole", fetch = FetchType.LAZY)
    private Set<Employee> employees;

    @NotEmpty
    @Size(max = 40)
    @Column(name = "name", unique = true, nullable = false, length = 40)
    private String name;

    @NotEmpty
    @Size(max = 500)
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationRole that = (ApplicationRole) o;

        return getId() == that.getId() &&
                (getName() != null ? getName().equals(that.getName()) : that.getName() == null) &&
                (getDescription() != null ? getDescription().equals(that.getDescription()) : that.getDescription() == null);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApplicationRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
