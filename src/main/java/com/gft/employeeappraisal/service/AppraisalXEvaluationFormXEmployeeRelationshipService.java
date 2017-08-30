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
     * Searches the AppraisalXEvaluationFormXEmployeeRelationship table for a series of values. Relationship
     * names provided are made given that the employee is of type Source (look into {@link EmployeeRelationship} for
     * details).
     * @param appraisal {@link Appraisal} First lookup filter.
     * @param employee {@link Employee} Source employee filter.
     * @param relationshipNames One or many relationship types to look up for the employee: SELF, MENTOR, PEER, etc.
     * @return A stream of {@link AppraisalXEvaluationFormXEmployeeRelationship} with h2 values found.
     */
    Stream<AppraisalXEvaluationFormXEmployeeRelationship> findByAppraisalAndEmployeeAndSourceRelationships(
            Appraisal appraisal,
            Employee employee,
            RelationshipName... relationshipNames
    );

    /**
     * TODO Add parameter documentation!
     *
     * Returns h2 AppraisalXEvaluationFormXEmployeeRelationship given an specific Appraisal and Employee being
     * evaluated
     */
    Stream<AppraisalXEvaluationFormXEmployeeRelationship> findByAppraisalAndEmployee(
            Appraisal appraisal, Employee employee);

    /**
     * Save an Entity instance of {@link AppraisalXEvaluationFormXEmployeeRelationship}
     *
     * @param appraisalXEvaluationFormXEmployeeRelationship Appraisal Entity to saveAndFlush
     */
    void saveAndFlush(AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship);
}
