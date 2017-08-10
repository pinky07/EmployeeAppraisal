package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.AppraisalXEvaluationForm;
import com.gft.employeeappraisal.model.EvaluationForm;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class AppraisalXEvaluationFormBuilder implements ObjectBuilder<AppraisalXEvaluationForm, Number> {

    private int id;
    private Appraisal appraisal;
    private EvaluationForm evaluationForm;

    public AppraisalXEvaluationFormBuilder id(int id) {
        this.id = id;
        return this;
    }

    public AppraisalXEvaluationFormBuilder appraisal(Appraisal appraisal) {
        this.appraisal = appraisal;
        return this;
    }

    public AppraisalXEvaluationFormBuilder evaluationForm(EvaluationForm evaluationForm) {
        this.evaluationForm = evaluationForm;
        return this;
    }

    @Override
    public AppraisalXEvaluationForm build() {
        AppraisalXEvaluationForm appraisalXEvaluationForm = new AppraisalXEvaluationForm();
        appraisalXEvaluationForm.setId(this.id);
        appraisalXEvaluationForm.setAppraisal(this.appraisal);
        appraisalXEvaluationForm.setEvaluationForm(this.evaluationForm);
        return appraisalXEvaluationForm;
    }

    @Override
    public AppraisalXEvaluationForm buildMock() {
        AppraisalXEvaluationForm mock = Mockito.mock(AppraisalXEvaluationForm.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getAppraisal()).thenReturn(this.appraisal);
        when(mock.getEvaluationForm()).thenReturn(this.evaluationForm);
        return mock;
    }
}
