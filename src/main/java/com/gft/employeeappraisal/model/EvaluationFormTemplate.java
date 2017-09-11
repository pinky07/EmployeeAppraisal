package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Entity persistent class that describes an EvaluationFormTemplate table.
 *
 * @author Ruben Jimenez
 */
@Entity
@Table(name = "EvaluationFormTemplate")
public class EvaluationFormTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotEmpty
    @Size(max = 500)
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @OneToMany(mappedBy = "evaluationFormTemplate", fetch = FetchType.LAZY)
    private Set<AppraisalXEvaluationFormTemplate> appraisalXEvaluationFormTemplateSet;

    @OneToMany(mappedBy = "evaluationFormTemplate", fetch = FetchType.LAZY)
    private Set<EvaluationFormTemplateXJobLevel> evaluationFormTemplateXJobLevelSet;

    @OneToMany(mappedBy = "evaluationFormTemplate", fetch = FetchType.LAZY)
    private Set<EvaluationFormTemplateXSectionXQuestion> evaluationFormXSectionXQuestionSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AppraisalXEvaluationFormTemplate> getAppraisalXEvaluationFormTemplateSet() {
        return appraisalXEvaluationFormTemplateSet;
    }

    public void setAppraisalXEvaluationFormTemplateSet(Set<AppraisalXEvaluationFormTemplate> appraisalXEvaluationFormTemplateSet) {
        this.appraisalXEvaluationFormTemplateSet = appraisalXEvaluationFormTemplateSet;
    }

    public Set<EvaluationFormTemplateXJobLevel> getEvaluationFormTemplateXJobLevelSet() {
        return evaluationFormTemplateXJobLevelSet;
    }

    public void setEvaluationFormTemplateXJobLevelSet(Set<EvaluationFormTemplateXJobLevel> evaluationFormTemplateXJobLevelSet) {
        this.evaluationFormTemplateXJobLevelSet = evaluationFormTemplateXJobLevelSet;
    }

    public Set<EvaluationFormTemplateXSectionXQuestion> getEvaluationFormXSectionXQuestionSet() {
        return evaluationFormXSectionXQuestionSet;
    }

    public void setEvaluationFormXSectionXQuestionSet(Set<EvaluationFormTemplateXSectionXQuestion> evaluationFormXSectionXQuestionSet) {
        this.evaluationFormXSectionXQuestionSet = evaluationFormXSectionXQuestionSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluationFormTemplate that = (EvaluationFormTemplate) o;

        return getId() == that.getId() &&
                getName().equals(that.getName()) &&
                getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "EvaluationFormTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
