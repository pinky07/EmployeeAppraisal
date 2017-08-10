package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EvaluationStatus;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface for methods interacting with the Appraisal repository.
 *
 * @author Manuel Yepez
 */
public interface AppraisalService {
	/**
	 * Lookup method for an Appraisal, given an ID.
	 * @param appraisalId ID to look up.
	 * @return An Optional object that may or may not contain a {@link Appraisal} entity.
	 */
	Optional<Appraisal> findById(int appraisalId);

	/**
	 * Given an employee and (optionally) an evaluation status, this method will return all the
	 * Appraisals the employee has been a part of.
	 *
	 * @param employee Employee to evaluate.
	 * @param evaluationStatus Evaluation type filter (PENDING, DONE). Pass {@code null} if all appraisals are desired.
	 * @return Stream set of matching appraisals.
	 */
	Stream<Appraisal> findEmployeeAppraisals(Employee employee, EvaluationStatus evaluationStatus);


	/**
	 * Save an Entity instance of {@link Appraisal}
	 *
	 * @param appraisal Appraisal Entity to saveAndFlush
	 * @return True if the saveAndFlush is successful
	 */
	void saveAndFlush(Appraisal appraisal);
}
