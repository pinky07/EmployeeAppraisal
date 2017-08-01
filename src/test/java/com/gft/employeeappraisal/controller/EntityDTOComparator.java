package com.gft.employeeappraisal.controller;


import com.gft.employeeappraisal.model.ApplicationRole;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.JobFamily;
import com.gft.employeeappraisal.model.JobLevel;
import com.gft.swagger.employees.model.ApplicationRoleDTO;
import com.gft.swagger.employees.model.EmployeeDTO;
import com.gft.swagger.employees.model.JobFamilyDTO;
import com.gft.swagger.employees.model.JobLevelDTO;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Compares Entities to DTOs
 *
 * @author Ruben Jimenez
 */
@Component
public class EntityDTOComparator {

    /**
     * Compare Employee Entity and DTO
     *
     * @param entity Employee Entity
     * @param dto    Employee DTO
     */
    public void assertEqualsEmployee(Employee entity, EmployeeDTO dto) {
        assertNotNull(entity);
        assertNotNull(dto);
        assertEquals((Integer) entity.getId(), dto.getId());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getGftIdentifier(), dto.getGftIdentifier());
        assertEqualsJobLevel(entity.getJobLevel(), dto.getJobLevel());
        assertEqualsApplicationRole(entity.getApplicationRole(), dto.getApplicationRole());
    }

    /**
     * Compare Application Role Entity and DTO
     *
     * @param entity Application Role Entity
     * @param dto    Application Role DTO
     */
    public void assertEqualsApplicationRole(ApplicationRole entity, ApplicationRoleDTO dto) {
        assertNotNull(entity);
        assertNotNull(dto);
        assertEquals((Integer) entity.getId(), dto.getId());
        assertEquals(entity.getName(), entity.getName());
        assertEquals(entity.getDescription(), entity.getDescription());
    }

    /**
     * Compare Job Level Entity and DTO
     *
     * @param entity Job Level Entity
     * @param dto    Job Level DTO
     */
    public void assertEqualsJobLevel(JobLevel entity, JobLevelDTO dto) {
        assertNotNull(entity);
        assertNotNull(dto);
        assertEquals((Integer) entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getExpertise(), dto.getExpertise());
        assertEqualsJobFamily(entity.getJobFamily(), dto.getJobFamily());
    }

    /**
     * Compare Job Family Entity and DTO
     *
     * @param entity Job Family Entity
     * @param dto    Job Family DTO
     */
    public void assertEqualsJobFamily(JobFamily entity, JobFamilyDTO dto) {
        assertNotNull(entity);
        assertNotNull(dto);
        assertEquals((Integer) entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
    }
}
