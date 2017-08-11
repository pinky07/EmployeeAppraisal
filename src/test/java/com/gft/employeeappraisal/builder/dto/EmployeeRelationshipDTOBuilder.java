package com.gft.employeeappraisal.builder.dto;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.RelationshipDTO;
import org.mockito.Mockito;

import java.time.OffsetDateTime;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link EmployeeRelationshipDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class EmployeeRelationshipDTOBuilder implements ObjectBuilder<EmployeeRelationshipDTO> {

    private int id;
    private EmployeeDTO reference;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private RelationshipDTO relationshipDTO;

    public EmployeeRelationshipDTOBuilder() {}

    public EmployeeRelationshipDTOBuilder id(int id) {
    	this.id = id;
    	return this;
	}

	public EmployeeRelationshipDTOBuilder reference(EmployeeDTO reference) {
    	this.reference = reference;
    	return this;
	}

	public EmployeeRelationshipDTOBuilder startDate(OffsetDateTime startDate) {
    	this.startDate = startDate;
    	return this;
	}

	public EmployeeRelationshipDTOBuilder endDate(OffsetDateTime endDate) {
    	this.endDate = endDate;
    	return this;
	}

	public EmployeeRelationshipDTOBuilder relationship(RelationshipDTO relationshipDTO) {
    	this.relationshipDTO = relationshipDTO;
    	return this;
	}

    @Override
    public EmployeeRelationshipDTO build() {
        EmployeeRelationshipDTO dto = new EmployeeRelationshipDTO();
        dto.setId(this.id);
        dto.setStartDate(this.startDate);
        dto.setEndDate(this.endDate);
        dto.setReference(this.reference);
        dto.setRelationship(this.relationshipDTO);
        return dto;
    }

    @Override
    public EmployeeRelationshipDTO buildMock() {
        EmployeeRelationshipDTO mock = Mockito.mock(EmployeeRelationshipDTO.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getStartDate()).thenReturn(this.startDate);
        when(mock.getEndDate()).thenReturn(this.endDate);
        when(mock.getReference()).thenReturn(this.reference);
        when(mock.getRelationship()).thenReturn(this.relationshipDTO);
        return mock;
    }
}
