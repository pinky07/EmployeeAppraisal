package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Entity persistent class that describes an AppraisalXEvaluationFormTemplate table.
 *
 * @author Ruben Jimenez
 */
@Entity
@Table(name = "AppraisalXEvaluationFormTemplate")
public class AppraisalXEvaluationFormTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appraisalId", nullable = false)
    private Appraisal appraisal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormTemplateId", nullable = false)
    private EvaluationFormTemplate evaluationFormTemplate;

    @OneToMany(mappedBy = "appraisalXEvaluationFormTemplate", fetch = FetchType.LAZY)
    private Set<EmployeeEvaluationForm> employeeEvaluationFormSet;

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

    public EvaluationFormTemplate getEvaluationFormTemplate() {
        return evaluationFormTemplate;
    }

    public void setEvaluationFormTemplate(EvaluationFormTemplate evaluationFormTemplate) {
        this.evaluationFormTemplate = evaluationFormTemplate;
    }

    public Set<EmployeeEvaluationForm> getEmployeeEvaluationFormSet() {
        return employeeEvaluationFormSet;
    }

    public void setEmployeeEvaluationFormSet(
            Set<EmployeeEvaluationForm> employeeEvaluationFormSet) {
        this.employeeEvaluationFormSet = employeeEvaluationFormSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppraisalXEvaluationFormTemplate that = (AppraisalXEvaluationFormTemplate) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AppraisalXEvaluationFormTemplate{" +
                "id=" + id +
                '}';
    }
}
