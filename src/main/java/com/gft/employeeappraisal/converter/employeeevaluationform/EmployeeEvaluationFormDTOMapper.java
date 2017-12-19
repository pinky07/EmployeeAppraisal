package com.gft.employeeappraisal.converter.employeeevaluationform;

import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.model.AnswerDTO;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeEvaluationFormDTO;
import com.gft.swagger.employees.model.ScoreValueDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Defines a mapping structure to be used by {@link EmployeeEvaluationFormDTOConverter}.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@Component
public class EmployeeEvaluationFormDTOMapper extends CustomMapper<EmployeeEvaluationForm, EmployeeEvaluationFormDTO> {

    private final EmployeeService employeeService;
    private final EmployeeEvaluationFormService employeeEvaluationFormService;

    @Autowired
    public EmployeeEvaluationFormDTOMapper(
            EmployeeService employeeService,
            EmployeeEvaluationFormService employeeEvaluationFormService) {
        this.employeeService = employeeService;
        this.employeeEvaluationFormService = employeeEvaluationFormService;

    }

    @Override
    public void mapAtoB(EmployeeEvaluationForm employeeEvaluationForm, EmployeeEvaluationFormDTO employeeEvaluationFormDTO, MappingContext context) {
        employeeEvaluationFormDTO.setId(employeeEvaluationForm.getId());
        employeeEvaluationFormDTO.setEmployee(mapperFacade.map(employeeEvaluationForm.getEmployee(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setFilledByEmployee(mapperFacade.map(employeeEvaluationForm.getFilledByEmployee(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setMentor(mapperFacade.map(employeeEvaluationForm.getMentor(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setCreateDate(employeeEvaluationForm.getCreateDate());
        employeeEvaluationFormDTO.setSubmitDate(employeeEvaluationForm.getSubmitDate());
        employeeEvaluationFormDTO.setEvaluationFormId(employeeEvaluationForm
                .getAppraisalXEvaluationFormTemplate()
                .getEvaluationFormTemplate()
                .getId());
        employeeEvaluationFormDTO.setAnswers(employeeEvaluationForm
                .getEmployeeEvaluationFormAnswerSet()
                .stream()
                .map(employeeEvaluationFormAnswer -> {
                    AnswerDTO answerDTO = mapperFacade.map(employeeEvaluationFormAnswer, AnswerDTO.class);
                    answerDTO.setScoreValue(mapperFacade.map(employeeEvaluationFormAnswer.getScoreValue(), ScoreValueDTO.class));
                    answerDTO.setComment(employeeEvaluationFormAnswer.getComment());
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
        employeeEvaluationForm.setFilledByEmployee(employeeService.getById(employeeEvaluationFormDTO.getFilledByEmployee().getId()));
        employeeEvaluationForm.setMentor(employeeService.getById(employeeEvaluationFormDTO.getMentor().getId()));
        employeeEvaluationForm.setCreateDate(employeeEvaluationFormDTO.getCreateDate());
        employeeEvaluationForm.setSubmitDate(employeeEvaluationFormDTO.getSubmitDate());
        EmployeeEvaluationForm evaluationForm = employeeEvaluationFormService
                .getById(employeeEvaluationFormDTO.getId());
        employeeEvaluationForm.setAppraisalXEvaluationFormTemplate(evaluationForm.getAppraisalXEvaluationFormTemplate());
        // TODO: Iterate the answers on the DTO after validating they are correctly formed, create a Set and overwrite the existing answer set
        //employeeEvaluationForm.setEmployeeEvaluationFormAnswerSet(evaluationForm.getEmployeeEvaluationFormAnswerSet());
    }

}
