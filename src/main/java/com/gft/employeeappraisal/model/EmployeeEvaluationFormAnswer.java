package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity persistent class that describes an EmployeeEvaluationFormAnswer table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "EmployeeEvaluationFormAnswer")
public class EmployeeEvaluationFormAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormTemplateXSectionXQuestion", nullable = false)
    private EvaluationFormTemplateXSectionXQuestion evaluationFormTemplateXSectionXQuestion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeEvaluationFormId", nullable = false)
    private EmployeeEvaluationForm employeeEvaluationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scoreValueId")
    private ScoreValue scoreValue;

    @Size(max = 1000)
    @Column(name = "comment", length = 1000)
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EvaluationFormTemplateXSectionXQuestion getEvaluationFormTemplateXSectionXQuestion() {
        return evaluationFormTemplateXSectionXQuestion;
    }

    public void setEvaluationFormTemplateXSectionXQuestion(EvaluationFormTemplateXSectionXQuestion evaluationFormTemplateXSectionXQuestion) {
        this.evaluationFormTemplateXSectionXQuestion = evaluationFormTemplateXSectionXQuestion;
    }

    public EmployeeEvaluationForm getEmployeeEvaluationForm() {
        return employeeEvaluationForm;
    }

    public void setEmployeeEvaluationForm(
            EmployeeEvaluationForm employeeEvaluationForm) {
        this.employeeEvaluationForm = employeeEvaluationForm;
    }

    public ScoreValue getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(ScoreValue scoreValue) {
        this.scoreValue = scoreValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeEvaluationFormAnswer that = (EmployeeEvaluationFormAnswer) o;

        return getId() == that.getId() &&
                (getComment() != null ? getComment().equals(that.getComment()) : that.getComment() == null);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeEvaluationFormAnswer{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                '}';
    }
}
