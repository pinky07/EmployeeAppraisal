package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.Relationship;

/**
 * Service that verifies what actions can be performed by users.
 *
 * @author Rubén Jiménez
 */
public interface SecurityService {

    /**
     * Determines if certain person can obtain a specific employee information.
     * TODO Add rules in this Javadoc
     *
     * @param reader    Employee who wants access to another Employee
     * @param requested Employee to be accessed
     * @throws AccessDeniedException If the access is denied
     */
    void canReadEmployee(Employee reader, Employee requested) throws AccessDeniedException;

    /**
     * Determines if a certain person can create a certain Employee Relationship between two employees.
     * TODO Add rules in this Javadoc
     *
     * @param writer
     * @param sourceEmployee
     * @param targetEmployee
     */
    void canWriteEmployeeRelationship(Employee writer, Employee sourceEmployee, Employee targetEmployee, Relationship relationship) throws AccessDeniedException;
}
