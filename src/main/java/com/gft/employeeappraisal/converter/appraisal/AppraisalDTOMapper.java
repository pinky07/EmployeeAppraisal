package com.gft.employeeappraisal.converter.appraisal;

import com.gft.employeeappraisal.converter.Mapper;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.swagger.employees.model.AppraisalDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class AppraisalDTOMapper implements Mapper<Appraisal, AppraisalDTO> {

	private BoundMapperFacade<Appraisal, AppraisalDTO> boundMapper;

	@Autowired
	public AppraisalDTOMapper(MapperFactory mapperFactory) {
		boundMapper = mapperFactory.getMapperFacade(Appraisal.class, AppraisalDTO.class);
	}

	@Override
	public AppraisalDTO map(Appraisal source) {
		return boundMapper.map(source);
	}

	public Appraisal mapReverse(AppraisalDTO source) {
		return boundMapper.mapReverse(source);
	}
}
