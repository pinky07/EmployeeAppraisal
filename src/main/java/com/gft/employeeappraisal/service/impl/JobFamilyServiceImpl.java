package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.JobFamily;
import com.gft.employeeappraisal.repository.JobFamilyRepository;
import com.gft.employeeappraisal.service.JobFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link JobFamilyService}
 *
 * @author Manuel Yepez
 */
@Service
public class JobFamilyServiceImpl implements JobFamilyService {

    private JobFamilyRepository jobFamilyRepository;

    @Autowired
    public JobFamilyServiceImpl(JobFamilyRepository jobFamilyRepository) {
        this.jobFamilyRepository = jobFamilyRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<JobFamily> findById(int jobFamilyId) {
        return Optional.ofNullable(jobFamilyRepository.findOne(jobFamilyId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<JobFamily> saveAndFlush(JobFamily entity) {
        return Optional.ofNullable(jobFamilyRepository.saveAndFlush(entity));
    }
}
