package com.gft.employeeappraisal.service;

import com.gft.employeeappraisal.model.Relationship;
import com.gft.employeeappraisal.model.RelationshipName;
import com.gft.employeeappraisal.repository.RelationshipRepository;
import com.gft.employeeappraisal.service.impl.RelationshipServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Service layer test for {@link RelationshipService}
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
@RunWith(SpringRunner.class)
@DataJpaTest()
public class RelationshipServiceTest {

    @Autowired
    private RelationshipRepository relationshipRepository;

    // Class under test
    private RelationshipService relationshipService;

    @Before
    public void setUp() throws Exception {
        this.relationshipService = new RelationshipServiceImpl(
                this.relationshipRepository);
    }

    @Test
    public void findById() throws Exception {
        Relationship relationship = relationshipService.findById(RelationshipName.OTHER.getId()).get();
        assertNotNull(relationship);
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
}
