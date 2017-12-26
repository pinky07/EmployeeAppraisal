package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Question;
import com.gft.employeeappraisal.model.ScoreValue;
import com.gft.employeeappraisal.repository.QuestionRepository;
import com.gft.employeeappraisal.repository.ScoreValueRepository;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import com.gft.employeeappraisal.service.QuestionService;
import com.gft.employeeappraisal.service.ScoreValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class QuestionServiceImpl implements QuestionService
{
private static QuestionRepository questionRepository;
@Autowired
public QuestionServiceImpl(QuestionRepository questionRepository){
	this.questionRepository=questionRepository;
}
	@Override public Optional<Question> findById(Integer id)
	{
		return Optional.ofNullable(questionRepository.findOne(id));
	}

	@Override public Question getById(Integer id) throws NotFoundException
	{
		return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
				"Question [%d] couldn't be found",
				id)));
	}

	@Override public Optional<Question> saveAndFlush(Question employee)
	{
		return Optional.ofNullable(questionRepository.saveAndFlush(employee));
	}
}
