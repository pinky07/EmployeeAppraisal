package com.gft.employeeappraisal.converter.appraisal;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.swagger.employees.model.AppraisalDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Defines a mapping structure to be used by {@link AppraisalDTOConverter}.
 *
 * @author Manuel Yepez
 */
@Component
public class AppraisalDTOMapper extends CustomMapper<Appraisal, AppraisalDTO> {

	@Override
	public void mapAtoB(Appraisal appraisal, AppraisalDTO appraisalDTO, MappingContext context) {
		appraisalDTO.setId(appraisal.getId());
		appraisalDTO.setName(appraisal.getName());
		appraisalDTO.setDescription(appraisal.getDescription());
		appraisalDTO.setStartDate(OffsetDateTime.of(appraisal.getStartDate(), ZoneOffset.UTC));
		if (appraisal.getEndDate() != null) {
			appraisalDTO.setEndDate(OffsetDateTime.of(appraisal.getEndDate(), ZoneOffset.UTC));
		}
	}

	@Override
	public void mapBtoA(AppraisalDTO appraisalDTO, Appraisal appraisal, MappingContext context) {
		if (appraisalDTO.getId() > 0) {
			appraisal.setId(appraisalDTO.getId());
		}
		appraisal.setName(appraisalDTO.getName());
		appraisal.setDescription(appraisalDTO.getDescription());
		appraisal.setStartDate(appraisalDTO.getStartDate().toLocalDateTime());
		if (appraisalDTO.getEndDate() != null) {
			appraisal.setEndDate(appraisalDTO.getEndDate().toLocalDateTime());
		}
	}
}
