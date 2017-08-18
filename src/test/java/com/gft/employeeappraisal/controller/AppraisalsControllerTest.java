package com.gft.employeeappraisal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.employeeappraisal.builder.model.*;
import com.gft.employeeappraisal.converter.appraisal.AppraisalDTOConverter;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.service.AppraisalService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.model.AppraisalDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test class for the {@link AppraisalsController} controller class, GET endpoints.
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles({ "default", "local", "test" })
public class AppraisalsControllerTest {

	private static final String APPRAISAL_URL = "/appraisals";

	private static final String USER_EMAIL = "user@gft.com";

	@Autowired
	private MockMvc mockMvc;

	@MockBean(reset = MockReset.AFTER)
	private AppraisalService appraisalService;

	@MockBean(reset = MockReset.AFTER)
	private EmployeeService employeeService;

	@Autowired
	@SuppressWarnings("unused")
	private AppraisalDTOConverter appraisalDTOConverter;

	@Autowired
	private ObjectMapper mapper;

	private Employee userMock;
	private JobLevel jobLevelMock;
	private ApplicationRole applicationRoleMock;

	@Before
	public void sharedSetUp() {
		// Mock the user

		JobFamily jobFamilyMock = new JobFamilyBuilder()
				.id(1)
				.name("Job Family")
				.description("Job Family Description")
				.buildMock();

		jobLevelMock = new JobLevelBuilder()
				.id(1)
				.name("Job Level")
				.description("Job Level Description")
				.expertise("Expertise")
				.jobFamily(jobFamilyMock)
				.buildMock();

		applicationRoleMock = new ApplicationRoleBuilder()
				.id(1)
				.name("Application Role")
				.description("Application Role Description")
				.buildMock();

		userMock = new EmployeeBuilder()
				.id(1)
				.email("user@gft.com")
				.firstName("John")
				.lastName("Doe")
				.gftIdentifier("JODO")
				.jobLevel(jobLevelMock)
				.applicationRole(applicationRoleMock)
				.buildMock();

		when(employeeService.getLoggedInUser()).thenReturn(userMock);
	}

	@Test
	public void employeesEmployeeIdAppraisalsAppraisalIdFormsFormIdGet() throws Exception {
	}

    @Test
    public void employeesEmployeeIdAppraisalsAppraisalIdFormsGet() throws Exception {
    }

    @Test
    public void employeesEmployeeIdAppraisalsAppraisalIdGet() throws Exception {
    }

    @Test
    public void employeesEmployeeIdAppraisalsGet() throws Exception {
    }

    @Test
    public void meAppraisalsAppraisalIdFormsFormIdGet() throws Exception {
    }

    @Test
    public void meAppraisalsAppraisalIdFormsGet() throws Exception {
    }

    @Test
    public void meAppraisalsAppraisalIdGet() throws Exception {
    }

	@Test
	@WithMockUser(USER_EMAIL)
	public void meAppraisalsGet() throws Exception {
		doReturn(Stream.of(mockAppraisal())).when(appraisalService)
				.findEmployeeAppraisals(any(Employee.class), isNull(EvaluationStatus.class));

		MvcResult result = mockMvc.perform(get(String.format("/me/%s", APPRAISAL_URL))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		List<AppraisalDTO> appraisalDTOList = Arrays.asList(mapper.readValue(result
				.getResponse().getContentAsString(), AppraisalDTO[].class));

		assertNotNull(appraisalDTOList);
		assertFalse(appraisalDTOList.isEmpty());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(appraisalService, times(1)).findEmployeeAppraisals(any(Employee.class),
				isNull(EvaluationStatus.class));
	}

	private Appraisal mockAppraisal() {
		return new AppraisalBuilder()
				.name("Mock Appraisal")
				.description("Mock Appraisal")
				.startDate(LocalDateTime.now())
				.build();
	}
}