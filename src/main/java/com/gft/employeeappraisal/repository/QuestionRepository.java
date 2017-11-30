package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.Question;
import com.gft.employeeappraisal.model.ScoreValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
