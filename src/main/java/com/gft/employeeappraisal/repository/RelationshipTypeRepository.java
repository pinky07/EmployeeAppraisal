package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.RelationshipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A Repository for the entity Relationship is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 *
 * @author Manuel Yepez
 */
@Repository
@Transactional
public interface RelationshipTypeRepository extends JpaRepository<RelationshipType, Integer> {

    Optional<RelationshipType> findByName(String name);

    @Query("SELECT r from RelationshipType r WHERE r.name IN :names")
    List<RelationshipType> findByNames(@Param("names") Set<String> names);
}
