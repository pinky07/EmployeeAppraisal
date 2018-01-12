package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for the EmployeeEvaluationForm database table. See class hierarchy for methods already existing.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@Repository
@Transactional
public interface EmployeeEvaluationFormRepository extends JpaRepository<EmployeeEvaluationForm, Integer> {

    List<EmployeeEvaluationForm> findByEmployee(Employee employee);

    List<EmployeeEvaluationForm> findByEmployeeAndFilledByEmployee(Employee employee, Employee filledByEmployee);

    EmployeeEvaluationForm findByEmployeeAndFilledByEmployeeAndAppraisal(Employee employee, Employee filledByEmployee,
            Appraisal appraisal);

    List<EmployeeEvaluationForm> findByEmployeeAndAppraisal(Employee employee, Appraisal appraisal);

    List<EmployeeEvaluationForm> findByFilledByEmployeeAndAppraisal(Employee filledByEmployee, Appraisal appraisal);

    List<EmployeeEvaluationForm> findByMentorAndAppraisal(Employee mentor, Appraisal appraisal);
}
