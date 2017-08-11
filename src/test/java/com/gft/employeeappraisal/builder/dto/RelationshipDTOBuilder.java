package com.gft.employeeappraisal.builder.dto;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.swagger.employees.model.RelationshipDTO;
import org.apache.commons.lang.NotImplementedException;

import static org.mockito.Mockito.when;

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

    public RelationshipDTOBuilder() {
    }

    public RelationshipDTOBuilder id(int id) {
        this.id = id;
        return this;
    }

    public RelationshipDTOBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RelationshipDTOBuilder description(String description) {
        this.description = description;
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
        throw new NotImplementedException();
    }

    @Override
    public RelationshipDTO buildMock() {
        RelationshipDTO mock = new RelationshipDTO();
        when(mock.getId()).thenReturn(this.id);
        when(mock.getName()).thenReturn(this.name);
        when(mock.getDescription()).thenReturn(this.description);
        return mock;
    }
}
