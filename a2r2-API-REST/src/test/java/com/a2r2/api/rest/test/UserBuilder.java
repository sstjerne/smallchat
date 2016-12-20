package com.a2r2.api.rest.test;

import java.util.Date;
import java.util.UUID;

import com.a2r2.api.rest.model.User;

public class UserBuilder {

	public static User create() {
		User resource = new User();
		String prefix = UUID.randomUUID().toString();

		resource.setUsername("username_" + prefix);
		resource.setPassword("password_" + prefix);
		// resource.setProfile(randomAlphabetic(6));
		resource.setName("name_" + prefix);
		resource.setSurname("surname_" + prefix);
		return resource;
	}
	
	public static User create(Date date) {

		User resource = new User();
		String prefix = UUID.randomUUID().toString();

		resource.setUsername("username_" + prefix);
		resource.setPassword("password_" + prefix);
		// resource.setProfile(randomAlphabetic(6));
		resource.setName("name_" + prefix);
		resource.setSurname("surname_" + prefix);
		resource.setExpires(date.getTime());
		return resource;
	
	}
}