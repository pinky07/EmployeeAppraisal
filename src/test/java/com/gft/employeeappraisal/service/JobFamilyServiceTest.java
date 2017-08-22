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

    @Before
    public void setUp() throws Exception {
        this.jobFamilyService = new JobFamilyServiceImpl(this.jobFamilyRepository);
    }

    @Test
    public void save() throws Exception {
        JobFamily jobFamily = new JobFamilyBuilder().buildWithDefaults();

        long beforeCount = jobFamilyRepository.count();
        jobFamily = jobFamilyService.saveAndFlush(jobFamily);
        long afterCount = jobFamilyRepository.count();

        assertTrue(beforeCount + 1 == afterCount);
        Optional<JobFamily> jobFamilyRetrieved = this.jobFamilyService.findById(jobFamily.getId());
        assertTrue(jobFamilyRetrieved.isPresent());
        assertEquals(jobFamily, jobFamilyRetrieved.get());
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_Invalid() throws Exception {
        jobFamilyService.saveAndFlush(new JobFamilyBuilder().name("invalid").build());
    }
}
