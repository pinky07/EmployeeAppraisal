package com.gft.employeeappraisal.converter.evaluationformtemplatexsectionxquestion;

import com.gft.employeeappraisal.converter.employeeevaluationform.EmployeeEvaluationFormDTOConverter;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import com.gft.employeeappraisal.service.QuestionService;
import com.gft.swagger.employees.model.EvaluationFormTemplateXSectionXQuestionDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Defines a mapping structure to be used by {@link EmployeeEvaluationFormDTOConverter}.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@Component
public class EvaluationFormTemplateXSectionXQuestionDTOMapper extends CustomMapper<EvaluationFormTemplateXSectionXQuestion, EvaluationFormTemplateXSectionXQuestionDTO>
{

    private final QuestionService questionService;

    @Autowired public EvaluationFormTemplateXSectionXQuestionDTOMapper(QuestionService questionService)
    { this.questionService=questionService;
    }

    @Override public void mapAtoB(EvaluationFormTemplateXSectionXQuestion question, EvaluationFormTemplateXSectionXQuestionDTO questionDTO, MappingContext context)
    {
        questionDTO.setId(question.getId());

    }

    @Override public void mapBtoA(EvaluationFormTemplateXSectionXQuestionDTO questionDTO, EvaluationFormTemplateXSectionXQuestion question, MappingContext context)
    {

    }
}
