package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EvaluationStatus;

import java.util.stream.Stream;

/**
 * Interface for methods interacting with the EmployeeEvaluationForm repository.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
public interface EmployeeEvaluationFormService {

    /**
     * Searches the EmployeeEvaluationForm table for a series of values.
     * @param appraisal {@link Appraisal} First lookup filter.
     * @param employee {@link Employee} Source employee filter.
     * @return A stream of {@link EmployeeEvaluationForm} with all values found.
     */
    Stream<EmployeeEvaluationForm> findByAppraisalAndEmployee(
            Appraisal appraisal,
            Employee employee
    );

    /**
     * Searches the EmployeeEvaluationForm table for a series of values. Only looks up for self-filled EvaluationForms.
     * @param employee {@link Employee} Source and target employee filter.
     * @return A stream of {@link EmployeeEvaluationForm} with all values found.
     */
    Stream<EmployeeEvaluationForm> findByEmployeeAndFilledByEmployeeId(
            Employee employee,
            EvaluationStatus... evaluationStatus
    );

    /**
     * Save an Entity instance of {@link EmployeeEvaluationForm}
     *
     * @param employeeEvaluationForm Appraisal Entity to saveAndFlush
     */
    void saveAndFlush(EmployeeEvaluationForm employeeEvaluationForm);
}
