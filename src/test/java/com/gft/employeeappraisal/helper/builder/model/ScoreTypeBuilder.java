package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.ScoreType;
import com.gft.employeeappraisal.model.ScoreValue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Builder object for the {@link ScoreType} object.
 *
 * @author Manuel Yepez
 */
public class ScoreTypeBuilder implements ObjectBuilder<ScoreType> {

    private int id;
    private String definition;
    private Set<ScoreValue> scoreValueSet;

    private boolean idSet;
    private boolean definitionSet;
    private boolean scoreValuesSet;

    public ScoreTypeBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public ScoreTypeBuilder definition(String definition) {
        this.definition = definition;
        this.definitionSet = true;
        return this;
    }

    public ScoreTypeBuilder scoreValues(Set<ScoreValue> scoreValues){
        this.scoreValueSet = scoreValues;
        this.scoreValuesSet = true;
        return this;
    }

    @Override
    public ScoreType build() {
        ScoreType obj = new ScoreType();
        obj.setId(this.id);
        obj.setDefinition(this.definition);
        obj.setScoreValueSet(this.scoreValueSet);
        return obj;
    }

    @Override
    public ScoreType buildWithDefaults() {
        ScoreType obj = new ScoreType();
        if (this.idSet) obj.setId(this.id);
        obj.setDefinition(this.definitionSet ? this.definition : "Definition");
        obj.setScoreValueSet(this.scoreValuesSet ? this.scoreValueSet :
                new HashSet<>(Collections.singletonList(new ScoreValueBuilder(obj).buildWithDefaults())));
        return obj;
    }
}
