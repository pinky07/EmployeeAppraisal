package com.gft.employeeappraisal.converter.evaluationFormQuestion;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.EvaluationFormQuestion;
import com.gft.swagger.employees.model.EvaluationFormQuestionDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter init class for bidirectional transformation between {@link EvaluationFormQuestion} and {@link EvaluationFormQuestionDTO}
 *
 * @author Rubén Jiménez
 */
@Component
public class EvaluationFormQuestionDTOConverter extends EntityDTOConverter<EvaluationFormQuestion, EvaluationFormQuestionDTO> {

    @Autowired
    public EvaluationFormQuestionDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(EvaluationFormQuestion.class, EvaluationFormQuestionDTO.class);
    }
}
