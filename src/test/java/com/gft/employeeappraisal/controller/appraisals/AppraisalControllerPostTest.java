package com.gft.employeeappraisal.controller.appraisals;

import com.gft.employeeappraisal.controller.BaseControllerTest;
import com.gft.employeeappraisal.helper.builder.dto.*;
import com.gft.employeeappraisal.helper.builder.model.*;
import com.gft.employeeappraisal.model.*;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeEvaluationFormDTO;
import com.gft.swagger.employees.model.JobLevelDTO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AppraisalControllerPostTest extends BaseControllerTest
{

	private static final String EMPLOYEES_URL = "/employees/{employeeId}/appraisals/{appraisalId}/forms";
	private static EmployeeDTO mockEmployeeDTO;
	private static EmployeeEvaluationForm mockEmployeeEvaluationFormDto;
	private static final String EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL = "/employees/%d/appraisals/%d/forms";
	private static final String EMPLOYEES_ID_APPRAISALS_ID_URL = "/employees/%d/appraisals/%d";
	private static final String EMPLOYEES_ID_APPRAISALS_ID_FORM_URL = "/employees/%d/appraisals/%d/forms";
	private Employee user;
	private Appraisal appraisal;
	private EmployeeEvaluationForm employeeEvaluationForm;
	private static EmployeeEvaluationFormDTO employeeEvaluationFormDTO;

	@Before
	public  void setUp() throws Exception
	{
		JobFamily jobFamilyMock = new JobFamilyBuilder()
			.buildWithDefaults();
		EvaluationFormTemplate evaluationFormTemplate = new EvaluationFormTemplateBuilder().buildWithDefaults();

		EvaluationFormTemplateXSectionXQuestion evaluationFormTemplateXSectionXQuestion = new
				EvaluationFormTemplateXSectionXQuestionBuilder(evaluationFormTemplate).buildWithDefaults();
		AppraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate = new AppraisalXEvaluationFormTemplateBuilder()
				.evaluationFormTemplate(evaluationFormTemplate)
				.buildWithDefaults();

		employeeEvaluationFormDTO =new EmployeeEvaluationFormDTOBuilder().buildWithDefaults();


		employeeEvaluationForm = new EmployeeEvaluationFormBuilder()
				.filledByEmployee(user)
				.appraisalXEvaluationFormTemplate(appraisalXEvaluationFormTemplate)
				.submitDate(OffsetDateTime.now()).buildWithDefaults();


		JobLevel jobLevel = new JobLevelBuilder()
				.jobFamily(jobFamilyMock)
				.buildWithDefaults();

		ApplicationRole applicationRole = new ApplicationRoleBuilder()
				.buildWithDefaults();
		mockEmployeeEvaluationFormDto = new EmployeeEvaluationFormBuilder()
				.id(26)
			.buildWithDefaults();

		this.user = new EmployeeBuilder()
				.id(26)
				.jobLevel(jobLevel)
				.applicationRole(applicationRole)
				.email("Pinky.Agrawal@gft.com")
				.buildWithDefaults();

		this.appraisal = new AppraisalBuilder()
				.id(1).buildWithDefaults();



		Employee employeeMentor = new EmployeeBuilder()
				.firstName("Mentor")
				.jobLevel(jobLevel)
				.applicationRole(applicationRole)
				.buildWithDefaults();


	}
	private Optional<ApplicationRole> mockApplicationRole() {
		return Optional.of(new ApplicationRoleBuilder()
				.name("ApplicationRole").description("AppDescription").build());
	}
	private Optional<JobLevel> mockJobLevel() {
		return Optional.of(new JobLevelBuilder()
				.name("Level").description("Description").expertise("Expertise")
				.jobFamily(new JobFamilyBuilder()
						.name("Job Family").description("Description").build()
				).build());
	}
	@SuppressWarnings("all")
	private EmployeeEvaluationForm mockEmployeeEvaulationForm() {
		return new EmployeeEvaluationFormBuilder().buildWithDefaults();
	}
	@Test
	public void employeesIdAppraisalsIdFormsPut() throws Exception {
//		when(employeeService.getById(anyInt())).thenReturn(user);
//		when(appraisalService.getById(anyInt())).thenReturn(appraisal);
//		//when(employeeEvaluationFormService.getById(anyInt())).thenReturn(employeeEvaluationFormDTO);
//		when(employeeEvaluationFormService.saveAndFlush(any(EmployeeEvaluationForm.class))).thenReturn( Optional.of(mockEmployeeEvaulationForm()));
//		MvcResult result = mockMvc.perform(put(String.format(
//				EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL,user.getId(),appraisal.getId()))
//				.with(csrf())
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsBytes(mockEmployeeEvaluationFormDto))
//		).andExpect(status().isOk()).andReturn();
//	}
}
