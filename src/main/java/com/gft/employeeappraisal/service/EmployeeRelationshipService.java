package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.model.RelationshipName;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface for methods interacting with the EmployeeRelationship repository.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public interface EmployeeRelationshipService {

    /**
     * Looks up an EmployeeRelationship and returns it. Can return an empty Optional.
     *
     * @param id EmployeeRelationship Id
     * @return EmployeeRelationship
     */
    Optional<EmployeeRelationship> findById(Integer id);

    /**
     * Looks up an EmployeeRelationship and returns it. If it doesn't exist, throws a NotFoundException.
     *
     * @param id EmployeeRelationship Id
     * @return EmployeeRelationship
     */
    EmployeeRelationship getById(Integer id);

    /**
     * Checks if there is a current MENTOR relationship from mentor to mentee.
     *
     * @param mentor Possible mentor
     * @param mentee Possible mentee
     * @return Whether the relationship exists
     * @see EmployeeRelationshipService#isCurrentRelationship(Employee, Employee, Relationship)
     */
    boolean isCurrentMentor(Employee mentor, Employee mentee);

    /**
     * Checks if there is a current PEER relationship from employeeA to employeeB.
     *
     * @param employeeA Left side of the possible relationship
     * @param employeeB Right side of the possible relationship
     * @return Whether the relationship exists
     */
    boolean isCurrentPeer(Employee employeeA, Employee employeeB);

    /**
     * Checks if there is a current Relationship from EmployeeA to EmployeeB.
     *
     * @param employeeA Left side of the possible relationship
     * @param employeeB Right side of the possible relationship
     * @return Whether there is a current relationship (endDate == null) between the employees.
     */
    boolean isCurrentRelationship(Employee employeeA, Employee employeeB, Relationship relationship);

    /**
     * Whether the Employee has a MENTOR.
     *
     * @param employee Employee to check
     * @return True if Employee has a MENTOR
     */
    boolean hasMentor(Employee employee);

    /**
     * Whether the Employee has any MENTEES.
     *
     * @param employee Employee to check
     * @return True if Employee has any MENTEES
     */
    boolean hasMentees(Employee employee);

    /**
     * Whether the Employee has any PEERS
     *
     * @param employee Employee to check
     * @return True if the Employee has at least one PEER
     */
    boolean hasPeers(Employee employee);

    /**
     * Checks if the Employee has any relationships of the indicated type as the SOURCE.
     *
     * @param source       Employee to check
     * @param relationship Type of relationship to check
     * @return True if the Employee is a SOURCE of the type of Relationship indicated
     */
    boolean hasRelationshipAsSourceEmployee(Employee source, Relationship relationship);

    /**
     * Checks if the Employee has any relationships of the indicated type as the TARGET.
     *
     * @param target       Employee to check
     * @param relationship Type of relationship to check
     * @return True if the Employee is a TARGET of the type of Relationship indicated
     */
    boolean hasRelationshipAsTargetEmployee(Employee target, Relationship relationship);

    /**
     * Starts a new MENTOR relationship from Mentor to Mentee.
     *
     * @param mentor Mentor to be
     * @param mentee Mentee to be
     */
    void changeMentor(Employee mentor, Employee mentee);

    /**
     * Starts a new PEER relationship from Employee to Peer
     *
     * @param employee Employee
     * @param peer     Peer to be associated
     */
    void addPeer(Employee employee, Employee peer);

    /**
     * Starts a new PEER relationship from Employee to each Peer in the list.
     *
     * @param employee Employee
     * @param peers    Peers to be associated
     */
    void addPeers(Employee employee, List<Employee> peers);

    /**
     * Creates a new Employee Relationship between two Employees. The relationship is started on the current
     * server datetime.
     * <p>
     * The Relationship is read from left to right. For example: if the relationship is MENTOR, then SOURCE would be
     * the MENTOR of TARGET.
     *
     * @param sourceEmployee Left side of the relationship
     * @param targetEmployee Right side of the relationship
     * @param relationship   Relationship that should be read from left to right
     */
    Optional<EmployeeRelationship> startEmployeeRelationship(Employee sourceEmployee, Employee targetEmployee, Relationship relationship);

    /**
     * Relationships shouldn't be deleted, rather it should be marked with an end date as this method does.
     * The end date will be the current server datetime
     *
     * @param employeeRelationship Relationship to end
     */
    Optional<EmployeeRelationship> endEmployeeRelationship(EmployeeRelationship employeeRelationship);

    /**
     * Finds a list of relationships, all of which should be the current valid relationships that source Employee has.
     *
     * @param sourceEmployee Source Employee in the Relationship
     * @param relationship   Type of Relationship to look for
     * @return A stream of EmployeeRelationships, with no expiration date (current ones)
     */
    Stream<EmployeeRelationship> findCurrentBySourceEmployeeAndRelationship(Employee sourceEmployee, Relationship relationship);

    /**
     * Finds a list of relationships, all of which should be the valid relationships that source Employee has.
     *
     * @param sourceEmployee Source Employee in the Relationship
     * @param relationships  List of Relationships to look for
     * @return A stream of all EmployeeRelationships belonging to the employee
     */
    Stream<EmployeeRelationship> findBySourceEmployeeAndRelationships(Employee sourceEmployee, RelationshipName... relationships);

    /**
     * Finds a list of relationships, all of which should be the current valid relationships that source Employee has.
     *
     * @param sourceEmployee Source Employee in the Relationship
     * @param relationships  List of Relationships to look for
     * @return A stream of EmployeeRelationships, with no expiration date (current ones)
     */
    Stream<EmployeeRelationship> findCurrentBySourceEmployeeAndRelationships(Employee sourceEmployee, RelationshipName... relationships);

    /**
     * Finds a list of relationships, all of which should be the current valid relationships that target Employee has.
     *
     * @param targetEmployee Target Employee in the Relationship
     * @param relationship   Type of Relationship to look for
     * @return A stream of EmployeeRelationships, with no expiration date (current ones)
     */
    Stream<EmployeeRelationship> findCurrentByTargetEmployeeAndRelationship(Employee targetEmployee, Relationship relationship);

    /**
     * Finds a list of relationships, all of which should be the current valid relationships that
     * source Employee and target Employee have.
     *
     * @param sourceEmployee Source Employee in the Relationship
     * @param targetEmployee Target Employee in the Relationship
     * @param relationship   Type of Relationship to look for
     * @return A stream of EmployeeRelationships, with no expiration date (current ones)
     */
    Stream<EmployeeRelationship> findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(Employee sourceEmployee, Employee targetEmployee, Relationship relationship);

    /**
     * Saves the EmployeeRelationship entity to the database. Returns the saved entity.
     *
     * @param employeeRelationship Entity to be persisted onto the database.
     */
    Optional<EmployeeRelationship> saveAndFlush(EmployeeRelationship employeeRelationship);

}
