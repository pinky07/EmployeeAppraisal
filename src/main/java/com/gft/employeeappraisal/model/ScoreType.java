package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreType scoreType = (ScoreType) o;

        return id == scoreType.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "ScoreType{" +
                "id=" + id +
                ", definition='" + definition + '\'' +
                '}';
    }
}
