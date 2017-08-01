package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.exception.EmployeeNotFoundException;
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
    Employee getLoggedInUser() throws EmployeeNotFoundException;

    /**
     * Determines if certain person can obtain a specific employeeappraisal information.
     *
     * @param employeeId  Id of the Employee who wants access
     * @param requestedId Id of the Employee to be accessed
     * @throws EmployeeNotFoundException if either the IDs provided do not correspond to an existing employeeappraisal.
	 * @throws AccessDeniedException if the employeeappraisal is not an admin or current mentor requesting the employeeappraisal information.
     */
    void checkAccess(int employeeId, int requestedId) throws EmployeeNotFoundException, AccessDeniedException;

    /**
     * Given an email, looks up an employeeappraisal and returns it.
     *
     * @param email Lookup email for the employeeappraisal.
     * @return An Optional object that may or may not contain the Employee entity.
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Given an employeeappraisal ID, looks up an employeeappraisal and returns it.
     *
     * @param id Internal lookup ID for the employeeappraisal.
     * @return An Optional object that may or may not contain the Employee entity.
     */
    Optional<Employee> findById(Integer id);

    /**
     * Given an Employee ID, looks up the current mentor for that employeeappraisal and returns it.
     *
     * @param menteeId Internal lookup ID for the employeeappraisal.
     * @return An Optional object that may or may not contain the employeeappraisal's current mentor.
     */
    Optional<Employee> findCurrentMentorById(int menteeId) throws EmployeeNotFoundException;

    /**
     * Given an Employee ID, looks up the current mentor for that employeeappraisal and returns it.
     *
     * @param mentorId Internal lookup ID for the mentor.
     * @return A stream of Employees describing the employeeappraisal's current mentees.
     */
    Stream<Employee> findCurrentMenteesById(int mentorId) throws EmployeeNotFoundException;

    /**
     * Given an Employee ID, looks up the current peers for that employeeappraisal and returns them.
     *
     * @param employeeId Internal lookup ID for the employeeappraisal.
     * @return A stream of Employees describing the employeeappraisal's current peers.
     */
    Stream<Employee> findCurrentPeersById(int employeeId) throws EmployeeNotFoundException;

    /**
     * Finds a stream of current relationships an employeeappraisal has, given a lookup ID.
     * @param employeeId Internal lookup ID for the employeeappraisal.
     * @param relationshipNames One or more relationship types for lookup.
     * @return A stream of found relationships.
     * @throws EmployeeNotFoundException If the ID provided does not correspond to an employeeappraisal.
     */
    Stream<EmployeeRelationship> findCurrentRelationshipsById(int employeeId, RelationshipName... relationshipNames)
            throws EmployeeNotFoundException;

	/**
	 * Finds a stream of current relationships an employeeappraisal has.
	 * @param employee Employee lookup entity
	 * @param relationshipNames One or more relationship types for lookup.
	 * @return A stream of found relationships.
	 * @throws EmployeeNotFoundException if the entity provided does not correspond to an employeeappraisal.
	 */
	Stream<EmployeeRelationship> findCurrentRelationshipsBySourceEmployee(Employee employee, RelationshipName... relationshipNames)
			throws EmployeeNotFoundException;

    /**
     * Given an Employee, determines if that employeeappraisal is an application administrator.
     *
     * @param employee Employee entity to determine a role.
     * @return Whether the employeeappraisal is an application administrator or not.
     */
    boolean isAdmin(Employee employee);

    /**
     * Saves an Employee entity to the database.
     *
     * @param employee Employee entity to be persisted.
     * @return An Optional object containing the persisted entity.
     */
    Optional<Employee> save(Employee employee);
}
