package com.gft.employee.service;

import com.gft.employee.model.Employee;
import com.gft.swagger.employees.model.EmployeeDTO;

/**
 * Auxiliary methods to validate and fill DTOs
 *
 * @author Ruben Jimenez
 */
public interface DTOService {

    /**
     * Sets the isAdmin, isMentor and isPeer flags
     * TODO Should this method return the DTO?
     *
     * @param employeeDTO DTO that will be the response
     * @param employee    Employee that was mapped to the DTO
     */
    void setEmployeeDTOFlags(EmployeeDTO employeeDTO, Employee employee);
}
