package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.builder.model.ApplicationRoleBuilder;
import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.employeeappraisal.model.ApplicationRoleName;
import com.gft.employeeappraisal.repository.ApplicationRoleRepository;
import com.gft.employeeappraisal.service.impl.ApplicationRoleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Service layer test for {@link ApplicationRoleService}
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationRoleServiceTest {

    @Autowired
    private ApplicationRoleRepository applicationRoleRepository;

    // Class under test
    private ApplicationRoleService applicationRoleService;

    @Before
    public void setUp() {
        this.applicationRoleService = new ApplicationRoleServiceImpl(
                this.applicationRoleRepository);
    }

    @Test
    public void findById_Admin() throws Exception {
        ApplicationRole applicationRole = applicationRoleService.findById(ApplicationRoleName.ADMIN.getId()).get();
        assertEquals(ApplicationRoleName.ADMIN.getId(), applicationRole.getId());
        assertEquals(ApplicationRoleName.ADMIN.name(), applicationRole.getName());
    }

    @Test
    public void findById_User() throws Exception {
        ApplicationRole applicationRole = applicationRoleService.findById(ApplicationRoleName.USER.getId()).get();
        assertEquals(ApplicationRoleName.USER.getId(), applicationRole.getId());
        assertEquals(ApplicationRoleName.USER.name(), applicationRole.getName());
    }

    @Test
    public void findById_NotFound() throws Exception {
        assertEquals(Optional.empty(), applicationRoleService.findById(0));
    }

    @Test
    public void save() throws Exception {
        // Set up
        ApplicationRole applicationRole = new ApplicationRoleBuilder().buildWithDefaults();

        // Execution
        long beforeCount = applicationRoleRepository.count();
        applicationRole = applicationRoleService.save(applicationRole);
        long afterCount = applicationRoleRepository.count();

        // Verification
        assertTrue(beforeCount + 1 == afterCount);
        Optional<ApplicationRole> applicationRoleRetrieved = applicationRoleService.findById(applicationRole.getId());
        assertTrue(applicationRoleRetrieved.isPresent());
        assertEquals(applicationRole, applicationRoleRetrieved.get());
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_Invalid() throws Exception {
        ApplicationRole applicationRole = new ApplicationRoleBuilder()
                .name("Invalid")
                .build();
        ApplicationRole applicationRoleSaved = applicationRoleService.save(applicationRole);
    }
}
