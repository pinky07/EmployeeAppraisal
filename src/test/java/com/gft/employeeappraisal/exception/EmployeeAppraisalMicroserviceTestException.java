package com.gft.employeeappraisal.exception;


/**
 * Thrown when a test fails and should throw an exception
 *
 * @author Rubën Jiménez
 */
public class EmployeeAppraisalMicroserviceTestException extends EmployeeAppraisalMicroserviceException {

    /**
     * Creates an instance of EmployeeAppraisalMicroserviceTestException.
     *
     * @param message Message contained in the Exception
     */
    public EmployeeAppraisalMicroserviceTestException(String message) {
        super(message);
    }
}
