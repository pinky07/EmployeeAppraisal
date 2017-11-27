package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.ScoreType;
import com.gft.employeeappraisal.model.ScoreValue;

import java.util.Optional;

public interface ScoreTypeService
{


    Optional<ScoreType> findById(int ScoreValue);

    Optional<ScoreType> saveAndFlush(ScoreValue entity);
}
