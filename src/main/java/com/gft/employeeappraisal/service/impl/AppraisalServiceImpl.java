package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.AppraisalRepository;
import com.gft.employeeappraisal.service.AppraisalService;
import com.gft.employeeappraisal.service.AppraisalXEvaluationFormXEmployeeRelationshipService;
import com.gft.employeeappraisal.service.EmployeeRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service implementation of {@link AppraisalService}.
 *
 * @author Manuel Yepez
 */
@Service
public class AppraisalServiceImpl implements AppraisalService {

    @Autowired
    private AppraisalRepository appraisalRepository;

	@Autowired
	private AppraisalXEvaluationFormXEmployeeRelationshipService appraisalXEvaluationFormXEmployeeRelationshipService;

	@Autowired
	private EmployeeRelationshipService employeeRelationshipService;

	/**
	 * @inheritDoc
	 */
	@Override
	public Optional<Appraisal> findById(int appraisalId) {
		return Optional.ofNullable(appraisalRepository.findOne(appraisalId));
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Stream<Appraisal> findEmployeeAppraisals(Employee employee, EvaluationStatus evaluationStatus) {
		// Find SELF relationships
		List<EmployeeRelationship> employeeRelationships = employeeRelationshipService
				.findBySourceEmployeeAndRelationships(employee, RelationshipName.SELF).collect(Collectors.toList());

		Stream<AppraisalXEvaluationFormXEmployeeRelationship> evaluationFormXEmployeeRelationshipStream =
				appraisalXEvaluationFormXEmployeeRelationshipService
				.findByEmployeeRelationshipsAndEvaluationStatus(employeeRelationships, evaluationStatus);

		return evaluationFormXEmployeeRelationshipStream
				.map(ef -> ef.getAppraisalXEvaluationForm().getAppraisal()).distinct();
	}

    /**
     * @inheritDoc
     */
    @Override
    public void saveAndFlush(Appraisal appraisal) {
        appraisalRepository.saveAndFlush(appraisal);
    }
}
