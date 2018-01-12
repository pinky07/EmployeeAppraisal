package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXJobLevel;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * Builder object for the {@link EvaluationFormTemplate} object.
 *
 * @author Ruben Jimenez
 */
public class EvaluationFormTemplateBuilder implements ObjectBuilder<EvaluationFormTemplate> {

    private int id;
    private String name;
    private String description;
    private Set<EvaluationFormTemplateXSectionXQuestion> evaluationFormTemplateXSectionXQuestionSet;
    private Set<EmployeeEvaluationForm> employeeEvaluationFormSet;
    private Set<EvaluationFormTemplateXJobLevel> evaluationFormTemplateXJobLevelSet;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;
    private boolean questionsSet;
    private boolean employeeEvaluationFormsSet;
    private boolean evaluationFormTemplateJobLevelsSet;

    public EvaluationFormTemplateBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EvaluationFormTemplateBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public EvaluationFormTemplateBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    public EvaluationFormTemplateBuilder questions(Set<EvaluationFormTemplateXSectionXQuestion> questionSet) {
        this.evaluationFormTemplateXSectionXQuestionSet = questionSet;
        this.questionsSet = true;
        return this;
    }

    public EvaluationFormTemplateBuilder employeeEvaluationForms(Set<EmployeeEvaluationForm> employeeEvaluationForms) {
        this.employeeEvaluationFormSet = employeeEvaluationForms;
        this.employeeEvaluationFormsSet = true;
        return this;
    }

    public EvaluationFormTemplateBuilder jobLevels(Set<EvaluationFormTemplateXJobLevel> evaluationFormTemplateXJobLevels) {
        this.evaluationFormTemplateXJobLevelSet = evaluationFormTemplateXJobLevels;
        this.evaluationFormTemplateJobLevelsSet = true;
        return this;
    }

    @Override
    public EvaluationFormTemplate build() {
        EvaluationFormTemplate obj = new EvaluationFormTemplate();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        obj.setEvaluationFormXSectionXQuestionSet(this.evaluationFormTemplateXSectionXQuestionSet);
        obj.setEmployeeEvaluationFormSet(this.employeeEvaluationFormSet);
        obj.setEvaluationFormTemplateXJobLevelSet(this.evaluationFormTemplateXJobLevelSet);
        return obj;
    }

    @Override
    public EvaluationFormTemplate buildWithDefaults() {
        EvaluationFormTemplate obj = new EvaluationFormTemplate();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : "Name");
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        obj.setEvaluationFormXSectionXQuestionSet(this.questionsSet ?
                this.evaluationFormTemplateXSectionXQuestionSet :
                new HashSet<>(Collections
                        .singletonList(new EvaluationFormTemplateXSectionXQuestionBuilder(obj)
                                .buildWithDefaults())));
        /*obj.setEmployeeEvaluationFormSet(this.employeeEvaluationFormsSet ?
                this.employeeEvaluationFormSet :
                new HashSet<>(Collections.singletonList(new EmployeeEvaluationFormBuilder(
                        new AppraisalBuilder().buildWithDefaults(), obj).buildWithDefaults())));*/
        obj.setEvaluationFormTemplateXJobLevelSet(this.evaluationFormTemplateJobLevelsSet ?
                this.evaluationFormTemplateXJobLevelSet : new HashSet<>(Collections
                    .singletonList(new EvaluationFormTemplateXJobLevelBuilder().evaluationFormTemplate(obj).buildWithDefaults())));
        return obj;
    }
}
