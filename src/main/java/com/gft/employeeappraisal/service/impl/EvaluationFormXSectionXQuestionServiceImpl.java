package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EvaluationForm;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import com.gft.employeeappraisal.service.EvaluationFormXSectionXQuestionService;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Service implementation of {@link EvaluationFormXSectionXQuestionService}.
 *
 * @author Rubén Jiménez
 */
@Service
public class EvaluationFormXSectionXQuestionServiceImpl implements EvaluationFormXSectionXQuestionService {

    private EmployeeEvaluationFormService employeeEvaluationFormService;

    @Autowired
    public EvaluationFormXSectionXQuestionServiceImpl(
            EmployeeEvaluationFormService employeeEvaluationFormService) {
        this.employeeEvaluationFormService = employeeEvaluationFormService;
    }

    @Override
    public Stream<EvaluationForm> findByAppraisalAndEmployee(Appraisal appraisal, Employee employee) {
        // TODO Implement this method!
        throw new NotImplementedException();
    }

}
