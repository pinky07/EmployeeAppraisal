package com.gft.employeeappraisal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.employeeappraisal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Base class for Controller layer tests.
 * <p>
 * DON'T use @MockBean or @SpyBean in any classes inheriting this class! If you need to, add those here. See note below.
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"default", "local-h2", "test"})
public abstract class BaseControllerTest {

    // Used in most tests

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    //
    // IMPORTANT!
    //
    // Don't use any @MockBean or @SpyBean annotations in child classes of this class! If you need an additional mocked
    // service, add it here! Yes, it will be unused in some child classes, but if you do it this way the
    // ApplicationContext will be cached across all child test classes thus reducing significantly the amount of time it
    // takes to do the integration tests!
    //

    @MockBean
    protected ApplicationRoleService applicationRoleService;

    @MockBean
    protected AppraisalService appraisalService;

    @MockBean
    protected AppraisalXEvaluationFormService appraisalXEvaluationFormService;

    @MockBean
    protected EmployeeEvaluationFormService employeeEvaluationFormService;

    @MockBean
    protected EmployeeRelationshipService employeeRelationshipService;

    @MockBean
    protected EmployeeService employeeService;

    @MockBean
    protected EvaluationFormService evaluationFormService;

    @MockBean
    protected EvaluationFormXSectionXQuestionService evaluationFormXSectionXQuestionService;

    @MockBean
    protected JobFamilyService jobFamilyService;

    @MockBean
    protected JobLevelService jobLevelService;

    @MockBean
    protected RelationshipService relationshipService;

    @MockBean
    protected SecurityService securityService;

    // Validation Service should not be mocked for Controller tets!
    // @MockBean
    // protected ValidationService validationService;
}
