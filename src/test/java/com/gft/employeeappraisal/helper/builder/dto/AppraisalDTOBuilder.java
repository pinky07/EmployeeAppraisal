package com.gft.employeeappraisal.helper.builder.dto;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.swagger.employees.model.AppraisalDTO;

import java.time.OffsetDateTime;

/**
 * Builder object for the {@link AppraisalDTO} object.
 *
 * @author Manuel Yepez
 */
public class AppraisalDTOBuilder implements ObjectBuilder<AppraisalDTO> {

    private int id;
    private String name;
    private String description;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    private boolean idSet;
    private boolean nameSet;
    private boolean startDateSet;
    private boolean endDateSet;
    private boolean descriptionSet;

    public AppraisalDTOBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public AppraisalDTOBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public AppraisalDTOBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    public AppraisalDTOBuilder startDate(OffsetDateTime startDate) {
        this.startDate = startDate;
        this.startDateSet = true;
        return this;
    }

    public AppraisalDTOBuilder endDate(OffsetDateTime endDate) {
        this.endDate = endDate;
        this.endDateSet = true;
        return this;
    }

    @Override
    public AppraisalDTO build() {
        AppraisalDTO dto = new AppraisalDTO();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setDescription(this.description);
        dto.setStartDate(this.startDate);
        dto.setEndDate(this.endDate);
        return dto;
    }

    @Override
    public AppraisalDTO buildWithDefaults() {
        AppraisalDTO dto = new AppraisalDTO();
        if (this.idSet) dto.setId(this.id);
        dto.setName(this.nameSet ? this.name : "Name");
        dto.setDescription(this.descriptionSet ? this.description : "Description");
        dto.setStartDate(this.startDateSet ? this.startDate : OffsetDateTime.now().minusDays(1));
        dto.setEndDate(this.endDateSet ? this.endDate : null);
        return dto;
    }
}
