package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Entity persistent class that describes an ScoreType table.
 *
 * @author Ruben Jimenez
 */
@Entity
@Table(name = "ScoreType")
public class ScoreType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(max = 40)
    @Column(name = "definition", nullable = false, length = 40)
    private String definition;

    @OneToMany(mappedBy = "scoreType", fetch = FetchType.LAZY)
    private Set<ScoreValue> scoreValueSet;

    @OneToMany(mappedBy = "scoreType", fetch = FetchType.LAZY)
    private Set<Section> sectionSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Set<ScoreValue> getScoreValueSet() {
        return scoreValueSet;
    }

    public void setScoreValueSet(Set<ScoreValue> scoreValueSet) {
        this.scoreValueSet = scoreValueSet;
    }

    public Set<Section> getSectionSet() {
        return sectionSet;
    }

    public void setSectionSet(Set<Section> sectionSet) {
        this.sectionSet = sectionSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreType scoreType = (ScoreType) o;

        return getId() == scoreType.getId() &&
                getDefinition().equals(scoreType.getDefinition());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getDefinition().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ScoreType{" +
                "id=" + id +
                ", definition='" + definition + '\'' +
                '}';
    }
}
