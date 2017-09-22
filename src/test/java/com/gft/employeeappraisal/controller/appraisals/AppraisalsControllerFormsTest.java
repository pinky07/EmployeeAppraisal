package com.gft.employeeappraisal.controller.appraisals;

import com.gft.employeeappraisal.controller.AppraisalsController;
import com.gft.employeeappraisal.controller.BaseControllerTest;
import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.helper.builder.model.*;
import com.gft.employeeappraisal.model.*;
import com.gft.swagger.employees.model.EmployeeEvaluationFormDTO;
import com.gft.swagger.employees.model.EvaluationFormTemplateDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AppraisalsControllerFormsTest extends BaseControllerTest {

    private static final String EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL = "/employees/%d/appraisals/%d/forms";
    private static final String EMPLOYEES_ID_APPRAISALS_ID_FORMS_ID_URL = "/employees/%d/appraisals/%d/forms/%d";
    private static final String ME_APPRAISALS_ID_FORMS_URL = "/me/appraisals/%d/forms";
    private static final String ME_APPRAISALS_ID_FORMS_ID_URL = "/me/appraisals/%d/forms/%d";

    // Test Fixtures
    private Employee user;
    private Employee employeePeer;
    private Appraisal appraisal;
    private EvaluationFormTemplate evaluationFormTemplate;

    private EmployeeEvaluationForm selfEmployeeEvaluationForm;
    private EmployeeEvaluationForm peerEmployeeEvaluationForm;

    @Before
    public void setUp() throws Exception {

        JobFamily jobFamilyMock = new JobFamilyBuilder()
                .buildWithDefaults();

        JobLevel jobLevel = new JobLevelBuilder()
                .jobFamily(jobFamilyMock)
                .buildWithDefaults();

        ApplicationRole applicationRole = new ApplicationRoleBuilder()
                .buildWithDefaults();

        this.user = new EmployeeBuilder()
                .id(1)
                .jobLevel(jobLevel)
                .applicationRole(applicationRole)
                .buildWithDefaults();

        this.appraisal = new AppraisalBuilder().buildWithDefaults();

        employeePeer = new EmployeeBuilder()
                .firstName("Peer")
                .jobLevel(jobLevel)
                .applicationRole(applicationRole)
                .buildWithDefaults();

        Employee employeeMentor = new EmployeeBuilder()
                .firstName("Mentor")
                .jobLevel(jobLevel)
                .applicationRole(applicationRole)
                .buildWithDefaults();

        evaluationFormTemplate = new EvaluationFormTemplateBuilder().buildWithDefaults();

        // Test EmployeeEvaluationForm
        this.selfEmployeeEvaluationForm = new EmployeeEvaluationFormBuilder()
                .employee(user)
                .filledByEmployee(user)
                .mentor(employeeMentor)
                .buildWithDefaults();
        this.peerEmployeeEvaluationForm = new EmployeeEvaluationFormBuilder()
                .employee(user)
                .filledByEmployee(employeePeer)
                .mentor(employeeMentor)
                .buildWithDefaults();

        when(employeeService.getLoggedInUser()).thenReturn(this.user);
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsIdFormsGet(Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsIdFormsGet() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(appraisalService.getById(anyInt())).thenReturn(appraisal);
        when(employeeEvaluationFormService.findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class)))
                .thenReturn(Stream.of(peerEmployeeEvaluationForm));

        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL,
                        user.getId(),
                        appraisal.getId()))
                    .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        List<EmployeeEvaluationFormDTO> employeeEvaluationFormDTOList = Arrays.asList(objectMapper
                .readValue(result.getResponse().getContentAsString(), EmployeeEvaluationFormDTO[].class));

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(employeeEvaluationFormService, times(1))
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, times(1))
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(employeeEvaluationFormDTOList);
        assertFalse(employeeEvaluationFormDTOList.isEmpty());
        assertTrue(employeeEvaluationFormDTOList.size() == 1);
    }

    @Test
    public void employeesIdAppraisalsIdFormsGet_Empty() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(appraisalService.getById(anyInt())).thenReturn(appraisal);
        when(employeeEvaluationFormService.findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class)))
                .thenReturn(Stream.empty());

        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL,
                        user.getId(),
                        appraisal.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        List<EmployeeEvaluationFormDTO> employeeEvaluationFormDTOList = Arrays.asList(objectMapper
                .readValue(result.getResponse().getContentAsString(), EmployeeEvaluationFormDTO[].class));

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(employeeEvaluationFormService, times(1))
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(employeeEvaluationFormDTOList);
        assertTrue(employeeEvaluationFormDTOList.isEmpty());
    }

    @Test
    public void employeesIdAppraisalsIdFormsGet_securityCheckFailed() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(employeePeer);
        when(appraisalService.getById(anyInt())).thenReturn(appraisal);
        when(employeeEvaluationFormService.findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class)))
                .thenReturn(Stream.of(peerEmployeeEvaluationForm));

        // We will simulate that employeePeer has nothing to do with the process
        doThrow(new AccessDeniedException("Employee[%d] can't read EmployeeEvaluationForm[%d]"))
                .when(securityService).canReadEmployeeEvaluationForm(any(Employee.class),
                any(EmployeeEvaluationForm.class));

        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL,
                        0,
                        appraisal.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(employeeEvaluationFormService, times(1))
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, times(1))
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void employeesIdAppraisalsIdFormsGet_appraisalNotFound() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(employeePeer);
        doThrow(new NotFoundException("Appraisal Not Found")).when(appraisalService).getById(anyInt());

        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL,
                        0,
                        appraisal.getId()))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(employeeEvaluationFormService, never())
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void employeesIdAppraisalsIdFormsGet_employeeNotFound() throws Exception {
        doThrow(new NotFoundException("Employee Not Found")).when(employeeService).getById(anyInt());

        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL,
                        0,
                        appraisal.getId()))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, never()).getById(anyInt());
        verify(employeeEvaluationFormService, never())
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void employeesIdAppraisalsIdFormsGet_badRequest() throws Exception {
        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL,
                        (Integer) null,
                        appraisal.getId()))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        verify(employeeService, never()).getLoggedInUser();
        verify(employeeService, never()).getById(anyInt());
        verify(appraisalService, never()).getById(anyInt());
        verify(employeeEvaluationFormService, never())
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(response);
        assertTrue(StringUtils.isEmpty(response));
    }

    /**
     * Tests {@link AppraisalsController#employeesIdAppraisalsIdFormsIdGet(Integer, Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void employeesIdAppraisalsIdFormsIdGet() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(appraisalService.getById(anyInt())).thenReturn(appraisal);
        when(evaluationFormTemplateService.getById(anyInt())).thenReturn(evaluationFormTemplate);

        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_ID_URL,
                        user.getId(),
                        appraisal.getId(),
                        evaluationFormTemplate.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        EvaluationFormTemplateDTO evaluationFormTemplateDTO = objectMapper
                .readValue(result.getResponse().getContentAsString(), EvaluationFormTemplateDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(evaluationFormTemplateService, times(1)).getById(anyInt());
        verify(securityService, times(1))
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertNotNull(evaluationFormTemplateDTO);
        assertEquals(evaluationFormTemplate.getName(), evaluationFormTemplateDTO.getName());
        assertEquals(evaluationFormTemplate.getDescription(), evaluationFormTemplateDTO.getDescription());
    }

    @Test
    public void employeesIdAppraisalsIdFormsIdGet_notFoundForm() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(evaluationFormTemplateService.getById(anyInt()))
                .thenThrow(new NotFoundException("Evaluation Form not found"));

        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_ID_URL,
                        user.getId(),
                        appraisal.getId(),
                        evaluationFormTemplate.getId()))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO resultDTO = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, never()).getById(anyInt());
        verify(evaluationFormTemplateService, times(1))
                .getById(anyInt());
        verify(securityService, never())
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertNotNull(resultDTO);
        assertEquals(Constants.ERROR, resultDTO.getMessage());
    }

    @Test
    public void employeesIdAppraisalsIdFormsIdGet_appraisalNotFound() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        doThrow(new NotFoundException("Appraisal Not Found")).when(appraisalService).getById(anyInt());

        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_ID_URL,
                        user.getId(),
                        appraisal.getId(),
                        selfEmployeeEvaluationForm.getId()))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(securityService, never())
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void employeesIdAppraisalsIdFormsIdGet_employeeNotFound() throws Exception {
        doThrow(new NotFoundException("Employee Not Found")).when(employeeService).getById(anyInt());

        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_ID_URL,
                        user.getId(),
                        appraisal.getId(),
                        selfEmployeeEvaluationForm.getId()))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, never()).getById(anyInt());
        verify(employeeEvaluationFormService, never())
                .findByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void employeesIdAppraisalsIdFormsIdGet_securityCheckFailed() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(appraisalService.getById(anyInt())).thenReturn(appraisal);
        doThrow(new AccessDeniedException("Access Denied")).when(securityService)
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_ID_URL,
                        user.getId(),
                        appraisal.getId(),
                        selfEmployeeEvaluationForm.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(securityService, times(1))
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void employeesIdAppraisalsIdFormsIdGet_badRequest() throws Exception {
        MvcResult result = mockMvc.perform(
                get(String.format(EMPLOYEES_ID_APPRAISALS_ID_FORMS_ID_URL,
                        user.getId(),
                        appraisal.getId(),
                        (Integer) null))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();

        verify(employeeService, never()).getLoggedInUser();
        verify(employeeService, never()).getById(anyInt());
        verify(appraisalService, never()).getById(anyInt());
        verify(employeeEvaluationFormService, never())
                .findByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertTrue(StringUtils.isEmpty(resultString));
    }

    /**
     * Tests {@link AppraisalsController#meAppraisalsIdFormsGet(Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void meAppraisalsIdFormsGet() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(appraisalService.getById(anyInt())).thenReturn(appraisal);
        when(employeeEvaluationFormService.findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class)))
                .thenReturn(Stream.of(selfEmployeeEvaluationForm, peerEmployeeEvaluationForm));

        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_URL,
                        appraisal.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        List<EmployeeEvaluationFormDTO> employeeEvaluationFormDTOList = Arrays.asList(objectMapper
                .readValue(result.getResponse().getContentAsString(), EmployeeEvaluationFormDTO[].class));

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(employeeEvaluationFormService, times(1))
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, times(2))
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(employeeEvaluationFormDTOList);
        assertFalse(employeeEvaluationFormDTOList.isEmpty());
        assertTrue(employeeEvaluationFormDTOList.size() == 2);
    }

    @Test
    public void meAppraisalsIdFormsGet_empty() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(appraisalService.getById(anyInt())).thenReturn(appraisal);
        when(employeeEvaluationFormService.findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class)))
                .thenReturn(Stream.empty());

        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_URL,
                        appraisal.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        List<EmployeeEvaluationFormDTO> employeeEvaluationFormDTOList = Arrays.asList(objectMapper
                .readValue(result.getResponse().getContentAsString(), EmployeeEvaluationFormDTO[].class));

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(employeeEvaluationFormService, times(1))
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(employeeEvaluationFormDTOList);
        assertTrue(employeeEvaluationFormDTOList.isEmpty());
    }

    @Test
    public void meAppraisalsIdFormsGet_securityCheckFailed() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(appraisalService.getById(anyInt())).thenReturn(appraisal);
        when(employeeEvaluationFormService.findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class)))
                .thenReturn(Stream.of(selfEmployeeEvaluationForm, peerEmployeeEvaluationForm));

        doThrow(new AccessDeniedException("Employee[%d] can't read EmployeeEvaluationForm[%d]"))
                .when(securityService).canReadEmployeeEvaluationForm(any(Employee.class),
                any(EmployeeEvaluationForm.class));

        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_URL,
                        appraisal.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(employeeEvaluationFormService, times(1))
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, times(1))
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void meAppraisalsIdFormsGet_appraisalNotFound() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        doThrow(new NotFoundException("Appraisal Not Found")).when(appraisalService).getById(anyInt());

        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_URL,
                        appraisal.getId()))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(employeeEvaluationFormService, never())
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void meAppraisalsIdFormsGet_employeeNotFound() throws Exception {
        doThrow(new NotFoundException("Appraisal Not Found")).when(employeeService).getById(anyInt());

        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_URL,
                        appraisal.getId()))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, never()).getById(anyInt());
        verify(employeeEvaluationFormService, never())
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void meAppraisalsIdFormsGet_badRequest() throws Exception {
        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_URL,
                        (Integer) null))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();

        verify(employeeService, never()).getLoggedInUser();
        verify(employeeService, never()).getById(anyInt());
        verify(appraisalService, never()).getById(anyInt());
        verify(employeeEvaluationFormService, never())
                .findByFilledByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEmployeeEvaluationForm(any(Employee.class), any(EmployeeEvaluationForm.class));

        assertTrue(StringUtils.isEmpty(resultString));
    }

    /**
     * Tests {@link AppraisalsController#meAppraisalsIdFormsIdGet(Integer, Integer)}
     *
     * @throws Exception If an error occurs
     */
    @Test
    public void meAppraisalsIdFormsIdGet() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(appraisalService.getById(anyInt())).thenReturn(appraisal);
        when(evaluationFormTemplateService.getById(anyInt())).thenReturn(evaluationFormTemplate);

        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_ID_URL,
                        appraisal.getId(),
                        evaluationFormTemplate.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        EvaluationFormTemplateDTO evaluationFormTemplateDTO = objectMapper
                .readValue(result.getResponse().getContentAsString(), EvaluationFormTemplateDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(evaluationFormTemplateService, times(1)).getById(anyInt());
        verify(securityService, times(1))
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertNotNull(evaluationFormTemplateDTO);
        assertEquals(evaluationFormTemplate.getName(), evaluationFormTemplateDTO.getName());
        assertEquals(evaluationFormTemplate.getDescription(), evaluationFormTemplateDTO.getDescription());
    }

    @Test
    public void meAppraisalsIdFormsIdGet_formNotFound() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(evaluationFormTemplateService.getById(anyInt()))
                .thenThrow(new NotFoundException("Evaluation Form not found"));

        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_ID_URL,
                        appraisal.getId(),
                        evaluationFormTemplate.getId()))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO resultDTO = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, never()).getById(anyInt());
        verify(evaluationFormTemplateService, times(1))
                .getById(anyInt());
        verify(securityService, never())
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertNotNull(resultDTO);
        assertEquals(Constants.ERROR, resultDTO.getMessage());
    }

    @Test
    public void meAppraisalsIdFormsIdGet_securityCheckFailed() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(appraisalService.getById(anyInt())).thenReturn(appraisal);
        // We will simulate that employeePeer has nothing to do with the process
        doThrow(new AccessDeniedException("Employee[%d] can't read EvaluationFormTemplate[%d]"))
                .when(securityService).canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                any(EvaluationFormTemplate.class), any(Appraisal.class));

        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_ID_URL,
                        appraisal.getId(),
                        selfEmployeeEvaluationForm.getId()))
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(securityService, times(1))
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void meAppraisalsIdFormsIdGet_appraisalNotFound() throws Exception {
        when(employeeService.getById(anyInt())).thenReturn(user);
        when(appraisalService.getById(anyInt())).thenThrow(new NotFoundException("Appraisal Not Found"));

        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_ID_URL,
                        appraisal.getId(),
                        selfEmployeeEvaluationForm.getId()))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, times(1)).getById(anyInt());
        verify(securityService, never())
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void meAppraisalsIdFormsIdGet_employeeNotFound() throws Exception {
        when(employeeService.getById(anyInt())).thenThrow(new NotFoundException("Employee Not Found"));

        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_ID_URL,
                        appraisal.getId(),
                        selfEmployeeEvaluationForm.getId()))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn();

        OperationResultDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), OperationResultDTO.class);

        verify(employeeService, times(1)).getLoggedInUser();
        verify(employeeService, times(1)).getById(anyInt());
        verify(appraisalService, never()).getById(anyInt());
        verify(securityService, never())
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertNotNull(response);
        assertEquals(Constants.ERROR, response.getMessage());
    }

    @Test
    public void meAppraisalsIdFormsIdGet_badRequest() throws Exception {
        MvcResult result = mockMvc.perform(
                get(String.format(ME_APPRAISALS_ID_FORMS_ID_URL,
                        appraisal.getId(),
                        (Integer) null))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();

        verify(employeeService, never()).getLoggedInUser();
        verify(employeeService, never()).getById(anyInt());
        verify(appraisalService, never()).getById(anyInt());
        verify(employeeEvaluationFormService, never())
                .findByEmployeeAndAppraisal(any(Employee.class), any(Appraisal.class));
        verify(securityService, never())
                .canReadEvaluationFormTemplate(any(Employee.class), any(Employee.class),
                        any(EvaluationFormTemplate.class), any(Appraisal.class));

        assertTrue(StringUtils.isEmpty(resultString));
    }
}