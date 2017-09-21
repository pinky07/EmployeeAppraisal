package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for the EvaluationFormTemplate database table. See class hierarchy for methods already existing.
 *
 * @author Rubén Jiménez
 */
@Repository
@Transactional
public interface EvaluationFormTemplateRepository extends JpaRepository<EvaluationFormTemplate, Integer> {

    @Query("SELECT evaluationFormTemplate " +
            "FROM EvaluationFormTemplate evaluationFormTemplate " +
            "JOIN FETCH evaluationFormTemplate.appraisalXEvaluationFormTemplateSet appraisalXEvaluationForms " +
            "JOIN FETCH appraisalXEvaluationForms.employeeEvaluationFormSet employeeEvaluationForms " +
            "WHERE evaluationFormTemplate.id = :id " +
            "AND appraisalXEvaluationForms.appraisal = :appraisal "+
            "AND employeeEvaluationForms.filledByEmployee = :employee")
    EvaluationFormTemplate findByIdAndFilledByEmployeeAndAppraisal(
            @Param("id") Integer id,
            @Param("employee") Employee employee,
            @Param("appraisal")Appraisal appraisal);
}
