package com.gft.employeeappraisal.model;

import com.gft.employeeappraisal.repository.ApplicationRoleRepository;
import com.gft.employeeappraisal.repository.EmployeeRepository;
import com.gft.employeeappraisal.repository.JobFamilyRepository;
import com.gft.employeeappraisal.repository.JobLevelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class ModelTest {

    // @Autowired
    // private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobFamilyRepository jobFamilyRepository;

    @Autowired
    private JobLevelRepository jobLevelRepository;

    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    @Test
    @Rollback
    public void testDb() throws Exception {

        JobFamily jobFamily = createJobFamily();
		assertEquals("Architecture", jobFamily.getName());
		assertEquals("Roles in this family create architecture solutions, " +
				"defining the technology and solution vision, conceptualising and experimenting with alternative architectural approaches.", jobFamily.getDescription());

		JobLevel jobLevel = createLevel(jobFamily);
		assertEquals(jobLevel.getJobFamily(), jobFamily);
		assertEquals("L1", jobLevel.getName());
		assertEquals("At senior levels, activities in this area include understanding client requirements, " +
				"concerns and goals, creating technology roadmaps and scanning for opportunities to differentiate, and " +
				"making assertions about technology directions and determining their consequences for the technical " +
				"strategy and hence architectural approach.", jobLevel.getDescription());
		assertEquals("Entry", jobLevel.getExpertise());

		ApplicationRole applicationRole = createRole();
		assertEquals("Admin", applicationRole.getName());
		assertEquals("Responsible for effective provisioning, installation/configuration, operation, and maintenance of systems.",
				applicationRole.getDescription());

		Employee employee1 = createEmployee(1, jobLevel, applicationRole);
		assertEquals("rfjr", employee1.getEmail());
		assertEquals(jobLevel, employee1.getJobLevel());
		assertEquals(applicationRole, employee1.getApplicationRole());

		Employee employee2 = createEmployee(2, jobLevel, applicationRole);
		assertEquals("mtfr", employee2.getEmail());
		assertEquals(jobLevel, employee2.getJobLevel());
		assertEquals(applicationRole, employee2.getApplicationRole());
    }


    public JobFamily createJobFamily() {
		JobFamily jfam = new JobFamily();
		jfam.setName("Architecture");
		jfam.setDescription(
				"Roles in this family create architecture solutions, defining the technology and solution vision, conceptualising and experimenting with alternative architectural approaches.");
		// jfam = this.entityManager.persistAndFlush(jfam);
		jfam = jobFamilyRepository.save(jfam);
		return jfam;
	}

	public JobLevel createLevel(JobFamily jFam) {
		JobLevel jobLevel = new JobLevel();
		jobLevel.setName("L1");
		jobLevel.setJobFamily(jFam);
		jobLevel.setDescription(
				"At senior levels, activities in this area include understanding client requirements, concerns and goals, creating technology roadmaps and scanning for opportunities to differentiate, and making assertions about technology directions and determining their consequences for the technical strategy and hence architectural approach.");
		// jobLevel = this.entityManager.persistAndFlush(jobLevel);
		jobLevel.setExpertise("Entry");
		jobLevel = jobLevelRepository.save(jobLevel);
		return jobLevel;
	}

	public ApplicationRole createRole() {
		ApplicationRole applicationRole = new ApplicationRole();
		applicationRole.setName("Admin");
		applicationRole.setDescription(
				"Responsible for effective provisioning, installation/configuration, operation, and maintenance of systems.");
		// applicationRole = this.entityManager.persistAndFlush(applicationRole);
		applicationRole = applicationRoleRepository.save(applicationRole);
		return applicationRole;
	}

	public Employee createEmployee(int nEmployee, JobLevel jobLevel, ApplicationRole applicationRole) {
		Employee employee = new Employee();
		if (nEmployee == 1) {
			employee.setEmail("rfjr");
			employee.setFirstName("R");
			employee.setLastName("R");
			employee.setGftIdentifier("RFJR");
			employee.setJobLevel(jobLevel);
			employee.setApplicationRole(applicationRole);
		} else {
			employee.setEmail("mtfr");
			employee.setFirstName("M");
			employee.setLastName("R");
			employee.setGftIdentifier("MTFR");
			employee.setJobLevel(jobLevel);
			employee.setApplicationRole(applicationRole);
		}
		// employeeappraisal = this.entityManager.persistAndFlush(employeeappraisal);
		employee = employeeRepository.save(employee);

		return employee;
	}
}
