package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.InvalidException;
import com.gft.swagger.employees.model.FieldErrorDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.springframework.http.HttpMethod;
import org.springframework.validation.Errors;

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
     * Validates the target object, according to the Http Method from which it was received. If a validation error
     * occurs, throws an InvalidException.
     *
     * @param target     Object to be validated
     * @param httpMethod Http method in which the object was received
     */
    void validate(Object target, HttpMethod httpMethod) throws InvalidException;

    /**
     * Returns a list of {@link FieldErrorDTO}.
     *
     * @return ResponseEntity object containing an {@link OperationResultDTO} OperationResultDTO with validation errors.
     */
    List<FieldErrorDTO> getFieldErrorDTOList(Errors errors);
}
