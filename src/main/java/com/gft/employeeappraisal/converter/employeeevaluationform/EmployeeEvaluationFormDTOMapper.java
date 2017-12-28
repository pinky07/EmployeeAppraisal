package com.gft.employeeappraisal.converter.employeeevaluationform;

import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.service.*;
import com.gft.swagger.employees.model.*;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Defines a mapping structure to be used by {@link EmployeeEvaluationFormDTOConverter}.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@Component
public class EmployeeEvaluationFormDTOMapper extends CustomMapper<EmployeeEvaluationForm, EmployeeEvaluationFormDTO>
{

    private final EmployeeService employeeService;

    private final EmployeeEvaluationFormService employeeEvaluationFormService;

    private final QuestionService questionService;
    private final EmployeeEvaluationFormAnswerService employeeEvaluationFormAnswerService;
    private final EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService;
    private final ScoreValueService scoreValueService;

    @Autowired
    public EmployeeEvaluationFormDTOMapper(EmployeeService employeeService,
            EmployeeEvaluationFormService employeeEvaluationFormService, QuestionService questionService,
            EmployeeEvaluationFormAnswerService employeeEvaluationFormAnswerService,
            EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService,
            ScoreValueService scoreValueService)
    {
        this.employeeService = employeeService;
        this.employeeEvaluationFormService = employeeEvaluationFormService;
        this.questionService = questionService;
        this.employeeEvaluationFormAnswerService =employeeEvaluationFormAnswerService;
        this.evaluationFormTemplateXSectionXQuestionService =evaluationFormTemplateXSectionXQuestionService;
        this.scoreValueService =scoreValueService;

    }

