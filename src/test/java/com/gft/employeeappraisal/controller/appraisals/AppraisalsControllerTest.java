package com.gft.employeeappraisal.controller.appraisals;

import com.gft.employeeappraisal.builder.model.*;
import com.gft.employeeappraisal.controller.AppraisalsController;
import com.gft.employeeappraisal.controller.BaseControllerTest;
import com.gft.employeeappraisal.converter.appraisal.AppraisalDTOConverter;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.service.AppraisalService;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.model.AppraisalDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
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
public class AppraisalsControllerTest extends BaseControllerTest {

	private static final String EMPLOYEES_URL = "/employees";

	private static final String APPRAISAL_URL = "/appraisals";

	private static final String USER_EMAIL = "user@gft.com";

	@MockBean(reset = MockReset.AFTER)
	private AppraisalService appraisalService;

	@MockBean(reset = MockReset.AFTER)
	private EmployeeService employeeService;

	@MockBean(reset = MockReset.AFTER)
	private EmployeeEvaluationFormService employeeEvaluationFormService;

	@Autowired
	@SuppressWarnings("unused")
	private AppraisalDTOConverter appraisalDTOConverter;

	private Employee userMock;

	@Before
	public void sharedSetUp() {
		// Mock the user

		JobFamily jobFamilyMock = new JobFamilyBuilder()
				.id(1)
				.name("Job Family")
				.description("Job Family Description")
				.build();

		JobLevel jobLevelMock = new JobLevelBuilder()
				.id(1)
				.name("Job Level")
				.description("Job Level Description")
				.expertise("Expertise")
				.jobFamily(jobFamilyMock)
				.build();

		ApplicationRole applicationRoleMock = new ApplicationRoleBuilder()
				.id(1)
				.name("Application Role")
				.description("Application Role Description")
				.build();

		userMock = new EmployeeBuilder()
				.id(1)
				.email("user@gft.com")
				.firstName("John")
				.lastName("Doe")
				.gftIdentifier("JODO")
				.jobLevel(jobLevelMock)
				.applicationRole(applicationRoleMock)
				.build();

		when(employeeService.getLoggedInUser()).thenReturn(userMock);
	}

	@Test
	public void employeesEmployeeIdAppraisalsAppraisalIdFormsFormIdGet() throws Exception {
	}

    @Test
    public void employeesEmployeeIdAppraisalsAppraisalIdFormsGet() throws Exception {
    }

