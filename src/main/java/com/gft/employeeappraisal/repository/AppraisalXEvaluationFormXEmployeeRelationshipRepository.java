package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.AppraisalXEvaluationFormXEmployeeRelationship;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Repository for the EvaluationFormXSectionXQuestion database table. See class hierarchy for methods already existing.
 *
 * @author Rubén Jiménez
 */
@Repository
@Transactional
public interface AppraisalXEvaluationFormXEmployeeRelationshipRepository extends JpaRepository<AppraisalXEvaluationFormXEmployeeRelationship, Integer> {

    @Query("SELECT appraisalXEvaluationFormXEmployeeRelationship " +
            "FROM AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship " +
            "JOIN FETCH appraisalXEvaluationFormXEmployeeRelationship.appraisalXEvaluationForm appraisalXEvaluationForm " +
            "JOIN FETCH appraisalXEvaluationFormXEmployeeRelationship.employeeRelationship employeeRelationship " +
            "WHERE appraisalXEvaluationForm.appraisal = :appraisal " +
            "AND employeeRelationship.sourceEmployee = :employee " +
            "AND employeeRelationship.relationship IN :relationships ")
    List<AppraisalXEvaluationFormXEmployeeRelationship> findByAppraisalAndSourceEmployee(
            @Param("appraisal") Appraisal appraisal,
            @Param("employee") Employee sourceEmployee,
            @Param("relationships") Set<Relationship> relationships);

    @Query("SELECT appraisalXEvaluationFormXEmployeeRelationship " +
            "FROM AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship " +
            "JOIN FETCH appraisalXEvaluationFormXEmployeeRelationship.appraisalXEvaluationForm appraisalXEvaluationForm " +
            "JOIN FETCH appraisalXEvaluationFormXEmployeeRelationship.employeeRelationship employeeRelationship " +
            "WHERE appraisalXEvaluationForm.appraisal = :appraisal " +
            "AND employeeRelationship.targetEmployee = :employee " +
            "AND employeeRelationship.relationship IN :relationships ")
    List<AppraisalXEvaluationFormXEmployeeRelationship> findByAppraisalAndTargetEmployee(
            @Param("appraisal") Appraisal appraisal,
            @Param("employee") Employee targetEmployee,
            @Param("relationships") Set<Relationship> relationships);

    @Query("SELECT appraisalXEvaluationFormXEmployeeRelationship " +
            "FROM AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship " +
            "JOIN FETCH appraisalXEvaluationFormXEmployeeRelationship.appraisalXEvaluationForm appraisalXEvaluationForm " +
            "JOIN FETCH appraisalXEvaluationFormXEmployeeRelationship.employeeRelationship employeeRelationship " +
            "WHERE appraisalXEvaluationForm.appraisal = :appraisal " +
            "AND employeeRelationship.sourceEmployee = :employee " +
            "AND employeeRelationship.targetEmployee = :employee " +
            "AND employeeRelationship.relationship IN :relationships ")
    List<AppraisalXEvaluationFormXEmployeeRelationship> findByAppraisalAndEmployeeAsSourceAndTarget(
            @Param("appraisal") Appraisal appraisal,
            @Param("employee") Employee employeeSourceAndTarget,
            @Param("relationships") Set<Relationship> relationships);
}
