package com.gft.employeeappraisal.converter.employeeevaluationform;

import com.gft.employeeappraisal.converter.scoretype.ScoreTypeDTOConverter;
import com.gft.employeeappraisal.converter.section.SectionDTOConverter;
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
    private final SectionService sectionService;
//    private final SectionDTOConverter sectionDTOConverter;
//    private final ScoreTypeDTOConverter scoreTypeDTOConverter;
    @Autowired
    public EmployeeEvaluationFormDTOMapper(EmployeeService employeeService,
            EmployeeEvaluationFormService employeeEvaluationFormService, QuestionService questionService,
            EmployeeEvaluationFormAnswerService employeeEvaluationFormAnswerService,
            EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService,
            ScoreValueService scoreValueService,
            SectionService sectionService
            )
    {
        this.employeeService = employeeService;
        this.employeeEvaluationFormService = employeeEvaluationFormService;
        this.questionService = questionService;
        this.employeeEvaluationFormAnswerService =employeeEvaluationFormAnswerService;
        this.evaluationFormTemplateXSectionXQuestionService =evaluationFormTemplateXSectionXQuestionService;
        this.scoreValueService =scoreValueService;
        this.sectionService =sectionService;
//        this.sectionDTOConverter=sectionDTOConverter;
//        this.scoreTypeDTOConverter=scoreTypeDTOConverter;

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
                    ScoreTypeDTO scoreTypeDTO = new ScoreTypeDTO();
                    List<ScoreTypeDTO> scoreTypeDTOS = new ArrayList<>();
                    SectionDTO sectionDTO = new SectionDTO();
                    SectionDescriptionDTO sectionDescriptionDTO = new SectionDescriptionDTO();


                    ScoreValue scoreValue =employeeEvaluationFormAnswerService.getById(answerDTO.getId()).getScoreValue();

                    scoreValueDTO.setId(scoreValue.getId());
                   scoreValueDTO.setValue(scoreValue.getValue());
                   scoreValueDTO.setDescription(scoreValue.getDescription());

                       Set<Section> sectionSet =scoreType.getSectionSet();
//                    scoreTypeDTOConverter.convertBack(scoreTypeDTO);
                      List<SectionDescriptionDTO> sectionDescriptionDTOList = new ArrayList<SectionDescriptionDTO>();
                       //if scoretype id is one then need to iterate section
                    if(scoreType.getId()==1){
                        Set<Section> sectionSet1=scoreType.getSectionSet();
                        for(Section section:sectionSet1){
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
        employeeEvaluationForm.setAppraisalXEvaluationFormTemplate(evaluationForm.getAppraisalXEvaluationFormTemplate());
        List<AnswerDTO> answerDTOS = employeeEvaluationFormDTO.getAnswers();
        EmployeeEvaluationFormAnswer answer = new EmployeeEvaluationFormAnswer();
        answer.setEmployeeEvaluationForm(evaluationForm);
        Set<EmployeeEvaluationFormAnswer> answerSet = new HashSet<>();
        ScoreValueDTO scoreValueDTO;
        ScoreValue scoreValue = new ScoreValue();
        ScoreType scoreType = new ScoreType();
        Question question = new Question();
        EvaluationFormTemplateXSectionXQuestion questionSection;

        for (AnswerDTO answerDTO : answerDTOS)
        {//getting value from AnswerDTO
            EvaluationFormTemplateXSectionXQuestionDTO questionSectionDto = answerDTO.getEvaluationFormTemplateXSectionXQuestionDTO();
            scoreValueDTO = answerDTO.getScoreValue();
            questionSection= evaluationFormTemplateXSectionXQuestionService.getById(questionSectionDto.getId());
            List<QuestionDTO> questionSectionDTOS = questionSectionDto.getQuestion();
            for(QuestionDTO questionDTO1:questionSectionDTOS)
            {
                //get value from questionDTO
                question.setId(questionDTO1.getId());
                question.setPosition(questionDTO1.getPosition());
                question.setName(questionDTO1.getName());
                questionSection.setId(questionDTO1.getId());
            }
                 scoreValue.setScoreType(scoreType);
                 questionSection.setEmployeeEvaluationFormAnswerSet(answerSet);
                scoreValue.setId(scoreValueDTO.getId());
                scoreValue.setValue(scoreValueDTO.getValue());
                questionSection.setId(questionSectionDto.getId());
                scoreValue.setScoreType(scoreType);
                questionSection.setQuestion(question);
                //setting answers
                answer.setId(answerDTO.getId());
                answer.setEvaluationFormTemplateXSectionXQuestion(questionSection);
                answer.setScoreValue(scoreValue);
                answer.setId(answerDTO.getId());
                answer.setComment(answerDTO.getComment());
                answer.setEmployeeEvaluationForm(evaluationForm);
                answerSet.add(answer);
                scoreValue.setScoreType(scoreType);
                employeeEvaluationForm.setEmployeeEvaluationFormAnswerSet(answerSet);
                employeeEvaluationFormAnswerService.saveAndFlush(answer);
        }
    }
}
