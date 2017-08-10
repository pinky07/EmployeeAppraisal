package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.AppraisalXEvaluationFormXEmployeeRelationshipRepository;
import com.gft.employeeappraisal.service.EvaluationFormService;
import com.gft.employeeappraisal.service.EvaluationFormXSectionXQuestionService;
import com.gft.employeeappraisal.service.RelationshipService;
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

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private AppraisalXEvaluationFormXEmployeeRelationshipRepository appraisalXEvaluationFormXEmployeeRelationshipRepository;

    @Override
    public Stream<EvaluationForm> findEvaluationFormsByAppraisalAndEmployee(Appraisal appraisal, Employee employee) {
//        Stream<EvaluationForm> selfEvaluationForm = Stream.of(this.findSelfEvaluationFormByAppraisalAndEmployee(appraisal, employee));
//        Stream<EvaluationForm> referenceEvaluationForms = this.findReferenceEvaluationFormsByAppraisalAndEmployee(appraisal, employee);
//        Stream<EvaluationForm> mentorEvaluationForm = Stream.of(this.findMentorEvaluationFormByAppraisalAndEmployee());
        return null;
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
                .map(appraisalXEvaluationFormXEmployeeRelationship -> appraisalXEvaluationFormXEmployeeRelationship.getAppraisalXEvaluationForm())
                .map(appraisalXEvaluationForm -> appraisalXEvaluationForm.getEvaluationForm());
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
                .map(appraisalXEvaluationFormXEmployeeRelationship -> appraisalXEvaluationFormXEmployeeRelationship.getAppraisalXEvaluationForm())
                .map(appraisalXEvaluationForm -> appraisalXEvaluationForm.getEvaluationForm());
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
                .map(appraisalXEvaluationFormXEmployeeRelationship -> appraisalXEvaluationFormXEmployeeRelationship.getAppraisalXEvaluationForm())
                .map(appraisalXEvaluationForm -> appraisalXEvaluationForm.getEvaluationForm());
    }
}
