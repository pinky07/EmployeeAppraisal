package com.gft.employeeappraisal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.employeeappraisal.builder.dto.ApplicationRoleDTOBuilder;
import com.gft.employeeappraisal.builder.dto.EmployeeDTOBuilder;
import com.gft.employeeappraisal.builder.dto.JobFamilyDTOBuilder;
import com.gft.employeeappraisal.builder.dto.JobLevelDTOBuilder;
import com.gft.employeeappraisal.builder.model.ApplicationRoleBuilder;
import com.gft.employeeappraisal.builder.model.EmployeeBuilder;
import com.gft.employeeappraisal.builder.model.JobFamilyBuilder;
import com.gft.employeeappraisal.builder.model.JobLevelBuilder;
import com.gft.employeeappraisal.configuration.BeanConfiguration;
import com.gft.employeeappraisal.configuration.ControllerConfiguration;
import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.employeeappraisal.model.Constants;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.JobLevel;
import com.gft.employeeappraisal.service.DTOService;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.JobLevelDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test class for the {@link EmployeesController} controller class, mentor GET and PUT endpoints.
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EmployeesController.class)
@Import({ControllerConfiguration.class, BeanConfiguration.class})
public class EmployeesControllerMentorTest {

	private static final String EMPLOYEES_URL = "/employees";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EntityDTOComparator entityDTOComparator;

	@Autowired
	private ObjectMapper mapper;

	@MockBean(reset = MockReset.AFTER)
	private EmployeeService employeeService;

	@MockBean(reset = MockReset.AFTER)
	private EmployeeRelationshipService employeeRelationshipService;

	@MockBean
	@SuppressWarnings("unused")
	private DTOService dtoService;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private static EmployeeDTO mockEmployeeDTO;
	private static EmployeeDTO mockMentorDTO;

	@BeforeClass
	public static void setUp() {
		mockEmployeeDTO = new EmployeeDTOBuilder()
				.id(-1)
				.firstName("Manuel")
				.lastName("Yepez")
				.gftIdentifier("....")
				.email("manuel.yepez@gft.com")
				.applicationRole(mockApplicationRoleDTO())
				.jobLevel(mockJobLevelDTO())
				.build();

		mockMentorDTO = new EmployeeDTOBuilder()
				.id(-2)
				.firstName("Admin")
				.lastName("Admin")
				.gftIdentifier("AAAA")
				.email("admin@gft.com")
				.applicationRole(mockApplicationRoleDTO())
				.jobLevel(mockJobLevelDTO())
				.build();
	}

