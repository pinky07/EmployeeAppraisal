package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.AppraisalXEvaluationFormTemplate;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.repository.AppraisalRepository;
import com.gft.employeeappraisal.service.AppraisalService;
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

    @Autowired
    public AppraisalServiceImpl(
            AppraisalRepository appraisalRepository,
            EmployeeEvaluationFormService employeeEvaluationFormService) {
        this.appraisalRepository = appraisalRepository;
        this.employeeEvaluationFormService = employeeEvaluationFormService;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Appraisal> findById(int appraisalId) {
        return Optional.ofNullable(this.appraisalRepository.findOne(appraisalId));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Appraisal getById(int appraisalId) throws NotFoundException {
        return this.findById(appraisalId).orElseThrow(() -> new NotFoundException(String.format(
                "Appraisal with id %d was not found", appraisalId)));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<Appraisal> findEmployeeAppraisals(Employee employee) {
        return employeeEvaluationFormService
                .findSelfByEmployee(employee)
                .map(EmployeeEvaluationForm::getAppraisalXEvaluationFormTemplate)
                .map(AppraisalXEvaluationFormTemplate::getAppraisal);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Appraisal> saveAndFlush(Appraisal appraisal) {
        return Optional.ofNullable(this.appraisalRepository.saveAndFlush(appraisal));
    }
}
