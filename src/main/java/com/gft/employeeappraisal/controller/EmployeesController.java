package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.converter.employee.EmployeeDTOConverter;
import com.gft.employeeappraisal.converter.employeerelationship.EmployeeRelationshipDTOConverter;
import com.gft.employeeappraisal.converter.relationship.RelationshipDTOConverter;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Constants;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.service.*;
import com.gft.swagger.employees.api.EmployeeApi;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import com.gft.swagger.employees.model.RelationshipDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    private final Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    private EmployeeService employeeService;
    private EmployeeDTOConverter employeeDTOConverter;
    private EmployeeRelationshipDTOConverter employeeRelationshipDTOConverter;
    private EmployeeRelationshipService employeeRelationshipService;
    private RelationshipDTOConverter relationshipDTOConverter;
    private RelationshipService relationshipService;
    private SecurityService securityService;
    private ValidationService validationService;

    @Autowired
    public EmployeesController(
            EmployeeService employeeService,
            EmployeeDTOConverter employeeDTOConverter,
            EmployeeRelationshipDTOConverter employeeRelationshipDTOConverter,
            EmployeeRelationshipService employeeRelationshipService,
            RelationshipDTOConverter relationshipDTOConverter,
            RelationshipService relationshipService,
            SecurityService securityService,
            ValidationService validationService) {
        this.employeeService = employeeService;
        this.employeeDTOConverter = employeeDTOConverter;
        this.employeeRelationshipDTOConverter = employeeRelationshipDTOConverter;
        this.employeeRelationshipService = employeeRelationshipService;
        this.relationshipDTOConverter = relationshipDTOConverter;
        this.relationshipService = relationshipService;
        this.securityService = securityService;
        this.validationService = validationService;
    }

    @Override
    public ResponseEntity<List<EmployeeDTO>> employeesGet(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

        logger.debug("GET endpoint: /employees/ with name: {} page: {} and size: {}", name, page, size);

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        //employeeService.findPagedByFirstNameOrLastName();

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> employeesIdDelete() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<EmployeeDTO> employeesIdGet(
            @PathVariable("employeeId") Integer employeeId) {

        // Get the logged in Employee
        Employee user = this.employeeService.getLoggedInUser();

        // This method will throw an Exception if the user can't access the Employee information
        securityService.canReadEmployee(user.getId(), employeeId);

        Employee employee = employeeService.findById(employeeId).orElseThrow(
                () -> new NotFoundException(
                        String.format("Employee with id %d was not found",
                                employeeId)));

        EmployeeDTO response = employeeDTOConverter.convert(employee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> employeesIdMenteesGet(
            @PathVariable("employeeId") Integer employeeId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> employeesIdMenteesPost(
            @PathVariable("employeeId") Integer employeeId) {
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
            @PathVariable("employeeId") Integer employeeId)
            throws NotFoundException {

        // Find Mentor
        Employee mentor = employeeService.findCurrentMentorById(employeeId)
                .orElseThrow(() -> new NotFoundException(String.format(
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
            @PathVariable("employeeId") Integer employeeId,
            @RequestBody EmployeeDTO newMentorDTO) {

        // TODO We don't need a complete Employee object for this endpoint!

        // Response object
        OperationResultDTO response = new OperationResultDTO();

        // Validate parameters
        this.validationService.validate(newMentorDTO);

        // Find Employee
        Employee employee = employeeService.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Employee with Id %d couldn't be found",
                        employeeId)));

        // Find Mentor to be
        Employee newMentor = employeeService.findById(newMentorDTO.getId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Employee with Id %d couldn't be found therefore it cannot be put as Mentor",
                        employeeId)));

        // Change Mentor
        employeeRelationshipService.changeMentor(newMentor, employee);

        // Return response
        response.setMessage(Constants.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> employeesIdPut(
            @RequestBody EmployeeDTO employee) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<EmployeeRelationshipDTO>> employeesIdRelationshipsGet(
            @PathVariable("employeeId") Integer employeeId,
            @RequestParam(value = "exclude", required = false) List<String> exclude,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "current", required = false) Boolean current) {

        // Get the logged in Employee
        Employee user = this.employeeService.getLoggedInUser();

        // This method will throw an Exception if the user can't access the Employee information
        securityService.canReadEmployee(user.getId(), employeeId);

        Employee employee = employeeService.findById(employeeId).orElseThrow(
                () -> new NotFoundException(
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
     * Creates a new Employee into the system.
     *
     * @param employeeDTOIn {@link EmployeeDTO} entity with the new employee's information (refer to Swagger documentation for syntaxis).
     * @return {@link OperationResultDTO} Entity with any possible validation error messages, or success.
     */
    @Override
    public ResponseEntity<OperationResultDTO> employeesPost(
            @RequestBody EmployeeDTO employeeDTOIn) {

        OperationResultDTO response = new OperationResultDTO();
        HttpStatus httpStatus;

        this.validationService.validate(employeeDTOIn, HttpMethod.POST);

        Optional<Employee> lookupEmployee = employeeService.findByEmail(employeeDTOIn.getEmail());

        if (lookupEmployee.isPresent()) {
            response.setMessage(Constants.ERROR);
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else {

            Optional<Employee> createdEmployee = employeeService.saveAndFlush(employeeDTOConverter.convertBack(employeeDTOIn));

            if (createdEmployee.isPresent()) {
                response.setMessage(Constants.SUCCESS);
                response.setData(employeeDTOConverter.convert(createdEmployee.get()));
                httpStatus = HttpStatus.CREATED;
            } else {
                response.setMessage(Constants.ERROR);
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            }
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    public ResponseEntity<Void> employeesPut(
            @RequestBody EmployeeDTO employee) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<RelationshipDTO>> relationshipsGet() {
        logger.debug("GET endpoint: /relationships/");

        List<RelationshipDTO> relationshipDTOList = new ArrayList<>();

        // We will omit other relationship types for now. Plus this might require a filter.
        relationshipService
                .findRelationshipsByNames(RelationshipName.LEAD,
                        RelationshipName.PEER,
                        RelationshipName.OTHER)
                .forEach(r -> relationshipDTOList.add(relationshipDTOConverter.convert(r)));

        return new ResponseEntity<>(relationshipDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RelationshipDTO> relationshipsIdGet(@PathVariable Integer relationshipId) {
        logger.debug("GET endpoint: /relationships/{}", relationshipId);

        RelationshipDTO relationship = relationshipService.findById(relationshipId)
                .map(r -> relationshipDTOConverter.convert(r))
                .orElseThrow(() -> new NotFoundException(String.format("Relationship with id %d was not found",
                        relationshipId)));

        return new ResponseEntity<>(relationship, HttpStatus.OK);
    }
}
