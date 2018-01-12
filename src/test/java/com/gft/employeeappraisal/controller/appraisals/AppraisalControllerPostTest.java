package com.gft.employeeappraisal.controller.appraisals;

import com.gft.employeeappraisal.controller.BaseControllerTest;
import com.gft.employeeappraisal.helper.builder.dto.*;
import com.gft.employeeappraisal.helper.builder.model.*;
import com.gft.employeeappraisal.model.*;
import com.gft.swagger.employees.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class AppraisalControllerPostTest extends BaseControllerTest {

    private static final String EMPLOYEES_URL = "/employees/{employeeId}/appraisals/{appraisalId}/forms";
    private static EmployeeDTO mockEmployeeDTO;
    private static EmployeeEvaluationForm evaluationForm;
    private static final String EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL = "/employees/%d/appraisals/%d/forms";
    private static final String EMPLOYEES_ID_APPRAISALS_ID_URL = "/employees/%d/appraisals/%d";
    private static final String EMPLOYEES_ID_APPRAISALS_ID_FORM_URL = "/employees/%d/appraisals/%d/forms";
    private static Employee user;
    private static Appraisal appraisal;
    private static EvaluationFormTemplate evaluationFormTemplate;
    private static EmployeeEvaluationForm employeeEvaluationForm;
    private static Employee employeeMentor;
    private static EmployeeEvaluationFormDTO employeeEvaluationFormDTO;
    private static EmployeeDTO employeeDTO;
    private static ApplicationRoleDTO applicationRoleDTO;
    private static JobLevelDTO jobLevelDTO;
    private static JobFamilyDTO jobFamilyDTO;

    @Before
    public void setUp() throws Exception {
        JobFamily jobFamilyMock = new JobFamilyBuilder()
                .buildWithDefaults();

        this.applicationRoleDTO = new ApplicationRoleDTOBuilder()
                .id(2)
                .name("USER")
                .description("System User").build();
        this.jobFamilyDTO = new JobFamilyDTOBuilder()
                .id(7)
                .name("Project Development")
                .description("Project Development Description")
                .build();

        this.jobLevelDTO = new JobLevelDTOBuilder()
                .id(45)
                .name("L3")
                .description("L3 description")
                .jobFamily(jobFamilyDTO)
                .build();

        OffsetDateTime dateNow = OffsetDateTime.now();
        this.employeeDTO = new EmployeeDTOBuilder()
                .firstName("Pinky")
                .lastName("Agrawal")
                .email("Pinky.Agrawal@gft.com")
                .isMentor(true)
                .isPeer(false)
                .gftIdentifier("PIAL")
                .isAdmin(false)
                .applicationRole(applicationRoleDTO)
                .jobLevel(jobLevelDTO)
                .build();

        employeeEvaluationFormDTO = new EmployeeEvaluationFormDTOBuilder()
                .setEvaluationFormId(1)
                .setId(24)
                .setEmployee(employeeDTO)
                .setCreateDate(dateNow)
                .setSubmitDate(dateNow)
                .setMentor(employeeDTO).setFilledByEmployee(employeeDTO).
                        build();

        this.appraisal = new AppraisalBuilder().id(1).build();
        this.evaluationFormTemplate = new EvaluationFormTemplateBuilder().id(26)
                .build();

        employeeEvaluationForm = new EmployeeEvaluationFormBuilder(appraisal, evaluationFormTemplate)
                .filledByEmployee(user)
//				.appraisalXEvaluationFormTemplate(appraisalXEvaluationFormTemplate)
                .submitDate(OffsetDateTime.now()).buildWithDefaults();
        ApplicationRole applicationRole = new ApplicationRoleBuilder()
                .buildWithDefaults();


        JobLevel jobLevel = new JobLevelBuilder()
                .jobFamily(jobFamilyMock)
                .buildWithDefaults();

        this.employeeMentor = new EmployeeBuilder()
                .firstName("Mentor")
                .jobLevel(jobLevel)
                .applicationRole(applicationRole)
                .buildWithDefaults();
        this.user = new EmployeeBuilder()
                .id(26)
                .jobLevel(jobLevel)
                .applicationRole(applicationRole)
                .email("Pinky.Agrawal@gft.com")
                .buildWithDefaults();


        evaluationForm = new EmployeeEvaluationFormBuilder(appraisal, evaluationFormTemplate)

                .id(26)
                .submitDate(dateNow)
                .createDate(dateNow)
                .filledByEmployee(user)
                .mentor(user)
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

    @SuppressWarnings("all")
    private EmployeeEvaluationForm mockEmployeeEvaulationForm() {
        return new EmployeeEvaluationFormBuilder(appraisal, evaluationFormTemplate).buildWithDefaults();
    }

    @Test
    public void employeesIdAppraisalsIdFormsPut() throws Exception {
//		MvcResult result = mockMvc.perform(put(String.format(
//				EMPLOYEES_ID_APPRAISALS_ID_FORMS_URL,
//				user.getId(),
//				appraisal.getId()))
//				.with(csrf())
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(employeeEvaluationFormDTO))
//		).andExpect(status().isOk()).andReturn();


//		when(employeeService.getById(anyInt())).thenReturn(user);
//		when(appraisalService.getById(anyInt())).thenReturn(appraisal);
////		//when(employeeEvaluationFormService.getById(anyInt())).thenReturn(employeeEvaluationFormDTO);
////		when(employeeEvaluationFormService.saveAndFlush(any(EmployeeEvaluationForm.class))).thenReturn( Optional.of(mockEmployeeEvaulationForm()));
////		verify(employeeService, times(1)).findById(anyInt());
//		verify(employeeEvaluationFormService, times(1)).saveAndFlush(any(EmployeeEvaluationForm.class));

    }
}
