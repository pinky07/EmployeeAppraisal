package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.AppraisalXEvaluationFormXEmployeeRelationship;
import com.gft.employeeappraisal.repository.AppraisalXEvaluationFormXEmployeeRelationshipRepository;
import com.gft.employeeappraisal.service.AppraisalXEvaluationFormService;
import com.gft.employeeappraisal.service.AppraisalXEvaluationFormXEmployeeRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation of {@link AppraisalXEvaluationFormXEmployeeRelationshipService}.
 *
 * @author Rubén Jiménez
 */
@Service
public class AppraisalXEvaluationFormXEmployeeRelationshipServiceImpl implements AppraisalXEvaluationFormXEmployeeRelationshipService {

    @Autowired
    private AppraisalXEvaluationFormXEmployeeRelationshipRepository appraisalXEvaluationFormXEmployeeRelationshipRepository;

    @Override
    public void saveAndFlush(AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship) {
        appraisalXEvaluationFormXEmployeeRelationshipRepository.save(appraisalXEvaluationFormXEmployeeRelationship);
    }
}
