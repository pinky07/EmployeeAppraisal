package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.repository.EmployeeRelationshipRepository;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import com.gft.employeeappraisal.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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

    @Autowired
    private EmployeeRelationshipRepository employeeRelationshipRepository;

    @Autowired
    private RelationshipService relationshipService;

    public EmployeeRelationshipServiceImpl() {
        super();
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
        // End any previous MENTOR Relationships
        this.findCurrentByTargetEmployeeAndRelationship(
                mentee,
                relationshipService.findByName(RelationshipName.MENTOR)
        ).forEach(this::endEmployeeRelationship);

        // Start a new MENTOR Relationship
        startEmployeeRelationship(
                mentor,
                mentee,
                relationshipService.findByName(RelationshipName.MENTOR));
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
    public void startEmployeeRelationship(Employee sourceEmployee, Employee targetEmployee, Relationship relationship) {
        EmployeeRelationship employeeRelationship = new EmployeeRelationship();
        employeeRelationship.setSourceEmployee(sourceEmployee);
        employeeRelationship.setTargetEmployee(targetEmployee);
        employeeRelationship.setRelationship(relationship);
        employeeRelationship.setStartDate(LocalDateTime.now());
        employeeRelationship.setEndDate(null);
        this.saveAndFlush(employeeRelationship);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endEmployeeRelationship(EmployeeRelationship employeeRelationship) {
        employeeRelationship.setEndDate(LocalDateTime.now());
        this.saveAndFlush(employeeRelationship);
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
	public Stream<EmployeeRelationship> findCurrentBySourceEmployeeAndRelationships(Employee sourceEmployee, RelationshipName... relationshipNames) {
    	List<Relationship> relationships = relationshipService.findRelationshipsByNames(relationshipNames).collect(Collectors.toList());

		return employeeRelationshipRepository
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
    public void saveAndFlush(EmployeeRelationship employeeRelationship) {
        employeeRelationshipRepository.saveAndFlush(employeeRelationship);
    }
}
