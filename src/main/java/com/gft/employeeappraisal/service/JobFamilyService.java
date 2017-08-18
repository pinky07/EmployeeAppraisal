package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.JobFamily;

/**
 * Interface for methods interacting with the JobFamily repository.
 *
 * @author Manuel Yepez
 */
public interface JobFamilyService {

    /**
     * Given a {@link JobFamily} entity, persists said entity into the database.
     *
     * @param entity Entity to be persisted.
     * @return Entity after being persisted to the DB.
     */
    JobFamily save(JobFamily entity);
}
