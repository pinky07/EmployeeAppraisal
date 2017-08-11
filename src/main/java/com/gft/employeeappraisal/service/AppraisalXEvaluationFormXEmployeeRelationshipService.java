package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * Interface for methods interacting with the AppraisalXEvaluationFormXEmployeeRelationship repository.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
public interface AppraisalXEvaluationFormXEmployeeRelationshipService {

    /**
     * TODO Document this!
     *
     * @param employeeRelationships
     * @return
     */
    Stream<AppraisalXEvaluationFormXEmployeeRelationship> findByEmployeeRelationships(
            List<EmployeeRelationship> employeeRelationships);

    /**
     * TODO Document this!
     *
     * @param employeeRelationships
     * @param evaluationStatus
     * @return
     */
    Stream<AppraisalXEvaluationFormXEmployeeRelationship> findByEmployeeRelationshipsAndEvaluationStatus(
            List<EmployeeRelationship> employeeRelationships,
            EvaluationStatus evaluationStatus);


    /**
     * Returns all AppraisalXEvaluationFormXEmployeeRelationship given an specific Appraisal and Employee being
     * evaluated
     */
    Stream<AppraisalXEvaluationFormXEmployeeRelationship> findByAppraisalAndEmployee(
            Appraisal appraisal, Employee employee);

    /**
     * Save an Entity instance of {@link AppraisalXEvaluationFormXEmployeeRelationship}
     *
     * @param appraisalXEvaluationFormXEmployeeRelationship Appraisal Entity to saveAndFlush
     * @return True if the saveAndFlush is successful
     */
    void saveAndFlush(AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship);
}
