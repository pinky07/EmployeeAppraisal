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

    private static int currentId = 1_000_000;

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
        Appraisal appraisal = new Appraisal();
        appraisal.setId(this.id);
        appraisal.setName(this.name);
        appraisal.setDescription(this.description);
        appraisal.setStartDate(this.startDate);
        appraisal.setEndDate(this.endDate);
        return appraisal;
    }

    @Override
    public Appraisal buildWithDefaults() {
        Appraisal appraisal = new Appraisal();
        appraisal.setId(this.idSet ? this.id : currentId++);
        appraisal.setName(this.nameSet ? this.name : "Name");
        appraisal.setDescription(this.descriptionSet ? this.description : "Description");
        appraisal.setStartDate(this.startDateSet ? this.startDate : LocalDateTime.now().minusDays(1));
        appraisal.setEndDate(this.endDateSet ? this.endDate : LocalDateTime.now().plusDays(1));
        return appraisal;
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
