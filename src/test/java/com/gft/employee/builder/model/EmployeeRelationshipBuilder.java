package com.gft.employee.builder.model;

import com.gft.employee.builder.ObjectBuilder;
import com.gft.employee.model.Employee;
import com.gft.employee.model.EmployeeRelationship;
import com.gft.employee.model.Relationship;
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

    private int id;
    private Employee sourceEmployee;
    private Employee targetEmployee;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Relationship relationship;

    public EmployeeRelationshipBuilder() {
    }

    public EmployeeRelationshipBuilder id(int id) {
        this.id = id;
        return this;
    }

    public EmployeeRelationshipBuilder sourceEmployee(Employee sourceEmployee) {
        this.sourceEmployee = sourceEmployee;
        return this;
    }

    public EmployeeRelationshipBuilder targetEmployee(Employee targetEmployee) {
        this.targetEmployee = targetEmployee;
        return this;
    }

    public EmployeeRelationshipBuilder startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public EmployeeRelationshipBuilder endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public EmployeeRelationshipBuilder relationship(Relationship relationship) {
        this.relationship = relationship;
        return this;
    }

    @Override
    public EmployeeRelationship build() {
        EmployeeRelationship employeeRelationship = new EmployeeRelationship();
        employeeRelationship.setId(this.id);
        employeeRelationship.setSourceEmployee(this.sourceEmployee);
        employeeRelationship.setTargetEmployee(this.targetEmployee);
        employeeRelationship.setStartDate(this.startDate);
        employeeRelationship.setEndDate(this.endDate);
        employeeRelationship.setRelationship(this.relationship);
        return employeeRelationship;
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
