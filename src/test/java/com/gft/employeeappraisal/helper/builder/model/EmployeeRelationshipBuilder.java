package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.Relationship;

import java.time.OffsetDateTime;

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
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
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

    public EmployeeRelationshipBuilder startDate(OffsetDateTime startDate) {
        this.startDate = startDate;
        this.startDateSet = true;
        return this;
    }

    public EmployeeRelationshipBuilder endDate(OffsetDateTime endDate) {
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
        if (this.idSet) obj.setId(this.id);
        obj.setSourceEmployee(this.sourceEmployeeSet ? this.sourceEmployee : new EmployeeBuilder().buildWithDefaults());
        obj.setTargetEmployee(this.targetEmployeeSet ? this.targetEmployee : new EmployeeBuilder().buildWithDefaults());
        obj.setStartDate(this.startDateSet ? this.startDate : OffsetDateTime.now().minusDays(1));
        obj.setEndDate(this.endDateSet ? this.endDate : null);
        obj.setRelationship(this.relationshipSet ? this.relationship : new RelationshipBuilder().buildWithDefaults());
        return obj;
    }
}
