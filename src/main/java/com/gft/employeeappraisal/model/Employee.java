package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity persistent class that describes an Employee table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "Employee", uniqueConstraints = {
        @UniqueConstraint(columnNames = "GFTIdentifier")})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "firstName", nullable = false, length = 20)
    private String firstName;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @NotEmpty
    @Size(min = 4, max = 4)
    @Column(name = "GFTIdentifier", nullable = false, length = 4, unique = true)
    private String gftIdentifier;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobLevelId", nullable = false)
    private JobLevel jobLevel;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationRoleId", nullable = false)
    private ApplicationRole applicationRole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGftIdentifier() {
        return gftIdentifier;
    }

    public void setGftIdentifier(String gftIdentifier) {
        this.gftIdentifier = gftIdentifier;
    }

    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
    }

    public ApplicationRole getApplicationRole() {
        return applicationRole;
    }

    public void setApplicationRole(ApplicationRole applicationRole) {
        this.applicationRole = applicationRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return getId() == employee.getId() &&
                (getEmail() != null ? getEmail().equals(employee.getEmail()) : employee.getEmail() == null) &&
                (getFirstName() != null ? getFirstName().equals(employee.getFirstName()) : employee.getFirstName() == null) &&
                (getLastName() != null ? getLastName().equals(employee.getLastName()) : employee.getLastName() == null) &&
                (getGftIdentifier() != null ? getGftIdentifier().equals(employee.getGftIdentifier()) : employee.getGftIdentifier() == null) &&
                (getJobLevel() != null ? getJobLevel().equals(employee.getJobLevel()) : employee.getJobLevel() == null) &&
                (getApplicationRole() != null ? getApplicationRole().equals(employee.getApplicationRole()) : employee.getApplicationRole() == null);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getGftIdentifier() != null ? getGftIdentifier().hashCode() : 0);
        result = 31 * result + (getJobLevel() != null ? getJobLevel().hashCode() : 0);
        result = 31 * result + (getApplicationRole() != null ? getApplicationRole().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gftIdentifier='" + gftIdentifier + '\'' +
                ", jobLevel=" + jobLevel +
                ", applicationRole=" + applicationRole +
                '}';
    }
}
