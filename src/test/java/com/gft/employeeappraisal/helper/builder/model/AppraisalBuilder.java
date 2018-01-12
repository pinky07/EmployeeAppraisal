package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;

import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Builder object for the {@link Appraisal} object.
 *
 * @author Manuel Yepez
 */
public class AppraisalBuilder implements ObjectBuilder<Appraisal> {

    private int id;
    private String name;
    private String description;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Set<EmployeeEvaluationForm> employeeEvaluationFormSet;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;
    private boolean startDateSet;
    private boolean endDateSet;
    private boolean evaluationFormsSet;

    public AppraisalBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public AppraisalBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public AppraisalBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    public AppraisalBuilder startDate(OffsetDateTime startDate) {
        this.startDate = startDate;
        this.startDateSet = true;
        return this;
    }

    public AppraisalBuilder endDate(OffsetDateTime endDate) {
        this.endDate = endDate;
        this.endDateSet = true;
        return this;
    }

    public AppraisalBuilder employeeEvaluationForms(Set<EmployeeEvaluationForm> employeeEvaluationForms) {
        this.employeeEvaluationFormSet = employeeEvaluationForms;
        this.evaluationFormsSet = true;
        return this;
    }

    @Override
    public Appraisal build() {
        Appraisal obj = new Appraisal();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        obj.setStartDate(this.startDate);
        obj.setEndDate(this.endDate);
        obj.setEmployeeEvaluationFormSet(this.employeeEvaluationFormSet);
        return obj;
    }

    @Override
    public Appraisal buildWithDefaults() {
        Appraisal obj = new Appraisal();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : "Name");
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        obj.setStartDate(this.startDateSet ? this.startDate : OffsetDateTime.now().minusDays(1));
        obj.setEndDate(this.endDateSet ? this.endDate : OffsetDateTime.now().plusDays(1));
        // commented while a better solution is found, this causes a StackOverflowError
        /*obj.setEmployeeEvaluationFormSet(this.evaluationFormsSet ? this.employeeEvaluationFormSet :
            new HashSet<>(Collections.singletonList(new EmployeeEvaluationFormBuilder(obj,
                    new EvaluationFormTemplateBuilder().buildWithDefaults()).buildWithDefaults())));*/
        return obj;
    }
}
