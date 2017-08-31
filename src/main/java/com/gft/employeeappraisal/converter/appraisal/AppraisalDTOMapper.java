package com.gft.employeeappraisal.converter.appraisal;

import com.gft.employeeappraisal.model.Appraisal;
import com.gft.swagger.employees.model.AppraisalDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Defines a mapping structure to be used by {@link AppraisalDTOConverter}.
 *
 * @author Manuel Yepez
 */
@Component
public class AppraisalDTOMapper extends CustomMapper<Appraisal, AppraisalDTO> {

    @Override
    public void mapAtoB(Appraisal appraisal, AppraisalDTO appraisalDTO, MappingContext context) {
        appraisalDTO.setId(appraisal.getId());
        appraisalDTO.setName(appraisal.getName());
        appraisalDTO.setDescription(appraisal.getDescription());
        appraisalDTO.setStartDate(appraisal.getStartDate());
        if (appraisal.getEndDate() != null) {
            appraisalDTO.setEndDate(appraisal.getEndDate());
        }
    }

    @Override
    public void mapBtoA(AppraisalDTO appraisalDTO, Appraisal appraisal, MappingContext context) {
        if (appraisalDTO.getId() > 0) {
            appraisal.setId(appraisalDTO.getId());
        }
        appraisal.setName(appraisalDTO.getName());
        appraisal.setDescription(appraisalDTO.getDescription());
        appraisal.setStartDate(appraisalDTO.getStartDate());
        if (appraisalDTO.getEndDate() != null) {
            appraisal.setEndDate(appraisalDTO.getEndDate());
        }
    }
}
