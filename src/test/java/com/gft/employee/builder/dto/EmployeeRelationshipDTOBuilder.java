package com.gft.employee.builder.dto;

import com.gft.employee.builder.ObjectBuilder;
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
    private int peerId;
    private int employeeId;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private RelationshipDTO relationshipDTO;

    @Override
    public EmployeeRelationshipDTO build() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EmployeeRelationshipDTO buildMock() {
        throw new UnsupportedOperationException();
    }
}
