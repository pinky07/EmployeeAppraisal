package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.AppraisalXEvaluationForm;
import com.gft.employeeappraisal.model.AppraisalXEvaluationFormXEmployeeRelationship;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.EvaluationStatus;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class AppraisalXEvaluationFormXEmployeeRelationshipBuilder implements ObjectBuilder<AppraisalXEvaluationFormXEmployeeRelationship, Integer> {

    private int id;
    private AppraisalXEvaluationForm appraisalXEvaluationForm;
    private EmployeeRelationship employeeRelationship;
    private EvaluationStatus evaluationStatus;

    public AppraisalXEvaluationFormXEmployeeRelationshipBuilder id(int id) {
        this.id = id;
        return this;
    }

    public AppraisalXEvaluationFormXEmployeeRelationshipBuilder appraisalXEvaluationForm(AppraisalXEvaluationForm appraisalXEvaluationForm) {
        this.appraisalXEvaluationForm = appraisalXEvaluationForm;
        return this;
    }

    public AppraisalXEvaluationFormXEmployeeRelationshipBuilder employeeRelationship(EmployeeRelationship employeeRelationship) {
        this.employeeRelationship = employeeRelationship;
        return this;
    }

    public AppraisalXEvaluationFormXEmployeeRelationshipBuilder evaluationStatus(EvaluationStatus evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
        return this;
    }

    @Override
    public AppraisalXEvaluationFormXEmployeeRelationship build() {
        AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship = new AppraisalXEvaluationFormXEmployeeRelationship();
        appraisalXEvaluationFormXEmployeeRelationship.setId(this.id);
        appraisalXEvaluationFormXEmployeeRelationship.setAppraisalXEvaluationForm(this.appraisalXEvaluationForm);
        appraisalXEvaluationFormXEmployeeRelationship.setEmployeeRelationship(this.employeeRelationship);
        appraisalXEvaluationFormXEmployeeRelationship.setEvaluationStatus(this.evaluationStatus);
        return appraisalXEvaluationFormXEmployeeRelationship;
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
