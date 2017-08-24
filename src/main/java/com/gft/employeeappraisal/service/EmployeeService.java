package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.RelationshipName;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface for methods interacting with the Employee repository.
 *
 * @author Manuel Yepez
 */
public interface EmployeeService {

    /**
     * Returns the currently logged in user.
     *
     * @return Currently logged in user
     */
    Employee getLoggedInUser() throws NotFoundException;

    /**
     * Searches all employees according to first and last name criterias, while being paginated.
     *
     * @param firstName Employee's first name.
     * @param lastName Employee's last name.
     * @param pageSize Page size to be returned by the repository.
     * @return A stream of found employees.
     */
    Stream<Employee> findPagedByFirstNameOrLastName(String firstName, String lastName, int pageSize);

    /**
     * Given an email, looks up an employee and returns it.
     *
     * @param email Lookup email for the employee.
     * @return An Optional object that may or may not contain the Employee entity.
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Given an employee ID, looks up an employee and returns it.
     *
     * @param id Internal lookup ID for the employee.
     * @return An Optional object that may or may not contain the Employee entity.
     */
    Optional<Employee> findById(Integer id);

    /**
     * Given an Employee ID, looks up the current mentor for that employee and returns it.
     *
     * @param menteeId Internal lookup ID for the employee.
     * @return An Optional object that may or may not contain the employee's current mentor.
     */
    Optional<Employee> findCurrentMentorById(int menteeId) throws NotFoundException;

    /**
     * Given an Employee ID, looks up the current mentor for that employee and returns it.
     *
     * @param mentorId Internal lookup ID for the mentor.
     * @return A stream of Employees describing the employee's current mentees.
     */
    Stream<Employee> findCurrentMenteesById(int mentorId) throws NotFoundException;

    /**
     * Given an Employee ID, looks up the current peers for that employee and returns them.
     *
     * @param employeeId Internal lookup ID for the employee.
     * @return A stream of Employees describing the employee's current peers.
     */
    Stream<Employee> findCurrentPeersById(int employeeId) throws NotFoundException;

    /**
     * Finds a stream of current relationships an employee has, given a lookup ID.
     *
     * @param employeeId        Internal lookup ID for the employee.
     * @param relationshipNames One or more relationship types for lookup.
     * @return A stream of found relationships.
     * @throws NotFoundException If the ID provided does not correspond to an employee.
     */
    Stream<EmployeeRelationship> findCurrentRelationshipsById(int employeeId, RelationshipName... relationshipNames)
            throws NotFoundException;

    /**
     * Finds a stream of current relationships an employee has.
     *
     * @param employee          Employee lookup entity
     * @param relationshipNames One or more relationship types for lookup.
     * @return A stream of found relationships.
     * @throws NotFoundException if the entity provided does not correspond to an employee.
     */
    Stream<EmployeeRelationship> findCurrentRelationshipsBySourceEmployee(Employee employee, RelationshipName... relationshipNames)
            throws NotFoundException;

    /**
     * Given an Employee, determines if that employee is an application administrator.
     *
     * @param employee Employee entity to determine a role.
     * @return Whether the employee is an application administrator or not.
     */
    boolean isAdmin(Employee employee);

    /**
     * Saves an Employee entity to the database.
     *
     * @param employee Employee entity to be persisted.
     * @return An Optional object containing the persisted entity.
     */
    Optional<Employee> saveAndFlush(Employee employee);
}
