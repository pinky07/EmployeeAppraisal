package com.gft.employee.service;

import com.gft.employee.ServiceSpringBootUnitTest;
import com.gft.employee.builder.model.ApplicationRoleBuilder;
import com.gft.employee.model.ApplicationRole;
import com.gft.employee.model.ApplicationRoleNames;
import com.gft.employee.repository.ApplicationRoleRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Service layer test for {@link ApplicationRoleService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class ApplicationRoleServiceTest extends ServiceSpringBootUnitTest {

	@Autowired
	private ApplicationRoleService applicationRoleService;

	@Autowired
	private ApplicationRoleRepository applicationRoleRepository;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	@SuppressWarnings("all")
	public void findById() throws Exception {
		ApplicationRole applicationRole = applicationRoleService.findById(ApplicationRoleNames.ADMIN.getId()).get();
		assertEquals(ApplicationRoleNames.ADMIN.getId(), applicationRole.getId());
		assertEquals(ApplicationRoleNames.ADMIN.name(), applicationRole.getName());

		applicationRole = applicationRoleService.findById(ApplicationRoleNames.USER.getId()).get();
		assertEquals(ApplicationRoleNames.USER.getId(), applicationRole.getId());
		assertEquals(ApplicationRoleNames.USER.name(), applicationRole.getName());
	}

	@Test
	public void findById_notExists() throws Exception {
		assertEquals(Optional.empty(), applicationRoleService.findById(0));
	}

	@Test
	public void save() throws Exception {
		long beforeCount = applicationRoleRepository.count();
		ApplicationRole applicationRole = applicationRoleService.save(mockApplicationRole().get());
		long afterCount = applicationRoleRepository.count();

		assertTrue(beforeCount + 1 == afterCount);
		assertEquals(applicationRole, applicationRoleService.findById(applicationRole.getId()).get());
	}

	@Test
	public void save_invalid() throws Exception {
		long beforeCount = applicationRoleRepository.count();
		exception.expect(ConstraintViolationException.class);
		ApplicationRole applicationRole = applicationRoleService.save(new ApplicationRoleBuilder().name("invalid").build());
		long afterCount = applicationRoleRepository.count();

		assertTrue(beforeCount == afterCount);
		assertNull(applicationRole);
	}

	private Optional<ApplicationRole> mockApplicationRole() {
		return Optional.of(new ApplicationRoleBuilder()
				.name("ApplicationRole").description("AppDescription").build());
	}
}
