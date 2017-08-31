package com.gft.employeeappraisal.controller.employees;

import com.gft.employeeappraisal.builder.dto.ApplicationRoleDTOBuilder;
import com.gft.employeeappraisal.builder.dto.EmployeeDTOBuilder;
import com.gft.employeeappraisal.builder.dto.JobFamilyDTOBuilder;
import com.gft.employeeappraisal.builder.dto.JobLevelDTOBuilder;
import com.gft.employeeappraisal.builder.model.ApplicationRoleBuilder;
import com.gft.employeeappraisal.builder.model.EmployeeBuilder;
import com.gft.employeeappraisal.builder.model.JobFamilyBuilder;
import com.gft.employeeappraisal.builder.model.JobLevelBuilder;
import com.gft.employeeappraisal.controller.BaseControllerTest;
import com.gft.employeeappraisal.converter.employee.EmployeeDTOConverter;
import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.employeeappraisal.model.Constants;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.JobLevel;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.JobLevelDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class EmployeesControllerPostTest extends BaseControllerTest {

    private static final String EMPLOYEES_URL = "/employees";
    private static EmployeeDTO mockEmployeeDTO;

    @Autowired
    @SuppressWarnings("unused")
    private EmployeeDTOConverter employeeDTOConverter;

    @MockBean(reset = MockReset.AFTER)
    private EmployeeService employeeService;

    @MockBean(reset = MockReset.AFTER)
    @SuppressWarnings("unused")
    private EmployeeRelationshipService employeeRelationshipService;

    @BeforeClass
    public static void setUp() {
        mockEmployeeDTO = new EmployeeDTOBuilder()
                .firstName("Manuel")
                .lastName("Yepez")
                .gftIdentifier("MLYZ")
                .email("manuel.yepez@gft.com")
                .applicationRole(mockApplicationRoleDTO())
                .jobLevel(mockJobLevelDTO())
                .build();

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

    @Test
    public void employeesPost() throws Exception {
        when(employeeService.findByEmail(anyString())).thenReturn(Optional.empty());
        when(employeeService.saveAndFlush(any(Employee.class))).thenReturn(Optional.of(mockEmployee()));

        MvcResult result = mockMvc.perform(post(EMPLOYEES_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockEmployeeDTO))
        ).andExpect(status().isCreated()).andReturn();

        OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).findByEmail(anyString());
        verify(employeeService, times(1)).saveAndFlush(any(Employee.class));

        EmployeeDTO resultEmployeeDTO = mapper.convertValue(resultDTO.getData(), EmployeeDTO.class);
        assertEquals(Constants.SUCCESS, resultDTO.getMessage());
        assertEquals(resultEmployeeDTO.getFirstName(), mockEmployeeDTO.getFirstName());
        assertNull(resultDTO.getErrors());
    }

    @Test
    public void employeesPost_Exists() throws Exception {
        when(employeeService.findByEmail(anyString())).thenReturn(Optional.of(mockEmployee()));

        MvcResult result = mockMvc.perform(post(EMPLOYEES_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockEmployeeDTO))
        ).andExpect(status().isUnprocessableEntity()).andReturn();

        verify(employeeService, times(1)).findByEmail(anyString());
        verify(employeeService, never()).saveAndFlush(any(Employee.class));

        OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);
        assertEquals(Constants.ERROR, resultDTO.getMessage());
        assertNull(resultDTO.getData());
    }

    @Test
    public void employeesPost_BadRequest() throws Exception {
        EmployeeDTO badRequestEmployee = new EmployeeDTO();

        MvcResult result = mockMvc.perform(post(EMPLOYEES_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(badRequestEmployee))
        ).andExpect(status().isBadRequest()).andReturn();

        verify(employeeService, never()).findByEmail(anyString());
        verify(employeeService, never()).saveAndFlush(any(Employee.class));

        OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);
        assertNull(resultDTO.getData());
        assertFalse(resultDTO.getErrors().isEmpty());
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

    @SuppressWarnings("h2")
    private Employee mockEmployee() {
        return new EmployeeBuilder()
                .firstName("Manuel")
                .lastName("Yepez")
                .gftIdentifier("MLYZ")
                .email("manuel.yepez@gft.com")
                .applicationRole(mockApplicationRole().get())
                .jobLevel(mockJobLevel().get())
                .build();
    }
}
