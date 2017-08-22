package com.gft.employeeappraisal.model;

/**
 * Lookup enum for ApplicationRoles.
 *
 * @author Manuel Yepez
 */
public enum ApplicationRoleName {
    ADMIN(1),
    USER(2);

    private final int id;

    ApplicationRoleName(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }
}
