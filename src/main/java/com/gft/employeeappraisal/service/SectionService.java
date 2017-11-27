package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.ScoreValue;
import com.gft.employeeappraisal.model.Section;

import java.util.Optional;

public interface SectionService
{


    Optional<Section> findById(int Section);

    Optional<Section> saveAndFlush(Section entity);
}
