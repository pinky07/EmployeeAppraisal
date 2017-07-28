package com.gft.employee.repository;

import com.gft.employee.model.Employee;
import com.gft.employee.model.EmployeeRelationship;
import com.gft.employee.model.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * A Repository for the entity EmployeeRelationship is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 *
 * @author Manuel Yepez
 */
@Repository
@Transactional
public interface EmployeeRelationshipRepository extends JpaRepository<EmployeeRelationship, Integer> {

    @Query("SELECT COUNT(er) from EmployeeRelationship er " +
            "WHERE er.sourceEmployee = :sourceEmployee " +
            "AND er.relationship = :relationship " +
            "AND er.endDate = NULL")
    int countCurrentBySourceEmployeeAndRelationship(
            @Param("sourceEmployee") Employee sourceEmployee,
            @Param("relationship") Relationship relationship);

    @Query("SELECT er from EmployeeRelationship er " +
            "WHERE er.sourceEmployee = :sourceEmployee " +
            "AND er.relationship = :relationship " +
            "AND er.endDate = NULL")
    List<EmployeeRelationship> findCurrentBySourceEmployeeAndRelationship(
            @Param("sourceEmployee") Employee sourceEmployee,
            @Param("relationship") Relationship relationship);

    @Query("SELECT er from EmployeeRelationship er " +
            "WHERE er.sourceEmployee = :sourceEmployee " +
            "AND er.relationship IN :relationships " +
            "AND er.endDate = NULL")
    List<EmployeeRelationship> findCurrentBySourceEmployeeAndRelationships(
            @Param("sourceEmployee") Employee sourceEmployee,
            @Param("relationships") Set<Relationship> relationships);

    @Query("SELECT COUNT(er) from EmployeeRelationship er " +
            "WHERE er.targetEmployee = :targetEmployee " +
            "AND er.relationship = :relationship " +
            "AND er.endDate = NULL")
    int countCurrentByTargetEmployeeAndRelationship(
            @Param("targetEmployee") Employee targetEmployee,
            @Param("relationship") Relationship relationship);

    @Query("SELECT er from EmployeeRelationship er " +
            "WHERE er.targetEmployee = :targetEmployee " +
            "AND er.relationship = :relationship " +
            "AND er.endDate = NULL")
    List<EmployeeRelationship> findCurrentByTargetEmployeeAndRelationship(
            @Param("targetEmployee") Employee targetEmployee,
            @Param("relationship") Relationship relationship);

    @Query("SELECT COUNT(er) from EmployeeRelationship er " +
            "WHERE er.sourceEmployee = :sourceEmployee " +
            "AND er.targetEmployee = :targetEmployee " +
            "AND er.relationship = :relationship " +
            "AND er.endDate = NULL")
    int countCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(
            @Param("sourceEmployee") Employee sourceEmployee,
            @Param("targetEmployee") Employee targetEmployee,
            @Param("relationship") Relationship relationship);

    @Query("SELECT er from EmployeeRelationship er " +
            "WHERE er.sourceEmployee = :sourceEmployee " +
            "AND er.targetEmployee = :targetEmployee " +
            "AND er.relationship = :relationship " +
            "AND er.endDate = NULL")
    List<EmployeeRelationship> findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(
            @Param("sourceEmployee") Employee sourceEmployee,
            @Param("targetEmployee") Employee targetEmployee,
            @Param("relationship") Relationship relationship);
}
