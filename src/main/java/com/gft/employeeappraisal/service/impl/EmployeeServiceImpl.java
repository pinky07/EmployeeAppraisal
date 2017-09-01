package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.EmployeeRepository;
import com.gft.employeeappraisal.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public Employee getLoggedInUser() throws NotFoundException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Logged in user with email: %s couldn't be found",
                        userEmail)));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<Employee> findPagedByFirstNameOrLastName(String firstName, String lastName, int pageNumber, int pageSize) {
        return employeeRepository
                .findByFirstNameContainsOrLastNameContainsAllIgnoreCase(firstName,
                        lastName, new PageRequest(pageNumber, pageSize)).getContent().stream();
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
    public Employee getById(Integer id) throws NotFoundException {
        return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "Employee with Id %d couldn't be found",
                id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> findByEmail(String email) {
        return Optional.ofNullable(employeeRepository.findByEmailIgnoreCase(email));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> findCurrentMentorById(int menteeId) throws NotFoundException {
        // Try to find the Mentee
        Employee mentee = this.getById(menteeId);
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
    public Employee getCurrentMentorById(int menteeId) throws NotFoundException {
        return this.findCurrentMentorById(menteeId).orElseThrow(() -> new NotFoundException(String.format(
                "Mentor for Employee with Id: %d was not found",
                menteeId)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Employee> findCurrentMenteesById(int mentorId) throws NotFoundException {
        // Try to find the Mentor
        Employee mentor = this.getById(mentorId);
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
    public Stream<Employee> findCurrentPeersById(int employeeId) throws NotFoundException {
        // Try to find the Employee
        Employee employee = this.getById(employeeId);
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
            throws NotFoundException {
        // Try to find the Employee
        Employee employee = this.getById(employeeId);
        // Try to get his Peers
        return findCurrentRelationshipsBySourceEmployee(employee, relationshipNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<EmployeeRelationship> findCurrentRelationshipsBySourceEmployee(Employee employee, RelationshipName... relationshipNames)
            throws NotFoundException {
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
        return employee.getApplicationRole().getName().equals(ApplicationRoleName.ADMIN.name()); // admin
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> saveAndFlush(Employee employee) {

        Optional<ApplicationRole> applicationRole = applicationRoleService.findById(employee.getApplicationRole().getId());
        Optional<JobLevel> jobLevel = jobLevelService.findById(employee.getJobLevel().getId());

        Employee newEmployee = null;

        if (applicationRole.isPresent() && jobLevel.isPresent()) {

            newEmployee = new Employee();
            newEmployee.setFirstName(employee.getFirstName());
            newEmployee.setLastName(employee.getLastName());
            newEmployee.setGftIdentifier(employee.getGftIdentifier());
            newEmployee.setEmail(employee.getEmail());
            newEmployee.setApplicationRole(applicationRole.get());
            newEmployee.setJobLevel(jobLevel.get());

            newEmployee = employeeRepository.saveAndFlush(newEmployee);
        } else {
            if (!applicationRole.isPresent()) {
                log.error("Application Role with Id: " + employee.getApplicationRole().getId() + " does not exist.");
            }
            if (!jobLevel.isPresent()) {
                log.error("Job Level with Id: " + employee.getJobLevel().getId() + " does not exist.");
            }
        }

        return Optional.ofNullable(newEmployee);
    }
}
