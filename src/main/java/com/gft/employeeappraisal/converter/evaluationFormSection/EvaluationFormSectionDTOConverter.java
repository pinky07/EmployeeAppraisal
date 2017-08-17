package com.gft.employeeappraisal.converter.evaluationFormSection;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.EvaluationFormSection;
import com.gft.swagger.employees.model.EvaluationFormSectionDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter init class for bidirectional transformation between {@link EvaluationFormSection} and {@link EvaluationFormSectionDTO}
 *
 * @author Rubén Jiménez
 */
@Component
public class EvaluationFormSectionDTOConverter extends EntityDTOConverter<EvaluationFormSection, EvaluationFormSectionDTO> {

    @Autowired
    public EvaluationFormSectionDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(EvaluationFormSection.class, EvaluationFormSectionDTO.class);
    }
}
