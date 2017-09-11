package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.AppraisalXEvaluationFormTemplate;
import com.gft.employeeappraisal.repository.AppraisalXEvaluationFormTemplateRepository;
import com.gft.employeeappraisal.service.AppraisalXEvaluationFormTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void saveAndFlush(AppraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate) {
        this.appraisalXEvaluationFormTemplateRepository.saveAndFlush(appraisalXEvaluationFormTemplate);
    }
}
