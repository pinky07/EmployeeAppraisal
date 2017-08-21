package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.InvalidException;
import com.gft.employeeappraisal.service.ValidationService;
import com.gft.employeeappraisal.validator.DTOValidator;
import com.gft.swagger.employees.model.FieldErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
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

    @Autowired
    public ValidationServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public <T> void validate(T target, DTOValidator<T> validator) throws InvalidException {

        DataBinder binder = new DataBinder(target);
        binder.setValidator(validator);
        binder.bind(validator.getPropertyValues(target));
        binder.validate();

        BindingResult bindingResult = binder.getBindingResult();
        if (bindingResult.hasErrors()) {
            throw new InvalidException("Object is not valid", bindingResult);
        }
    }

    @Override
    public List<FieldErrorDTO> getErrorList(BindingResult bindingResult) {
        List<FieldErrorDTO> errorList = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO();
            fieldErrorDTO.setField(fieldError.getField());
            fieldErrorDTO.setMessage(messageSource.getMessage(fieldError, null));
            errorList.add(fieldErrorDTO);
        }
        return errorList;
    }
}
