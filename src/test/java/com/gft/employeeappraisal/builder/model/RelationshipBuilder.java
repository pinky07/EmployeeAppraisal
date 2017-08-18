package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.model.RelationshipName;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link Relationship} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class RelationshipBuilder implements ObjectBuilder<Relationship> {

    private static int currentId = 1_000_000;

    private int id;
    private String name;
    private String description;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;

    public RelationshipBuilder() {
    }

    public RelationshipBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public RelationshipBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public RelationshipBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    @Override
    public Relationship build() {
        Relationship relationship = new Relationship();
        relationship.setId(this.id);
        relationship.setName(this.name);
        relationship.setDescription(this.description);
        return relationship;
    }

    @Override
    public Relationship buildWithDefaults() {
        Relationship relationship = new Relationship();
        relationship.setId(this.idSet ? this.id : currentId++);
        relationship.setName(this.nameSet ? this.name : RelationshipName.PEER.name());
        relationship.setDescription(this.descriptionSet ? this.description : "Description");
        return relationship;
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
