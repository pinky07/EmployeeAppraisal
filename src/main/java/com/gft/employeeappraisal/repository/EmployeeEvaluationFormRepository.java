package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for the EmployeeEvaluationform database table. See class hierarchy for methods already existing.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@Repository
@Transactional
public interface EmployeeEvaluationFormRepository extends JpaRepository<EmployeeEvaluationForm, Integer> {

    List<EmployeeEvaluationForm> findByEmployee(Employee employee);

    List<EmployeeEvaluationForm> findByEmployeeAndFilledByEmployee(Employee employee, Employee filledByEmployee);

    @Query("SELECT employeeEvaluationForm " +
            "FROM EmployeeEvaluationForm AS employeeEvaluationForm " +
            "JOIN FETCH employeeEvaluationForm.appraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate " +
            "WHERE employeeEvaluationForm.employee = :employee " +
            "AND employeeEvaluationForm.filledByEmployee = :employee " +
            "AND appraisalXEvaluationFormTemplate.appraisal = :appraisal")
    EmployeeEvaluationForm findSelfByEmployeeAndAppraisal(
            @Param("employee") Employee employee,
            @Param("appraisal") Appraisal appraisal);

    @Query("SELECT employeeEvaluationForm " +
            "FROM EmployeeEvaluationForm employeeEvaluationForm " +
            "JOIN FETCH employeeEvaluationForm.appraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate " +
            "WHERE employeeEvaluationForm.employee = :employee " +
            "AND appraisalXEvaluationFormTemplate.appraisal = :appraisal ")
    List<EmployeeEvaluationForm> findByEmployeeAndAppraisal(
            @Param("employee") Employee employee,
            @Param("appraisal") Appraisal appraisal);

    @Query("SELECT employeeEvaluationForm " +
            "FROM EmployeeEvaluationForm employeeEvaluationForm " +
            "JOIN FETCH employeeEvaluationForm.appraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate " +
            "WHERE employeeEvaluationForm.filledByEmployee = :filledByEmployee " +
            "AND appraisalXEvaluationFormTemplate.appraisal = :appraisal ")
    List<EmployeeEvaluationForm> findByFilledByEmployeeAndAppraisal(
            @Param("filledByEmployee") Employee filledByEmployee,
            @Param("appraisal") Appraisal appraisal);

    @Query("SELECT employeeEvaluationForm " +
            "FROM EmployeeEvaluationForm employeeEvaluationForm " +
            "JOIN FETCH employeeEvaluationForm.appraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate " +
            "WHERE employeeEvaluationForm.mentor = :mentor " +
            "AND appraisalXEvaluationFormTemplate.appraisal = :appraisal ")
    List<EmployeeEvaluationForm> findByMentorAndAppraisal(
            @Param("mentor") Employee mentor,
            @Param("appraisal") Appraisal appraisal);
}
