package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.Question;
import com.gft.employeeappraisal.model.ScoreValue;

import java.util.Optional;

public interface QuestionService
{


    Optional<Question> findById(int ScoreValue);

    Optional<Question> saveAndFlush(ScoreValue entity);
}
