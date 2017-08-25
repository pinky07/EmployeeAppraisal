package com.gft.employeeappraisal.validator;

import com.gft.swagger.employees.model.JobLevelDTO;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Class that describes validation rules for the conversion from a JobLevelDTO to a JobLevel entity.
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@Component
public class JobLevelDTOValidator implements HttpValidator {

    private static final String ID_FIELD = "id";

    @Override
    public boolean supports(Class<?> clazz) {
        return JobLevelDTO.class.equals(clazz);
    }

    @Override
    public boolean supports(HttpMethod httpMethod) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, ID_FIELD, "jobLevelDTO.idNotSet");
    }

    @Override
    public void validate(Object target, HttpMethod httpMethod, Errors errors) {
        validate(target, errors);
    }
}
