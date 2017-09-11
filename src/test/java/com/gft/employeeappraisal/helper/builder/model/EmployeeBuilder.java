package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.helper.builder.helper.GftIdentifierGenerator;
import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.JobLevel;

import java.util.UUID;

/**
 * Builder object for the {@link Employee} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class EmployeeBuilder implements ObjectBuilder<Employee> {

    private int id;
    private JobLevel jobLevel;
    private ApplicationRole applicationRole;
    private String email;
    private String gftIdentifier;
    private String firstName;
    private String lastName;

    private boolean idSet;
    private boolean jobLevelSet;
    private boolean applicationRoleSet;
    private boolean emailSet;
    private boolean gftIdentifierSet;
    private boolean firstNameSet;
    private boolean lastNameSet;

    public EmployeeBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EmployeeBuilder jobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
        this.jobLevelSet = true;
        return this;
    }

    public EmployeeBuilder applicationRole(ApplicationRole applicationRole) {
        this.applicationRole = applicationRole;
        this.applicationRoleSet = true;
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

    @Override
    public Employee build() {
        Employee obj = new Employee();
        obj.setId(this.id);
        obj.setJobLevel(this.jobLevel);
        obj.setApplicationRole(this.applicationRole);
        obj.setEmail(this.email);
        obj.setGftIdentifier(this.gftIdentifier);
        obj.setFirstName(this.firstName);
        obj.setLastName(this.lastName);
        return obj;
    }

    @Override
    public Employee buildWithDefaults() {
        Employee obj = new Employee();
        if (this.idSet) obj.setId(this.id);
        obj.setJobLevel(this.jobLevelSet ? this.jobLevel : new JobLevelBuilder().buildWithDefaults());
        obj.setApplicationRole(this.applicationRoleSet ? this.applicationRole : new ApplicationRoleBuilder().buildWithDefaults());
        obj.setEmail(this.emailSet ? this.email : UUID.randomUUID().toString() + "@gft.com");
        obj.setGftIdentifier(this.gftIdentifierSet ? this.gftIdentifier : GftIdentifierGenerator.next());
        obj.setFirstName(this.firstNameSet ? this.firstName : "First Name");
        obj.setLastName(this.lastNameSet ? this.lastName : "Last Name");
        return obj;
    }
}
