package com.gft.employeeappraisal.converter.employeerelationship;

import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.employeeappraisal.service.RelationshipTypeService;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.RelationshipTypeDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Defines a mapping structure to be used by {@link EmployeeRelationshipDTOConverter}.
 *
 * @author Manuel Yepez
 */
@Component
public class EmployeeRelationshipDTOMapper extends CustomMapper<EmployeeRelationship, EmployeeRelationshipDTO> {

    private final EmployeeService employeeService;
    private final RelationshipTypeService relationshipTypeService;

    @Autowired
    public EmployeeRelationshipDTOMapper(
            EmployeeService employeeService,
            RelationshipTypeService relationshipTypeService) {
        this.employeeService = employeeService;
        this.relationshipTypeService = relationshipTypeService;
    }

    @Override
    public void mapAtoB(EmployeeRelationship employeeRelationship, EmployeeRelationshipDTO employeeRelationshipDTO,
                        MappingContext context) {
        employeeRelationshipDTO.setId(employeeRelationship.getId());
        employeeRelationshipDTO.setComments(employeeRelationship.getComments());
        employeeRelationshipDTO.setStartDate(employeeRelationship.getStartDate());
        employeeRelationshipDTO.setComments(employeeRelationship.getComments());
        if (employeeRelationship.getEndDate() != null) {
            employeeRelationshipDTO.setEndDate(employeeRelationship.getEndDate());
        }
        employeeRelationshipDTO.setReferred(mapperFacade.map(employeeRelationship.getTargetEmployee(),
                EmployeeDTO.class));
        employeeRelationshipDTO.setRelationshipType(mapperFacade.map(employeeRelationship.getRelationshipType(),
                RelationshipTypeDTO.class));
    }

    @Override
    public void mapBtoA(EmployeeRelationshipDTO employeeRelationshipDTO, EmployeeRelationship employeeRelationship,
                        MappingContext context) {
        if (Objects.nonNull(employeeRelationshipDTO.getId()) && employeeRelationshipDTO.getId() > 0) {
            employeeRelationship.setId(employeeRelationshipDTO.getId());
        }
        if (Objects.nonNull(employeeRelationshipDTO.getStartDate())) {
            employeeRelationship.setStartDate(employeeRelationshipDTO.getStartDate());
        }
        if (Objects.nonNull(employeeRelationshipDTO.getEndDate())) {
            employeeRelationship.setEndDate(employeeRelationshipDTO.getEndDate());
        }
        employeeRelationship.setComments(employeeRelationshipDTO.getComments());
        employeeRelationship.setRelationshipType(this.relationshipTypeService.getById(employeeRelationshipDTO.getRelationshipType().getId()));
        // Remember that the DTO doesn't have a Source Employee equivalent field.
        // This responsability is left for whoever is using the mapper.
        employeeRelationship.setTargetEmployee(this.employeeService.getById(employeeRelationshipDTO.getReferred().getId()));
        employeeRelationship.setComments(employeeRelationshipDTO.getComments());
    }
}
