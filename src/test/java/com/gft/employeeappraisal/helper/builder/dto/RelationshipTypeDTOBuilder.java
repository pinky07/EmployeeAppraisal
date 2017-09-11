package com.gft.employeeappraisal.helper.builder.dto;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.swagger.employees.model.RelationshipTypeDTO;

/**
 * Builder object for the {@link RelationshipTypeDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class RelationshipTypeDTOBuilder implements ObjectBuilder<RelationshipTypeDTO> {

    private int id;
    private String name;
    private String description;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;

    public RelationshipTypeDTOBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public RelationshipTypeDTOBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public RelationshipTypeDTOBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    @Override
    public RelationshipTypeDTO build() {
        RelationshipTypeDTO dto = new RelationshipTypeDTO();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setDescription(this.description);
        return dto;
    }

    @Override
    public RelationshipTypeDTO buildWithDefaults() {
        RelationshipTypeDTO dto = new RelationshipTypeDTO();
        if (this.idSet) dto.setId(this.id);
        dto.setName(this.nameSet ? this.name : "Name");
        dto.setDescription(this.descriptionSet ? this.description : "Description");
        return dto;
    }
}
