package com.gft.employeeappraisal.repository;

import com.gft.employeeappraisal.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A Repository for the entity Employee is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 * 
 * @author Ricardo Coto
 * @author Manuel Yepez
 */
@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	Employee findByEmail(String email);
}