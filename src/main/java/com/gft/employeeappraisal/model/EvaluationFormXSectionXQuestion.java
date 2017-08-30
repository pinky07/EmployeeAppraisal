package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Entity persistent class that describes an EvaluationFormXSectionXQuestion table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "EvaluationFormXSectionXQuestion")
public class EvaluationFormXSectionXQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormId", nullable = false)
    private EvaluationForm evaluationForm;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormSectionId", nullable = false)
    private EvaluationFormSection evaluationFormSection;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormQuestionId", nullable = false)
    private EvaluationFormQuestion evaluationFormQuestion;

    @OneToMany(mappedBy = "evaluationFormXSectionXQuestion", fetch = FetchType.LAZY)
    private Set<EvaluationFormXSectionXQuestionXAnswer> evaluationFormXSectionXQuestionXAnswers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EvaluationForm getEvaluationForm() {
        return evaluationForm;
    }

    public void setEvaluationForm(EvaluationForm evaluationForm) {
        this.evaluationForm = evaluationForm;
    }

    public EvaluationFormSection getEvaluationFormSection() {
        return evaluationFormSection;
    }

    public void setEvaluationFormSection(EvaluationFormSection evaluationFormSection) {
        this.evaluationFormSection = evaluationFormSection;
    }

    public EvaluationFormQuestion getEvaluationFormQuestion() {
        return evaluationFormQuestion;
    }

    public void setEvaluationFormQuestion(EvaluationFormQuestion evaluationFormQuestion) {
        this.evaluationFormQuestion = evaluationFormQuestion;
    }

    public Set<EvaluationFormXSectionXQuestionXAnswer> getEvaluationFormXSectionXQuestionXAnswers() {
        return evaluationFormXSectionXQuestionXAnswers;
    }

    public void setEvaluationFormXSectionXQuestionXAnswers(
            Set<EvaluationFormXSectionXQuestionXAnswer> evaluationFormXSectionXQuestionXAnswers) {
        this.evaluationFormXSectionXQuestionXAnswers = evaluationFormXSectionXQuestionXAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluationFormXSectionXQuestion that = (EvaluationFormXSectionXQuestion) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return "EvaluationFormXSectionXQuestion{" +
                "id=" + id +
                '}';
    }
}
