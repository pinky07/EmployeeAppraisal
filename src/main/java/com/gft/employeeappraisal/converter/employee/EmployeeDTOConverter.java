package com.gft.employeeappraisal.converter.employee;

import com.gft.employeeappraisal.model.Employee;
import com.gft.swagger.employees.model.EmployeeDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Manuel Yepez
 */
public class EmployeeDTOConverter {

	private BoundMapperFacade<Employee, EmployeeDTO> boundMapper;

	@Autowired
	public EmployeeDTOConverter(MapperFactory mapperFactory) {
		this.boundMapper = mapperFactory.getMapperFacade(Employee.class, EmployeeDTO.class);
	}

	public EmployeeDTO convert(Employee source) {
		return boundMapper.map(source);
	}

	public Employee convertBack(EmployeeDTO source) {
		return boundMapper.mapReverse(source);
	}
}
