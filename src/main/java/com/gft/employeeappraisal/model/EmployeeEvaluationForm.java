package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Entity persistent class that describes an EmployeeEvaluationForm table.
 *
 * @author Manuel Yepez
 */
@Entity
@Table(name = "EmployeeEvaluationForm")
public class EmployeeEvaluationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appraisalXEvaluationFormTemplateId", nullable = false)
    private AppraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filledByEmployeeId", nullable = false)
    private Employee filledByEmployee;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentorId", nullable = false)
    private Employee mentor;

    @NotNull
    @Column(name = "createDate", columnDefinition = "TIMESTAMP", nullable = false)
    private OffsetDateTime createDate;

    @NotNull
    @Column(name = "submitDate", columnDefinition = "TIMESTAMP", nullable = false)
    private OffsetDateTime submitDate;

    @OneToMany(mappedBy = "employeeEvaluationForm", fetch = FetchType.LAZY)
    private Set<EmployeeEvaluationFormAnswer> employeeEvaluationFormAnswerSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppraisalXEvaluationFormTemplate getAppraisalXEvaluationFormTemplate() {
        return appraisalXEvaluationFormTemplate;
    }

    public void setAppraisalXEvaluationFormTemplate(AppraisalXEvaluationFormTemplate appraisalXEvaluationFormTemplate) {
        this.appraisalXEvaluationFormTemplate = appraisalXEvaluationFormTemplate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getFilledByEmployee() {
        return filledByEmployee;
    }

    public void setFilledByEmployee(Employee filledByEmployee) {
        this.filledByEmployee = filledByEmployee;
    }

    public Employee getMentor() {
        return mentor;
    }

    public void setMentor(Employee mentor) {
        this.mentor = mentor;
    }

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public OffsetDateTime getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(OffsetDateTime submitDate) {
        this.submitDate = submitDate;
    }

    public Set<EmployeeEvaluationFormAnswer> getEmployeeEvaluationFormAnswerSet() {
        return employeeEvaluationFormAnswerSet;
    }

    public void setEmployeeEvaluationFormAnswerSet(Set<EmployeeEvaluationFormAnswer> employeeEvaluationFormAnswerSet) {
        this.employeeEvaluationFormAnswerSet = employeeEvaluationFormAnswerSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeEvaluationForm that = (EmployeeEvaluationForm) o;

        return id == that.id &&
                employee == that.employee &&
                filledByEmployee == that.filledByEmployee &&
                mentor == that.mentor &&
                createDate.equals(that.createDate) &&
                submitDate.equals(that.submitDate);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + employee.hashCode();
        result = 31 * result + filledByEmployee.hashCode();
        result = 31 * result + mentor.hashCode();
        result = 31 * result + createDate.hashCode();
        result = 31 * result + submitDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeEvaluationForm{" +
                "id=" + id +
                ", employee=" + employee +
                ", filledByEmployee=" + filledByEmployee +
                ", mentor=" + mentor +
                ", createDate=" + createDate +
                ", submitDate=" + submitDate +
                '}';
    }
}
