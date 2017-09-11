package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.AppraisalXEvaluationFormTemplate;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;

/**
 * Builder object for the {@link AppraisalXEvaluationFormTemplate} object.
 *
 * @author Rubén Jiménez
 */
public class AppraisalXEvaluationFormTemplateBuilder implements ObjectBuilder<AppraisalXEvaluationFormTemplate> {

    private int id;
    private Appraisal appraisal;
    private EvaluationFormTemplate evaluationFormTemplate;

    private boolean idSet;
    private boolean appraisalSet;
    private boolean evaluationFormSet;

    public AppraisalXEvaluationFormTemplateBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public AppraisalXEvaluationFormTemplateBuilder appraisal(Appraisal appraisal) {
        this.appraisal = appraisal;
        this.appraisalSet = true;
        return this;
    }

    public AppraisalXEvaluationFormTemplateBuilder evaluationForm(EvaluationFormTemplate evaluationFormTemplate) {
        this.evaluationFormTemplate = evaluationFormTemplate;
        this.evaluationFormSet = true;
        return this;
    }

    @Override
    public AppraisalXEvaluationFormTemplate build() {
        AppraisalXEvaluationFormTemplate obj = new AppraisalXEvaluationFormTemplate();
        obj.setId(this.id);
        obj.setAppraisal(this.appraisal);
        obj.setEvaluationFormTemplate(this.evaluationFormTemplate);
        return obj;
    }

    @Override
    public AppraisalXEvaluationFormTemplate buildWithDefaults() {
        AppraisalXEvaluationFormTemplate obj = new AppraisalXEvaluationFormTemplate();
        if (this.idSet) obj.setId(this.id);
        obj.setAppraisal(this.appraisalSet ? this.appraisal : new AppraisalBuilder().buildWithDefaults());
        obj.setEvaluationFormTemplate(this.evaluationFormSet ? this.evaluationFormTemplate : new EvaluationFormBuilder().buildWithDefaults());
        return obj;
    }
}
