package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.builder.model.EmployeeEvaluationFormBuilder;
import com.gft.employeeappraisal.exception.EmployeeAppraisalMicroserviceTestException;
import com.gft.employeeappraisal.helper.builder.model.AppraisalBuilder;
import com.gft.employeeappraisal.helper.builder.model.AppraisalXEvaluationFormBuilder;
import com.gft.employeeappraisal.helper.builder.model.EmployeeBuilder;
import com.gft.employeeappraisal.helper.builder.model.EvaluationFormBuilder;
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

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.Assert.assertTrue;


/**
 * Repository layer test for  {@link EmployeeEvaluationFormRepository}
 *
 * @author Rubén Jiménez
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeEvaluationFormRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(EmployeeEvaluationFormRepositoryTest.class);

    @Autowired
    private AppraisalRepository appraisalRepository;

    @Autowired
    private JobLevelRepository jobLevelRepository;

    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EvaluationFormRepository evaluationFormRepository;

    @Autowired
    private AppraisalXEvaluationFormRepository appraisalXEvaluationFormRepository;

    @Autowired
    private EmployeeEvaluationFormRepository employeeEvaluationFormRepository;

    // Fixtures

    private Employee employeeA;
    private Employee employeeB;
    private Appraisal appraisal;
    private EmployeeEvaluationForm a2BEmployeeEvaluationForm;
    private EmployeeEvaluationForm a2AEmployeeEvaluationForm;

    /**
     * Utility method to create test fixtures.
     * TODO Determine if @Before annotation can work with this method and still rollback the changes
     */
    private void setUp() {
        JobLevel jobLevel = Optional.of(this.jobLevelRepository.findOne(1))
                .orElseThrow(() -> new EmployeeAppraisalMicroserviceTestException("Couldn't find Job Level with id 1"));

        ApplicationRole applicationRole = Optional.of(this.applicationRoleRepository.findOne(1))
                .orElseThrow(() -> new EmployeeAppraisalMicroserviceTestException("Couldn't find Application Role with id 1"));

        employeeA = new EmployeeBuilder()
                .id(1)
                .email("employeeA@gft.com")
                .firstName("A")
                .lastName("A")
                .gftIdentifier("AAAA")
                .jobLevel(jobLevel)
                .applicationRole(applicationRole)
                .build();

        employeeB = new EmployeeBuilder()
                .id(2)
                .email("employeeB@gft.com")
                .firstName("B")
                .lastName("B")
                .gftIdentifier("BBBB")
                .jobLevel(jobLevel)
                .applicationRole(applicationRole)
                .build();

        appraisal = new AppraisalBuilder()
                .name("Appraisal Test")
                .description("Appraisal Test Description")
                .startDate(OffsetDateTime.now().minusDays(1))
                .endDate(OffsetDateTime.now().plusDays(1))
                .build();

        EvaluationForm evaluationForm = new EvaluationFormBuilder()
                .name("Test Evaluation Form")
                .description("Test Description")
                .build();

        AppraisalXEvaluationForm appraisalXEvaluationForm = new AppraisalXEvaluationFormBuilder()
                .appraisal(appraisal)
                .evaluationForm(evaluationForm)
                .build();

        a2BEmployeeEvaluationForm = new EmployeeEvaluationFormBuilder()
                .appraisalXEvaluationForm(appraisalXEvaluationForm)
                .employeeId(employeeA.getId())
                .filledByEmployeeId(employeeB.getId())
                .evaluationStatus(EvaluationStatus.PENDING)
                .buildWithDefaults();

        a2AEmployeeEvaluationForm = new EmployeeEvaluationFormBuilder()
                .appraisalXEvaluationForm(appraisalXEvaluationForm)
                .employeeId(employeeA.getId())
                .filledByEmployeeId(employeeA.getId())
                .evaluationStatus(EvaluationStatus.PENDING)
                .buildWithDefaults();

        employeeRepository.saveAndFlush(employeeA);
        employeeRepository.saveAndFlush(employeeB);
        appraisalRepository.saveAndFlush(appraisal);
        evaluationFormRepository.saveAndFlush(evaluationForm);
        appraisalXEvaluationFormRepository.saveAndFlush(appraisalXEvaluationForm);
        employeeEvaluationFormRepository.saveAndFlush(a2BEmployeeEvaluationForm);
        employeeEvaluationFormRepository.saveAndFlush(a2AEmployeeEvaluationForm);
    }


    /**
     * Tests if the method {@link EmployeeEvaluationFormRepository#findByAppraisalAndSourceEmployee(Appraisal, int)}
     * executes successfully
     */
    @Test
    @Rollback
    public void findByAppraisalAndSourceEmployee_Successful() {
        logger.debug("Test: findByAppraisalAndSourceEmployee_Successful()");

        // Set up
        setUp();

        // Execution
        List<EmployeeEvaluationForm> result =
                employeeEvaluationFormRepository.findByAppraisalAndSourceEmployee(appraisal, employeeA.getId());

        // Verification
        // Has SELF and PEER evaluation
        assertTrue(result.size() == 2);

        assertTrue(result.get(0).getId() == a2BEmployeeEvaluationForm.getId());
        assertTrue(result.get(0).getAppraisalXEvaluationForm() == a2BEmployeeEvaluationForm.getAppraisalXEvaluationForm());
        assertTrue(result.get(0).getEvaluationStatus() == a2BEmployeeEvaluationForm.getEvaluationStatus());
    }

    /**
     * Tests if the method {@link EmployeeEvaluationFormRepository#findByAppraisalAndTargetEmployee(Appraisal, int)}
     * executes successfully
     */
    @Test
    @Rollback
    public void findByAppraisalAndTargetEmployee_Successful() {
        logger.debug("Test: findByAppraisalAndSourceEmployee_Successful()");

        // Set up
        setUp();

        // Execution
        List<EmployeeEvaluationForm> result =
                employeeEvaluationFormRepository.findByAppraisalAndTargetEmployee(appraisal, employeeB.getId());

        // Verification

        assertTrue(result.size() == 1);

        assertTrue(result.get(0).getId() == a2BEmployeeEvaluationForm.getId());
        assertTrue(result.get(0).getAppraisalXEvaluationForm() == a2BEmployeeEvaluationForm.getAppraisalXEvaluationForm());
        assertTrue(result.get(0).getEvaluationStatus() == a2BEmployeeEvaluationForm.getEvaluationStatus());
    }

    /**
     * Tests if the method {@link EmployeeEvaluationFormRepository#findByAppraisalAndEmployeeAsSourceAndTarget(int, Set)}
     * executes successfully
     */
    @Test
    @Rollback
    public void findByAppraisalAndEmployeeAsSourceAndTarget_Successful() {
        logger.debug("Test: findByAppraisalAndSourceEmployee_Successful()");

        // Set up
        setUp();

        // Execution
        List<EmployeeEvaluationForm> result =
                employeeEvaluationFormRepository.findByAppraisalAndEmployeeAsSourceAndTarget(employeeA.getId(),
                        new HashSet<>(Collections.singletonList(EvaluationStatus.PENDING)));

        // Verification

        assertTrue(result.size() == 1);

        assertTrue(result.get(0).getId() == a2AEmployeeEvaluationForm.getId());
        assertTrue(result.get(0).getAppraisalXEvaluationForm() == a2AEmployeeEvaluationForm.getAppraisalXEvaluationForm());
        assertTrue(result.get(0).getEvaluationStatus() == a2AEmployeeEvaluationForm.getEvaluationStatus());
    }
}
