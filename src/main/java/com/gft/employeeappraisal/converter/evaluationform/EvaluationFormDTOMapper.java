package com.gft.employeeappraisal.converter.evaluationform;

import com.gft.employeeappraisal.model.EvaluationForm;
import com.gft.employeeappraisal.model.EvaluationFormQuestion;
import com.gft.employeeappraisal.model.EvaluationFormSection;
import com.gft.employeeappraisal.model.EvaluationFormXSectionXQuestion;
import com.gft.swagger.employees.model.EvaluationFormDTO;
import com.gft.swagger.employees.model.EvaluationFormQuestionDTO;
import com.gft.swagger.employees.model.EvaluationFormSectionDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Defines a mapping structure to be used by {@link EvaluationFormDTOConverter}.
 *
 * @author Rubén Jiménez
 */
@Component
public class EvaluationFormDTOMapper extends CustomMapper<EvaluationForm, EvaluationFormDTO> {


    @Override
    public void mapAtoB(EvaluationForm evaluationForm, EvaluationFormDTO evaluationFormDTO, MappingContext context) {

        // Id, name, description
        evaluationFormDTO.setId(evaluationForm.getId());
        evaluationFormDTO.setName(evaluationForm.getName());
        evaluationFormDTO.setDescription(evaluationForm.getDescription());

        // Get questions by section
        Map<EvaluationFormSection, List<EvaluationFormQuestion>> questionsBySection = evaluationForm.getEvaluationFormXSectionXQuestions()
                .stream()
                .collect(Collectors.toMap(
                        EvaluationFormXSectionXQuestion::getEvaluationFormSection,
                        evaluationFormXSectionXQuestion -> {
                            List<EvaluationFormQuestion> question = new ArrayList<>();
                            question.add(evaluationFormXSectionXQuestion.getEvaluationFormQuestion());
                            return question;
                        },
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }));

        // Map every section and question to a DTO
        evaluationFormDTO.setSections(questionsBySection.keySet()
                .stream()
                .map(evaluationFormSection -> {
                    EvaluationFormSectionDTO evaluationFormSectionDTO = mapperFacade.map(evaluationFormSection, EvaluationFormSectionDTO.class);
                    evaluationFormSectionDTO.setQuestions(questionsBySection.get(evaluationFormSection)
                            .stream()
                            .map(evaluationFormQuestion -> mapperFacade.map(evaluationFormQuestion, EvaluationFormQuestionDTO.class))
                            .collect(Collectors.toList()));
                    return evaluationFormSectionDTO;
                })
                .collect(Collectors.toList()));
    }

    @Override
    public void mapBtoA(EvaluationFormDTO evaluationFormDTO, EvaluationForm evaluationForm, MappingContext context) {
        // TODO Implement this method!
        // This conversion might not be trivial since every Evaluation Form will have many sections and questions.
        // We might need to implement an special logic to handle this scenarios. But it will be better to first encounter
        // one of such scenarios to decide how to implement this method.
        throw new NotImplementedException();
    }
}
