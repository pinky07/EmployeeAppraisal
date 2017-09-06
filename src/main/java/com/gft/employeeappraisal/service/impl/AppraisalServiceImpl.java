package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EvaluationStatus;
import com.gft.employeeappraisal.repository.AppraisalRepository;
import com.gft.employeeappraisal.service.AppraisalService;
import com.gft.employeeappraisal.service.AppraisalXEvaluationFormService;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service implementation of {@link AppraisalService}.
 *
 * @author Manuel Yepez
 */
@Service
public class AppraisalServiceImpl implements AppraisalService {

    private final AppraisalRepository appraisalRepository;
    private final EmployeeEvaluationFormService employeeEvaluationFormService;
    private final AppraisalXEvaluationFormService appraisalXEvaluationFormService;

    @Autowired
    public AppraisalServiceImpl(
            AppraisalRepository appraisalRepository,
            EmployeeEvaluationFormService employeeEvaluationFormService,
            AppraisalXEvaluationFormService appraisalXEvaluationFormService) {
        this.appraisalRepository = appraisalRepository;
        this.employeeEvaluationFormService = employeeEvaluationFormService;
        this.appraisalXEvaluationFormService = appraisalXEvaluationFormService;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Appraisal> findById(int appraisalId) {
        return Optional.ofNullable(appraisalRepository.findOne(appraisalId));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<Appraisal> findEmployeeAppraisals(Employee employee, EvaluationStatus evaluationStatus) {

        if (evaluationStatus == null) {
            evaluationStatus = EvaluationStatus.PENDING;
        }

        Stream<EmployeeEvaluationForm> employeeEvaluationFormStream =
                employeeEvaluationFormService
                        .findByEmployeeAndFilledByEmployeeId(employee, evaluationStatus);

        return employeeEvaluationFormStream
                .map(ef -> ef.getAppraisalXEvaluationForm().getAppraisal()).distinct();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Appraisal> saveAndFlush(Appraisal appraisal) {
        return Optional.ofNullable(appraisalRepository.saveAndFlush(appraisal));
    }
}
