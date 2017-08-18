package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Entity persistent class that describes an EvaluationFormQuestion table.
 *
 * @author Ruben Jimenez
 */
@Entity
@Table(name = "EvaluationFormQuestion")
public class EvaluationFormQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scoreTypeId", nullable = false)
    private ScoreType scoreType;

    @NotEmpty
    @Size(max = 40)
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @NotEmpty
    @Size(max = 500)
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @OneToMany(mappedBy = "evaluationFormQuestion", fetch = FetchType.LAZY)
    private Set<EvaluationFormXSectionXQuestion> evaluationFormXSectionXQuestions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ScoreType getScoreType() {
        return scoreType;
    }

    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
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

    public Set<EvaluationFormXSectionXQuestion> getEvaluationFormXSectionXQuestions() {
        return evaluationFormXSectionXQuestions;
    }

    public void setEvaluationFormXSectionXQuestions(Set<EvaluationFormXSectionXQuestion> evaluationFormXSectionXQuestions) {
        this.evaluationFormXSectionXQuestions = evaluationFormXSectionXQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluationFormQuestion that = (EvaluationFormQuestion) o;

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
        return "EvaluationFormQuestion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
