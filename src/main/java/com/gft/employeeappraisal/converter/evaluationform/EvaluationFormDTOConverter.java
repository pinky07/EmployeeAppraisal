package com.gft.employeeappraisal.converter.evaluationform;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.EvaluationForm;
import com.gft.swagger.employees.model.EvaluationFormDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter init class for bidirectional transformation between {@link EvaluationForm} and {@link EvaluationFormDTO}
 *
 * @author Rubén Jiménez
 */
@Component
public class EvaluationFormDTOConverter extends EntityDTOConverter<EvaluationForm, EvaluationFormDTO> {

    @Autowired
    public EvaluationFormDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(EvaluationForm.class, EvaluationFormDTO.class);
    }
}
