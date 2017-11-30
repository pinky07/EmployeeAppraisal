package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.repository.EmployeeEvaluationFormAnswerRepository;
import com.gft.employeeappraisal.repository.EvaluationFormTemplateXSectionXQuestionRepository;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormAnswerService;
import com.gft.employeeappraisal.service.EvaluationFormTemplateXSectionXQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationFormTemplateXSectionXQuestionServiceImpl implements EvaluationFormTemplateXSectionXQuestionService
{

    private final EvaluationFormTemplateXSectionXQuestionRepository evaluationFormTemplateXSectionXQuestionRepository;

    /**
     * @inheritDoc
     */
    @Autowired
    public EvaluationFormTemplateXSectionXQuestionServiceImpl(EvaluationFormTemplateXSectionXQuestionRepository evaluationFormTemplateXSectionXQuestionRepository) {
        this.evaluationFormTemplateXSectionXQuestionRepository = evaluationFormTemplateXSectionXQuestionRepository;
    }

}
