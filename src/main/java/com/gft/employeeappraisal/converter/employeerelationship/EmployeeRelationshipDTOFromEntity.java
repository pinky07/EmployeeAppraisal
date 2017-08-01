package com.gft.employeeappraisal.converter.employeerelationship;

import com.gft.employeeappraisal.converter.Mapper;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Converts a persistent EmployeeRelationship entity to the EmployeeRelationship DTO.
 *
 * @author Manuel Yepez
 */
public class EmployeeRelationshipDTOFromEntity implements Mapper<EmployeeRelationship, EmployeeRelationshipDTO> {

	private MapperFactory mapperFactory;

	@Autowired
	public EmployeeRelationshipDTOFromEntity(MapperFactory mapperFactory) {
		this.mapperFactory = mapperFactory;
		this.mapperFactory.classMap(EmployeeRelationship.class, EmployeeRelationshipDTO.class)
				.field("targetEmployee", "reference")
				.byDefault()
				.register();
	}

	@Override
	public EmployeeRelationshipDTO map(EmployeeRelationship source) {
		return this.mapperFactory.getMapperFacade().map(source, EmployeeRelationshipDTO.class);
	}
}
