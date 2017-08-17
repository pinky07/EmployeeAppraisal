package com.gft.employeeappraisal.converter.appraisal;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.swagger.employees.model.AppraisalDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between Appraisal and AppraisalDTO.
 *
 * @author Manuel Yepez
 */
@Component
public class AppraisalDTOConverter extends EntityDTOConverter<Appraisal, AppraisalDTO> {

    @Autowired
    public AppraisalDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(Appraisal.class, AppraisalDTO.class);
    }
}
