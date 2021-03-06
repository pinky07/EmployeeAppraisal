package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.converter.employee.EmployeeDTOConverter;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.api.MeApi;
import com.gft.swagger.employees.model.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class that implements the /me API endpoints generated by Swagger via its interface.
 *
 * @author Ruben Jimenez
 * @author Manuel Yepez
 */
@Controller
public class MeController implements MeApi {

    private final Logger logger = LoggerFactory.getLogger(MeController.class);

    private final EmployeeService employeeService;
    private final EmployeeDTOConverter employeeDTOConverter;

    @Autowired
    public MeController(EmployeeService employeeService, EmployeeDTOConverter employeeDTOConverter) {
        this.employeeService = employeeService;
        this.employeeDTOConverter = employeeDTOConverter;
    }

    /**
     * Obtains all the employee info (delimited on the EmployeeAppraisal microservice) for the authenticated user.
     *
     * @return {@link EmployeeDTO} Entity with all the information pertinent to the authenticated user.
     */
    @Override
    public ResponseEntity<EmployeeDTO> meGet() {

        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: GET /me", user.getEmail());

        // Set Result DTO
        EmployeeDTO result;
        result = employeeDTOConverter.convert(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EmployeeDTO>> meMenteesGet() {

        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: GET /me/mentees ", user.getEmail());

        // Find Mentees and set result
        List<EmployeeDTO> result = employeeService
                .findCurrentMenteesById(user.getId())
                .map(employeeDTOConverter::convert)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Obtains all the mentor info associated to the authenticated user.
     *
     * @return {@link EmployeeDTO} Entity with all the information pertinent to the mentor of the authenticated user.
     */
    @Override
    public ResponseEntity<EmployeeDTO> meMentorGet() {

        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: GET /me/mentor", user.getEmail());

        // Find Mentor
        Employee mentor = employeeService.findCurrentMentorById(user.getId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Mentor for Employee with Id %s was not found",
                        user.getId())));

        // Set Result DTO
        EmployeeDTO result = employeeDTOConverter.convert(mentor);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Obtains all the peers associated to the authenticated user.
     *
     * @return {@link List<EmployeeDTO>} List of peers associated to the authenticated user.
     */
    @Override
    public ResponseEntity<List<EmployeeDTO>> mePeersGet() {
        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint called: GET /me/peers", user.getEmail());
        List<EmployeeDTO> result = new ArrayList<>();

        HttpStatus status;

        employeeService.findCurrentPeersById(user.getId())
                .forEach(peer -> result.add(employeeDTOConverter.convert(peer)));
        status = result.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;

        return new ResponseEntity<>(result, status);
    }
}
