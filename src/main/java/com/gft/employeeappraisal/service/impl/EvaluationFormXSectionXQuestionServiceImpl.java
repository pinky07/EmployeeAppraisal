package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.AppraisalXEvaluationFormXEmployeeRelationshipRepository;
import com.gft.employeeappraisal.service.EvaluationFormXSectionXQuestionService;
import com.gft.employeeappraisal.service.RelationshipService;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service implementation of {@link EvaluationFormXSectionXQuestionService}.
 *
 * @author Rubén Jiménez
 */
@Service
public class EvaluationFormXSectionXQuestionServiceImpl implements EvaluationFormXSectionXQuestionService {

    private final RelationshipService relationshipService;
    private final AppraisalXEvaluationFormXEmployeeRelationshipRepository appraisalXEvaluationFormXEmployeeRelationshipRepository;

    @Autowired
    public EvaluationFormXSectionXQuestionServiceImpl(
            RelationshipService relationshipService,
            AppraisalXEvaluationFormXEmployeeRelationshipRepository appraisalXEvaluationFormXEmployeeRelationshipRepository) {
        this.relationshipService = relationshipService;
        this.appraisalXEvaluationFormXEmployeeRelationshipRepository = appraisalXEvaluationFormXEmployeeRelationshipRepository;
    }

    @Override
    public Stream<EvaluationForm> findByAppraisalAndEmployee(Appraisal appraisal, Employee employee) {
        // TODO Implement this method!
        throw new NotImplementedException();
    }

    @Override
    public Optional<EvaluationForm> findSelfEvaluationFormByAppraisalAndEmployee(Appraisal appraisal, Employee employee) {
        Set<Relationship> relationshipSet = relationshipService
                .findRelationshipsByNames(RelationshipName.SELF)
                .collect(Collectors.toSet());
        return appraisalXEvaluationFormXEmployeeRelationshipRepository
                .findByAppraisalAndEmployeeAsSourceAndTarget(appraisal, employee, relationshipSet)
                .stream()
                .findFirst()
                .map(AppraisalXEvaluationFormXEmployeeRelationship::getAppraisalXEvaluationForm)
                .map(AppraisalXEvaluationForm::getEvaluationForm);
    }

    @Override
    public Stream<EvaluationForm> findReferenceEvaluationFormsByAppraisalAndEmployee(Appraisal appraisal, Employee employee) {
        Set<Relationship> relationshipSet = relationshipService
                .findRelationshipsByNames(
                        RelationshipName.PEER,
                        RelationshipName.LEAD,
                        RelationshipName.OTHER)
                .collect(Collectors.toSet());
        return appraisalXEvaluationFormXEmployeeRelationshipRepository
                .findByAppraisalAndSourceEmployee(appraisal, employee, relationshipSet)
                .stream()
                .map(AppraisalXEvaluationFormXEmployeeRelationship::getAppraisalXEvaluationForm)
                .map(AppraisalXEvaluationForm::getEvaluationForm);
    }

    @Override
    public Optional<EvaluationForm> findMentorEvaluationFormByAppraisalAndEmployee(Appraisal appraisal, Employee employee) {
        Set<Relationship> relationshipSet = relationshipService
                .findRelationshipsByNames(RelationshipName.MENTOR)
                .collect(Collectors.toSet());
        return appraisalXEvaluationFormXEmployeeRelationshipRepository
                .findByAppraisalAndTargetEmployee(appraisal, employee, relationshipSet)
                .stream()
                .findFirst()
                .map(AppraisalXEvaluationFormXEmployeeRelationship::getAppraisalXEvaluationForm)
                .map(AppraisalXEvaluationForm::getEvaluationForm);
    }
}
