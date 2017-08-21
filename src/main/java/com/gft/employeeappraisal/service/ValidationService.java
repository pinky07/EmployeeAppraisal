package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.InvalidException;
import com.gft.employeeappraisal.validator.DTOValidator;
import com.gft.swagger.employees.model.FieldErrorDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Validates objects on demand
 *
 * @author Rubén Jiménez
 */
public interface ValidationService {

    /**
     * Method used to fill a BindingResult with validation errors.
     *
     * @param target    Object to be validated
     * @param validator {@link DTOValidator} Validator instance for the object. Must have PropertyValues associated on its implementation.
     * @param <T>       Template class of the validating object
     * @return BindingResult with a collection of validation errors.
     */
    <T> void validate(T target, DTOValidator<T> validator) throws InvalidException;

    /**
     * Returns a list of {@link FieldErrorDTO}.
     *
     * @return ResponseEntity object containing an {@link OperationResultDTO} OperationResultDTO with validation errors.
     */
    List<FieldErrorDTO> getErrorList(BindingResult bindingResult);
}
