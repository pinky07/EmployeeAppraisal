package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import com.gft.employeeappraisal.repository.EvaluationFormTemplateRepository;
import com.gft.employeeappraisal.service.EvaluationFormTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link EvaluationFormTemplateService}.
 *
 * @author Rubén Jiménez
 */
@Service
public class EvaluationFormTemplateServiceImpl implements EvaluationFormTemplateService {

    private final EvaluationFormTemplateRepository evaluationFormTemplateRepository;

    @Autowired
    public EvaluationFormTemplateServiceImpl(
            EvaluationFormTemplateRepository evaluationFormTemplateRepository) {
        this.evaluationFormTemplateRepository = evaluationFormTemplateRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<EvaluationFormTemplate> saveAndFlush(EvaluationFormTemplate evaluationFormTemplate) {
        return Optional.ofNullable(this.evaluationFormTemplateRepository.saveAndFlush(evaluationFormTemplate));
    }
}
