package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EvaluationForm;

import java.util.stream.Stream;

public interface EvaluationFormXSectionXQuestionService {

    /**
     * Returns all Evaluation Forms of an Employee for a given Appraisal process.
     *
     * @param appraisal Appraisal process to which the Evaluation Forms belong to
     * @param employee  Employee that's being evaluated
     * @return
     */
    Stream<EvaluationForm> findByAppraisalAndEmployee(Appraisal appraisal, Employee employee);

}
