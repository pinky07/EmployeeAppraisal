package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Entity persistent class that describes an EmployeeRelationship table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "EmployeeRelationship")
public class EmployeeRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sourceEmployeeId", nullable = false)
    private Employee sourceEmployee;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetEmployeeId", nullable = false)
    private Employee targetEmployee;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "relationshipId", nullable = false)
    private Relationship relationship;

    @NotNull
    @Column(name = "startDate", columnDefinition = "TIMESTAMP", nullable = false)
    private OffsetDateTime startDate;

    @Column(name = "endDate", columnDefinition = "TIMESTAMP")
    private OffsetDateTime endDate;

    @OneToMany(mappedBy = "employeeRelationship", fetch = FetchType.LAZY)
    private Set<AppraisalXEvaluationFormXEmployeeRelationship> appraisalXEvaluationFormXEmployeeRelationships;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getSourceEmployee() {
        return sourceEmployee;
    }

    public void setSourceEmployee(Employee sourceEmployee) {
        this.sourceEmployee = sourceEmployee;
    }

    public Employee getTargetEmployee() {
        return targetEmployee;
    }

    public void setTargetEmployee(Employee targetEmployee) {
        this.targetEmployee = targetEmployee;
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

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public Set<AppraisalXEvaluationFormXEmployeeRelationship> getAppraisalXEvaluationFormXEmployeeRelationships() {
        return appraisalXEvaluationFormXEmployeeRelationships;
    }

    public void setAppraisalXEvaluationFormXEmployeeRelationships(
            Set<AppraisalXEvaluationFormXEmployeeRelationship> appraisalXEvaluationFormXEmployeeRelationships) {
        this.appraisalXEvaluationFormXEmployeeRelationships = appraisalXEvaluationFormXEmployeeRelationships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeRelationship that = (EmployeeRelationship) o;

        return getId() == that.getId() &&
                getStartDate().equals(that.getStartDate()) &&
                getEndDate().equals(that.getEndDate());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getEndDate().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeRelationship{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
