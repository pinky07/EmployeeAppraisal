package com.gft.employeeappraisal.controller;
import com.gft.employeeappraisal.converter.appraisal.AppraisalDTOConverter;
import com.gft.employeeappraisal.converter.employeeevaluationform.EmployeeEvaluationFormDTOConverter;
import com.gft.employeeappraisal.converter.evaluationformtemplate.EvaluationFormTemplateDTOConverter;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.*;
import com.gft.employeeappraisal.service.*;
import com.gft.swagger.employees.model.EmployeeEvaluationFormDTO;
import com.gft.swagger.employees.model.EvaluationFormTemplateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping(value ="/employees/{}/appraisals/{}/forms")
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
    private final AppraisalXEvaluationFormTemplateRepository appraisalXEvaluationFormTemplateRepository;
    private final EvaluationFormTemplateXSectionXQuestionRepository evaluationFormTemplateXSectionXQuestionRepository;
    private final ScoreTypeRepository scoreTypeRepository;
    private final EmployeeEvaluationFormRepository employeeEvaluationFormRepository;
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
            ScoreValueRepository scoreValueRepository,
            AppraisalXEvaluationFormTemplateRepository appraisalXEvaluationFormTemplateRepository,
            EvaluationFormTemplateXSectionXQuestionRepository evaluationFormTemplateXSectionXQuestionRepository,
            ScoreTypeRepository scoreTypeRepository,
            SectionRepository sectionRepository,
            EmployeeEvaluationFormRepository employeeEvaluationFormRepository) {
        this.appraisalService = appraisalService;
        this.employeeService = employeeService;
        this.employeeEvaluationFormService = employeeEvaluationFormService;
        this.evaluationFormTemplateService = evaluationFormTemplateService;
        this.securityService = securityService;
        this.appraisalDTOConverter = appraisalDTOConverter;
        this.employeeEvaluationFormDTOConverter = employeeEvaluationFormDTOConverter;
        this.evaluationFormTemplateDTOConverter = evaluationFormTemplateDTOConverter;
        this.scoreValueService =scoreValueService;

        this.evaluationFormTemplateXSectionXQuestionRepository=evaluationFormTemplateXSectionXQuestionRepository;
        this.scoreValueRepository = scoreValueRepository;
        this.appraisalXEvaluationFormTemplateRepository = appraisalXEvaluationFormTemplateRepository;
        this.scoreTypeRepository=scoreTypeRepository;
        this.employeeEvaluationFormRepository =employeeEvaluationFormRepository;

    }
//    @PutMapping(value = "", consumes = "application/json", produces = "application/json")
//    private ResponseEntity<EvaluationFormTemplateDTO> employeesIdAppraisalsIdFormsIdPut(
//        @PathVariable("employeeId") Integer employeeId,
//        @PathVariable("appraisalId") Integer appraisalId,
//        @PathVariable("formId") Integer formId,@RequestBody ScoreValue scoreValue) {
//
//        EvaluationFormTemplate template ;
//
//        Section section= new Section();
//        ScoreType scoreType ;
//        Employee employee = employeeService.getById(employeeId);
//        Question question;
//        //ScoreValue scoreValue = new ScoreValue();
//        Set<ScoreValue> scoreValues= new HashSet<>();
//        //get templateID, template-->1:m--->EvaluationFormTemplateXSectionXQuestion-->m:1-->Section-->M:1--->ScoreType---1:M--ScoreValue
//        EvaluationFormTemplate evaluationFormTemplate = evaluationFormTemplateService.getById(formId);
//
//        //get evaluationFormTemplate---1:M-->EvaluationFormTemplateXSectionXQuestion
//        Set<EvaluationFormTemplateXSectionXQuestion> evaluationFormXSectionXQuestionSet= evaluationFormTemplate.getEvaluationFormXSectionXQuestionSet();
//
//        //get Section from, EvaluationFormTemplateXSectionXQuestion--M:1--->section
//        //get Question from EvaluationFormTemplateXSectionXQuestion--M:1--->Question
//        for(EvaluationFormTemplateXSectionXQuestion evaluationFormTemplateXSectionXQuestion:evaluationFormXSectionXQuestionSet){
//            section = evaluationFormTemplateXSectionXQuestion.getSection();
//            question =evaluationFormTemplateXSectionXQuestion.getQuestion();
//            //get ScoreType, from section
//            scoreType =  section.getScoreType();
//
//            //get ScoreValue, from ScoreType
//            scoreValues=  scoreType.getScoreValueSet();
//            for(ScoreValue scoreValue1:scoreValues)
//            {scoreValue1.setValue(scoreValue.getValue());
//                scoreValueRepository.save(scoreValue1);
//            }
//            //we need to set value for ScoreValue, bullet button table
//            scoreTypeRepository.save(scoreType);
//        }
//
//
//
//        Appraisal appraisal = appraisalService.getById(appraisalId);
//        Set <AppraisalXEvaluationFormTemplate> appraisalXEvaluationFormTemplates =appraisal.getAppraisalXEvaluationFormTemplateSet();
//        appraisalXEvaluationFormTemplateRepository.save(appraisalXEvaluationFormTemplates);
//
//        evaluationFormTemplateXSectionXQuestionRepository.save(evaluationFormXSectionXQuestionSet);
//        EvaluationFormTemplateDTO evaluationFormTemplateDTO =
//                evaluationFormTemplateDTOConverter.convert(evaluationFormTemplate);
//
//        return new ResponseEntity<>(evaluationFormTemplateDTO, HttpStatus.OK);
//    }
    @PutMapping( consumes = "application/json", produces = "application/json")
    public ResponseEntity<EmployeeEvaluationFormDTO> employeesIdAppraisalsIdFormsPut(
            @PathVariable Integer employeeId,
            @PathVariable Integer appraisalId,
            @RequestBody EmployeeEvaluationFormDTO evaluationFormBody) {
        EmployeeEvaluationForm employeeEvaluationForm = new EmployeeEvaluationForm();
        HttpStatus httpStatus;
        EmployeeEvaluationFormDTO response = new EmployeeEvaluationFormDTO();
        if(evaluationFormBody.getSubmitDate()==null){
            //call here saveAndContinue()
        }
        if(evaluationFormBody.getSubmitDate()!=null)
        {// call here saveAndContinue()
            employeeEvaluationForm.setSubmitDate(evaluationFormBody.getSubmitDate());
            employeeEvaluationForm= employeeEvaluationFormDTOConverter.convertBack(evaluationFormBody);
            OffsetDateTime submitday = OffsetDateTime.now();
            employeeEvaluationForm.setSubmitDate(submitday);
            employeeEvaluationFormRepository.saveAndFlush(employeeEvaluationForm);
            //employeeEvaluationFormService.saveAndFlush(employeeEvaluationForm);
        }
        return new ResponseEntity<EmployeeEvaluationFormDTO>(HttpStatus.OK);
    }
    }
