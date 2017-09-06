package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.employeeappraisal.repository.ApplicationRoleRepository;
import com.gft.employeeappraisal.service.ApplicationRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link ApplicationRoleService}.
 *
 * @author Manuel Yepez
 */
@Service
public class ApplicationRoleServiceImpl implements ApplicationRoleService {

    private final ApplicationRoleRepository applicationRoleRepository;

    @Autowired
    public ApplicationRoleServiceImpl(
            ApplicationRoleRepository applicationRoleRepository) {
        this.applicationRoleRepository = applicationRoleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ApplicationRole> findById(int applicationRoleId) {
        return Optional.ofNullable(applicationRoleRepository.findOne(applicationRoleId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationRole save(ApplicationRole entity) {
        return applicationRoleRepository.save(entity);
    }
}
