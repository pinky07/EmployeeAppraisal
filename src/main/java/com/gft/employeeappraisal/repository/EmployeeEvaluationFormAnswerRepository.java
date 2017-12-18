package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EmployeeEvaluationFormAnswer;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EmployeeEvaluationFormAnswerRepository extends JpaRepository<EmployeeEvaluationFormAnswer, Integer> {
	@Query("SELECT  elfa.*  " +
			 "from  EmployeeEvaluationFormAnswer elfa ,EmployeeEvaluationForm ef "+
			"WHERE elfa.employeeevaluationformid = ef.id "+
			"AND ef.id = :evaluationFormId "
			)

	List<EmployeeEvaluationFormAnswer> findByEmployeeEvaluationFormId(@Param ("employeeEvaluationForm") EmployeeEvaluationForm evaluationFormId);
}
