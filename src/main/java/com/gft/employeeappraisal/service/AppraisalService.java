package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.Appraisal;

import java.util.Optional;

/**
 * Interface for methods interacting with the Appraisal repository.
 *
 * @author Manuel Yepez
 */
public interface AppraisalService {
	/**
	 * Lookup method for an ApplicationRole, given an ID.
	 * @param appraisalId ID to look up.
	 * @return An Optional object that may or may not contain a {@link Appraisal} entity.
	 */
	Optional<Appraisal> findById(int appraisalId);
}
