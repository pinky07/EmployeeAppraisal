package com.gft.employeeappraisal.converter.evaluationFormQuestion;

import com.gft.employeeappraisal.model.EvaluationFormQuestion;
import com.gft.swagger.employees.model.EvaluationFormQuestionDTO;
import com.gft.swagger.employees.model.ScoreTypeDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;

/**
 * Defines a mapping structure to be used by {@link EvaluationFormQuestionDTOConverter}.
 *
 * @author Rubén Jiménez
 */
@Component
public class EvaluationFormQuestionDTOMapper extends CustomMapper<EvaluationFormQuestion, EvaluationFormQuestionDTO> {

    @Override
    public void mapAtoB(EvaluationFormQuestion evaluationFormQuestion, EvaluationFormQuestionDTO evaluationFormQuestionDTO, MappingContext context) {
        evaluationFormQuestionDTO.setId(evaluationFormQuestion.getId());
        evaluationFormQuestionDTO.setName(evaluationFormQuestion.getName());
        evaluationFormQuestionDTO.setDescription(evaluationFormQuestion.getDescription());
        evaluationFormQuestionDTO.setScoreType(mapperFacade.map(evaluationFormQuestion.getScoreType(), ScoreTypeDTO.class));
    }

    @Override
    public void mapBtoA(EvaluationFormQuestionDTO evaluationFormQuestionDTO, EvaluationFormQuestion evaluationFormQuestion, MappingContext context) {
        // TODO Implement this method!
        throw new NotImplementedException();
    }
}
