package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.builder.model.*;
import com.gft.employeeappraisal.exception.EmployeeAppraisalMicroserviceTestException;
import com.gft.employeeappraisal.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.Assert.assertTrue;


/**
 * Repository layer test for  {@link AppraisalXEvaluationFormXEmployeeRelationshipRepository}
 *
 * @author Rubén Jiménez
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class AppraisalXEvaluationFormXEmployeeRelationshipRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(AppraisalXEvaluationFormXEmployeeRelationshipRepositoryTest.class);

    @Autowired
    private AppraisalRepository appraisalRepository;

    @Autowired
    private JobLevelRepository jobLevelRepository;

    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private EmployeeRelationshipRepository employeeRelationshipRepository;

    @Autowired
    private EvaluationFormRepository evaluationFormRepository;

    @Autowired
    private AppraisalXEvaluationFormRepository appraisalXEvaluationFormRepository;

    @Autowired
    private AppraisalXEvaluationFormXEmployeeRelationshipRepository appraisalXEvaluationFormXEmployeeRelationshipRepository;

    // Fixtures

    private JobLevel jobLevel;
    private ApplicationRole applicationRole;
    private Employee employeeA;
    private Employee employeeB;
    private Relationship selfRelationship;
    private Relationship peerRelationship;
    private Set<Relationship> selfRelationshipSet;
    private Set<Relationship> peerRelationshipSet;
    private EmployeeRelationship a2bEmployeeRelationship;
    private EmployeeRelationship a2aEmployeeRelationship;
    private Appraisal appraisal;
    private EvaluationForm evaluationForm;
    private AppraisalXEvaluationForm appraisalXEvaluationForm;
    private AppraisalXEvaluationFormXEmployeeRelationship a2bAppraisalXEvaluationFormXEmployeeRelationship;
    private AppraisalXEvaluationFormXEmployeeRelationship a2aAppraisalXEvaluationFormXEmployeeRelationship;

    /**
     * Utility method to create test fixtures.
     * TODO Determine if @Before annotation can work with this method and still rollback the changes
     */
    private void setUp() {
        jobLevel = Optional.of(this.jobLevelRepository.findOne(1))
                .orElseThrow(() -> new EmployeeAppraisalMicroserviceTestException("Couldn't find Job Level with id 1"));

        applicationRole = Optional.of(this.applicationRoleRepository.findOne(1))
                .orElseThrow(() -> new EmployeeAppraisalMicroserviceTestException("Couldn't find Application Role with id 1"));

        employeeA = new EmployeeBuilder()
                .email("employeeA@gft.com")
                .firstName("A")
                .lastName("A")
                .gftIdentifier("AAAA")
                .jobLevel(jobLevel)
                .applicationRole(applicationRole)
                .build();

        employeeB = new EmployeeBuilder()
                .email("employeeB@gft.com")
                .firstName("B")
                .lastName("B")
                .gftIdentifier("BBBB")
                .jobLevel(jobLevel)
                .applicationRole(applicationRole)
                .build();

        selfRelationship = this.relationshipRepository.findByName(RelationshipName.SELF.name())
                .orElseThrow(() -> new EmployeeAppraisalMicroserviceTestException("Couldn't find Relationship Name PEER"));

        peerRelationship = this.relationshipRepository.findByName(RelationshipName.PEER.name())
                .orElseThrow(() -> new EmployeeAppraisalMicroserviceTestException("Couldn't find Relationship Name PEER"));

        selfRelationshipSet = new HashSet<>(Collections.singletonList(selfRelationship));

        peerRelationshipSet = new HashSet<>(Collections.singletonList(peerRelationship));

        a2bEmployeeRelationship = new EmployeeRelationshipBuilder()
                .sourceEmployee(employeeA)
                .targetEmployee(employeeB)
                .startDate(OffsetDateTime.now().minusDays(1))
                .endDate(OffsetDateTime.now().plusDays(1))
                .relationship(peerRelationship)
                .build();

        a2aEmployeeRelationship = new EmployeeRelationshipBuilder()
                .sourceEmployee(employeeA)
                .targetEmployee(employeeA)
                .startDate(OffsetDateTime.now().minusDays(1))
                .endDate(OffsetDateTime.now().plusDays(1))
                .relationship(selfRelationship)
                .build();

        appraisal = new AppraisalBuilder()
                .name("Appraisal Test")
                .description("Appraisal Test Description")
                .startDate(OffsetDateTime.now().minusDays(1))
                .endDate(OffsetDateTime.now().plusDays(1))
                .build();

        evaluationForm = new EvaluationFormBuilder()
                .name("Test Evaluation Form")
                .description("Test Description")
                .build();

        appraisalXEvaluationForm = new AppraisalXEvaluationFormBuilder()
                .appraisal(appraisal)
                .evaluationForm(evaluationForm)
                .build();

        a2bAppraisalXEvaluationFormXEmployeeRelationship = new AppraisalXEvaluationFormXEmployeeRelationshipBuilder()
                .appraisalXEvaluationForm(appraisalXEvaluationForm)
                .employeeRelationship(a2bEmployeeRelationship)
                .evaluationStatus(EvaluationStatus.PENDING)
                .build();

        a2aAppraisalXEvaluationFormXEmployeeRelationship = new AppraisalXEvaluationFormXEmployeeRelationshipBuilder()
                .appraisalXEvaluationForm(appraisalXEvaluationForm)
                .employeeRelationship(a2aEmployeeRelationship)
                .evaluationStatus(EvaluationStatus.PENDING)
                .build();

        employeeRepository.saveAndFlush(employeeA);
        employeeRepository.saveAndFlush(employeeB);
        employeeRelationshipRepository.saveAndFlush(a2bEmployeeRelationship);
        employeeRelationshipRepository.saveAndFlush(a2aEmployeeRelationship);
        appraisalRepository.saveAndFlush(appraisal);
        evaluationFormRepository.saveAndFlush(evaluationForm);
        appraisalXEvaluationFormRepository.saveAndFlush(appraisalXEvaluationForm);
        appraisalXEvaluationFormXEmployeeRelationshipRepository.saveAndFlush(a2bAppraisalXEvaluationFormXEmployeeRelationship);
        appraisalXEvaluationFormXEmployeeRelationshipRepository.saveAndFlush(a2aAppraisalXEvaluationFormXEmployeeRelationship);
    }


    /**
     * Tests if the method {@link AppraisalXEvaluationFormXEmployeeRelationshipRepository#findByAppraisalAndSourceEmployee(Appraisal, Employee, Set)}
     * executes successfully
     */
    @Test
    @Rollback
    public void findByAppraisalAndSourceEmployee_Successful() {
        logger.debug("Test: findByAppraisalAndSourceEmployee_Successful()");

        // Set up
        setUp();

        // Execution
        List<AppraisalXEvaluationFormXEmployeeRelationship> result =
                appraisalXEvaluationFormXEmployeeRelationshipRepository.findByAppraisalAndSourceEmployee(appraisal, employeeA, peerRelationshipSet);

        // Verification

        assertTrue(result.size() == 1);

        assertTrue(result.get(0).getId() == a2bAppraisalXEvaluationFormXEmployeeRelationship.getId());
        assertTrue(result.get(0).getAppraisalXEvaluationForm() == a2bAppraisalXEvaluationFormXEmployeeRelationship.getAppraisalXEvaluationForm());
        assertTrue(result.get(0).getEmployeeRelationship() == a2bAppraisalXEvaluationFormXEmployeeRelationship.getEmployeeRelationship());
        assertTrue(result.get(0).getEvaluationStatus() == a2bAppraisalXEvaluationFormXEmployeeRelationship.getEvaluationStatus());
    }

    /**
     * Tests if the method {@link AppraisalXEvaluationFormXEmployeeRelationshipRepository#findByAppraisalAndTargetEmployee(Appraisal, Employee, Set)}
     * executes successfully
     */
    @Test
    @Rollback
    public void findByAppraisalAndTargetEmployee_Successful() {
        logger.debug("Test: findByAppraisalAndSourceEmployee_Successful()");

        // Set up
        setUp();

        // Execution
        List<AppraisalXEvaluationFormXEmployeeRelationship> result =
                appraisalXEvaluationFormXEmployeeRelationshipRepository.findByAppraisalAndTargetEmployee(appraisal, employeeB, peerRelationshipSet);

        // Verification

        assertTrue(result.size() == 1);

        assertTrue(result.get(0).getId() == a2bAppraisalXEvaluationFormXEmployeeRelationship.getId());
        assertTrue(result.get(0).getAppraisalXEvaluationForm() == a2bAppraisalXEvaluationFormXEmployeeRelationship.getAppraisalXEvaluationForm());
        assertTrue(result.get(0).getEmployeeRelationship() == a2bAppraisalXEvaluationFormXEmployeeRelationship.getEmployeeRelationship());
        assertTrue(result.get(0).getEvaluationStatus() == a2bAppraisalXEvaluationFormXEmployeeRelationship.getEvaluationStatus());
    }

    /**
     * Tests if the method {@link AppraisalXEvaluationFormXEmployeeRelationshipRepository#findByAppraisalAndEmployeeAsSourceAndTarget(Appraisal, Employee, Set)}
     * executes successfully
     */
    @Test
    @Rollback
    public void findByAppraisalAndEmployeeAsSourceAndTarget_Successful() {
        logger.debug("Test: findByAppraisalAndSourceEmployee_Successful()");

        // Set up
        setUp();

        // Execution
        List<AppraisalXEvaluationFormXEmployeeRelationship> result =
                appraisalXEvaluationFormXEmployeeRelationshipRepository.findByAppraisalAndEmployeeAsSourceAndTarget(appraisal, employeeA, selfRelationshipSet);

        // Verification

        assertTrue(result.size() == 1);

        assertTrue(result.get(0).getId() == a2aAppraisalXEvaluationFormXEmployeeRelationship.getId());
        assertTrue(result.get(0).getAppraisalXEvaluationForm() == a2aAppraisalXEvaluationFormXEmployeeRelationship.getAppraisalXEvaluationForm());
        assertTrue(result.get(0).getEmployeeRelationship() == a2aAppraisalXEvaluationFormXEmployeeRelationship.getEmployeeRelationship());
        assertTrue(result.get(0).getEvaluationStatus() == a2aAppraisalXEvaluationFormXEmployeeRelationship.getEvaluationStatus());
    }
}
