package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.ScoreType;

import java.util.Optional;


public interface ScoreTypeService
{


	Optional<ScoreType> findById(Integer id);

	ScoreType getById(Integer id) throws NotFoundException;

	Optional<ScoreType> saveAndFlush(ScoreType employee);
}
