package com.gft.employeeappraisal.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Set;

/**
 * This Validator is meant to be used from any other Validators and from the Validation Service. It contains a Set of
 * all Validators implementing the HttpValidator interface and it's smart enough to decide which Validator to use when
 * its validate method is called.
 *
 * @author Rubén Jiménez
 */
@Component
public class CompositeValidator implements HttpValidator {

    private final Set<HttpValidator> availableValidators;

    @Autowired
    public CompositeValidator(@Lazy Set<HttpValidator> validatorSet) {
        this.availableValidators = validatorSet;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public boolean supports(HttpMethod httpMethod) {
        return true;
    }


    @Override
    public void validate(Object target, Errors errors) {
        this.availableValidators
                .stream()
                .filter(validator -> validator.supports(target.getClass()))
                .forEach(validator -> validator.validate(target, errors));
    }

    @Override
    public void validate(Object target, HttpMethod httpMethod, Errors errors) {
        this.availableValidators
                .stream()
                .filter(validator -> validator.supports(target.getClass()))
                .filter(validator -> validator.supports(httpMethod))
                .forEach(validator -> validator.validate(target, errors));
    }
}
