package com.gft.employeeappraisal.helper.builder.model;

import com.gft.employeeappraisal.helper.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Question;

/**
 * Builder object for the {@link Question} object.
 *
 * @author Manuel Yepez
 */
public class QuestionBuilder implements ObjectBuilder<Question> {

    private int id;
    private String name;
    private String description;
    private int position;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;
    private boolean positionSet;

    public QuestionBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public QuestionBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public QuestionBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    public QuestionBuilder position(int position) {
        this.position = position;
        this.positionSet = true;
        return this;
    }

    @Override
    public Question build() {
        Question obj = new Question();
        obj.setId(this.id);
        obj.setDescription(this.description);
        obj.setName(this.name);
        obj.setPosition(this.position);
        return obj;
    }

    @Override
    public Question buildWithDefaults() {
        Question obj = new Question();
        if (this.idSet) obj.setId(this.id);
        obj.setDescription(this.descriptionSet ? this.description : "Description?");
        obj.setName(this.nameSet ? this.name : "Name?");
        if (this.positionSet) obj.setPosition(this.position);
        return obj;
    }
}
