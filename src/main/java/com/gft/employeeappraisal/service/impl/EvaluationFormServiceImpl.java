package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.EvaluationForm;
import com.gft.employeeappraisal.repository.EvaluationFormRepository;
import com.gft.employeeappraisal.service.EvaluationFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link EvaluationFormService}.
 *
 * @author Rubén Jiménez
 */
@Service
public class EvaluationFormServiceImpl implements EvaluationFormService {

    private final EvaluationFormRepository evaluationFormRepository;

    @Autowired
    public EvaluationFormServiceImpl(
            EvaluationFormRepository evaluationFormRepository) {
        this.evaluationFormRepository = evaluationFormRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<EvaluationForm> saveAndFlush(EvaluationForm evaluationForm) {
        return Optional.ofNullable(this.evaluationFormRepository.saveAndFlush(evaluationForm));
    }
}
