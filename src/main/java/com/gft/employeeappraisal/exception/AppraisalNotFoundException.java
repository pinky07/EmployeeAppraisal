package com.gft.employeeappraisal.exception;

/**
 * Exception to throw anytime that an appraisal is not found in the database.
 *
 * @author Ruben Jimenez
 */
public class AppraisalNotFoundException extends EmployeeAppraisalMicroserviceException {

    /**
     * Creates an instance of EmployeeNotFoundException.
     *
     * @param message Message contained in the Exception
     */
    public AppraisalNotFoundException(String message) {
        super(message);
    }
}
