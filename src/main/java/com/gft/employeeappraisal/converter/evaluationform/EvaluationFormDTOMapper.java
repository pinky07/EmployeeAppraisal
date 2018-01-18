package com.gft.employeeappraisal.converter.evaluationform;

import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EmployeeEvaluationFormAnswer;
import com.gft.employeeappraisal.model.EvaluationFormTemplateXSectionXQuestion;
import com.gft.employeeappraisal.model.ScoreValue;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormAnswerService;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import com.gft.employeeappraisal.service.ScoreValueService;
import com.gft.swagger.employees.model.*;
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

    private ScoreValueService scoreValueService;

    @Autowired
    public EvaluationFormDTOMapper(
            ScoreValueService scoreValueService
    ){
        this.scoreValueService = scoreValueService;
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
            questionAndAnswer.setId(templateSectionQuestion.getId());
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
        // sort sections by position
        List<FormSectionDTO> sectionDTOArrayList = new ArrayList<>(sectionsMap.values());
        sectionDTOArrayList.sort(Comparator.comparing(FormSectionDTO::getPosition));

        // sort questions
        sectionDTOArrayList.forEach(section -> section.getQuestions()
                .sort(Comparator.comparing(QuestionAndAnswerDTO::getPosition)));

        evaluationFormDTO.setSections(sectionDTOArrayList);
    }

    @Override
    public void mapBtoA(EvaluationFormDTO evaluationFormDTO, EmployeeEvaluationForm employeeEvaluationForm, MappingContext context) {
        // Check received questions and answers and update accordingly
        Supplier<Stream<EvaluationFormTemplateXSectionXQuestion>> templateSectionsQuestionsSupplier = () -> employeeEvaluationForm.getEvaluationFormTemplate().getEvaluationFormXSectionXQuestionSet().stream();
        Supplier<Stream<EmployeeEvaluationFormAnswer>> formAnswersSupplier = () -> employeeEvaluationForm.getEmployeeEvaluationFormAnswerSet().stream();
        // Iterate the sections and questions
        for(FormSectionDTO formSection : evaluationFormDTO.getSections()){
            for(QuestionAndAnswerDTO questionAndAnswer : formSection.getQuestions()){
                if(null != questionAndAnswer.getAnswerId()) {
                    EmployeeEvaluationFormAnswer formAnswer;
                    // Check is answer already exist in DB
                    List<EmployeeEvaluationFormAnswer> currentAnswers = formAnswersSupplier.get()
                            .filter(answer -> answer.getEvaluationFormTemplateXSectionXQuestion().getId() == questionAndAnswer.getId())
                            .limit(1)
                            .collect(Collectors.toList());
                    if (!currentAnswers.isEmpty()) {
                        // An answer already exists, check if coming answer is different, if so, update it, otherwise nothing
                        formAnswer = currentAnswers.get(0);
                        if (questionAndAnswer.getAnswerId() != formAnswer.getScoreValue().getId()) {
                            ScoreValue newScoreValue = this.scoreValueService.getById(questionAndAnswer.getAnswerId());
                            formAnswer.setScoreValue(newScoreValue);
                        }
                        if (!questionAndAnswer.getAnswerComment().equals(formAnswer.getComment())) {
                            formAnswer.setComment(questionAndAnswer.getAnswerComment());
                        }
                    } else {
                        // No answer for question, add new answer
                        formAnswer = new EmployeeEvaluationFormAnswer();
                        // Set the EvaluationForm
                        formAnswer.setEmployeeEvaluationForm(employeeEvaluationForm);
                        // Set the proper templateXSectionXQuestion
                        List<EvaluationFormTemplateXSectionXQuestion> templateXSectionXQuestions = templateSectionsQuestionsSupplier.get()
                                .filter(templateSectionQuestion -> templateSectionQuestion.getId() == questionAndAnswer.getId())
                                .limit(1)
                                .collect(Collectors.toList());
                        if (!templateXSectionXQuestions.isEmpty()) {
                            formAnswer.setEvaluationFormTemplateXSectionXQuestion(templateXSectionXQuestions.get(0));
                        }
                        // Set the proper ScoreValue
                        ScoreValue scoreValue = this.scoreValueService.getById(questionAndAnswer.getAnswerId());
                        formAnswer.setScoreValue(scoreValue);
                        // Set the comment
                        formAnswer.setComment(questionAndAnswer.getAnswerComment());
                    }
                    // Adds the answer to the set of answers
                    employeeEvaluationForm.addEmployeeEvaluationFormAnswer(formAnswer);
                }
            }
        }
    }
}
