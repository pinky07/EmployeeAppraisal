package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.RelationshipType;
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
            "AND er.relationshipType = :relationshipType " +
            "AND er.endDate = NULL")
    int countCurrentBySourceEmployeeAndRelationshipType(
            @Param("sourceEmployee") Employee sourceEmployee,
            @Param("relationshipType") RelationshipType relationshipType);

    @Query("SELECT er from EmployeeRelationship er " +
            "WHERE er.sourceEmployee = :sourceEmployee " +
            "AND er.relationshipType = :relationshipType " +
            "AND er.endDate = NULL")
    List<EmployeeRelationship> findCurrentBySourceEmployeeAndRelationshipType(
            @Param("sourceEmployee") Employee sourceEmployee,
            @Param("relationshipType") RelationshipType relationshipType);

    List<EmployeeRelationship> findAllBySourceEmployeeAndRelationshipTypeIn(
            Employee sourceEmployee,
            Set<RelationshipType> relationshipTypes);

    @Query("SELECT er from EmployeeRelationship er " +
            "WHERE er.sourceEmployee = :sourceEmployee " +
            "AND er.relationshipType IN :relationshipTypes " +
            "AND er.endDate = NULL")
    List<EmployeeRelationship> findCurrentBySourceEmployeeAndRelationshipTypes(
            @Param("sourceEmployee") Employee sourceEmployee,
            @Param("relationshipTypes") Set<RelationshipType> relationshipTypes);

    @Query("SELECT COUNT(er) from EmployeeRelationship er " +
            "WHERE er.targetEmployee = :targetEmployee " +
            "AND er.relationshipType = :relationshipType " +
            "AND er.endDate = NULL")
    int countCurrentByTargetEmployeeAndRelationshipType(
            @Param("targetEmployee") Employee targetEmployee,
            @Param("relationshipType") RelationshipType relationshipType);

    @Query("SELECT er from EmployeeRelationship er " +
            "WHERE er.targetEmployee = :targetEmployee " +
            "AND er.relationshipType = :relationshipType " +
            "AND er.endDate = NULL")
    List<EmployeeRelationship> findCurrentByTargetEmployeeAndRelationshipType(
            @Param("targetEmployee") Employee targetEmployee,
            @Param("relationshipType") RelationshipType relationshipType);

    @Query("SELECT COUNT(er) from EmployeeRelationship er " +
            "WHERE er.sourceEmployee = :sourceEmployee " +
            "AND er.targetEmployee = :targetEmployee " +
            "AND er.relationshipType = :relationshipType " +
            "AND er.endDate = NULL")
    int countCurrentBySourceEmployeeAndTargetEmployeeAndRelationshipType(
            @Param("sourceEmployee") Employee sourceEmployee,
            @Param("targetEmployee") Employee targetEmployee,
            @Param("relationshipType") RelationshipType relationshipType);

    @Query("SELECT er from EmployeeRelationship er " +
            "WHERE er.sourceEmployee = :sourceEmployee " +
            "AND er.targetEmployee = :targetEmployee " +
            "AND er.relationshipType = :relationshipType " +
            "AND er.endDate = NULL")
    List<EmployeeRelationship> findCurrentBySourceEmployeeAndTargetEmployeeAndRelationshipType(
            @Param("sourceEmployee") Employee sourceEmployee,
            @Param("targetEmployee") Employee targetEmployee,
            @Param("relationshipType") RelationshipType relationshipType);
}
