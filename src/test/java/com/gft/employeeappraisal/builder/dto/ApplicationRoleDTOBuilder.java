package com.gft.employeeappraisal.builder.dto;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.swagger.employees.model.ApplicationRoleDTO;

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

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;

    public ApplicationRoleDTOBuilder() {
    }

    public ApplicationRoleDTOBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public ApplicationRoleDTOBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public ApplicationRoleDTOBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
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
        ApplicationRoleDTO dto = new ApplicationRoleDTO();
        if (this.idSet) dto.setId(this.id);
        dto.setName(this.nameSet ? this.name : "Name");
        dto.setDescription(this.descriptionSet ? this.description : "Description");
        return dto;
    }
}
