package com.gft.employee.service;

import com.gft.employee.ServiceSpringBootUnitTest;
import com.gft.employee.builder.model.JobFamilyBuilder;
import com.gft.employee.builder.model.JobLevelBuilder;
import com.gft.employee.model.JobFamily;
import com.gft.employee.model.JobLevel;
import com.gft.employee.repository.JobLevelRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Service layer test for {@link JobLevelService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class JobLevelServiceTest extends ServiceSpringBootUnitTest {

	@Autowired
	private JobFamilyService jobFamilyService;

	@Autowired
	private JobLevelRepository jobLevelRepository;

	@Autowired
	private JobLevelService jobLevelService;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private JobLevel jobLevel;
	private JobFamily jobFamily;

	@Before
	public void generateData() {
		jobFamily = jobFamilyService.save(mockJobFamily());
		jobLevel = jobLevelService.save(mockJobLevel().get());
	}

	@Test
	public void findById() throws Exception {
		assertEquals(jobLevel, jobLevelService.findById(jobLevel.getId()).get());
	}

	@Test
	public void findById_notExists() throws Exception {
		assertEquals(Optional.empty(), jobLevelService.findById(-100));
	}

	@Test
	public void save() throws Exception {
		JobLevel newJobLevel = new JobLevelBuilder()
				.name("LY").description("Desc").expertise("Exp")
				.jobFamily(jobFamily
				).build();

		assertEquals(Optional.empty(), jobLevelService.findById(newJobLevel.getId()));

		long beforeCount = jobLevelRepository.count();
		newJobLevel = jobLevelService.save(newJobLevel);
		long afterCount = jobLevelRepository.count();

		assertTrue(beforeCount + 1 == afterCount);
		assertEquals(newJobLevel, jobLevelService.findById(newJobLevel.getId()).get());
	}

	@Test
	public void save_invalid() throws Exception {
		JobLevel newJobLevel = new JobLevelBuilder()
				.name("LZ").description("Desc2").expertise("Exp2")
				.build();

		assertEquals(Optional.empty(), jobLevelService.findById(newJobLevel.getId()));
		exception.expect(ConstraintViolationException.class);
		jobLevelService.save(newJobLevel);
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
