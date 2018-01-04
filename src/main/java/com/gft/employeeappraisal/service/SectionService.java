package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.ScoreValue;
import com.gft.employeeappraisal.model.Section;

import java.util.Optional;

public interface SectionService
{
	Optional<Section> findById(Integer id);

	Section getById(Integer id) throws NotFoundException;

	Optional<Section> saveAndFlush(Section section);

   
}
