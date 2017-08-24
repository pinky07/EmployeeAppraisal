package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.builder.helper.GftIdentifierGenerator;
import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.JobLevel;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link Employee} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class EmployeeBuilder implements ObjectBuilder<Employee> {

    private int id;
    private ApplicationRole applicationRole;
    private JobLevel jobLevel;
    private String firstName;
    private String lastName;
    private String email;
    private String gftIdentifier;

    private boolean idSet;
    private boolean applicationRoleSet;
    private boolean jobLevelSet;
    private boolean firstNameSet;
    private boolean lastNameSet;
    private boolean emailSet;
    private boolean gftIdentifierSet;

    public EmployeeBuilder() {
    }

    public EmployeeBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EmployeeBuilder firstName(String firstName) {
        this.firstName = firstName;
        this.firstNameSet = true;
        return this;
    }

    public EmployeeBuilder lastName(String lastName) {
        this.lastName = lastName;
        this.lastNameSet = true;
        return this;
    }

    public EmployeeBuilder email(String email) {
        this.email = email;
        this.emailSet = true;
        return this;
    }

    public EmployeeBuilder gftIdentifier(String gftIdentifier) {
        this.gftIdentifier = gftIdentifier;
        this.gftIdentifierSet = true;
        return this;
    }

    public EmployeeBuilder applicationRole(ApplicationRole applicationRole) {
        this.applicationRole = applicationRole;
        this.applicationRoleSet = true;
        return this;
    }

    public EmployeeBuilder jobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
        this.jobLevelSet = true;
        return this;
    }

    @Override
    public Employee build() {
        Employee obj = new Employee();
        obj.setId(this.id);
        obj.setFirstName(this.firstName);
        obj.setLastName(this.lastName);
        obj.setGftIdentifier(this.gftIdentifier);
        obj.setEmail(this.email);
        obj.setApplicationRole(this.applicationRole);
        obj.setJobLevel(this.jobLevel);
        return obj;
    }

    @Override
    public Employee buildWithDefaults() {
        Employee obj = new Employee();
        if (this.idSet) obj.setId(this.id);
        obj.setFirstName(this.firstNameSet ? this.firstName : "First Name");
        obj.setLastName(this.lastNameSet ? this.lastName : "Last Name");
        obj.setGftIdentifier(this.gftIdentifierSet ? this.gftIdentifier : GftIdentifierGenerator.next());
        obj.setEmail(this.emailSet ? this.email : UUID.randomUUID().toString() + "@gft.com");
        obj.setApplicationRole(this.applicationRoleSet ? this.applicationRole : new ApplicationRoleBuilder().buildWithDefaults());
        obj.setJobLevel(this.jobLevelSet ? this.jobLevel : new JobLevelBuilder().buildWithDefaults());
        return obj;
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
