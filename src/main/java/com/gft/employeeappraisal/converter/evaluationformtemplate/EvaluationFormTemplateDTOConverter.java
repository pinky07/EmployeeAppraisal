package com.gft.employeeappraisal.converter.evaluationformtemplate;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import com.gft.swagger.employees.model.EvaluationFormTemplateDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between {@link EvaluationFormTemplate} and {@link EvaluationFormTemplateDTO}.
 *
 * @author Rubén Jiménez
 */
@Component
public class EvaluationFormTemplateDTOConverter extends EntityDTOConverter<EvaluationFormTemplate, EvaluationFormTemplateDTO> {

    @Autowired
    public EvaluationFormTemplateDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(EvaluationFormTemplate.class, EvaluationFormTemplateDTO.class);
    }
}
