package com.gft.employeeappraisal.converter.validator;

import com.gft.swagger.employees.model.JobLevelDTO;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Class that describes validation rules for the conversion from a JobLevelDTO to a JobLevel entity.
 *
 * @author Manuel Yepez
 */
public class JobLevelDTOToEntityValidator implements DTOValidator<JobLevelDTO> {

	private MutablePropertyValues values;

	private static final String ID_FIELD = "id";

	public JobLevelDTOToEntityValidator() { this.values = new MutablePropertyValues(); }

	/**
	 * @see DTOValidator#getPropertyValues()
	 * @return Initialized PropertyValues.
	 */
	@Override
	public MutablePropertyValues getPropertyValues() {
		return this.values;
	}

	/**
	 * Maps the ID_FIELD to the DTO's getId method.
	 *
	 * @see DTOValidator#setPropertyValues(Object)
	 * @param dtoObject Target DTO object to be validated.
	 */
	@Override
	public void setPropertyValues(JobLevelDTO dtoObject) {
		values.addPropertyValue(ID_FIELD, dtoObject.getId());
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return JobLevelDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, ID_FIELD, "employeeDTO.emptyField", new Object[]{ID_FIELD});
	}
}
