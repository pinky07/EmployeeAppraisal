package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import com.gft.employeeappraisal.repository.EmployeeEvaluationFormAnswerRepository;
import com.gft.employeeappraisal.repository.EvaluationFormTemplateXSectionXQuestionRepository;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormAnswerService;
import com.gft.employeeappraisal.service.EvaluationFormTemplateXSectionXQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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



    @Override public Optional<EvaluationFormTemplateXSectionXQuestion> findById(Integer id)
    {
        return Optional.ofNullable(evaluationFormTemplateXSectionXQuestionRepository.findOne(id));
    }

    @Override public EvaluationFormTemplateXSectionXQuestion getById(Integer id) throws NotFoundException
    {
        return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "EvaluationFormTemplateXSectionXQuestion [%d] couldn't be found",
                id)));
    }

    @Override
    public Optional<EvaluationFormTemplateXSectionXQuestion> saveAndFlush(
            EvaluationFormTemplateXSectionXQuestion employee)
    {
        return Optional.ofNullable(evaluationFormTemplateXSectionXQuestionRepository.saveAndFlush(employee));
    }
}
