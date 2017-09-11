package com.gft.employeeappraisal.converter.employeeevaluationform;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
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

    private final EmployeeEvaluationFormService employeeEvaluationFormService;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeEvaluationFormDTOMapper(
            EmployeeEvaluationFormService employeeEvaluationFormService,
            EmployeeService employeeService) {
        this.employeeEvaluationFormService = employeeEvaluationFormService;
        this.employeeService = employeeService;
    }

    @Override
    public void mapAtoB(EmployeeEvaluationForm employeeEvaluationForm, EmployeeEvaluationFormDTO employeeEvaluationFormDTO, MappingContext context) {
        employeeEvaluationFormDTO.setId(employeeEvaluationForm.getId());
        employeeEvaluationFormDTO.setEmployee(mapperFacade.map(employeeEvaluationForm.getEmployee(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setFilledByEmployee(mapperFacade.map(employeeEvaluationForm.getFilledByEmployee(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setMentor(mapperFacade.map(employeeEvaluationForm.getMentor(), EmployeeDTO.class));
        employeeEvaluationFormDTO.setCreateDate(employeeEvaluationForm.getCreateDate());
        employeeEvaluationFormDTO.setSubmitDate(employeeEvaluationForm.getSubmitDate());

    }

    @Override
    public void mapBtoA(EmployeeEvaluationFormDTO employeeEvaluationFormDTO, EmployeeEvaluationForm employeeEvaluationForm, MappingContext context) {
        employeeEvaluationForm.setId(employeeEvaluationFormDTO.getId());
        employeeEvaluationForm.setEmployee(employeeService.getById(employeeEvaluationFormDTO.getEmployee().getId()));
        employeeEvaluationForm.setFilledByEmployee(employeeService.getById(employeeEvaluationFormDTO.getFilledByEmployee().getId()));
        employeeEvaluationForm.setMentor(employeeService.getById(employeeEvaluationFormDTO.getMentor().getId()));
        employeeEvaluationForm.setCreateDate(employeeEvaluationFormDTO.getCreateDate());
        employeeEvaluationForm.setSubmitDate(employeeEvaluationFormDTO.getSubmitDate());
    }
}