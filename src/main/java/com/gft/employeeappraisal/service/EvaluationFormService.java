package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.EvaluationForm;

import java.util.Optional;

/**
 * Interacts with tables and repositories related to the EvaluationForm
 *
 * @author Rubén Jiménez
 */
public interface EvaluationFormService {

    /**
     * Save an Entity instance of {@link EvaluationForm}
     *
     * @param evaluationForm Entity to saveAndFlush
     */
    Optional<EvaluationForm> saveAndFlush(EvaluationForm evaluationForm);
}
