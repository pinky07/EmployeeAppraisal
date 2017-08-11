package com.gft.employeeappraisal.converter.appraisal;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.swagger.employees.model.AppraisalDTO;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Converter logic for bidirectional transformation between Appraisal and AppraisalDTO.
 *
 * @author Manuel Yepez
 */
public class AppraisalDTOConverter extends BidirectionalConverter<Appraisal, AppraisalDTO> {

	@Override
	public AppraisalDTO convertTo(Appraisal source, Type<AppraisalDTO> destinationType,
								  MappingContext mappingContext) {
		AppraisalDTO result = new AppraisalDTO();
		result.setId(source.getId());
		result.setName(source.getName());
		result.setDescription(source.getDescription());
		result.setStartDate(OffsetDateTime.of(source.getStartDate(), ZoneOffset.UTC));
		if (source.getEndDate() != null) {
			result.setEndDate(OffsetDateTime.of(source.getEndDate(), ZoneOffset.UTC));
		}
		return result;
	}

	@Override
	public Appraisal convertFrom(AppraisalDTO source, Type<Appraisal> destinationType,
								 MappingContext mappingContext) {
		Appraisal result = new Appraisal();
		result.setId(source.getId());
		result.setName(source.getName());
		result.setDescription(source.getDescription());
		result.setStartDate(source.getStartDate().toLocalDateTime());
		if (source.getEndDate() != null) {
			result.setEndDate(source.getEndDate().toLocalDateTime());
		}
		return result;
	}
}
