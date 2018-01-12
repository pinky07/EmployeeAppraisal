package com.gft.employeeappraisal.converter.employeeevaluationform;

import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormAnswerService;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.employeeappraisal.service.EvaluationFormTemplateXSectionXQuestionService;
import com.gft.swagger.employees.model.*;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Defines a mapping structure to be used by {@link EmployeeEvaluationFormDTOConverter}.
 *
 * @author Rubén Jiménez
 */
@Component
public class EmployeeEvaluationFormDTOMapper extends CustomMapper<EmployeeEvaluationForm, EmployeeEvaluationFormDTO> {

    private final EmployeeService employeeService;

    private final EmployeeEvaluationFormService employeeEvaluationFormService;

    private final EmployeeEvaluationFormAnswerService employeeEvaluationFormAnswerService;
    private final EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService;

    //    private final SectionDTOConverter sectionDTOConverter;
//    private final ScoreTypeDTOConverter scoreTypeDTOConverter;
    @Autowired
    public EmployeeEvaluationFormDTOMapper(EmployeeService employeeService,
                                           EmployeeEvaluationFormService employeeEvaluationFormService,
                                           EmployeeEvaluationFormAnswerService employeeEvaluationFormAnswerService,
                                           EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService
    ) {
        this.employeeService = employeeService;
        this.employeeEvaluationFormService = employeeEvaluationFormService;
        this.employeeEvaluationFormAnswerService = employeeEvaluationFormAnswerService;
        this.evaluationFormTemplateXSectionXQuestionService = evaluationFormTemplateXSectionXQuestionService;
//        this.sectionDTOConverter=sectionDTOConverter;
//        this.scoreTypeDTOConverter=scoreTypeDTOConverter;

    }

