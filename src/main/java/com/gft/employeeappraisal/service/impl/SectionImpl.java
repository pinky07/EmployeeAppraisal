package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.JobLevel;
import com.gft.employeeappraisal.model.Section;
import com.gft.employeeappraisal.repository.JobLevelRepository;
import com.gft.employeeappraisal.repository.SectionRepository;
import com.gft.employeeappraisal.service.JobLevelService;
import com.gft.employeeappraisal.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SectionImpl implements SectionService
{

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }


    @Override
    public Optional<Section> findById(int id) {
        return Optional.ofNullable(sectionRepository.findOne(id));
    }

    @Override
    public Optional<Section> saveAndFlush(Section entity) {
        return Optional.ofNullable(sectionRepository.saveAndFlush(entity));
    }
}
