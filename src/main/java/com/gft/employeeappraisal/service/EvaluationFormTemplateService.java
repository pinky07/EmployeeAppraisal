package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.EvaluationFormTemplate;

import java.util.Optional;

/**
 * Interacts with tables and repositories related to the EvaluationFormTemplate
 *
 * @author Rubén Jiménez
 */
public interface EvaluationFormTemplateService {

    /**
     * Save an Entity instance of {@link EvaluationFormTemplate}
     *
     * @param evaluationFormTemplate Entity to saveAndFlush
     */
    Optional<EvaluationFormTemplate> saveAndFlush(EvaluationFormTemplate evaluationFormTemplate);
}
