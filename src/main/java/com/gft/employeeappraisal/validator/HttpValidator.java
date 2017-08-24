package com.gft.employeeappraisal.validator;

import org.springframework.http.HttpMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface HttpValidator extends Validator {

    /**
     * TODO Document this!
     *
     * @param httpMethod
     * @return
     */
    boolean supports(HttpMethod httpMethod);

    /**
     * TODO Document this!
     *
     * @param target
     * @param httpMethod
     * @param errors
     */
    void validate(Object target, HttpMethod httpMethod, Errors errors);
}
