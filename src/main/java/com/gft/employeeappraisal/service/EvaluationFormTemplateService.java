package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;

import java.util.Optional;

/**
 * Interacts with tables and repositories related to the EvaluationFormTemplate
 *
 * @author Rubén Jiménez
 */
public interface EvaluationFormTemplateService {

    /**
     * Given an employee Id, looks up an employee and returns it. If it doesn't exist, it throws an exception.
     *
     * @param id Internal lookup Id for the the evaluation form template.
     * @return EvaluationFormTemplate entity.
     *
     * @throws NotFoundException if the provided ID does not match to an existing record.
     */
    EvaluationFormTemplate getById(Integer id) throws NotFoundException;

    /**
     * Given an evaluationFormTemplate Id, looks up an EvaluationFormTemplate and returns it.
     *
     * @param id Internal lookup Id for the evaluation form template.
     * @return An Optional object that may or may not contain the EvaluationFormTemplate entity.
     */
    Optional<EvaluationFormTemplate> findById(Integer id);

    /**
     * Returns a matching Evaluation Form Template which can be filled by an specific employee on a
     * specific appraisal.
     * @param id Internal lookup ID for the evaluation form template
     * @param filledByEmployee {@link Employee} who can fill the evaluation form
     * @param appraisal {@link Appraisal} Specific appraisal process
     * @return An Optional object that may or may not contain the EvaluationFormTemplate entity.
     */
    Optional<EvaluationFormTemplate> findByIdAndFilledByEmployeeAndAppraisal(Integer id,
                                                                             Employee filledByEmployee,
                                                                             Appraisal appraisal);

    /**
     * Save an Entity instance of {@link EvaluationFormTemplate}
     *
     * @param evaluationFormTemplate Entity to saveAndFlush
     */
    Optional<EvaluationFormTemplate> saveAndFlush(EvaluationFormTemplate evaluationFormTemplate);
}
