package com.gft.employeeappraisal.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Entity persistent class that describes an EvaluationForm table.
 *
 * @author Ruben Jimenez
 */
@Entity
@Table(name = "EvaluationForm")
public class EvaluationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(max = 40)
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @NotEmpty
    @Size(max = 500)
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @OneToMany(mappedBy = "evaluationForm", fetch = FetchType.LAZY)
	private Set<AppraisalXEvaluationForm> appraisalXEvaluationForms;

	@OneToMany(mappedBy = "evaluationForm", fetch = FetchType.LAZY)
	private Set<EvaluationFormXJobLevel> evaluationFormXJobLevels;

	@OneToMany(mappedBy = "evaluationForm", fetch = FetchType.LAZY)
	private Set<EvaluationFormXSectionXQuestion> evaluationFormXSectionXQuestions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Set<AppraisalXEvaluationForm> getAppraisalXEvaluationForms() {
		return appraisalXEvaluationForms;
	}

	public void setAppraisalXEvaluationForms(Set<AppraisalXEvaluationForm> appraisalXEvaluationForms) {
		this.appraisalXEvaluationForms = appraisalXEvaluationForms;
	}

	public Set<EvaluationFormXJobLevel> getEvaluationFormXJobLevels() {
		return evaluationFormXJobLevels;
	}

	public void setEvaluationFormXJobLevels(Set<EvaluationFormXJobLevel> evaluationFormXJobLevels) {
		this.evaluationFormXJobLevels = evaluationFormXJobLevels;
	}

	public Set<EvaluationFormXSectionXQuestion> getEvaluationFormXSectionXQuestions() {
		return evaluationFormXSectionXQuestions;
	}

	public void setEvaluationFormXSectionXQuestions(Set<EvaluationFormXSectionXQuestion> evaluationFormXSectionXQuestions) {
		this.evaluationFormXSectionXQuestions = evaluationFormXSectionXQuestions;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluationForm that = (EvaluationForm) o;

        return getId() == that.getId() &&
                getName().equals(that.getName()) &&
                getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "EvaluationForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
