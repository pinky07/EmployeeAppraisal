package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Question;
import com.gft.employeeappraisal.model.ScoreType;
import com.gft.employeeappraisal.model.ScoreValue;

import java.util.Optional;
import java.util.stream.Stream;

public interface QuestionService
{



	Optional<Question> findById(Integer id);

	Question getById(Integer id) throws NotFoundException;

	Optional<Question> saveAndFlush(Question employee);
}
