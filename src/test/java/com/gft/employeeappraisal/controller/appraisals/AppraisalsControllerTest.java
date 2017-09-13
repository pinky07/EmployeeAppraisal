package com.gft.employeeappraisal.controller.appraisals;

import com.gft.employeeappraisal.controller.AppraisalsController;
import com.gft.employeeappraisal.controller.BaseControllerTest;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.helper.builder.model.*;
import com.gft.employeeappraisal.model.*;
import com.gft.swagger.employees.model.AppraisalDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

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
 * Integration test class for the {@link AppraisalsController} controller class, GET endpoints.
 *
 * @author Manuel Yepez
 */
@RunWith(SpringRunner.class)
public class AppraisalsControllerTest extends BaseControllerTest {

    private static final String EMPLOYEES_URL = "/employees";
    private static final String APPRAISAL_URL = "/appraisals";

    private static final String EMPLOYEES_ID_APPRAISALS_URL = "/employees/%d/appraisals";
    private static final String EMPLOYEES_ID_APPRAISALS_ID_URL = "/employees/%d/appraisals/%d";
    private static final String ME_APPRAISALS_URL = "/me/appraisals";
    private static final String ME_APPRAISALS_ID_URL = "/me/appraisals/%d";

    // Test Fixtures
    private Employee user;
    private Appraisal appraisal;
    private EmployeeEvaluationForm employeeEvaluationForm;

