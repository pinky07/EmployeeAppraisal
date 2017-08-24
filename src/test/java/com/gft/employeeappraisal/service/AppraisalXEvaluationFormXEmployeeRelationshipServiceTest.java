package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.builder.model.*;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.*;
import com.gft.employeeappraisal.service.impl.AppraisalXEvaluationFormXEmployeeRelationshipServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Service layer test for {@link AppraisalXEvaluationFormXEmployeeRelationshipService}
 *
 * @author Rubén Jiménez
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AppraisalXEvaluationFormXEmployeeRelationshipServiceTest {

    // Required to initialize the class under test

    @Autowired
    private AppraisalXEvaluationFormXEmployeeRelationshipRepository appraisalXEvaluationFormXEmployeeRelationshipRepository;

    @Mock
    private RelationshipService relationshipService;

    // Class under test
    private AppraisalXEvaluationFormXEmployeeRelationshipService appraisalXEvaluationFormXEmployeeRelationshipService;

    // Other repositories

    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private JobFamilyRepository jobFamilyRepository;

    @Autowired
    private JobLevelRepository jobLevelRepository;

    @Autowired
    private AppraisalRepository appraisalRepository;

    @Autowired
    private EvaluationFormRepository evaluationFormRepository;

    @Autowired
    private AppraisalXEvaluationFormRepository appraisalXEvaluationFormRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeRelationshipRepository employeeRelationshipRepository;

    // Test Fixtures
    private ApplicationRole userApplicationRole;
    private Relationship selfRelationship;
    private Relationship peerRelationship;
    private Relationship leadRelationship;
    private Relationship otherRelationship;
    private Relationship mentorRelationship;
    private JobFamily jobFamily;
    private JobLevel jobLevel;
    private Appraisal appraisal;
    private EvaluationForm evaluationForm;
    private AppraisalXEvaluationForm appraisalXEvaluationForm;
    private Employee employee;
    private Employee employeePeer;
    private Employee employeeLead;
    private Employee employeeOther;
    private Employee employeeMentor;
    private EmployeeRelationship selfEmployeeRelationship;
    private EmployeeRelationship peerEmployeeRelationship;
    private EmployeeRelationship leadEmployeeRelationship;
    private EmployeeRelationship otherEmployeeRelationship;
    private EmployeeRelationship mentorEmployeeRelationship;
    private AppraisalXEvaluationFormXEmployeeRelationship selfAppraisalXEvaluationFormXEmployeeRelationship;
    private AppraisalXEvaluationFormXEmployeeRelationship peerfAppraisalXEvaluationFormXEmployeeRelationship;
    private AppraisalXEvaluationFormXEmployeeRelationship leadAppraisalXEvaluationFormXEmployeeRelationship;
    private AppraisalXEvaluationFormXEmployeeRelationship otherAppraisalXEvaluationFormXEmployeeRelationship;
    private AppraisalXEvaluationFormXEmployeeRelationship mentorAppraisalXEvaluationFormXEmployeeRelationship;

    /**
     * Set up. Objects that need to be reinitialized.
     *
     * @throws Exception
     */
    @Before
    public void setUp() {
        // Initialize the class under test
        appraisalXEvaluationFormXEmployeeRelationshipService = new AppraisalXEvaluationFormXEmployeeRelationshipServiceImpl(
                this.relationshipService,
                this.appraisalXEvaluationFormXEmployeeRelationshipRepository);

        // Retrieve the User Application Role
        this.userApplicationRole = this.applicationRoleRepository
                .findByNameIgnoreCase(ApplicationRoleName.USER.name());

        // Retrieve Relationships
        this.selfRelationship = this.relationshipRepository
                .findByName(RelationshipName.SELF.name()).get();
        this.peerRelationship = this.relationshipRepository
                .findByName(RelationshipName.PEER.name()).get();
        this.leadRelationship = this.relationshipRepository
                .findByName(RelationshipName.LEAD.name()).get();
        this.otherRelationship = this.relationshipRepository
                .findByName(RelationshipName.OTHER.name()).get();
        this.mentorRelationship = this.relationshipRepository
                .findByName(RelationshipName.MENTOR.name()).get();

        // Test Job Family & Job Level
        this.jobFamily = this.jobFamilyRepository.saveAndFlush(new JobFamilyBuilder()
                .buildWithDefaults());
        this.jobLevel = this.jobLevelRepository.saveAndFlush(new JobLevelBuilder()
                .jobFamily(jobFamily)
                .buildWithDefaults());

        // Test Appraisal
        this.appraisal = this.appraisalRepository.saveAndFlush(new AppraisalBuilder()
                .buildWithDefaults());

        // Test Evaluation Form
        this.evaluationForm = this.evaluationFormRepository.saveAndFlush(new EvaluationFormBuilder()
                .buildWithDefaults());

        // Test
        this.appraisalXEvaluationForm = this.appraisalXEvaluationFormRepository.saveAndFlush(new AppraisalXEvaluationFormBuilder()
                .appraisal(this.appraisal)
                .evaluationForm(this.evaluationForm)
                .buildWithDefaults());

        // Test Employees
        this.employee = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Employee")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        this.employeePeer = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Peer")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        this.employeeLead = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Lead")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        this.employeeOther = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Lead")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        this.employeeMentor = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Mentor")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());

        // Test Employee Relationships
        this.selfEmployeeRelationship = this.employeeRelationshipRepository.saveAndFlush(new EmployeeRelationshipBuilder()
                .sourceEmployee(employee)
                .targetEmployee(employee)
                .relationship(selfRelationship)
                .buildWithDefaults());
        this.peerEmployeeRelationship = this.employeeRelationshipRepository.saveAndFlush(new EmployeeRelationshipBuilder()
                .sourceEmployee(employee)
                .targetEmployee(employeePeer)
                .relationship(peerRelationship)
                .buildWithDefaults());
        this.leadEmployeeRelationship = this.employeeRelationshipRepository.saveAndFlush(new EmployeeRelationshipBuilder()
                .sourceEmployee(employee)
                .targetEmployee(employeeLead)
                .relationship(leadRelationship)
                .buildWithDefaults());
        this.otherEmployeeRelationship = this.employeeRelationshipRepository.saveAndFlush(new EmployeeRelationshipBuilder()
                .sourceEmployee(employee)
                .targetEmployee(employeeOther)
                .relationship(otherRelationship)
                .buildWithDefaults());
        this.mentorEmployeeRelationship = this.employeeRelationshipRepository.saveAndFlush(new EmployeeRelationshipBuilder()
                .sourceEmployee(employeeMentor)
                .targetEmployee(employee)
                .relationship(mentorRelationship)
                .buildWithDefaults());

        // Test AppraisalXEvaluationFormXEmployeeRelationship
        this.selfAppraisalXEvaluationFormXEmployeeRelationship = this.appraisalXEvaluationFormXEmployeeRelationshipRepository
                .saveAndFlush(new AppraisalXEvaluationFormXEmployeeRelationshipBuilder()
                        .appraisalXEvaluationForm(appraisalXEvaluationForm)
                        .employeeRelationship(selfEmployeeRelationship)
                        .evaluationStatus(EvaluationStatus.PENDING).buildWithDefaults());
        this.peerfAppraisalXEvaluationFormXEmployeeRelationship = this.appraisalXEvaluationFormXEmployeeRelationshipRepository
                .saveAndFlush(new AppraisalXEvaluationFormXEmployeeRelationshipBuilder()
                        .appraisalXEvaluationForm(appraisalXEvaluationForm)
                        .employeeRelationship(peerEmployeeRelationship)
                        .evaluationStatus(EvaluationStatus.PENDING).buildWithDefaults());
        this.leadAppraisalXEvaluationFormXEmployeeRelationship = this.appraisalXEvaluationFormXEmployeeRelationshipRepository
                .saveAndFlush(new AppraisalXEvaluationFormXEmployeeRelationshipBuilder()
                        .appraisalXEvaluationForm(appraisalXEvaluationForm)
                        .employeeRelationship(leadEmployeeRelationship)
                        .evaluationStatus(EvaluationStatus.PENDING).buildWithDefaults());
        this.otherAppraisalXEvaluationFormXEmployeeRelationship = this.appraisalXEvaluationFormXEmployeeRelationshipRepository
                .saveAndFlush(new AppraisalXEvaluationFormXEmployeeRelationshipBuilder()
                        .appraisalXEvaluationForm(appraisalXEvaluationForm)
                        .employeeRelationship(otherEmployeeRelationship)
                        .evaluationStatus(EvaluationStatus.PENDING).buildWithDefaults());
        this.mentorAppraisalXEvaluationFormXEmployeeRelationship = this.appraisalXEvaluationFormXEmployeeRelationshipRepository
                .saveAndFlush(new AppraisalXEvaluationFormXEmployeeRelationshipBuilder()
                        .appraisalXEvaluationForm(appraisalXEvaluationForm)
                        .employeeRelationship(mentorEmployeeRelationship)
                        .evaluationStatus(EvaluationStatus.PENDING).buildWithDefaults());
    }

    /**
     * Tests if the method {@link AppraisalXEvaluationFormXEmployeeRelationshipService#findByAppraisalAndEmployee(Appraisal, Employee)}
     * executes successfully
     */
    @Test
    public void findByAppraisalAndEmployee_Successful() {
        // Set up
        when(relationshipService.findRelationshipsByNames(
                RelationshipName.SELF,
                RelationshipName.PEER,
                RelationshipName.LEAD,
                RelationshipName.OTHER))
                .thenReturn(Stream.of(
                        selfRelationship,
                        peerRelationship,
                        leadRelationship,
                        otherRelationship));
        when(relationshipService.findRelationshipsByNames(
                RelationshipName.MENTOR))
                .thenReturn(Stream.of(
                        mentorRelationship));

        // Execution
        List<AppraisalXEvaluationFormXEmployeeRelationship> result = this.appraisalXEvaluationFormXEmployeeRelationshipService.findByAppraisalAndEmployee(appraisal, employee)
                .collect(Collectors.toList());

        // Verification
        assertTrue(result.size() == 5);
        assertTrue(result.contains(selfAppraisalXEvaluationFormXEmployeeRelationship));
        assertTrue(result.contains(peerfAppraisalXEvaluationFormXEmployeeRelationship));
        assertTrue(result.contains(leadAppraisalXEvaluationFormXEmployeeRelationship));
        assertTrue(result.contains(otherAppraisalXEvaluationFormXEmployeeRelationship));
        assertTrue(result.contains(mentorAppraisalXEvaluationFormXEmployeeRelationship));
    }
}
