package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity persistent class that describes an EvaluationFormTemplateXJobLevel table.
 *
 * @author Ruben Jimenez
 */
@Entity
@Table(name = "EvaluationFormTemplateXJobLevel")
public class EvaluationFormTemplateXJobLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormTemplateId", nullable = false)
    private EvaluationFormTemplate evaluationFormTemplate;

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

    public EvaluationFormTemplate getEvaluationFormTemplate() {
        return evaluationFormTemplate;
    }

    public void setEvaluationFormTemplate(EvaluationFormTemplate evaluationFormTemplate) {
        this.evaluationFormTemplate = evaluationFormTemplate;
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

        EvaluationFormTemplateXJobLevel that = (EvaluationFormTemplateXJobLevel) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "EvaluationFormTemplateXJobLevel{" +
                "id=" + id +
                '}';
    }
}
