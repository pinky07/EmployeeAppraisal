package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXJobLevel;
import com.gft.employeeappraisal.model.JobLevel;

/**
 * Builder object for the {@link EvaluationFormTemplateXJobLevel} object.
 *
 * @author Manuel Yepez
 */
public class EvaluationFormTemplateXJobLevelBuilder implements ObjectBuilder<EvaluationFormTemplateXJobLevel> {

    private int id;
    private EvaluationFormTemplate evaluationFormTemplate;
    private JobLevel jobLevel;

    private boolean idSet;
    private boolean evaluationFormTemplateSet;
    private boolean jobLevelSet;

    public EvaluationFormTemplateXJobLevelBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EvaluationFormTemplateXJobLevelBuilder evaluationFormTemplate(EvaluationFormTemplate evaluationFormTemplate) {
        this.evaluationFormTemplate = evaluationFormTemplate;
        this.evaluationFormTemplateSet = true;
        return this;
    }

    public EvaluationFormTemplateXJobLevelBuilder jobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
        this.jobLevelSet = true;
        return this;
    }

    @Override
    public EvaluationFormTemplateXJobLevel build() {
        EvaluationFormTemplateXJobLevel obj = new EvaluationFormTemplateXJobLevel();
        obj.setId(this.id);
        obj.setEvaluationFormTemplate(this.evaluationFormTemplate);
        obj.setJobLevel(this.jobLevel);
        return obj;
    }

    @Override
    public EvaluationFormTemplateXJobLevel buildWithDefaults() {
        EvaluationFormTemplateXJobLevel obj = new EvaluationFormTemplateXJobLevel();
        if (this.idSet) obj.setId(this.id);
        obj.setEvaluationFormTemplate(this.evaluationFormTemplateSet ? this.evaluationFormTemplate :
            new EvaluationFormTemplateBuilder().buildWithDefaults());
        obj.setJobLevel(this.jobLevelSet ? this.jobLevel : new JobLevelBuilder().buildWithDefaults());
        return obj;
    }
}
