package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import com.gft.employeeappraisal.repository.EvaluationFormTemplateRepository;
import com.gft.employeeappraisal.service.EvaluationFormTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link EvaluationFormTemplateService}.
 *
 * @author Rubén Jiménez
 */
@Service
public class EvaluationFormTemplateServiceImpl implements EvaluationFormTemplateService {

    private final EvaluationFormTemplateRepository evaluationFormTemplateRepository;

    @Autowired
    public EvaluationFormTemplateServiceImpl(
            EvaluationFormTemplateRepository evaluationFormTemplateRepository) {
        this.evaluationFormTemplateRepository = evaluationFormTemplateRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public EvaluationFormTemplate getById(Integer id) throws NotFoundException {
        return this.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "EvaluationFormTemplate[%d] couldn't be found",
                id)));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<EvaluationFormTemplate> findById(Integer id) {
        return Optional.ofNullable(evaluationFormTemplateRepository.findOne(id));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<EvaluationFormTemplate> findByIdAndFilledByEmployeeAndAppraisal(Integer id,
                                                                                    Employee filledByEmployee,
                                                                                    Appraisal appraisal) {
        return Optional.ofNullable(evaluationFormTemplateRepository
                .findByIdAndFilledByEmployeeAndAppraisal(id, filledByEmployee, appraisal));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<EvaluationFormTemplate> saveAndFlush(EvaluationFormTemplate evaluationFormTemplate) {
        return Optional.ofNullable(this.evaluationFormTemplateRepository.saveAndFlush(evaluationFormTemplate));
    }
}
