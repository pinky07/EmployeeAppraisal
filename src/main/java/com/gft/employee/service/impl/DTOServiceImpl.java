package com.gft.employee.service.impl;

import com.gft.employee.model.Employee;
import com.gft.employee.service.DTOService;
import com.gft.employee.service.EmployeeRelationshipService;
import com.gft.employee.service.EmployeeService;
import com.gft.swagger.employees.model.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation of {@link DTOService}
 *
 * @author Ruben Jimenez
 */
@Service
public class DTOServiceImpl implements DTOService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRelationshipService employeeRelationshipService;

    @Override
    public void setEmployeeDTOFlags(EmployeeDTO employeeDTO, Employee employee) {
        employeeDTO.setIsAdmin(employeeService.isAdmin(employee));
        employeeDTO.setIsMentor(employeeRelationshipService.hasMentees(employee));
        employeeDTO.setIsPeer(employeeRelationshipService.hasPeers(employee));
    }
}
