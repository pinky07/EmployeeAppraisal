package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EmployeeEvaluationFormAnswer;
import com.gft.employeeappraisal.model.JobFamily;
import com.gft.employeeappraisal.model.Question;

import java.util.Optional;
import java.util.stream.Stream;

public interface EmployeeEvaluationFormAnswerService
{
	Optional<EmployeeEvaluationFormAnswer> findById(int id);

	Optional< EmployeeEvaluationFormAnswer> saveAndFlush( EmployeeEvaluationFormAnswer entity);
	EmployeeEvaluationFormAnswer getById(Integer id) throws NotFoundException;
	Stream<EmployeeEvaluationFormAnswer> findByEmployeeEvaluationFormId(EmployeeEvaluationForm evaluationForm);

}
