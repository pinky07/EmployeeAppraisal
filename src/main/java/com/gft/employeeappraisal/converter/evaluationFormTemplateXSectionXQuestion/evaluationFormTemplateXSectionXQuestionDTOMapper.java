package com.gft.employeeappraisal.converter.evaluationFormTemplateXSectionXQuestion;

import com.gft.employeeappraisal.converter.employeeevaluationform.EmployeeEvaluationFormDTOConverter;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EmployeeEvaluationFormAnswer;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import com.gft.employeeappraisal.model.Question;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.employeeappraisal.service.QuestionService;
import com.gft.swagger.employees.model.*;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Defines a mapping structure to be used by {@link EmployeeEvaluationFormDTOConverter}.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@Component
public class evaluationFormTemplateXSectionXQuestionDTOMapper extends CustomMapper<EvaluationFormTemplateXSectionXQuestion, EvaluationFormTemplateXSectionXQuestionDTO>
{

    private final QuestionService questionService;

    @Autowired public evaluationFormTemplateXSectionXQuestionDTOMapper(QuestionService questionService)
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
