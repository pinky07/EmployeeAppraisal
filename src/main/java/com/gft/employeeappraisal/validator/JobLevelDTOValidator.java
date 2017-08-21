package com.gft.employeeappraisal.validator;

import com.gft.swagger.employees.model.JobLevelDTO;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Class that describes validation rules for the conversion from a JobLevelDTO to a JobLevel entity.
 *
 * @author Manuel Yepez
 */
@Component
public class JobLevelDTOValidator implements DTOValidator<JobLevelDTO> {

    private static final String ID_FIELD = "id";

    @Override
    public boolean supports(Class<?> clazz) {
        return JobLevelDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, ID_FIELD, "employeeDTO.emptyField", new Object[]{ID_FIELD});
    }

    @Override
    public MutablePropertyValues getPropertyValues(JobLevelDTO dtoObject) {
        MutablePropertyValues values = new MutablePropertyValues();
        values.addPropertyValue(ID_FIELD, dtoObject.getId());
        return values;
    }
}
