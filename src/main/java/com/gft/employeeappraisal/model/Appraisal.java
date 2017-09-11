package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
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
    private OffsetDateTime startDate;

    @NotNull
    @Column(name = "endDate", columnDefinition = "TIMESTAMP", nullable = false)
    private OffsetDateTime endDate;

    @OneToMany(mappedBy = "appraisal", fetch = FetchType.LAZY)
    private Set<AppraisalXEvaluationFormTemplate> appraisalXEvaluationFormTemplateSet;

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

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public Set<AppraisalXEvaluationFormTemplate> getAppraisalXEvaluationFormTemplateSet() {
        return appraisalXEvaluationFormTemplateSet;
    }

    public void setAppraisalXEvaluationFormTemplateSet(Set<AppraisalXEvaluationFormTemplate> appraisalXEvaluationFormTemplateSet) {
        this.appraisalXEvaluationFormTemplateSet = appraisalXEvaluationFormTemplateSet;
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
