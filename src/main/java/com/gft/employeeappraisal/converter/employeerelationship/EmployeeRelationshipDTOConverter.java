package com.gft.employeeappraisal.converter.employeerelationship;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.EmployeeRelationship;
import com.gft.swagger.employees.model.EmployeeRelationshipDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter init class for bidirectional transformation between EmployeeRelationship and EmployeeRelationshipDTO.
 * Using a BoundMapper increases performance according to Orika's authors.
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
