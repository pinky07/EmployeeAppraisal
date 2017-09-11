package com.gft.employeeappraisal.controller.employees;

import com.gft.employeeappraisal.controller.BaseControllerTest;
import com.gft.employeeappraisal.controller.EmployeesController;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.helper.builder.model.ApplicationRoleBuilder;
import com.gft.employeeappraisal.helper.builder.model.EmployeeBuilder;
import com.gft.employeeappraisal.helper.builder.model.JobFamilyBuilder;
import com.gft.employeeappraisal.helper.builder.model.JobLevelBuilder;
import com.gft.employeeappraisal.model.*;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
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
                .email("user@gft.com")
                .firstName("John")
                .lastName("Doe")
                .gftIdentifier("JODO")
                .jobLevel(jobLevelMock)
                .applicationRole(applicationRoleMock)
                .build();

        when(employeeService.getLoggedInUser()).thenReturn(userMock);
    }

    /**
     * TODO: This test will need to be updated later since current functionality only returns employees if there
     * is a search term.
     */
    @Test
    public void employeesGet() throws Exception {
        when(employeeService.findPagedByFirstNameOrLastName(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(Stream.of(userMock));

        // Execution
        MvcResult result = mockMvc.perform(
                get(String.format("%s",
                        EMPLOYEES_URL))
                        .param("searchTerm", "searchTermExample")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<EmployeeDTO> employeeDTOList = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(),
                EmployeeDTO[].class));

        assertNotNull(employeeDTOList);
        assertFalse(employeeDTOList.isEmpty());

        verify(employeeService, times(1))
                .findPagedByFirstNameOrLastName(anyString(), anyString(), anyInt(), anyInt());

        entityDTOComparator.assertEqualsEmployee(userMock, employeeDTOList.get(0));
    }

    /**
     * TODO: This test will need to be updated later since current functionality only returns employees if there
     * is a search term.
     */
    @Test
    public void employeesGet_emptyList() throws Exception {
        when(employeeService.findPagedByFirstNameOrLastName(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(Stream.empty());

        // Execution
        MvcResult result = mockMvc.perform(
                get(String.format("%s",
                        EMPLOYEES_URL))
                        .param("searchTerm", "searchTerm")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<EmployeeDTO> employeeDTOList = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(),
                EmployeeDTO[].class));

        assertNotNull(employeeDTOList);
        assertTrue(employeeDTOList.isEmpty());

        verify(employeeService, times(1))
                .findPagedByFirstNameOrLastName(anyString(), anyString(), anyInt(), anyInt());
    }

    /**
     * Tests {@link EmployeesController#employeesIdGet(Integer)}
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(USER_EMAIL)
    public void employeesIdGet_Successful() throws Exception {
        // Set-up
        when(employeeService.getById(userMock.getId())).thenReturn(userMock);

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
        EmployeeDTO employeeDTO = objectMapper.readValue(resultString, EmployeeDTO.class);

        // Verification
        verify(securityService, times(1)).canReadEmployee(any(Employee.class), any(Employee.class));
        verify(employeeService, times(1)).getById(userMock.getId());
        entityDTOComparator.assertEqualsEmployee(userMock, employeeDTO);
    }

    /**
     * Tests {@link EmployeesController#employeesIdGet(Integer)}
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(USER_EMAIL)
    public void employeesIdGet_NotFound() throws Exception {
        // Set-up
        when(employeeService.getById(userMock.getId())).thenThrow(new NotFoundException("Employee Not Found"));

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
        OperationResultDTO operationResultDTO = objectMapper.readValue(resultString, OperationResultDTO.class);

        // Verification
        verify(employeeService, times(1)).getById(userMock.getId());
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

        verify(securityService, never()).canReadEmployee(any(Employee.class), any(Employee.class));

        verify(employeeService, never()).findById(userMock.getId());

        assertTrue(StringUtils.isEmpty(resultString));
    }
}
