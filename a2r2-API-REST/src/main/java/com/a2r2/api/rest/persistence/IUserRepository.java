package com.a2r2.api.rest.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.a2r2.api.rest.model.User;


@Repository(value= "userRepository")
public interface IUserRepository extends JpaRepository<User, String>,
		JpaSpecificationExecutor<User> {


}
