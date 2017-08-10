package com.gft.employeeappraisal.exception;

/**
 * Exception to throw anytime that an user is not found in the database.
 *
 * @author Ruben Jimenez
 */
public class EmployeeNotFoundException extends EmployeeAppraisalMicroserviceException {

    /**
     * Creates an instance of EmployeeNotFoundException.
     *
     * @param message Message contained in the Exception
     */
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
