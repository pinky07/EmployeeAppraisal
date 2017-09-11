package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.RelationshipType;
import com.gft.employeeappraisal.model.RelationshipName;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface for methods interacting with the Relationship repository.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public interface RelationshipTypeService {

    /**
     * Given a relationship Id, returns a Relationship entity.
     *
     * @param relationshipId Internal lookup Id for the relationship
     * @return An Optional object that may or may not contain a Relationship entity.
     */
    Optional<RelationshipType> findById(int relationshipId);

    /**
     * Given a relationship Id, returns a Relationship entity. If it not found, it throws an Exception.
     *
     * @param relationshipId Internal lookup Id for the relationship
     * @return The Relationship looked for
     * @throws NotFoundException If the Relationship is not found
     */
    RelationshipType getById(int relationshipId) throws NotFoundException;

    /**
     * Given a relationship name, returns a Relationship entity. Notice this method doesn't return an Optional.
     * Be careful when modifying the database to ensure all names in the RelationshipName enum actually exist in
     * the database.
     *
     * @param relationshipName Specific enum value to query from the database
     * @return A Relationship entity
     */
    RelationshipType findByName(RelationshipName relationshipName);

    /**
     * Given several relationship names, returns a stream of Relationship entities associated to them.
     *
     * @param relationshipNames List of lookup relationship names
     * @return A stream of Relationships
     */
    Stream<RelationshipType> findRelationshipsByNames(RelationshipName... relationshipNames);
}
