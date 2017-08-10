package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.JobLevel;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link Employee} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class EmployeeBuilder implements ObjectBuilder<Employee, Number> {

    private int id;
    private ApplicationRole applicationRole;
    private JobLevel jobLevel;
    private String firstName;
    private String lastName;
    private String email;
    private String gftIdentifier;

    public EmployeeBuilder() {
    }

    public EmployeeBuilder id(int id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeBuilder email(String email) {
        this.email = email;
        return this;
    }

    public EmployeeBuilder gftIdentifier(String gftIdentifier) {
        this.gftIdentifier = gftIdentifier;
        return this;
    }

    public EmployeeBuilder applicationRole(ApplicationRole applicationRole) {
        this.applicationRole = applicationRole;
        return this;
    }

    public EmployeeBuilder jobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
        return this;
    }

    @Override
    public Employee build() {
        Employee employee = new Employee();
        employee.setId(this.id);
        employee.setFirstName(this.firstName);
        employee.setLastName(this.lastName);
        employee.setGftIdentifier(this.gftIdentifier);
        employee.setEmail(this.email);
        employee.setApplicationRole(this.applicationRole);
        employee.setJobLevel(this.jobLevel);
        return employee;
    }

    @Override
    public Employee buildMock() {
        Employee mock = Mockito.mock(Employee.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getFirstName()).thenReturn(this.firstName);
        when(mock.getLastName()).thenReturn(this.lastName);
        when(mock.getGftIdentifier()).thenReturn(this.gftIdentifier);
        when(mock.getEmail()).thenReturn(this.email);
        when(mock.getApplicationRole()).thenReturn(this.applicationRole);
        when(mock.getJobLevel()).thenReturn(this.jobLevel);
        return mock;
    }
}
