package com.gft.employeeappraisal.validator;

import com.gft.swagger.employees.model.ApplicationRoleDTO;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Class that describes validation rules for the conversion from an ApplicationRoleDTO to an ApplicationRole entity.
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@Component
public class ApplicationRoleDTOValidator implements HttpValidator {

    private static final String ID_FIELD = "id";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return ApplicationRoleDTO.class.equals(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(HttpMethod httpMethod) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, ID_FIELD, "applicationRoleDTOValidator.idNotSet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, HttpMethod httpMethod, Errors errors) {
        validate(target, errors);
    }
}
