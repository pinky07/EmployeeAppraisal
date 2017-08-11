package com.gft.employeeappraisal.converter.employeerelationship;

import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.RelationshipDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @author Manuel Yepez
 */
public class EmployeeRelationshipDTOMapper extends CustomMapper<EmployeeRelationship, EmployeeRelationshipDTO> {

	@Override
	public void mapAtoB(EmployeeRelationship employeeRelationship, EmployeeRelationshipDTO employeeRelationshipDTO,
						MappingContext context) {
		if (employeeRelationship.getEndDate() != null) {
			employeeRelationshipDTO.setEndDate(OffsetDateTime.of(employeeRelationship.getEndDate(), ZoneOffset.UTC));
		}

		employeeRelationshipDTO.setReference(mapperFacade.map(employeeRelationship.getSourceEmployee(),
				EmployeeDTO.class));
		employeeRelationshipDTO.setRelationship(mapperFacade.map(employeeRelationship.getRelationship(),
				RelationshipDTO.class));
	}

	@Override
	public void mapBtoA(EmployeeRelationshipDTO employeeRelationshipDTO, EmployeeRelationship employeeRelationship,
						MappingContext context) {
		employeeRelationship.setStartDate(employeeRelationshipDTO.getStartDate().toLocalDateTime());
		if (employeeRelationshipDTO.getEndDate() != null) {
			employeeRelationship.setStartDate(employeeRelationshipDTO.getEndDate().toLocalDateTime());
		}
		employeeRelationship.setSourceEmployee(mapperFacade.map(employeeRelationshipDTO.getReference(),
				Employee.class));
		employeeRelationship.setRelationship(mapperFacade.map(employeeRelationshipDTO.getRelationship(),
				Relationship.class));
	}
}
