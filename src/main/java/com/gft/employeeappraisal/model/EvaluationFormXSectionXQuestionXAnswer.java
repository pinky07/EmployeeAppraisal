package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity persistent class that describes an EvaluationFormXSectionXQuestionXAnswer table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "EvaluationFormXSectionXQuestionXAnswer")
public class EvaluationFormXSectionXQuestionXAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormXSectionXQuestionId", nullable = false)
    private EvaluationFormXSectionXQuestion evaluationFormXSectionXQuestion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appraisalXEvaluationFormXEmployeeRelationshipId", nullable = false)
    private AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship;

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

    public EvaluationFormXSectionXQuestion getEvaluationFormXSectionXQuestion() {
        return evaluationFormXSectionXQuestion;
    }

    public void setEvaluationFormXSectionXQuestion(EvaluationFormXSectionXQuestion evaluationFormXSectionXQuestion) {
        this.evaluationFormXSectionXQuestion = evaluationFormXSectionXQuestion;
    }

    public AppraisalXEvaluationFormXEmployeeRelationship getAppraisalXEvaluationFormXEmployeeRelationship() {
        return appraisalXEvaluationFormXEmployeeRelationship;
    }

    public void setAppraisalXEvaluationFormXEmployeeRelationship(
            AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship) {
        this.appraisalXEvaluationFormXEmployeeRelationship = appraisalXEvaluationFormXEmployeeRelationship;
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

        EvaluationFormXSectionXQuestionXAnswer that = (EvaluationFormXSectionXQuestionXAnswer) o;

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
        return "EvaluationFormXSectionXQuestionXAnswer{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                '}';
    }
}
