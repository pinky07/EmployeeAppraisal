package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface for methods interacting with the Appraisal repository.
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
public interface AppraisalService {

    /**
     * Looks up an {@link Appraisal} given its Id.
     *
     * @param appraisalId Id to look up.
     * @return An Optional object that may or may not contain a {@link Appraisal} entity.
     */
    Optional<Appraisal> findById(int appraisalId);

    /**
     * Looks up an {@link Appraisal} given its Id. Throws an Exception if the {@link Appraisal} is not found.
     *
     * @param appraisalId Id to look up.
     * @return An Optional object that may or may not contain a {@link Appraisal} entity.
     */
    Appraisal getById(int appraisalId) throws NotFoundException;

    /**
     * Given an employee and (optionally) an evaluation status, this method will return all the
     * Appraisals the employee has been a part of.
     *
     * @param employee Employee to evaluate.
     * @return Stream set of matching appraisals.
     */
    Stream<Appraisal> findEmployeeAppraisals(Employee employee);


    /**
     * Save an Entity instance of {@link Appraisal}
     *
     * @param appraisal Appraisal Entity to saveAndFlush
     */
    Optional<Appraisal> saveAndFlush(Appraisal appraisal);
}
