package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.model.RelationshipName;

/**
 * Builder object for the {@link Relationship} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class RelationshipBuilder implements ObjectBuilder<Relationship> {

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
        Relationship obj = new Relationship();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        return obj;
    }

    @Override
    public Relationship buildWithDefaults() {
        Relationship obj = new Relationship();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : RelationshipName.PEER.name());
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        return obj;
    }
}
