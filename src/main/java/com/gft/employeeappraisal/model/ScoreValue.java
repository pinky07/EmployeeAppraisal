package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private ScoreType sourceEmployee;

    @NotEmpty
    @Size(max = 40)
    @Column(name = "value", nullable = false, length = 40)
    private String value;

    @NotEmpty
    @Size(max = 500)
    @Column(name = "description", nullable = false, length = 40)
    private String description;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ScoreType getSourceEmployee() {
        return sourceEmployee;
    }

    public void setSourceEmployee(ScoreType sourceEmployee) {
        this.sourceEmployee = sourceEmployee;
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
                ", sourceEmployee=" + sourceEmployee +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
