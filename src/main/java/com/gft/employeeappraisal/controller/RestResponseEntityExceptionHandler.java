package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.exception.InvalidException;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Constants;
import com.gft.employeeappraisal.service.ValidationService;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class that will handle exceptions thrown by the controller classes belonging to the EmployeeAppraisal microservice.
 *
 * @author Ruben Jimenez
 * @author Manuel Yepez
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    private ValidationService validationService;

    @Autowired
    public RestResponseEntityExceptionHandler(ValidationService validationService) {
        this.validationService = validationService;
    }

    /**
     * Handles exceptions of type {@link NotFoundException} coming from the controllers that support it.
     *
     * @param exception  Exception received from the controller.
     * @param webRequest Request that triggered the exception.
     * @return {@link OperationResultDTO} Object containing the error messages.
     */
    @ExceptionHandler(value = {NotFoundException.class})
    private ResponseEntity<OperationResultDTO> handleNotFoundException(Exception exception, WebRequest webRequest) {
        logger.error("Handling NotFoundException: {}", exception);
        OperationResultDTO operationResultDTO = new OperationResultDTO();
        operationResultDTO.setMessage(Constants.ERROR);
        operationResultDTO.setData(exception.getMessage());
        return new ResponseEntity<>(operationResultDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions of type {@link AccessDeniedException} coming from the controllers that support it.
     *
     * @param exception  Exception received from the controller.
     * @param webRequest Request that triggered the exception.
     * @return {@link OperationResultDTO} Object containing the error messages.
     */
    @ExceptionHandler(value = {AccessDeniedException.class})
    private ResponseEntity<OperationResultDTO> handleAccessDeniedException(Exception exception, WebRequest webRequest) {
        logger.error("Handling AccessDeniedException: {}", exception);
        OperationResultDTO operationResultDTO = new OperationResultDTO();
        operationResultDTO.setMessage(Constants.ERROR);
        operationResultDTO.setData(exception.getMessage());
        return new ResponseEntity<>(operationResultDTO, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles exceptions of type {@link InvalidException} coming from the controllers that support it.
     *
     * @param exception  Exception received from the controller.
     * @param webRequest Request that triggered the exception.
     * @return {@link OperationResultDTO} Object containing the error messages.
     */
    @ExceptionHandler(value = {InvalidException.class})
    private ResponseEntity<OperationResultDTO> handleInvalidException(Exception exception, WebRequest webRequest) {
        logger.error("Handling InvalidException: {}", exception);
        InvalidException invalidException = (InvalidException) exception;
        OperationResultDTO operationResultDTO = new OperationResultDTO();
        operationResultDTO.setMessage(Constants.ERROR);
        operationResultDTO.setErrors(this.validationService.getFieldErrorDTOList(invalidException.getErrors()));
        return new ResponseEntity<>(operationResultDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions (RuntimeExceptions) coming from the controllers that support it.
     * Until Swagger Codegen supports exceptions thrown from the controller methods, this will be handled this way.
     *
     * @param exception  Exception received from the controller.
     * @param webRequest Request that triggered the exception.
     * @return {@link OperationResultDTO} Object containing the error messages.
     */
    @ExceptionHandler(value = {RuntimeException.class})
    private ResponseEntity<Object> handleRuntimeException(Exception exception, WebRequest webRequest) {
        logger.error("Handling Exception: {}", exception);
        OperationResultDTO operationResultDTO = new OperationResultDTO();
        operationResultDTO.setMessage(Constants.ERROR);
        operationResultDTO.setData(Constants.INTERNAL_SERVER_ERROR_MESSAGE);
        return new ResponseEntity<>(operationResultDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
