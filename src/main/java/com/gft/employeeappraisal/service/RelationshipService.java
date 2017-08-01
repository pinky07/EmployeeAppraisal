package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.model.RelationshipName;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface for methods interacting with the Relationship repository.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public interface RelationshipService {
    /**
     * Given a relationship ID, returns a Relationship entity.
     *
     * @param relationshipId Internal lookup ID for the relationship
     * @return An Optional object that may or may not contain a Relationship entity.
     */
    Optional<Relationship> findById(int relationshipId);

    /**
     * Given a relationship name, returns a Relationship entity.
     *
	 * @param relationshipName Specific enum value to query from the database
     * @return A Relationship entity
     */
    Relationship findByName(RelationshipName relationshipName);

    /**
     * Given several relationship names, returns a stream of Relationship entities associated to them.
	 *
     * @param relationshipNames List of lookup relationship names
     * @return A stream of Relationships
     */
    Stream<Relationship> findRelationshipsByNames(RelationshipName... relationshipNames);
}
