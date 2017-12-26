package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.JobFamily;
import com.gft.employeeappraisal.model.ScoreType;
import com.gft.employeeappraisal.model.ScoreValue;
import com.gft.employeeappraisal.repository.ScoreTypeRepository;
import com.gft.employeeappraisal.repository.ScoreValueRepository;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import com.gft.employeeappraisal.service.ScoreTypeService;
import com.gft.employeeappraisal.service.ScoreValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ScoreTypeServiceImpl implements ScoreTypeService
{
private final  ScoreTypeRepository scoreTypeRepository;

@Autowired
public ScoreTypeServiceImpl(ScoreTypeRepository scoreTypeRepository){
	this.scoreTypeRepository=scoreTypeRepository;
}

	@Override public Optional<ScoreType> findById(Integer id)
	{
		return Optional.ofNullable(scoreTypeRepository.findOne(id));
	}

	@Override public ScoreType getById(Integer id) throws NotFoundException
	{
		return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
				"ScoreType [%d] couldn't be found",
				id)));
	}

	@Override public Optional<ScoreType> saveAndFlush(ScoreType scoreType)
	{
		return Optional.ofNullable(scoreTypeRepository.saveAndFlush(scoreType));
	}
}


