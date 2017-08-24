package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.repository.AppraisalRepository;
import com.gft.employeeappraisal.service.impl.AppraisalServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * Service layer test for {@link AppraisalService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AppraisalServiceTest {

    @Autowired
    private AppraisalRepository appraisalRepository;

    @Mock
    private AppraisalXEvaluationFormXEmployeeRelationshipService appraisalXEvaluationFormXEmployeeRelationshipService;

    @Mock
    private EmployeeRelationshipService employeeRelationshipService;

    // Class under test
    private AppraisalService appraisalService;

    @Before
    public void setUp() throws Exception {
        this.appraisalService = new AppraisalServiceImpl(
                this.appraisalRepository,
                this.appraisalXEvaluationFormXEmployeeRelationshipService,
                this.employeeRelationshipService);
    }

    @Test
    public void findById() throws Exception {
        // TODO Implement this!
        assertTrue("Hello World".length() == 11);
    }
}