package com.gft.employeeappraisal.builder.dto;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import org.apache.commons.lang.NotImplementedException;

/**
 * Builder object for the {@link ApplicationRoleDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class ApplicationRoleDTOBuilder implements ObjectBuilder<ApplicationRoleDTO> {

    private int id;
    private String name;
    private String description;

    public ApplicationRoleDTOBuilder() {
    }

    public ApplicationRoleDTOBuilder id(int id) {
        this.id = id;
        return this;
    }

    public ApplicationRoleDTOBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ApplicationRoleDTOBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public ApplicationRoleDTO build() {
        ApplicationRoleDTO dto = new ApplicationRoleDTO();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setDescription(this.description);
        return dto;
    }

    @Override
    public ApplicationRoleDTO buildWithDefaults() {
        throw new NotImplementedException();
    }
}
