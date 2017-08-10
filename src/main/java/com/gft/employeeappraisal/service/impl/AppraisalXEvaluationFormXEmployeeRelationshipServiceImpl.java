package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.AppraisalXEvaluationFormXEmployeeRelationship;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.EvaluationStatus;
import com.gft.employeeappraisal.repository.AppraisalXEvaluationFormXEmployeeRelationshipRepository;
import com.gft.employeeappraisal.service.AppraisalXEvaluationFormXEmployeeRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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

	@Autowired
	private AppraisalXEvaluationFormXEmployeeRelationshipRepository
			appraisalXEvaluationFormXEmployeeRelationshipRepository;

	@Override
	public Stream<AppraisalXEvaluationFormXEmployeeRelationship>
		findByEmployeeRelationships(List<EmployeeRelationship> employeeRelationships) {
		return appraisalXEvaluationFormXEmployeeRelationshipRepository
				.findByEmployeeRelationshipIn(new HashSet<>(employeeRelationships)).stream();
	}

	@Override
	public Stream<AppraisalXEvaluationFormXEmployeeRelationship>
		findByEmployeeRelationshipsAndEvaluationStatus(List<EmployeeRelationship> employeeRelationships,
													   EvaluationStatus evaluationStatus) {
		return appraisalXEvaluationFormXEmployeeRelationshipRepository
				.findByEmployeeRelationshipInAndEvaluationStatus(new HashSet<>(employeeRelationships), evaluationStatus)
				.stream();
	}

	@Override
	public void saveAndFlush(AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship) {
		appraisalXEvaluationFormXEmployeeRelationshipRepository.save(appraisalXEvaluationFormXEmployeeRelationship);
	}
}
