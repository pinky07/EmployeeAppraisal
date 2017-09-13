package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.model.RelationshipType;
import com.gft.employeeappraisal.repository.RelationshipTypeRepository;
import com.gft.employeeappraisal.service.RelationshipTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Service implementation of {@link RelationshipTypeService}
 *
 * @author Manuel Yepez
 */
@Service
public class RelationshipTypeServiceImpl implements RelationshipTypeService {

    private final RelationshipTypeRepository relationshipTypeRepository;

    @Autowired
    public RelationshipTypeServiceImpl(
            RelationshipTypeRepository relationshipTypeRepository) {
        this.relationshipTypeRepository = relationshipTypeRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<RelationshipType> findById(int relationshipId) {
        return Optional.ofNullable(relationshipTypeRepository.findOne(relationshipId));
    }

    /**
     * @inheritDoc
     */
    @Override
    public RelationshipType getById(int id) throws NotFoundException {
        return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "RelationshipType[%d] couldn't be found",
                id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelationshipType findByName(RelationshipName relationshipName) {
        // Since RelationshipName enum values are managed by the application,
        // We can assume they always exist
        return this.findById(relationshipName.getId()).get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<RelationshipType> findRelationshipsByNames(RelationshipName... relationshipNames) {
        Set<String> relationships = new HashSet<>();
        for (RelationshipName rn : relationshipNames) {
            relationships.add(rn.name());
        }
        return relationshipTypeRepository.findByNames(relationships).stream();
    }
}
