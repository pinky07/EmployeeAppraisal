package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.repository.RelationshipRepository;
import com.gft.employeeappraisal.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Service implementation of {@link RelationshipService}
 *
 * @author Manuel Yepez
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {

    private final RelationshipRepository relationshipRepository;

    @Autowired
    public RelationshipServiceImpl(
            RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Relationship> findById(int relationshipId) {
        return Optional.ofNullable(relationshipRepository.findOne(relationshipId));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Relationship getById(int id) throws NotFoundException {
        return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "Relationship with Id %d couldn't be found",
                id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Relationship findByName(RelationshipName relationshipName) {
        // Since RelationshipName enum values are managed by the application,
        // We can assume they always exist
        return this.findById(relationshipName.getId()).get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Relationship> findRelationshipsByNames(RelationshipName... relationshipNames) {
        Set<String> relationships = new HashSet<>();
        for (RelationshipName rn : relationshipNames) {
            relationships.add(rn.name());
        }
        return relationshipRepository.findByNames(relationships).stream();
    }
}
