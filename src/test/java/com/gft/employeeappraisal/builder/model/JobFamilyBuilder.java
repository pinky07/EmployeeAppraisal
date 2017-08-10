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

    public JobFamilyBuilder() {
    }

    public JobFamilyBuilder id(int id) {
        this.id = id;
        return this;
    }

    public JobFamilyBuilder name(String name) {
        this.name = name;
        return this;
    }

    public JobFamilyBuilder description(String description) {
        this.description = description;
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
    public JobFamily buildMock() {
        JobFamily mock = Mockito.mock(JobFamily.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getName()).thenReturn(this.name);
        when(mock.getDescription()).thenReturn(this.description);
        return mock;
    }
}
