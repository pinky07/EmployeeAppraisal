package com.gft.employeeappraisal.exception;

/**
 * Exception to throw anytime that an user is not found in the database.
 *
 * @author Ruben Jimenez
 */
public class NotFoundException extends EmployeeAppraisalMicroserviceException {

    /**
     * Creates an instance of NotFoundException.
     *
     * @param message Message contained in the Exception
     */
    public NotFoundException(String message) {
        super(message);
    }
}
