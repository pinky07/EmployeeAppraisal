package com.gft.employeeappraisal.exception;

/**
 * Thrown when an Employee requests a resource which he doesn't have access to
 *
 * @author Ruben Jimenez
 */
public class AccessDeniedException extends EmployeeMicroserviceException {

    /**
     * Creates an instance of AccessDeniedException.
     *
     * @param message Message contained in the Exception
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
