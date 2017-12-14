package com.gft.employeeappraisal.converter.question;

import com.gft.employeeappraisal.model.Question;
import com.gft.swagger.employees.model.QuestionDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;

/**
 * Defines a mapping structure to be used by {@link QuestionDTOConverter}.
 *
 * @author Rubén Jiménez
 */
@Component
public class QuestionDTOMapper extends CustomMapper<Question, QuestionDTO> {

    @Override
    public void mapAtoB(Question question, QuestionDTO questionDTO, MappingContext context) {
        questionDTO.setId(question.getId());
        questionDTO.setName(question.getName());
        questionDTO.setDescription(question.getDescription());
        questionDTO.setPosition(question.getPosition());
    }

    @Override
    public void mapBtoA(QuestionDTO evaluationFormQuestionDTO, Question question, MappingContext context) {
        // TODO Implement this method!
        // question.setEvaluationFormXSectionXQuestionSet(evaluationFormQuestionDTO.set);
         question.setId(evaluationFormQuestionDTO.getId());
         question.setDescription(evaluationFormQuestionDTO.getDescription());
         question.setName(evaluationFormQuestionDTO.getName());
//        throw new NotImplementedException();
    }
}
