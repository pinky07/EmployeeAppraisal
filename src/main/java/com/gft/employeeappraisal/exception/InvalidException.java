package com.gft.employeeappraisal.exception;

import org.springframework.validation.BindingResult;

/**
 * Exception thrown when the Validator Service finds an invalid objects.
 *
 * @author Ruben Jimenez
 */
public class InvalidException extends EmployeeAppraisalMicroserviceException {

    private BindingResult bindingResult;

    /**
     * Creates an instance of InvalidException.
     *
     * @param bindingResult Result that contains the validation errors.
     */
    public InvalidException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    /**
     * Returns the BindingResult with errors associated with this exception.
     *
     * @return BindingResult with errors
     */
    public BindingResult getBindingResult() {
        return this.bindingResult;
    }
}
