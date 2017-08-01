package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Entity persistent class that describes a JobFamily table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "JobFamily")
public class JobFamily {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	@Size(max = 40)
	@Column(name = "name", nullable = false, length = 40)
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		JobFamily jobFamily = (JobFamily) o;

		return getId() == jobFamily.getId() &&
				(getName() != null ? getName().equals(jobFamily.getName()) : jobFamily.getName() == null) &&
				(getDescription() != null ? getDescription().equals(jobFamily.getDescription()) : jobFamily.getDescription() == null);
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
		return "JobFamily{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
