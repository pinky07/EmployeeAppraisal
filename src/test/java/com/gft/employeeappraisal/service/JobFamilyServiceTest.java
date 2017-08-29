package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.builder.model.JobFamilyBuilder;
import com.gft.employeeappraisal.model.JobFamily;
import com.gft.employeeappraisal.repository.JobFamilyRepository;
import com.gft.employeeappraisal.service.impl.JobFamilyServiceImpl;
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
 * Service layer test for {@link JobFamilyService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class JobFamilyServiceTest {

    @Autowired
    private JobFamilyRepository jobFamilyRepository;

    // Class under test
    private JobFamilyService jobFamilyService;

    private JobFamily jobFamily;

    @Before
    public void setUp() throws Exception {
        this.jobFamilyService = new JobFamilyServiceImpl(this.jobFamilyRepository);

        this.jobFamily = this.jobFamilyRepository.saveAndFlush(new JobFamilyBuilder()
                .buildWithDefaults());
    }

    @Test
    public void findById() throws Exception {
        Optional<JobFamily> retrieved = jobFamilyService.findById(jobFamily.getId());

        assertTrue(retrieved.isPresent());
        assertEquals(jobFamily, retrieved.get());
    }

    @Test
    public void save() throws Exception {
        // Set up
        JobFamily jobFamily = new JobFamilyBuilder().buildWithDefaults();

        // Execution
        long beforeCount = jobFamilyRepository.count();
        Optional<JobFamily> jobFamilyRetrieved = jobFamilyService.saveAndFlush(jobFamily);
        long afterCount = jobFamilyRepository.count();

        // Verification
        assertTrue(beforeCount + 1 == afterCount);
        assertTrue(jobFamilyRetrieved.isPresent());
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_Invalid() throws Exception {
        // Execution
        jobFamilyService.saveAndFlush(new JobFamilyBuilder()
                .name("invalid")
                .build());
    }
}
