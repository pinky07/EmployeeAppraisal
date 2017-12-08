package com.gft.employeeappraisal.controller.appraisals;

import com.gft.employeeappraisal.controller.BaseControllerTest;
import com.gft.swagger.employees.model.EmployeeEvaluationFormDTO;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AppraisalControllerPostTest extends BaseControllerTest
{
	private static EmployeeEvaluationFormDTO mockEmployeeEvaluationFormDto;
	private static final String EMPLOYEES_ID_APPRAISALS_ID_URL = "/employees/%d/appraisals/%d";
	@BeforeClass
	public static void setUp(){
		mockEmployeeEvaluationFormDto = new EmployeeEvaluationFormDTO();
	}
}
