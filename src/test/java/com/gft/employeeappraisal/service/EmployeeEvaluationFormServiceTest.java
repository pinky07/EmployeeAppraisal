package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.helper.builder.model.*;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.service.impl.EmployeeEvaluationFormServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Service layer test for {@link EmployeeEvaluationFormService}
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class EmployeeEvaluationFormServiceTest extends BaseServiceTest {

    // Class under test
    private EmployeeEvaluationFormService employeeEvaluationFormService;

    // Test fixtures
    private Appraisal appraisal;
    private ApplicationRole userApplicationRole;
    private EvaluationFormTemplate evaluationFormTemplate;
    private JobLevel jobLevel;
    private Employee employee;
    private Employee employeePeer;
    private Employee employeeMentor;
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
        userApplicationRole = this.applicationRoleRepository
                .findByNameIgnoreCase(ApplicationRoleName.USER.name());

        // Test Job Family & Job Level
        JobFamily jobFamily = this.jobFamilyRepository.saveAndFlush(new JobFamilyBuilder()
                .buildWithDefaults());
        jobLevel = this.jobLevelRepository.saveAndFlush(new JobLevelBuilder()
                .jobFamily(jobFamily)
                .buildWithDefaults());

        // Test Appraisal
        this.appraisal = this.appraisalRepository.saveAndFlush(new AppraisalBuilder()
                .buildWithDefaults());

        // Test Evaluation Form
        this.evaluationFormTemplate = this.evaluationFormTemplateRepository.saveAndFlush(new EvaluationFormTemplateBuilder()
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
        Employee employeeLead = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Lead")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        Employee employeeOther = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Other")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        employeeMentor = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Mentor")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());

        // Test EmployeeEvaluationForm
        this.selfEmployeeEvaluationForm = this.employeeEvaluationFormRepository.saveAndFlush(new
                EmployeeEvaluationFormBuilder(this.appraisal, evaluationFormTemplate)
                .employee(employee)
                .filledByEmployee(employee)
                .mentor(employeeMentor)
                .buildWithDefaults());
        this.peerfEmployeeEvaluationForm = this.employeeEvaluationFormRepository.saveAndFlush(new
                EmployeeEvaluationFormBuilder(this.appraisal, evaluationFormTemplate)
                .employee(employee)
                .filledByEmployee(employeePeer)
                .mentor(employeeMentor)
                .buildWithDefaults());
        this.leadEmployeeEvaluationForm = this.employeeEvaluationFormRepository.saveAndFlush(new
                EmployeeEvaluationFormBuilder(this.appraisal, evaluationFormTemplate)
                .employee(employee)
                .filledByEmployee(employeeLead)
                .mentor(employeeMentor)
                .buildWithDefaults());
        this.otherEmployeeEvaluationForm = this.employeeEvaluationFormRepository.saveAndFlush(new
                EmployeeEvaluationFormBuilder(this.appraisal, evaluationFormTemplate)
                .employee(employee)
                .filledByEmployee(employeeOther)
                .mentor(employeeMentor)
                .buildWithDefaults());
        this.mentorEmployeeEvaluationForm = this.employeeEvaluationFormRepository.saveAndFlush(new
                EmployeeEvaluationFormBuilder(this.appraisal, evaluationFormTemplate)
                .employee(employee)
                .filledByEmployee(employeeMentor)
                .mentor(employeeMentor)
                .buildWithDefaults());
    }

    @Test
    public void findById() {
        Optional<EmployeeEvaluationForm> retrieved = employeeEvaluationFormService
                .findById(selfEmployeeEvaluationForm.getId());

        assertTrue(retrieved.isPresent());
        assertEquals(selfEmployeeEvaluationForm, retrieved.get());
    }

    @Test
    public void findById_invalid() {
        assertEquals(Optional.empty(), employeeEvaluationFormService.findById(-100));
    }

    @Test
    public void findByEmployee() {
        List<EmployeeEvaluationForm> retrieved = employeeEvaluationFormService
                .findByEmployee(employee).collect(Collectors.toList());

        assertNotNull(retrieved);
        assertTrue(retrieved.size() == 5);
        assertTrue(retrieved.contains(selfEmployeeEvaluationForm));
        assertTrue(retrieved.contains(peerfEmployeeEvaluationForm));
        assertTrue(retrieved.contains(leadEmployeeEvaluationForm));
        assertTrue(retrieved.contains(otherEmployeeEvaluationForm));
        assertTrue(retrieved.contains(mentorEmployeeEvaluationForm));
    }

    @Test
    public void findByEmployee_empty() {
        List<EmployeeEvaluationForm> retrieved = employeeEvaluationFormService
                .findByEmployee(employeePeer).collect(Collectors.toList());

        assertNotNull(retrieved);
        assertTrue(retrieved.isEmpty());
    }

    @Test
    public void findSelfByEmployee() {
        List<EmployeeEvaluationForm> retrieved = employeeEvaluationFormService
                .findSelfByEmployee(employee).collect(Collectors.toList());

        assertNotNull(retrieved);
        assertTrue(retrieved.size() == 1);
        assertTrue(retrieved.contains(selfEmployeeEvaluationForm));
    }

    @Test
    public void findSelfByEmployee_empty() {
        List<EmployeeEvaluationForm> retrieved = employeeEvaluationFormService
                .findSelfByEmployee(employeePeer).collect(Collectors.toList());

        assertNotNull(retrieved);
        assertTrue(retrieved.isEmpty());
    }

    @Test
    public void findSelfByEmployeeAndAppraisal() {
        Optional<EmployeeEvaluationForm> retrieved = employeeEvaluationFormService
                .findSelfByEmployeeAndAppraisal(employee, appraisal);

        assertTrue(retrieved.isPresent());
        assertEquals(selfEmployeeEvaluationForm, retrieved.get());
    }

    @Test
    public void findSelfByEmployeeAndAppraisal_empty() {
        Optional<EmployeeEvaluationForm> retrieved = employeeEvaluationFormService
                .findSelfByEmployeeAndAppraisal(employeePeer, appraisal);

        assertEquals(Optional.empty(), retrieved);
    }

    /**
     * Tests if the method {@link EmployeeEvaluationFormService#findByEmployeeAndAppraisal(Employee, Appraisal)}
     * executes successfully
     */
    @Test
    public void findByEmployeeAndAppraisal_Successful() {
        // Execution
        List<EmployeeEvaluationForm> result = this.employeeEvaluationFormService
                .findByEmployeeAndAppraisal(employee, appraisal)
                .collect(Collectors.toList());

        // Verification
        assertTrue(result.size() == 5);
        assertTrue(result.contains(selfEmployeeEvaluationForm));
        assertTrue(result.contains(peerfEmployeeEvaluationForm));
        assertTrue(result.contains(leadEmployeeEvaluationForm));
        assertTrue(result.contains(otherEmployeeEvaluationForm));
        assertTrue(result.contains(mentorEmployeeEvaluationForm));
    }

    @Test
    public void findByEmployeeAndAppraisal_empty() {
        // Execution
        List<EmployeeEvaluationForm> result = this.employeeEvaluationFormService
                .findByEmployeeAndAppraisal(employeePeer, appraisal)
                .collect(Collectors.toList());

        // Verification
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findByFilledByEmployeeAndAppraisal() {
        // Execution
        List<EmployeeEvaluationForm> result = this.employeeEvaluationFormService
                .findByFilledByEmployeeAndAppraisal(employeePeer, appraisal)
                .collect(Collectors.toList());

        // Verification
        assertTrue(result.size() == 1);
        assertTrue(result.contains(peerfEmployeeEvaluationForm));
    }

    @Test
    public void findByFilledByEmployeeAndAppraisal_empty() {
        Employee notFilled = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("NotFilled")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        // Execution
        List<EmployeeEvaluationForm> result = this.employeeEvaluationFormService
                .findByFilledByEmployeeAndAppraisal(notFilled, appraisal)
                .collect(Collectors.toList());

        // Verification
        assertTrue(result.isEmpty());
    }

    @Test
    public void findByMentorAndAppraisal() {
        // Execution
        List<EmployeeEvaluationForm> result = this.employeeEvaluationFormService
                .findByMentorAndAppraisal(employeeMentor, appraisal)
                .collect(Collectors.toList());

        // Verification
        assertTrue(result.size() == 5);
        assertTrue(result.contains(selfEmployeeEvaluationForm));
        assertTrue(result.contains(peerfEmployeeEvaluationForm));
        assertTrue(result.contains(leadEmployeeEvaluationForm));
        assertTrue(result.contains(otherEmployeeEvaluationForm));
        assertTrue(result.contains(mentorEmployeeEvaluationForm));
    }

    @Test
    public void findByMentorAndAppraisal_empty() {
        // Execution
        List<EmployeeEvaluationForm> result = this.employeeEvaluationFormService
                .findByMentorAndAppraisal(employee, appraisal)
                .collect(Collectors.toList());

        // Verification
        assertTrue(result.isEmpty());
    }

    @Test
    public void saveAndFlush() {
        Employee notFilled = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("NotFilled")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());

        EmployeeEvaluationForm newEmployeeEvaluationForm = new EmployeeEvaluationFormBuilder(appraisal, evaluationFormTemplate)
                .employee(employee)
                .filledByEmployee(notFilled)
                .mentor(employeeMentor)
                .buildWithDefaults();

        long beforeCount = this.employeeEvaluationFormRepository.count();
        Optional<EmployeeEvaluationForm> saved = this.employeeEvaluationFormService
                .saveAndFlush(newEmployeeEvaluationForm);
        long afterCount = this.employeeEvaluationFormRepository.count();

        assertTrue(beforeCount + 1 == afterCount);
        assertTrue(saved.isPresent());
        assertEquals(newEmployeeEvaluationForm, saved.get());
    }
}
