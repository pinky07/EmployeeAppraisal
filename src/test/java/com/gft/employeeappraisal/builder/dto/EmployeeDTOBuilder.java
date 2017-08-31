package com.gft.employeeappraisal.builder.dto;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.JobLevelDTO;
import org.apache.commons.lang.NotImplementedException;

/**
 * Builder object for the {@link EmployeeDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class EmployeeDTOBuilder implements ObjectBuilder<EmployeeDTO> {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String gftIdentifier;
    private boolean isAdmin;
    private boolean isMentor;
    private boolean isPeer;
    private ApplicationRoleDTO applicationRoleDTO;
    private JobLevelDTO jobLevelDTO;

    public EmployeeDTOBuilder() {
    }

    public EmployeeDTOBuilder id(int id) {
        this.id = id;
        return this;
    }

    public EmployeeDTOBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeDTOBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeDTOBuilder email(String email) {
        this.email = email;
        return this;
    }

    public EmployeeDTOBuilder gftIdentifier(String gftIdentifier) {
        this.gftIdentifier = gftIdentifier;
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

    public EmployeeDTOBuilder applicationRole(ApplicationRoleDTO applicationRoleDTO) {
        this.applicationRoleDTO = applicationRoleDTO;
        return this;
    }

    public EmployeeDTOBuilder jobLevel(JobLevelDTO jobLevelDTO) {
        this.jobLevelDTO = jobLevelDTO;
        return this;
    }

    @Override
    public EmployeeDTO build() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(this.id);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setEmail(this.email);
        dto.setGftIdentifier(this.gftIdentifier);
        dto.setIsAdmin(this.isAdmin);
        dto.setIsPeer(this.isPeer);
        dto.setIsMentor(this.isMentor);
        dto.setApplicationRole(this.applicationRoleDTO);
        dto.setJobLevel(this.jobLevelDTO);
        return dto;
    }

    @Override
    public EmployeeDTO buildWithDefaults() {
        throw new NotImplementedException();
    }
}
