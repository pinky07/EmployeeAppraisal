package com.gft.employeeappraisal.converter.employee;

import com.gft.employeeappraisal.converter.Mapper;
import com.gft.employeeappraisal.model.Employee;
import com.gft.swagger.employees.model.EmployeeDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Converts a persistent Employee entity to the Employee DTO.
 * @author Manuel Yepez
 */
public class EmployeeDTOFromEntity implements Mapper<Employee, EmployeeDTO> {

	private MapperFactory mapperFactory;

	@Autowired
	public EmployeeDTOFromEntity(MapperFactory mapperFactory) {
		this.mapperFactory = mapperFactory;
		this.mapperFactory.classMap(Employee.class, EmployeeDTO.class)
				.exclude("applicationRole.employee")
				.byDefault()
				.register();
	}

	/**
	 * @see Mapper#map(Object)
	 * @param source {@link EmployeeDTO} EmployeeDTO object to be converted.
	 * @return {@link Employee} Transformed Employee object.
	 */
	@Override
	public EmployeeDTO map(Employee source) {
		return this.mapperFactory.getMapperFacade().map(source, EmployeeDTO.class);
	}

}
