package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.AccessDeniedException;
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
    public void canReadEmployee(Employee reader, Employee requested) throws AccessDeniedException {
        // The Employee can access his own information
        if (!reader.equals(requested)) {

            //  Get the Mentor of the Employee that's being trying to be accessed
            Optional<Employee> requestedMentor = employeeService.findCurrentMentorById(requested.getId());

            // The Employee can access the requested Employee if he is his Mentor
            if (!requestedMentor.isPresent() || !reader.equals(requestedMentor.get())) {

                // Otherwise an Access Denied Exception is thrown
                throw new AccessDeniedException(String.format(
                        "Employee with Id: %d can't read Employee with Id: %d",
                        reader,
                        requested));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void canWriteEmployeeRelationship(Employee writer, Employee sourceEmployee, Employee targetEmployee) throws AccessDeniedException {
        // The Employee can change his own information
        if (!writer.equals(sourceEmployee)) {

            //  Get the Mentor of the Employee that's being trying to be accessed
            Optional<Employee> requestedMentor = employeeService.findCurrentMentorById(sourceEmployee.getId());

            // The Employee can access the requested Employee if he is his Mentor
            if (!requestedMentor.isPresent() || !writer.equals(requestedMentor.get())) {

                // Otherwise an Access Denied Exception is thrown
                throw new AccessDeniedException(String.format(
                        "Employee with Id %d can't write an EmployeeRelationship from Employee with Id: %d to Employee with Id: %d",
                        writer,
                        sourceEmployee,
                        targetEmployee));
            }
        }
    }
}
