package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity persistent class that describes an EvaluationFormXJobLevel table.
 *
 * @author Ruben Jimenez
 */
@Entity
@Table(name = "EvaluationFormXJobLevel")
public class EvaluationFormXJobLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormId", nullable = false)
    private EvaluationForm evaluationForm;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobLevelId", nullable = false)
    private JobLevel jobLevel;

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

    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluationFormXJobLevel that = (EvaluationFormXJobLevel) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "EvaluationFormXJobLevel{" +
                "id=" + id +
                ", evaluationForm=" + evaluationForm +
                ", jobLevel=" + jobLevel +
                '}';
    }
}
