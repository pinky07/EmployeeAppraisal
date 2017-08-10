package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.EvaluationForm;
import com.gft.employeeappraisal.repository.EvaluationFormRepository;
import com.gft.employeeappraisal.service.EvaluationFormService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation of {@link EvaluationFormService}.
 *
 * @author Rubén Jiménez
 */
@Service
public class EvaluationFormServiceImpl implements EvaluationFormService {

    @Autowired
    private EvaluationFormRepository evaluationFormRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAndFlush(EvaluationForm evaluationForm) {
        this.evaluationFormRepository.saveAndFlush(evaluationForm);
    }
}
