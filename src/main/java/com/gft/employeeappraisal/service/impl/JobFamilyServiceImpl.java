package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.JobFamily;
import com.gft.employeeappraisal.repository.JobFamilyRepository;
import com.gft.employeeappraisal.service.JobFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation of {@link JobFamilyService}
 *
 * @author Manuel Yepez
 */
@Service
public class JobFamilyServiceImpl implements JobFamilyService {

	@Autowired
	private JobFamilyRepository jobFamilyRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobFamily save(JobFamily entity) {
		return jobFamilyRepository.save(entity);
	}
}
