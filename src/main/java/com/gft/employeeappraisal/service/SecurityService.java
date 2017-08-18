package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.exception.EmployeeNotFoundException;

/**
 * Service that verifies what actions can be performed by users.
 *
 * @author Rubén Jiménez
 */
public interface SecurityService {

    /**
     * Determines if certain person can obtain a specific employee information.
     *
     * @param employeeId  Id of the Employee who wants access
     * @param requestedId Id of the Employee to be accessed
     * @throws EmployeeNotFoundException if either the IDs provided do not correspond to an existing employee.
     * @throws AccessDeniedException     if the employee is not an admin or current mentor requesting the employee information.
     */
    void canReadEmployeeInformation(int employeeId, int requestedId) throws EmployeeNotFoundException, AccessDeniedException;

}
