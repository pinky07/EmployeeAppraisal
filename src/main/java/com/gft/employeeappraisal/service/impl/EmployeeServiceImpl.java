package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.EmployeeNotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.EmployeeRepository;
import com.gft.employeeappraisal.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service implementation of {@link EmployeeService}
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private ApplicationRoleService applicationRoleService;
    private EmployeeRelationshipService employeeRelationshipService;
    private JobLevelService jobLevelService;
    private RelationshipService relationshipService;
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(
            ApplicationRoleService applicationRoleService,
            EmployeeRelationshipService employeeRelationshipService,
            JobLevelService jobLevelService,
            RelationshipService relationshipService,
            EmployeeRepository employeeRepository) {
        this.applicationRoleService = applicationRoleService;
        this.employeeRelationshipService = employeeRelationshipService;
        this.jobLevelService = jobLevelService;
        this.relationshipService = relationshipService;
        this.employeeRepository = employeeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getLoggedInUser() throws EmployeeNotFoundException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.findByEmail(userEmail)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(
                        "Logged in user with email: %s couldn't be found",
                        userEmail)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> findById(Integer id) {
        return Optional.ofNullable(employeeRepository.findOne(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> findByEmail(String email) {
        return Optional.ofNullable(employeeRepository.findByEmail(email));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> findCurrentMentorById(int menteeId) throws EmployeeNotFoundException {
        // Try to find the Mentee
        Employee mentee = this.findById(menteeId).orElseThrow(() ->
                new EmployeeNotFoundException(String.format("Employee with id: %s was not found", menteeId)));
        // Try to get his Mentor
        return employeeRelationshipService
                .findCurrentByTargetEmployeeAndRelationship(
                        mentee,
                        relationshipService.findByName(RelationshipName.MENTOR))
                .map(EmployeeRelationship::getSourceEmployee)
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Employee> findCurrentMenteesById(int mentorId) throws EmployeeNotFoundException {
        // Try to find the Mentor
        Employee mentor = this.findById(mentorId).orElseThrow(() ->
                new EmployeeNotFoundException(String.format("Employee with id: %s was not found", mentorId)));
        // Try to get his Mentees
        return employeeRelationshipService
                .findCurrentBySourceEmployeeAndRelationship(
                        mentor,
                        relationshipService.findByName(RelationshipName.MENTOR))
                .map(EmployeeRelationship::getTargetEmployee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Employee> findCurrentPeersById(int employeeId) throws EmployeeNotFoundException {
        // Try to find the Employee
        Employee employee = this.findById(employeeId).orElseThrow(() ->
                new EmployeeNotFoundException(String.format("Employee with id: %s was not found", employeeId)));
        // Try to get his Peers
        return employeeRelationshipService
                .findCurrentBySourceEmployeeAndRelationship(
                        employee,
                        relationshipService.findByName(RelationshipName.PEER))
                .map(EmployeeRelationship::getTargetEmployee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<EmployeeRelationship> findCurrentRelationshipsById(int employeeId, RelationshipName... relationshipNames)
            throws EmployeeNotFoundException {
        // Try to find the Employee
        Employee employee = this.findById(employeeId).orElseThrow(() ->
                new EmployeeNotFoundException(String.format("Employee with id: %s was not found", employeeId)));
        // Try to get his Peers
        return findCurrentRelationshipsBySourceEmployee(employee, relationshipNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<EmployeeRelationship> findCurrentRelationshipsBySourceEmployee(Employee employee, RelationshipName... relationshipNames)
            throws EmployeeNotFoundException {
        // Try to get his References
        return employeeRelationshipService
                .findCurrentBySourceEmployeeAndRelationships(
                        employee, relationshipNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdmin(Employee employee) {
        return employee.getApplicationRole().getName().equals(ApplicationRoleNames.ADMIN.name()); // admin
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> saveAndFlush(Employee employee) {
        Employee savedEmployee = null;
        Optional<ApplicationRole> applicationRole = applicationRoleService.findById(employee.getApplicationRole().getId());
        Optional<JobLevel> jobLevel = jobLevelService.findById(employee.getJobLevel().getId());

        if (applicationRole.isPresent() && jobLevel.isPresent()) {
            savedEmployee = new Employee();
            savedEmployee.setFirstName(employee.getFirstName());
            savedEmployee.setLastName(employee.getLastName());
            savedEmployee.setGftIdentifier(employee.getGftIdentifier());
            savedEmployee.setEmail(employee.getEmail());
            savedEmployee.setApplicationRole(applicationRole.get());
            savedEmployee.setJobLevel(jobLevel.get());

            savedEmployee = employeeRepository.saveAndFlush(savedEmployee);
        } else if (!applicationRole.isPresent()) {
            log.error("Application Role:" + employee.getApplicationRole().getId() + " does not exist.");
        } else {
            log.error("Job Level:" + employee.getJobLevel().getId() + " does not exist.");
        }

        return Optional.ofNullable(savedEmployee);
    }
}
