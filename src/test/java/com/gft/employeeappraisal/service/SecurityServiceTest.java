package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.helper.builder.model.AppraisalBuilder;
import com.gft.employeeappraisal.helper.builder.model.EmployeeBuilder;
import com.gft.employeeappraisal.helper.builder.model.EmployeeEvaluationFormBuilder;
import com.gft.employeeappraisal.helper.builder.model.EmployeeRelationshipBuilder;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.service.impl.SecurityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

/**
 * Service layer test for {@link SecurityService}
 *
 * @author Rubén Jiménez
 */
@RunWith(SpringRunner.class)
public class SecurityServiceTest extends BaseServiceTest {

    // Required to initialize the class under test
    @Value("${com.gft.businessRules.maxMenteeReferences}")
    private Integer maxMenteeReferences;

    // Class under test
    private SecurityService securityService;

    @Before
    public void setUp() throws Exception {
        this.securityService = new SecurityServiceImpl(
                this.maxMenteeReferences,
                this.employeeRelationshipService,
                this.employeeService);
    }

    @Test
    public void canReadAppraisal() {
        // Set up
        Employee employee = new EmployeeBuilder().id(1).buildWithDefaults();
        Appraisal appraisal = new AppraisalBuilder().id(1).buildWithDefaults();

        // Execution
        this.securityService.canReadAppraisal(employee, employee, appraisal);
    }

    @Test(expected = AccessDeniedException.class)
    public void canReadAppraisal_accessDenied() {
        // Set up
        Employee employee = new EmployeeBuilder().id(1).buildWithDefaults();
        Employee anotherEmployee = new EmployeeBuilder().id(2).buildWithDefaults();
        Appraisal appraisal = new AppraisalBuilder().id(1).buildWithDefaults();

        // Execution
        this.securityService.canReadAppraisal(employee, anotherEmployee, appraisal);
    }

    /**
     * Tests {@link SecurityService#canReadEmployee(Employee, Employee)}
     *
     * @throws Exception
     */
    @Test
    public void canReadEmployee_Successful_Self() throws Exception {
        // Set up
        Employee employee = new EmployeeBuilder().id(1).buildWithDefaults();

        // Execution
        this.securityService.canReadEmployee(employee, employee);

        // Verification
        verify(this.employeeService, times(0)).findCurrentMentorById(anyInt());
    }

    /**
     * Tests {@link SecurityService#canReadEmployee(Employee, Employee)}
     *
     * @throws Exception
     */
    @Test
    public void canReadEmployee_Successful_Mentee() throws Exception {
        // Set up
        Employee reader = new EmployeeBuilder().id(1).buildWithDefaults();
        Employee requested = new EmployeeBuilder().id(2).buildWithDefaults();
        when(this.employeeService.findCurrentMentorById(requested.getId()))
                .thenReturn(Optional.of(reader));

        // Execution
        this.securityService.canReadEmployee(reader, requested);

        // Verification
        verify(this.employeeService, times(1)).findCurrentMentorById(requested.getId());
    }

    /**
     * Tests {@link SecurityService#canReadEmployee(Employee, Employee)}
     *
     * @throws Exception
     */
    @Test(expected = AccessDeniedException.class)
    public void canReadEmployee_RequestedNotMentee() throws Exception {
        // Set up
        Employee reader = new EmployeeBuilder().id(1).buildWithDefaults();
        Employee requested = new EmployeeBuilder().id(2).buildWithDefaults();
        when(this.employeeService.findCurrentMentorById(requested.getId()))
                .thenReturn(Optional.of(new EmployeeBuilder().id(3).buildWithDefaults()));

        // Execution
        this.securityService.canReadEmployee(reader, requested);
    }

    @Test
    public void canReadEmployeeEvaluationForm() {
        // Set up
        Employee employee = new EmployeeBuilder().id(1).buildWithDefaults();

        EmployeeEvaluationForm employeeEvaluationForm = new EmployeeEvaluationFormBuilder()
                .employee(employee)
                .buildWithDefaults();

        // Execution
        this.securityService.canReadEmployeeEvaluationForm(employee, employeeEvaluationForm);

        Employee filledByEmployee = new EmployeeBuilder().id(2).buildWithDefaults();
        employeeEvaluationForm.setFilledByEmployee(filledByEmployee);

        this.securityService.canReadEmployeeEvaluationForm(filledByEmployee, employeeEvaluationForm);

        Employee mentor = new EmployeeBuilder().id(3).buildWithDefaults();
        employeeEvaluationForm.setMentor(mentor);

        this.securityService.canReadEmployeeEvaluationForm(mentor, employeeEvaluationForm);
    }

    @Test(expected = AccessDeniedException.class)
    public void canReadEmployeeEvaluationForm_accessDenied() {
        // Set up
        Employee employee = new EmployeeBuilder().id(1).buildWithDefaults();
        Employee another = new EmployeeBuilder().id(2).buildWithDefaults();

        EmployeeEvaluationForm employeeEvaluationForm = new EmployeeEvaluationFormBuilder()
                .employee(employee)
                .buildWithDefaults();

        // Execution
        this.securityService.canReadEmployeeEvaluationForm(another, employeeEvaluationForm);
    }

