package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.AppraisalXEvaluationFormTemplate;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;

import java.time.OffsetDateTime;

/**
 * Builder object for the {@link EmployeeEvaluationForm} object.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
public class EmployeeEvaluationFormBuilder implements ObjectBuilder<EmployeeEvaluationForm> {

    private int id;
    private AppraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate;
    private Employee employee;
    private Employee filledByEmployee;
    private Employee mentor;
    private OffsetDateTime createDate;
    private OffsetDateTime submitDate;

    private boolean idSet;
    private boolean appraisalXEvaluationFormSet;
    private boolean employeeSet;
    private boolean filledByEmployeeSet;
    private boolean mentorSet;
    private boolean createDateSet;
    private boolean submitDateSet;

    public EmployeeEvaluationFormBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder appraisalXEvaluationForm(AppraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate) {
        this.appraisalXEvaluationFormTemplate = appraisalXEvaluationFormTemplate;
        this.appraisalXEvaluationFormSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder employee(Employee employee) {
        this.employee = employee;
        this.employeeSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder filledByEmployee(Employee filledByEmployee) {
        this.filledByEmployee = filledByEmployee;
        this.filledByEmployeeSet = true;
        return this;
    }

    public EmployeeEvaluationFormBuilder mentor(Employee mentor) {
        this.mentor = mentor;
        this.mentorSet = true;
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

    @Override
    public EmployeeEvaluationForm build() {
        EmployeeEvaluationForm obj = new EmployeeEvaluationForm();
        obj.setId(this.id);
        obj.setAppraisalXEvaluationFormTemplate(this.appraisalXEvaluationFormTemplate);
        obj.setEmployee(this.employee);
        obj.setFilledByEmployee(this.filledByEmployee);
        obj.setMentor(this.mentor);
        obj.setCreateDate(this.createDate);
        obj.setSubmitDate(this.submitDate);
        return obj;
    }

    @Override
    public EmployeeEvaluationForm buildWithDefaults() {
        EmployeeEvaluationForm obj = new EmployeeEvaluationForm();
        if (this.idSet) obj.setId(this.id);
        obj.setAppraisalXEvaluationFormTemplate(this.appraisalXEvaluationFormSet ? this.appraisalXEvaluationFormTemplate :
                new AppraisalXEvaluationFormTemplateBuilder().buildWithDefaults());
        if (this.employeeSet) obj.setEmployee(this.employee);
        if (this.filledByEmployeeSet) obj.setFilledByEmployee(this.filledByEmployee);
        if (this.mentorSet) obj.setMentor(this.mentor);
        obj.setCreateDate(this.createDateSet ? this.createDate : OffsetDateTime.now());
        obj.setSubmitDate(this.submitDateSet ? this.submitDate : OffsetDateTime.now().plusDays(1));
        return obj;
    }
}