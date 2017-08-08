package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.ServiceSpringBootUnitTest;
import com.gft.employeeappraisal.repository.AppraisalRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Service layer test for {@link AppraisalService}
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class AppraisalServiceTest extends ServiceSpringBootUnitTest {

	@Autowired
	private AppraisalRepository appraisalRepository;

	@Test
	public void findById() throws Exception {
	}

}