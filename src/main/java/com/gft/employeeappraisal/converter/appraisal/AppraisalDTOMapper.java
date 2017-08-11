package com.gft.employeeappraisal.converter.appraisal;

import com.gft.employeeappraisal.converter.Mapper;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.swagger.employees.model.AppraisalDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper class for processing the conversion between Appraisals and their respective DTO.
 * Using a BoundMapper increases performance according to Orika's authors.
 *
 * @author Manuel Yepez
 */
public class AppraisalDTOMapper implements Mapper<Appraisal, AppraisalDTO> {

	private BoundMapperFacade<Appraisal, AppraisalDTO> boundMapper;

	@Autowired
	public AppraisalDTOMapper(MapperFactory mapperFactory) {
		boundMapper = mapperFactory.getMapperFacade(Appraisal.class, AppraisalDTO.class);
	}

	/**
	 * @see Mapper#map(Object)
	 * @param source {@link Appraisal} Appraisal object to be converted.
	 * @return {@link AppraisalDTO} Transformed AppraisalDTO object.
	 */
	@Override
	public AppraisalDTO map(Appraisal source) {
		return boundMapper.map(source);
	}

	public Appraisal mapReverse(AppraisalDTO source) {
		return boundMapper.mapReverse(source);
	}
}
