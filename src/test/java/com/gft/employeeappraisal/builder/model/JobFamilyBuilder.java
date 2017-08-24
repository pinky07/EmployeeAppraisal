package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.JobFamily;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link JobFamily} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class JobFamilyBuilder implements ObjectBuilder<JobFamily> {

    private int id;
    private String name;
    private String description;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;

    public JobFamilyBuilder() {
    }

    public JobFamilyBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public JobFamilyBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public JobFamilyBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    @Override
    public JobFamily build() {
        JobFamily obj = new JobFamily();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        return obj;
    }

    @Override
    public JobFamily buildWithDefaults() {
        JobFamily obj = new JobFamily();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : "Name");
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        return obj;
    }

    @Override
    public JobFamily buildMock() {
        JobFamily mock = Mockito.mock(JobFamily.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getName()).thenReturn(this.name);
        when(mock.getDescription()).thenReturn(this.description);
        return mock;
    }
}
