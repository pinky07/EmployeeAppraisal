package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluationFormQuestion that = (EvaluationFormQuestion) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "EvaluationFormQuestion{" +
                "id=" + id +
                ", scoreType=" + scoreType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
