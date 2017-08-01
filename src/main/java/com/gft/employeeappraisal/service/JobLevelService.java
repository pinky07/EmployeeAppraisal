package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.JobLevel;

import java.util.Optional;

/**
 * Interface for methods interacting with the JobLevel repository.
 *
 * @author Manuel Yepez
 */
public interface JobLevelService {

	/**
	 * Given an ID, returns a JobLevel entity.
	 * @param jobLevelId Internal lookup ID for the JobLevel.
	 * @return An Optional object that may or may not contain a JobLevel entity.
	 */
	Optional<JobLevel> findById(int jobLevelId);

	/**
	 * Given a {@link JobLevel} entity, persists such an entity to the database.
	 * @param entity The entity to be persisted.
	 * @return The same entity after being persisted.
	 */
	JobLevel save(JobLevel entity);
}
