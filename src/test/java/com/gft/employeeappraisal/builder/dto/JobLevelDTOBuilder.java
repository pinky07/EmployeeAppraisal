package com.gft.employeeappraisal.builder.dto;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.swagger.employees.model.JobFamilyDTO;
import com.gft.swagger.employees.model.JobLevelDTO;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link JobLevelDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class JobLevelDTOBuilder implements ObjectBuilder<JobLevelDTO, Number> {

    private int id;
    private JobFamilyDTO jobFamilyDTO;
    private String name;
    private String description;
    private String expertise;

    public JobLevelDTOBuilder() {
    }

    public JobLevelDTOBuilder id(int id) {
        this.id = id;
        return this;
    }

    public JobLevelDTOBuilder jobFamily(JobFamilyDTO jobFamilyDTO) {
        this.jobFamilyDTO = jobFamilyDTO;
        return this;
    }

    public JobLevelDTOBuilder name(String name) {
        this.name = name;
        return this;
    }

    public JobLevelDTOBuilder description(String description) {
        this.description = description;
        return this;
    }

    public JobLevelDTOBuilder expertise(String expertise) {
        this.expertise = expertise;
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
    public JobLevelDTO buildMock() {
        JobLevelDTO mock = Mockito.mock(JobLevelDTO.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getJobFamily()).thenReturn(this.jobFamilyDTO);
        when(mock.getName()).thenReturn(this.name);
        when(mock.getDescription()).thenReturn(this.description);
        when(mock.getExpertise()).thenReturn(this.expertise);
        return mock;
    }
}
