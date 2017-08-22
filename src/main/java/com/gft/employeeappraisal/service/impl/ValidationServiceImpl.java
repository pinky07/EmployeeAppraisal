package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.InvalidException;
import com.gft.employeeappraisal.service.ValidationService;
import com.gft.swagger.employees.model.FieldErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.*;

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
    private Validator validator;

    @Autowired
    public ValidationServiceImpl(MessageSource messageSource, Validator validator) {
        this.messageSource = messageSource;
        this.validator = validator;
    }

    @Override
    public void validate(Object target) throws InvalidException {
        BindingResult errors = new BeanPropertyBindingResult(target, target.getClass().toString());
        validator.validate(target, errors);
        if (errors.hasErrors()) {
            throw new InvalidException("Object is not valid", errors);
        }
    }

    @Override
    public void validate(Object target, Errors errors) {
        validator.validate(target, errors);
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
