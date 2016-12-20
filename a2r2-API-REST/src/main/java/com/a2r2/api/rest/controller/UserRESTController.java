package com.a2r2.api.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.a2r2.api.rest.model.User;
import com.a2r2.api.rest.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserRESTController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRESTController.class.getName());

	@Autowired
	private UserService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		LOGGER.debug("Entering listAllUsers");
		List<User> users = service.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		LOGGER.debug("Fetching User with id " + username);
		User user = service.findOne(username);
		if (user == null) {
			LOGGER.debug("User with id " + username + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}


	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		LOGGER.debug("Creating User " + user.getName());

		if (service.findOne(user.getName()) != null) {
			LOGGER.debug("A User with name " + user.getName() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		service.create(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{username}").buildAndExpand(user.getUsername()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}


	@RequestMapping(value = "/{username}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("username") String username, @RequestBody User user) {
		LOGGER.debug("Updating User " + username);

		user = service.update(username,user);
		
		if (user == null) {
			LOGGER.debug("User with username " + username + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}


	@RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("username") String username) {
		LOGGER.debug("Fetching & Deleting User with id " + username);

		User user = service.findOne(username);
		if (user == null) {
			LOGGER.debug("Unable to delete. User with id " + username + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		service.delete(username);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

}