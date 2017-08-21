package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.AppraisalXEvaluationFormXEmployeeRelationshipRepository;
import com.gft.employeeappraisal.service.AppraisalXEvaluationFormXEmployeeRelationshipService;
import com.gft.employeeappraisal.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service implementation of {@link AppraisalXEvaluationFormXEmployeeRelationshipService}.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
@Service
public class AppraisalXEvaluationFormXEmployeeRelationshipServiceImpl implements
        AppraisalXEvaluationFormXEmployeeRelationshipService {

    private RelationshipService relationshipService;
    private AppraisalXEvaluationFormXEmployeeRelationshipRepository appraisalXEvaluationFormXEmployeeRelationshipRepository;

    @Autowired
    public AppraisalXEvaluationFormXEmployeeRelationshipServiceImpl(
            RelationshipService relationshipService,
            AppraisalXEvaluationFormXEmployeeRelationshipRepository appraisalXEvaluationFormXEmployeeRelationshipRepository) {
        this.relationshipService = relationshipService;
        this.appraisalXEvaluationFormXEmployeeRelationshipRepository = appraisalXEvaluationFormXEmployeeRelationshipRepository;
    }


    @Override
    public Stream<AppraisalXEvaluationFormXEmployeeRelationship> findByEmployeeRelationships(
            List<EmployeeRelationship> employeeRelationships) {
        return appraisalXEvaluationFormXEmployeeRelationshipRepository
                .findByEmployeeRelationshipIn(new HashSet<>(employeeRelationships)).stream();
    }

    @Override
    public Stream<AppraisalXEvaluationFormXEmployeeRelationship> findByEmployeeRelationshipsAndEvaluationStatus(
            List<EmployeeRelationship> employeeRelationships,
            EvaluationStatus evaluationStatus) {
        return appraisalXEvaluationFormXEmployeeRelationshipRepository
                .findByEmployeeRelationshipInAndEvaluationStatus(new HashSet<>(employeeRelationships), evaluationStatus)
                .stream();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Stream<AppraisalXEvaluationFormXEmployeeRelationship> findByAppraisalAndEmployeeAndSourceRelationships(
            Appraisal appraisal,
            Employee employee,
            RelationshipName... relationshipNames
    ) {
        // All relationships where employee appears as source
        Set<Relationship> sourceRelationshipSet = this.relationshipService.findRelationshipsByNames(
                relationshipNames)
                .collect(Collectors.toSet());

        // Query the DB
        List<AppraisalXEvaluationFormXEmployeeRelationship> sourceList = new ArrayList<>(this.appraisalXEvaluationFormXEmployeeRelationshipRepository
                .findByAppraisalAndSourceEmployee(appraisal, employee, sourceRelationshipSet));

        return sourceList.stream();
    }

    @Override
    public Stream<AppraisalXEvaluationFormXEmployeeRelationship> findByAppraisalAndEmployee(
            Appraisal appraisal,
            Employee employee) {

        // All relationships where employee appears as source
        Set<Relationship> sourceRelationshipSet = this.relationshipService.findRelationshipsByNames(
                RelationshipName.SELF,
                RelationshipName.PEER,
                RelationshipName.LEAD,
                RelationshipName.OTHER)
                .collect(Collectors.toSet());

        // All relationships where employee appears as target
        Set<Relationship> targetRelationshipSet = this.relationshipService.findRelationshipsByNames(
                RelationshipName.MENTOR)
                .collect(Collectors.toSet());

        // Query the DB
        List<AppraisalXEvaluationFormXEmployeeRelationship> sourceList = new ArrayList<>(this.appraisalXEvaluationFormXEmployeeRelationshipRepository
                .findByAppraisalAndSourceEmployee(appraisal, employee, sourceRelationshipSet));
        List<AppraisalXEvaluationFormXEmployeeRelationship> targetList = new ArrayList<>(this.appraisalXEvaluationFormXEmployeeRelationshipRepository
                .findByAppraisalAndTargetEmployee(appraisal, employee, targetRelationshipSet));

        // Join lists and stream them
        sourceList.addAll(targetList);
        return sourceList.stream();
    }

    @Override
    public void saveAndFlush(AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship) {
        appraisalXEvaluationFormXEmployeeRelationshipRepository.save(appraisalXEvaluationFormXEmployeeRelationship);
    }
}
