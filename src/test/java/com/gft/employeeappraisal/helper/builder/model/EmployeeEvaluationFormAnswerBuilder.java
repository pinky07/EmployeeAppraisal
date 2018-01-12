package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EmployeeEvaluationFormAnswer;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import com.gft.employeeappraisal.model.ScoreValue;

/**
 * Builder object for the {@link EmployeeEvaluationFormAnswer} class.
 *
 * @author Manuel Yépez
 */
public class EmployeeEvaluationFormAnswerBuilder implements ObjectBuilder<EmployeeEvaluationFormAnswer> {

    private int id;
    private EmployeeEvaluationForm employeeEvaluationForm;
    private EvaluationFormTemplateXSectionXQuestion evaluationFormTemplateXSectionXQuestion;
    private ScoreValue scoreValue;
    private String comment;

    private boolean idSet;
    private boolean evaluationFormTemplateSet;
    private boolean scoreValueSet;
    private boolean commentSet;

    // This was made to avoid a cyclic redundance error. On a business level, it's impossible to have no
    // EmployeeEvaluationForm nor EvaluationFormTemplate here.
    public EmployeeEvaluationFormAnswerBuilder(EmployeeEvaluationForm employeeEvaluationForm) {
        this.employeeEvaluationForm = employeeEvaluationForm;
    }

    public EmployeeEvaluationFormAnswerBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EmployeeEvaluationFormAnswerBuilder evaluationFormTemplateXSectionXQuestion(
            EvaluationFormTemplateXSectionXQuestion evaluationFormTemplateXSectionXQuestion) {
        this.evaluationFormTemplateXSectionXQuestion = evaluationFormTemplateXSectionXQuestion;
        this.evaluationFormTemplateSet = true;
        return this;
    }

    public EmployeeEvaluationFormAnswerBuilder scoreValue(ScoreValue scoreValue) {
        this.scoreValue = scoreValue;
        this.scoreValueSet = true;
        return this;
    }

    public EmployeeEvaluationFormAnswerBuilder comment(String comment) {
        this.comment = comment;
        this.commentSet = true;
        return this;
    }

    @Override
    public EmployeeEvaluationFormAnswer build() {
        EmployeeEvaluationFormAnswer obj = new EmployeeEvaluationFormAnswer();
        obj.setId(this.id);
        obj.setEmployeeEvaluationForm(this.employeeEvaluationForm);
        obj.setEvaluationFormTemplateXSectionXQuestion(this.evaluationFormTemplateXSectionXQuestion);
        obj.setScoreValue(this.scoreValue);
        obj.setComment(this.comment);
        return obj;
    }

    @Override
    public EmployeeEvaluationFormAnswer buildWithDefaults() {
        EmployeeEvaluationFormAnswer obj = new EmployeeEvaluationFormAnswer();
        if (this.idSet) obj.setId(this.id);
        obj.setEmployeeEvaluationForm(this.employeeEvaluationForm);
        obj.setEvaluationFormTemplateXSectionXQuestion(this.evaluationFormTemplateSet ?
                this.evaluationFormTemplateXSectionXQuestion :
                new EvaluationFormTemplateXSectionXQuestionBuilder(obj.getEmployeeEvaluationForm()
                        .getEvaluationFormTemplate()).buildWithDefaults());
        if (this.scoreValueSet) obj.setScoreValue(this.scoreValue);
        obj.setComment(this.commentSet ? this.comment : "Test Comment");
        return obj;
    }
}
