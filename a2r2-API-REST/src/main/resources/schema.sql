CREATE TABLE oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove tinyint
);

CREATE TABLE oauth_client_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

CREATE TABLE oauth_access_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication BLOB,
  refresh_token VARCHAR(255)
);

CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication BLOB
);

CREATE TABLE oauth_code (
  code VARCHAR(255), authentication BLOB
);


CREATE TABLE `users` (
  `username` VARCHAR(90) NOT NULL,
  `accountExpired` bit(1) NOT NULL,
  `accountLocked` bit(1) NOT NULL,
  `avatarUrl` VARCHAR(255) DEFAULT NULL,
  `credentialsExpired` bit(1) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` VARCHAR(90) NOT NULL,
  `password` VARCHAR(90) NOT NULL,
  `surname` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`username`)
);

CREATE TABLE `authorities` (
  `authority` VARCHAR(15) NOT NULL,
  `username` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`username`,`authority`),
  CONSTRAINT `FK_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
);


INSERT INTO oauth_client_details 
(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`authorities`,`access_token_validity`,`refresh_token_validity`) 
VALUES ('my-trusted-client','my_rest_api','secret','read,write,trust','password,authorization_code,refresh_token,implicit','ROLE_CLIENT,ROLE_TRUSTED_CLIENT',120,600);

INSERT INTO oauth_client_details 
(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`authorities`,`access_token_validity`,`refresh_token_validity`) 
VALUES ('my-android-client','chatbot','secret','read,write,trust','password,authorization_code,refresh_token,implicit','ROLE_CLIENT,ROLE_TRUSTED_CLIENT',120,600);


INSERT INTO `a2r2`.`users`(`enabled`,`accountExpired`,`accountLocked`,`credentialsExpired`,`name`,`password`,`surname`,`username`)
VALUES(true,false,false,false,'Jude','123456','Jude','jude');

INSERT INTO `a2r2`.`users`(`enabled`,`accountExpired`,`accountLocked`,`credentialsExpired`,`name`,`password`,`surname`,`username`)
VALUES(true,false,false,false,'Seba','123456','Seba','seba');


INSERT INTO authorities (username, authority) VALUES ('jude', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('jude', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('seba', 'ROLE_USER');