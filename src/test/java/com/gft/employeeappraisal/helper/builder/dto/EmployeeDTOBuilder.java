package com.gft.employeeappraisal.helper.builder.dto;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.helper.builder.util.GftIdentifierGenerator;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.JobLevelDTO;

import java.util.UUID;

/**
 * Builder object for the {@link EmployeeDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class EmployeeDTOBuilder implements ObjectBuilder<EmployeeDTO> {

    private int id;
    private JobLevelDTO jobLevelDTO;
    private ApplicationRoleDTO applicationRoleDTO;
    private String email;
    private String gftIdentifier;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
    private boolean isMentor;
    private boolean isPeer;

    private boolean idSet;
    private boolean jobLevelSet;
    private boolean applicationRoleSet;
    private boolean emailSet;
    private boolean gftIdentifierSet;
    private boolean firstNameSet;
    private boolean lastNameSet;

    public EmployeeDTOBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EmployeeDTOBuilder jobLevel(JobLevelDTO jobLevelDTO) {
        this.jobLevelDTO = jobLevelDTO;
        this.jobLevelSet = true;
        return this;
    }

    public EmployeeDTOBuilder applicationRole(ApplicationRoleDTO applicationRoleDTO) {
        this.applicationRoleDTO = applicationRoleDTO;
        this.applicationRoleSet = true;
        return this;
    }

    public EmployeeDTOBuilder email(String email) {
        this.email = email;
        this.emailSet = true;
        return this;
    }

    public EmployeeDTOBuilder gftIdentifier(String gftIdentifier) {
        this.gftIdentifier = gftIdentifier;
        this.gftIdentifierSet = true;
        return this;
    }

    public EmployeeDTOBuilder firstName(String firstName) {
        this.firstName = firstName;
        this.firstNameSet = true;
        return this;
    }

    public EmployeeDTOBuilder lastName(String lastName) {
        this.lastName = lastName;
        this.lastNameSet = true;
        return this;
    }

    public EmployeeDTOBuilder isAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public EmployeeDTOBuilder isMentor(boolean isMentor) {
        this.isMentor = isMentor;
        return this;
    }

    public EmployeeDTOBuilder isPeer(boolean isPeer) {
        this.isPeer = isPeer;
        return this;
    }

    @Override
    public EmployeeDTO build() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(this.id);
        dto.setJobLevel(this.jobLevelDTO);
        dto.setApplicationRole(this.applicationRoleDTO);
        dto.setEmail(this.email);
        dto.setGftIdentifier(this.gftIdentifier);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setIsAdmin(this.isAdmin);
        dto.setIsMentor(this.isMentor);
        dto.setIsPeer(this.isPeer);
        return dto;
    }

    @Override
    public EmployeeDTO buildWithDefaults() {
        EmployeeDTO dto = new EmployeeDTO();
        if (this.idSet) dto.setId(this.id);
        dto.setJobLevel(this.jobLevelSet ? this.jobLevelDTO : new JobLevelDTOBuilder().buildWithDefaults());
        dto.setApplicationRole(this.applicationRoleSet ? this.applicationRoleDTO :
                new ApplicationRoleDTOBuilder().buildWithDefaults());
        dto.setEmail(this.emailSet ? this.email : UUID.randomUUID().toString() + "@gft.com");
        dto.setGftIdentifier(this.gftIdentifierSet ? this.gftIdentifier : GftIdentifierGenerator.next());
        dto.setFirstName(this.firstNameSet ? this.firstName : "First Name");
        dto.setLastName(this.lastNameSet ? this.lastName : "Last Name");
        dto.setIsAdmin(this.isAdmin);
        dto.setIsMentor(this.isMentor);
        dto.setIsPeer(this.isPeer);
        return dto;
    }
}
