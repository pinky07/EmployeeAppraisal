package com.gft.employeeappraisal.builder.model;

import com.gft.employeeappraisal.builder.ObjectBuilder;
import com.gft.employeeappraisal.model.Appraisal;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

/**
 * Builder object for the {@link Appraisal} object.
 *
 * @author Ruben Jimenez
 */
public class AppraisalBuilder implements ObjectBuilder<Appraisal, Number> {

    private int id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public AppraisalBuilder id(int id) {
        this.id = id;
        return this;
    }

    public AppraisalBuilder name(String name) {
        this.name = name;
        return this;
    }

    public AppraisalBuilder description(String description) {
        this.description = description;
        return this;
    }

    public AppraisalBuilder startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public AppraisalBuilder endDate(LocalDateTime endDate) {
        this.endDate = endDate;
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
