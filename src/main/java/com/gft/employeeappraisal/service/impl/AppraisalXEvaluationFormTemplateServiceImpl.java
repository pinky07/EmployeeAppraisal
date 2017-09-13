package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.AppraisalXEvaluationFormTemplate;
import com.gft.employeeappraisal.repository.AppraisalXEvaluationFormTemplateRepository;
import com.gft.employeeappraisal.service.AppraisalXEvaluationFormTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link AppraisalXEvaluationFormTemplateService}.
 *
 * @author Rubén Jiménez
 */
@Service
public class AppraisalXEvaluationFormTemplateServiceImpl implements AppraisalXEvaluationFormTemplateService {

    private final AppraisalXEvaluationFormTemplateRepository appraisalXEvaluationFormTemplateRepository;

    @Autowired
    public AppraisalXEvaluationFormTemplateServiceImpl(
            AppraisalXEvaluationFormTemplateRepository appraisalXEvaluationFormTemplateRepository) {
        this.appraisalXEvaluationFormTemplateRepository = appraisalXEvaluationFormTemplateRepository;
    }

    @Override
    public Optional<AppraisalXEvaluationFormTemplate> saveAndFlush(AppraisalXEvaluationFormTemplate
                                                                               appraisalXEvaluationFormTemplate) {
        return Optional.ofNullable(this.appraisalXEvaluationFormTemplateRepository
                .saveAndFlush(appraisalXEvaluationFormTemplate));
    }
}
