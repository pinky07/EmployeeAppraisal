package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.ScoreType;
import com.gft.employeeappraisal.model.ScoreValue;

/**
 * Builder object for the {@link ScoreValue} object.
 *
 * @author Manuel Yepez
 */
public class ScoreValueBuilder implements ObjectBuilder<ScoreValue> {

    private int id;
    private ScoreType scoreType;
    private String value;
    private String description;

    private boolean idSet;
    private boolean valueSet;
    private boolean descriptionSet;

    public ScoreValueBuilder(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

    public ScoreValueBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public ScoreValueBuilder value(String value) {
        this.value = value;
        this.valueSet = true;
        return this;
    }

    public ScoreValueBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    @Override
    public ScoreValue build() {
        ScoreValue obj = new ScoreValue();
        obj.setId(this.id);
        obj.setDescription(this.description);
        obj.setValue(this.value);
        obj.setScoreType(this.scoreType);
        return obj;
    }

    @Override
    public ScoreValue buildWithDefaults() {
        ScoreValue obj = new ScoreValue();
        if (this.idSet) obj.setId(this.id);
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        obj.setValue(this.valueSet ? this.value : "Value");
        obj.setScoreType(this.scoreType);
        return obj;
    }
}
