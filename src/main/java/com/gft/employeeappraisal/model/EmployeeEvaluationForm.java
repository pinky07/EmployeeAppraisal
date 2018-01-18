package com.gft.employeeappraisal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.HashSet;
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
    @JoinColumn(name = "appraisalId", nullable = false)
    private Appraisal appraisal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationFormTemplateId", nullable = false)
    private EvaluationFormTemplate evaluationFormTemplate;

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

    @OneToMany(mappedBy = "employeeEvaluationForm", fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
    private Set<EmployeeEvaluationFormAnswer> employeeEvaluationFormAnswerSet;

    @Size(max = 1000)
    @Column(name = "comments", length = 1000)
    private String comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Appraisal getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(Appraisal appraisal) {
        this.appraisal = appraisal;
    }

    public EvaluationFormTemplate getEvaluationFormTemplate() {
        return evaluationFormTemplate;
    }

    public void setEvaluationFormTemplate(EvaluationFormTemplate evaluationFormTemplate) {
        this.evaluationFormTemplate = evaluationFormTemplate;
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

    /**
     * Add a <code>EmployeeEvaluationFormAnswer</code> to the internal set of answers.
     * @param employeeEvaluationFormAnswer The <code>EmployeeEvaluationFormAnswer</code> to be added.
     */
    public void addEmployeeEvaluationFormAnswer(EmployeeEvaluationFormAnswer employeeEvaluationFormAnswer){
        if(null == this.employeeEvaluationFormAnswerSet){
            this.employeeEvaluationFormAnswerSet = new HashSet<>();
        }
        this.employeeEvaluationFormAnswerSet.add(employeeEvaluationFormAnswer);
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeEvaluationForm that = (EmployeeEvaluationForm) o;

        return id == that.id && (appraisal != null ? appraisal.equals(that.appraisal) : that.appraisal == null) &&
                (evaluationFormTemplate != null ? evaluationFormTemplate.equals(that.evaluationFormTemplate) : that.evaluationFormTemplate == null) &&
                (employee != null ? employee.equals(that.employee) : that.employee == null) &&
                (filledByEmployee != null ? filledByEmployee.equals(that.filledByEmployee) : that.filledByEmployee == null) &&
                (mentor != null ? mentor.equals(that.mentor) : that.mentor == null) &&
                (createDate != null ? createDate.equals(that.createDate) : that.createDate == null) &&
                (submitDate != null ? submitDate.equals(that.submitDate) : that.submitDate == null) &&
                (comments != null ? comments.equals(that.comments) : that.comments == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (appraisal != null ? appraisal.hashCode() : 0);
        result = 31 * result + (evaluationFormTemplate != null ? evaluationFormTemplate.hashCode() : 0);
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        result = 31 * result + (filledByEmployee != null ? filledByEmployee.hashCode() : 0);
        result = 31 * result + (mentor != null ? mentor.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (submitDate != null ? submitDate.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
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
                ",comments="+comments+
                '}';
    }
}
