package com.gft.employeeappraisal.converter.employeeevaluationformanswer;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.EmployeeEvaluationFormAnswer;
import com.gft.swagger.employees.model.AnswerDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between {@link EmployeeEvaluationFormAnswer}
 * and {@link AnswerDTO}.
 *
 * @author Manuel Yépez
 */
@Component
public class EmployeeEvaluationFormAnswerDTOConverter extends EntityDTOConverter<EmployeeEvaluationFormAnswer, AnswerDTO> {

    @Autowired
    public EmployeeEvaluationFormAnswerDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(EmployeeEvaluationFormAnswer.class, AnswerDTO.class);
    }
}
