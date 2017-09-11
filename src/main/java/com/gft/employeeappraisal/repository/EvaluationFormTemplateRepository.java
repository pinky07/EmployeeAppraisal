package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for the EvaluationFormTemplate database table. See class hierarchy for methods already existing.
 *
 * @author Rubén Jiménez
 */
@Repository
@Transactional
public interface EvaluationFormTemplateRepository extends JpaRepository<EvaluationFormTemplate, Integer> {
}
