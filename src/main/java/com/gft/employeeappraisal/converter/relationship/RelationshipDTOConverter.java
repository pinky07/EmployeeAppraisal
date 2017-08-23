package com.gft.employeeappraisal.converter.relationship;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.swagger.employees.model.RelationshipDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between Relationship and RelationshipDTO.
 *
 * @author Manuel Yepez
 */
@Component
public class RelationshipDTOConverter extends EntityDTOConverter<Relationship, RelationshipDTO> {

    @Autowired
    public RelationshipDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(Relationship.class, RelationshipDTO.class);
    }
}