    @Test
    public void canReadEvaluationFormTemplate() {
        // Set up
        Employee employee = new EmployeeBuilder().id(1).buildWithDefaults();

        // Execution
        this.securityService.canReadEvaluationFormTemplate(employee, employee);
    }

    @Test(expected = AccessDeniedException.class)
    public void canReadEvaluationFormTemplate_accessDenied() {
        // Set up
        Employee employee = new EmployeeBuilder().id(1).buildWithDefaults();
        Employee another = new EmployeeBuilder().id(2).buildWithDefaults();

        // Execution
        this.securityService.canReadEvaluationFormTemplate(another, employee);
    }

    /**
     * Tests {@link SecurityService#canWriteEmployeeRelationship(Employee, Employee, Employee)}
     *
     * @throws Exception
     */
    @Test
    public void canWriteEmployeeRelationship_Successful_Self() throws Exception {
        // Set up
        Employee writer = new EmployeeBuilder().id(1).buildWithDefaults();
        Employee target = new EmployeeBuilder().id(2).buildWithDefaults();

        // Execution
        this.securityService.canWriteEmployeeRelationship(writer, writer, target);

        // Verification
        verify(this.employeeService, times(0)).findCurrentMentorById(anyInt());
    }

    /**
     * Tests {@link SecurityService#canWriteEmployeeRelationship(Employee, Employee, Employee)}
     *
     * @throws Exception
     */
    @Test
    public void canWriteEmployeeRelationship_Successful_Mentee() throws Exception {
        // Set up
        Employee writer = new EmployeeBuilder().id(1).buildWithDefaults();
        Employee source = new EmployeeBuilder().id(2).buildWithDefaults();
        Employee target = new EmployeeBuilder().id(3).buildWithDefaults();
        when(this.employeeService.findCurrentMentorById(source.getId()))
                .thenReturn(Optional.of(writer));

        // Execution
        this.securityService.canWriteEmployeeRelationship(writer, source, target);

        // Verification
        verify(this.employeeService, times(1)).findCurrentMentorById(source.getId());
    }

    /**
     * Tests {@link SecurityService#canWriteEmployeeRelationship(Employee, Employee, Employee)}
     *
     * @throws Exception
     */
    @Test(expected = AccessDeniedException.class)
    public void canWriteEmployeeRelationship_RequestedNotMentee() throws Exception {
        // Set up
        Employee writer = new EmployeeBuilder().id(1).buildWithDefaults();
        Employee source = new EmployeeBuilder().id(2).buildWithDefaults();
        Employee target = new EmployeeBuilder().id(3).buildWithDefaults();
        when(this.employeeService.findCurrentMentorById(source.getId()))
                .thenReturn(Optional.of(new EmployeeBuilder().id(4).buildWithDefaults()));

        // Execution
        this.securityService.canWriteEmployeeRelationship(writer, source, target);
    }

    /**
     * Tests {@link SecurityService#checkRelationshipCount(Employee)}
     *
     * @throws Exception
     */
    @Test
    public void checkRelationshipCount_Successful() throws Exception {
        // Set up
        Employee employee = new EmployeeBuilder().id(1).buildWithDefaults();
        when(employeeRelationshipService.findCurrentBySourceEmployeeAndRelationships(employee,
                RelationshipName.LEAD,
                RelationshipName.PEER,
                RelationshipName.OTHER)).thenReturn(Stream.of(
                new EmployeeRelationshipBuilder().buildWithDefaults(),
                new EmployeeRelationshipBuilder().buildWithDefaults()));

        // Execution
        this.securityService.checkRelationshipCount(employee);

        // Verification
        verify(employeeRelationshipService, times(1)).findCurrentBySourceEmployeeAndRelationships(employee,
                RelationshipName.LEAD,
                RelationshipName.PEER,
                RelationshipName.OTHER);
    }


    /**
     * Tests {@link SecurityService#checkRelationshipCount(Employee)}
     *
     * @throws Exception
     */
    @Test(expected = AccessDeniedException.class)
    public void checkRelationshipCount_LimitExceeded() throws Exception {
        // Set up
        Employee employee = new EmployeeBuilder().id(1).buildWithDefaults();
        when(employeeRelationshipService.findCurrentBySourceEmployeeAndRelationships(employee,
                RelationshipName.LEAD,
                RelationshipName.PEER,
                RelationshipName.OTHER)).thenReturn(Stream.of(
                new EmployeeRelationshipBuilder().buildWithDefaults(),
                new EmployeeRelationshipBuilder().buildWithDefaults(),
                new EmployeeRelationshipBuilder().buildWithDefaults(),
                new EmployeeRelationshipBuilder().buildWithDefaults(),
                new EmployeeRelationshipBuilder().buildWithDefaults(),
                new EmployeeRelationshipBuilder().buildWithDefaults()));

        // Execution
        this.securityService.checkRelationshipCount(employee);

        // Verification
        verify(employeeRelationshipService, times(1)).findCurrentBySourceEmployeeAndRelationships(employee,
                RelationshipName.LEAD,
                RelationshipName.PEER,
                RelationshipName.OTHER);
    }

}
