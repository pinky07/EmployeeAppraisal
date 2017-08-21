package com.gft.employeeappraisal.validator;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.Validator;

/**
 * Interface designed to provide DTO Validators with MutablePropertyValues, which are used by Spring Validation
 * to map FieldErrors to DTO getter fields.
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
public interface DTOValidator<T> extends Validator {

    /**
     * Maps the corresponding PropertyValues to the getter methods of the target object.
     *
     * @param targetObject Target object to be validated.
     * @return PropertyValues with mapped fields.
     */
    MutablePropertyValues getPropertyValues(T targetObject);
}
