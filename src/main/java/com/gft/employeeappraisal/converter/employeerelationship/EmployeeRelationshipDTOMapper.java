package com.gft.employeeappraisal.converter.employeerelationship;

import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.RelationshipDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Defines a mapping structure to be used by {@link EmployeeRelationshipDTOConverter}.
 *
 * @author Manuel Yepez
 */
@Component
public class EmployeeRelationshipDTOMapper extends CustomMapper<EmployeeRelationship, EmployeeRelationshipDTO> {

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
		if (employeeRelationshipDTO.getId() > 0) {
			employeeRelationship.setId(employeeRelationshipDTO.getId());
		}
		employeeRelationship.setStartDate(employeeRelationshipDTO.getStartDate().toLocalDateTime());
		if (employeeRelationshipDTO.getEndDate() != null) {
			employeeRelationship.setStartDate(employeeRelationshipDTO.getEndDate().toLocalDateTime());
		}
		employeeRelationship.setTargetEmployee(mapperFacade.map(employeeRelationshipDTO.getReferred(),
				Employee.class));
		employeeRelationship.setRelationship(mapperFacade.map(employeeRelationshipDTO.getRelationship(),
				Relationship.class));
	}
}
