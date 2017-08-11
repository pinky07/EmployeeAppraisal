package com.gft.employeeappraisal.converter.employee;

import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.JobLevel;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.JobLevelDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper class for processing the conversion between Employees and their respective DTO.
 * Using a BoundMapper increases performance according to Orika's authors.
 *
 * @author Manuel Yepez
 */
@Component
public class EmployeeDTOMapper extends CustomMapper<Employee, EmployeeDTO> {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRelationshipService employeeRelationshipService;

	@Override
	public void mapAtoB(Employee employee, EmployeeDTO employeeDTO, MappingContext context) {
		employeeDTO.setApplicationRole(mapperFacade.map(employee.getApplicationRole(), ApplicationRoleDTO.class));
		employeeDTO.setJobLevel(mapperFacade.map(employee.getJobLevel(), JobLevelDTO.class));

		employeeDTO.setIsAdmin(employeeService.isAdmin(employee));
		employeeDTO.setIsMentor(employeeRelationshipService.hasMentees(employee));
		employeeDTO.setIsPeer(employeeRelationshipService.hasPeers(employee));
	}

	@Override
	public void mapBtoA(EmployeeDTO employeeDTO, Employee employee, MappingContext context) {
		employee.setApplicationRole(mapperFacade.map(employeeDTO.getApplicationRole(), ApplicationRole.class));
		employee.setJobLevel(mapperFacade.map(employeeDTO.getJobLevel(), JobLevel.class));
	}
}
