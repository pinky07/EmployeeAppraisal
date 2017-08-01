package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    private LocalDateTime startDate;

    @Column(name = "endDate", columnDefinition = "TIMESTAMP")
    private LocalDateTime endDate;

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

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }
}
