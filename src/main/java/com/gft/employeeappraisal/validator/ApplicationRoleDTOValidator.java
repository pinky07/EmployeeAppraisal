package com.gft.employeeappraisal.validator;

import com.gft.swagger.employees.model.ApplicationRoleDTO;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Class that describes validation rules for the conversion from an ApplicationRoleDTO to an ApplicationRole entity.
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@Component
public class ApplicationRoleDTOValidator implements Validator, HttpValidator {

    private static final String ID_FIELD = "id";

    @Override
    public boolean supports(Class<?> clazz) {
        return ApplicationRoleDTO.class.equals(clazz);
    }

    @Override
    public boolean supports(HttpMethod httpMethod) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, ID_FIELD, "employeeDTO.emptyField", new Object[]{ID_FIELD});
    }

    @Override
    public void validate(Object target, HttpMethod httpMethod, Errors errors) {
        validate(target, errors);
    }
}
