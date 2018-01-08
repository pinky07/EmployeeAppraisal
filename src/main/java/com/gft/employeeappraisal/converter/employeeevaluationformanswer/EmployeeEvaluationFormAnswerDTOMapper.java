package com.gft.employeeappraisal.converter.employeeevaluationformanswer;

import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EmployeeEvaluationFormAnswer;
import com.gft.employeeappraisal.model.ScoreValue;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import com.gft.employeeappraisal.service.EvaluationFormTemplateXSectionXQuestionService;
import com.gft.swagger.employees.model.AnswerDTO;
import com.gft.swagger.employees.model.ScoreValueDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Defines a mapping structure to be used by {@link EmployeeEvaluationFormAnswerDTOConverter}.
 *
 * @author Manuel Yépez
 */
@Component
public class EmployeeEvaluationFormAnswerDTOMapper extends CustomMapper<EmployeeEvaluationFormAnswer, AnswerDTO> {

    private final EmployeeEvaluationFormService employeeEvaluationFormService;
    private final EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService;

    @Autowired
    public EmployeeEvaluationFormAnswerDTOMapper(EmployeeEvaluationFormService employeeEvaluationFormService,
                                                 EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService) {
        this.employeeEvaluationFormService = employeeEvaluationFormService;
        this.evaluationFormTemplateXSectionXQuestionService = evaluationFormTemplateXSectionXQuestionService;
    }

    @Override
    public void mapAtoB(EmployeeEvaluationFormAnswer employeeEvaluationFormAnswer, AnswerDTO answerDTO,
                        MappingContext context) {
        answerDTO.setId(employeeEvaluationFormAnswer.getId());
        answerDTO.setEmployeeEvaluationFormId(employeeEvaluationFormAnswer.getEmployeeEvaluationForm().getId());
        answerDTO.setEvaluationFormTemplateXSectionXQuestionId(employeeEvaluationFormAnswer
                .getEvaluationFormTemplateXSectionXQuestion().getId());
        if (employeeEvaluationFormAnswer.getScoreValue() != null) {
            answerDTO.setScoreValue(mapperFacade.map(employeeEvaluationFormAnswer.getScoreValue(), ScoreValueDTO.class));
        }
        answerDTO.setComment(employeeEvaluationFormAnswer.getComment());
    }

    @Override
    public void mapBtoA(AnswerDTO answerDTO, EmployeeEvaluationFormAnswer employeeEvaluationFormAnswer,
                        MappingContext context) {
        if (Objects.nonNull(answerDTO.getId()) && answerDTO.getId() > 0) {
            employeeEvaluationFormAnswer.setId(answerDTO.getId());
        }

        EmployeeEvaluationForm employeeEvaluationForm = employeeEvaluationFormService
                .getById(answerDTO.getEmployeeEvaluationFormId());
        employeeEvaluationFormAnswer.setEmployeeEvaluationForm(employeeEvaluationForm);
        employeeEvaluationFormAnswer.setEvaluationFormTemplateXSectionXQuestion(evaluationFormTemplateXSectionXQuestionService
                .getById(answerDTO.getEvaluationFormTemplateXSectionXQuestionId()));
        if (Objects.nonNull(answerDTO.getScoreValue())) {
            employeeEvaluationFormAnswer.setScoreValue(mapperFacade.map(answerDTO.getScoreValue(), ScoreValue.class));
        }

        employeeEvaluationFormAnswer.setComment(answerDTO.getComment());
    }
}
