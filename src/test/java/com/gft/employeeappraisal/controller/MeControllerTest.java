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
import com.gft.employeeappraisal.converter.employee.EmployeeDTOConverter;
import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.employeeappraisal.model.Constants;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.JobLevel;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.JobLevelDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
 * Integration test class for the {@link MeController} controller class.
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MeController.class)
@Import({ControllerConfiguration.class, BeanConfiguration.class})
public class MeControllerTest {

	@Autowired
	private EntityDTOComparator entityDTOComparator;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean(reset = MockReset.AFTER)
	private EmployeeService employeeService;

	@MockBean(reset = MockReset.AFTER)
	private EmployeeDTOConverter employeeDTOConverter;

	private static final String URL_TEMPLATE = "/me";

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
	public void meGet() throws Exception {
		Employee mockEmployee = mockEmployee();
		when(employeeService.getLoggedInUser()).thenReturn(mockEmployee);

		doReturn(mockEmployeeDTO).when(employeeDTOConverter).convert(any(Employee.class));

		MvcResult result =
				mockMvc.perform(get(URL_TEMPLATE).with(csrf()))
						.andExpect(status().isOk())
						.andReturn();

		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeDTOConverter, times(1)).convert(any(Employee.class));

		EmployeeDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(),
				EmployeeDTO.class);

		entityDTOComparator.assertEqualsEmployee(mockEmployee, resultDTO);
	}

	@Test
	public void meGet_userNotExists() throws Exception {
		when(employeeService.getLoggedInUser()).thenReturn(null);

		MvcResult result =
				mockMvc.perform(get(URL_TEMPLATE).with(csrf()))
						.andExpect(status().isInternalServerError())
						.andReturn();

		verify(employeeService, times(1)).getLoggedInUser();

		OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(),
				OperationResultDTO.class);

		assertNotNull(resultDTO);
		assertEquals(Constants.ERROR, resultDTO.getMessage());
	}

	@Test
	public void meMentorGet() throws Exception {
		Employee mockEmployee = mockEmployee();
		when(employeeService.getLoggedInUser()).thenReturn(mockEmployee);

		//mentor lookup
		Employee mockMentor = mockMentor();
		when(employeeService.findCurrentMentorById(anyInt())).thenReturn(Optional.of(mockMentor));

		doReturn(mockMentorDTO).when(employeeDTOConverter).convert(any(Employee.class));

		MvcResult result =
				mockMvc.perform(get(URL_TEMPLATE + "/mentor").with(csrf()))
						.andExpect(status().isOk())
						.andReturn();

		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeService, times(1)).findCurrentMentorById(anyInt());
		verify(employeeDTOConverter, times(1)).convert(any(Employee.class));

		EmployeeDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(),
				EmployeeDTO.class);

		entityDTOComparator.assertEqualsEmployee(mockMentor, resultDTO);
	}

	@Test
	public void meMentorGet_employeeNotExists() throws Exception {
		when(employeeService.getLoggedInUser()).thenReturn(null);

		MvcResult result =
				mockMvc.perform(get(URL_TEMPLATE + "/mentor").with(csrf()))
						.andExpect(status().isInternalServerError())
						.andReturn();

		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeService, never()).findCurrentMentorById(anyInt());
		verify(employeeDTOConverter, never()).convert(any(Employee.class));

		EmployeeDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(),
				EmployeeDTO.class);

		assertNotNull(resultDTO);
		assertNull(resultDTO.getId());
		assertNull(resultDTO.getFirstName());
		assertNull(resultDTO.getLastName());
		assertNull(resultDTO.getEmail());
		assertNull(resultDTO.getGftIdentifier());
		assertNull(resultDTO.getApplicationRole());
		assertNull(resultDTO.getIsAdmin());
		assertNull(resultDTO.getIsMentor());
		assertNull(resultDTO.getIsPeer());
		assertNull(resultDTO.getJobLevel());
	}

	@Test
	public void meMentorGet_MentorNotExists() throws Exception {
		Employee mockEmployee = mockEmployee();
		when(employeeService.getLoggedInUser()).thenReturn(mockEmployee);
		when(employeeService.findCurrentMentorById(anyInt())).thenReturn(Optional.empty());

		MvcResult result =
				mockMvc.perform(get(URL_TEMPLATE + "/mentor").with(csrf()))
						.andExpect(status().isNotFound())
						.andReturn();

		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeService, times(1)).findCurrentMentorById(anyInt());
		verify(employeeDTOConverter, never()).convert(any(Employee.class));

		OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(),
				OperationResultDTO.class);

		assertNotNull(resultDTO);
		assertEquals(Constants.ERROR, resultDTO.getMessage());
		assertEquals("Mentor for Employee with Id -1 was not found", resultDTO.getData());
	}


	@Test
	public void meMenteesGet() throws Exception {
		Employee mockMentor = mockMentor();
		when(employeeService.getLoggedInUser()).thenReturn(mockMentor);

		//mentee lookup
		Employee mockEmployee = mockEmployee();
		when(employeeService.findCurrentMenteesById(anyInt())).thenReturn(Stream.of(mockEmployee));

		doReturn(mockEmployeeDTO).when(employeeDTOConverter).convert(any(Employee.class));

		MvcResult result =
				mockMvc.perform(get(URL_TEMPLATE + "/mentees").with(csrf()))
						.andExpect(status().isOk())
						.andReturn();

		List<EmployeeDTO> resultDTO = Arrays.asList(mapper.readValue(result.getResponse().getContentAsString(),
				EmployeeDTO[].class));

		assertNotNull(resultDTO);
		assertFalse(resultDTO.isEmpty());
		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeService, times(1)).findCurrentMenteesById(anyInt());
		verify(employeeDTOConverter, times(1)).convert(any(Employee.class));

		EmployeeDTO employeeDTO = resultDTO.get(0);

		entityDTOComparator.assertEqualsEmployee(mockEmployee, employeeDTO);
	}

	@Test
	public void meMenteesGet_emptyList() throws Exception {
		Employee mockMentor = mockMentor();
		when(employeeService.getLoggedInUser()).thenReturn(mockMentor);
		when(employeeService.findCurrentMenteesById(anyInt())).thenReturn(Stream.empty());

		MvcResult result =
				mockMvc.perform(get(URL_TEMPLATE + "/mentees").with(csrf()))
						.andExpect(status().isOk())
						.andReturn();

		List<EmployeeDTO> resultDTO = Arrays.asList(mapper.readValue(result.getResponse().getContentAsString(),
				EmployeeDTO[].class));

		assertNotNull(resultDTO);
		assertTrue(resultDTO.isEmpty());
		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeService, times(1)).findCurrentMenteesById(anyInt());
		verify(employeeDTOConverter, never()).convert(any(Employee.class));
	}

	@Test
	public void mePeersGet() throws Exception {
		Employee mockEmployee = mockEmployee();
		when(employeeService.getLoggedInUser()).thenReturn(mockEmployee);

		Employee mockMentor = mockMentor();
		when(employeeService.findCurrentPeersById(anyInt())).thenReturn(Stream.of(mockMentor));

		doReturn(mockMentorDTO).when(employeeDTOConverter).convert(any(Employee.class));

		MvcResult result =
				mockMvc.perform(get(URL_TEMPLATE + "/peers").with(csrf()))
						.andExpect(status().isOk())
						.andReturn();

		List<EmployeeDTO> resultDTO = Arrays.asList(mapper.readValue(result.getResponse().getContentAsString(),
				EmployeeDTO[].class));

		assertNotNull(resultDTO);
		assertFalse(resultDTO.isEmpty());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeService, times(1)).findCurrentPeersById(anyInt());
		verify(employeeDTOConverter, times(1)).convert(any(Employee.class));

		EmployeeDTO employeeDTO = resultDTO.get(0);

		entityDTOComparator.assertEqualsEmployee(mockMentor, employeeDTO);
	}

	@Test
	public void mePeersGet_noPeers() throws Exception {
		Employee mockEmployee = mockEmployee();
		when(employeeService.getLoggedInUser()).thenReturn(mockEmployee);
		when(employeeService.findCurrentPeersById(anyInt())).thenReturn(Stream.empty());

		MvcResult result =
				mockMvc.perform(get(URL_TEMPLATE + "/peers").with(csrf()))
						.andExpect(status().isNotFound())
						.andReturn();

		List<EmployeeDTO> resultDTO = Arrays.asList(mapper.readValue(result.getResponse().getContentAsString(),
				EmployeeDTO[].class));

		assertNotNull(resultDTO);
		assertTrue(resultDTO.isEmpty());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeService, times(1)).findCurrentPeersById(anyInt());
		verify(employeeDTOConverter, never()).convert(any(Employee.class));
	}

	@SuppressWarnings("all")
	private Employee mockEmployee() {
		return new EmployeeBuilder()
				.id(-1)
				.firstName("Manuel")
				.lastName("Yepez")
				.gftIdentifier("....")
				.email("manuel.yepez@gft.com")
				.applicationRole(mockApplicationRole().get())
				.jobLevel(mockJobLevel().get())
				.build();
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
				.jobFamily(new JobFamilyBuilder()
						.name("Job Family").description("Description").build()
				).build());
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
}