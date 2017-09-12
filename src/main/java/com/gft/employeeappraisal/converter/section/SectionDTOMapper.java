package com.gft.employeeappraisal.converter.section;

import com.gft.employeeappraisal.model.Section;
import com.gft.swagger.employees.model.ScoreTypeDTO;
import com.gft.swagger.employees.model.SectionDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;

/**
 * Defines a mapping structure to be used by {@link SectionDTOConverter}.
 *
 * @author Rubén Jiménez
 */
@Component
public class SectionDTOMapper extends CustomMapper<Section, SectionDTO> {

    @Override
    public void mapAtoB(Section section, SectionDTO sectionDTO, MappingContext context) {
        sectionDTO.setId(section.getId());
        sectionDTO.setScoreType(mapperFacade.map(section.getScoreType(), ScoreTypeDTO.class));
        sectionDTO.setName(section.getName());
        sectionDTO.setDescription(section.getDescription());
        sectionDTO.setPosition(section.getPosition());
    }

    @Override
    public void mapBtoA(SectionDTO evaluationFormSectionDTO, Section section, MappingContext context) {
        // TODO Implement this method!
        throw new NotImplementedException();
    }
}
