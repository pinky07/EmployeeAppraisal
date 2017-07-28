package com.gft.employee.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Entity persistent class that describes a Relationship table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "Relationship", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty
	@Size(max = 20)
    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;

    @NotEmpty
	@Size(max = 500)
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relationship")
	private Set<EmployeeRelationship> employeeRelationships;

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

    public Set<EmployeeRelationship> getEmployeeRelationships() {
        return employeeRelationships;
    }

    public void setEmployeeRelationships(Set<EmployeeRelationship> employeeRelationships) {
        this.employeeRelationships = employeeRelationships;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Relationship that = (Relationship) o;

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
		return "Relationship{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}