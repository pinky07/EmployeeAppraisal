package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Entity persistent class that describes an AppraisalXEvaluationFormXEmployeeRelationship table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "AppraisalXEvaluationFormXEmployeeRelationship")
public class AppraisalXEvaluationFormXEmployeeRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appraisalXEvaluationFormId", nullable = false)
    private AppraisalXEvaluationForm appraisalXEvaluationForm;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeRelationshipId", nullable = false)
    private EmployeeRelationship employeeRelationship;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    @OneToMany(mappedBy = "appraisalXEvaluationFormXEmployeeRelationship", fetch = FetchType.LAZY)
    private Set<EvaluationFormXSectionXQuestionXAnswer> evaluationFormXSectionXQuestionXAnswers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppraisalXEvaluationForm getAppraisalXEvaluationForm() {
        return appraisalXEvaluationForm;
    }

    public void setAppraisalXEvaluationForm(AppraisalXEvaluationForm appraisalXEvaluationForm) {
        this.appraisalXEvaluationForm = appraisalXEvaluationForm;
    }

    public EmployeeRelationship getEmployeeRelationship() {
        return employeeRelationship;
    }

    public void setEmployeeRelationship(EmployeeRelationship employeeRelationship) {
        this.employeeRelationship = employeeRelationship;
    }

    public EvaluationStatus getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(EvaluationStatus evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
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

        AppraisalXEvaluationFormXEmployeeRelationship that = (AppraisalXEvaluationFormXEmployeeRelationship) o;

        return getId() == that.getId() &&
                getEvaluationStatus() == that.getEvaluationStatus();
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getEvaluationStatus().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AppraisalXEvaluationFormXEmployeeRelationship{" +
                "id=" + id +
                ", evaluationStatus=" + evaluationStatus +
                '}';
    }
}
