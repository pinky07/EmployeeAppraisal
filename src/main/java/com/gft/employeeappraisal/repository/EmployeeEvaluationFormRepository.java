package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EvaluationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Repository for the EmployeeEvaluationform database table. See class hierarchy for methods already existing.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@Repository
@Transactional
public interface EmployeeEvaluationFormRepository extends JpaRepository<EmployeeEvaluationForm, Integer> {

    @Query("SELECT employeeEvaluationForm " +
            "FROM EmployeeEvaluationForm employeeEvaluationForm " +
            "JOIN FETCH employeeEvaluationForm.appraisalXEvaluationForm appraisalXEvaluationForm " +
            "WHERE appraisalXEvaluationForm.appraisal = :appraisal " +
            "AND employeeEvaluationForm.employeeId = :employeeId ")
    List<EmployeeEvaluationForm> findByAppraisalAndSourceEmployee(
            @Param("appraisal") Appraisal appraisal,
            @Param("employeeId") int sourceEmployeeId);

    @Query("SELECT employeeEvaluationForm " +
            "FROM EmployeeEvaluationForm employeeEvaluationForm " +
            "JOIN FETCH employeeEvaluationForm.appraisalXEvaluationForm appraisalXEvaluationForm " +
            "WHERE appraisalXEvaluationForm.appraisal = :appraisal " +
            "AND employeeEvaluationForm.filledByEmployeeId = :employeeId ")
    List<EmployeeEvaluationForm> findByAppraisalAndTargetEmployee(
            @Param("appraisal") Appraisal appraisal,
            @Param("employeeId") int targetEmployeeId);

    @Query("SELECT employeeEvaluationForm " +
            "FROM EmployeeEvaluationForm employeeEvaluationForm " +
            "JOIN FETCH employeeEvaluationForm.appraisalXEvaluationForm appraisalXEvaluationForm " +
            "WHERE employeeEvaluationForm.employeeId = :employeeId " +
            "AND employeeEvaluationForm.filledByEmployeeId = :employeeId " +
            "AND employeeEvaluationForm.evaluationStatus = :evaluationStatus")
    List<EmployeeEvaluationForm> findByAppraisalAndEmployeeAsSourceAndTarget(
            @Param("employeeId") int employeeSourceAndTarget,
            @Param("evaluationStatus") Set<EvaluationStatus> evaluationStatus);
}
