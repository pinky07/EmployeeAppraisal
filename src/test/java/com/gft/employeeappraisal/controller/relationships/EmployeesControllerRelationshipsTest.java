package com.gft.employeeappraisal.controller.relationships;

import com.gft.employeeappraisal.builder.model.*;
import com.gft.employeeappraisal.controller.BaseControllerTest;
import com.gft.employeeappraisal.controller.EmployeesController;
import com.gft.employeeappraisal.controller.EntityDTOComparator;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.employeeappraisal.service.RelationshipService;
import com.gft.employeeappraisal.service.SecurityService;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import com.gft.swagger.employees.model.RelationshipDTO;
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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test class for the {@link EmployeesController} controller class, Relationship endpoints.
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class EmployeesControllerRelationshipsTest extends BaseControllerTest {

    private static final String EMPLOYEES_URL = "/employees";
    private static final String RELATIONSHIP_URL = "/relationships";

    @MockBean
    private EmployeeService employeeService;

    @MockBean(reset = MockReset.AFTER)
    @SuppressWarnings("unused")
    private EmployeeRelationshipService employeeRelationshipService;

    @MockBean(reset = MockReset.AFTER)
    private RelationshipService relationshipService;

    @MockBean(reset = MockReset.AFTER)
    private SecurityService securityService;

    @Autowired
    private EntityDTOComparator entityDTOComparator;

    private Employee userMock;
    private Employee mentorMock;


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
                .email("user@gft.com")
                .firstName("John")
                .lastName("Doe")
                .gftIdentifier("JODO")
                .jobLevel(jobLevelMock)
                .applicationRole(applicationRoleMock)
                .build();

        mentorMock = new EmployeeBuilder()
                .id(2)
                .email("mock@gft.com")
                .firstName("Mocker")
                .lastName("Mockoo")
                .gftIdentifier("MOCK")
                .jobLevel(jobLevelMock)
                .applicationRole(applicationRoleMock)
                .build();
    }

    /**
     * Tests {@link EmployeesController#employeesIdRelationshipsGet(Integer, List, String, String, Boolean)}
     *
     * @throws Exception
     */
    @Test
    public void employeesIdRelationshipsGet_Successful() throws Exception {
        // Set up
        when(employeeService.getById(userMock.getId())).thenReturn(userMock);
        doReturn(Stream.of(testEmployeeRelationship(false)))
                .when(employeeService).findCurrentRelationshipsBySourceEmployee(userMock,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);

        // Execution
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

        // Verification
        assertNotNull(employeeRelationshipDTOList);
        assertFalse(employeeRelationshipDTOList.isEmpty());
        verify(securityService, times(1)).canReadEmployee(any(Employee.class), any(Employee.class));
        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(employeeService, times(1)).findCurrentRelationshipsBySourceEmployee(userMock,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);
        EmployeeRelationshipDTO employeeRelationshipDTO = employeeRelationshipDTOList.get(0);
        assertNotNull(employeeRelationshipDTO.getReferred());
        assertNotNull(employeeRelationshipDTO.getRelationship());
        assertNotNull(employeeRelationshipDTO.getStartDate());
        assertNull(employeeRelationshipDTO.getEndDate());
        EmployeeDTO reference = employeeRelationshipDTO.getReferred();
        entityDTOComparator.assertEqualsEmployee(mentorMock, reference);
    }

    /**
     * Tests {@link EmployeesController#employeesIdRelationshipsGet(Integer, List, String, String, Boolean)}
     *
     * @throws Exception
     */
    @Test
    public void employeesIdRelationshipsGet_emptyList() throws Exception {
        // set up
        when(employeeService.getById(userMock.getId())).thenReturn(userMock);
        doReturn(Stream.empty())
                .when(employeeService).findCurrentRelationshipsBySourceEmployee(userMock,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);

        // Execution
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

        // Verification
        assertNotNull(employeeRelationshipDTOList);
        assertTrue(employeeRelationshipDTOList.isEmpty());
        verify(securityService, times(1)).canReadEmployee(any(Employee.class), any(Employee.class));
        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(employeeService, times(1)).findCurrentRelationshipsBySourceEmployee(userMock,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);
    }

    /**
     * Tests {@link EmployeesController#employeesIdRelationshipsGet(Integer, List, String, String, Boolean)}
     *
     * @throws Exception
     */
    @Test
    public void employeesIdRelationshipsGet_requestedNotExists() throws Exception {
        // Set up
        when(employeeService.getById(anyInt())).thenThrow(NotFoundException.class);

        // Execution
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

        // Verification
        assertNotNull(resultDTO);
        assertEquals(Constants.ERROR, resultDTO.getMessage());
        assertNull(resultDTO.getData());
        assertNull(resultDTO.getErrors());
        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(securityService, never()).canReadEmployee(any(Employee.class), any(Employee.class));
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

        verify(securityService, never()).canReadEmployee(any(Employee.class), any(Employee.class));

        verify(employeeService, never()).getLoggedInUser();
        verify(employeeService, never()).findById(anyInt());
        verify(employeeService, never()).findCurrentRelationshipsBySourceEmployee(userMock,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER);
    }

    @Test
    public void relationshipsGet() throws Exception {
        when(relationshipService.findRelationshipsByNames(RelationshipName.LEAD,
                RelationshipName.PEER,
                RelationshipName.OTHER))
                .thenReturn(Stream.of(testRelationship()));

        MvcResult result = mockMvc.perform(
                get(String.format("%s/",
                        RELATIONSHIP_URL))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<RelationshipDTO> relationshipDTOList = Arrays.asList(mapper.readValue(result
                .getResponse().getContentAsString(), RelationshipDTO[].class));

        assertNotNull(relationshipDTOList);
        assertFalse(relationshipDTOList.isEmpty());

        verify(relationshipService, times(1))
                .findRelationshipsByNames(RelationshipName.LEAD,
                        RelationshipName.PEER,
                        RelationshipName.OTHER);

        assertEquals(testRelationship().getDescription(), relationshipDTOList.get(0).getDescription());
    }

    @Test
    public void relationshipsGet_emptyList() throws Exception {
        when(relationshipService.findRelationshipsByNames(RelationshipName.LEAD,
                RelationshipName.PEER,
                RelationshipName.OTHER))
                .thenReturn(Stream.empty());

        MvcResult result = mockMvc.perform(
                get(String.format("%s/",
                        RELATIONSHIP_URL))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<RelationshipDTO> relationshipDTOList = Arrays.asList(mapper.readValue(result
                .getResponse().getContentAsString(), RelationshipDTO[].class));

        assertNotNull(relationshipDTOList);
        assertTrue(relationshipDTOList.isEmpty());

        verify(relationshipService, times(1))
                .findRelationshipsByNames(RelationshipName.LEAD,
                        RelationshipName.PEER,
                        RelationshipName.OTHER);
    }

    @Test
    public void relationshipsIdGet() throws Exception {
        when(relationshipService.findById(anyInt())).thenReturn(Optional.of(testRelationship()));

        MvcResult result = mockMvc.perform(
                get(String.format("%s/%d",
                        RELATIONSHIP_URL,
                        testRelationship().getId()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        RelationshipDTO relationshipDTO = mapper.readValue(result
                .getResponse().getContentAsString(), RelationshipDTO.class);

        assertNotNull(relationshipDTO);
        assertEquals(testRelationship().getDescription(), relationshipDTO.getDescription());

        verify(relationshipService, times(1)).findById(anyInt());
    }

    @Test
    public void relationshipsIdGet_notFound() throws Exception {
        when(relationshipService.findById(anyInt())).thenReturn(Optional.empty());

        MvcResult result = mockMvc.perform(
                get(String.format("%s/%d",
                        RELATIONSHIP_URL,
                        testRelationship().getId()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO operationResultDTO = mapper.readValue(result.getResponse().getContentAsString(),
                OperationResultDTO.class);
        assertNotNull(operationResultDTO);
        assertEquals(Constants.ERROR, operationResultDTO.getMessage());

        verify(relationshipService, times(1)).findById(anyInt());
    }

    @Test
    public void relationshipsIdGet_badRequest() throws Exception {
        MvcResult result = mockMvc.perform(
                get(String.format("%s/null",
                        RELATIONSHIP_URL))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        assertTrue(StringUtils.isEmpty(resultString));

        verify(relationshipService, never()).findById(anyInt());
    }

    @Test
    @WithMockUser
    public void employeesIdRelationshipsIdDelete() throws Exception {
        when(employeeService.getLoggedInUser()).thenReturn(mentorMock);
        when(employeeService.getById(anyInt())).thenReturn(userMock);
        when(employeeRelationshipService.getById(anyInt())).thenReturn(testEmployeeRelationship(false));
        when(employeeRelationshipService.endEmployeeRelationship(any(EmployeeRelationship.class)))
                .thenReturn(Optional.of(testEmployeeRelationship(true)));

        // Execution
        mockMvc.perform(
                delete(String.format("%s/%d%s/%d",
                        EMPLOYEES_URL,
                        this.userMock.getId(),
                        RELATIONSHIP_URL,
                        testEmployeeRelationship(false).getId()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(employeeRelationshipService, times(1)).getById(anyInt());
        verify(securityService, times(1))
                .canWriteEmployeeRelationship(any(Employee.class), any(Employee.class), any(Employee.class));
        verify(employeeRelationshipService, times(1))
                .endEmployeeRelationship(any(EmployeeRelationship.class));
    }

    private EmployeeRelationship testEmployeeRelationship(boolean withEndDate) {
        return new EmployeeRelationshipBuilder()
                .sourceEmployee(userMock)
                .targetEmployee(mentorMock)
                .relationship(testRelationship())
                .startDate(OffsetDateTime.now())
                .endDate(withEndDate ? OffsetDateTime.now() : null)
                .build();
    }

    private Relationship testRelationship() {
        return new RelationshipBuilder()
                .name(RelationshipName.OTHER.name())
                .description("Mock Relationship")
                .build();
    }
}