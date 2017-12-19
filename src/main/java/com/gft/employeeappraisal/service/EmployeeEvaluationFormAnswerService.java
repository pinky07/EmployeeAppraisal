package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.EmployeeEvaluationFormAnswer;

import java.util.Optional;

public interface EmployeeEvaluationFormAnswerService {
	Optional<EmployeeEvaluationFormAnswer> findById(int id);
	Optional< EmployeeEvaluationFormAnswer> saveAndFlush( EmployeeEvaluationFormAnswer entity);
	EmployeeEvaluationFormAnswer getById(Integer id) throws NotFoundException;
}
