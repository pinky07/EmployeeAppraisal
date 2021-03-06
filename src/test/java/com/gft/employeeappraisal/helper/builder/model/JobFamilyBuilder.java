package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.JobFamily;

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
}
