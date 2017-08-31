package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.ApplicationRole;

/**
 * Builder object for the {@link ApplicationRole} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class ApplicationRoleBuilder implements ObjectBuilder<ApplicationRole> {

    private int id;
    private String name;
    private String description;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;

    public ApplicationRoleBuilder() {
    }

    public ApplicationRoleBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public ApplicationRoleBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public ApplicationRoleBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    @Override
    public ApplicationRole build() {
        ApplicationRole obj = new ApplicationRole();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        return obj;
    }

    @Override
    public ApplicationRole buildWithDefaults() {
        ApplicationRole obj = new ApplicationRole();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : "Name");
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        return obj;
    }
}