package com.gft.employeeappraisal.helper.builder.dto;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.RelationshipTypeDTO;

import java.time.OffsetDateTime;

/**
 * Builder object for the {@link EmployeeRelationshipDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class EmployeeRelationshipDTOBuilder implements ObjectBuilder<EmployeeRelationshipDTO> {

    private int id;
    private EmployeeDTO referred;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private RelationshipTypeDTO relationshipTypeDTO;

    private boolean idSet;
    private boolean referredSet;
    private boolean startDateSet;
    private boolean endDateSet;
    private boolean relationshipTypeSet;

    public EmployeeRelationshipDTOBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EmployeeRelationshipDTOBuilder referred(EmployeeDTO reference) {
        this.referred = reference;
        this.referredSet = true;
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

    public EmployeeRelationshipDTOBuilder relationship(RelationshipTypeDTO relationshipDTO) {
        this.relationshipTypeDTO = relationshipDTO;
        this.relationshipTypeSet = true;
        return this;
    }

    @Override
    public EmployeeRelationshipDTO build() {
        EmployeeRelationshipDTO dto = new EmployeeRelationshipDTO();
        dto.setId(this.id);
        dto.setStartDate(this.startDate);
        dto.setEndDate(this.endDate);
        dto.setReferred(this.referred);
        dto.setRelationshipType(this.relationshipTypeDTO);
        return dto;
    }

    @Override
    public EmployeeRelationshipDTO buildWithDefaults() {
        EmployeeRelationshipDTO dto = new EmployeeRelationshipDTO();
        if (this.idSet) dto.setId(this.id);
        dto.setStartDate(this.startDateSet ? this.startDate : OffsetDateTime.now().minusDays(1));
        dto.setEndDate(this.endDateSet ? this.endDate : null);
        dto.setReferred(this.referredSet ? this.referred : new EmployeeDTOBuilder().buildWithDefaults());
        dto.setRelationshipType(this.relationshipTypeSet ? this.relationshipTypeDTO : new RelationshipTypeDTOBuilder().buildWithDefaults());
        return dto;
    }
}
