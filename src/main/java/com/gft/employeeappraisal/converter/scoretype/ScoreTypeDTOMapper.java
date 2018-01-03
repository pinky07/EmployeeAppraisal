package com.gft.employeeappraisal.converter.scoretype;

import com.gft.employeeappraisal.model.ScoreType;
import com.gft.swagger.employees.model.ScoreTypeDTO;
import com.gft.swagger.employees.model.ScoreValueDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Defines a mapping structure to be used by {@link ScoreTypeDTOConverter}.
 *
 * @author Rubén Jiménez
 */
@Component
public class ScoreTypeDTOMapper extends CustomMapper<ScoreType, ScoreTypeDTO> {

    @Override
    public void mapAtoB(ScoreType scoreType, ScoreTypeDTO scoreTypeDTO, MappingContext context) {
        scoreTypeDTO.setId(scoreType.getId());
        scoreTypeDTO.setDefinition(scoreType.getDefinition());
        scoreTypeDTO.setScoreValues(scoreType.getScoreValueSet()
                .stream()
                .map(scoreValue -> mapperFacade.map(scoreValue, ScoreValueDTO.class))
                .collect(Collectors.toList()));
    }

    @Override
    public void mapBtoA(ScoreTypeDTO scoreTypeDTO, ScoreType scoreType, MappingContext context) {
        // TODO Implement this method!
        scoreType.setId(scoreTypeDTO.getId());
        scoreType.setDefinition(scoreTypeDTO.getDefinition());
      //  scoreType.setSectionSet(scoreTypeDTO.getScoreValues());
        throw new NotImplementedException();
    }
}
