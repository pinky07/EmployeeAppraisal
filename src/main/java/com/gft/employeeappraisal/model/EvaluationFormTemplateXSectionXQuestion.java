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
@Table(name = "EvaluationFormTemplateXSectionXQuestion")
public class EvaluationFormTemplateXSectionXQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormTemplateId", nullable = false)
    private EvaluationFormTemplate evaluationFormTemplate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sectionId", nullable = false)
    private Section section;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

    @OneToMany(mappedBy = "evaluationFormTemplateXSectionXQuestion", fetch = FetchType.LAZY)
    private Set<EmployeeEvaluationFormAnswer> employeeEvaluationFormAnswerSet;

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

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<EmployeeEvaluationFormAnswer> getEmployeeEvaluationFormAnswerSet() {
        return employeeEvaluationFormAnswerSet;
    }

    public void setEmployeeEvaluationFormAnswerSet(
            Set<EmployeeEvaluationFormAnswer> employeeEvaluationFormAnswerSet) {
        this.employeeEvaluationFormAnswerSet = employeeEvaluationFormAnswerSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluationFormTemplateXSectionXQuestion that = (EvaluationFormTemplateXSectionXQuestion) o;
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
