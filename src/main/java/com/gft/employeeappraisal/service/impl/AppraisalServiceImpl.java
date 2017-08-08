package com.gft.employeeappraisal.service.impl;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.repository.AppraisalRepository;
import com.gft.employeeappraisal.service.AppraisalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link AppraisalService}.
 *
 * @author Manuel Yepez
 */
@Service
public class AppraisalServiceImpl implements AppraisalService {

	@Autowired
	private AppraisalRepository appraisalRepository;

	/**
	 * @inheritDoc
	 */
	@Override
	public Optional<Appraisal> findById(int appraisalId) {
		return Optional.ofNullable(appraisalRepository.findOne(appraisalId));
	}
}
