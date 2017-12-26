package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.converter.employee.EmployeeDTOConverter;
import com.gft.employeeappraisal.converter.employeerelationship.EmployeeRelationshipDTOConverter;
import com.gft.employeeappraisal.converter.relationshiptype.RelationshipTypeDTOConverter;
import com.gft.employeeappraisal.exception.AccessDeniedException;
import com.gft.employeeappraisal.exception.EmployeeAppraisalMicroserviceException;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Constants;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.service.*;
import com.gft.swagger.employees.api.EmployeeApi;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import com.gft.swagger.employees.model.OperationResultDTO;
import com.gft.swagger.employees.model.RelationshipTypeDTO;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    private final EmployeeService employeeService;
    private final EmployeeDTOConverter employeeDTOConverter;
    private final EmployeeRelationshipDTOConverter employeeRelationshipDTOConverter;
    private final EmployeeRelationshipService employeeRelationshipService;
    private final RelationshipTypeDTOConverter relationshipTypeDTOConverter;
    private final RelationshipTypeService relationshipTypeService;
    private final SecurityService securityService;
    private final ValidationService validationService;

    @Autowired
    public EmployeesController(
            EmployeeService employeeService,
            EmployeeDTOConverter employeeDTOConverter,
            EmployeeRelationshipDTOConverter employeeRelationshipDTOConverter,
            EmployeeRelationshipService employeeRelationshipService,
            RelationshipTypeDTOConverter relationshipTypeDTOConverter,
            RelationshipTypeService relationshipTypeService,
            SecurityService securityService,
            ValidationService validationService) {
        this.employeeService = employeeService;
        this.employeeDTOConverter = employeeDTOConverter;
        this.employeeRelationshipDTOConverter = employeeRelationshipDTOConverter;
        this.employeeRelationshipService = employeeRelationshipService;
        this.relationshipTypeDTOConverter = relationshipTypeDTOConverter;
        this.relationshipTypeService = relationshipTypeService;
        this.securityService = securityService;
        this.validationService = validationService;
    }

    @Override
    public ResponseEntity<List<EmployeeDTO>> employeesGet(
            @RequestParam(value = "searchTerm", required = false) String term,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

        logger.debug("GET endpoint: /employees/ with term: {} page: {} and size: {}", term, page, size);

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        if (term != null && !StringUtils.isEmpty(term.trim())) {
            logger.debug("Calling search term {}", term);
            // This is an OR search, so term must be on both first and last name.
            employeeService.findPagedByFirstNameOrLastName(term, term, page, size)
                    .forEach(e -> employeeDTOList.add(employeeDTOConverter.convert(e)));
        }

        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
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

        // Find Employee
        Employee employee = this.employeeService.getById(employeeId);

        // This method will throw an Exception if the user can't access the Employee information
        securityService.canReadEmployee(user, employee);

        EmployeeDTO response = employeeDTOConverter.convert(employee);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EmployeeDTO>> employeesIdMenteesGet(
            @PathVariable("employeeId") Integer employeeId) {
        // Get the logged in Employee
        Employee user = this.employeeService.getLoggedInUser();
        List<EmployeeDTO> result = new ArrayList<>();

        if (this.employeeService.isAdmin(user)) {
            // Find provided employee
            Employee mentor = this.employeeService.getById(employeeId);

            // Fetch mentees
            this.employeeService.findCurrentMenteesById(mentor.getId())
                    .forEach(mentee -> result.add(employeeDTOConverter.convert(mentee)));
        } else {
            throw new AccessDeniedException(String.format("User %s is not an admin and cannot execute this operation.",
                    user.getEmail()));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
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
        Employee mentor = this.employeeService.getCurrentMentorById(employeeId);

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

        // Response object
        OperationResultDTO response = new OperationResultDTO();

        // Validate parameters
        this.validationService.validate(newMentorDTO);
        String comments =newMentorDTO.getFirstName();
        // Find Employee
        Employee employee = this.employeeService.getById(employeeId);

        // Find Mentor to be
        Employee newMentor = this.employeeService.getById(newMentorDTO.getId());

        // Change Mentor
        employeeRelationshipService.changeMentor(newMentor, employee, comments);

        // Return response
        response.setMessage(Constants.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OperationResultDTO> employeesIdMentorDelete(
            @PathVariable("employeeId") Integer employeeId) {
        // Get the logged in Employee
        Employee user = this.employeeService.getLoggedInUser();
        OperationResultDTO response = new OperationResultDTO();

        if (this.employeeService.isAdmin(user)) {
            // Fetch employee
            Employee employee = this.employeeService.getById(employeeId);

            this.employeeRelationshipService.removeMentor(employee);
        } else {
            throw new AccessDeniedException(String.format("User %s is not an admin and cannot execute this operation.",
                    user.getEmail()));
        }

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
            @RequestParam(value = "exclude", required = false) String exclude,
            @RequestParam(value = "comments", required = false) Integer comments,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "current", required = false) Boolean current)

    {

        // Get the logged in Employee
        Employee user = this.employeeService.getLoggedInUser();

        // Find Employee
        Employee employee = this.employeeService.getById(employeeId);

        // Security check
        securityService.canReadEmployee(user, employee);

        List<EmployeeRelationshipDTO> employeeRelationshipDTOList = new ArrayList<>();
        employeeService.findCurrentRelationshipsBySourceEmployee(employee,
                RelationshipName.PEER, RelationshipName.LEAD, RelationshipName.OTHER)
                .forEach(er -> employeeRelationshipDTOList.add(employeeRelationshipDTOConverter.convert(er)));

        return new ResponseEntity<>(employeeRelationshipDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> employeesIdRelationshipsIdDelete(
            @PathVariable("employeeId") Integer employeeId,
            @PathVariable("relationshipId") Integer employeeRelationshipId) {

        // Get the logged in Employee
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: DELETE /employees/{}/relationships/{}", user.getEmail(), employeeId, employeeRelationshipId);

        // Find Employee
        Employee employee = this.employeeService.getById(employeeId);

        // Find EmployeeRelationship
        EmployeeRelationship employeeRelationship = this.employeeRelationshipService.getById(employeeRelationshipId);

        // Is the relationship already ended?
        if (Objects.nonNull(employeeRelationship.getEndDate())) {
            throw new EmployeeAppraisalMicroserviceException(String.format(
                    "EmployeeRelationship[%d] is already ended",
                    employeeRelationship.getId()));
        }

        // The Employee must be the SourceEmployee of the EmployeeRelationship
        if (!employee.equals(employeeRelationship.getSourceEmployee()))
            throw new AccessDeniedException(String.format(
                    "Employee[%d] is not the source employee of EmployeeRelationship[%d]",
                    employeeId,
                    employeeRelationshipId));

        // Security check
        this.securityService.canWriteEmployeeRelationship(user, employeeRelationship.getSourceEmployee(),
                employeeRelationship.getTargetEmployee());

        // End EmployeeRelationship
        // TODO Define what type of Exception should this throw?
        this.employeeRelationshipService.endEmployeeRelationship(employeeRelationship)
                .orElseThrow(() -> new EmployeeAppraisalMicroserviceException(String.format(
                        "EmployeeRelationship[%d] could not be terminated",
                        employeeRelationshipId)));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OperationResultDTO> employeesIdRelationshipsPost(
            @PathVariable("employeeId") Integer employeeId,
            @RequestBody EmployeeRelationshipDTO employeeRelationshipDTO) {

        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: POST /employees/{}/relationships", user.getEmail(), employeeId);
        logger.debug("POST body: {}", employeeRelationshipDTO);

        // Validate DTO
        this.validationService.validate(employeeRelationshipDTO);

        // Find Employee who will be the Source Employee of the EmployeeRelationship
        Employee sourceEmployee = this.employeeService.getById(employeeId);

        securityService.checkRelationshipCount(sourceEmployee);

        // Convert DTO to Entity
        EmployeeRelationship employeeRelationship = this.employeeRelationshipDTOConverter.convertBack(employeeRelationshipDTO);

        // Security check
        this.securityService.canWriteEmployeeRelationship(user, sourceEmployee, employeeRelationship.getTargetEmployee());

        // Create the new EmployeeRelationship
        EmployeeRelationship createdEmployeeRelationship = this.employeeRelationshipService.startEmployeeRelationship( // <-- This creates a new EmployeeRelationship and could be improved!
                sourceEmployee,
                employeeRelationship.getTargetEmployee(),
                employeeRelationship.getRelationshipType(),
                employeeRelationship.getComments())
                .orElseThrow(() -> new EmployeeAppraisalMicroserviceException(String.format(
                        "EmployeeRelationship between Employee[%d] -> Employee[%d] of type %s",
                        sourceEmployee.getId(),
                        employeeRelationship.getTargetEmployee().getId(),
                        employeeRelationship.getRelationshipType().getName())));

        // Create Result DTO
        OperationResultDTO response = new OperationResultDTO();
        response.setMessage(Constants.SUCCESS);
//        response.setData(this.employeeRelationshipDTOConverter.convert(createdEmployeeRelationship));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
    public ResponseEntity<List<RelationshipTypeDTO>> relationshipsGet() {
        logger.debug("GET endpoint: /relationships/");

        List<RelationshipTypeDTO> relationshipDTOList = new ArrayList<>();

        // We will omit other relationship types for now. Plus this might require a filter.
        relationshipTypeService.findRelationshipsByNames(RelationshipName.LEAD,
                RelationshipName.PEER,
                RelationshipName.OTHER)
                .forEach(r -> relationshipDTOList.add(relationshipTypeDTOConverter.convert(r)));

        return new ResponseEntity<>(relationshipDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RelationshipTypeDTO> relationshipsIdGet(@PathVariable Integer relationshipId) {
        logger.debug("GET endpoint: /relationships/{}", relationshipId);

        RelationshipTypeDTO relationship = relationshipTypeService.findById(relationshipId)
                .map(relationshipTypeDTOConverter::convert)
                .orElseThrow(() -> new NotFoundException(String.format("RelationshipType with id %d was not found",
                        relationshipId)));

        return new ResponseEntity<>(relationship, HttpStatus.OK);
    }
}
