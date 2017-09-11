package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.AppraisalXEvaluationFormTemplate;

/**
 * Interacts with tables and repositories related to the AppraisalXEvaluationFormTemplate
 *
 * @author Rubén Jiménez
 */
public interface AppraisalXEvaluationFormTemplateService {

    /**
     * Saves an AppraisalXEvaluationFormTemplate Entity
     *
     * @param appraisalXEvaluationFormTemplate Appraisal Entity to saveAndFlush
     */
    void saveAndFlush(AppraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate);
}
