package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity persistent class that describes an Appraisal table.
 *
 * @author Ruben Jimenez
 */
@Entity
@Table(name = "Appraisal")
public class Appraisal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;

    @NotEmpty
    @Size(max = 40)
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @NotEmpty
    @Size(max = 500)
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @NotNull
    @Column(name = "startDate", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "endDate", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "appraisal", fetch = FetchType.LAZY)
    private Set<AppraisalXEvaluationForm> appraisalXEvaluationForms;

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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Set<AppraisalXEvaluationForm> getAppraisalXEvaluationForms() {
        return appraisalXEvaluationForms;
    }

    public void setAppraisalXEvaluationForms(Set<AppraisalXEvaluationForm> appraisalXEvaluationForms) {
        this.appraisalXEvaluationForms = appraisalXEvaluationForms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appraisal appraisal = (Appraisal) o;

        return getId() == appraisal.getId() &&
                getName().equals(appraisal.getName()) &&
                getDescription().equals(appraisal.getDescription()) &&
                getStartDate().equals(appraisal.getStartDate()) &&
                getEndDate().equals(appraisal.getEndDate());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getEndDate().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Appraisal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
