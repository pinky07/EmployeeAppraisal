package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.helper.builder.model.AppraisalXEvaluationFormBuilder;
import com.gft.employeeappraisal.model.AppraisalXEvaluationForm;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EvaluationStatus;

import java.time.OffsetDateTime;

/**
 * Builder object for the {@link EmployeeEvaluationForm} object.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
public class EmployeeEvaluationFormBuilder implements ObjectBuilder<EmployeeEvaluationForm> {

    private int id;
    private AppraisalXEvaluationForm appraisalXEvaluationForm;
    private int employeeId;
    private int filledByEmployeeId;
    private int mentorId;
    private OffsetDateTime createDate;
    private OffsetDateTime submitDate;
    private EvaluationStatus evaluationStatus;

    private boolean idSet;
    private boolean appraisalXEvaluationFormSet;
    private boolean employeeIdSet;
    private boolean filledByEmployeeIdSet;
    private boolean mentorIdSet;
    private boolean createDateSet;
    private boolean submitDateSet;
    private boolean evaluationStatusSet;

    public EmployeeEvaluationFormBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder appraisalXEvaluationForm(AppraisalXEvaluationForm appraisalXEvaluationForm) {
        this.appraisalXEvaluationForm = appraisalXEvaluationForm;
        this.appraisalXEvaluationFormSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder employeeId(int employeeId) {
        this.employeeId = employeeId;
        this.employeeIdSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder filledByEmployeeId(int filledByEmployeeId) {
        this.filledByEmployeeId = filledByEmployeeId;
        this.filledByEmployeeIdSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder mentorId(int mentorId) {
        this.mentorId = mentorId;
        this.mentorIdSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder createDate(OffsetDateTime createDate) {
        this.createDate = createDate;
        this.createDateSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder submitDate(OffsetDateTime submitDate) {
        this.submitDate = submitDate;
        this.submitDateSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder evaluationStatus(EvaluationStatus evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
        this.evaluationStatusSet = true;
        return this;
    }

    @Override
    public EmployeeEvaluationForm build() {
        EmployeeEvaluationForm obj = new EmployeeEvaluationForm();
        obj.setId(this.id);
        obj.setAppraisalXEvaluationForm(this.appraisalXEvaluationForm);
        obj.setEmployeeId(this.employeeId);
        obj.setFilledByEmployeeId(this.filledByEmployeeId);
        obj.setMentorId(this.mentorId);
        obj.setCreateDate(this.createDate);
        obj.setSubmitDate(this.submitDate);
        obj.setEvaluationStatus(this.evaluationStatus);
        return obj;
    }

    @Override
    public EmployeeEvaluationForm buildWithDefaults() {
        EmployeeEvaluationForm obj = new EmployeeEvaluationForm();
        if (this.idSet) obj.setId(this.id);
        obj.setAppraisalXEvaluationForm(this.appraisalXEvaluationFormSet ? this.appraisalXEvaluationForm :
                new AppraisalXEvaluationFormBuilder().buildWithDefaults());
        if (this.employeeIdSet) obj.setEmployeeId(this.employeeId);
        if (this.filledByEmployeeIdSet) obj.setFilledByEmployeeId(this.filledByEmployeeId);
        if (this.mentorIdSet) obj.setMentorId(this.mentorId);
        obj.setCreateDate(this.createDateSet ? this.createDate : OffsetDateTime.now());
        obj.setSubmitDate(this.submitDateSet ? this.submitDate : OffsetDateTime.now().plusDays(1));
        obj.setEvaluationStatus(this.evaluationStatusSet ? this.evaluationStatus : EvaluationStatus.PENDING);
        return obj;
    }
}
