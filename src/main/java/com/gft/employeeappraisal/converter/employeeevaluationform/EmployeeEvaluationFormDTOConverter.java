package com.gft.employeeappraisal.converter.employeeevaluationform;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.swagger.employees.model.EmployeeEvaluationFormDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between {@link EmployeeEvaluationForm} and {@link EmployeeEvaluationFormDTO}.
 *
 * @author Rubén Jiménez
 */
@Component
public class EmployeeEvaluationFormDTOConverter extends EntityDTOConverter<EmployeeEvaluationForm, EmployeeEvaluationFormDTO> {

    @Autowired
    public EmployeeEvaluationFormDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(EmployeeEvaluationForm.class, EmployeeEvaluationFormDTO.class);
    }
}
