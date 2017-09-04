package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.AppraisalXEvaluationForm;
import com.gft.employeeappraisal.model.EvaluationForm;

/**
 * Builder object for the {@link AppraisalXEvaluationForm} object.
 *
 * @author Rubén Jiménez
 */
public class AppraisalXEvaluationFormBuilder implements ObjectBuilder<AppraisalXEvaluationForm> {

    private int id;
    private Appraisal appraisal;
    private EvaluationForm evaluationForm;

    private boolean idSet;
    private boolean appraisalSet;
    private boolean evaluationFormSet;

    public AppraisalXEvaluationFormBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public AppraisalXEvaluationFormBuilder appraisal(Appraisal appraisal) {
        this.appraisal = appraisal;
        this.appraisalSet = true;
        return this;
    }

    public AppraisalXEvaluationFormBuilder evaluationForm(EvaluationForm evaluationForm) {
        this.evaluationForm = evaluationForm;
        this.evaluationFormSet = true;
        return this;
    }

    @Override
    public AppraisalXEvaluationForm build() {
        AppraisalXEvaluationForm obj = new AppraisalXEvaluationForm();
        obj.setId(this.id);
        obj.setAppraisal(this.appraisal);
        obj.setEvaluationForm(this.evaluationForm);
        return obj;
    }

    @Override
    public AppraisalXEvaluationForm buildWithDefaults() {
        AppraisalXEvaluationForm obj = new AppraisalXEvaluationForm();
        if (this.idSet) obj.setId(this.id);
        obj.setAppraisal(this.appraisalSet ? this.appraisal : new AppraisalBuilder().buildWithDefaults());
        obj.setEvaluationForm(this.evaluationFormSet ? this.evaluationForm : new EvaluationFormBuilder().buildWithDefaults());
        return obj;
    }
}
