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

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;

    public EvaluationFormBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public EvaluationFormBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public EvaluationFormBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    @Override
    public EvaluationForm build() {
        EvaluationForm obj = new EvaluationForm();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        return obj;
    }

    @Override
    public EvaluationForm buildWithDefaults() {
        EvaluationForm obj = new EvaluationForm();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : "Name");
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        return obj;
    }

    @Override
    public EvaluationForm buildMock() {
        EvaluationForm mock = Mockito.mock(EvaluationForm.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getName()).thenReturn(this.name);
        when(mock.getDescription()).thenReturn(this.description);
        return mock;
    }
}
