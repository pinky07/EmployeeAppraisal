package com.gft.employeeappraisal.builder.dto;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.swagger.employees.model.AppraisalDTO;
import org.apache.commons.lang.NotImplementedException;

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

	public AppraisalDTOBuilder(){ }

	public AppraisalDTOBuilder id(int id) {
		this.id = id;
		return this;
	}

	public AppraisalDTOBuilder name(String name) {
		this.name = name;
		return this;
	}

	public AppraisalDTOBuilder description(String description) {
		this.description = description;
		return this;
	}

	public AppraisalDTOBuilder startDate(OffsetDateTime startDate) {
		this.startDate = startDate;
		return this;
	}

	public AppraisalDTOBuilder endDate(OffsetDateTime endDate) {
		this.endDate = endDate;
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
		throw new NotImplementedException();
	}
}
