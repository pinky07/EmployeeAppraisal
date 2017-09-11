package com.gft.employeeappraisal.converter.relationshiptype;

import com.gft.employeeappraisal.converter.EntityDTOConverter;
import com.gft.employeeappraisal.model.RelationshipType;
import com.gft.swagger.employees.model.RelationshipTypeDTO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter logic for bidirectional transformation between {@link RelationshipType} and {@link RelationshipTypeDTO}.
 *
 * @author Manuel Yepez
 */
@Component
public class RelationshipTypeDTOConverter extends EntityDTOConverter<RelationshipType, RelationshipTypeDTO> {

    @Autowired
    public RelationshipTypeDTOConverter(MapperFactory mapperFactory) {
        this.boundMapper = mapperFactory.getMapperFacade(RelationshipType.class, RelationshipTypeDTO.class);
    }
}
