package com.gft.employeeappraisal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.employeeappraisal.builder.model.*;
import com.gft.employeeappraisal.converter.employee.EmployeeDTOConverter;
import com.gft.employeeappraisal.converter.employeerelationship.EmployeeRelationshipDTOConverter;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.employeeappraisal.service.SecurityService;
import com.gft.employeeappraisal.service.ValidationService;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;
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
public class EmployeesControllerGetTest extends BaseControllerTest {

    private static final String EMPLOYEES_URL = "/employees";
    private static final String USER_EMAIL = "user@gft.com";

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    @SuppressWarnings("unused")
    private EmployeeRelationshipService employeeRelationshipService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityDTOComparator entityDTOComparator;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private ValidationService validationService;

    @Autowired
    @SuppressWarnings("unused")
    private EmployeeDTOConverter employeeDTOConverter;

    @Autowired
    @SuppressWarnings("unused")
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
                .build();

        jobLevelMock = new JobLevelBuilder()
                .id(1)
                .name("Job Level")
                .description("Job Level Description")
                .expertise("Expertise")
                .jobFamily(jobFamilyMock)
                .build();

        applicationRoleMock = new ApplicationRoleBuilder()
                .id(1)
                .name("Application Role")
                .description("Application Role Description")
                .build();

        userMock = new EmployeeBuilder()
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
    @WithMockUser(USER_EMAIL)
    public void employeesEmployeeIdGet_Successful() throws Exception {

        // Set-up

        when(employeeService.findById(userMock.getId())).thenReturn(Optional.of(userMock));

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

        verify(securityService, times(1)).canReadEmployee(anyInt(), anyInt());
        verify(employeeService, times(1)).findById(userMock.getId());

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

        verify(securityService, times(1)).canReadEmployee(anyInt(), anyInt());

        verify(employeeService, times(1)).findById(userMock.getId());

        assertEquals(operationResultDTO.getMessage(), Constants.ERROR);
    }

    @Test
    @WithMockUser(USER_EMAIL)
    public void employeesEmployeeIdGet_badRequest() throws Exception {

        // Execution

        MvcResult result = mockMvc.perform(get(String.format("%s/%s", EMPLOYEES_URL, "null"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();

        String resultString = result.getResponse().getContentAsString();

        // Verification

        verify(securityService, never()).canReadEmployee(anyInt(), anyInt());

        verify(employeeService, never()).findById(userMock.getId());

        assertTrue(StringUtils.isEmpty(resultString));
    }

    @Test
    public void employeesEmployeeIdRelationshipsGet() throws Exception {
        when(employeeService.findById(userMock.getId())).thenReturn(Optional.of(userMock));
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

        verify(securityService, times(1)).canReadEmployee(anyInt(), anyInt());

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).findById(anyInt());
        verify(employeeService, times(1)).findCurrentRelationshipsBySourceEmployee(userMock,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);

        EmployeeRelationshipDTO employeeRelationshipDTO = employeeRelationshipDTOList.get(0);
        assertNotNull(employeeRelationshipDTO.getReferred());
        assertNotNull(employeeRelationshipDTO.getRelationship());
        assertNotNull(employeeRelationshipDTO.getStartDate());
        assertNull(employeeRelationshipDTO.getEndDate());

        EmployeeDTO reference = employeeRelationshipDTO.getReferred();
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

        verify(securityService, times(1)).canReadEmployee(anyInt(), anyInt());

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).findById(anyInt());
        verify(employeeService, times(1)).findCurrentRelationshipsBySourceEmployee(userMock,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);
    }

    @Test
    public void employeesEmployeeIdRelationshipsGet_requestedNotExists() throws Exception {
        doThrow(NotFoundException.class).when(employeeService).findById(anyInt());

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

        verify(securityService, times(1)).canReadEmployee(anyInt(), anyInt());

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).findById(anyInt());
        verify(employeeService, never()).findCurrentRelationshipsBySourceEmployee(userMock,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);
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

        verify(securityService, never()).canReadEmployee(anyInt(), anyInt());

        verify(employeeService, never()).getLoggedInUser();
        verify(employeeService, never()).findById(anyInt());
        verify(employeeService, never()).findCurrentRelationshipsBySourceEmployee(userMock,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);
    }

    private EmployeeRelationship mockEmployeeRelationship() {
        return new EmployeeRelationshipBuilder()
                .sourceEmployee(userMock)
                .targetEmployee(mockMentor())
                .relationship(mockRelationship())
                .startDate(LocalDateTime.now()).build();
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
                .build();
    }
}
