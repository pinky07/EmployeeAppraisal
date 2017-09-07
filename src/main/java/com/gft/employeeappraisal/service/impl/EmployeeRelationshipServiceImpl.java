package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.EmployeeAppraisalMicroserviceException;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.repository.EmployeeRelationshipRepository;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import com.gft.employeeappraisal.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service implementation of {@link EmployeeRelationshipService}
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
@Service
public class EmployeeRelationshipServiceImpl implements EmployeeRelationshipService {

    private final EmployeeRelationshipRepository employeeRelationshipRepository;
    private final RelationshipService relationshipService;

    @Autowired
    public EmployeeRelationshipServiceImpl(
            EmployeeRelationshipRepository employeeRelationshipRepository,
            RelationshipService relationshipService) {
        this.employeeRelationshipRepository = employeeRelationshipRepository;
        this.relationshipService = relationshipService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<EmployeeRelationship> findById(Integer id) {
        return Optional.ofNullable(this.employeeRelationshipRepository.findOne(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRelationship getById(Integer id) {
        return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "EmployeeRelationship with Id %d couldn't be found",
                id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCurrentMentor(Employee mentor, Employee mentee) {
        return this.isCurrentRelationship(
                mentor,
                mentee,
                this.relationshipService.findByName(RelationshipName.MENTOR)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCurrentPeer(Employee employeeA, Employee employeeB) {
        return this.isCurrentRelationship(
                employeeA,
                employeeB,
                this.relationshipService.findByName(RelationshipName.PEER));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCurrentRelationship(Employee employeeA, Employee employeeB, Relationship relationship) {
        return this.employeeRelationshipRepository.countCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(
                employeeA,
                employeeB,
                relationship) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMentor(Employee employee) {
        return this.hasRelationshipAsTargetEmployee(
                employee,
                this.relationshipService.findByName(RelationshipName.MENTOR));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMentees(Employee employee) {
        return this.hasRelationshipAsSourceEmployee(
                employee,
                this.relationshipService.findByName(RelationshipName.MENTOR));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPeers(Employee employee) {
        return this.hasRelationshipAsSourceEmployee(
                employee,
                this.relationshipService.findByName(RelationshipName.PEER));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasRelationshipAsSourceEmployee(Employee source, Relationship relationship) {
        return this.employeeRelationshipRepository.countCurrentBySourceEmployeeAndRelationship(
                source,
                relationship) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasRelationshipAsTargetEmployee(Employee target, Relationship relationship) {
        return this.employeeRelationshipRepository.countCurrentByTargetEmployeeAndRelationship(
                target,
                relationship) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeMentor(Employee mentor, Employee mentee) {
        if (hasMentor(mentee)) {
            removeMentor(mentee); // If I'm _changing_ mentor, there's nothing wrong with it not having a mentor.
        }

        // Start a new MENTOR Relationship
        startEmployeeRelationship(
                mentor,
                mentee,
                relationshipService.findByName(RelationshipName.MENTOR));
    }

    @Override
    public void removeMentor(Employee mentee) throws EmployeeAppraisalMicroserviceException {
        if (!hasMentor(mentee)) {
            throw new EmployeeAppraisalMicroserviceException(String
                    .format("User %s has no mentor.", mentee.getEmail()));
        }

        // End any previous MENTOR Relationships
        this.findCurrentByTargetEmployeeAndRelationship(
                mentee,
                relationshipService.findByName(RelationshipName.MENTOR))
                .forEach(this::endEmployeeRelationship);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPeer(Employee employee, Employee peer) {
        startEmployeeRelationship(
                employee,
                peer,
                relationshipService.findByName(RelationshipName.PEER));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPeers(Employee employee, List<Employee> peers) {
        peers.forEach(peer -> addPeer(employee, peer));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<EmployeeRelationship> startEmployeeRelationship(Employee sourceEmployee, Employee targetEmployee, Relationship relationship) {
        EmployeeRelationship employeeRelationship = new EmployeeRelationship();
        employeeRelationship.setSourceEmployee(sourceEmployee);
        employeeRelationship.setTargetEmployee(targetEmployee);
        employeeRelationship.setRelationship(relationship);
        employeeRelationship.setStartDate(OffsetDateTime.now());
        employeeRelationship.setEndDate(null);
        return this.saveAndFlush(employeeRelationship);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<EmployeeRelationship> endEmployeeRelationship(EmployeeRelationship employeeRelationship) {
        employeeRelationship.setEndDate(OffsetDateTime.now());
        return this.saveAndFlush(employeeRelationship);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<EmployeeRelationship> findCurrentBySourceEmployeeAndRelationship(Employee sourceEmployee, Relationship relationship) {
        return employeeRelationshipRepository
                .findCurrentBySourceEmployeeAndRelationship(
                        sourceEmployee,
                        relationship)
                .stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<EmployeeRelationship> findBySourceEmployeeAndRelationships(Employee sourceEmployee, RelationshipName... relationshipNames) {
        List<Relationship> relationships = relationshipService.findRelationshipsByNames(relationshipNames).collect(Collectors.toList());

        return employeeRelationshipRepository
                .findAllBySourceEmployeeAndRelationshipIn(
                        sourceEmployee,
                        new HashSet<>(relationships))
                .stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<EmployeeRelationship> findCurrentBySourceEmployeeAndRelationships(Employee sourceEmployee, RelationshipName... relationshipNames) {
        List<Relationship> relationships = this.relationshipService
                .findRelationshipsByNames(relationshipNames)
                .collect(Collectors.toList());

        return this.employeeRelationshipRepository
                .findCurrentBySourceEmployeeAndRelationships(
                        sourceEmployee,
                        new HashSet<>(relationships))
                .stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<EmployeeRelationship> findCurrentByTargetEmployeeAndRelationship(Employee targetEmployee, Relationship relationship) {
        return employeeRelationshipRepository
                .findCurrentByTargetEmployeeAndRelationship(
                        targetEmployee,
                        relationship)
                .stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<EmployeeRelationship> findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(Employee sourceEmployee, Employee targetEmployee, Relationship relationship) {
        return employeeRelationshipRepository
                .findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(
                        sourceEmployee,
                        targetEmployee,
                        relationship)
                .stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<EmployeeRelationship> saveAndFlush(EmployeeRelationship employeeRelationship) {
        return Optional.ofNullable(employeeRelationshipRepository.saveAndFlush(employeeRelationship));
    }
}
