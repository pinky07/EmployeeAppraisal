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
    @JoinColumn(name = "appraisalXEvaluationFormId", nullable = false)
    private AppraisalXEvaluationForm appraisalXEvaluationForm;

    // These fields will not be joined to the Employee table?
    @NotNull
    @Column(name = "employeeId", nullable = false)
    private int employeeId;

    @NotNull
    @Column(name = "filledByEmployeeId", nullable = false)
    private int filledByEmployeeId;

    @NotNull
    @Column(name = "mentorId", nullable = false)
    private int mentorId;

    @NotNull
    @Column(name = "createDate", columnDefinition = "TIMESTAMP", nullable = false)
    private OffsetDateTime createDate;

    @NotNull
    @Column(name = "submitDate", columnDefinition = "TIMESTAMP", nullable = false)
    private OffsetDateTime submitDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    @OneToMany(mappedBy = "employeeEvaluationForm", fetch = FetchType.LAZY)
    private Set<EmployeeEvaluationFormAnswer> employeeEvaluationFormAnswers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppraisalXEvaluationForm getAppraisalXEvaluationForm() {
        return appraisalXEvaluationForm;
    }

    public void setAppraisalXEvaluationForm(AppraisalXEvaluationForm appraisalXEvaluationForm) {
        this.appraisalXEvaluationForm = appraisalXEvaluationForm;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getFilledByEmployeeId() {
        return filledByEmployeeId;
    }

    public void setFilledByEmployeeId(int filledByEmployeeId) {
        this.filledByEmployeeId = filledByEmployeeId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
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

    public EvaluationStatus getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(EvaluationStatus evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public Set<EmployeeEvaluationFormAnswer> getEmployeeEvaluationFormAnswers() {
        return employeeEvaluationFormAnswers;
    }

    public void setEmployeeEvaluationFormAnswers(
            Set<EmployeeEvaluationFormAnswer> employeeEvaluationFormAnswers) {
        this.employeeEvaluationFormAnswers = employeeEvaluationFormAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeEvaluationForm that = (EmployeeEvaluationForm) o;

        return id == that.id &&
                employeeId == that.employeeId &&
                filledByEmployeeId == that.filledByEmployeeId &&
                mentorId == that.mentorId &&
                createDate.equals(that.createDate) &&
                submitDate.equals(that.submitDate) &&
                evaluationStatus == that.evaluationStatus;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + employeeId;
        result = 31 * result + filledByEmployeeId;
        result = 31 * result + mentorId;
        result = 31 * result + createDate.hashCode();
        result = 31 * result + submitDate.hashCode();
        result = 31 * result + evaluationStatus.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeEvaluationForm{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", filledByEmployeeId=" + filledByEmployeeId +
                ", mentorId=" + mentorId +
                ", createDate=" + createDate +
                ", submitDate=" + submitDate +
                ", evaluationStatus=" + evaluationStatus +
                '}';
    }
}
