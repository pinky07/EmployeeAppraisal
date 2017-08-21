package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.employeeappraisal.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link EmployeeService}
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private EmployeeService employeeService;

    @Autowired
    public SecurityServiceImpl(
            EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void canReadEmployee(int employeeId, int requestedId) throws NotFoundException, AccessDeniedException {

        // The Employee can access his own information
        if (employeeId != requestedId) {

            // Get Employee requesting access
            Employee employee = employeeService.findById(employeeId)
                    .orElseThrow(() -> new NotFoundException(
                            String.format("Can't find Employee with Id %d",
                                    employeeId)));


            // Get the Employee that is trying to be accessed
            Employee requestedEmployee = employeeService.findById(requestedId)
                    .orElseThrow(() -> new NotFoundException(
                            String.format("Can't find requested Employee with Id %d",
                                    requestedId)));

            //  Get the Mentor of the Employee that's being trying to be accessed
            Optional<Employee> requestedEmployeeMentor = employeeService.findCurrentMentorById(requestedEmployee.getId());

            // The Employee can access the requested Employee if he is his Mentor
            if (!requestedEmployeeMentor.isPresent() || !employee.equals(requestedEmployeeMentor.get())) {

                // Otherwise an Access Denied Exception is thrown
                throw new AccessDeniedException(String.format(
                        "Employee with Id %d can't access Employee with %d information",
                        employeeId,
                        requestedId));
            }
        }
    }

}
