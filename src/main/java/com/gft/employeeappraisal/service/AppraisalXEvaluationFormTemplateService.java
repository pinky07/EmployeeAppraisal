package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.AppraisalXEvaluationFormTemplate;

import java.util.Optional;

/**
 * Interacts with tables and repositories related to the AppraisalXEvaluationFormTemplate
 *
 * @author Rubén Jiménez
 */
public interface AppraisalXEvaluationFormTemplateService {

    /**
     * Saves an AppraisalXEvaluationFormTemplate Entity
     * @return Optional with The saved entity.
     * @param appraisalXEvaluationFormTemplate Appraisal Entity to saveAndFlush
     */
    Optional<AppraisalXEvaluationFormTemplate> saveAndFlush(AppraisalXEvaluationFormTemplate
                                                                    appraisalXEvaluationFormTemplate);
}
