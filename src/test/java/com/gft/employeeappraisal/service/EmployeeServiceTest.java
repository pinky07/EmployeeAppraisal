package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.builder.model.EmployeeBuilder;
import com.gft.employeeappraisal.builder.model.EmployeeRelationshipBuilder;
import com.gft.employeeappraisal.builder.model.JobFamilyBuilder;
import com.gft.employeeappraisal.builder.model.JobLevelBuilder;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.*;
import com.gft.employeeappraisal.service.impl.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Service layer test for {@link EmployeeService}
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeServiceTest {

    // Required to initialize the class under test

    @Autowired
    private EmployeeRepository employeeRepository;

    @Mock
    private ApplicationRoleService applicationRoleService;

    @Mock
    private EmployeeRelationshipService employeeRelationshipService;

    @Mock
    private JobLevelService jobLevelService;

    @Mock
    private RelationshipService relationshipService;

    // Class under test
    private EmployeeService employeeService;

    // Other repositories

    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    private JobFamilyRepository jobFamilyRepository;

    @Autowired
    private JobLevelRepository jobLevelRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private EmployeeRelationshipRepository employeeRelationshipRepository;

    // Test Fixtures
    private ApplicationRole userApplicationRole;
    private ApplicationRole adminApplicationRole;
    private JobFamily jobFamily;
    private JobLevel jobLevel;
    private Employee employeeA;
    private Employee employeeB;
    private Employee mentee;
    private Employee mentor;
    private Employee admin;
    private Relationship mentorRelationship;
    private Relationship peerRelationship;
    private EmployeeRelationship mentorEmployeeRelationship;
    private EmployeeRelationship peerEmployeeRelationship;

    /**
     * Set up. Objects that need to be reinitialized.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        // Initialize the service
        this.employeeService = new EmployeeServiceImpl(
                this.applicationRoleService,
                this.employeeRelationshipService,
                this.jobLevelService,
                this.relationshipService,
                this.employeeRepository);

        // Create an Application Role
        this.userApplicationRole = this.applicationRoleRepository
                .findByNameIgnoreCase(ApplicationRoleName.USER.name());

        // Create and Admin Application Role
        this.adminApplicationRole = this.applicationRoleRepository
                .findByNameIgnoreCase(ApplicationRoleName.ADMIN.name());

        // Create a Job Family
        this.jobFamily = this.jobFamilyRepository.saveAndFlush(new JobFamilyBuilder()
                .buildWithDefaults());

        // Create a Job Level
        this.jobLevel = this.jobLevelRepository.saveAndFlush(new JobLevelBuilder()
                .jobFamily(jobFamily)
                .buildWithDefaults());

        // Test employees
        this.employeeA = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("EmployeeA")
                .lastName("LastNameA")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        this.employeeB = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("EmployeeB")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        this.mentee = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Mentee")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        this.mentor = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Mentor")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        this.admin = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("Admin")
                .jobLevel(jobLevel)
                .applicationRole(adminApplicationRole)
                .buildWithDefaults());

        // Retrieve the Mentor Relationship
        this.mentorRelationship = this.relationshipRepository
                .findByName(RelationshipName.MENTOR.name()).get();

        // Retrieve the Peer Relationship
        this.peerRelationship = this.relationshipRepository
                .findByName(RelationshipName.PEER.name()).get();

        // Create an EmployeeRelationship: Mentor --> Mentee (MENTOR)
        this.mentorEmployeeRelationship = this.employeeRelationshipRepository.saveAndFlush(new EmployeeRelationshipBuilder()
                .sourceEmployee(mentor)
                .targetEmployee(mentee)
                .relationship(mentorRelationship)
                .buildWithDefaults());

        // Create an EmployeeRelationship: EmployeeA --> EmployeeB (PEER)
        this.peerEmployeeRelationship = this.employeeRelationshipRepository.saveAndFlush(new EmployeeRelationshipBuilder()
                .sourceEmployee(employeeA)
                .targetEmployee(employeeB)
                .relationship(peerRelationship)
                .buildWithDefaults());
    }

    @Test
    public void getById() throws Exception {
        Employee retrieved = employeeService.getById(employeeA.getId());

        assertNotNull(retrieved);
        assertEquals(retrieved, employeeA);
    }

    @Test(expected = NotFoundException.class)
    public void getById_NotFound() throws Exception {
        employeeService.getById(-100);
    }

    @Test
    public void getCurrentMentorById() throws Exception {
        when(this.relationshipService.findByName(any(RelationshipName.class)))
                .thenReturn(mentorRelationship);
        when(this.employeeRelationshipService.findCurrentByTargetEmployeeAndRelationship(mentee, mentorRelationship))
                .thenReturn(Stream.of(mentorEmployeeRelationship));

        Employee retrieved = employeeService.getCurrentMentorById(mentee.getId());

        assertNotNull(retrieved);
        assertEquals(mentor, retrieved);
    }

    @Test(expected = NotFoundException.class)
    public void getCurrentMentorById_noMentor() throws Exception {
        when(this.relationshipService.findByName(any(RelationshipName.class)))
                .thenReturn(mentorRelationship);
        when(this.employeeRelationshipService.findCurrentByTargetEmployeeAndRelationship(employeeA, mentorRelationship))
                .thenReturn(Stream.empty());

        employeeService.getCurrentMentorById(employeeA.getId());
    }

    @Test(expected = NotFoundException.class)
    public void getCurrentMentorById_invalidMentee() throws Exception {
        employeeService.getCurrentMentorById(-100);
    }

    @Test
    public void findCurrentRelationshipsById() throws Exception {
        when(employeeRelationshipService.findCurrentBySourceEmployeeAndRelationships(mentor,
                RelationshipName.MENTOR)).thenReturn(Stream.of(mentorEmployeeRelationship));

        List<EmployeeRelationship> relationships = employeeService
                .findCurrentRelationshipsById(mentor.getId(), RelationshipName.MENTOR).collect(Collectors.toList());

        assertNotNull(relationships);
        assertTrue(relationships.size() == 1);

        EmployeeRelationship relationship = relationships.get(0);
        assertEquals(relationship.getSourceEmployee(), mentor);
        assertEquals(relationship.getTargetEmployee(), mentee);
        assertEquals(relationship.getRelationship(), mentorRelationship);
        assertNotNull(relationship.getStartDate());
    }

    @Test
    public void findCurrentRelationshipsById_noRelationships() throws Exception {
        when(employeeRelationshipService.findCurrentBySourceEmployeeAndRelationships(any(Employee.class),
                any(RelationshipName.class))).thenReturn(Stream.empty());

        List<EmployeeRelationship> relationships = employeeService
                .findCurrentRelationshipsById(mentor.getId(), RelationshipName.MENTOR).collect(Collectors.toList());

        assertNotNull(relationships);
        assertTrue(relationships.isEmpty());
    }

    @Test(expected = NotFoundException.class)
    public void findCurrentRelationshipsById_invalidEmployee() throws Exception {
        employeeService
                .findCurrentRelationshipsById(-100, RelationshipName.MENTOR);
    }

    /**
     * Tests {@link EmployeeService#findById(Integer)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findById_Successful() throws Exception {
        // Execution
        Optional<Employee> employeeRetrieved = this.employeeService
                .findById(employeeA.getId());

        // Verification
        assertTrue(employeeRetrieved.isPresent());
        assertEquals(employeeA, employeeRetrieved.get());
    }

    /**
     * Tests {@link EmployeeService#findById(Integer)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findById_NotFound() throws Exception {
        // Execution
        Optional<Employee> employeeRetrieved = this.employeeService
                .findById(-100);

        // Verification
        assertFalse(employeeRetrieved.isPresent());
    }

    /**
     * Tests {@link EmployeeService#findByEmail(String)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findByEmail_Successful() throws Exception {
        // Execution
        Optional<Employee> employeeRetrieved = this.employeeService
                .findByEmail(employeeA.getEmail());

        // Verification
        assertTrue(employeeRetrieved.isPresent());
        assertEquals(employeeA, employeeRetrieved.get());
    }

    /**
     * Tests {@link EmployeeService#findByEmail(String)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findByEmail_NotFound() throws Exception {
        // Execution
        Optional<Employee> employeeRetrieved = this.employeeService
                .findByEmail("notfound@gft.com");

        // Verification
        assertFalse(employeeRetrieved.isPresent());
    }

    /**
     * Tests {@link EmployeeService#findCurrentMentorById(int)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findCurrentMentorById_Successful() throws Exception {
        // Set up
        when(this.relationshipService.findByName(any(RelationshipName.class)))
                .thenReturn(mentorRelationship);
        when(this.employeeRelationshipService.findCurrentByTargetEmployeeAndRelationship(mentee, mentorRelationship))
                .thenReturn(Stream.of(mentorEmployeeRelationship));

        // Execution
        Optional<Employee> mentorRetrieved = this.employeeService.findCurrentMentorById(mentee.getId());

        // Verification
        assertTrue(mentorRetrieved.isPresent());
        assertEquals(mentor, mentorRetrieved.get());
    }

    @Test(expected = NotFoundException.class)
    public void findCurrentMentorById_notFound() throws Exception {
        // Execution
        this.employeeService.findCurrentMentorById(-100);
    }

    /**
     * Tests {@link EmployeeService#findCurrentMenteesById(int)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findCurrentMenteesById_Successful() throws Exception {
        // Set up
        when(this.relationshipService.findByName(any(RelationshipName.class)))
                .thenReturn(mentorRelationship);
        when(this.employeeRelationshipService.findCurrentBySourceEmployeeAndRelationship(mentor, mentorRelationship))
                .thenReturn(Stream.of(mentorEmployeeRelationship));

        // Execution
        List<Employee> menteesRetrieved = this.employeeService.findCurrentMenteesById(mentor.getId())
                .collect(Collectors.toList());

        // Verification
        assertTrue(menteesRetrieved.size() == 1);
        assertEquals(mentee, menteesRetrieved.get(0));
    }

    @Test(expected = NotFoundException.class)
    public void findCurrentMenteesById_notFound() throws Exception {
        // Execution
        this.employeeService.findCurrentMenteesById(-100);
    }

    /**
     * Tests {@link EmployeeService#findCurrentPeersById(int)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findCurrentPeersById_Successful() throws Exception {
        // Set up
        when(this.relationshipService.findByName(any(RelationshipName.class)))
                .thenReturn(peerRelationship);
        when(this.employeeRelationshipService.findCurrentBySourceEmployeeAndRelationship(employeeA, peerRelationship))
                .thenReturn(Stream.of(peerEmployeeRelationship));

        // Execution
        List<Employee> peersRetrieved = this.employeeService.findCurrentPeersById(employeeA.getId())
                .collect(Collectors.toList());

        // Verification
        assertTrue(peersRetrieved.size() == 1);
        assertEquals(employeeB, peersRetrieved.get(0));
    }

    @Test(expected = NotFoundException.class)
    public void findCurrentPeersById_notFound() throws Exception {
        // Execution
        this.employeeService.findCurrentPeersById(-100);
    }

    @Test
    public void findPagedByFirstNameOrLastName() throws Exception {
        List<Employee> employees = employeeService
                .findPagedByFirstNameOrLastName(employeeA.getFirstName(), employeeA.getLastName(),
                        0, 10).collect(Collectors.toList());

        assertTrue(employees.size() == 1);
        assertEquals(employeeA, employees.get(0));
    }

    @Test
    public void findPagedByFirstNameOrLastName_noResults() throws Exception {
        List<Employee> employees = employeeService
                .findPagedByFirstNameOrLastName("someunexistingname ", "someunexistingname",
                        0, 10).collect(Collectors.toList());

        assertTrue(employees.isEmpty());
    }

    /**
     * Tests {@link EmployeeService#isAdmin(Employee)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void isAdmin() throws Exception {
        // Execution & Verification
        assertFalse(this.employeeService.isAdmin(employeeA));
        assertTrue(this.employeeService.isAdmin(admin));
    }

    /**
     * Tests {@link EmployeeService#saveAndFlush(Employee)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void saveAndFlush_Successful() throws Exception {
        // Set up
        Employee employee = new EmployeeBuilder()
                .applicationRole(userApplicationRole)
                .jobLevel(jobLevel)
                .buildWithDefaults();
        when(this.applicationRoleService.findById(this.userApplicationRole.getId()))
                .thenReturn(Optional.of(this.userApplicationRole));
        when(this.jobLevelService.findById(this.jobLevel.getId()))
                .thenReturn(Optional.of(this.jobLevel));

        // Execution
        long beforeCount = employeeRepository.count();
        Optional<Employee> employeeRetrieved = employeeService.saveAndFlush(employee);
        long afterCount = employeeRepository.count();

        // Verification
        assertTrue(beforeCount + 1 == afterCount);
        assertTrue(employeeRetrieved.isPresent());
    }

    /**
     * Tests {@link EmployeeService#saveAndFlush(Employee)}
     *
     * @throws Exception If the test fails
     */
    @Test(expected = ConstraintViolationException.class)
    public void saveAndFlush_Invalid() throws Exception {
        // Set up
        Employee employee = new EmployeeBuilder()
                .gftIdentifier("12345")
                .applicationRole(userApplicationRole)
                .jobLevel(jobLevel)
                .build();
        when(this.applicationRoleService.findById(this.userApplicationRole.getId()))
                .thenReturn(Optional.of(this.userApplicationRole));
        when(this.jobLevelService.findById(this.jobLevel.getId()))
                .thenReturn(Optional.of(this.jobLevel));

        // Execution
        employeeService.saveAndFlush(employee);
    }
}
