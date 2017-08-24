package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.JobFamily;

import java.util.Optional;

/**
 * Interface for methods interacting with the JobFamily repository.
 *
 * @author Manuel Yepez
 */
public interface JobFamilyService {

    /**
     * Given an ID, returns a JobFamily entity.
     *
     * @param jobFamilyId Internal lookup ID for the JobFamily.
     * @return An Optional object that may or may not contain a JobLevel entity.
     */
    Optional<JobFamily> findById(int jobFamilyId);

    /**
     * Given a {@link JobFamily} entity, persists said entity into the database.
     *
     * @param entity Entity to be persisted.
     * @return Entity after being persisted to the DB.
     */
    Optional<JobFamily> saveAndFlush(JobFamily entity);
}