    @Override
    public void mapAtoB(EmployeeEvaluationForm employeeEvaluationForm, EmployeeEvaluationFormDTO employeeEvaluationFormDTO, MappingContext context)
    {
        employeeEvaluationFormDTO.setId(employeeEvaluationForm.getId());
        employeeEvaluationFormDTO.setEmployee(mapperFacade.map(employeeEvaluationForm.getEmployee(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setFilledByEmployee(mapperFacade.map(employeeEvaluationForm.getFilledByEmployee(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setMentor(mapperFacade.map(employeeEvaluationForm.getMentor(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setCreateDate(employeeEvaluationForm.getCreateDate());
        employeeEvaluationFormDTO.setSubmitDate(employeeEvaluationForm.getSubmitDate());
        employeeEvaluationFormDTO.setComments(employeeEvaluationForm.getComments());
        employeeEvaluationFormDTO.setEvaluationFormId(employeeEvaluationForm.getAppraisalXEvaluationFormTemplate().getEvaluationFormTemplate().getId());

        employeeEvaluationFormDTO.setAnswers(employeeEvaluationForm.getEmployeeEvaluationFormAnswerSet().stream()
                .map(employeeEvaluationFormAnswer -> {
                    AnswerDTO answerDTO = mapperFacade.map(employeeEvaluationFormAnswer, AnswerDTO.class);
                    answerDTO.setScoreValue(mapperFacade.map(employeeEvaluationFormAnswer.getScoreValue(), ScoreValueDTO.class));
                    answerDTO.setComment(employeeEvaluationFormAnswer.getComment());
                    EvaluationFormTemplateXSectionXQuestion xSectionXQuestion = employeeEvaluationFormAnswer.getEvaluationFormTemplateXSectionXQuestion();
                    Question question = xSectionXQuestion.getQuestion();

                    EvaluationFormTemplateXSectionXQuestionDTO xSectionXQuestionDTO = new EvaluationFormTemplateXSectionXQuestionDTO();
                    xSectionXQuestionDTO.setId(xSectionXQuestion.getId());
                    ScoreType scoreType =employeeEvaluationFormAnswerService.getById(answerDTO.getId()).getScoreValue().getScoreType();
                    ScoreValueDTO scoreValueDTO = new ScoreValueDTO();
                    List<ScoreTypeDTO> scoreTypeDTOS = new ArrayList<>();
                    ScoreTypeDTO scoreTypeDTO = new ScoreTypeDTO();
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
                    answerDTO.setScoreValue(scoreValueDTO);
                    answerDTO.setEvaluationFormTemplateXSectionXQuestionDTO(xSectionXQuestionDTO);

                    // Omitted for brevity, every answer doesn't need this information
                    /*answerDTO.setEvaluationFormTemplateXSectionXQuestion(mapperFacade.map(employeeEvaluationFormAnswer
                            .getEvaluationFormTemplateXSectionXQuestion(), EvaluationFormTemplateXSectionXQuestionDTO.class));*/
                    return answerDTO;
                }).collect(Collectors.toList()));
    }

    @Override
    public void mapBtoA(EmployeeEvaluationFormDTO employeeEvaluationFormDTO, EmployeeEvaluationForm employeeEvaluationForm, MappingContext context)
    {
        employeeEvaluationForm.setId(employeeEvaluationFormDTO.getId());
        employeeEvaluationForm.setEmployee(employeeService.getById(employeeEvaluationFormDTO.getEmployee().getId()));
        employeeEvaluationForm
                .setFilledByEmployee(employeeService.getById(employeeEvaluationFormDTO.getFilledByEmployee().getId()));
        employeeEvaluationForm.setMentor(employeeService.getById(employeeEvaluationFormDTO.getMentor().getId()));
        employeeEvaluationForm.setCreateDate(employeeEvaluationFormDTO.getCreateDate());
        employeeEvaluationForm.setSubmitDate(employeeEvaluationFormDTO.getSubmitDate());
        employeeEvaluationForm.setComments(employeeEvaluationFormDTO.getComments());
        EmployeeEvaluationForm evaluationForm = employeeEvaluationFormService .getById(employeeEvaluationFormDTO.getId());
        evaluationForm.getId();

        employeeEvaluationForm.setAppraisalXEvaluationFormTemplate(evaluationForm.getAppraisalXEvaluationFormTemplate());
        // TODO: Iterate the answers on the DTO after validating they are correctly formed, create a Set and overwrite the existing answer set
        List<AnswerDTO> answerDTOS = employeeEvaluationFormDTO.getAnswers();
        EmployeeEvaluationFormAnswer answer = new EmployeeEvaluationFormAnswer();
        answer.setEmployeeEvaluationForm(evaluationForm);
        Set<EmployeeEvaluationFormAnswer> answerSet = new HashSet<>();
        QuestionDTO questionDTO;
        ScoreValueDTO scoreValueDTO;
        ScoreValue scoreValue = new ScoreValue();
        ScoreTypeDTO scoreTypeDTO;
        ScoreType scoreType ;
        Question question = new Question();
        Section section;
        Set<ScoreValue> scoreValueSet = new HashSet<>();
        EvaluationFormTemplateXSectionXQuestion questionSection;

        for (AnswerDTO answerDTO : answerDTOS)
        {//getting value from AnswerDTO
            EvaluationFormTemplateXSectionXQuestionDTO questionSectionDto = answerDTO.getEvaluationFormTemplateXSectionXQuestionDTO();
            scoreValueDTO = answerDTO.getScoreValue();
            int questionSectionDtoId =questionSectionDto.getId();
            questionSection= evaluationFormTemplateXSectionXQuestionService.getById(questionSectionDtoId);
            List<QuestionDTO> questionSectionDTOS = questionSectionDto.getQuestion();
            for(QuestionDTO questionDTO1:questionSectionDTOS)
            {
                //get value from questionDTO
                question.setId(questionDTO1.getId());
                question.setPosition(questionDTO1.getPosition());
                question.setName(questionDTO1.getName());
                questionSection.setId(questionDTO1.getId());
//                section=questionSection.getSection();
//                questionSectionDTOS.add(questionDTO1);
            }
            section=questionSection.getSection();
            scoreType  =section.getScoreType();
            section.setId(questionSection.getSection().getId());
            questionSection.setSection(section);
                questionSection.setEmployeeEvaluationFormAnswerSet(answerSet);

                int id = questionSectionDto.getId();

                scoreValue.setId(scoreValueDTO.getId());
                scoreValue.setValue(scoreValueDTO.getValue());
                scoreValue.setScoreType(scoreType);

                //
                //how to convert xSectionXquestionDTo to XsectionXquestion
                questionSection.setId(id);
                questionSection.setSection(section);
                scoreValue.setScoreType(scoreType);
                questionSection.setQuestion(question);
                //setting answers
                answer.setId(answerDTO.getId());

                answer.setEvaluationFormTemplateXSectionXQuestion(questionSection);
                answer.setScoreValue(scoreValue);
                answer.setComment(answerDTO.getComment());
                answer.setId(answerDTO.getId());
                answer.setEmployeeEvaluationForm(evaluationForm);
                scoreValueSet.add(scoreValue);
                scoreType.setScoreValueSet(scoreValueSet);
                answerSet.add(answer);
                questionSection.setEmployeeEvaluationFormAnswerSet(answerSet);
                employeeEvaluationFormAnswerService.saveAndFlush(answer);
            }
        employeeEvaluationForm.setEmployeeEvaluationFormAnswerSet(answerSet);
    }
}
