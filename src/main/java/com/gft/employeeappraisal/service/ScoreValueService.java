package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.JobFamily;
import com.gft.employeeappraisal.model.ScoreValue;

import java.util.Optional;


public interface ScoreValueService
{


    Optional<ScoreValue> findById(int ScoreValue);

    Optional<ScoreValue> saveAndFlush(ScoreValue entity);
}
