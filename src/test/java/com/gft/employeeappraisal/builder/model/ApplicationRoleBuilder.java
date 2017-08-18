package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.ApplicationRole;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link ApplicationRole} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class ApplicationRoleBuilder implements ObjectBuilder<ApplicationRole> {

    private static int currentId = 1_000_000;

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
        ApplicationRole applicationRole = new ApplicationRole();
        applicationRole.setId(this.id);
        applicationRole.setName(this.name);
        applicationRole.setDescription(this.description);
        return applicationRole;
    }

    @Override
    public ApplicationRole buildWithDefaults() {
        ApplicationRole applicationRole = new ApplicationRole();
        applicationRole.setId(this.idSet ? this.id : currentId++);
        applicationRole.setName(this.nameSet ? this.name : "Name");
        applicationRole.setDescription(this.descriptionSet ? this.description : "Description");
        return applicationRole;
    }

    @Override
    public ApplicationRole buildMock() {
        ApplicationRole mock = Mockito.mock(ApplicationRole.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getName()).thenReturn(this.name);
        when(mock.getDescription()).thenReturn(this.description);
        return mock;
    }
}