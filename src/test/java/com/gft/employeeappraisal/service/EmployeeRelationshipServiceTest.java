package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.ServiceSpringBootUnitTest;
import com.gft.employeeappraisal.builder.model.*;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.EmployeeRelationshipRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Service layer test for {@link EmployeeRelationshipService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class EmployeeRelationshipServiceTest extends ServiceSpringBootUnitTest {

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRelationshipService employeeRelationshipService;

    @Autowired
    private EmployeeRelationshipRepository employeeRelationshipRepository;

    @Autowired
    private JobFamilyService jobFamilyService;

    @Autowired
    private JobLevelService jobLevelService;

    @Autowired
    private RelationshipService relationshipService;

    private Employee employee;
    private Employee mentor;
    private ApplicationRole applicationRole;
    private JobLevel jobLevel;
    private JobFamily jobFamily;

    @Before
    @SuppressWarnings("all")
    public void generateData() {
        applicationRole = applicationRoleService.save(mockApplicationRole().get());
        jobFamily = jobFamilyService.save(mockJobFamily());
        jobLevel = jobLevelService.save(mockJobLevel().get());
        employee = employeeService.save(mockEmployee()).get();
        mentor = employeeService.save(mockMentor()).get();
    }

    @Test
    public void changeMentor() throws Exception {
		assertFalse(employeeRelationshipService.isCurrentMentor(mentor, employee));
         long beforeCount = employeeRelationshipRepository.count();
         employeeRelationshipService.changeMentor(mentor, employee);
         long afterCount = employeeRelationshipRepository.count();
         assertTrue(beforeCount + 1 == afterCount);

		assertTrue(employeeRelationshipService.isCurrentMentor(mentor, employee));
    }

    @Test
    public void isCurrentMentor() throws Exception {
         employeeRelationshipService.changeMentor(mentor, employee);
         assertTrue(employeeRelationshipService.isCurrentMentor(mentor, employee));
         assertFalse(employeeRelationshipService.isCurrentMentor(employee, mentor));
    }

    @Test
	public void isCurrentPeer() throws Exception {
    	assertFalse(employeeRelationshipService.hasPeers(employee));
    	employeeRelationshipService.addPeer(employee, mentor);
    	assertTrue(employeeRelationshipService.hasPeers(employee));
    	assertTrue(employeeRelationshipService.isCurrentPeer(employee, mentor));
	}

	@Test
	public void isCurrentRelationship() throws Exception {
    	Relationship mentorRelationship = relationshipService.findByName(RelationshipName.MENTOR);

		assertFalse(employeeRelationshipService.isCurrentRelationship(mentor, employee, mentorRelationship));
		employeeRelationshipService.changeMentor(mentor, employee);
		assertTrue(employeeRelationshipService.isCurrentRelationship(mentor, employee, mentorRelationship));
	}

	@Test
	public void hasMentor() throws Exception {
    	assertFalse(employeeRelationshipService.hasMentor(employee));
    	employeeRelationshipService.changeMentor(mentor, employee);
		assertTrue(employeeRelationshipService.hasMentor(employee));
	}

	@Test
	public void hasMentees() throws Exception {
		assertFalse(employeeRelationshipService.hasMentees(mentor));
		employeeRelationshipService.changeMentor(mentor, employee);
		assertTrue(employeeRelationshipService.hasMentees(mentor));
	}

	@Test
	public void hasPeers() throws Exception {
		assertFalse(employeeRelationshipService.hasPeers(employee));
		employeeRelationshipService.addPeer(employee, mentor);
		assertTrue(employeeRelationshipService.hasPeers(employee));
	}

	@Test
	public void hasRelationshipAsSourceEmployee() throws Exception {
		Relationship mentorRelationship = relationshipService.findByName(RelationshipName.MENTOR);

		assertFalse(employeeRelationshipService.hasRelationshipAsSourceEmployee(mentor, mentorRelationship));
		employeeRelationshipService.changeMentor(mentor, employee);
		assertTrue(employeeRelationshipService.hasRelationshipAsSourceEmployee(mentor, mentorRelationship));
	}

	@Test
	public void hasRelationshipAsTargetEmployee() throws Exception {
		Relationship mentorRelationship = relationshipService.findByName(RelationshipName.MENTOR);

		assertFalse(employeeRelationshipService.hasRelationshipAsTargetEmployee(employee, mentorRelationship));
		employeeRelationshipService.changeMentor(mentor, employee);
		assertTrue(employeeRelationshipService.hasRelationshipAsTargetEmployee(employee, mentorRelationship));
	}

	@Test
	public void addPeer() throws Exception {
		assertFalse(employeeRelationshipService.hasPeers(employee));
		employeeRelationshipService.addPeer(employee, mentor);
		assertTrue(employeeRelationshipService.hasPeers(employee));
	}

	@Test
	public void addPeers() throws Exception {
		assertFalse(employeeRelationshipService.hasPeers(employee));
		employeeRelationshipService.addPeers(employee, Collections.singletonList(mentor));
		assertTrue(employeeRelationshipService.hasPeers(employee));
	}

	@Test
	public void startEmployeeRelationship() throws Exception {
		Relationship mentorRelationship = relationshipService.findByName(RelationshipName.MENTOR);

		assertFalse(employeeRelationshipService.hasMentees(mentor));
		employeeRelationshipService.startEmployeeRelationship(mentor, employee, mentorRelationship);
		assertTrue(employeeRelationshipService.hasMentees(mentor));
	}

	@Test
	public void endEmployeeRelationship() throws Exception {
		Relationship mentorRelationship = relationshipService.findByName(RelationshipName.MENTOR);

		assertFalse(employeeRelationshipService.hasMentees(mentor));
		employeeRelationshipService.startEmployeeRelationship(mentor, employee, mentorRelationship);
		assertTrue(employeeRelationshipService.hasMentees(mentor));

		EmployeeRelationship employeeRelationship = employeeRelationshipService
				.findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(mentor, employee, mentorRelationship)
				.findFirst().get();

		assertNull(employeeRelationship.getEndDate());
		employeeRelationshipService.endEmployeeRelationship(employeeRelationship);
		assertNotNull(employeeRelationship.getEndDate());
	}

	@Test
	public void findCurrentBySourceEmployeeAndRelationship() throws Exception {
		Relationship mentorRelationship = relationshipService.findByName(RelationshipName.MENTOR);

		assertFalse(employeeRelationshipService
				.findCurrentBySourceEmployeeAndRelationship(mentor, mentorRelationship)
				.findFirst().isPresent());
		employeeRelationshipService.startEmployeeRelationship(mentor, employee, mentorRelationship);

		assertTrue(employeeRelationshipService
				.findCurrentBySourceEmployeeAndRelationship(mentor, mentorRelationship)
				.findFirst().isPresent());
	}

	@Test
	public void findCurrentBySourceEmployeeAndRelationships() throws Exception {
		Relationship mentorRelationship = relationshipService.findByName(RelationshipName.MENTOR);

		assertFalse(employeeRelationshipService
				.findCurrentBySourceEmployeeAndRelationships(mentor, RelationshipName.MENTOR)
				.findFirst().isPresent());
		employeeRelationshipService.startEmployeeRelationship(mentor, employee, mentorRelationship);

		assertTrue(employeeRelationshipService
				.findCurrentBySourceEmployeeAndRelationships(mentor, RelationshipName.MENTOR)
				.findFirst().isPresent());
	}

	@Test
	public void findCurrentByTargetEmployeeAndRelationship() throws Exception {
		Relationship mentorRelationship = relationshipService.findByName(RelationshipName.MENTOR);

		assertFalse(employeeRelationshipService
				.findCurrentByTargetEmployeeAndRelationship(employee, mentorRelationship)
				.findFirst().isPresent());
		employeeRelationshipService.startEmployeeRelationship(mentor, employee, mentorRelationship);

		assertTrue(employeeRelationshipService
				.findCurrentByTargetEmployeeAndRelationship(employee, mentorRelationship)
				.findFirst().isPresent());
	}

	@Test
	public void findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship() throws Exception {
		Relationship mentorRelationship = relationshipService.findByName(RelationshipName.MENTOR);

		assertFalse(employeeRelationshipService
				.findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(mentor, employee, mentorRelationship)
				.findFirst().isPresent());
		employeeRelationshipService.startEmployeeRelationship(mentor, employee, mentorRelationship);

		assertTrue(employeeRelationshipService
				.findCurrentBySourceEmployeeAndTargetEmployeeAndRelationship(mentor, employee, mentorRelationship)
				.findFirst().isPresent());
	}

	@Test
    public void save() throws Exception {
         Employee newPeer = new EmployeeBuilder()
         .firstName("Peer")
         .lastName("Peerez")
         .gftIdentifier("1111")
         .email("peerpeerez@gft.com")
         .applicationRole(applicationRole)
         .jobLevel(jobLevel)
         .build();

         Employee newPeer2 = new EmployeeBuilder()
         .firstName("Peer2")
         .lastName("Peerez2")
         .gftIdentifier("2222")
         .email("peerpeerez2@gft.com")
         .applicationRole(applicationRole)
         .jobLevel(jobLevel)
         .build();

         newPeer = employeeService.save(newPeer).get();
         newPeer2 = employeeService.save(newPeer2).get();

         Relationship relationship = relationshipService.findById(RelationshipName.PEER.getId()).get();

         EmployeeRelationship employeeRelationship = new EmployeeRelationshipBuilder()
         .relationship(relationship)
         .sourceEmployee(newPeer)
         .targetEmployee(newPeer2)
         .startDate(LocalDateTime.now())
         .build();

         long beforeCount = employeeRelationshipRepository.count();
         employeeRelationshipService.save(employeeRelationship);
         long afterCount = employeeRelationshipRepository.count();

         assertTrue(beforeCount + 1 == afterCount);

         Stream<EmployeeRelationship> stream = employeeRelationshipService
         	.findCurrentBySourceEmployeeAndRelationship(newPeer, relationship);
         assertFalse(stream.equals(Stream.empty()));
    }

    @SuppressWarnings("all")
    private Employee mockEmployee() {
        return new EmployeeBuilder()
                .firstName("Manuel")
                .lastName("Yepez")
                .gftIdentifier("----")
                .email("manuel.yepez@gft.com")
                .applicationRole(applicationRole)
                .jobLevel(jobLevel)
                .build();
    }

    @SuppressWarnings("all")
    private Employee mockMentor() {
        return new EmployeeBuilder()
                .firstName("Admin")
                .lastName("Admin")
                .gftIdentifier("AAAA")
                .email("admin@gft.com")
                .applicationRole(applicationRole)
                .jobLevel(jobLevel)
                .build();
    }

    private Optional<ApplicationRole> mockApplicationRole() {
        return Optional.of(new ApplicationRoleBuilder()
                .name("ApplicationRole").description("AppDescription").build());
    }

    private Optional<JobLevel> mockJobLevel() {
        return Optional.of(new JobLevelBuilder()
                .name("LX").description("Description").expertise("Expertise")
                .jobFamily(jobFamily
                ).build());
    }

    private JobFamily mockJobFamily() {
        return new JobFamilyBuilder()
                .name("Job Family").description("Description").build();
    }
}
