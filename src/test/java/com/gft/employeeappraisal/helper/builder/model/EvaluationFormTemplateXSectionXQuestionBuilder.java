package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import com.gft.employeeappraisal.model.Question;
import com.gft.employeeappraisal.model.Section;

/**
 * Builder object for the {@link EvaluationFormTemplateXSectionXQuestion} object.
 *
 * @author Manuel Yepez
 */
public class EvaluationFormTemplateXSectionXQuestionBuilder implements
        ObjectBuilder<EvaluationFormTemplateXSectionXQuestion> {

    private int id;
    private EvaluationFormTemplate evaluationFormTemplate;
    private Section section;
    private Question question;

    private boolean idSet;
    private boolean sectionSet;
    private boolean questionSet;

    // This was made to avoid a cyclic redundance error. On a business level, it's impossible to have no
    // EvaluationFormTemplate here.
    public EvaluationFormTemplateXSectionXQuestionBuilder(EvaluationFormTemplate evaluationFormTemplate) {
        this.evaluationFormTemplate = evaluationFormTemplate;
    }

    public EvaluationFormTemplateXSectionXQuestionBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EvaluationFormTemplateXSectionXQuestionBuilder
        evaluationFormTemplate(EvaluationFormTemplate evaluationFormTemplate) {
        this.evaluationFormTemplate = evaluationFormTemplate;
        return this;
    }

    public EvaluationFormTemplateXSectionXQuestionBuilder section(Section section) {
        this.section = section;
        this.sectionSet = true;
        return this;
    }

    public EvaluationFormTemplateXSectionXQuestionBuilder question(Question question) {
        this.question = question;
        this.questionSet = true;
        return this;
    }

    @Override
    public EvaluationFormTemplateXSectionXQuestion build() {
        EvaluationFormTemplateXSectionXQuestion obj = new EvaluationFormTemplateXSectionXQuestion();
        obj.setId(this.id);
        obj.setEvaluationFormTemplate(this.evaluationFormTemplate);
        obj.setQuestion(this.question);
        obj.setSection(this.section);
        return obj;
    }

    @Override
    public EvaluationFormTemplateXSectionXQuestion buildWithDefaults() {
        EvaluationFormTemplateXSectionXQuestion obj = new EvaluationFormTemplateXSectionXQuestion();
        if (this.idSet) obj.setId(this.id);
        obj.setEvaluationFormTemplate(this.evaluationFormTemplate);
        obj.setSection(this.sectionSet ? this.section : new SectionBuilder().buildWithDefaults());
        obj.setQuestion(this.questionSet ? this.question : new QuestionBuilder().buildWithDefaults());
        return obj;
    }
}
