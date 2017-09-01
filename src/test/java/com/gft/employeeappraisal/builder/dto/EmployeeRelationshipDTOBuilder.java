package com.gft.employeeappraisal.builder.dto;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.RelationshipDTO;

import java.time.OffsetDateTime;

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

    private boolean idSet;
    private boolean referenceSet;
    private boolean startDateSet;
    private boolean endDateSet;
    private boolean relationshipSet;

    public EmployeeRelationshipDTOBuilder() {
    }

    public EmployeeRelationshipDTOBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EmployeeRelationshipDTOBuilder reference(EmployeeDTO reference) {
        this.reference = reference;
        this.referenceSet = true;
        return this;
    }

    public EmployeeRelationshipDTOBuilder startDate(OffsetDateTime startDate) {
        this.startDate = startDate;
        this.startDateSet = true;
        return this;
    }

    public EmployeeRelationshipDTOBuilder endDate(OffsetDateTime endDate) {
        this.endDate = endDate;
        this.endDateSet = true;
        return this;
    }

    public EmployeeRelationshipDTOBuilder relationship(RelationshipDTO relationshipDTO) {
        this.relationshipDTO = relationshipDTO;
        this.relationshipSet = true;
        return this;
    }

    @Override
    public EmployeeRelationshipDTO build() {
        EmployeeRelationshipDTO dto = new EmployeeRelationshipDTO();
        dto.setId(this.id);
        dto.setStartDate(this.startDate);
        dto.setEndDate(this.endDate);
        dto.setReferred(this.reference);
        dto.setRelationship(this.relationshipDTO);
        return dto;
    }

    @Override
    public EmployeeRelationshipDTO buildWithDefaults() {
        EmployeeRelationshipDTO dto = new EmployeeRelationshipDTO();
        if (this.idSet) dto.setId(this.id);
        dto.setStartDate(this.startDateSet ? this.startDate : OffsetDateTime.now().minusDays(1));
        dto.setEndDate(this.endDateSet ? this.endDate : null);
        dto.setReferred(this.referenceSet ? this.reference : new EmployeeDTOBuilder().buildWithDefaults());
        dto.setRelationship(this.relationshipSet ? this.relationshipDTO : new RelationshipDTOBuilder().buildWithDefaults());
        return dto;
    }
}
