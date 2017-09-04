package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.builder.model.*;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.*;
import com.gft.employeeappraisal.service.impl.AppraisalServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
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
@DataJpaTest
public class AppraisalServiceTest {

    @Autowired
    private AppraisalRepository appraisalRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    private EmployeeRelationshipRepository employeeRelationshipRepository;

    @Autowired
    private JobFamilyRepository jobFamilyRepository;

    @Autowired
    private JobLevelRepository jobLevelRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Mock
    private AppraisalXEvaluationFormXEmployeeRelationshipService appraisalXEvaluationFormXEmployeeRelationshipService;

    @Mock
    private EmployeeRelationshipService employeeRelationshipService;

    // Class under test
    private AppraisalService appraisalService;

    private Appraisal appraisal;
    private Employee employeeA;
    private EmployeeRelationship selfEmployeeRelationship;

    @Before
    public void setUp() throws Exception {
        this.appraisalService = new AppraisalServiceImpl(
                this.appraisalRepository,
                this.appraisalXEvaluationFormXEmployeeRelationshipService,
                this.employeeRelationshipService);

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

        // Retrieve the Mentor Relationship
        Relationship selfRelationship = this.relationshipRepository
                .findByName(RelationshipName.SELF.name()).get();

        // Create a SELF EmployeeRelationship
        this.selfEmployeeRelationship = this.employeeRelationshipRepository
                .saveAndFlush(new EmployeeRelationshipBuilder()
                        .sourceEmployee(employeeA)
                        .targetEmployee(employeeA)
                        .relationship(selfRelationship)
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
        when(employeeRelationshipService
                .findBySourceEmployeeAndRelationships(employeeA, RelationshipName.SELF))
                .thenReturn(Stream.of(selfEmployeeRelationship));
        when(appraisalXEvaluationFormXEmployeeRelationshipService
                .findByEmployeeRelationshipsAndEvaluationStatus(Collections.singletonList(selfEmployeeRelationship),
                        null)).thenReturn(Stream.of(mockAEFER()));

        List<Appraisal> retrieved = appraisalService
                .findEmployeeAppraisals(employeeA, null).collect(Collectors.toList());

        assertFalse(retrieved.isEmpty());
        assertEquals(appraisal, retrieved.get(0));
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

    private AppraisalXEvaluationFormXEmployeeRelationship mockAEFER() {
        return new AppraisalXEvaluationFormXEmployeeRelationshipBuilder()
                .appraisalXEvaluationForm(new AppraisalXEvaluationFormBuilder()
                        .appraisal(appraisal).buildWithDefaults()).buildWithDefaults();
    }
}