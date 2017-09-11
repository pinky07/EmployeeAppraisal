package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for the EvaluationFormXSectionXQuestion database table. See class hierarchy for methods already existing.
 *
 * @author Rubén Jiménez
 */
@Repository
@Transactional
public interface EvaluationFormXSectionXQuestionRepository extends JpaRepository<EvaluationFormTemplateXSectionXQuestion, Integer> {
}
