package com.gft.employeeappraisal.converter.question;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.Question;
import com.gft.swagger.employees.model.QuestionDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between {@link Question} and {@link QuestionDTO}.
 *
 * @author Rubén Jiménez
 */
@Component
public class QuestionDTOConverter extends EntityDTOConverter<Question, QuestionDTO> {

    @Autowired
    public QuestionDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(Question.class, QuestionDTO.class);
    }
}