    @Override
    public void mapAtoB(EmployeeEvaluationForm employeeEvaluationForm, EmployeeEvaluationFormDTO employeeEvaluationFormDTO, MappingContext context) {
        employeeEvaluationFormDTO.setId(employeeEvaluationForm.getId());
        employeeEvaluationFormDTO.setEmployee(mapperFacade.map(employeeEvaluationForm.getEmployee(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setFilledByEmployee(mapperFacade.map(employeeEvaluationForm.getFilledByEmployee(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setMentor(mapperFacade.map(employeeEvaluationForm.getMentor(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setCreateDate(employeeEvaluationForm.getCreateDate());
        employeeEvaluationFormDTO.setSubmitDate(employeeEvaluationForm.getSubmitDate());
        employeeEvaluationFormDTO.setComments(employeeEvaluationForm.getComments());
        employeeEvaluationFormDTO.setEvaluationFormId(employeeEvaluationForm.getEvaluationFormTemplate().getId());

        employeeEvaluationFormDTO.setAnswers(employeeEvaluationForm.getEmployeeEvaluationFormAnswerSet().stream()
                .map(employeeEvaluationFormAnswer -> {
                    AnswerDTO answerDTO = mapperFacade.map(employeeEvaluationFormAnswer, AnswerDTO.class);
                    answerDTO.setScoreValue(mapperFacade.map(employeeEvaluationFormAnswer.getScoreValue(), ScoreValueDTO.class));
                    answerDTO.setComment(employeeEvaluationFormAnswer.getComment());
                    EvaluationFormTemplateXSectionXQuestion xSectionXQuestion = employeeEvaluationFormAnswer.getEvaluationFormTemplateXSectionXQuestion();
                    Question question = xSectionXQuestion.getQuestion();

                    EvaluationFormTemplateXSectionXQuestionDTO xSectionXQuestionDTO = new EvaluationFormTemplateXSectionXQuestionDTO();
                    xSectionXQuestionDTO.setId(xSectionXQuestion.getId());
                    ScoreType scoreType = employeeEvaluationFormAnswerService.getById(answerDTO.getId()).getScoreValue().getScoreType();

                    ScoreValueDTO scoreValueDTO = new ScoreValueDTO();
                    ScoreTypeDTO scoreTypeDTO = new ScoreTypeDTO();
                    List<ScoreTypeDTO> scoreTypeDTOS = new ArrayList<>();
                    SectionDTO sectionDTO = new SectionDTO();
                    SectionDescriptionDTO sectionDescriptionDTO = new SectionDescriptionDTO();


                    ScoreValue scoreValue = employeeEvaluationFormAnswerService.getById(answerDTO.getId()).getScoreValue();

                    scoreValueDTO.setId(scoreValue.getId());
                    scoreValueDTO.setValue(scoreValue.getValue());
                    scoreValueDTO.setDescription(scoreValue.getDescription());

                    Set<Section> sectionSet = scoreType.getSectionSet();
//                    scoreTypeDTOConverter.convertBack(scoreTypeDTO);
                    List<SectionDescriptionDTO> sectionDescriptionDTOList = new ArrayList<SectionDescriptionDTO>();
                    //if scoretype id is one then need to iterate section
                    if (scoreType.getId() == 1) {
                        Set<Section> sectionSet1 = scoreType.getSectionSet();
                        for (Section section : sectionSet1) {
                            sectionDTO.setId(section.getId());
                            sectionDTO.setDescription(section.getDescription());
                            sectionDTO.setName(section.getName());
//                            sectionDTOConverter.convertBack(sectionDTO);//scoretyp convertback need to call here
//                            scoreTypeDTOConverter.convertBack(scoreTypeDTO);
                            sectionDescriptionDTO.setId(section.getId());
                            sectionDescriptionDTO.setName(section.getName());
                            sectionDescriptionDTO.setDescription(section.getDescription());
                            sectionDescriptionDTOList.add(sectionDescriptionDTO);
//                            scoreTypeDTO.addSectionDescriptionItem(sectionDescriptionDTO);

                        }

//                        scoreTypeDTO.setSectionDescription(sectionDescriptionDTOList);

                    }

                    scoreTypeDTO.setId(scoreType.getId());
                    scoreTypeDTO.setDefinition(scoreType.getDefinition());
                    scoreTypeDTOS.add(scoreTypeDTO);
                    scoreValueDTO.setScoreType(scoreTypeDTOS);
                    QuestionDTO questionDTO = new QuestionDTO();
                    questionDTO.setId(question.getId());
                    questionDTO.setName(question.getName());
                    questionDTO.setDescription(question.getDescription());
                    questionDTO.setPosition(question.getPosition());
                    xSectionXQuestionDTO.addQuestionItem(questionDTO);
                    answerDTO.setEmployeeEvaluationFormId(employeeEvaluationForm.getId());
                    answerDTO.setScoreValue(scoreValueDTO);
                    answerDTO.setEvaluationFormTemplateXSectionXQuestionId(xSectionXQuestionDTO.getId());

                    // Omitted for brevity, every answer doesn't need this information
                    /*answerDTO.setEvaluationFormTemplateXSectionXQuestion(mapperFacade.map(employeeEvaluationFormAnswer
                            .getEvaluationFormTemplateXSectionXQuestion(), EvaluationFormTemplateXSectionXQuestionDTO.class));*/
                    return answerDTO;
                }).collect(Collectors.toList()));
    }

    @Override
    public void mapBtoA(EmployeeEvaluationFormDTO employeeEvaluationFormDTO, EmployeeEvaluationForm employeeEvaluationForm, MappingContext context) {
        employeeEvaluationForm.setId(employeeEvaluationFormDTO.getId());
        employeeEvaluationForm.setEmployee(employeeService.getById(employeeEvaluationFormDTO.getEmployee().getId()));
        employeeEvaluationForm
                .setFilledByEmployee(employeeService.getById(employeeEvaluationFormDTO.getFilledByEmployee().getId()));
        employeeEvaluationForm.setMentor(employeeService.getById(employeeEvaluationFormDTO.getMentor().getId()));
        employeeEvaluationForm.setCreateDate(employeeEvaluationFormDTO.getCreateDate());
        employeeEvaluationForm.setSubmitDate(employeeEvaluationFormDTO.getSubmitDate());
        employeeEvaluationForm.setComments(employeeEvaluationFormDTO.getComments());
        EmployeeEvaluationForm evaluationForm = employeeEvaluationFormService.getById(employeeEvaluationFormDTO.getId());
        employeeEvaluationForm.setEvaluationFormTemplate(evaluationForm.getEvaluationFormTemplate());

        Set<EmployeeEvaluationFormAnswer> answerSet = new HashSet<>();

        List<AnswerDTO> answerDTOS = employeeEvaluationFormDTO.getAnswers();

        for (AnswerDTO answerDTO : answerDTOS) {
            EmployeeEvaluationFormAnswer answer = new EmployeeEvaluationFormAnswer();
            answer.setId(answerDTO.getId());
            answer.setEvaluationFormTemplateXSectionXQuestion(evaluationFormTemplateXSectionXQuestionService
                    .getById(answerDTO.getEvaluationFormTemplateXSectionXQuestionId()));
            answer.setComment(answerDTO.getComment());
            answer.setEmployeeEvaluationForm(employeeEvaluationForm);
            answer.setScoreValue(mapperFacade.map(answerDTO.getScoreValue(), ScoreValue.class));
            answerSet.add(answer);
        }
        employeeEvaluationForm.setEmployeeEvaluationFormAnswerSet(answerSet);
    }
}
