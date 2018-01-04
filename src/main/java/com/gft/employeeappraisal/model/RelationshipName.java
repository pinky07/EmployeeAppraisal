package com.gft.employeeappraisal.model;

/**
 * Lookup enum for Relationships.
 *
 * @author Manuel Yepez
 */
public enum RelationshipName {

    // NOTE: Always ensure that the Ids in this class, correspond to the ones in the database
    // for each relationship value

    SELF(1),
    PEER(2),
    MENTOR(3),
    LEAD(4),
    OTHER(5),
    TEAM_MEMBER(6),
    EXTERNAL(7);

    private final int id;

    RelationshipName(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }
}
