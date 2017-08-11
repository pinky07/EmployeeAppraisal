package com.gft.employeeappraisal.converter.employeerelationship;

import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Converter logic for bidirectional transformation between EmployeeRelationship and EmployeeRelationshipDTO.
 *
 * @author Manuel Yepez
 */
public class EmployeeRelationshipDTOConverter {

	private BoundMapperFacade<EmployeeRelationship, EmployeeRelationshipDTO> boundMapper;

	@Autowired
	public EmployeeRelationshipDTOConverter(MapperFactory mapperFactory) {
		this.boundMapper = mapperFactory.getMapperFacade(EmployeeRelationship.class, EmployeeRelationshipDTO.class);
	}

	public EmployeeRelationshipDTO convert(EmployeeRelationship source) {
		return boundMapper.map(source);
	}

	public EmployeeRelationship convertBack(EmployeeRelationshipDTO source) {
		return boundMapper.mapReverse(source);
	}
}
