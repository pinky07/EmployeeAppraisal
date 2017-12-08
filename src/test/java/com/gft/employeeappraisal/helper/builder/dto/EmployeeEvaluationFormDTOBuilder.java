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
}
