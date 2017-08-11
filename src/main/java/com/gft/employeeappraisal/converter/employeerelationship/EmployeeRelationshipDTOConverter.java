package com.gft.employeeappraisal.converter.employeerelationship;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Converter init class for bidirectional transformation between EmployeeRelationship and EmployeeRelationshipDTO.
 * Using a BoundMapper increases performance according to Orika's authors.
 *
 * @author Manuel Yepez
 */
public class EmployeeRelationshipDTOConverter implements EntityDTOConverter<EmployeeRelationship, EmployeeRelationshipDTO> {

	private BoundMapperFacade<EmployeeRelationship, EmployeeRelationshipDTO> boundMapper;

	@Autowired
	public EmployeeRelationshipDTOConverter(MapperFactory mapperFactory) {
		this.boundMapper = mapperFactory.getMapperFacade(EmployeeRelationship.class, EmployeeRelationshipDTO.class);
	}

	@Override
	public EmployeeRelationshipDTO convert(EmployeeRelationship source) {
		return boundMapper.map(source);
	}

	@Override
	public EmployeeRelationship convertBack(EmployeeRelationshipDTO source) {
		return boundMapper.mapReverse(source);
	}
}
