package com.gft.employee.service.impl;

import com.gft.employee.model.ApplicationRole;
import com.gft.employee.repository.ApplicationRoleRepository;
import com.gft.employee.service.ApplicationRoleService;
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

	@Autowired
	private ApplicationRoleRepository applicationRoleRepository;

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
