package com.gft.employeeappraisal.converter.evaluationform;

import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EmployeeEvaluationFormAnswer;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import com.gft.swagger.employees.model.EvaluationFormDTO;
import com.gft.swagger.employees.model.FormSectionDTO;
import com.gft.swagger.employees.model.QuestionAndAnswerDTO;
import com.gft.swagger.employees.model.ScoreTypeDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class EvaluationFormDTOMapper extends CustomMapper<EmployeeEvaluationForm, EvaluationFormDTO>{

    @Autowired
    public EvaluationFormDTOMapper(){

    }

    @Override
    public void mapAtoB(EmployeeEvaluationForm employeeEvaluationForm, EvaluationFormDTO evaluationFormDTO, MappingContext context) {
        evaluationFormDTO.setId(employeeEvaluationForm.getId());
        evaluationFormDTO.setDescription(employeeEvaluationForm.getEvaluationFormTemplate().getDescription());
        evaluationFormDTO.setName(employeeEvaluationForm.getEvaluationFormTemplate().getName());
        evaluationFormDTO.setEmployeeName(employeeEvaluationForm.getEmployee().getFirstName().concat(" ").concat(employeeEvaluationForm.getEmployee().getLastName()));
        evaluationFormDTO.setMentorName(employeeEvaluationForm.getMentor().getFirstName().concat(" ").concat(employeeEvaluationForm.getMentor().getLastName()));

        // Fill in the sections
        Map<String, FormSectionDTO> sectionsMap = new HashMap<>();
        Set<EvaluationFormTemplateXSectionXQuestion> templateSectionsQuestions = employeeEvaluationForm.getEvaluationFormTemplate().getEvaluationFormXSectionXQuestionSet();
        Supplier<Stream<EmployeeEvaluationFormAnswer>> formAnswersSupplier = () -> employeeEvaluationForm.getEmployeeEvaluationFormAnswerSet().stream();
        for(EvaluationFormTemplateXSectionXQuestion templateSectionQuestion : templateSectionsQuestions){
            FormSectionDTO formSection;
            // Determine if current section exist already, if not, create a new one
            String sectionMapKey = "Section-"+templateSectionQuestion.getSection().getId();
            if(sectionsMap.containsKey(sectionMapKey)){
                formSection = sectionsMap.get(sectionMapKey);
            } else{
                formSection = new FormSectionDTO();
                formSection.setId(templateSectionQuestion.getSection().getId());
                formSection.setName(templateSectionQuestion.getSection().getName());
                formSection.setDescription(templateSectionQuestion.getSection().getDescription());
                formSection.setPosition(templateSectionQuestion.getSection().getPosition());
                formSection.setScoreType(mapperFacade.map(templateSectionQuestion.getSection().getScoreType(), ScoreTypeDTO.class));
            }
            // Now fill in the questions
            QuestionAndAnswerDTO questionAndAnswer = new QuestionAndAnswerDTO();
            questionAndAnswer.setId(templateSectionQuestion.getQuestion().getId());
            questionAndAnswer.setName(templateSectionQuestion.getQuestion().getName());
            questionAndAnswer.setDescription(templateSectionQuestion.getQuestion().getDescription());
            questionAndAnswer.setPosition(templateSectionQuestion.getQuestion().getPosition());
            // Now add the answer information, if any
            List<EmployeeEvaluationFormAnswer> questionAnswers = formAnswersSupplier.get()
                    .filter(formAnswer -> formAnswer.getEvaluationFormTemplateXSectionXQuestion().equals(templateSectionQuestion))
                    .limit(1)
                    .collect(Collectors.toList());
            if(!questionAnswers.isEmpty()){
                EmployeeEvaluationFormAnswer questionAnswer = questionAnswers.get(0);
                questionAndAnswer.setAnswerId(questionAnswer.getScoreValue().getId());
                questionAndAnswer.setAnswerScoreValue(questionAnswer.getScoreValue().getValue());
                questionAndAnswer.setAnswerComment(questionAnswer.getComment());
            }
            formSection.addQuestionsItem(questionAndAnswer);
            sectionsMap.put(sectionMapKey, formSection);
        }
        evaluationFormDTO.setSections(new ArrayList<>(sectionsMap.values()));
    }

    @Override
    public void mapBtoA(EvaluationFormDTO evaluationFormDTO, EmployeeEvaluationForm employeeEvaluationForm, MappingContext context) {
        super.mapBtoA(evaluationFormDTO, employeeEvaluationForm, context);
    }
}
