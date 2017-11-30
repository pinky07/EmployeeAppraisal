package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.repository.EmployeeEvaluationFormAnswerRepository;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmployeeEvaluationFormAnswerServiceImpl implements EmployeeEvaluationFormAnswerService
{

    private final EmployeeEvaluationFormAnswerRepository employeeEvaluationFormAnswerRepository;

    /**
     * @inheritDoc
     */
    @Autowired
    public EmployeeEvaluationFormAnswerServiceImpl(EmployeeEvaluationFormAnswerRepository employeeEvaluationFormAnswerRepository) {
        this.employeeEvaluationFormAnswerRepository = employeeEvaluationFormAnswerRepository;
    }

}
