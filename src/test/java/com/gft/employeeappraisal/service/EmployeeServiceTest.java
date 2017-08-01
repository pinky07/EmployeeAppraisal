package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.ServiceSpringBootUnitTest;
import com.gft.employeeappraisal.builder.model.ApplicationRoleBuilder;
import com.gft.employeeappraisal.builder.model.EmployeeBuilder;
import com.gft.employeeappraisal.builder.model.JobFamilyBuilder;
import com.gft.employeeappraisal.builder.model.JobLevelBuilder;
import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.exception.EmployeeNotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Service layer test for {@link EmployeeService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class EmployeeServiceTest extends ServiceSpringBootUnitTest {

	@Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
	private EmployeeRelationshipService employeeRelationshipService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobFamilyService jobFamilyService;

    @Autowired
    private JobLevelService jobLevelService;

    @Rule
	public final ExpectedException exception = ExpectedException.none();

	private Employee employee;
    private ApplicationRole applicationRole;
    private JobLevel jobLevel;
    private JobFamily jobFamily;

    @Before
    public void generateData() {
        applicationRole = applicationRoleService.save(mockApplicationRole().get());
        jobFamily = jobFamilyService.save(mockJobFamily());
        jobLevel = jobLevelService.save(mockJobLevel().get());
        employee = employeeService.save(mockEmployee(0, "----")).get();
    }

    @Test
    public void checkAccess() throws Exception {
    	// no exception is thrown in this case and test passes
		employeeService.checkAccess(employee.getId(), employee.getId());
    }

	@Test
	public void checkAccess_mentor() throws Exception {
		Employee mentor = employeeService.save(mockAdmin(-2)).get();
		employeeRelationshipService.changeMentor(mentor, employee);
		// no exception is thrown in this case and test passes
		employeeService.checkAccess(mentor.getId(), employee.getId());
	}

	@Test
	public void checkAccess_notFound() throws Exception {
		// non-existing employeeappraisal
		exception.expect(EmployeeNotFoundException.class);
		employeeService.checkAccess(-100, employee.getId());
		employeeService.checkAccess(employee.getId(), -100);
	}

	@Test
	public void checkAccess_forbidden() throws Exception {
		Employee mentor = employeeService.save(mockAdmin(-2)).get();
		exception.expect(AccessDeniedException.class);
		employeeService.checkAccess(mentor.getId(), employee.getId());
	}

    @Test
    public void findById() throws Exception {
        assertEquals(employee, employeeService.findById(employee.getId()).get());
    }

    @Test
    public void findById_notExists() throws Exception {
        assertEquals(Optional.empty(), employeeService.findById(-100));
    }

    @Test
    public void findByEmail() throws Exception {
        assertEquals(employee, employeeService.findByEmail(employee.getEmail()).get());
    }

    @Test
    public void findByEmail_notExists() throws Exception {
        assertEquals(Optional.empty(), employeeService.findByEmail("doesnotexist@gft.com"));
    }

    @Test
    public void findCurrentMentorById() throws Exception {
    	Employee mentor = employeeService.save(mockAdmin(-2)).get();
    	employeeRelationshipService.changeMentor(mentor, employee);

    	assertEquals(mentor, employeeService.findCurrentMentorById(employee.getId()).get());
	}

	@Test
	public void findCurrentMenteesById() throws Exception {
		Employee mentor = employeeService.save(mockAdmin(-2)).get();
		employeeRelationshipService.changeMentor(mentor, employee);

		assertEquals(employee, employeeService.findCurrentMenteesById(mentor.getId()).findFirst().get());
	}

	@Test
	public void findCurrentPeersById() throws Exception {
		Employee anotherEmployee = employeeService.save(mockEmployee(-3, "....")).get();
    	employeeRelationshipService.addPeer(employee, anotherEmployee);

		List<Employee> currentPeersById = employeeService.findCurrentPeersById(employee.getId()).collect(Collectors.toList());
		assertEquals(anotherEmployee, currentPeersById.get(0));
	}

	@Test
	public void findCurrentRelationshipsById() throws Exception {
		Employee mentor = employeeService.save(mockAdmin(-2)).get();
		employeeRelationshipService.changeMentor(mentor, employee);

		Optional<EmployeeRelationship> employeeRelationshipOpt =
				employeeService.findCurrentRelationshipsById(mentor.getId(), RelationshipName.MENTOR).findFirst();

		assertTrue(employeeRelationshipOpt.isPresent());
		EmployeeRelationship employeeRelationship = employeeRelationshipOpt.get();
		assertEquals(mentor, employeeRelationship.getSourceEmployee());
		assertEquals(employee, employeeRelationship.getTargetEmployee());
	}

	@Test
	public void findCurrentRelationshipsBySourceEmployee() throws Exception {
		Employee mentor = employeeService.save(mockAdmin(-2)).get();
		employeeRelationshipService.changeMentor(mentor, employee);

		Optional<EmployeeRelationship> employeeRelationshipOpt =
				employeeService.findCurrentRelationshipsBySourceEmployee(mentor, RelationshipName.MENTOR).findFirst();

		assertTrue(employeeRelationshipOpt.isPresent());
		EmployeeRelationship employeeRelationship = employeeRelationshipOpt.get();
		assertEquals(mentor, employeeRelationship.getSourceEmployee());
		assertEquals(employee, employeeRelationship.getTargetEmployee());
	}

    @Test
    public void isAdmin() throws Exception {
        assertFalse(employeeService.isAdmin(employee));
        assertTrue(employeeService.isAdmin(mockAdmin(-2)));
    }

    @Test
    public void save() throws Exception {
        Employee newEmployee = new EmployeeBuilder()
                .firstName("Newb")
                .lastName("Newbie")
                .gftIdentifier("NOOB")
                .email("noob@gft.com")
                .applicationRole(applicationRole)
                .jobLevel(jobLevel)
                .build();

        assertEquals(Optional.empty(), employeeService.findByEmail(newEmployee.getEmail()));

        long beforeCount = employeeRepository.count();
        newEmployee = employeeService.save(newEmployee).get();
        long afterCount = employeeRepository.count();

        assertTrue(beforeCount + 1 == afterCount);
        assertEquals(newEmployee, employeeService.findByEmail(newEmployee.getEmail()).get());
    }

    @Test
    public void save_invalid() throws Exception {
        Employee newEmployee = new EmployeeBuilder()
                .firstName("Newb")
                .lastName("Newbie")
                .gftIdentifier("NOOB")
                .email("noob@gft.com")
                .applicationRole(new ApplicationRoleBuilder()
                        .name("invalid").description("invalid").build())
                .jobLevel(jobLevel)
                .build();

        assertEquals(Optional.empty(), employeeService.findByEmail(newEmployee.getEmail()));

        long beforeCount = employeeRepository.count();
        Optional<Employee> result = employeeService.save(newEmployee);
        long afterCount = employeeRepository.count();

        assertTrue(beforeCount == afterCount);
        assertEquals(Optional.empty(), result);
        assertEquals(Optional.empty(), employeeService.findByEmail(newEmployee.getEmail()));
    }

    @SuppressWarnings("all")
    private Employee mockEmployee(int offsetId, String identifier) {
        return new EmployeeBuilder()
				.id(offsetId)
                .firstName("Manuel")
                .lastName("Yepez")
                .gftIdentifier(identifier)
                .email("nolo.yepez@gft.com")
                .applicationRole(applicationRole)
                .jobLevel(jobLevel)
                .build();
    }

    @SuppressWarnings("all")
    private Employee mockAdmin(int offsetId) {
        return new EmployeeBuilder()
				.id(offsetId)
                .firstName("Admin")
                .lastName("Admin")
                .gftIdentifier("0000")
                .email("admin@gft.com")
                .applicationRole(applicationRoleService.findById(ApplicationRoleNames.ADMIN.getId()).get())
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
