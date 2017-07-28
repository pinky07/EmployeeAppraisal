package com.gft.employee.service.impl;

import com.gft.employee.model.JobLevel;
import com.gft.employee.repository.JobLevelRepository;
import com.gft.employee.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link JobLevelService}
 *
 * @author Manuel Yepez
 */
@Service
public class JobLevelServiceImpl implements JobLevelService {

	@Autowired
	private JobLevelRepository jobLevelRepository;

	/**
	 * @see JobLevelService#findById(int)
	 * @param jobLevelId Internal lookup ID for the JobLevel.
	 * @return The JobLevel entity. {@code null} if there is no JobLevel under the provided ID.
	 */
	@Override
	public Optional<JobLevel> findById(int jobLevelId) {
		return Optional.ofNullable(jobLevelRepository.findOne(jobLevelId));
	}

	@Override
	public JobLevel save(JobLevel entity) {
		return jobLevelRepository.save(entity);
	}
}
