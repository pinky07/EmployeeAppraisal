package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.InvalidException;
import com.gft.employeeappraisal.service.ValidationService;
import com.gft.employeeappraisal.validator.CompositeValidator;
import com.gft.swagger.employees.model.FieldErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation of {@link ValidationService}
 *
 * @author Ruben Jimenez
 */
@Service
public class ValidationServiceImpl implements ValidationService {

    private MessageSource messageSource;
    private CompositeValidator compositeValidator;

    @Autowired
    public ValidationServiceImpl(MessageSource messageSource, CompositeValidator compositeValidator) {
        this.messageSource = messageSource;
        this.compositeValidator = compositeValidator;
    }

    @Override
    public void validate(Object target) throws InvalidException {
        BindingResult errors = new BeanPropertyBindingResult(target, target.getClass().toString());
        compositeValidator.validate(target, errors);
        if (errors.hasErrors()) {
            throw new InvalidException("Object is not valid", errors);
        }
    }

    @Override
    public void validate(Object target, HttpMethod httpMethod) throws InvalidException {
        BindingResult errors = new BeanPropertyBindingResult(target, target.getClass().toString());
        compositeValidator.validate(target, httpMethod, errors);
        if (errors.hasErrors()) {
            throw new InvalidException("Object is not valid", errors);
        }
    }

    @Override
    public List<FieldErrorDTO> getFieldErrorDTOList(Errors errors) {
        List<FieldErrorDTO> errorList = new ArrayList<>();
        for (FieldError fieldError : errors.getFieldErrors()) {
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO();
            fieldErrorDTO.setField(fieldError.getField());
            fieldErrorDTO.setMessage(messageSource.getMessage(fieldError, null));
            errorList.add(fieldErrorDTO);
        }
        return errorList;
    }
}
