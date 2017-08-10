package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.JobFamily;
import com.gft.employeeappraisal.model.JobLevel;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link JobLevel} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class JobLevelBuilder implements ObjectBuilder<JobLevel, Number> {

    private int id;
    private JobFamily jobFamily;
    private String name;
    private String description;
    private String expertise;

    public JobLevelBuilder() {
    }

    public JobLevelBuilder id(int id) {
        this.id = id;
        return this;
    }

    public JobLevelBuilder name(String name) {
        this.name = name;
        return this;
    }

    public JobLevelBuilder description(String description) {
        this.description = description;
        return this;
    }

    public JobLevelBuilder expertise(String expertise) {
        this.expertise = expertise;
        return this;
    }

    public JobLevelBuilder jobFamily(JobFamily jobFamily) {
        this.jobFamily = jobFamily;
        return this;
    }

    @Override
    public JobLevel build() {
        JobLevel jobLevel = new JobLevel();
        jobLevel.setId(this.id);
        jobLevel.setName(this.name);
        jobLevel.setDescription(this.description);
        jobLevel.setExpertise(this.expertise);
        jobLevel.setJobFamily(this.jobFamily);
        return jobLevel;
    }

    @Override
    public JobLevel buildMock() {
        JobLevel mock = Mockito.mock(JobLevel.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getName()).thenReturn(this.name);
        when(mock.getDescription()).thenReturn(this.description);
        when(mock.getExpertise()).thenReturn(this.expertise);
        when(mock.getJobFamily()).thenReturn(this.jobFamily);
        return mock;
    }
}
