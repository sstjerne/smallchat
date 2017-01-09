package com.a2r2.api.rest.test;

import java.util.HashSet;
import java.util.UUID;

import com.a2r2.api.rest.model.User;
import com.a2r2.api.rest.model.UserAuthority;

public class UserBuilder {

	public static User create() {
		User resource = new User();
		String prefix = UUID.randomUUID().toString();

		resource.setUsername("username_" + prefix);
		resource.setPassword("password_" + prefix);
		resource.setName("name_" + prefix);
		resource.setSurname("surname_" + prefix);
		resource.setAuthorities(new HashSet<>());

		resource.getAuthorities().add(new UserAuthority("ADMIN"));
		resource.getAuthorities().add(new UserAuthority("USER"));
		return resource;
	}
	
	public static User create(String username) {

		User resource = new User();
		String prefix = UUID.randomUUID().toString();

		resource.setUsername(username);
		resource.setPassword("password_" + prefix);
		resource.setName("name_" + prefix);
		resource.setSurname("surname_" + prefix);
		resource.setAuthorities(new HashSet<>());
		resource.getAuthorities().add(new UserAuthority("ADMIN"));
		resource.getAuthorities().add(new UserAuthority("USER"));

		return resource;
	
	}
}
