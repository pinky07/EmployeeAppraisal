package com.gft.employeeappraisal.converter.employeerelationship;

import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.employeeappraisal.service.RelationshipService;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.RelationshipDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * Defines a mapping structure to be used by {@link EmployeeRelationshipDTOConverter}.
 *
 * @author Manuel Yepez
 */
@Component
public class EmployeeRelationshipDTOMapper extends CustomMapper<EmployeeRelationship, EmployeeRelationshipDTO> {

    private EmployeeService employeeService;
    private RelationshipService relationshipService;

    @Autowired
    public EmployeeRelationshipDTOMapper(
            EmployeeService employeeService,
            RelationshipService relationshipService) {
        this.employeeService = employeeService;
        this.relationshipService = relationshipService;
    }

    @Override
    public void mapAtoB(EmployeeRelationship employeeRelationship, EmployeeRelationshipDTO employeeRelationshipDTO,
                        MappingContext context) {
        employeeRelationshipDTO.setId(employeeRelationship.getId());
        employeeRelationshipDTO.setStartDate(OffsetDateTime.of(employeeRelationship.getStartDate(), ZoneOffset.UTC));
        if (employeeRelationship.getEndDate() != null) {
            employeeRelationshipDTO.setEndDate(OffsetDateTime.of(employeeRelationship.getEndDate(), ZoneOffset.UTC));
        }
        employeeRelationshipDTO.setReferred(mapperFacade.map(employeeRelationship.getTargetEmployee(),
                EmployeeDTO.class));
        employeeRelationshipDTO.setRelationship(mapperFacade.map(employeeRelationship.getRelationship(),
                RelationshipDTO.class));
    }

    @Override
    public void mapBtoA(EmployeeRelationshipDTO employeeRelationshipDTO, EmployeeRelationship employeeRelationship,
                        MappingContext context) {
        if (Objects.nonNull(employeeRelationshipDTO.getId()) && employeeRelationshipDTO.getId() > 0) {
            employeeRelationship.setId(employeeRelationshipDTO.getId());
        }
        if (Objects.nonNull(employeeRelationshipDTO.getStartDate())) {
            employeeRelationship.setStartDate(employeeRelationshipDTO.getStartDate().toLocalDateTime());
        }
        if (Objects.nonNull(employeeRelationshipDTO.getEndDate())) {
            employeeRelationship.setEndDate(employeeRelationshipDTO.getEndDate().toLocalDateTime());
        }
        employeeRelationship.setRelationship(this.relationshipService.getById(employeeRelationshipDTO.getRelationship().getId()));
        // Remember that the DTO doesn't have a Source Employee equivalent field.
        // This responsability is left for whoever is using the mapper.
        employeeRelationship.setTargetEmployee(this.employeeService.getById(employeeRelationshipDTO.getReferred().getId()));
    }
}
