package com.gft.employeeappraisal.converter.evaluationform;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.swagger.employees.model.EvaluationFormDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EvaluationFormDTOConverter extends EntityDTOConverter<EmployeeEvaluationForm, EvaluationFormDTO> {

    @Autowired
    public EvaluationFormDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(EmployeeEvaluationForm.class, EvaluationFormDTO.class);
    }
}
