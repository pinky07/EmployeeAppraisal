package com.gft.employeeappraisal.exception;

import org.springframework.validation.Errors;

/**
 * Exception thrown when the Validator Service finds an invalid objects.
 *
 * @author Ruben Jimenez
 */
public class InvalidException extends EmployeeAppraisalMicroserviceException {

    private Errors errors;

    /**
     * Creates an instance of InvalidException.
     *
     * @param errors Validation Errors
     */
    public InvalidException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
