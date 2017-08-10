package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.service.AppraisalService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.api.AppraisalApi;
import com.gft.swagger.employees.model.AppraisalDTO;
import com.gft.swagger.employees.model.EvaluationFormDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

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
    private EmployeeService employeeService;

    @Autowired
    private AppraisalService appraisalService;

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
    public ResponseEntity<AppraisalDTO> meAppraisalsAppraisalIdGet(Integer appraisalId) {
        return null;
    }

    @Override
    public ResponseEntity<List<AppraisalDTO>> meAppraisalsGet() {
        return null;
    }


    /**
     * Returns a list of EvaluationForm for an specific appraisal of the authenticated user.
     * GET /me/appraisals/:id/forms
     *
     * @param appraisalId Appraisal to which the returned list of EvaluationForm belongs to
     * @return List of desired EvaluationForm
     */
    @Override
    public ResponseEntity<List<EvaluationFormDTO>> meAppraisalsAppraisalIdFormsGet(Integer appraisalId) {

        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: GET /me/appraisals/{}/forms", user.getEmail(), appraisalId);



        return null;
    }

    /**
     * Returns a list of EvaluationForm for an specific appraisal of the given user.
     * GET /employees/:id/appraisals/:id/forms
     *
     * @param employeeId Employee whose EvaluationForm are desired
     * @param appraisalId Appraisal to which the returned list of EvaluationForm belongs to
     * @return List of desired EvaluationForm
     */
    @Override
    public ResponseEntity<List<EvaluationFormDTO>> employeesEmployeeIdAppraisalsAppraisalIdFormsGet(Integer employeeId,
                                                                                                    Integer appraisalId) {

        // Get logged in user
        Employee user = this.employeeService.getLoggedInUser();
        logger.debug("{} called endpoint: GET /employees/{}/appraisals/{}/forms", user.getEmail(), employeeId, appraisalId);


        return null;
    }




}
