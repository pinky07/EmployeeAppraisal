package com.gft.employeeappraisal.validator;

import org.springframework.http.HttpMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator extension to support Validation according to the Http Method used to send the DTO to the server.
 *
 * @author Rubén Jiménez
 */
public interface HttpValidator extends Validator {

    /**
     * Whether the Validator supports the Http Method passed as parameter.
     *
     * @param httpMethod Http Method
     * @return True if the Http Method is supported.
     */
    boolean supports(HttpMethod httpMethod);

    /**
     * Validates the Target object and fills the Errors object if any are found.
     *
     * @param target     Object to validate
     * @param httpMethod Http Method used to send the DTO
     * @param errors     Errors object
     */
    void validate(Object target, HttpMethod httpMethod, Errors errors);
}
