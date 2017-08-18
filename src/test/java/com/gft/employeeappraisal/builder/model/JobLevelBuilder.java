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
public class JobLevelBuilder implements ObjectBuilder<JobLevel> {

    private static int currentId = 1_000_000;

    private int id;
    private JobFamily jobFamily;
    private String name;
    private String description;
    private String expertise;

    private boolean idSet;
    private boolean jobFamilySet;
    private boolean nameSet;
    private boolean descriptionSet;
    private boolean expertiseSet;

    public JobLevelBuilder() {
    }

    public JobLevelBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public JobLevelBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public JobLevelBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    public JobLevelBuilder expertise(String expertise) {
        this.expertise = expertise;
        this.expertiseSet = true;
        return this;
    }

    public JobLevelBuilder jobFamily(JobFamily jobFamily) {
        this.jobFamily = jobFamily;
        this.jobFamilySet = true;
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
    public JobLevel buildWithDefaults() {
        JobLevel jobLevel = new JobLevel();
        jobLevel.setId(this.idSet ? this.id : currentId++);
        jobLevel.setName(this.nameSet ? this.name : "Name");
        jobLevel.setDescription(this.descriptionSet ? this.description : "Description");
        jobLevel.setExpertise(this.expertiseSet ? this.expertise : "Expertise");
        jobLevel.setJobFamily(this.jobFamilySet ? this.jobFamily : new JobFamilyBuilder().buildWithDefaults());
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
