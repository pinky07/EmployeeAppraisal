package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.exception.EmployeeNotFoundException;
import com.gft.employeeappraisal.model.Constants;
import com.gft.swagger.employees.model.OperationResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * Handles exceptions of type {@link EmployeeNotFoundException} coming from the controllers that support it.
     *
     * @param ex      Exception received from the controller.
     * @param request Request that triggered the exception.
     * @return {@link OperationResultDTO} Object containing the error messages.
     */
    @ExceptionHandler(value = {EmployeeNotFoundException.class})
    private ResponseEntity<OperationResultDTO> handleEmployeeNotFoundException(Exception ex, WebRequest request) {
        logger.error("Handling exception: {}", ex);
        OperationResultDTO operationResultDTO = new OperationResultDTO();
        operationResultDTO.setMessage(Constants.ERROR);
        operationResultDTO.setData(ex.getMessage());
        return new ResponseEntity<>(operationResultDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions of type {@link AccessDeniedException} coming from the controllers that support it.
     *
     * @param ex      Exception received from the controller.
     * @param request Request that triggered the exception.
     * @return {@link OperationResultDTO} Object containing the error messages.
     */
    @ExceptionHandler(value = {AccessDeniedException.class})
    private ResponseEntity<OperationResultDTO> handleAccessDeniedException(Exception ex, WebRequest request) {
        logger.error("Handling exception: {}", ex);
        OperationResultDTO operationResultDTO = new OperationResultDTO();
        operationResultDTO.setMessage(Constants.ERROR);
        operationResultDTO.setData(ex.getMessage());
        return new ResponseEntity<>(operationResultDTO, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles all other exceptions (RuntimeExceptions) coming from the controllers that support it.
     * Until Swagger Codegen supports exceptions thrown from the controller methods, this will be handled this way.
     *
     * @param ex      Exception received from the controller.
     * @param request Request that triggered the exception.
     * @return {@link OperationResultDTO} Object containing the error messages.
     */
    @ExceptionHandler(value = {RuntimeException.class})
    private ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request) {
        logger.error("Handling exception: {}", ex);
        OperationResultDTO operationResultDTO = new OperationResultDTO();
        operationResultDTO.setMessage(Constants.ERROR);
        operationResultDTO.setData(Constants.INTERNAL_SERVER_ERROR_MESSAGE);
        return new ResponseEntity<>(operationResultDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
