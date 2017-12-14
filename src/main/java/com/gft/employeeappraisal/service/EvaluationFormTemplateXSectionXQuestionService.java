package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import com.gft.employeeappraisal.model.ScoreType;

import java.util.Optional;

public interface EvaluationFormTemplateXSectionXQuestionService
{


	Optional<EvaluationFormTemplateXSectionXQuestion> findById(Integer id);

	EvaluationFormTemplateXSectionXQuestion getById(Integer id) throws NotFoundException;

	Optional<EvaluationFormTemplateXSectionXQuestion> saveAndFlush(EvaluationFormTemplateXSectionXQuestion employee);
}
