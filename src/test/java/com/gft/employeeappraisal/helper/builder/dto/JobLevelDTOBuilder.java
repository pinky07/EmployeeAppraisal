package com.gft.employeeappraisal.helper.builder.dto;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.swagger.employees.model.JobFamilyDTO;
import com.gft.swagger.employees.model.JobLevelDTO;

/**
 * Builder object for the {@link JobLevelDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class JobLevelDTOBuilder implements ObjectBuilder<JobLevelDTO> {

    private int id;
    private JobFamilyDTO jobFamilyDTO;
    private String name;
    private String description;
    private String expertise;

    private boolean idSet;
    private boolean jobFamilySet;
    private boolean nameSet;
    private boolean descriptionSet;
    private boolean expertiseSet;

    public JobLevelDTOBuilder() {
    }

    public JobLevelDTOBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public JobLevelDTOBuilder jobFamily(JobFamilyDTO jobFamilyDTO) {
        this.jobFamilyDTO = jobFamilyDTO;
        this.jobFamilySet = true;
        return this;
    }

    public JobLevelDTOBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public JobLevelDTOBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    public JobLevelDTOBuilder expertise(String expertise) {
        this.expertise = expertise;
        this.expertiseSet = true;
        return this;
    }

    @Override
    public JobLevelDTO build() {
        JobLevelDTO dto = new JobLevelDTO();
        dto.setId(this.id);
        dto.setJobFamily(this.jobFamilyDTO);
        dto.setName(this.name);
        dto.setDescription(this.description);
        dto.setExpertise(this.expertise);
        return dto;
    }

    @Override
    public JobLevelDTO buildWithDefaults() {
        JobLevelDTO dto = new JobLevelDTO();
        if (this.idSet) dto.setId(this.id);
        dto.setName(this.nameSet ? this.name : "LL");
        dto.setDescription(this.descriptionSet ? this.description : "Description");
        dto.setExpertise(this.expertiseSet ? this.expertise : "Expertise");
        dto.setJobFamily(this.jobFamilySet ? this.jobFamilyDTO : new JobFamilyDTOBuilder().buildWithDefaults());
        return dto;
    }
}
