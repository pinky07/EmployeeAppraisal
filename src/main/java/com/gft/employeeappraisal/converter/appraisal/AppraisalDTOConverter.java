package com.gft.employeeappraisal.converter.appraisal;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.swagger.employees.model.AppraisalDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Converter logic for bidirectional transformation between Appraisal and AppraisalDTO.
 *
 * @author Manuel Yepez
 */
public class AppraisalDTOConverter implements EntityDTOConverter<Appraisal, AppraisalDTO> {

	private BoundMapperFacade<Appraisal, AppraisalDTO> boundMapper;

	@Autowired
	public AppraisalDTOConverter(MapperFactory mapperFactory) {
		boundMapper = mapperFactory.getMapperFacade(Appraisal.class, AppraisalDTO.class);
	}

	/**
	 * @see EntityDTOConverter#convert(Object)
	 * @param source {@link Appraisal} Appraisal object to be converted.
	 * @return {@link AppraisalDTO} Transformed AppraisalDTO object.
	 */
	@Override
	public AppraisalDTO convert(Appraisal source) {
		return boundMapper.map(source);
	}

	/**
	 * @see EntityDTOConverter#convertBack(Object)
	 * @param source {@link AppraisalDTO} object to be converted.
	 * @return {@link Appraisal} Transformed object.
	 */
	@Override
	public Appraisal convertBack(AppraisalDTO source) {
		return boundMapper.mapReverse(source);
	}
}
