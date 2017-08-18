package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.ApplicationRole;

import java.util.Optional;

/**
 * Interface for methods interacting with the ApplicationRole repository.
 *
 * @author Manuel Yepez
 */
public interface ApplicationRoleService {
    /**
     * Lookup method for an ApplicationRole, given an ID.
     *
     * @param applicationRoleId ID to look up.
     * @return An Optional object that may or may not contain a {@link ApplicationRole} entity.
     */
    Optional<ApplicationRole> findById(int applicationRoleId);

    /**
     * Given a {@link ApplicationRole} entity, persists such an entity to the database.
     *
     * @param entity Entity to be persisted.
     * @return The same entity after being persisted.
     */
    ApplicationRole save(ApplicationRole entity);
}
