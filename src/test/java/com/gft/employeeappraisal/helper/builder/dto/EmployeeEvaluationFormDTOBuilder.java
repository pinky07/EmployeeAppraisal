package com.gft.employeeappraisal.helper.builder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.helper.builder.util.GftIdentifierGenerator;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.EmployeeEvaluationFormDTO;
import com.gft.swagger.employees.model.JobLevelDTO;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Builder object for the {@link EmployeeDTO} object.
 *
 * @author Manuel Yepez
 * @author Ruben Jimenez
 */
public class EmployeeEvaluationFormDTOBuilder implements ObjectBuilder<EmployeeEvaluationFormDTO>
{

    private Integer id;
    private EmployeeDTO employee;

    private EmployeeDTO filledByEmployee;

    private EmployeeDTO mentor;

    private OffsetDateTime createDate;

    private OffsetDateTime submitDate;

    private Integer evaluationFormId;

    public EmployeeEvaluationFormDTOBuilder setId(Integer id)
    {
        this.id = id;
        return this;
    }

    public EmployeeEvaluationFormDTOBuilder setEmployee(EmployeeDTO employee)
    {
        this.employee = employee;
        return this;
    }

    public EmployeeEvaluationFormDTOBuilder setFilledByEmployee(EmployeeDTO filledByEmployee)
    {
        this.filledByEmployee = filledByEmployee;
        return this;
    }

    public EmployeeEvaluationFormDTOBuilder setMentor(EmployeeDTO mentor)
    {
        this.mentor = mentor;
        return this;
    }

    public EmployeeEvaluationFormDTOBuilder setCreateDate(OffsetDateTime createDate)
    {
        this.createDate = createDate;
        return this;
    }

    public EmployeeEvaluationFormDTOBuilder setSubmitDate(OffsetDateTime submitDate)
    {
        this.submitDate = submitDate;
        return this;
    }

    public EmployeeEvaluationFormDTOBuilder setEvaluationFormId(Integer evaluationFormId)
    {
        this.evaluationFormId = evaluationFormId;
        return this;
    }

    @Override public EmployeeEvaluationFormDTO build()
    {
        EmployeeEvaluationFormDTO dto = new EmployeeEvaluationFormDTO();
        dto.setId(this.id);
        dto.setSubmitDate(this.submitDate);
        dto.setCreateDate(this.createDate);
        dto.setEmployee(this.employee);
        dto.setEvaluationFormId(this.evaluationFormId);
        dto.setFilledByEmployee(this.filledByEmployee);
        dto.setMentor(this.mentor);
        return dto;

    }
//do not understand why use this method
    @Override public EmployeeEvaluationFormDTO buildWithDefaults()
    {
        return null;
    }
}
