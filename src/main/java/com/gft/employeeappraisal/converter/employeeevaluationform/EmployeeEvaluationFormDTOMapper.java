package com.gft.employeeappraisal.converter.employeeevaluationform;

import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormAnswerService;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormService;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeEvaluationFormDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    public EmployeeEvaluationFormDTOMapper(
            EmployeeService employeeService,
			EmployeeEvaluationFormService employeeEvaluationFormService,
            EmployeeEvaluationFormAnswerService employeeEvaluationFormAnswerService) {
        this.employeeService = employeeService;
        this.employeeEvaluationFormService = employeeEvaluationFormService;
        this.employeeEvaluationFormAnswerService =employeeEvaluationFormAnswerService;

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
        employeeEvaluationFormDTO.setComments(employeeEvaluationForm.getComments());

    }

    @Override
    public void mapBtoA(EmployeeEvaluationFormDTO employeeEvaluationFormDTO, EmployeeEvaluationForm employeeEvaluationForm, MappingContext context) {
        employeeEvaluationForm.setId(employeeEvaluationFormDTO.getId());
        employeeEvaluationForm.setEmployee(employeeService.getById(employeeEvaluationFormDTO.getEmployee().getId()));
        employeeEvaluationForm.setFilledByEmployee(employeeService.getById(employeeEvaluationFormDTO.getFilledByEmployee().getId()));
        employeeEvaluationForm.setMentor(employeeService.getById(employeeEvaluationFormDTO.getMentor().getId()));
        employeeEvaluationForm.setCreateDate(employeeEvaluationFormDTO.getCreateDate());
        employeeEvaluationForm.setSubmitDate(employeeEvaluationFormDTO.getSubmitDate());
        employeeEvaluationForm.setComments(employeeEvaluationFormDTO.getComments());
        employeeEvaluationForm.setAppraisalXEvaluationFormTemplate(employeeEvaluationFormService
				.getById(employeeEvaluationFormDTO.getId()).getAppraisalXEvaluationFormTemplate());
//        employeeEvaluationForm.setEmployeeEvaluationFormAnswerSet
//                (employeeEvaluationFormAnswerService.findByEmployeeEvaluationFormId(employeeEvaluationForm ));
       employeeEvaluationForm.setEmployeeEvaluationFormAnswerSet(employeeEvaluationFormService.getById(employeeEvaluationFormDTO.getId()).getEmployeeEvaluationFormAnswerSet());
    }

}
