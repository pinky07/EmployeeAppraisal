package com.gft.employeeappraisal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.employeeappraisal.builder.dto.*;
import com.gft.employeeappraisal.builder.model.*;
import com.gft.employeeappraisal.configuration.BeanConfiguration;
import com.gft.employeeappraisal.configuration.ControllerConfiguration;
import com.gft.employeeappraisal.converter.employee.EmployeeDTOConverter;
import com.gft.employeeappraisal.converter.employeerelationship.EmployeeRelationshipDTOConverter;
import com.gft.employeeappraisal.exception.EmployeeNotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test class for the {@link EmployeesController} controller class, GET endpoints.
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EmployeesController.class)
@Import({ControllerConfiguration.class, BeanConfiguration.class})
public class EmployeesControllerGetTest {

    private static final String EMPLOYEES_URL = "/employees";

    private static final String USER_EMAIL = "user@gft.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityDTOComparator entityDTOComparator;

	@MockBean(reset = MockReset.AFTER)
	private EmployeeService employeeService;

	@MockBean(reset = MockReset.AFTER)
	@SuppressWarnings("unused")
	private EmployeeRelationshipService employeeRelationshipService;

	@MockBean(reset = MockReset.AFTER)
	private EmployeeDTOConverter employeeDTOConverter;

