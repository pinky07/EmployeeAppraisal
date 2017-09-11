package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;

/**
 * Service that verifies what actions can be performed by users.
 *
 * @author Rubén Jiménez
 */
public interface SecurityService {

    /**
     * Determines if a certain person can obtain specific appraisal information.
     * Rules:
     * - An Employee can read his own information
     *
     * @param employee  Employee who wants access to an Appraisal
     * @param appraisal Appraisal to be accessed
     * @throws AccessDeniedException If the access is denied
     */
    void canReadAppraisal(Employee reader, Employee employee, Appraisal appraisal) throws AccessDeniedException;

    /**
     * Determines if a certain person can obtain specific employee information.
     * Rules:
     * - An Employee can read his own information
     * - A Mentor can read the information of their Mentees
     *
     * @param reader    Employee who wants access to another Employee
     * @param requested Employee to be accessed
     * @throws AccessDeniedException If the access is denied
     */
    void canReadEmployee(Employee reader, Employee requested) throws AccessDeniedException;

    /**
     * Determines if a certain person can obtain specific employee evaluation form information.
     * Rules:
     * - An Employee can read his own information
     *
     * @param reader                 Employee who want to read an {@link EmployeeEvaluationForm}
     * @param employeeEvaluationForm Entity to be read
     * @throws AccessDeniedException If the access is denied
     */
    void canReadEmployeeEvaluationForm(Employee reader, EmployeeEvaluationForm employeeEvaluationForm) throws AccessDeniedException;

    /**
     * Determines if a certain person can create a certain Employee Relationship between two employees.
     * Rules:
     * - An Employee can write EmployeeRelationships where he is the SourceEmployee.
     * - A Mentor can write EmployeeRelationships for their Mentees where their Mentee is the SourceEmployee.
     *
     * @param writer         Employee who wants to write a new EmployeeRelationship
     * @param sourceEmployee Source Employee of the new Relationship
     * @param targetEmployee Target Employee of the new Relationship
     * @throws AccessDeniedException If the access is denied
     */
    void canWriteEmployeeRelationship(Employee writer, Employee sourceEmployee, Employee targetEmployee) throws AccessDeniedException;

    /**
     * We have a business limitation where an Employee cannot have more than 5 active references on a single moment
     * in time. This method checks for such a limit.
     *
     * @param sourceEmployee Employee to check.
     */
    void checkRelationshipCount(Employee sourceEmployee) throws AccessDeniedException;
}
