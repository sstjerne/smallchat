package com.a2r2.api.rest.model;

public enum UserRole {
	USER, OWNER, ADMIN;

	public UserAuthority asAuthorityFor(final User user) {
		final UserAuthority authority = new UserAuthority();
		authority.setAuthority("ROLE_" + toString());
		authority.setUser(user);
		return authority;
	}

	public static UserRole valueOf(final UserAuthority authority) {
		switch (authority.getAuthority()) {
		case "ROLE_USER":
				return USER;
		case "ROLE_ADMIN":
				return ADMIN;			

		}
		throw new IllegalArgumentException("No role defined for authority: " + authority.getAuthority());
	}
}