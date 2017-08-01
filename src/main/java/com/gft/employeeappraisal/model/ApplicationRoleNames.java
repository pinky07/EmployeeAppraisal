package com.gft.employeeappraisal.model;

/**
 * Lookup enum for ApplicationRoles.
 *
 * @author Manuel Yepez
 */
public enum ApplicationRoleNames {
	ADMIN(1),
	USER(2);

	private final int id;

	ApplicationRoleNames(int i) {
		this.id = i;
	}

	public int getId() {
		return this.id;
	}
}
