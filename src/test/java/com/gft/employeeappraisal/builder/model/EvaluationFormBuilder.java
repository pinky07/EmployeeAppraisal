package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.EvaluationForm;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;


/**
 * Builder object for the {@link EvaluationForm} object.
 *
 * @author Ruben Jimenez
 */
public class EvaluationFormBuilder implements ObjectBuilder<EvaluationForm> {

    private int id;
    private String name;
    private String description;

    public EvaluationFormBuilder id(int id) {
        this.id = id;
        return this;
    }

    public EvaluationFormBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EvaluationFormBuilder description(String description) {
        this.description = description;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvaluationForm build() {
        EvaluationForm evaluationForm = new EvaluationForm();
        evaluationForm.setId(this.id);
        evaluationForm.setName(this.name);
        evaluationForm.setDescription(this.description);
        return evaluationForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvaluationForm buildMock() {
        EvaluationForm mock = Mockito.mock(EvaluationForm.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getName()).thenReturn(this.name);
        when(mock.getDescription()).thenReturn(this.description);
        return mock;
    }
}
