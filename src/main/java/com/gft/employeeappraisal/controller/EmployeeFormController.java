package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.converter.appraisal.AppraisalDTOConverter;
import com.gft.employeeappraisal.converter.employeeevaluationform.EmployeeEvaluationFormDTOConverter;
import com.gft.employeeappraisal.converter.evaluationformtemplate.EvaluationFormTemplateDTOConverter;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.AppraisalXEvaluationFormTemplateRepository;
import com.gft.employeeappraisal.repository.EvaluationFormTemplateXSectionXQuestionRepository;
import com.gft.employeeappraisal.repository.ScoreValueRepository;
import com.gft.employeeappraisal.repository.SectionRepository;
import com.gft.employeeappraisal.service.*;
import com.gft.swagger.employees.api.AppraisalApi;
import com.gft.swagger.employees.model.AppraisalDTO;
import com.gft.swagger.employees.model.EmployeeEvaluationFormDTO;
import com.gft.swagger.employees.model.EvaluationFormTemplateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class EmployeeFormController {

    private final Logger logger = LoggerFactory.getLogger(EmployeeFormController.class);

    // Services
    private final AppraisalService appraisalService;
    private final EmployeeService employeeService;
    private final EmployeeEvaluationFormService employeeEvaluationFormService;
    private final EvaluationFormTemplateService evaluationFormTemplateService;
    private final SecurityService securityService;
    private final ScoreValueService scoreValueService;
    private final ScoreValueRepository scoreValueRepository;
    private final AppraisalXEvaluationFormTemplateService appraisalXEvaluationFormTemplateService;
    private final AppraisalXEvaluationFormTemplateRepository appraisalXEvaluationFormTemplateRepository;
    private final EvaluationFormTemplateXSectionXQuestionRepository evaluationFormTemplateXSectionXQuestionRepository;
    private final SectionRepository sectionRepository;
    // DTO converters
    private final AppraisalDTOConverter appraisalDTOConverter;
    private final EmployeeEvaluationFormDTOConverter employeeEvaluationFormDTOConverter;
    private final EvaluationFormTemplateDTOConverter evaluationFormTemplateDTOConverter;


    @Autowired
    public EmployeeFormController(
            AppraisalService appraisalService,
            EmployeeService employeeService,
            EmployeeEvaluationFormService employeeEvaluationFormService,
            EvaluationFormTemplateService evaluationFormTemplateService,
            SecurityService securityService,
            AppraisalDTOConverter appraisalDTOConverter,
            EmployeeEvaluationFormDTOConverter employeeEvaluationFormDTOConverter,
            EvaluationFormTemplateDTOConverter evaluationFormTemplateDTOConverter,
            ScoreValueService scoreValueService,
            AppraisalXEvaluationFormTemplateService appraisalXEvaluationFormTemplateService,
            EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService,
            EmployeeEvaluationFormAnswerService employeeEvaluationFormAnswerService,
            ScoreValueRepository scoreValueRepository,
            AppraisalXEvaluationFormTemplateRepository appraisalXEvaluationFormTemplateRepository,
            EvaluationFormTemplateXSectionXQuestionRepository evaluationFormTemplateXSectionXQuestionRepository,

            SectionRepository sectionRepository) {
        this.appraisalService = appraisalService;
        this.employeeService = employeeService;
        this.employeeEvaluationFormService = employeeEvaluationFormService;
        this.evaluationFormTemplateService = evaluationFormTemplateService;
        this.securityService = securityService;
        this.appraisalDTOConverter = appraisalDTOConverter;
        this.employeeEvaluationFormDTOConverter = employeeEvaluationFormDTOConverter;
        this.evaluationFormTemplateDTOConverter = evaluationFormTemplateDTOConverter;
        this.scoreValueService =scoreValueService;
        this.appraisalXEvaluationFormTemplateService =appraisalXEvaluationFormTemplateService;
        this.evaluationFormTemplateXSectionXQuestionRepository=evaluationFormTemplateXSectionXQuestionRepository;
        this.scoreValueRepository = scoreValueRepository;
        this.appraisalXEvaluationFormTemplateRepository = appraisalXEvaluationFormTemplateRepository;
        this.sectionRepository=sectionRepository;

    }


    }
