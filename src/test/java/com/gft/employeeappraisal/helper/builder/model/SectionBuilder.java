package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.ScoreType;
import com.gft.employeeappraisal.model.Section;

/**
 * Builder object for the {@link Section} object.
 *
 * @author Manuel Yepez
 */
public class SectionBuilder implements ObjectBuilder<Section> {

    private int id;
    private ScoreType scoreType;
    private String name;
    private String description;
    private int position;

    private boolean idSet;
    private boolean scoreTypeSet;
    private boolean nameSet;
    private boolean descriptionSet;
    private boolean positionSet;

    public SectionBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public SectionBuilder scoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
        this.scoreTypeSet = true;
        return this;
    }

    public SectionBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public SectionBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    public SectionBuilder position(int position) {
        this.position = position;
        this.positionSet = true;
        return this;
    }

    @Override
    public Section build() {
        Section obj = new Section();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        obj.setScoreType(this.scoreType);
        obj.setPosition(this.position);
        return obj;
    }

    @Override
    public Section buildWithDefaults() {
        Section obj = new Section();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : "Name");
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        obj.setScoreType(this.scoreTypeSet ? this.scoreType : new ScoreTypeBuilder().buildWithDefaults());
        if (this.positionSet) obj.setPosition(this.position);
        return obj;
    }
}
