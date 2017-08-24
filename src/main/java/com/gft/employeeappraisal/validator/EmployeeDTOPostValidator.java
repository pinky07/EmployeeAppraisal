package com.gft.employeeappraisal.validator;

import com.gft.swagger.employees.model.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Class that describes validation rules for the conversion from an EmployeeDTO to an Employee entity,
 * specifically in the case of an Employee being created.
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@Component
public class EmployeeDTOPostValidator implements Validator, HttpValidator {

    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String EMAIL_FIELD = "email";
    private static final String GFT_IDENTIFIER_FIELD = "gftIdentifier";
    private static final String APPLICATION_ROLE_FIELD = "applicationRole";
    private static final String JOB_LEVEL_FIELD = "jobLevel";

    private CompositeValidator compositeValidator;

    @Autowired
    public EmployeeDTOPostValidator(CompositeValidator compositeValidator) {
        this.compositeValidator = compositeValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return EmployeeDTO.class.equals(clazz);
    }

    @Override
    public boolean supports(HttpMethod httpMethod) {
        return httpMethod.equals(HttpMethod.POST);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIRST_NAME_FIELD, "employeeDTO.emptyField", new Object[]{FIRST_NAME_FIELD});
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, LAST_NAME_FIELD, "employeeDTO.emptyField", new Object[]{LAST_NAME_FIELD});
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, EMAIL_FIELD, "employeeDTO.emptyField", new Object[]{EMAIL_FIELD});
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, GFT_IDENTIFIER_FIELD, "employeeDTO.emptyField", new Object[]{GFT_IDENTIFIER_FIELD});
        ValidationUtils.rejectIfEmpty(errors, APPLICATION_ROLE_FIELD, "employeeDTO.emptyField", new Object[]{APPLICATION_ROLE_FIELD});
        ValidationUtils.rejectIfEmpty(errors, JOB_LEVEL_FIELD, "employeeDTO.emptyField", new Object[]{JOB_LEVEL_FIELD});

        EmployeeDTO employeeDTO = (EmployeeDTO) target;

        if (employeeDTO.getGftIdentifier() != null && employeeDTO.getGftIdentifier().length() != 4) {
            errors.rejectValue(GFT_IDENTIFIER_FIELD, "employeeDTO.gftIdentifierLength");
        }

        if (employeeDTO.getApplicationRole() != null) {
            errors.pushNestedPath("applicationRole");
            this.compositeValidator.validate(employeeDTO.getApplicationRole(), errors);
            errors.popNestedPath();
        }

        if (employeeDTO.getJobLevel() != null) {
            errors.pushNestedPath("jobLevel");
            this.compositeValidator.validate(employeeDTO.getJobLevel(), errors);
            errors.popNestedPath();
        }
    }

    @Override
    public void validate(Object target, HttpMethod httpMethod, Errors errors) {
        validate(target, errors);
    }
}
