package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.helper.builder.model.EvaluationFormTemplateBuilder;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import com.gft.employeeappraisal.service.impl.EvaluationFormTemplateServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class EvaluationFormTemplateServiceTest extends BaseServiceTest {

    @Before
    public void setUp() throws Exception {
        this.evaluationFormTemplateService =
                new EvaluationFormTemplateServiceImpl(this.evaluationFormTemplateRepository);
    }

    @Test
    public void saveAndFlush() throws Exception {
        EvaluationFormTemplate evaluationFormTemplate = new EvaluationFormTemplateBuilder().buildWithDefaults();

        long beforeCount = evaluationFormTemplateRepository.count();
        Optional<EvaluationFormTemplate> retrieved = evaluationFormTemplateService.saveAndFlush(evaluationFormTemplate);
        long afterCount = evaluationFormTemplateRepository.count();

        assertTrue(beforeCount + 1 == afterCount);
        assertTrue(retrieved.isPresent());
        assertEquals(evaluationFormTemplate, retrieved.get());
    }

}