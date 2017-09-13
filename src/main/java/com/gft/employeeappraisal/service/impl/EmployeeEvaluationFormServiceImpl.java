package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.repository.EmployeeEvaluationFormRepository;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Service implementation of {@link EmployeeEvaluationFormService}.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@Service
public class EmployeeEvaluationFormServiceImpl implements EmployeeEvaluationFormService {

    private EmployeeEvaluationFormRepository employeeEvaluationFormRepository;

    /**
     * @inheritDoc
     */
    @Autowired
    public EmployeeEvaluationFormServiceImpl(EmployeeEvaluationFormRepository employeeEvaluationFormRepository) {
        this.employeeEvaluationFormRepository = employeeEvaluationFormRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<EmployeeEvaluationForm> findById(int id) {
        return Optional.ofNullable(this.employeeEvaluationFormRepository.findOne(id));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<EmployeeEvaluationForm> findByEmployee(Employee employee) {
        return this.employeeEvaluationFormRepository.findByEmployee(employee)
                .stream();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<EmployeeEvaluationForm> findSelfByEmployee(Employee employee) {
        return this.employeeEvaluationFormRepository.findByEmployeeAndFilledByEmployee(employee, employee)
                .stream();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<EmployeeEvaluationForm> findSelfByEmployeeAndAppraisal(Employee employee, Appraisal appraisal) {
        return Optional.ofNullable(this.employeeEvaluationFormRepository
                .findSelfByEmployeeAndAppraisal(employee, appraisal));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<EmployeeEvaluationForm> findByEmployeeAndAppraisal(Employee employee, Appraisal appraisal) {
        return this.employeeEvaluationFormRepository.findByEmployeeAndAppraisal(employee, appraisal)
                .stream();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<EmployeeEvaluationForm> findByFilledByEmployeeAndAppraisal(Employee employee, Appraisal appraisal) {
        return this.employeeEvaluationFormRepository.findByFilledByEmployeeAndAppraisal(employee, appraisal)
                .stream();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<EmployeeEvaluationForm> findByMentorAndAppraisal(Employee mentor, Appraisal appraisal) {
        return this.employeeEvaluationFormRepository.findByMentorAndAppraisal(mentor, appraisal)
                .stream();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<EmployeeEvaluationForm> saveAndFlush(EmployeeEvaluationForm employeeEvaluationForm) {
        return Optional.ofNullable(employeeEvaluationFormRepository.saveAndFlush(employeeEvaluationForm));
    }
}
