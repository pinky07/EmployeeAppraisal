package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.converter.employee.EmployeeDTOConverter;
import com.gft.employeeappraisal.converter.employeerelationship.EmployeeRelationshipDTOConverter;
import com.gft.employeeappraisal.exception.EmployeeNotFoundException;
import com.gft.employeeappraisal.model.Constants;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.employeeappraisal.validator.DTOValidator;
import com.gft.employeeappraisal.validator.EmployeeDTOToEntityCreateValidator;
import com.gft.swagger.employees.api.EmployeeApi;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.FieldErrorDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller class that implements the /employees API endpoints generated by Swagger via its interface.
 *
 * @author Ruben Jimenez
 * @author Manuel Yepez
 */
@Controller
public class EmployeesController implements EmployeeApi {

    private Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeDTOConverter employeeDTOConverter;

    @Autowired
    private EmployeeRelationshipDTOConverter employeeRelationshipDTOConverter;

    @Autowired
    private EmployeeRelationshipService employeeRelationshipService;

    @Autowired
    private EmployeeDTOToEntityCreateValidator employeeDTOToEntityCreateValidator;

    @Autowired
    private MessageSource messageSource;

    @Override
    public ResponseEntity<List<EmployeeDTO>> employeesGet(
            @ApiParam(value = "Page number. Defaults to 1. ", defaultValue = "1") @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "1") Integer page,
            @ApiParam(value = "Number of elements in each page. Defaults to 10. ", defaultValue = "10") @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") Integer size) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> employeesIdDelete() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<EmployeeDTO> employeesIdGet(
            @ApiParam(value = "`Employee` Id. ", required = true) @PathVariable("employeeId") Integer employeeId) {

        // Get the logged in Employee
        Employee user = this.employeeService.getLoggedInUser();

        // This method will throw an Exception if the user can't access the Employee information
        employeeService.checkAccess(user.getId(), employeeId);

        Employee employee = employeeService.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException(
                        String.format("Employee with id %d was not found",
                                employeeId)));

        EmployeeDTO response = employeeDTOConverter.convert(employee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> employeesIdMenteesGet(
            @ApiParam(value = "`Employee` Id. ", required = true) @PathVariable("employeeId") Integer employeeId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> employeesIdMenteesPost(
            @ApiParam(value = "`Employee` Id. ", required = true) @PathVariable("employeeId") Integer employeeId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Given an employeeId, returns the current Mentor associated to that employee.
     *
     * @param employeeId ID of the Employee to look up.
     * @return {@link EmployeeDTO} Mentor in the form on an EmployeeDTO object.
     */
    @Override
    public ResponseEntity<EmployeeDTO> employeesIdMentorGet(
            @ApiParam(value = "`Employee` Id. ", required = true)
            @PathVariable("employeeId") Integer employeeId)
            throws EmployeeNotFoundException {

        // Find Mentor
        Employee mentor = employeeService.findCurrentMentorById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(
                        "Mentor for Employee with Id %d was not found",
                        employeeId)));

        // Set the response
        EmployeeDTO response = employeeDTOConverter.convert(mentor);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates the mentor associated to an employee.
     *
     * @param employeeId   ID of the employee to be updated.
     * @param newMentorDTO Mentor entity {@link EmployeeDTO} to associate to the employee.
     * @return {@link OperationResultDTO} Entity with any possible validation error messages, or success.
     */
    @Override
    public ResponseEntity<OperationResultDTO> employeesIdMentorPut(
            @ApiParam(value = "`Employee` Id. ", required = true) @PathVariable("employeeId") Integer employeeId,
            @RequestBody EmployeeDTO newMentorDTO) {

        // TODO We don't need a complete Employee object for this endpoint!

        // Response object
        OperationResultDTO response = new OperationResultDTO();

        // Validate parameters
        BindingResult result = validate(newMentorDTO, employeeDTOToEntityCreateValidator);
        if (result.hasErrors()) {
            return buildErrorMessage(result);
        }

        // Find Employee
        Employee employee = employeeService.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(
                        "Employee with Id %d couldn't be found",
                        employeeId)));

        // Find Mentor to be
        Employee newMentor = employeeService.findById(newMentorDTO.getId())
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(
                        "Employee with Id %d couldn't be found therefore it cannot be put as Mentor",
                        employeeId)));

        // Change Mentor
        employeeRelationshipService.changeMentor(newMentor, employee);

        // Return response
        response.setMessage(Constants.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> employeesIdPut(EmployeeDTO employee) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<EmployeeRelationshipDTO>> employeesIdRelationshipsGet(@PathVariable("employeeId")
                                                                                             Integer employeeId, @RequestParam(value = "exclude", required = false) List<String> exclude,
                                                                                     String startDate, String endDate, Boolean current) {
        // Get the logged in Employee
        Employee user = this.employeeService.getLoggedInUser();

        // This method will throw an Exception if the user can't access the Employee information
        employeeService.checkAccess(user.getId(), employeeId);

        Employee employee = employeeService.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException(
                        String.format("Employee with id %d was not found",
                                employeeId)));

        List<EmployeeRelationshipDTO> employeeRelationshipDTOList = new ArrayList<>();
        employeeService.findCurrentRelationshipsBySourceEmployee(employee,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER)
                .forEach(er -> employeeRelationshipDTOList.add(employeeRelationshipDTOConverter.convert(er)));

        return new ResponseEntity<>(employeeRelationshipDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> employeesIdRelationshipsIdDelete(
            @PathVariable("employeeId") Integer employeeId,
            @PathVariable("relationshipId") Integer relationshipId) {

        // TODO Implement this!

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OperationResultDTO> employeesIdRelationshipsPost(
            @PathVariable("employeeId") Integer employeeId,
            @RequestBody EmployeeRelationshipDTO relationship) {

        // TODO Implement this!

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Creates a new employee into the system.
     *
     * @param employee {@link EmployeeDTO} entity with the new employee's information (refer to Swagger documentation for syntaxis).
     * @return {@link OperationResultDTO} Entity with any possible validation error messages, or success.
     */
    @Override
    public ResponseEntity<OperationResultDTO> employeesPost(@RequestBody EmployeeDTO employee) {
        OperationResultDTO response = new OperationResultDTO();
        HttpStatus status;
        BindingResult result = validate(employee, employeeDTOToEntityCreateValidator);
        if (result.hasErrors()) {
            return buildErrorMessage(result);
        }

        Optional<Employee> lookupEmployee = employeeService.findByEmail(employee.getEmail());
        if (lookupEmployee.isPresent()) {
            response.setMessage(Constants.ERROR);
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            return new ResponseEntity<>(response, status);
        }

        Optional<Employee> createdEmployee = employeeService.saveAndFlush(employeeDTOConverter.convertBack(employee));
        EmployeeDTO employeeDTO = null;

        if (createdEmployee.isPresent()) {
            response.setMessage(Constants.SUCCESS);

            employeeDTO = employeeDTOConverter.convert(createdEmployee.get());

            status = HttpStatus.CREATED;
        } else {
            response.setMessage(Constants.ERROR);
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        }

        response.setData(employeeDTO);
        return new ResponseEntity<>(response, status);
    }

    @Override
    public ResponseEntity<Void> employeesPut(EmployeeDTO postEmployee) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Method used to fill a BindingResult with validation errors.
     *
     * @param target    Object to be validated
     * @param validator {@link DTOValidator} Validator instance for the object. Must have PropertyValues associated on its implementation.
     * @param <T>       Template class of the validating object
     * @return BindingResult with a collection of validation errors.
     */
    private <T> BindingResult validate(T target, DTOValidator<T> validator) {
        DataBinder binder = new DataBinder(target);
        binder.setValidator(validator);

        validator.setPropertyValues(target);
        binder.bind(validator.getPropertyValues());

        binder.validate();

        return binder.getBindingResult();
    }

    /**
     * Method that given a BindingResult containing errors, constructs an OperationResultDTO with
     * the corresponding error messages, coming from a properties file.
     *
     * @param resultWithErrors BindingResult object with errors
     * @return ResponseEntity object containing an {@link OperationResultDTO} OperationResultDTO with validation errors.
     */
    private ResponseEntity<OperationResultDTO> buildErrorMessage(BindingResult resultWithErrors) {
        OperationResultDTO response = new OperationResultDTO();
        List<FieldErrorDTO> fieldErrorDTOList = new ArrayList<>();
        response.setMessage(Constants.ERROR);
        for (FieldError fieldError : resultWithErrors.getFieldErrors()) {
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO();
            fieldErrorDTO.setField(fieldError.getField());
            fieldErrorDTO.setMessage(messageSource.getMessage(fieldError, null));
            fieldErrorDTOList.add(fieldErrorDTO);
            logger.debug("field: " + fieldError.getField() + " message: " + messageSource.getMessage(fieldError, null));
        }
        response.setErrors(fieldErrorDTOList);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
