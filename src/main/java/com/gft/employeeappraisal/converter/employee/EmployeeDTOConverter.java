package com.gft.employeeappraisal.converter.employee;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.Employee;
import com.gft.swagger.employees.model.EmployeeDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Converter init class for bidirectional transformation between Employee and EmployeeDTO.
 * Using a BoundMapper increases performance according to Orika's authors.
 *
 * @author Manuel Yepez
 */
public class EmployeeDTOConverter implements EntityDTOConverter<Employee, EmployeeDTO> {

	private BoundMapperFacade<Employee, EmployeeDTO> boundMapper;

	@Autowired
	public EmployeeDTOConverter(MapperFactory mapperFactory) {
		this.boundMapper = mapperFactory.getMapperFacade(Employee.class, EmployeeDTO.class);
	}

	@Override
	public EmployeeDTO convert(Employee source) {
		return boundMapper.map(source);
	}

	@Override
	public Employee convertBack(EmployeeDTO source) {
		return boundMapper.mapReverse(source);
	}
}
