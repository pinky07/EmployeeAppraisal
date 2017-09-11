package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;


/**
 * Builder object for the {@link EvaluationFormTemplate} object.
 *
 * @author Ruben Jimenez
 */
public class EvaluationFormBuilder implements ObjectBuilder<EvaluationFormTemplate> {

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
    public EvaluationFormTemplate build() {
        EvaluationFormTemplate obj = new EvaluationFormTemplate();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        return obj;
    }

    @Override
    public EvaluationFormTemplate buildWithDefaults() {
        EvaluationFormTemplate obj = new EvaluationFormTemplate();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : "Name");
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        return obj;
    }
}
