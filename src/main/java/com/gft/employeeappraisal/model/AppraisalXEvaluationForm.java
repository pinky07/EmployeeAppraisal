package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Entity persistent class that describes an AppraisalXEvaluationForm table.
 *
 * @author Ruben Jimenez
 */
@Entity
@Table(name = "AppraisalXEvaluationForm")
public class AppraisalXEvaluationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appraisalId", nullable = false)
    private Appraisal appraisal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormId", nullable = false)
    private EvaluationForm evaluationForm;

    @OneToMany(mappedBy = "appraisalXEvaluationForm", fetch = FetchType.LAZY)
    private Set<AppraisalXEvaluationFormXEmployeeRelationship> appraisalXEvaluationFormXEmployeeRelationships;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Appraisal getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(Appraisal appraisal) {
        this.appraisal = appraisal;
    }

    public EvaluationForm getEvaluationForm() {
        return evaluationForm;
    }

    public void setEvaluationForm(EvaluationForm evaluationForm) {
        this.evaluationForm = evaluationForm;
    }

    public Set<AppraisalXEvaluationFormXEmployeeRelationship> getAppraisalXEvaluationFormXEmployeeRelationships() {
        return appraisalXEvaluationFormXEmployeeRelationships;
    }

    public void setAppraisalXEvaluationFormXEmployeeRelationships(
            Set<AppraisalXEvaluationFormXEmployeeRelationship> appraisalXEvaluationFormXEmployeeRelationships) {
        this.appraisalXEvaluationFormXEmployeeRelationships = appraisalXEvaluationFormXEmployeeRelationships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppraisalXEvaluationForm that = (AppraisalXEvaluationForm) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AppraisalXEvaluationForm{" +
                "id=" + id +
                '}';
    }
}
