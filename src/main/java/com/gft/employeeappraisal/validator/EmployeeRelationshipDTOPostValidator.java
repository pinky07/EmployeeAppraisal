package com.gft.employeeappraisal.validator;

import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.service.EmployeeService;
import com.gft.employeeappraisal.service.RelationshipTypeService;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.Objects;

/**
 * Class that describes validation rules for the conversion from an {@link EmployeeRelationshipDTO} to an
 * {@link EmployeeRelationship}
 *
 * @author Rubén Jiménez
 */
public class EmployeeRelationshipDTOPostValidator implements HttpValidator {

    private static final String ID_FIELD = "id";
    private static final String REFERRED_FIELD = "referred";
    private static final String RELATIONSHIP_FIELD = "relationship";
    private static final String START_DATE_FIELD = "startDate";
    private static final String END_DATE_FIELD = "endDate";

    private final EmployeeService employeeService;
    private final RelationshipTypeService relationshipTypeService;

    @Autowired
    public EmployeeRelationshipDTOPostValidator(
            EmployeeService employeeService,
            RelationshipTypeService relationshipTypeService) {
        this.employeeService = employeeService;
        this.relationshipTypeService = relationshipTypeService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return EmployeeRelationshipDTO.class.equals(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(HttpMethod httpMethod) {
        return httpMethod.equals(HttpMethod.POST);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {
        EmployeeRelationshipDTO employeeRelationshipDTO = (EmployeeRelationshipDTO) target;

        // Id Field
        if (employeeRelationshipDTO.getId() != 0) {
            errors.rejectValue(ID_FIELD, "employeeRelationshipDTO.post.idSet");
        }

        // Referred Field
        ValidationUtils.rejectIfEmpty(errors, REFERRED_FIELD, "employeeRelationshipDTO.post.referredNotSet");
        if (Objects.nonNull(employeeRelationshipDTO.getReferred())
                && !this.employeeService.findById(employeeRelationshipDTO.getReferred().getId()).isPresent()) {
            errors.rejectValue(REFERRED_FIELD, "employeeRelationshipDTO.post.referredNotFound");
        }

        // Relationship Field
        ValidationUtils.rejectIfEmpty(errors, RELATIONSHIP_FIELD, "employeeRelationshipDTO.post.relationshipNotSet");
        if (Objects.nonNull(employeeRelationshipDTO.getRelationshipType())
                && !this.relationshipTypeService.findById(employeeRelationshipDTO.getRelationshipType().getId()).isPresent()) {
            errors.rejectValue(RELATIONSHIP_FIELD, "employeeRelationshipDTO.post.relationshipNotFound");
        }

        // Start Date Field
        if (Objects.nonNull(employeeRelationshipDTO.getStartDate())) {
            errors.rejectValue(START_DATE_FIELD, "employeeRelationshipDTO.post.startDateSet");
        }

        // End Date Field
        if (Objects.nonNull(employeeRelationshipDTO.getEndDate())) {
            errors.rejectValue(END_DATE_FIELD, "employeeRelationshipDTO.post.endDateSet");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, HttpMethod httpMethod, Errors errors) {
        validate(target, errors);
    }
}
