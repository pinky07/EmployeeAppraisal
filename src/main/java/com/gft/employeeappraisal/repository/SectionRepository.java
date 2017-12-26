package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SectionRepository extends JpaRepository<Section, Integer> {
}