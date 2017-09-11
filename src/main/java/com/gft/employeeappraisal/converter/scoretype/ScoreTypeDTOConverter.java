package com.gft.employeeappraisal.converter.scoretype;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.ScoreType;
import com.gft.swagger.employees.model.ScoreTypeDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between {@link ScoreType} and {@link ScoreTypeDTO}.
 *
 * @author Rubén Jiménez
 */
@Component
public class ScoreTypeDTOConverter extends EntityDTOConverter<ScoreType, ScoreTypeDTO> {

    @Autowired
    public ScoreTypeDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(ScoreType.class, ScoreTypeDTO.class);
    }
}
