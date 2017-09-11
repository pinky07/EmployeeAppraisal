package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Entity persistent class that describes a JobLevel table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "JobLevel")
public class JobLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobFamilyId", nullable = false)
    private JobFamily jobFamily;

    @NotEmpty
    @Size(max = 2)
    @Column(name = "name", nullable = false, length = 2)
    private String name;

    @NotEmpty
    @Size(max = 500)
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @NotEmpty
    @Size(max = 20)
    @Column(name = "expertise", nullable = false, length = 20)
    private String expertise;

    @OneToMany(mappedBy = "jobLevel", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<Employee> employeeSet;

    @OneToMany(mappedBy = "jobLevel", fetch = FetchType.LAZY)
    private Set<EvaluationFormTemplateXJobLevel> evaluationFormTemplateXJobLevelSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JobFamily getJobFamily() {
        return jobFamily;
    }

    public void setJobFamily(JobFamily jobFamily) {
        this.jobFamily = jobFamily;
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

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(Set<Employee> employeeSet) {
        this.employeeSet = employeeSet;
    }

    public Set<EvaluationFormTemplateXJobLevel> getEvaluationFormTemplateXJobLevelSet() {
        return evaluationFormTemplateXJobLevelSet;
    }

    public void setEvaluationFormTemplateXJobLevelSet(Set<EvaluationFormTemplateXJobLevel> evaluationFormTemplateXJobLevelSet) {
        this.evaluationFormTemplateXJobLevelSet = evaluationFormTemplateXJobLevelSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobLevel jobLevel = (JobLevel) o;

        return getId() == jobLevel.getId() &&
                (getName() != null ? getName().equals(jobLevel.getName()) : jobLevel.getName() == null) &&
                (getDescription() != null ? getDescription().equals(jobLevel.getDescription()) : jobLevel.getDescription() == null) &&
                (getExpertise() != null ? getExpertise().equals(jobLevel.getExpertise()) : jobLevel.getExpertise() == null);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getExpertise() != null ? getExpertise().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobLevel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", expertise='" + expertise + '\'' +
                '}';
    }
}
