package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.employeeappraisal.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link SecurityService}
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private final int maxMenteeReferences;
    private final EmployeeRelationshipService employeeRelationshipService;
    private final EmployeeService employeeService;

    /**
     * Creates an instance of SecurityServiceImpl.
     *
     * @param maxMenteeReferences         Maximum number of references a mentee can have
     * @param employeeRelationshipService EmployeeRelationship service
     * @param employeeService             Employee service
     */
    @Autowired
    public SecurityServiceImpl(
            @Value("${com.gft.businessRules.maxMenteeReferences}") Integer maxMenteeReferences,
            EmployeeRelationshipService employeeRelationshipService,
            EmployeeService employeeService) {
        this.maxMenteeReferences = maxMenteeReferences;
        this.employeeRelationshipService = employeeRelationshipService;
        this.employeeService = employeeService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void canReadAppraisal(Employee reader, Employee employee, Appraisal appraisal) throws AccessDeniedException {
        if (!employee.equals(employee)) {
            throw new AccessDeniedException(String.format(
                    "Employee[%d] can't read Appraisal[%d] from Employee[%d]",
                    reader.getId(),
                    employee.getId(),
                    appraisal.getId()));
        }
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
                        "Employee[%d] can't read Employee[%d]",
                        reader.getId(),
                        requested.getId()));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void canReadEmployeeEvaluationForm(Employee reader, EmployeeEvaluationForm employeeEvaluationForm) throws AccessDeniedException {
        if (!reader.equals(employeeEvaluationForm.getEmployee())) {
            throw new AccessDeniedException(String.format(
                    "Employee[%d] can't read EmployeeEvaluationForm[%d]",
                    reader.getId(),
                    employeeEvaluationForm.getId()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void canReadEvaluationFormTemplate(Employee reader, Employee employee) throws AccessDeniedException {
        if (!reader.equals(employee)) {
            throw new AccessDeniedException(String.format(
                    "Employee[%d] can't read Employee[%d] EvaluationFormTemplate",
                    reader.getId(),
                    employee.getId()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void canWriteEmployeeRelationship(Employee writer, Employee sourceEmployee, Employee targetEmployee) throws
            AccessDeniedException {
        // The Employee can change his own information
        if (!writer.equals(sourceEmployee)) {

            //  Get the Mentor of the Employee that's being trying to be accessed
            Optional<Employee> requestedMentor = employeeService.findCurrentMentorById(sourceEmployee.getId());

            // The Employee can access the requested Employee if he is his Mentor
            if (!requestedMentor.isPresent() || !writer.equals(requestedMentor.get())) {

                // Otherwise an Access Denied Exception is thrown
                throw new AccessDeniedException(String.format(
                        "Employee[%d] can't write an EmployeeRelationship from Employee[%d] to Employee[%d]",
                        writer.getId(),
                        sourceEmployee.getId(),
                        targetEmployee.getId()));
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void checkRelationshipCount(Employee sourceEmployee) throws AccessDeniedException {
        int currentEmployeeReferences = (int) employeeRelationshipService.findCurrentBySourceEmployeeAndRelationships(sourceEmployee,
                RelationshipName.LEAD,
                RelationshipName.PEER,
                RelationshipName.OTHER).count();
        if (currentEmployeeReferences < 0 || maxMenteeReferences < currentEmployeeReferences) {
            throw new AccessDeniedException(String.format(
                    "Employee[%d] has already %d references active and cannot add any more.",
                    sourceEmployee.getId(), currentEmployeeReferences));
        }
    }
}
