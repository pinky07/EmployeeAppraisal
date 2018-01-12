package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.repository.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Base class for Service layer tests.
 * <p>
 * DON'T use @Mock or @SpyBean in any classes inheriting this class! If you need to, add those here. See note below.
 *
 * @author Rubén Jiménez
 */
@DataJpaTest
public class BaseServiceTest {

    //
    // IMPORTANT!
    //
    // Don't use any @Mock or @SpyBean annotations in child classes of this class! If you need to, add it here! Yes,
    // it will be unused in some child classes, but if you do it this way the ApplicationContext will be cached across
    // all child test classes thus reducing significantly the amount of time it takes to do the integration tests!
    //

    @Autowired
    protected ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    protected AppraisalRepository appraisalRepository;

    @Autowired
    protected EmployeeEvaluationFormRepository employeeEvaluationFormRepository;

    @Autowired
    protected EmployeeRelationshipRepository employeeRelationshipRepository;

    @Autowired
    protected EmployeeRepository employeeRepository;

    @Autowired
    protected EvaluationFormTemplateRepository evaluationFormTemplateRepository;

    @Autowired
    protected EvaluationFormTemplateXSectionXQuestionRepository evaluationFormXSectionXQuestionRepository;

    @Autowired
    protected JobFamilyRepository jobFamilyRepository;

    @Autowired
    protected JobLevelRepository jobLevelRepository;

    @Autowired
    protected RelationshipTypeRepository relationshipTypeRepository;

    //
    // Note: Shadow the Service you're testing with a field in the your ServiceTest!
    //

    @Mock
    protected ApplicationRoleService applicationRoleService;

    @Mock
    protected AppraisalService appraisalService;

    @Mock
    protected EmployeeEvaluationFormService employeeEvaluationFormService;

    @Mock
    protected EmployeeRelationshipService employeeRelationshipService;

    @Mock
    protected EmployeeService employeeService;

    @Mock
    protected EvaluationFormTemplateService evaluationFormTemplateService;

    @Mock
    protected EvaluationFormTemplateXSectionXQuestionService evaluationFormXSectionXQuestionService;

    @Mock
    protected JobFamilyService jobFamilyService;

    @Mock
    protected JobLevelService jobLevelService;

    @Mock
    protected RelationshipTypeService relationshipTypeService;

    @Mock
    protected SecurityService securityService;

    // Validation Service should not be mocked for Controller tets!
    // @Mock
    // protected ValidationService validationService;

}
