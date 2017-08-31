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
import com.gft.employeeappraisal.converter.employee.EmployeeDTOConverter;
import com.gft.employeeappraisal.exception.NotFoundException;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static org.junit.Assert.*;
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
public class EmployeesControllerMentorTest extends BaseControllerTest {

    private static final String EMPLOYEES_URL = "/employees";
    private static EmployeeDTO mockEmployeeDTO;
    private static EmployeeDTO mockMentorDTO;
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityDTOComparator entityDTOComparator;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    @SuppressWarnings("unused")
    private EmployeeDTOConverter employeeDTOConverter;
    @MockBean(reset = MockReset.AFTER)
    private EmployeeService employeeService;
    @MockBean(reset = MockReset.AFTER)
    private EmployeeRelationshipService employeeRelationshipService;

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

    /**
     * Tests {@link EmployeesController#employeesIdMentorGet(Integer)}
     *
     * @throws Exception
     */
    @Test
    public void employeesIdMentorGet_Successful() throws Exception {
        // Set up
        Employee mockMentor = mockMentor();
        when(employeeService.getCurrentMentorById(anyInt())).thenReturn(mockMentor);

        // Execution
        MvcResult result = mockMvc.perform(get(String.format(
                "%s/%d/mentor",
                EMPLOYEES_URL,
                mockEmployeeDTO.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        // Verification
        verify(employeeService, times(1)).getCurrentMentorById(anyInt());
        EmployeeDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(),
                EmployeeDTO.class);
        entityDTOComparator.assertEqualsEmployee(mockMentor, resultDTO);
    }

    /**
     * Tessts {@link EmployeesController#employeesIdMentorGet(Integer)}
     *
     * @throws Exception
     */
    @Test
    public void employeesIdMentorGet_MentorNotFound() throws Exception {
        // Set up
        when(employeeService.getCurrentMentorById(anyInt())).thenThrow(NotFoundException.class);

        // Execution
        MvcResult result = mockMvc.perform(get(String.format("%s/%d/mentor", EMPLOYEES_URL, mockEmployeeDTO.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();
        EmployeeDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), EmployeeDTO.class);

        // Verification
        assertNotNull(resultDTO);
        assertNull(resultDTO.getFirstName());
        assertNull(resultDTO.getLastName());
        assertNull(resultDTO.getGftIdentifier());
        assertNull(resultDTO.getEmail());
        assertNull(resultDTO.getApplicationRole());
        assertNull(resultDTO.getJobLevel());
        verify(employeeService, times(1)).getCurrentMentorById(anyInt());
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

    /**
     * Tests {@link EmployeesController#employeesIdMentorPut(Integer, EmployeeDTO)}
     *
     * @throws Exception
     */
    @Test
    public void employeesIdMentorPut() throws Exception {
        // Set up
        when(employeeService.getById(mockEmployeeDTO.getId())).thenReturn(mockEmployee());
        when(employeeService.getById(mockMentorDTO.getId())).thenReturn(mockMentor());
        doNothing().when(employeeRelationshipService).changeMentor(any(Employee.class), any(Employee.class));

        // Execution
        MvcResult result = mockMvc.perform(put(String.format("%s/%d/mentor", EMPLOYEES_URL, mockEmployeeDTO.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockMentorDTO)))
                .andExpect(status().isOk()).andReturn();
        OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        // Verification
        assertEquals(Constants.SUCCESS, resultDTO.getMessage());
        assertNotNull(resultDTO);
        assertNull(resultDTO.getData());
        assertNull(resultDTO.getErrors());
        verify(employeeService, times(2)).getById(anyInt());
        verify(employeeRelationshipService, times(1)).changeMentor(any(Employee.class), any(Employee.class));
    }

    /**
     * Tests {@link EmployeesController#employeesIdMentorPut(Integer, EmployeeDTO)}
     *
     * @throws Exception
     */
    @Test
    public void employeesIdMentorPut_EmployeeNotFound() throws Exception {
        // Set up
        when(employeeService.getById(mockEmployeeDTO.getId())).thenThrow(NotFoundException.class);

        // Execution
        MvcResult result = mockMvc.perform(put(String.format(
                "%s/%d/mentor",
                EMPLOYEES_URL,
                mockEmployeeDTO.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockMentorDTO))
        ).andExpect(status().isNotFound()).andReturn();
        OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        // Verification
        assertNotNull(resultDTO);
        assertEquals(Constants.ERROR, resultDTO.getMessage());
        assertNull(resultDTO.getErrors());
        verify(employeeService, times(1)).getById(anyInt());
        verify(employeeRelationshipService, never()).changeMentor(any(Employee.class), any(Employee.class));
    }

    /**
     * Tests {@link EmployeesController#employeesIdMentorPut(Integer, EmployeeDTO)}
     *
     * @throws Exception
     */
    @Test
    public void employeesIdMentorPut_MentorNotFound() throws Exception {
        // Set up
        Employee mockEmployee = mockEmployee();
        when(employeeService.getById(mockEmployeeDTO.getId())).thenReturn(mockEmployee);
        when(employeeService.getById(mockMentorDTO.getId())).thenThrow(NotFoundException.class);

        // Execution
        MvcResult result = mockMvc.perform(put(String.format("%s/%d/mentor", EMPLOYEES_URL, mockEmployeeDTO.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockMentorDTO))
        ).andExpect(status().isNotFound()).andReturn();
        OperationResultDTO resultDTO = mapper.readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        // Verification
        assertNotNull(resultDTO);
        assertEquals(Constants.ERROR, resultDTO.getMessage());
        assertNull(resultDTO.getErrors());
        verify(employeeService, times(2)).getById(anyInt());
        verify(employeeRelationshipService, never()).changeMentor(any(Employee.class), any(Employee.class));
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