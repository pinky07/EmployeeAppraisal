package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.helper.builder.model.EmployeeBuilder;
import com.gft.employeeappraisal.helper.builder.model.EmployeeRelationshipBuilder;
import com.gft.employeeappraisal.helper.builder.model.JobFamilyBuilder;
import com.gft.employeeappraisal.helper.builder.model.JobLevelBuilder;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.service.impl.EmployeeRelationshipServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Service layer test for {@link EmployeeRelationshipService}
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@RunWith(SpringRunner.class)
public class EmployeeRelationshipServiceTest extends BaseServiceTest {

    // Class under test
    private EmployeeRelationshipService employeeRelationshipService;

    // Test Fixtures
    private Employee employeeA;
    private Employee employeeB;
    private Employee employeeC;
    private Employee mentee;
    private Employee mentor;
    private Relationship mentorRelationship;
    private Relationship peerRelationship;
    private Relationship leadRelationship;
    private EmployeeRelationship mentorEmployeeRelationship;
    private EmployeeRelationship peerEmployeeRelationship;

    /**
     * Set up. Objects that need to be reinitialized.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Initialize the class under test
        this.employeeRelationshipService = new EmployeeRelationshipServiceImpl(
                this.employeeRelationshipRepository,
                this.relationshipService);

        // Retrieve the User Application Role
        ApplicationRole userApplicationRole = this.applicationRoleRepository
                .findByNameIgnoreCase(ApplicationRoleName.USER.name());

        // Retrieve the Mentor Relationship
        this.mentorRelationship = this.relationshipRepository
                .findByName(RelationshipName.MENTOR.name()).get();
        when(this.relationshipService.findByName(RelationshipName.MENTOR))
                .thenReturn(this.mentorRelationship);

        // Retrieve the Peer Relationship
        this.peerRelationship = this.relationshipRepository
                .findByName(RelationshipName.PEER.name()).get();
        when(this.relationshipService.findByName(RelationshipName.PEER))
                .thenReturn(this.peerRelationship);

        // Retrieve the Lead Relationship
        this.leadRelationship = this.relationshipRepository
                .findByName(RelationshipName.LEAD.name()).get();
        when(this.relationshipService.findByName(RelationshipName.LEAD))
                .thenReturn(this.leadRelationship);

        // Create a Job Family
        JobFamily jobFamily = this.jobFamilyRepository.saveAndFlush(new JobFamilyBuilder()
                .buildWithDefaults());

        // Create a Job Level
        JobLevel jobLevel = this.jobLevelRepository.saveAndFlush(new JobLevelBuilder()
                .jobFamily(jobFamily)
                .buildWithDefaults());

        // Test Employees
        this.employeeA = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("EmployeeA")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        this.employeeB = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("EmployeeB")
                .jobLevel(jobLevel)
                .applicationRole(userApplicationRole)
                .buildWithDefaults());
        this.employeeC = this.employeeRepository.saveAndFlush(new EmployeeBuilder()
                .firstName("EmployeeC")
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

        // Create an EmployeeRelationship: Mentor --> Mentee (MENTOR)
        this.mentorEmployeeRelationship = this.employeeRelationshipRepository
                .saveAndFlush(new EmployeeRelationshipBuilder()
                        .sourceEmployee(mentor)
                        .targetEmployee(mentee)
                        .relationship(mentorRelationship)
                        .buildWithDefaults());

        // Create an EmployeeRelationship: EmployeeA --> EmployeeB (PEER)
        this.peerEmployeeRelationship = this.employeeRelationshipRepository
                .saveAndFlush(new EmployeeRelationshipBuilder()
                        .sourceEmployee(employeeA)
                        .targetEmployee(employeeB)
                        .relationship(peerRelationship)
                        .buildWithDefaults());
    }

    /**
     * Tests {@link EmployeeRelationshipService#changeMentor(Employee, Employee)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void changeMentor_Successful() throws Exception {
        // Set up
        assertFalse(employeeRelationshipService.isCurrentMentor(employeeB, employeeA));

        // Execution
        long beforeCount = employeeRelationshipRepository.count();
        employeeRelationshipService.changeMentor(employeeB, employeeA);
        long afterCount = employeeRelationshipRepository.count();

        // Verification
        assertTrue(beforeCount + 1 == afterCount);
        assertTrue(employeeRelationshipService.isCurrentMentor(employeeB, employeeA));
    }

    /**
     * Tests {@link EmployeeRelationshipService#isCurrentMentor(Employee, Employee)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void isCurrentMentor_Successful() throws Exception {
        // Set up
        assertFalse(employeeRelationshipService.isCurrentMentor(employeeB, employeeA));
        assertFalse(employeeRelationshipService.isCurrentMentor(employeeA, employeeB));
        employeeRelationshipService.changeMentor(employeeB, employeeA);

        // Execution & Verification
        assertTrue(employeeRelationshipService.isCurrentMentor(employeeB, employeeA));
        assertFalse(employeeRelationshipService.isCurrentMentor(employeeA, employeeB));
    }

    /**
     * Tests {@link EmployeeRelationshipService#isCurrentPeer(Employee, Employee)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void isCurrentPeer_Successful() throws Exception {
        // Set up
        assertFalse(employeeRelationshipService.hasPeers(employeeC));
        assertFalse(employeeRelationshipService.isCurrentPeer(employeeC, employeeB));
        employeeRelationshipService.addPeer(employeeC, employeeB);

        // Execution & Verification
        assertTrue(employeeRelationshipService.hasPeers(employeeC));
        assertTrue(employeeRelationshipService.isCurrentPeer(employeeC, employeeB));
    }

    /**
     * Tests {@link EmployeeRelationshipService#isCurrentRelationship(Employee, Employee, Relationship)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void isCurrentRelationship_Successful() throws Exception {
        // Set up
        assertFalse(this.employeeRelationshipService.isCurrentRelationship(employeeC, employeeB, mentorRelationship));
        this.employeeRelationshipService.changeMentor(employeeC, employeeB);

        // Execution & Verification
        assertTrue(this.employeeRelationshipService.isCurrentRelationship(employeeC, employeeB, mentorRelationship));
    }

    /**
     * Tests {@link EmployeeRelationshipService#hasMentor(Employee)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void hasMentor_Successful() throws Exception {
        // Execution & Verification
        assertTrue(this.employeeRelationshipService.hasMentor(mentee));
    }

    /**
     * Tests {@link EmployeeRelationshipService#hasMentees(Employee)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void hasMentees_Successful() throws Exception {
        // Execution & Verification
        assertTrue(employeeRelationshipService.hasMentees(mentor));
    }

    /**
     * Tests {@link EmployeeRelationshipService#hasPeers(Employee)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void hasPeers_Successful() throws Exception {
        // Execution & Verification
        assertTrue(employeeRelationshipService.hasPeers(employeeA));
    }

    /**
     * Tests {@link EmployeeRelationshipService#hasRelationshipAsSourceEmployee(Employee, Relationship)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void hasRelationshipAsSourceEmployee_Successful() throws Exception {
        // Execution & Verification
        assertTrue(employeeRelationshipService.hasRelationshipAsSourceEmployee(mentor, mentorRelationship));
    }

    /**
     * Tests {@link EmployeeRelationshipService#hasRelationshipAsTargetEmployee(Employee, Relationship)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void hasRelationshipAsTargetEmployee_Successful() throws Exception {
        // Execution & Verification
        assertTrue(employeeRelationshipService.hasRelationshipAsTargetEmployee(mentee, mentorRelationship));
    }

    /**
     * Tests {@link EmployeeRelationshipService#addPeer(Employee, Employee)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void addPeer_Successful() throws Exception {
        // Set up
        assertFalse(employeeRelationshipService.hasPeers(employeeB));

        // Execution
        employeeRelationshipService.addPeer(employeeB, employeeA);

        // Verification
        assertTrue(employeeRelationshipService.hasPeers(employeeB));
    }

    /**
     * Tests {@link EmployeeRelationshipService#addPeers(Employee, List)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void addPeers_Successful() throws Exception {
        // Set up
        assertFalse(employeeRelationshipService.hasPeers(employeeC));

        // Execution
        employeeRelationshipService.addPeers(employeeC, Arrays.asList(employeeA, employeeB));

        // Verification
        assertTrue(employeeRelationshipService.hasPeers(employeeC));
        assertTrue(employeeRelationshipService.isCurrentRelationship(employeeC, employeeA, peerRelationship));
        assertTrue(employeeRelationshipService.isCurrentRelationship(employeeC, employeeB, peerRelationship));
    }

    /**
     * Tests {@link EmployeeRelationshipService#startEmployeeRelationship(Employee, Employee, Relationship)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void startEmployeeRelationship_Successful() throws Exception {
        // Set up
        assertFalse(employeeRelationshipService.hasPeers(employeeC));

        // Execution
        employeeRelationshipService.startEmployeeRelationship(employeeC, employeeA, peerRelationship);

        // Verification
        assertTrue(employeeRelationshipService.hasPeers(employeeC));
    }

    /**
     * Tests {@link EmployeeRelationshipService#endEmployeeRelationship(EmployeeRelationship)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void endEmployeeRelationship_Successful() throws Exception {
        // Set up
        assertFalse(employeeRelationshipService.hasMentees(employeeC));
        Optional<EmployeeRelationship> employeeRelationshipOptional = employeeRelationshipService.startEmployeeRelationship(employeeC, employeeA, mentorRelationship);
        assertTrue(employeeRelationshipOptional.isPresent());
        assertNull(employeeRelationshipOptional.get().getEndDate());

        // Execution
        employeeRelationshipOptional = employeeRelationshipService.endEmployeeRelationship(employeeRelationshipOptional.get());

        // Verification
        assertTrue(employeeRelationshipOptional.isPresent());
        assertNotNull(employeeRelationshipOptional.get().getEndDate());
    }

    @Test
    public void findById() throws Exception {
        Optional<EmployeeRelationship> retrieved = employeeRelationshipService
                .findById(mentorEmployeeRelationship.getId());

        assertTrue(retrieved.isPresent());
        assertEquals(mentorEmployeeRelationship, retrieved.get());
    }

    @Test
    public void findById_notFound() throws Exception {
        Optional<EmployeeRelationship> retrieved = employeeRelationshipService
                .findById(-100);

        assertFalse(retrieved.isPresent());
    }

    @Test
    public void getById() throws Exception {
        EmployeeRelationship retrieved = employeeRelationshipService
                .getById(mentorEmployeeRelationship.getId());

        assertNotNull(retrieved);
        assertEquals(mentorEmployeeRelationship, retrieved);
    }

    @Test(expected = NotFoundException.class)
    public void getById_notFound() throws Exception {
        this.employeeRelationshipService.getById(-100);
    }

    @Test
    public void findBySourceEmployeeAndRelationships() throws Exception {
        // Set up
        when(this.relationshipService.findRelationshipsByNames(
                eq(RelationshipName.PEER)))
                .thenReturn(Stream.of(
                        peerRelationship));

        List<EmployeeRelationship> relationships = employeeRelationshipService
                .findBySourceEmployeeAndRelationships(employeeA, RelationshipName.PEER).collect(Collectors.toList());

        assertNotNull(relationships);
        assertTrue(relationships.size() == 1);
        assertEquals(peerEmployeeRelationship, relationships.get(0));
    }

    @Test
    public void findBySourceEmployeeAndRelationships_noRelationships() throws Exception {
        // Set up
        when(this.relationshipService.findRelationshipsByNames(
                eq(RelationshipName.PEER)))
                .thenReturn(Stream.of(
                        peerRelationship));

        List<EmployeeRelationship> relationships = employeeRelationshipService
                .findBySourceEmployeeAndRelationships(employeeC, RelationshipName.PEER).collect(Collectors.toList());

        assertNotNull(relationships);
        assertTrue(relationships.isEmpty());
    }

    /**
     * Tests {@link EmployeeRelationshipService#findCurrentBySourceEmployeeAndRelationship(Employee, Relationship)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findCurrentBySourceEmployeeAndRelationship_Successful() throws Exception {
        // Set up
        assertFalse(employeeRelationshipService
                .findCurrentBySourceEmployeeAndRelationship(employeeA, leadRelationship)
                .findFirst()
                .isPresent());
        employeeRelationshipService.startEmployeeRelationship(employeeA, employeeB, leadRelationship);

        // Execution & Verification
        assertTrue(employeeRelationshipService
                .findCurrentBySourceEmployeeAndRelationship(employeeA, leadRelationship)
                .findFirst()
                .isPresent());
    }

    /**
     * Tests {@link EmployeeRelationshipService#findCurrentBySourceEmployeeAndRelationships(Employee, RelationshipName...)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findCurrentBySourceEmployeeAndRelationships_Successful() throws Exception {
        // Set up
        when(this.relationshipService.findRelationshipsByNames(
                eq(RelationshipName.PEER),
                eq(RelationshipName.LEAD)))
                .thenReturn(Stream.of(
                        peerRelationship,
                        leadRelationship));
        assertFalse(employeeRelationshipService
                .findCurrentBySourceEmployeeAndRelationship(employeeC, peerRelationship)
                .findFirst()
                .isPresent());
        assertFalse(employeeRelationshipService
                .findCurrentBySourceEmployeeAndRelationship(employeeC, leadRelationship)
                .findFirst()
                .isPresent());
        employeeRelationshipService.startEmployeeRelationship(employeeC, employeeA, peerRelationship);
        employeeRelationshipService.startEmployeeRelationship(employeeC, employeeB, leadRelationship);

        // Execution
        List<EmployeeRelationship> result = employeeRelationshipService
                .findCurrentBySourceEmployeeAndRelationships(employeeC, RelationshipName.PEER, RelationshipName.LEAD)
                .collect(Collectors.toList());

        // Verification
        assertTrue(result.size() == 2);

        assertEquals(employeeC, result.get(0).getSourceEmployee());
        assertEquals(employeeA, result.get(0).getTargetEmployee());
        assertEquals(peerRelationship, result.get(0).getRelationship());

        assertEquals(employeeC, result.get(1).getSourceEmployee());
        assertEquals(employeeB, result.get(1).getTargetEmployee());
        assertEquals(leadRelationship, result.get(1).getRelationship());
    }


    /**
     * Tests {@link EmployeeRelationshipService#findCurrentByTargetEmployeeAndRelationship(Employee, Relationship)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findCurrentByTargetEmployeeAndRelationship_Successful() throws Exception {
        // Set up
        assertFalse(employeeRelationshipService
                .findCurrentByTargetEmployeeAndRelationship(employeeB, leadRelationship)
                .findFirst()
                .isPresent());
        employeeRelationshipService.startEmployeeRelationship(employeeC, employeeB, leadRelationship);

        // Execution & Verification
        assertTrue(employeeRelationshipService
                .findCurrentByTargetEmployeeAndRelationship(employeeB, leadRelationship)
                .findFirst()
                .isPresent());
    }

    /**
     * Tests {@link EmployeeRelationshipService#findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(Employee, Employee, Relationship)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship_Successful() throws Exception {
        // Set up
        assertFalse(employeeRelationshipService
                .findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(employeeA, employeeB, leadRelationship)
                .findFirst()
                .isPresent());
        employeeRelationshipService.startEmployeeRelationship(employeeA, employeeB, leadRelationship);

        // Execution & Verification
        assertTrue(employeeRelationshipService
                .findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(employeeA, employeeB, leadRelationship)
                .findFirst()
                .isPresent());
    }

    /**
     * Tests {@link EmployeeRelationshipService#saveAndFlush(EmployeeRelationship)}
     *
     * @throws Exception If the test fails
     */
    @Test
    public void saveAndFlush_Successful() throws Exception {
        // Set up
        assertFalse(this.employeeRelationshipService.isCurrentRelationship(employeeA, employeeB, leadRelationship));
        EmployeeRelationship employeeRelationship = new EmployeeRelationshipBuilder()
                .sourceEmployee(employeeA)
                .targetEmployee(employeeB)
                .relationship(leadRelationship)
                .buildWithDefaults();

        // Execution
        long beforeCount = employeeRelationshipRepository.count();
        employeeRelationshipService.saveAndFlush(employeeRelationship);
        long afterCount = employeeRelationshipRepository.count();

        // Verification
        assertTrue(beforeCount + 1 == afterCount);
        assertTrue(this.employeeRelationshipService.isCurrentRelationship(employeeA, employeeB, leadRelationship));
    }
}
