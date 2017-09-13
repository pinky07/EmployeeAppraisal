package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface for methods interacting with the EmployeeEvaluationForm repository.
 *
 * @author Rubén Jiménez
 * @author Manuel Yepez
 */
public interface EmployeeEvaluationFormService {

    /**
     * Looks up an {@link EmployeeEvaluationForm} entity with the given Id.
     *
     * @param id Internal lookup Id for the {@link EmployeeEvaluationForm}
     * @return An Optional object that may or not contain the {@link EmployeeEvaluationForm} entity.
     */
    Optional<EmployeeEvaluationForm> findById(int id);

    /**
     * Looks for all {@link EmployeeEvaluationForm} for a given Employee during any {@link Appraisal} process.
     *
     * @param employee {@link Employee} evaluated
     * @return List of EmployeeEvaluationForm for the given {@link Employee}
     */
    Stream<EmployeeEvaluationForm> findByEmployee(
            Employee employee);

    /**
     * Looks for all {@link EmployeeEvaluationForm} for a given Employee during any {@link Appraisal} process.
     * Returns only the {@link EmployeeEvaluationForm} that needs to be filled by the {@link Employee}.
     *
     * @param employee {@link Employee} evaluated
     * @return List of EmployeeEvaluationForm for the given {@link Employee}
     */
    Stream<EmployeeEvaluationForm> findSelfByEmployee(
            Employee employee);

    /**
     * Looks for all {@link EmployeeEvaluationForm} for a given Employee during a specific {@link Appraisal} process.
     * Returns only the {@link EmployeeEvaluationForm} that needs to be filled by the {@link Employee}.
     *
     * @param employee  {@link Employee} being evaluated
     * @param appraisal Specific {@link Appraisal} process
     * @return List of EmployeeEvaluationForm for the given {@link Employee}
     */
    Optional<EmployeeEvaluationForm> findSelfByEmployeeAndAppraisal(
            Employee employee,
            Appraisal appraisal);

    /**
     * Looks for all {@link EmployeeEvaluationForm} for a given Employee during a specific {@link Appraisal} process.
     * Returns all the {@link EmployeeEvaluationForm} that need to be filled by the {@link Employee}, his references and
     * mentor.
     *
     * @param employee  {@link Employee} being evaluated
     * @param appraisal Specific {@link Appraisal} process
     * @return List of EmployeeEvaluationForm for the given {@link Employee}
     */
    Stream<EmployeeEvaluationForm> findByEmployeeAndAppraisal(
            Employee employee,
            Appraisal appraisal);

    /**
     * Looks for all {@link EmployeeEvaluationForm} for a given Employee during a specific {@link Appraisal} process.
     *
     * @param employee  {@link Employee} being evaluated
     * @param appraisal Specific {@link Appraisal} process
     * @return List of EmployeeEvaluationForm for the given {@link Employee}
     */
    Stream<EmployeeEvaluationForm> findByFilledByEmployeeAndAppraisal(
            Employee employee,
            Appraisal appraisal);

    /**
     * Looks for all {@link EmployeeEvaluationForm} for a given Employee during a specific {@link Appraisal} process.
     *
     * @param mentor  {@link Employee} Mentor who filled the form
     * @param appraisal Specific {@link Appraisal} process
     * @return List of EmployeeEvaluationForm for the given {@link Employee}
     */
    Stream<EmployeeEvaluationForm> findByMentorAndAppraisal(
            Employee mentor,
            Appraisal appraisal);


    /**
     * Save an Entity instance of {@link EmployeeEvaluationForm}
     *
     * @param employeeEvaluationForm Appraisal Entity to saveAndFlush
     */
    Optional<EmployeeEvaluationForm> saveAndFlush(EmployeeEvaluationForm employeeEvaluationForm);
}
