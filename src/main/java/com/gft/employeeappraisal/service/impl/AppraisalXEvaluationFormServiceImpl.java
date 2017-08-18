package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.AppraisalXEvaluationForm;
import com.gft.employeeappraisal.repository.AppraisalXEvaluationFormRepository;
import com.gft.employeeappraisal.service.AppraisalXEvaluationFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation of {@link AppraisalXEvaluationFormService}.
 *
 * @author Rubén Jiménez
 */
@Service
public class AppraisalXEvaluationFormServiceImpl implements AppraisalXEvaluationFormService {

    @Autowired
    private AppraisalXEvaluationFormRepository appraisalXEvaluationFormRepository;

    @Override
    public void saveAndFlush(AppraisalXEvaluationForm appraisalXEvaluationForm) {
        this.appraisalXEvaluationFormRepository.saveAndFlush(appraisalXEvaluationForm);
    }
}
