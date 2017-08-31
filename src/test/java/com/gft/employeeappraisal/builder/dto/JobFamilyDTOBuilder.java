package com.gft.employeeappraisal.builder.dto;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.swagger.employees.model.JobFamilyDTO;
import org.apache.commons.lang.NotImplementedException;

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

    public JobFamilyDTOBuilder() {
    }

    public JobFamilyDTOBuilder id(int id) {
        this.id = id;
        return this;
    }

    public JobFamilyDTOBuilder name(String name) {
        this.name = name;
        return this;
    }

    public JobFamilyDTOBuilder description(String description) {
        this.description = description;
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
        throw new NotImplementedException();
    }
}
