package com.a2r2.api.rest.model.serializer;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.a2r2.api.rest.model.UserAuthority;
import com.a2r2.api.rest.model.UserRole;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class UserAuthorityArrayJsonSerializer extends JsonSerializer<Set<UserAuthority>> {

	private static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityArrayJsonSerializer.class.getName());

	@Override
	public void serialize(Set<UserAuthority> value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

    	jgen.writeStartArray();
        if (!value.isEmpty()) {
        	for (UserAuthority userAuthority : value) {
        		String auth = UserRole.valueOf(userAuthority).toString();
        		LOGGER.debug("Add authority::"  + auth);
				jgen.writeString(auth);
        	}
        }
    	jgen.writeEndArray();

	}

}