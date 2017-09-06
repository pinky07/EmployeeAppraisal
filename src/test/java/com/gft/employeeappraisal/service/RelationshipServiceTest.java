package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.service.impl.RelationshipServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Service layer test for {@link RelationshipService}
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@RunWith(SpringRunner.class)
public class RelationshipServiceTest extends BaseServiceTest {

    // Class under test
    private RelationshipService relationshipService;

    @Before
    public void setUp() throws Exception {
        this.relationshipService = new RelationshipServiceImpl(
                this.relationshipRepository);
    }

    @Test
    public void getById() throws Exception {
        Relationship relationship = relationshipService.getById(RelationshipName.PEER.getId());
        assertNotNull(relationship);
    }

    @Test(expected = NotFoundException.class)
    public void getById_InvalidRelationshipId() throws Exception {
        relationshipService.getById(-100);
    }

    @Test
    public void findById() throws Exception {
        Optional<Relationship> relationship = relationshipService.findById(RelationshipName.OTHER.getId());
        assertTrue(relationship.isPresent());
    }

    @Test
    public void findById_invalid() throws Exception {
        assertEquals(Optional.empty(), relationshipService.findById(-100));
    }

    @Test
    public void findByName() throws Exception {
        Relationship relationship = relationshipService.findByName(RelationshipName.OTHER);
        assertNotNull(relationship);
    }

    @Test
    public void findByNames() throws Exception {
        List<Relationship> relationships = relationshipService
                .findRelationshipsByNames(RelationshipName.SELF, RelationshipName.OTHER)
                .collect(Collectors.toList());

        assertTrue(relationships.size() == 2);
        assertTrue(relationships.contains(relationshipService.findByName(RelationshipName.OTHER)));
        assertTrue(relationships.contains(relationshipService.findByName(RelationshipName.SELF)));
    }
}
