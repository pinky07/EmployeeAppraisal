package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.helper.builder.model.AppraisalBuilder;
import com.gft.employeeappraisal.helper.builder.model.AppraisalXEvaluationFormTemplateBuilder;
import com.gft.employeeappraisal.helper.builder.model.EvaluationFormTemplateBuilder;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.AppraisalXEvaluationFormTemplate;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import com.gft.employeeappraisal.service.impl.AppraisalXEvaluationFormTemplateServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class AppraisalXEvaluationFormTemplateServiceTest extends BaseServiceTest {

    private Appraisal appraisal;
    private EvaluationFormTemplate evaluationFormTemplate;

    @Before
    public void setUp() {
        this.appraisalXEvaluationFormTemplateService =
                new AppraisalXEvaluationFormTemplateServiceImpl(this.appraisalXEvaluationFormTemplateRepository);

        // Test Evaluation Form
        evaluationFormTemplate = this.evaluationFormTemplateRepository.saveAndFlush(new EvaluationFormTemplateBuilder()
                .buildWithDefaults());

        this.appraisal = this.appraisalRepository.saveAndFlush(new AppraisalBuilder().buildWithDefaults());
    }

    @Test
    public void saveAndFlush() throws Exception {
        AppraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate =
                new AppraisalXEvaluationFormTemplateBuilder()
                        .appraisal(appraisal)
                        .evaluationFormTemplate(evaluationFormTemplate)
                        .buildWithDefaults();

        long beforeCount = appraisalXEvaluationFormTemplateRepository.count();
        Optional<AppraisalXEvaluationFormTemplate> retrieved = appraisalXEvaluationFormTemplateService
                .saveAndFlush(appraisalXEvaluationFormTemplate);
        long afterCount = appraisalXEvaluationFormTemplateRepository.count();

        assertTrue(beforeCount + 1 == afterCount);
        assertTrue(retrieved.isPresent());
        assertEquals(appraisalXEvaluationFormTemplate, retrieved.get());
    }

}