package com.a2r2.api.rest.model.serializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.a2r2.api.rest.model.UserAuthority;
import com.a2r2.api.rest.model.UserRole;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class UserAuthorityArrayJsonDeserializer extends JsonDeserializer<Set<UserAuthority>> {

	private static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityArrayJsonDeserializer.class.getName());

	@Override
	public Set<UserAuthority> deserialize(JsonParser jp, DeserializationContext deserializationcontext)
			throws IOException, JsonProcessingException {

		
		Set<UserAuthority> authorities = new HashSet<>();
		if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
			while (jp.getCurrentToken() != JsonToken.END_ARRAY) {
				JsonToken token = jp.nextToken();
				if (token == JsonToken.VALUE_STRING) {
					String role = jp.getText();

					UserRole userRole = UserRole.valueOf(role);
					UserAuthority authorityPM = new UserAuthority();
					authorityPM.setAuthority("ROLE_" + userRole.toString());
					authorities.add(authorityPM);
				}
			}
		} else {
			UserAuthority authorityPM = new UserAuthority();
			authorityPM.setAuthority("ROLE_" + UserRole.USER.toString());
			authorities.add(authorityPM);
		}

		return authorities;
	}

}
