package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.AppraisalXEvaluationForm;

/**
 * Interacts with tables and repositories related to the AppraisalXEvaluationForm
 *
 * @author Rubén Jiménez
 */
public interface AppraisalXEvaluationFormService {

    /**
     * Saves an AppraisalXEvaluationForm Entity
     *
     * @param appraisalXEvaluationForm Appraisal Entity to saveAndFlush
     */
    void saveAndFlush(AppraisalXEvaluationForm appraisalXEvaluationForm);
}