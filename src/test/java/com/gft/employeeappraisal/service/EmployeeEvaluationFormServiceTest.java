package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.builder.model.*;
import com.gft.employeeappraisal.helper.builder.model.*;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.*;
import com.gft.employeeappraisal.service.impl.EmployeeEvaluationFormServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Service layer test for {@link EmployeeEvaluationFormService}
 *
 * @author Rubén Jiménez
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeEvaluationFormServiceTest extends BaseServiceTest {

    // Class under test
    private EmployeeEvaluationFormService employeeEvaluationFormService;

    // Test fixtures
    private Appraisal appraisal;
    private Employee employee;
    private EmployeeEvaluationForm selfEmployeeEvaluationForm;
    private EmployeeEvaluationForm peerfEmployeeEvaluationForm;
    private EmployeeEvaluationForm leadEmployeeEvaluationForm;
    private EmployeeEvaluationForm otherEmployeeEvaluationForm;
    private EmployeeEvaluationForm mentorEmployeeEvaluationForm;

    /**
     * Set up. Objects that need to be reinitialized.
     *
     * @throws Exception
     */
    @Before
    public void setUp() {
        // Initialize the class under test
        employeeEvaluationFormService = new EmployeeEvaluationFormServiceImpl(
                this.employeeEvaluationFormRepository);

        // Retrieve the User Application Role
        ApplicationRole userApplicationRole = this.applicationRoleRepository
                .findByNameIgnoreCase(ApplicationRoleName.USER.name());

        // Test Job Family & Job Level
        JobFamily jobFamily = this.jobFamilyRepository.saveAndFlush(new JobFamilyBuilder()
                .buildWithDefaults());
        JobLevel jobLevel = this.jobLevelRepository.saveAndFlush(new JobLevelBuilder()
                .jobFamily(jobFamily)
                .buildWithDefaults());

        // Test Appraisal
        this.appraisal = this.appraisalRepository.saveAndFlush(new AppraisalBuilder()
                .buildWithDefaults());

        // Test Evaluation Form
        EvaluationForm evaluationForm = this.evaluationFormRepository.saveAndFlush(new EvaluationFormBuilder()
                .buildWithDefaults());

        // Test
        AppraisalXEvaluationForm appraisalXEvaluationForm = this.appraisalXEvaluationFormRepository.saveAndFlush(new AppraisalXEvaluationFormBuilder()
                .appraisal(this.appraisal)
                .evaluationForm(evaluationForm)
                .buildWithDefaults());

        // Test Employees
        this.employee = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .id(1)
                .firstName("Employee")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        Employee employeePeer = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .id(2)
                .firstName("Peer")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        Employee employeeLead = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .id(3)
                .firstName("Lead")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        Employee employeeOther = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .id(4)
                .firstName("Lead")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        Employee employeeMentor = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .id(5)
                .firstName("Mentor")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());

        // Test EmployeeEvaluationForm
        this.selfEmployeeEvaluationForm = this.employeeEvaluationFormRepository
                .saveAndFlush(new EmployeeEvaluationFormBuilder()
                        .appraisalXEvaluationForm(appraisalXEvaluationForm)
                        .employeeId(employee.getId())
                        .filledByEmployeeId(employee.getId())
                        .mentorId(employeeMentor.getId())
                        .evaluationStatus(EvaluationStatus.PENDING).buildWithDefaults());
        this.peerfEmployeeEvaluationForm = this.employeeEvaluationFormRepository
                .saveAndFlush(new EmployeeEvaluationFormBuilder()
                        .appraisalXEvaluationForm(appraisalXEvaluationForm)
                        .employeeId(employee.getId())
                        .filledByEmployeeId(employeePeer.getId())
                        .mentorId(employeeMentor.getId())
                        .evaluationStatus(EvaluationStatus.PENDING).buildWithDefaults());
        this.leadEmployeeEvaluationForm = this.employeeEvaluationFormRepository
                .saveAndFlush(new EmployeeEvaluationFormBuilder()
                        .appraisalXEvaluationForm(appraisalXEvaluationForm)
                        .employeeId(employee.getId())
                        .filledByEmployeeId(employeeLead.getId())
                        .mentorId(employeeMentor.getId())
                        .evaluationStatus(EvaluationStatus.PENDING).buildWithDefaults());
        this.otherEmployeeEvaluationForm = this.employeeEvaluationFormRepository
                .saveAndFlush(new EmployeeEvaluationFormBuilder()
                        .appraisalXEvaluationForm(appraisalXEvaluationForm)
                        .employeeId(employee.getId())
                        .filledByEmployeeId(employeeOther.getId())
                        .mentorId(employeeMentor.getId())
                        .evaluationStatus(EvaluationStatus.PENDING).buildWithDefaults());
        this.mentorEmployeeEvaluationForm = this.employeeEvaluationFormRepository
                .saveAndFlush(new EmployeeEvaluationFormBuilder()
                        .appraisalXEvaluationForm(appraisalXEvaluationForm)
                        .employeeId(employee.getId())
                        .filledByEmployeeId(employeeMentor.getId())
                        .mentorId(employeeMentor.getId())
                        .evaluationStatus(EvaluationStatus.PENDING).buildWithDefaults());
    }

    /**
     * Tests if the method {@link EmployeeEvaluationFormService#findByAppraisalAndEmployee(Appraisal, Employee)}
     * executes successfully
     */
    @Test
    public void findByAppraisalAndEmployee_Successful() {
        // Execution
        List<EmployeeEvaluationForm> result = this.employeeEvaluationFormService.findByAppraisalAndEmployee(appraisal, employee)
                .collect(Collectors.toList());

        // Verification
        assertTrue(result.size() == 5);
        assertTrue(result.contains(selfEmployeeEvaluationForm));
        assertTrue(result.contains(peerfEmployeeEvaluationForm));
        assertTrue(result.contains(leadEmployeeEvaluationForm));
        assertTrue(result.contains(otherEmployeeEvaluationForm));
        assertTrue(result.contains(mentorEmployeeEvaluationForm));
    }
}
