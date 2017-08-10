package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.AppraisalXEvaluationFormXEmployeeRelationship;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.employeeappraisal.model.EvaluationStatus;

import java.util.List;
import java.util.stream.Stream;

/**
 * Interface for methods interacting with the AppraisalXEvaluationFormXEmployeeRelationship repository.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
public interface AppraisalXEvaluationFormXEmployeeRelationshipService {

	Stream<AppraisalXEvaluationFormXEmployeeRelationship>
		findByEmployeeRelationships(List<EmployeeRelationship> employeeRelationships);

	Stream<AppraisalXEvaluationFormXEmployeeRelationship>
		findByEmployeeRelationshipsAndEvaluationStatus(List<EmployeeRelationship> employeeRelationships,
													   EvaluationStatus evaluationStatus);

	/**
	 * Save an Entity instance of {@link AppraisalXEvaluationFormXEmployeeRelationship}
	 *
	 * @param appraisalXEvaluationFormXEmployeeRelationship Appraisal Entity to saveAndFlush
	 * @return True if the saveAndFlush is successful
	 */
	void saveAndFlush(AppraisalXEvaluationFormXEmployeeRelationship appraisalXEvaluationFormXEmployeeRelationship);
}
