package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.InvalidException;
import com.gft.swagger.employees.model.FieldErrorDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Validates objects on demand
 *
 * @author Rubén Jiménez
 */
public interface ValidationService {

    /**
     * Validates the target object. If a validation error occurs, throws an InvalidException.
     *
     * @param target Object to be validated
     */
    void validate(Object target) throws InvalidException;

    /**
     * Validates the target object, relies on an errors object passed as parameter. Use only in classes implementing the
     * {@link Validator interface} since it will be a nested call of {@link ValidationService#validate(Object)}
     *
     * @param target Object to be validated
     * @param errors Errors object to use
     */
    void validate(Object target, Errors errors);

    /**
     * Returns a list of {@link FieldErrorDTO}.
     *
     * @return ResponseEntity object containing an {@link OperationResultDTO} OperationResultDTO with validation errors.
     */
    List<FieldErrorDTO> getFieldErrorDTOList(Errors errors);
}
