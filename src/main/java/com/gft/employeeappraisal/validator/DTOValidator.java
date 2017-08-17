package com.gft.employeeappraisal.validator;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.Validator;

/**
 * Interface designed to provide DTO Validators with MutablePropertyValues, which are used by Spring Validation
 * to map FieldErrors to DTO getter fields.
 *
 * @author Manuel Yepez
 */
public interface DTOValidator<T> extends Validator {
	/**
	 * Getter for the MutablePropertyValues initialized on the Validator implementations.
	 * @return PropertyValues with mapped fields.
	 */
	MutablePropertyValues getPropertyValues();

	/**
	 * Implementations of this method should map the corresponding PropertyValues to the target objects' getter methods.
	 * @param dtoObject Target DTO object to be validated.
	 */
	void setPropertyValues(T dtoObject);
}
