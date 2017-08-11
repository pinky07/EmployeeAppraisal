package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.builder.model.AppraisalBuilder;
import com.gft.employeeappraisal.builder.model.AppraisalXEvaluationFormXEmployeeRelationshipBuilder;
import com.gft.employeeappraisal.builder.model.EmployeeBuilder;
import com.gft.employeeappraisal.builder.model.RelationshipBuilder;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.AppraisalXEvaluationFormXEmployeeRelationshipRepository;
import com.gft.employeeappraisal.service.impl.AppraisalXEvaluationFormXEmployeeRelationshipServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Service layer test for {@link AppraisalXEvaluationFormXEmployeeRelationshipService}
 *
 * @author Rubén Jiménez
 */
@RunWith(SpringRunner.class)
public class AppraisalXEvaluationFormXEmployeeRelationshipServiceUnitTest {

    @Mock
    private RelationshipService relationshipService;

    @Mock
    private AppraisalXEvaluationFormXEmployeeRelationshipRepository appraisalXEvaluationFormXEmployeeRelationshipRepository;

    private AppraisalXEvaluationFormXEmployeeRelationshipService appraisalXEvaluationFormXEmployeeRelationshipService;

    @Before
    public void setUp() {
        appraisalXEvaluationFormXEmployeeRelationshipService = new AppraisalXEvaluationFormXEmployeeRelationshipServiceImpl(
                relationshipService,
                appraisalXEvaluationFormXEmployeeRelationshipRepository);
    }


    /**
     * Tests if the method {@link AppraisalXEvaluationFormXEmployeeRelationshipService#findByAppraisalAndEmployee(Appraisal, Employee)}
     * executes successfully
     */
    @Test
    public void findByAppraisalAndEmployee_Successful() {

        // Set up

        // Mock employee
        Employee employee = new EmployeeBuilder()
                .buildWithDefaults();

        // Mock Appraisal
        Appraisal appraisal = new AppraisalBuilder()
                .buildWithDefaults();

        // Mock Relationships
        Relationship self = new RelationshipBuilder().name(RelationshipName.SELF.name()).buildWithDefaults();
        Relationship peer = new RelationshipBuilder().name(RelationshipName.PEER.name()).buildWithDefaults();
        Relationship lead = new RelationshipBuilder().name(RelationshipName.LEAD.name()).buildWithDefaults();
        Relationship other = new RelationshipBuilder().name(RelationshipName.OTHER.name()).buildWithDefaults();
        Relationship mentor = new RelationshipBuilder().name(RelationshipName.MENTOR.name()).buildWithDefaults();

        // Mock source stream
        Stream<Relationship> sourceStream = Stream.of(self, peer, lead, other);
        when(relationshipService.findRelationshipsByNames(
                eq(RelationshipName.SELF),
                eq(RelationshipName.PEER),
                eq(RelationshipName.LEAD),
                eq(RelationshipName.OTHER)))
                .thenReturn(sourceStream);

        // Mock target stream
        Stream<Relationship> targetStream = Stream.of(mentor);
        when(relationshipService.findRelationshipsByNames(
                eq(RelationshipName.MENTOR)))
                .thenReturn(targetStream);

        // Mock source and target list
        List<AppraisalXEvaluationFormXEmployeeRelationship> sourceList = new ArrayList<>();
        List<AppraisalXEvaluationFormXEmployeeRelationship> targetList = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            sourceList.add(new AppraisalXEvaluationFormXEmployeeRelationshipBuilder()
                    .buildWithDefaults());
            targetList.add(new AppraisalXEvaluationFormXEmployeeRelationshipBuilder()
                    .buildWithDefaults());
        }

        when(appraisalXEvaluationFormXEmployeeRelationshipRepository.findByAppraisalAndSourceEmployee(
                eq(appraisal),
                eq(employee),
                any()))
                .thenReturn(sourceList);

        when(appraisalXEvaluationFormXEmployeeRelationshipRepository.findByAppraisalAndTargetEmployee(
                eq(appraisal),
                eq(employee),
                any()))
                .thenReturn(targetList);


        // Execution
        List<AppraisalXEvaluationFormXEmployeeRelationship> result = this.appraisalXEvaluationFormXEmployeeRelationshipService
                .findByAppraisalAndEmployee(appraisal, employee)
                .collect(Collectors.toList());

        // Verification
        assertTrue(result.size() == sourceList.size() + targetList.size());
        assertTrue(result.containsAll(sourceList));
        assertTrue(result.containsAll(targetList));
    }
}
