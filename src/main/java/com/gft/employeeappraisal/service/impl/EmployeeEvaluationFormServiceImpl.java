package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EvaluationStatus;
import com.gft.employeeappraisal.repository.EmployeeEvaluationFormRepository;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

/**
 * Service implementation of {@link EmployeeEvaluationFormService}.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@Service
public class EmployeeEvaluationFormServiceImpl implements
        EmployeeEvaluationFormService {

    private EmployeeEvaluationFormRepository employeeEvaluationFormRepository;

    @Autowired
    public EmployeeEvaluationFormServiceImpl(
            EmployeeEvaluationFormRepository employeeEvaluationFormRepository) {
        this.employeeEvaluationFormRepository = employeeEvaluationFormRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<EmployeeEvaluationForm> findByAppraisalAndEmployee(
            Appraisal appraisal,
            Employee employee
    ) {

        // Query the DB
        List<EmployeeEvaluationForm> sourceList = new ArrayList<>(this.employeeEvaluationFormRepository
                .findByAppraisalAndSourceEmployee(appraisal, employee.getId()));

        return sourceList.stream();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<EmployeeEvaluationForm> findByEmployeeAndFilledByEmployeeId(
            Employee employee,
            EvaluationStatus... evaluationStatus
    ) {

        // Query the DB
        List<EmployeeEvaluationForm> sourceList = new ArrayList<>(this.employeeEvaluationFormRepository
                .findByAppraisalAndEmployeeAsSourceAndTarget(employee.getId(), new HashSet<>(Arrays.asList(evaluationStatus))));

        return sourceList.stream();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void saveAndFlush(EmployeeEvaluationForm employeeEvaluationForm) {
        employeeEvaluationFormRepository.save(employeeEvaluationForm);
    }
}
