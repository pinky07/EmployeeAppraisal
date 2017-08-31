package com.gft.employeeappraisal.exception;

/**
 * Wraps all business exceptions.
 *
 * @author Ruben Jimenez
 */
public class EmployeeAppraisalMicroserviceException extends RuntimeException {
    // TODO Remember to change RuntimeException to Exception when Swagger Codegen Spring supports throwing Exceptions
    // TODO Swagger Codegen v2.2.2 does not allow API generated methods to throw exceptions.
    // TODO A new feature planned for v2.3.0 will allow this.

    /**
     * Creates an instance of EmployeeAppraisalMicroserviceException.
     *
     * @param message Message contained in the Exception
     */
    public EmployeeAppraisalMicroserviceException(String message) {
        super(message);
    }
}