    @Test
    public void employeesIdAppraisalsIdGet() throws Exception {
		when(employeeService.getById(userMock.getId())).thenReturn(userMock);
		doReturn(Stream.of(testEmployeeEvaluationForm())).when(employeeEvaluationFormService)
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));

		doReturn(Optional.of(mockAppraisal())).when(appraisalService).findById(anyInt());

		MvcResult result = mockMvc.perform(get(String.format("%s/%d%s/%d", EMPLOYEES_URL, userMock.getId(),
				APPRAISAL_URL, mockAppraisal().getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		AppraisalDTO appraisalDTO = mapper.readValue(result.getResponse().getContentAsString(), AppraisalDTO.class);

		assertNotNull(appraisalDTO);
		assertEquals(mockAppraisal().getName(), appraisalDTO.getName());

		verify(employeeService, times(1)).getById(anyInt());
		verify(appraisalService, times(1)).findById(anyInt());
		verify(employeeEvaluationFormService, times(1))
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));
    }

	@Test
	public void employeesIdAppraisalsIdGet_emptyDTO() throws Exception {
		when(employeeService.getById(userMock.getId())).thenReturn(userMock);
		doReturn(Stream.empty()).when(employeeEvaluationFormService)
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));

		doReturn(Optional.of(mockAppraisal())).when(appraisalService).findById(anyInt());

		MvcResult result = mockMvc.perform(get(String.format("%s/%d%s/%d", EMPLOYEES_URL, userMock.getId(),
				APPRAISAL_URL, mockAppraisal().getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		AppraisalDTO appraisalDTO = mapper.readValue(result.getResponse().getContentAsString(), AppraisalDTO.class);

		assertNotNull(appraisalDTO);
		assertNull(appraisalDTO.getName());

		verify(employeeService, times(1)).getById(anyInt());
		verify(appraisalService, times(1)).findById(anyInt());
		verify(employeeEvaluationFormService, times(1))
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));
	}

	@Test
	public void employeesIdAppraisalsIdGet_AppNotFound() throws Exception {
		when(employeeService.getById(userMock.getId())).thenReturn(userMock);
		doReturn(Stream.of(testEmployeeEvaluationForm())).when(employeeEvaluationFormService)
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));

		doReturn(Optional.empty()).when(appraisalService).findById(anyInt());

		MvcResult result = mockMvc.perform(get(String.format("%s/%d%s/%d", EMPLOYEES_URL, userMock.getId(),
				APPRAISAL_URL, mockAppraisal().getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		OperationResultDTO operationResultDTO = mapper.readValue(result.getResponse().getContentAsString(),
				OperationResultDTO.class);

		assertNotNull(operationResultDTO);
		assertEquals(Constants.ERROR, operationResultDTO.getMessage());

		verify(employeeService, times(1)).getById(anyInt());
		verify(appraisalService, times(1)).findById(anyInt());
		verify(employeeEvaluationFormService, never())
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));
	}

	@Test
	public void employeesIdAppraisalsIdGet_EmpNotFound() throws Exception {
		when(employeeService.getById(userMock.getId())).thenThrow(new NotFoundException("Employee Not Found"));

		MvcResult result = mockMvc.perform(get(String.format("%s/%d%s/%d", EMPLOYEES_URL, userMock.getId(),
				APPRAISAL_URL, mockAppraisal().getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		OperationResultDTO operationResultDTO = mapper.readValue(result.getResponse().getContentAsString(),
				OperationResultDTO.class);

		assertNotNull(operationResultDTO);
		assertEquals(Constants.ERROR, operationResultDTO.getMessage());

		verify(employeeService, times(1)).getById(anyInt());
		verify(appraisalService, never()).findById(anyInt());
		verify(employeeEvaluationFormService, never())
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));
	}

	@Test
	public void employeesIdAppraisalsIdGet_badRequest() throws Exception {

		MvcResult result = mockMvc.perform(get(String.format("%s/%d%s/null", EMPLOYEES_URL, userMock.getId(),
				APPRAISAL_URL))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();

		String resultString = result.getResponse().getContentAsString();

		assertTrue(StringUtils.isEmpty(resultString));

		verify(employeeService, never()).findById(anyInt());
		verify(appraisalService, never()).findById(anyInt());
		verify(employeeEvaluationFormService, never())
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));
	}

    @Test
    public void meAppraisalsAppraisalIdFormsFormIdGet() throws Exception {
    }

    @Test
    public void meAppraisalsAppraisalIdFormsGet() throws Exception {
    }

    @Test
	@WithMockUser(USER_EMAIL)
    public void meAppraisalsIdGet() throws Exception {
		doReturn(Stream.of(testEmployeeEvaluationForm())).when(employeeEvaluationFormService)
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));

		doReturn(Optional.of(mockAppraisal())).when(appraisalService).findById(anyInt());

		MvcResult result = mockMvc.perform(get(String.format("/me/%s/%d", APPRAISAL_URL, mockAppraisal().getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		AppraisalDTO appraisalDTO = mapper.readValue(result.getResponse().getContentAsString(), AppraisalDTO.class);

		assertNotNull(appraisalDTO);
		assertEquals(mockAppraisal().getName(), appraisalDTO.getName());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(appraisalService, times(1)).findById(anyInt());
		verify(employeeEvaluationFormService, times(1))
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));
    }

	@Test
	@WithMockUser(USER_EMAIL)
	public void meAppraisalsIdGet_emptyResult() throws Exception {
		doReturn(Stream.empty()).when(employeeEvaluationFormService)
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));

		doReturn(Optional.of(mockAppraisal())).when(appraisalService).findById(anyInt());

		MvcResult result = mockMvc.perform(get(String.format("/me/%s/%d", APPRAISAL_URL, mockAppraisal().getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		AppraisalDTO appraisalDTO = mapper.readValue(result.getResponse().getContentAsString(), AppraisalDTO.class);

		assertNotNull(appraisalDTO);
		assertNull(appraisalDTO.getName());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(appraisalService, times(1)).findById(anyInt());
		verify(employeeEvaluationFormService, times(1))
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));
	}

	@Test
	@WithMockUser(USER_EMAIL)
	public void meAppraisalsIdGet_NotFound() throws Exception {
		doReturn(Optional.empty()).when(appraisalService).findById(anyInt());

		MvcResult result = mockMvc.perform(get(String.format("/me/%s/%d", APPRAISAL_URL, mockAppraisal().getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		AppraisalDTO appraisalDTO = mapper.readValue(result.getResponse().getContentAsString(), AppraisalDTO.class);

		assertNotNull(appraisalDTO);
		assertNull(appraisalDTO.getName());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(appraisalService, times(1)).findById(anyInt());
		verify(employeeEvaluationFormService, never())
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));
	}

	@Test
	@WithMockUser(USER_EMAIL)
	public void meAppraisalsIdGet_badRequest() throws Exception {

		MvcResult result = mockMvc.perform(get(String.format("/me/%s/null", APPRAISAL_URL))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();

		String response = result.getResponse().getContentAsString();

		assertTrue(StringUtils.isEmpty(response));

		verify(employeeService, never()).getLoggedInUser();
		verify(appraisalService, never()).findById(anyInt());
		verify(employeeEvaluationFormService, never())
				.findByAppraisalAndEmployee(any(Appraisal.class), any(Employee.class));
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

		AppraisalDTO appraisalDTO = appraisalDTOList.get(0);
		assertEquals(mockAppraisal().getName(), appraisalDTO.getName());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(appraisalService, times(1)).findEmployeeAppraisals(any(Employee.class),
				isNull(EvaluationStatus.class));
	}

	@Test
	@WithMockUser(USER_EMAIL)
	public void meAppraisalsGet_empty() throws Exception {
		doReturn(Stream.empty()).when(appraisalService)
				.findEmployeeAppraisals(any(Employee.class), isNull(EvaluationStatus.class));

		MvcResult result = mockMvc.perform(get(String.format("/me/%s", APPRAISAL_URL))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		List<AppraisalDTO> appraisalDTOList = Arrays.asList(mapper.readValue(result
				.getResponse().getContentAsString(), AppraisalDTO[].class));

		assertNotNull(appraisalDTOList);
		assertTrue(appraisalDTOList.isEmpty());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(appraisalService, times(1)).findEmployeeAppraisals(any(Employee.class),
				isNull(EvaluationStatus.class));
	}

	@Test
	public void employeesIdAppraisalsGet() throws Exception {
		when(employeeService.getById(userMock.getId())).thenReturn(userMock);
		doReturn(Stream.of(mockAppraisal())).when(appraisalService)
				.findEmployeeAppraisals(any(Employee.class), isNull(EvaluationStatus.class));

		MvcResult result = mockMvc.perform(
				get(String.format("%s/%d%s",
						EMPLOYEES_URL,
						this.userMock.getId(),
						APPRAISAL_URL))
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		List<AppraisalDTO> appraisalDTOList = Arrays.asList(mapper.readValue(result
				.getResponse().getContentAsString(), AppraisalDTO[].class));

		assertNotNull(appraisalDTOList);
		assertFalse(appraisalDTOList.isEmpty());

		AppraisalDTO appraisalDTO = appraisalDTOList.get(0);
		assertEquals(mockAppraisal().getName(), appraisalDTO.getName());

		verify(employeeService, times(1)).getById(anyInt());
		verify(appraisalService, times(1)).findEmployeeAppraisals(any(Employee.class),
				isNull(EvaluationStatus.class));
	}

	@Test
	public void employeesIdAppraisalsGet_emptyList() throws Exception {
		when(employeeService.getById(userMock.getId())).thenReturn(userMock);
		doReturn(Stream.empty()).when(appraisalService)
				.findEmployeeAppraisals(any(Employee.class), isNull(EvaluationStatus.class));

		MvcResult result = mockMvc.perform(
				get(String.format("%s/%d%s",
						EMPLOYEES_URL,
						this.userMock.getId(),
						APPRAISAL_URL))
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		List<AppraisalDTO> appraisalDTOList = Arrays.asList(mapper.readValue(result
				.getResponse().getContentAsString(), AppraisalDTO[].class));

		assertNotNull(appraisalDTOList);
		assertTrue(appraisalDTOList.isEmpty());

		verify(employeeService, times(1)).getById(anyInt());
		verify(appraisalService, times(1)).findEmployeeAppraisals(any(Employee.class),
				isNull(EvaluationStatus.class));
	}

	@Test
	public void employeesIdAppraisalsGet_employeeNotFound() throws Exception {
		when(employeeService.getById(userMock.getId())).thenThrow(new NotFoundException("Employee Not Found"));

		MvcResult result = mockMvc.perform(
				get(String.format("%s/%d%s",
						EMPLOYEES_URL,
						this.userMock.getId(),
						APPRAISAL_URL))
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		OperationResultDTO resultDTO = mapper.readValue(result
				.getResponse().getContentAsString(), OperationResultDTO.class);

		assertNotNull(resultDTO);
		assertEquals(resultDTO.getMessage(), Constants.ERROR);

		verify(employeeService, times(1)).getById(anyInt());
		verify(appraisalService, never()).findEmployeeAppraisals(any(Employee.class),
				isNull(EvaluationStatus.class));
	}

	@Test
	public void employeesIdAppraisalsGet_badRequest() throws Exception {

		MvcResult result = mockMvc.perform(
				get(String.format("%s/null%s",
						EMPLOYEES_URL,
						APPRAISAL_URL))
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();

		String resultString = result.getResponse().getContentAsString();

		assertTrue(StringUtils.isEmpty(resultString));

		verify(employeeService, never()).findById(anyInt());
		verify(appraisalService, never()).findEmployeeAppraisals(any(Employee.class),
				isNull(EvaluationStatus.class));
	}

	private Appraisal mockAppraisal() {
		return new AppraisalBuilder()
				.name("Mock Appraisal")
				.description("Mock Appraisal")
				.startDate(OffsetDateTime.now())
				.build();
	}

	private EmployeeEvaluationForm testEmployeeEvaluationForm() {
		return new EmployeeEvaluationFormBuilder()
				.appraisalXEvaluationForm(new AppraisalXEvaluationFormBuilder()
				.appraisal(mockAppraisal()).buildWithDefaults())
				.buildWithDefaults();
	}
}