package com.a2r2.api.rest.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2r2.api.rest.model.User;
import com.a2r2.api.rest.persistence.IUserRepository;
import com.a2r2.api.rest.service.UserService;
import com.google.common.base.Preconditions;

/**
 * Simple Service to manage User entity
 * 
 * @author sstjerne
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getName());

	@Autowired
	private IUserRepository userRepository;

	@Override
	public User findOne(String username) {
		return userRepository.findOne(username);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User create(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public User update(String username, User entity) {
		Preconditions.checkNotNull(entity);
		
		User user = userRepository.findOne(username);
		if (user != null) {
			return null;
		}		
		entity = userRepository.save(entity);
		return entity;
	}

	@Override
	public void delete(String username) {
		userRepository.delete(username);
	}

}
