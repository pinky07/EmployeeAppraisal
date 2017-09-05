package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Entity persistent class that describes an ScoreValue table.
 *
 * @author Ruben Jimenez
 */
@Entity
@Table(name = "ScoreValue")
public class ScoreValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scoreTypeId", nullable = false)
    private ScoreType scoreType;

    @NotEmpty
    @Size(max = 40)
    @Column(name = "value", nullable = false, length = 40)
    private String value;

    @NotEmpty
    @Size(max = 500)
    @Column(name = "description", nullable = false, length = 40)
    private String description;

    @OneToMany(mappedBy = "scoreValue", fetch = FetchType.LAZY)
    private Set<EmployeeEvaluationFormAnswer> employeeEvaluationFormAnswers;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<EmployeeEvaluationFormAnswer> getEmployeeEvaluationFormAnswers() {
        return employeeEvaluationFormAnswers;
    }

    public void setEmployeeEvaluationFormAnswers(Set<EmployeeEvaluationFormAnswer> employeeEvaluationFormAnswers) {
        this.employeeEvaluationFormAnswers = employeeEvaluationFormAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreValue that = (ScoreValue) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "ScoreValue{" +
                "id=" + id +
                ", scoreType=" + scoreType +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
