package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.converter.appraisal.AppraisalDTOConverter;
import com.gft.employeeappraisal.converter.evaluationform.EvaluationFormDTOConverter;
import com.gft.employeeappraisal.exception.AppraisalNotFoundException;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.AppraisalXEvaluationForm;
import com.gft.employeeappraisal.model.AppraisalXEvaluationFormXEmployeeRelationship;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.service.AppraisalService;
import com.gft.employeeappraisal.service.AppraisalXEvaluationFormXEmployeeRelationshipService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.api.AppraisalApi;
import com.gft.swagger.employees.model.AppraisalDTO;
import com.gft.swagger.employees.model.EvaluationFormDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class that implements the /appraisals API endpoints generated by Swagger via its interface.
 *
 * @author Ruben Jimenez
 * @author Manuel Yepez
 */
@Controller
public class AppraisalsController implements AppraisalApi {

    private Logger logger = LoggerFactory.getLogger(AppraisalsController.class);

    @Autowired
    private AppraisalService appraisalService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AppraisalXEvaluationFormXEmployeeRelationshipService appraisalXEvaluationFormXEmployeeRelationshipService;

    @Autowired
    private AppraisalDTOConverter appraisalDTOConverter;

    @Autowired
    private EvaluationFormDTOConverter evaluationFormDTOConverter;

    @Override
    public ResponseEntity<EvaluationFormDTO> employeesEmployeeIdAppraisalsAppraisalIdFormsFormIdGet(Integer employeeId,
                                                                                                    Integer appraisalId,
                                                                                                    Integer formId) {
        return null;
    }

    @Override
    public ResponseEntity<AppraisalDTO> employeesEmployeeIdAppraisalsAppraisalIdGet(Integer employeeId,
                                                                                    Integer appraisalId) {
        return null;
    }

    @Override
    public ResponseEntity<List<AppraisalDTO>> employeesEmployeeIdAppraisalsGet(Integer employeeId) {
        return null;
    }

    @Override
    public ResponseEntity<EvaluationFormDTO> meAppraisalsAppraisalIdFormsFormIdGet(Integer appraisalId,
                                                                                   Integer formId) {
        return null;
    }

    @Override
    public ResponseEntity<AppraisalDTO> meAppraisalsAppraisalIdGet(@PathVariable Integer appraisalId) {

        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: GET /me/appraisals/:id", user.getEmail());

        return null;
    }

    @Override
    public ResponseEntity<List<AppraisalDTO>> meAppraisalsGet(@RequestParam(value = "status", required = false)
                                                                      List<String> statusList) {
        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: GET /me/appraisals", user.getEmail());

        List<AppraisalDTO> result = new ArrayList<>();
        appraisalService
                .findEmployeeAppraisals(user, null)
                .forEach(ea -> result.add(appraisalDTOConverter.convert(ea)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Returns a list of EvaluationForm for an specific appraisal of the authenticated user.
     * GET /me/appraisals/:id/forms
     *
     * @param appraisalId Appraisal to which the returned list of EvaluationForm belongs to
     * @return List of desired EvaluationForm
     */
    @Override
    public ResponseEntity<List<EvaluationFormDTO>> meAppraisalsAppraisalIdFormsGet(@PathVariable("appraisalId") Integer appraisalId) {

        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: GET /me/appraisals/{}/forms", user.getEmail(), appraisalId);

        // Get Appraisal
        Appraisal appraisal = appraisalService.findById(appraisalId)
                .orElseThrow(() -> new AppraisalNotFoundException(String.format(
                        "Appraisal with id %d was not found", appraisalId)));

        // Get Evaluation Forms
        List<EvaluationFormDTO> result = this.appraisalXEvaluationFormXEmployeeRelationshipService.findByAppraisalAndEmployee(
                appraisal,
                user)
                .map(AppraisalXEvaluationFormXEmployeeRelationship::getAppraisalXEvaluationForm)
                .map(AppraisalXEvaluationForm::getEvaluationForm)
                .map(evaluationForm -> evaluationFormDTOConverter.convert(evaluationForm))
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Returns a list of EvaluationForm for an specific appraisal of the given user.
     * GET /employees/:id/appraisals/:id/forms
     *
     * @param employeeId  Employee whose EvaluationForm are desired
     * @param appraisalId Appraisal to which the returned list of EvaluationForm belongs to
     * @return List of desired EvaluationForm
     */
    @Override
    public ResponseEntity<List<EvaluationFormDTO>> employeesEmployeeIdAppraisalsAppraisalIdFormsGet(
            @PathVariable("employeeId") Integer employeeId,
            @PathVariable("appraisalId") Integer appraisalId) {

        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: GET /employees/{}/appraisals/{}/forms", user.getEmail(), employeeId, appraisalId);


        return null;
    }
}
