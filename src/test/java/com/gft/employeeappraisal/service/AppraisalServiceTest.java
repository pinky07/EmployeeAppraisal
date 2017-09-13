package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.helper.builder.model.*;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.service.impl.AppraisalServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Service layer test for {@link AppraisalService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class AppraisalServiceTest extends BaseServiceTest {

    // Test Fixtures
    private Appraisal appraisal;
    private Employee employeeA;

    @Before
    public void setUp() throws Exception {
        this.appraisalService = new AppraisalServiceImpl(
                this.appraisalRepository,
                this.employeeEvaluationFormService,
                this.appraisalXEvaluationFormTemplateService);

        // Create an Application Role
        ApplicationRole userApplicationRole = this.applicationRoleRepository
                .findByNameIgnoreCase(ApplicationRoleName.USER.name());

        // Create a Job Family
        JobFamily jobFamily = this.jobFamilyRepository.saveAndFlush(new JobFamilyBuilder()
                .buildWithDefaults());

        // Create a Job Level
        JobLevel jobLevel = this.jobLevelRepository.saveAndFlush(new JobLevelBuilder()
                .jobFamily(jobFamily)
                .buildWithDefaults());

        // Test employees
        this.employeeA = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("EmployeeA")
                .lastName("LastNameA")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());

        this.appraisal = this.appraisalRepository.saveAndFlush(new AppraisalBuilder().buildWithDefaults());
    }

    @Test
    public void findById() throws Exception {
        Optional<Appraisal> retrieved = appraisalService.findById(appraisal.getId());

        assertTrue(retrieved.isPresent());
        assertEquals(appraisal, retrieved.get());
    }

    @Test
    public void findById_invalid() throws Exception {
        assertEquals(Optional.empty(), appraisalService.findById(-100));
    }

    @Test
    public void findEmployeeAppraisals() throws Exception {
        when(employeeEvaluationFormService.findSelfByEmployee(employeeA))
                .thenReturn(Stream.of(testEmployeeEvaluationForm()));

        List<Appraisal> retrieved = appraisalService
                .findEmployeeAppraisals(employeeA).collect(Collectors.toList());

        assertFalse(retrieved.isEmpty());
        assertEquals(appraisal, retrieved.get(0));
    }

    @Test
    public void getById() throws Exception {
        Appraisal retrieved = appraisalService.getById(appraisal.getId());

        assertNotNull(retrieved);
        assertEquals(appraisal, retrieved);
    }

    @Test(expected = NotFoundException.class)
    public void getById_invalid() throws Exception {
        appraisalService.getById(-100);
    }

    @Test
    public void saveAndFlush() throws Exception {
        Appraisal appraisal = new AppraisalBuilder()
                .name("newAppraisal")
                .buildWithDefaults();

        long beforeCount = appraisalRepository.count();
        Optional<Appraisal> retrieved = appraisalService.saveAndFlush(appraisal);
        long afterCount = appraisalRepository.count();

        assertTrue(beforeCount + 1 == afterCount);
        assertTrue(retrieved.isPresent());
        assertEquals(appraisal, retrieved.get());
    }

    private EmployeeEvaluationForm testEmployeeEvaluationForm() {
        return new EmployeeEvaluationFormBuilder()
                .appraisalXEvaluationFormTemplate(new AppraisalXEvaluationFormTemplateBuilder()
                        .appraisal(appraisal).buildWithDefaults()).buildWithDefaults();
    }
}