package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.AppraisalXEvaluationForm;
import com.gft.employeeappraisal.model.AppraisalXEvaluationFormXEmployeeRelationship;
import com.gft.employeeappraisal.model.EvaluationForm;

/**
 * Interacts with tables and repositories related to the AppraisalXEvaluationForm
 *
 * @author Rubén Jiménez
 */
public interface AppraisalXEvaluationFormXEmployeeRelationshipService {

    /**
     * Save an Entity instance of {@link AppraisalXEvaluationFormXEmployeeRelationship}
     *
     * @param appraisalXEvaluationFormXEmployeeRelationship Appraisal Entity to saveAndFlush
     * @return True if the saveAndFlush is successful
     */
    void saveAndFlush(AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship);
}
