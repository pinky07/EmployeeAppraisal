package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.JobFamily;
import com.gft.employeeappraisal.model.JobLevel;

/**
 * Builder object for the {@link JobLevel} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class JobLevelBuilder implements ObjectBuilder<JobLevel> {

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
        JobLevel obj = new JobLevel();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        obj.setExpertise(this.expertise);
        obj.setJobFamily(this.jobFamily);
        return obj;
    }

    @Override
    public JobLevel buildWithDefaults() {
        JobLevel obj = new JobLevel();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : "LL");
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        obj.setExpertise(this.expertiseSet ? this.expertise : "Expertise");
        obj.setJobFamily(this.jobFamilySet ? this.jobFamily : new JobFamilyBuilder().buildWithDefaults());
        return obj;
    }
}
