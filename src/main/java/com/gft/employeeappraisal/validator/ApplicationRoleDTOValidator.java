package com.gft.employeeappraisal.validator;

import com.gft.swagger.employees.model.ApplicationRoleDTO;
import org.springframework.beans.MutablePropertyValues;
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
public class ApplicationRoleDTOValidator implements DTOValidator<ApplicationRoleDTO> {

    private static final String ID_FIELD = "id";

    @Override
    public boolean supports(Class<?> clazz) {
        return ApplicationRoleDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, ID_FIELD, "employeeDTO.emptyField", new Object[]{ID_FIELD});
    }

    @Override
    public MutablePropertyValues getPropertyValues(ApplicationRoleDTO dtoObject) {
        MutablePropertyValues values = new MutablePropertyValues();
        values.addPropertyValue(ID_FIELD, dtoObject.getId());
        return values;
    }
}
