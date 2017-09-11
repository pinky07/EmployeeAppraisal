package com.gft.employeeappraisal.converter.evaluationformtemplate;

import com.gft.employeeappraisal.model.EvaluationFormTemplate;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import com.gft.employeeappraisal.model.Question;
import com.gft.employeeappraisal.model.Section;
import com.gft.swagger.employees.model.EvaluationFormTemplateDTO;
import com.gft.swagger.employees.model.QuestionDTO;
import com.gft.swagger.employees.model.SectionDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Defines a mapping structure to be used by {@link EvaluationFormTemplateDTOConverter}.
 *
 * @author Rubén Jiménez
 */
@Component
public class EvaluationFormTemplateDTOMapper extends CustomMapper<EvaluationFormTemplate, EvaluationFormTemplateDTO> {


    @Override
    public void mapAtoB(EvaluationFormTemplate evaluationFormTemplate, EvaluationFormTemplateDTO evaluationFormTemplateDTO, MappingContext context) {

        // Id, name, description
        evaluationFormTemplateDTO.setId(evaluationFormTemplate.getId());
        evaluationFormTemplateDTO.setName(evaluationFormTemplate.getName());
        evaluationFormTemplateDTO.setDescription(evaluationFormTemplate.getDescription());

        // Get questions by section
        Map<Section, List<Question>> questionsBySection = evaluationFormTemplate.getEvaluationFormXSectionXQuestionSet()
                .stream()
                .collect(Collectors.toMap(
                        EvaluationFormTemplateXSectionXQuestion::getSection,
                        evaluationFormXSectionXQuestion -> {
                            List<Question> question = new ArrayList<>();
                            question.add(evaluationFormXSectionXQuestion.getQuestion());
                            return question;
                        },
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }));

        // Map every section and question to a DTO
        evaluationFormTemplateDTO.setSections(questionsBySection.keySet()
                .stream()
                .map(evaluationFormSection -> {
                    SectionDTO evaluationFormSectionDTO = mapperFacade.map(evaluationFormSection, SectionDTO.class);
                    evaluationFormSectionDTO.setQuestions(questionsBySection.get(evaluationFormSection)
                            .stream()
                            .map(evaluationFormQuestion -> mapperFacade.map(evaluationFormQuestion, QuestionDTO.class))
                            .collect(Collectors.toList()));
                    return evaluationFormSectionDTO;
                })
                .collect(Collectors.toList()));
    }

    @Override
    public void mapBtoA(EvaluationFormTemplateDTO evaluationFormDTO, EvaluationFormTemplate evaluationFormTemplate, MappingContext context) {
        // TODO Implement this method!
        // This conversion might not be trivial since every Evaluation Form will have many sections and questions.
        // We might need to implement an special logic to handle this scenarios. But it will be better to first encounter
        // one of such scenarios to decide how to implement this method.
        throw new NotImplementedException();
    }
}
