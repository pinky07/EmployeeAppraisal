package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.model.RelationshipType;

/**
 * Builder object for the {@link RelationshipType} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class RelationshipTypeBuilder implements ObjectBuilder<RelationshipType> {

    private int id;
    private String name;
    private String description;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;

    public RelationshipTypeBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public RelationshipTypeBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public RelationshipTypeBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    @Override
    public RelationshipType build() {
        RelationshipType obj = new RelationshipType();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        return obj;
    }

    @Override
    public RelationshipType buildWithDefaults() {
        RelationshipType obj = new RelationshipType();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : RelationshipName.PEER.name());
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        return obj;
    }
}
