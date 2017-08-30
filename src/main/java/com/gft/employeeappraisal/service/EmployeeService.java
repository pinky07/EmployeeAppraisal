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
     * Searches h2 employees according to first and last name criterias, while being paginated.
     *
     * @param firstName Employee's first name.
     * @param lastName  Employee's last name.
     * @param pageSize  Page size to be returned by the repository.
     * @return A stream of found employees.
     */
    Stream<Employee> findPagedByFirstNameOrLastName(String firstName, String lastName, int pageNumber, int pageSize);

    /**
     * Given an email, looks up an employee and returns it.
     *
     * @param email Lookup email for the employee.
     * @return An Optional object that may or may not contain the Employee entity.
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Given an employee Id, looks up an employee and returns it.
     *
     * @param id Internal lookup Id for the employee.
     * @return An Optional object that may or may not contain the Employee entity.
     */
    Optional<Employee> findById(Integer id);

    /**
     * Given an employee Id, looks up an employee and returns it. If it doesn't exist, it throws an exception.
     *
     * @param id Internal lookup Id for the employee.
     * @return Employee entity.
     */
    Employee getById(Integer id) throws NotFoundException;

    /**
     * Given an Employee Id, looks up the current Mentor for that Employee and returns it.
     *
     * @param menteeId Internal lookup Id for the employee.
     * @return An Optional object that may or may not contain the employee's current mentor.
     */
    Optional<Employee> findCurrentMentorById(int menteeId) throws NotFoundException;

    /**
     * Given an Employee Id, looks up the current Mentor for that Employee and returns it. Will throw a
     * NotFoundException if the Employee of its Mentor can't be found.
     *
     * @param menteeId Internal lookup Id for the Employee.
     * @return The Employee's current Mentor.
     * @throws NotFoundException If either the Employee or its Mentor are not found.
     */
    Employee getCurrentMentorById(int menteeId) throws NotFoundException;

    /**
     * Given an Employee Id, looks up the current mentor for that employee and returns it.
     *
     * @param mentorId Internal lookup Id for the mentor.
     * @return A stream of Employees describing the employee's current mentees.
     */
    Stream<Employee> findCurrentMenteesById(int mentorId) throws NotFoundException;

    /**
     * Given an Employee Id, looks up the current peers for that employee and returns them.
     *
     * @param employeeId Internal lookup Id for the employee.
     * @return A stream of Employees describing the employee's current peers.
     */
    Stream<Employee> findCurrentPeersById(int employeeId) throws NotFoundException;

    /**
     * Finds a stream of current relationships an employee has, given a lookup Id.
     *
     * @param employeeId        Internal lookup Id for the employee.
     * @param relationshipNames One or more relationship types for lookup.
     * @return A stream of found relationships.
     * @throws NotFoundException If the Id provided does not correspond to an employee.
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
