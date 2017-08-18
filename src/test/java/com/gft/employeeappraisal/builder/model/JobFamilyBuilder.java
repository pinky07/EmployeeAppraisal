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

    private static int currentId = 1_000_0000;

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
        JobFamily jobFamily = new JobFamily();
        jobFamily.setId(this.id);
        jobFamily.setName(this.name);
        jobFamily.setDescription(this.description);
        return jobFamily;
    }

    @Override
    public JobFamily buildWithDefaults() {
        JobFamily jobFamily = new JobFamily();
        jobFamily.setId(this.idSet ? this.id : currentId++);
        jobFamily.setName(this.nameSet ? this.name : "Name");
        jobFamily.setDescription(this.descriptionSet ? this.description : "Description");
        return jobFamily;
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
