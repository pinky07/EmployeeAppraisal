package com.gft.employee.service;

import com.gft.employee.ServiceSpringBootUnitTest;
import com.gft.employee.builder.model.JobFamilyBuilder;
import com.gft.employee.model.JobFamily;
import com.gft.employee.repository.JobFamilyRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Service layer test for {@link JobFamilyService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class JobFamilyServiceTest extends ServiceSpringBootUnitTest {

	@Autowired
	private JobFamilyService jobFamilyService;

	@Autowired
	private JobFamilyRepository jobFamilyRepository;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private JobFamily jobFamily;

	@Test
	public void save() throws Exception {
		long beforeCount = jobFamilyRepository.count();
		jobFamily = jobFamilyService.save(mockJobFamily());
		long afterCount = jobFamilyRepository.count();
		assertTrue(beforeCount + 1 == afterCount);

		assertNotNull(jobFamilyRepository.findOne(jobFamily.getId()));
	}

	@Test
	public void save_invalid() throws Exception {
		long beforeCount = jobFamilyRepository.count();
		exception.expect(ConstraintViolationException.class);
		jobFamily = jobFamilyService.save(new JobFamilyBuilder().name("invalid").build());
		long afterCount = jobFamilyRepository.count();
		assertTrue(beforeCount == afterCount);

		assertNull(jobFamilyRepository.findOne(jobFamily.getId()));
	}

	private JobFamily mockJobFamily() {
		return new JobFamilyBuilder()
				.name("Job Family").description("Description").build();
	}
}
