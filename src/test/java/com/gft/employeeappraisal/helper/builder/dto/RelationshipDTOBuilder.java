package com.gft.employeeappraisal.helper.builder.dto;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.swagger.employees.model.RelationshipDTO;

/**
 * Builder object for the {@link RelationshipDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class RelationshipDTOBuilder implements ObjectBuilder<RelationshipDTO> {

    private int id;
    private String name;
    private String description;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;

    public RelationshipDTOBuilder() {
    }

    public RelationshipDTOBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public RelationshipDTOBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public RelationshipDTOBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    @Override
    public RelationshipDTO build() {
        RelationshipDTO dto = new RelationshipDTO();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setDescription(this.description);
        return dto;
    }

    @Override
    public RelationshipDTO buildWithDefaults() {
        RelationshipDTO dto = new RelationshipDTO();
        if (this.idSet) dto.setId(this.id);
        dto.setName(this.nameSet ? this.name : "Name");
        dto.setDescription(this.descriptionSet ? this.description : "Description");
        return dto;
    }

}
