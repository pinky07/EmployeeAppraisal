package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.ScoreValue;

import java.util.Optional;
import java.util.stream.Stream;

public interface ScoreValueService
{
	Optional<ScoreValue> findById(Integer id);

	ScoreValue getById(Integer id) throws NotFoundException;

	Optional<ScoreValue> saveAndFlush(ScoreValue scoreValue);

   
}
