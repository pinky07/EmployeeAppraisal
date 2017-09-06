package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.helper.builder.model.JobFamilyBuilder;
import com.gft.employeeappraisal.helper.builder.model.JobLevelBuilder;
import com.gft.employeeappraisal.model.JobFamily;
import com.gft.employeeappraisal.model.JobLevel;
import com.gft.employeeappraisal.repository.JobFamilyRepository;
import com.gft.employeeappraisal.repository.JobLevelRepository;
import com.gft.employeeappraisal.service.impl.JobLevelServiceImpl;
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
 * Service layer test for {@link JobLevelService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class JobLevelServiceTest extends BaseServiceTest {

    // Class under test
    private JobLevelService jobLevelService;

    // Test Fixtures
    private JobFamily jobFamily;

    @Before
    public void setUp() throws Exception {
        this.jobLevelService = new JobLevelServiceImpl(
                this.jobLevelRepository);
        this.jobFamily = this.jobFamilyRepository.saveAndFlush(new JobFamilyBuilder()
                .buildWithDefaults());
    }

    @Test
    public void findById() throws Exception {
        // Set up
        JobLevel jobLevel = new JobLevelBuilder()
                .jobFamily(this.jobFamily)
                .buildWithDefaults();

        // Execution
        Optional<JobLevel> jobLevelRetrieved = jobLevelService.saveAndFlush(jobLevel);

        // Verification
        assertTrue(jobLevelRetrieved.isPresent());
    }

    @Test
    public void findById_NotFound() throws Exception {
        assertEquals(Optional.empty(), jobLevelService.findById(-100));
    }

    @Test
    public void save() throws Exception {
        // Set up
        JobLevel jobLevel = new JobLevelBuilder()
                .jobFamily(this.jobFamily)
                .buildWithDefaults();

        // Execution
        long beforeCount = jobLevelRepository.count();
        Optional<JobLevel> jobLevelRetrieved = jobLevelService.saveAndFlush(jobLevel);
        long afterCount = jobLevelRepository.count();

        // Verification
        assertTrue(beforeCount + 1 == afterCount);
        assertTrue(jobLevelRetrieved.isPresent());
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_Invalid() throws Exception {
        // Set up
        JobLevel newJobLevel = new JobLevelBuilder()
                .build();

        // Execution
        jobLevelService.saveAndFlush(newJobLevel);
    }
}
