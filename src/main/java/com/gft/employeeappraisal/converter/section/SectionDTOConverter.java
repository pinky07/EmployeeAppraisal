package com.gft.employeeappraisal.converter.section;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.Section;
import com.gft.swagger.employees.model.SectionDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between {@link Section} and {@link SectionDTO}.
 *
 * @author Rubén Jiménez
 */
@Component
public class SectionDTOConverter extends EntityDTOConverter<Section, SectionDTO> {

    @Autowired
    public SectionDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(Section.class, SectionDTO.class);
    }
}
