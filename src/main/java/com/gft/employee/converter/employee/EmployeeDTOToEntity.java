package com.gft.employee.converter.employee;

import com.gft.employee.converter.Mapper;
import com.gft.employee.model.Employee;
import com.gft.swagger.employees.model.EmployeeDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Converts an Employee DTO to the persistent Employee entity.
 *
 * @author Manuel Yepez
 */
public class EmployeeDTOToEntity implements Mapper<EmployeeDTO, Employee> {

	private MapperFactory mapperFactory;

	@Autowired
	public EmployeeDTOToEntity(MapperFactory mapperFactory) {
		this.mapperFactory = mapperFactory;
	}

	/**
	 * @see Mapper#map(Object)
	 * @param source {@link Employee} Employee object to be converted.
	 * @return {@link EmployeeDTO} Transformed EmployeeDTO object.
	 */
	@Override
	public Employee map(EmployeeDTO source) {
		return this.mapperFactory.getMapperFacade().map(source, Employee.class);
	}
}
