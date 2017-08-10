package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.EvaluationForm;

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
    void saveAndFlush(EvaluationForm evaluationForm);
}
