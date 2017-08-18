package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.AppraisalXEvaluationForm;
import com.gft.employeeappraisal.model.AppraisalXEvaluationFormXEmployeeRelationship;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.EvaluationStatus;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * TODO: Document this!
 *
 * @author Rubén Jiménez
 */
public class AppraisalXEvaluationFormXEmployeeRelationshipBuilder implements ObjectBuilder<AppraisalXEvaluationFormXEmployeeRelationship> {

    private static int currentId = 1_000_000;

    private int id;
    private AppraisalXEvaluationForm appraisalXEvaluationForm;
    private EmployeeRelationship employeeRelationship;
    private EvaluationStatus evaluationStatus;

    private boolean idSet;
    private boolean appraisalXEvaluationFormSet;
    private boolean employeeRelationshipSet;
    private boolean evaluationStatusSet;

    public AppraisalXEvaluationFormXEmployeeRelationshipBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public AppraisalXEvaluationFormXEmployeeRelationshipBuilder appraisalXEvaluationForm(AppraisalXEvaluationForm appraisalXEvaluationForm) {
        this.appraisalXEvaluationForm = appraisalXEvaluationForm;
        this.appraisalXEvaluationFormSet = true;
        return this;
    }

    public AppraisalXEvaluationFormXEmployeeRelationshipBuilder employeeRelationship(EmployeeRelationship employeeRelationship) {
        this.employeeRelationship = employeeRelationship;
        this.employeeRelationshipSet = true;
        return this;
    }

    public AppraisalXEvaluationFormXEmployeeRelationshipBuilder evaluationStatus(EvaluationStatus evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
        this.evaluationStatusSet = true;
        return this;
    }

    @Override
    public AppraisalXEvaluationFormXEmployeeRelationship build() {
        AppraisalXEvaluationFormXEmployeeRelationship obj = new AppraisalXEvaluationFormXEmployeeRelationship();
        obj.setId(this.id);
        obj.setAppraisalXEvaluationForm(this.appraisalXEvaluationForm);
        obj.setEmployeeRelationship(this.employeeRelationship);
        obj.setEvaluationStatus(this.evaluationStatus);
        return obj;
    }

    @Override
    public AppraisalXEvaluationFormXEmployeeRelationship buildWithDefaults() {
        AppraisalXEvaluationFormXEmployeeRelationship obj = new AppraisalXEvaluationFormXEmployeeRelationship();
        obj.setId(this.idSet ? this.id : currentId++);
        obj.setAppraisalXEvaluationForm(this.appraisalXEvaluationFormSet ? this.appraisalXEvaluationForm :
                new AppraisalXEvaluationFormBuilder().buildWithDefaults());
        obj.setEmployeeRelationship(this.employeeRelationshipSet ? this.employeeRelationship :
                new EmployeeRelationshipBuilder().buildWithDefaults());
        obj.setEvaluationStatus(this.evaluationStatusSet ? this.evaluationStatus : EvaluationStatus.PENDING);
        return obj;
    }

    @Override
    public AppraisalXEvaluationFormXEmployeeRelationship buildMock() {
        AppraisalXEvaluationFormXEmployeeRelationship mock = Mockito.mock(AppraisalXEvaluationFormXEmployeeRelationship.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getAppraisalXEvaluationForm()).thenReturn(this.appraisalXEvaluationForm);
        when(mock.getEmployeeRelationship()).thenReturn(this.employeeRelationship);
        when(mock.getEvaluationStatus()).thenReturn(this.evaluationStatus);
        return mock;
    }
}