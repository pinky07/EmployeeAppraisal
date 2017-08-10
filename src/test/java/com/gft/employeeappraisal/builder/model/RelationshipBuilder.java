package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Relationship;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link Relationship} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class RelationshipBuilder implements ObjectBuilder<Relationship, Number> {

    private int id;
    private String name;
    private String description;

    public RelationshipBuilder() {
    }

    public RelationshipBuilder id(int id) {
        this.id = id;
        return this;
    }

    public RelationshipBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RelationshipBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Relationship build() {
        Relationship object = new Relationship();
        object.setId(this.id);
        object.setName(this.name);
        object.setDescription(this.description);
        return object;
    }

    @Override
    public Relationship buildMock() {
        Relationship mock = new Relationship();
        when(mock.getId()).thenReturn(this.id);
        when(mock.getName()).thenReturn(this.name);
        when(mock.getDescription()).thenReturn(this.description);
        return mock;
    }
}
