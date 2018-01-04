package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Question;
import com.gft.employeeappraisal.model.ScoreValue;
import com.gft.employeeappraisal.repository.QuestionRepository;
import com.gft.employeeappraisal.repository.ScoreValueRepository;
import com.gft.employeeappraisal.service.ScoreValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScoreValueServiceImpl implements ScoreValueService
{
	private static ScoreValueRepository scoreValueRepository;
	@Autowired
	public ScoreValueServiceImpl(ScoreValueRepository scoreValueRepository){
		this.scoreValueRepository=scoreValueRepository;
	}
	@Override public Optional<ScoreValue> findById(Integer id)
	{
		return Optional.ofNullable(scoreValueRepository.findOne(id));
	}

	@Override
	public ScoreValue getById(Integer id) throws NotFoundException
	{
		return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
				"Question [%d] couldn't be found",
				id)));
	}

	@Override public Optional<ScoreValue> saveAndFlush(ScoreValue employee)
	{
		return Optional.ofNullable(scoreValueRepository.saveAndFlush(employee));
	}
}
