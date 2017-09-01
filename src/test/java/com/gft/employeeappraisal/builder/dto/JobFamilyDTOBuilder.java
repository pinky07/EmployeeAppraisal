package com.gft.employeeappraisal.builder.dto;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.swagger.employees.model.JobFamilyDTO;

/**
 * Builder object for the {@link JobFamilyDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class JobFamilyDTOBuilder implements ObjectBuilder<JobFamilyDTO> {

    private int id;
    private String name;
    private String description;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;

    public JobFamilyDTOBuilder() {
    }

    public JobFamilyDTOBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public JobFamilyDTOBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public JobFamilyDTOBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    @Override
    public JobFamilyDTO build() {
        JobFamilyDTO dto = new JobFamilyDTO();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.description(this.description);
        return dto;
    }

    @Override
    public JobFamilyDTO buildWithDefaults() {
        JobFamilyDTO dto = new JobFamilyDTO();
        if (this.idSet) dto.setId(this.id);
        dto.setName(this.nameSet ? this.name : "Name");
        dto.setDescription(this.descriptionSet ? this.description : "Description");
        return dto;
    }
}
