package com.gft.employeeappraisal.converter.employeerelationship;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between {@link EmployeeRelationship} and {@link EmployeeRelationshipDTO}.
 *
 * @author Manuel Yepez
 */
@Component
public class EmployeeRelationshipDTOConverter extends EntityDTOConverter<EmployeeRelationship, EmployeeRelationshipDTO> {

    @Autowired
    public EmployeeRelationshipDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(EmployeeRelationship.class, EmployeeRelationshipDTO.class);
    }
}