    @Before
    public void sharedSetUp() {
        // Mock the user

        JobFamily jobFamilyMock = new JobFamilyBuilder()
                .buildWithDefaults();

        JobLevel jobLevel = new JobLevelBuilder()
                .jobFamily(jobFamilyMock)
                .buildWithDefaults();

        ApplicationRole applicationRole = new ApplicationRoleBuilder()
                .buildWithDefaults();

        this.user = new EmployeeBuilder()
                .jobLevel(jobLevel)
                .applicationRole(applicationRole)
                .buildWithDefaults();

        this.appraisal = new AppraisalBuilder()
                .buildWithDefaults();

        AppraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate = new AppraisalXEvaluationFormTemplateBuilder()
                .appraisal(this.appraisal)
                .buildWithDefaults();

        this.employeeEvaluationForm = new EmployeeEvaluationFormBuilder()
                .appraisalXEvaluationForm(appraisalXEvaluationFormTemplate)
                .buildWithDefaults();

        when(employeeService.getLoggedInUser()).thenReturn(this.user);
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsGet(Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsGet_Successful() throws Exception {
        // Set up
        when(this.employeeService.getById(this.user.getId())).thenReturn(this.user);
        when(this.employeeEvaluationFormService.findSelfByEmployee(this.user)).thenReturn(Stream.of(employeeEvaluationForm));

        // Execution
        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_URL,
                        this.user.getId()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<AppraisalDTO> appraisalDTOList = Arrays.asList(objectMapper.readValue(result
                .getResponse().getContentAsString(), AppraisalDTO[].class));

        // Verification
        assertNotNull(appraisalDTOList);
        assertEquals(appraisalDTOList.size(), 1);
        AppraisalDTO appraisalDTO = appraisalDTOList.get(0);
        assertEquals(this.appraisal.getName(), appraisalDTO.getName());
        verify(this.employeeService, times(1)).getById(anyInt());
        verify(this.employeeEvaluationFormService, times(1)).findSelfByEmployee(any(Employee.class));
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsGet(Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsGet_Empty() throws Exception {
        // Set up
        when(this.employeeService.getById(this.user.getId())).thenReturn(this.user);
        when(this.employeeEvaluationFormService.findSelfByEmployee(this.user)).thenReturn(Stream.empty());

        // Execution
        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_URL,
                        this.user.getId()))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<AppraisalDTO> appraisalDTOList = Arrays.asList(objectMapper.readValue(result
                .getResponse().getContentAsString(), AppraisalDTO[].class));

        // Verification
        assertNotNull(appraisalDTOList);
        assertTrue(appraisalDTOList.isEmpty());
        verify(this.employeeService, times(1)).getById(anyInt());
        verify(this.employeeEvaluationFormService, times(1)).findSelfByEmployee(any(Employee.class));
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsGet(Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsGet_EmployeeNotFound() throws Exception {
        when(employeeService.getById(this.user.getId())).thenThrow(new NotFoundException("Employee Not Found"));

        MvcResult result = mockMvc.perform(
                get(String.format("%s/%d%s",
                        EMPLOYEES_URL,
                        this.user.getId(),
                        APPRAISAL_URL))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO resultDTO = objectMapper.readValue(result
                .getResponse().getContentAsString(), OperationResultDTO.class);

        assertNotNull(resultDTO);
        assertEquals(resultDTO.getMessage(), Constants.ERROR);

        verify(this.employeeService, times(1)).getById(anyInt());
        verify(this.appraisalService, never()).findEmployeeAppraisals(any(Employee.class));
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsGet(Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsGet_BadRequest() throws Exception {

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

        verify(employeeService, never()).getById(anyInt());
        verify(appraisalService, never()).findEmployeeAppraisals(any(Employee.class));
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsIdFormsGet(Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsIdFormsGet_Successful() throws Exception {
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsIdFormsIdGet(Integer, Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsIdFormsIdGet_Successful() throws Exception {
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsIdGet(Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsIdGet_Successful() throws Exception {
        // Set up
        when(this.employeeService.getById(this.user.getId())).thenReturn(this.user);
        when(this.appraisalService.getById(this.appraisal.getId())).thenReturn(this.appraisal);
        when(this.employeeEvaluationFormService.findSelfByEmployeeAndAppraisal(this.user, this.appraisal)).thenReturn(Optional.of(employeeEvaluationForm));

        // Execution
        MvcResult result = mockMvc.perform(get(String.format(EMPLOYEES_ID_APPRAISALS_ID_URL,
                this.user.getId(),
                this.appraisal.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        AppraisalDTO appraisalDTO = objectMapper.readValue(result.getResponse().getContentAsString(), AppraisalDTO.class);

        // Verification
        assertNotNull(appraisalDTO);
        assertEquals(this.appraisal.getName(), appraisalDTO.getName());
        verify(this.employeeService, times(1)).getById(anyInt());
        verify(this.appraisalService, times(1)).getById(anyInt());
        verify(this.employeeEvaluationFormService, times(1)).findSelfByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsIdGet(Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsIdGet_EmptyAppraisalDTO() throws Exception {
        // Set up
        when(this.employeeService.getById(this.user.getId())).thenReturn(this.user);
        when(this.appraisalService.getById(this.appraisal.getId())).thenReturn(this.appraisal);
        when(this.employeeEvaluationFormService.findSelfByEmployeeAndAppraisal(this.user, this.appraisal)).thenReturn(Optional.empty());

        // Execution
        MvcResult result = mockMvc.perform(get(String.format(EMPLOYEES_ID_APPRAISALS_ID_URL,
                this.user.getId(),
                this.appraisal.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        OperationResultDTO operationResultDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                OperationResultDTO.class);

        // Verification
        assertNotNull(operationResultDTO);
        assertEquals(Constants.ERROR, operationResultDTO.getMessage());
        verify(this.employeeService, times(1)).getById(anyInt());
        verify(this.appraisalService, times(1)).getById(anyInt());
        verify(this.employeeEvaluationFormService, times(1)).findSelfByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsIdGet(Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsIdGet_EmployeeNotFound() throws Exception {
        // Set up
        when(this.employeeService.getById(this.user.getId())).thenThrow(new NotFoundException("Employee Not Found"));
        when(this.appraisalService.getById(this.appraisal.getId())).thenReturn(this.appraisal);
        when(this.employeeEvaluationFormService.findSelfByEmployeeAndAppraisal(this.user, appraisal)).thenReturn(Optional.empty());

        // Execution
        MvcResult result = mockMvc.perform(get(String.format(
                EMPLOYEES_ID_APPRAISALS_ID_URL,
                this.user.getId(),
                appraisal.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        OperationResultDTO operationResultDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                OperationResultDTO.class);

        // Verification
        assertNotNull(operationResultDTO);
        assertEquals(Constants.ERROR, operationResultDTO.getMessage());
        verify(this.employeeService, times(1)).getById(anyInt());
        verify(this.appraisalService, never()).getById(anyInt());
        verify(this.employeeEvaluationFormService, never()).findSelfByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsIdGet(Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsIdGet_AppraisalNotFound() throws Exception {
        // Set up
        when(this.employeeService.getById(this.user.getId())).thenReturn(this.user);
        when(this.appraisalService.getById(this.appraisal.getId())).thenThrow(new NotFoundException("Appraisal Not Found"));
        when(this.employeeEvaluationFormService.findSelfByEmployeeAndAppraisal(this.user, appraisal)).thenReturn(Optional.empty());

        // Execution
        MvcResult result = mockMvc.perform(get(String.format(
                EMPLOYEES_ID_APPRAISALS_ID_URL,
                this.user.getId(),
                appraisal.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        OperationResultDTO operationResultDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                OperationResultDTO.class);

        // Verification
        assertNotNull(operationResultDTO);
        assertEquals(Constants.ERROR, operationResultDTO.getMessage());
        verify(this.employeeService, times(1)).getById(anyInt());
        verify(this.appraisalService, times(1)).getById(anyInt());
        verify(this.employeeEvaluationFormService, never()).findSelfByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsIdGet(Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsIdGet_BadRequest() throws Exception {
        // Execution
        MvcResult result = mockMvc.perform(get(String.format(
                EMPLOYEES_ID_APPRAISALS_ID_URL,
                this.user.getId(),
                null))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();

        // Verification
        assertTrue(StringUtils.isEmpty(resultString));
        verify(this.employeeService, never()).getById(anyInt());
        verify(this.appraisalService, never()).getById(anyInt());
        verify(this.employeeEvaluationFormService, never()).findSelfByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
    }

    /**
     * Tests {@link AppraisalsController#meAppraisalsGet()}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void meAppraisalsGet_Successful() throws Exception {
        // Set up
        when(this.employeeService.getById(this.user.getId())).thenReturn(this.user);
        when(this.employeeEvaluationFormService.findSelfByEmployee(this.user))
                .thenReturn(Stream.of(employeeEvaluationForm));

        // Execution
        MvcResult result = mockMvc.perform(get(
                ME_APPRAISALS_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<AppraisalDTO> appraisalDTOList = Arrays.asList(objectMapper.readValue(result
                .getResponse().getContentAsString(), AppraisalDTO[].class));

        // Verification
        assertNotNull(appraisalDTOList);
        assertEquals(appraisalDTOList.size(), 1);
        AppraisalDTO appraisalDTO = appraisalDTOList.get(0);
        assertEquals(this.appraisal.getName(), appraisalDTO.getName());
        verify(this.employeeService, times(1)).getLoggedInUser();
        verify(this.employeeEvaluationFormService, times(1)).findSelfByEmployee(any(Employee.class));
    }

    /**
     * Tests {@link AppraisalsController#meAppraisalsGet()}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void meAppraisalsGet_Empty() throws Exception {
        // Set up
        when(this.employeeService.getById(this.user.getId())).thenReturn(this.user);
        when(this.employeeEvaluationFormService.findSelfByEmployee(this.user))
                .thenReturn(Stream.empty());

        // Execution
        MvcResult result = mockMvc.perform(get(
                ME_APPRAISALS_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<AppraisalDTO> appraisalDTOList = Arrays.asList(objectMapper.readValue(result
                .getResponse().getContentAsString(), AppraisalDTO[].class));

        // Verification
        assertNotNull(appraisalDTOList);
        assertTrue(appraisalDTOList.isEmpty());
        verify(this.employeeService, times(1)).getLoggedInUser();
        verify(this.employeeEvaluationFormService, times(1)).findSelfByEmployee(any(Employee.class));
    }

    /**
     * Tests {@link AppraisalsController#meAppraisalsIdFormsGet(Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void meAppraisalsIdFormsGet_Successful() throws Exception {
    }

    /**
     * Tests {@link AppraisalsController#meAppraisalsIdFormsIdGet(Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void meAppraisalsIdFormsIdGet_Successful() throws Exception {
    }

    /**
     * Tests {@link AppraisalsController#meAppraisalsIdGet(Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void meAppraisalsIdGet_Successful() throws Exception {
        // Set up
        when(this.employeeService.getById(user.getId())).thenReturn(user);
        when(this.appraisalService.getById(appraisal.getId())).thenReturn(appraisal);
        when(this.employeeEvaluationFormService.findSelfByEmployeeAndAppraisal(user, appraisal)).thenReturn(Optional.of(employeeEvaluationForm));

        // Execution
        MvcResult result = mockMvc.perform(get(String.format(
                ME_APPRAISALS_ID_URL,
                appraisal.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        AppraisalDTO appraisalDTO = objectMapper.readValue(result.getResponse().getContentAsString(), AppraisalDTO.class);

        // Verification
        assertNotNull(appraisalDTO);
        assertEquals(appraisal.getName(), appraisalDTO.getName());
        verify(this.employeeService, times(1)).getLoggedInUser();
        verify(this.appraisalService, times(1)).getById(anyInt());
        verify(this.employeeEvaluationFormService, times(1)).findSelfByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
    }

    /**
     * Tests {@link AppraisalsController#meAppraisalsIdGet(Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void meAppraisalsIdGet_Empty() throws Exception {
        // Set up
        when(this.employeeService.getById(user.getId())).thenReturn(user);
        when(this.appraisalService.getById(appraisal.getId())).thenReturn(appraisal);
        when(this.employeeEvaluationFormService.findSelfByEmployeeAndAppraisal(user, appraisal)).thenReturn(Optional.empty());

        // Execution
        MvcResult result = mockMvc.perform(get(String.format(
                ME_APPRAISALS_ID_URL,
                appraisal.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        AppraisalDTO appraisalDTO = objectMapper.readValue(result.getResponse().getContentAsString(), AppraisalDTO.class);

        // Verification
        assertNotNull(appraisalDTO);
        assertNull(appraisalDTO.getName());
        verify(this.employeeService, times(1)).getLoggedInUser();
        verify(this.appraisalService, times(1)).getById(anyInt());
        verify(this.employeeEvaluationFormService, times(1)).findSelfByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
    }

    /**
     * Tests {@link AppraisalsController#meAppraisalsIdGet(Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void meAppraisalsIdGet_AppraisalNotFound() throws Exception {
        // Set up
        when(this.employeeService.getById(user.getId())).thenReturn(user);
        when(this.appraisalService.getById(appraisal.getId())).thenThrow(new NotFoundException("Appraisal Not Found"));
        when(this.employeeEvaluationFormService.findSelfByEmployeeAndAppraisal(user, appraisal)).thenReturn(Optional.empty());

        // Execution
        MvcResult result = mockMvc.perform(get(String.format(
                ME_APPRAISALS_ID_URL,
                appraisal.getId()))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        AppraisalDTO appraisalDTO = objectMapper.readValue(result.getResponse().getContentAsString(), AppraisalDTO.class);

        // Verification
        assertNotNull(appraisalDTO);
        assertNull(appraisalDTO.getName());
        verify(this.employeeService, times(1)).getLoggedInUser();
        verify(this.appraisalService, times(1)).getById(anyInt());
        verify(this.employeeEvaluationFormService, never()).findSelfByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
    }

    /**
     * Tests {@link AppraisalsController#meAppraisalsIdGet(Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void meAppraisalsIdGet_BadRequest() throws Exception {
        // Execution
        MvcResult result = mockMvc.perform(get(String.format(
                ME_APPRAISALS_ID_URL,
                (Object) null))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String response = result.getResponse().getContentAsString();

        // Verification
        assertTrue(StringUtils.isEmpty(response));
        verify(this.employeeService, never()).getLoggedInUser();
        verify(this.appraisalService, never()).getById(anyInt());
        verify(this.employeeEvaluationFormService, never()).findSelfByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
    }
}