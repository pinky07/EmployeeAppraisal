package com.gft.employeeappraisal.converter.evaluationFormSection;

import com.gft.employeeappraisal.model.EvaluationFormSection;
import com.gft.swagger.employees.model.EvaluationFormSectionDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;

/**
 * Defines a mapping structure to be used by {@link EvaluationFormSectionDTOConverter}.
 *
 * @author Rubén Jiménez
 */
@Component
public class EvaluationFormSectionDTOMapper extends CustomMapper<EvaluationFormSection, EvaluationFormSectionDTO> {

    @Override
    public void mapAtoB(EvaluationFormSection evaluationFormSection, EvaluationFormSectionDTO evaluationFormSectionDTO, MappingContext context) {
        evaluationFormSectionDTO.setId(evaluationFormSection.getId());
        evaluationFormSectionDTO.setName(evaluationFormSection.getName());
        evaluationFormSectionDTO.setDescription(evaluationFormSection.getDescription());
    }

    @Override
    public void mapBtoA(EvaluationFormSectionDTO evaluationFormSectionDTO, EvaluationFormSection evaluationFormSection, MappingContext context) {
        // TODO Implement this method!
        throw new NotImplementedException();
    }
}
