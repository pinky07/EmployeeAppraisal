package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.EmployeeEvaluationFormAnswer;
import com.gft.employeeappraisal.repository.EmployeeEvaluationFormAnswerRepository;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeEvaluationFormAnswerServiceImpl implements EmployeeEvaluationFormAnswerService {

    private final EmployeeEvaluationFormAnswerRepository employeeEvaluationFormAnswerRepository;

    /**
     * @inheritDoc
     */
    @Autowired
    public EmployeeEvaluationFormAnswerServiceImpl(EmployeeEvaluationFormAnswerRepository employeeEvaluationFormAnswerRepository) {
        this.employeeEvaluationFormAnswerRepository = employeeEvaluationFormAnswerRepository;
    }

    @Override
    public Optional<EmployeeEvaluationFormAnswer> findById(int id) {
        return Optional.ofNullable(this.employeeEvaluationFormAnswerRepository.findOne(id));
    }

    @Override
    public Optional<EmployeeEvaluationFormAnswer> saveAndFlush(EmployeeEvaluationFormAnswer entity) {
        return Optional.ofNullable(employeeEvaluationFormAnswerRepository.saveAndFlush(entity));
    }

    @Override
    public EmployeeEvaluationFormAnswer getById(Integer id) throws NotFoundException {
        return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "EmployeeEvaluationFormAnswer [%d] couldn't be found",
                id)));
    }
}
