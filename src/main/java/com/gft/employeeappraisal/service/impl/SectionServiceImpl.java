package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.ScoreValue;
import com.gft.employeeappraisal.model.Section;
import com.gft.employeeappraisal.repository.ScoreValueRepository;
import com.gft.employeeappraisal.repository.SectionRepository;
import com.gft.employeeappraisal.service.ScoreValueService;
import com.gft.employeeappraisal.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService
{
	private static SectionRepository sectionRepository;
	@Autowired
	public SectionServiceImpl(SectionRepository sectionRepository){
		this.sectionRepository=sectionRepository;
	}
	@Override public Optional<Section> findById(Integer id)
	{
		return Optional.ofNullable(sectionRepository.findOne(id));
	}

	@Override
	public Section getById(Integer id) throws NotFoundException
	{
		return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
				"Section [%d] couldn't be found",
				id)));
	}

	@Override public Optional<Section> saveAndFlush(Section employee)
	{
		return Optional.ofNullable(sectionRepository.saveAndFlush(employee));
	}
}