    @MockBean(reset = MockReset.AFTER)
	private EmployeeRelationshipDTOConverter employeeRelationshipDTOConverter;

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
    @WithMockUser(USER_EMAIL)
    public void employeesEmployeeIdGet_Successful() throws Exception {

        // Set-up

        when(employeeService.findById(userMock.getId())).thenReturn(Optional.of(userMock));
        doReturn(mockEmployeeDTO()).when(employeeDTOConverter).convert(any(Employee.class));

        // Execution

        MvcResult result = mockMvc.perform(
                get(String.format("%s/%d",
                        EMPLOYEES_URL,
                        this.userMock.getId()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        EmployeeDTO employeeDTO = mapper.readValue(resultString, EmployeeDTO.class);

        // Verification

        verify(employeeService, times(1)).checkAccess(anyInt(), anyInt());
        verify(employeeService, times(1)).findById(userMock.getId());
        verify(employeeDTOConverter, times(1)).convert(any(Employee.class));

        entityDTOComparator.assertEqualsEmployee(userMock, employeeDTO);
    }


    @Test
    @WithMockUser(USER_EMAIL)
    public void employeesEmployeeIdGet_userNotFound() throws Exception {

        // Set-up

        when(employeeService.findById(userMock.getId())).thenReturn(Optional.empty());

        // Execution

        MvcResult result = mockMvc.perform(
                get(String.format("%s/%d",
                        EMPLOYEES_URL,
                        this.userMock.getId()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        OperationResultDTO operationResultDTO = mapper.readValue(resultString, OperationResultDTO.class);

        // Verification

        verify(employeeService, times(1)).checkAccess(anyInt(), anyInt());
        verify(employeeService, times(1)).findById(userMock.getId());
        verify(employeeDTOConverter, never()).convert(any(Employee.class));

        assertEquals(operationResultDTO.getMessage(), Constants.ERROR);
    }

    @Test
    @WithMockUser(USER_EMAIL)
    public void employeesEmployeeIdGet_badRequest() throws Exception {

        // Set-up

        // Execution

        MvcResult result = mockMvc.perform(get(String.format("%s/%s", EMPLOYEES_URL, "null"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();

        String resultString = result.getResponse().getContentAsString();

        // Verification

        verify(employeeService, never()).checkAccess(anyInt(), anyInt());
        verify(employeeService, never()).findById(userMock.getId());
		verify(employeeDTOConverter, never()).convert(any(Employee.class));

        assertTrue(StringUtils.isEmpty(resultString));
    }

	@Test
	public void employeesEmployeeIdRelationshipsGet() throws Exception {
		when(employeeService.findById(userMock.getId())).thenReturn(Optional.of(userMock));
		doReturn(mockEmployeeRelationshipDTO()).when(employeeRelationshipDTOConverter)
				.convert(any(EmployeeRelationship.class));
    	doReturn(Stream.of(mockEmployeeRelationship()))
				.when(employeeService).findCurrentRelationshipsBySourceEmployee(userMock,
				RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);

		MvcResult result = mockMvc.perform(
				get(String.format("%s/%d/relationships",
						EMPLOYEES_URL,
						this.userMock.getId()))
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		List<EmployeeRelationshipDTO> employeeRelationshipDTOList = Arrays.asList(mapper.readValue(result
				.getResponse().getContentAsString(), EmployeeRelationshipDTO[].class));

		assertNotNull(employeeRelationshipDTOList);
		assertFalse(employeeRelationshipDTOList.isEmpty());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeService, times(1)).checkAccess(anyInt(), anyInt());
		verify(employeeService, times(1)).findById(anyInt());
		verify(employeeService, times(1)).findCurrentRelationshipsBySourceEmployee(userMock,
				RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);
		verify(employeeRelationshipDTOConverter, times(1)).convert(any(EmployeeRelationship.class));

		EmployeeRelationshipDTO employeeRelationshipDTO = employeeRelationshipDTOList.get(0);
		assertNotNull(employeeRelationshipDTO.getReference());
		assertNotNull(employeeRelationshipDTO.getRelationship());
		assertNotNull(employeeRelationshipDTO.getStartDate());
		assertNotNull(employeeRelationshipDTO.getEndDate());

		EmployeeDTO reference = employeeRelationshipDTO.getReference();
		entityDTOComparator.assertEqualsEmployee(mockMentor(), reference);
	}

	@Test
	public void employeesEmployeeIdRelationshipsGet_emptyList() throws Exception {
		when(employeeService.findById(userMock.getId())).thenReturn(Optional.of(userMock));
		doReturn(Stream.empty())
				.when(employeeService).findCurrentRelationshipsBySourceEmployee(userMock,
				RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);

		MvcResult result = mockMvc.perform(
				get(String.format("%s/%d/relationships",
						EMPLOYEES_URL,
						this.userMock.getId()))
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		List<EmployeeRelationshipDTO> employeeRelationshipDTOList = Arrays.asList(mapper.readValue(result
				.getResponse().getContentAsString(), EmployeeRelationshipDTO[].class));

		assertNotNull(employeeRelationshipDTOList);
		assertTrue(employeeRelationshipDTOList.isEmpty());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeService, times(1)).checkAccess(anyInt(), anyInt());
		verify(employeeService, times(1)).findById(anyInt());
		verify(employeeService, times(1)).findCurrentRelationshipsBySourceEmployee(userMock,
				RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);
		verify(employeeRelationshipDTOConverter, never()).convert(any(EmployeeRelationship.class));
	}

	@Test
	public void employeesEmployeeIdRelationshipsGet_requestedNotExists() throws Exception {
    	doThrow(EmployeeNotFoundException.class).when(employeeService).findById(anyInt());

		MvcResult result = mockMvc.perform(
				get(String.format("%s/%d/relationships",
						EMPLOYEES_URL,
						this.userMock.getId()))
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(),
				OperationResultDTO.class);

		assertNotNull(resultDTO);
		assertEquals(Constants.ERROR, resultDTO.getMessage());
		assertNull(resultDTO.getData());
		assertNull(resultDTO.getErrors());

		verify(employeeService, times(1)).getLoggedInUser();
		verify(employeeService, times(1)).checkAccess(anyInt(), anyInt());
		verify(employeeService, times(1)).findById(anyInt());
		verify(employeeService, never()).findCurrentRelationshipsBySourceEmployee(userMock,
				RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);
		verify(employeeRelationshipDTOConverter, never()).convert(any(EmployeeRelationship.class));
	}

	@Test
	public void employeesEmployeeIdRelationshipsGet_badRequest() throws Exception {
		MvcResult result = mockMvc.perform(
				get(String.format("%s/null/relationships",
						EMPLOYEES_URL))
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		assertTrue(StringUtils.isEmpty(response));

		verify(employeeService, never()).getLoggedInUser();
		verify(employeeService, never()).checkAccess(anyInt(), anyInt());
		verify(employeeService, never()).findById(anyInt());
		verify(employeeService, never()).findCurrentRelationshipsBySourceEmployee(userMock,
				RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);
		verify(employeeRelationshipDTOConverter, never()).convert(any(EmployeeRelationship.class));
	}

	private EmployeeRelationship mockEmployeeRelationship() {
    	return new EmployeeRelationshipBuilder()
				.sourceEmployee(userMock)
				.targetEmployee(mockMentor())
				.relationship(mockRelationship())
				.startDate(LocalDateTime.now()).build();
	}

	private EmployeeDTO mockEmployeeDTO() {
		return new EmployeeDTOBuilder()
				.id(userMock.getId())
				.firstName(userMock.getFirstName())
				.lastName(userMock.getLastName())
				.gftIdentifier(userMock.getGftIdentifier())
				.email(userMock.getEmail())
				.applicationRole(mockApplicationRoleDTO())
				.jobLevel(mockJobLevelDTO())
				.isAdmin(false)
				.isMentor(false)
				.isPeer(false)
				.build();
	}

	private EmployeeDTO mockMentorDTO() {
    	return new EmployeeDTOBuilder()
				.id(mockMentor().getId())
				.firstName(mockMentor().getFirstName())
				.lastName(mockMentor().getLastName())
				.gftIdentifier(mockMentor().getGftIdentifier())
				.email(mockMentor().getEmail())
				.applicationRole(mockApplicationRoleDTO())
				.jobLevel(mockJobLevelDTO())
				.isAdmin(false)
				.isMentor(true)
				.isPeer(false)
				.build();
	}

	private EmployeeRelationshipDTO mockEmployeeRelationshipDTO() {
		return new EmployeeRelationshipDTOBuilder()
				.reference(mockMentorDTO())
				.relationship(mockRelationshipDTO())
				.startDate(OffsetDateTime.now())
				.endDate(OffsetDateTime.now())
				.build();
	}

	private ApplicationRoleDTO mockApplicationRoleDTO() {
    	return new ApplicationRoleDTOBuilder()
				.id(1)
				.name("Application Role")
				.description("Application Role Description")
				.build();
	}

	private JobLevelDTO mockJobLevelDTO() {
    	return new JobLevelDTOBuilder()
				.id(1)
				.name("Job Level")
				.description("Job Level Description")
				.expertise("Expertise")
				.jobFamily(mockJobFamilyDTO())
				.build();
	}

	private JobFamilyDTO mockJobFamilyDTO() {
    	return new JobFamilyDTOBuilder()
				.id(1)
				.name("Job Family")
				.description("Job Family Description")
				.build();
	}

	private RelationshipDTO mockRelationshipDTO() {
    	return new RelationshipDTOBuilder()
				.name(RelationshipName.OTHER.name())
				.description("Mock Relationship")
				.build();
	}

	private Relationship mockRelationship() {
		return new RelationshipBuilder()
				.name(RelationshipName.OTHER.name())
				.description("Mock Relationship")
				.build();
	}

	private Employee mockMentor() {
		return new EmployeeBuilder()
				.id(2)
				.email("mock@gft.com")
				.firstName("Mocker")
				.lastName("Mockoo")
				.gftIdentifier("MOCK")
				.jobLevel(jobLevelMock)
				.applicationRole(applicationRoleMock)
				.buildMock();
	}
}
