package com.gft.employeeappraisal.converter.scorevalue;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.ScoreValue;
import com.gft.swagger.employees.model.ScoreValueDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter init class for bidirectional transformation between {@link ScoreValue} and {@link ScoreValueDTO}
 *
 * @author Rubén Jiménez
 */
@Component
public class ScoreValueDTOConverter extends EntityDTOConverter<ScoreValue, ScoreValueDTO> {

    @Autowired
    public ScoreValueDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(ScoreValue.class, ScoreValueDTO.class);
    }
}
