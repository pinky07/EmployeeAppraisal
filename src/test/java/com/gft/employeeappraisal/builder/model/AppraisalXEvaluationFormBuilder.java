package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.AppraisalXEvaluationForm;
import com.gft.employeeappraisal.model.EvaluationForm;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link AppraisalXEvaluationForm} object.
 *
 * @author Rubén Jiménez
 */
public class AppraisalXEvaluationFormBuilder implements ObjectBuilder<AppraisalXEvaluationForm> {

    private static int currentId = 1_000_000;

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
        obj.setId(this.idSet ? this.id : this.currentId++);
        obj.setAppraisal(this.appraisalSet ? this.appraisal : new AppraisalBuilder().buildWithDefaults());
        obj.setEvaluationForm(this.evaluationFormSet ? this.evaluationForm : new EvaluationFormBuilder().buildWithDefaults());
        return obj;
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
