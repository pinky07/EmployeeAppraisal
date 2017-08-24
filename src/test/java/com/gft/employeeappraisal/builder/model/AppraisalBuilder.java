package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Appraisal;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link Appraisal} object.
 *
 * @author Manuel Yepez
 */
public class AppraisalBuilder implements ObjectBuilder<Appraisal> {

    private int id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private boolean idSet;
    private boolean nameSet;
    private boolean descriptionSet;
    private boolean startDateSet;
    private boolean endDateSet;

    public AppraisalBuilder() {
    }

    public AppraisalBuilder id(int id) {
        this.id = id;
        this.idSet = true;
        return this;
    }

    public AppraisalBuilder name(String name) {
        this.name = name;
        this.nameSet = true;
        return this;
    }

    public AppraisalBuilder description(String description) {
        this.description = description;
        this.descriptionSet = true;
        return this;
    }

    public AppraisalBuilder startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        this.startDateSet = true;
        return this;
    }

    public AppraisalBuilder endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        this.endDateSet = true;
        return this;
    }

    @Override
    public Appraisal build() {
        Appraisal obj = new Appraisal();
        obj.setId(this.id);
        obj.setName(this.name);
        obj.setDescription(this.description);
        obj.setStartDate(this.startDate);
        obj.setEndDate(this.endDate);
        return obj;
    }

    @Override
    public Appraisal buildWithDefaults() {
        Appraisal obj = new Appraisal();
        if (this.idSet) obj.setId(this.id);
        obj.setName(this.nameSet ? this.name : "Name");
        obj.setDescription(this.descriptionSet ? this.description : "Description");
        obj.setStartDate(this.startDateSet ? this.startDate : LocalDateTime.now().minusDays(1));
        obj.setEndDate(this.endDateSet ? this.endDate : LocalDateTime.now().plusDays(1));
        return obj;
    }

    @Override
    public Appraisal buildMock() {
        Appraisal mock = Mockito.mock(Appraisal.class);
        when(mock.getId()).thenReturn(this.id);
        when(mock.getName()).thenReturn(this.name);
        when(mock.getDescription()).thenReturn(this.description);
        when(mock.getStartDate()).thenReturn(this.startDate);
        when(mock.getEndDate()).thenReturn(this.endDate);
        return mock;
    }
}
