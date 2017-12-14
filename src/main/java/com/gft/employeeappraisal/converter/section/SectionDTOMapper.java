package com.gft.employeeappraisal.converter.section;

import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import com.gft.employeeappraisal.model.JobLevel;
import com.gft.employeeappraisal.model.Section;
import com.gft.employeeappraisal.service.EvaluationFormTemplateXSectionXQuestionService;
import com.gft.employeeappraisal.service.ScoreTypeService;
import com.gft.swagger.employees.model.ScoreTypeDTO;
import com.gft.swagger.employees.model.SectionDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Defines a mapping structure to be used by {@link SectionDTOConverter}.
 *
 * @author Rubén Jiménez
 */
@Component
public class SectionDTOMapper extends CustomMapper<Section, SectionDTO> {

    private static ScoreTypeService scoreTypeService;
    private static EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService;
    @Autowired
    public SectionDTOMapper(ScoreTypeService scoreTypeService,EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService){
        this.scoreTypeService=scoreTypeService;
        evaluationFormTemplateXSectionXQuestionService =evaluationFormTemplateXSectionXQuestionService;
    }
    @Override
    public void mapAtoB(Section section, SectionDTO sectionDTO, MappingContext context) {
        sectionDTO.setId(section.getId());
        sectionDTO.setScoreType(mapperFacade.map(section.getScoreType(), ScoreTypeDTO.class));
        sectionDTO.setName(section.getName());
        sectionDTO.setDescription(section.getDescription());
        sectionDTO.setPosition(section.getPosition());
    }

    @Override
    public void mapBtoA(SectionDTO sectionDTO, Section section, MappingContext context) {
        // TODO Implement this method!
        // section.setEvaluationFormXSectionXQuestionSet(mapperFacade.map(evaluationFormSectionDTO.ge););
        section.setId(sectionDTO.getId());
       section.setPosition(sectionDTO.getPosition());
       section.setDescription(sectionDTO.getDescription());
       section.setName(sectionDTO.getName());
       section.setPosition(sectionDTO.getPosition());
      // section.setScoreType(scoreTypeService.getById(sectionDTO.getId()));
      // section.setEvaluationFormXSectionXQuestionSet(evaluationFormTemplateXSectionXQuestionService.getById(sectionDTO.getId()).);
//        throw new NotImplementedException();
    }
}