	@Test
	public void employeesEmployeeIdMentorGet() throws Exception {
		//mentor lookup
		Employee mockMentor = mockMentor();
		when(employeeService.findCurrentMentorById(anyInt())).thenReturn(Optional.of(mockMentor));

		MvcResult result = mockMvc.perform(get(String.format("%s/%d/mentor", EMPLOYEES_URL, mockEmployeeDTO.getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk()).andReturn();

		verify(employeeService, times(1)).findCurrentMentorById(anyInt());

		EmployeeDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(),
				EmployeeDTO.class);

		entityDTOComparator.assertEqualsEmployee(mockMentor, resultDTO);
	}

	@Test
	public void employeesEmployeeIdMentorGet_MentorNotExists() throws Exception {
		when(employeeService.findCurrentMentorById(anyInt())).thenReturn(Optional.empty());

		MvcResult result = mockMvc.perform(get(String.format("%s/%d/mentor", EMPLOYEES_URL, mockEmployeeDTO.getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isNotFound()).andReturn();

		verify(employeeService, times(1)).findCurrentMentorById(anyInt());

		EmployeeDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), EmployeeDTO.class);

		assertNotNull(resultDTO);
		assertNull(resultDTO.getFirstName());
		assertNull(resultDTO.getLastName());
		assertNull(resultDTO.getGftIdentifier());
		assertNull(resultDTO.getEmail());
		assertNull(resultDTO.getApplicationRole());
		assertNull(resultDTO.getJobLevel());
	}

	@Test
	public void employeesEmployeeIdMentorGet_badRequest() throws Exception {
		MvcResult result = mockMvc.perform(get(String.format("%s/null/mentor", EMPLOYEES_URL))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isBadRequest()).andReturn();

		verify(employeeService, never()).findCurrentMentorById(anyInt());
		assertTrue(StringUtils.isEmpty(result.getResponse().getContentAsString()));
	}

	@Test
	public void employeesEmployeeIdMentorPut() throws Exception {
		when(employeeService.findById(mockEmployeeDTO.getId())).thenReturn(Optional.of(mockEmployee()));
		when(employeeService.findById(mockMentorDTO.getId())).thenReturn(Optional.of(mockMentor()));
		doNothing().when(employeeRelationshipService).changeMentor(any(Employee.class), any(Employee.class));

		MvcResult result = mockMvc.perform(put(String.format("%s/%d/mentor", EMPLOYEES_URL, mockEmployeeDTO.getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(mockMentorDTO)))
				.andExpect(status().isOk()).andReturn();

		OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

		verify(employeeService, times(2)).findById(anyInt());
		verify(employeeRelationshipService, times(1)).changeMentor(any(Employee.class), any(Employee.class));

		assertNotNull(resultDTO);
		assertEquals(Constants.SUCCESS, resultDTO.getMessage());
		assertNull(resultDTO.getData());
		assertTrue(resultDTO.getErrors().isEmpty());
	}

	@Test
	public void employeesEmployeeIdMentorPut_EmployeeNotExists() throws Exception {
		when(employeeService.findById(mockEmployeeDTO.getId())).thenReturn(Optional.empty());

		MvcResult result = mockMvc.perform(put(String.format("%s/%d/mentor", EMPLOYEES_URL, mockEmployeeDTO.getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(mockMentorDTO))
		).andExpect(status().isNotFound()).andReturn();

		OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

		verify(employeeService, times(1)).findById(anyInt());
		verify(employeeRelationshipService, never()).changeMentor(any(Employee.class), any(Employee.class));

		assertNotNull(resultDTO);
		assertEquals(Constants.ERROR, resultDTO.getMessage());
		assertEquals("Employee with Id -1 couldn't be found", resultDTO.getData());
		assertTrue(resultDTO.getErrors().isEmpty());
	}

	@Test
	public void employeesEmployeeIdMentorPut_MentorNotExists() throws Exception {
		when(employeeService.findById(mockEmployeeDTO.getId())).thenReturn(Optional.of(mockEmployee()));
		when(employeeService.findById(mockMentorDTO.getId())).thenReturn(Optional.empty());

		MvcResult result = mockMvc.perform(put(String.format("%s/%d/mentor", EMPLOYEES_URL, mockEmployeeDTO.getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(mockMentorDTO))
		).andExpect(status().isNotFound()).andReturn();

		OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

		verify(employeeService, times(2)).findById(anyInt());
		verify(employeeRelationshipService, never()).changeMentor(any(Employee.class), any(Employee.class));

		assertNotNull(resultDTO);
		assertEquals(Constants.ERROR, resultDTO.getMessage());
		assertEquals("Employee with Id -1 couldn't be found therefore it cannot be put as Mentor", resultDTO.getData());
		assertTrue(resultDTO.getErrors().isEmpty());
	}

	@Test
	public void employeesEmployeeIdMentorPut_badRequest() throws Exception {

		MvcResult result = mockMvc.perform(put(String.format("%s/%d/mentor", EMPLOYEES_URL, mockEmployeeDTO.getId()))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isBadRequest()).andReturn();

		assertTrue(StringUtils.isEmpty(result.getResponse().getContentAsString()));

		verify(employeeService, never()).findById(anyInt());
		verify(employeeRelationshipService, never()).changeMentor(any(Employee.class), any(Employee.class));

		result = mockMvc.perform(put(String.format("%s/null/mentor", EMPLOYEES_URL))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(mockMentorDTO)))
				.andExpect(status().isBadRequest()).andReturn();

		assertTrue(StringUtils.isEmpty(result.getResponse().getContentAsString()));

		verify(employeeService, never()).findById(anyInt());
		verify(employeeRelationshipService, never()).changeMentor(any(Employee.class), any(Employee.class));
	}

	private static ApplicationRoleDTO mockApplicationRoleDTO() {
		return new ApplicationRoleDTOBuilder()
				.name("ApplicationRole").description("AppDescription").build();
	}

	private static JobLevelDTO mockJobLevelDTO() {
		return new JobLevelDTOBuilder()
				.name("Level").description("Description").expertise("Expertise")
				.jobFamily(new JobFamilyDTOBuilder()
						.name("Job Family").description("Description").build()
				).build();
	}

	@SuppressWarnings("all")
	private Employee mockEmployee() {
		return new EmployeeBuilder()
				.id(-1)
				.firstName("Manuel").lastName("Yepez").gftIdentifier("....").email("manuel.yepez@gft.com")
				.applicationRole(mockApplicationRole().get()).jobLevel(mockJobLevel().get()).build();
	}

	@SuppressWarnings("all")
	private Employee mockMentor() {
		return new EmployeeBuilder()
				.id(-2)
				.firstName("Admin")
				.lastName("Admin")
				.gftIdentifier("AAAA")
				.email("admin@gft.com")
				.applicationRole(mockApplicationRole().get())
				.jobLevel(mockJobLevel().get())
				.build();
	}

	private Optional<ApplicationRole> mockApplicationRole() {
		return Optional.of(new ApplicationRoleBuilder()
				.name("ApplicationRole").description("AppDescription").build());
	}

	private Optional<JobLevel> mockJobLevel() {
		return Optional.of(new JobLevelBuilder()
				.name("Level").description("Description").expertise("Expertise")
				.jobFamily(new JobFamilyBuilder().name("Job Family").description("Description").build()).build());
	}
}