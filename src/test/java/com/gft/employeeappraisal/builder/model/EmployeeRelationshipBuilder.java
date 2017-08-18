package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.Relationship;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link EmployeeRelationship} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class EmployeeRelationshipBuilder implements ObjectBuilder<EmployeeRelationship> {

    private static int currentId = 1_000_000;

    private int id;
    private Employee sourceEmployee;
    private Employee targetEmployee;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Relationship relationship;

    private boolean idSet;
    private boolean sourceEmployeeSet;
    private boolean targetEmployeeSet;
    private boolean startDateSet;
    private boolean endDateSet;
    private boolean relationshipSet;

    public EmployeeRelationshipBuilder() {
    }

    public EmployeeRelationshipBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EmployeeRelationshipBuilder sourceEmployee(Employee sourceEmployee) {
        this.sourceEmployee = sourceEmployee;
        this.sourceEmployeeSet = true;
        return this;
    }

    public EmployeeRelationshipBuilder targetEmployee(Employee targetEmployee) {
        this.targetEmployee = targetEmployee;
        this.targetEmployeeSet = true;
        return this;
    }

    public EmployeeRelationshipBuilder startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        this.startDateSet = true;
        return this;
    }

    public EmployeeRelationshipBuilder endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        this.endDateSet = true;
        return this;
    }

    public EmployeeRelationshipBuilder relationship(Relationship relationship) {
        this.relationship = relationship;
        this.relationshipSet = true;
        return this;
    }

    @Override
    public EmployeeRelationship build() {
        EmployeeRelationship obj = new EmployeeRelationship();
        obj.setId(this.id);
        obj.setSourceEmployee(this.sourceEmployee);
        obj.setTargetEmployee(this.targetEmployee);
        obj.setStartDate(this.startDate);
        obj.setEndDate(this.endDate);
        obj.setRelationship(this.relationship);
        return obj;
    }

    @Override
    public EmployeeRelationship buildWithDefaults() {
        EmployeeRelationship obj = new EmployeeRelationship();
        obj.setId(this.idSet ? this.id : currentId++);
        obj.setSourceEmployee(this.sourceEmployeeSet ? this.sourceEmployee : new EmployeeBuilder().buildWithDefaults());
        obj.setTargetEmployee(this.targetEmployeeSet ? this.targetEmployee : new EmployeeBuilder().buildWithDefaults());
        obj.setStartDate(this.startDateSet ? this.startDate : LocalDateTime.now().minusDays(1));
        obj.setEndDate(this.endDateSet ? this.endDate : LocalDateTime.now().plusDays(1));
        obj.setRelationship(this.relationshipSet ? this.relationship : new RelationshipBuilder().buildWithDefaults());
        return obj;
    }

    @Override
    public EmployeeRelationship buildMock() {
        EmployeeRelationship mock = Mockito.mock(EmployeeRelationship.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getSourceEmployee()).thenReturn(this.sourceEmployee);
        when(mock.getTargetEmployee()).thenReturn(this.targetEmployee);
        when(mock.getStartDate()).thenReturn(this.startDate);
        when(mock.getEndDate()).thenReturn(this.endDate);
        when(mock.getRelationship()).thenReturn(this.relationship);
        return mock;
    }
}
