package com.gft.employeeappraisal.converter.relationship;

import com.gft.employeeappraisal.model.Relationship;
import com.gft.swagger.employees.model.RelationshipDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Defines a mapping structure to be used by {@link RelationshipDTOConverter}.
 *
 * @author Manuel Yepez
 */
@Component
public class RelationshipDTOMapper extends CustomMapper<Relationship, RelationshipDTO> {

    @Override
    public void mapAtoB(Relationship relationship, RelationshipDTO relationshipDTO, MappingContext context) {
        relationshipDTO.setId(relationship.getId());
        relationshipDTO.setName(relationship.getName());
        relationshipDTO.setDescription(relationship.getDescription());
    }

    @Override
    public void mapBtoA(RelationshipDTO relationshipDTO, Relationship relationship, MappingContext context) {
        if (relationshipDTO.getId() > 0) {
            relationship.setId(relationshipDTO.getId());
        }
        relationship.setName(relationshipDTO.getName());
        relationship.setDescription(relationshipDTO.getDescription());
    }
}
