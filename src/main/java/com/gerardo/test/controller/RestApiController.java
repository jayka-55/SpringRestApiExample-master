package com.gerardo.test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gerardo.test.model.User;
import com.gerardo.test.service.UserService;
import com.gerardo.test.service.UserServiceImpl;
import com.gerardo.test.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController implements ErrorController {
	
	@Autowired
	UserService userService;
	
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	
	/**
	 * Return all users
	 * @return
	 */
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	/**
	 * Return a single user
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		User user = userService.findById(id);
		if (user == null ) {
			return new ResponseEntity(new CustomErrorType("User with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	/**
	 * Create a user
	 * @param user
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);
		if (userService.isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			user.getName() + " already exist."), HttpStatus.CONFLICT);
		}
		
		if(user.getName() == null) {
			logger.error("Unable to create. name field is REQUIRED.");
			return new ResponseEntity(new CustomErrorType("Unable to create. name field is REQUIRED."), HttpStatus.CONFLICT);
		}
		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * Update a user.
	 * @param id
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Updating User with id {}", id);	
		User currentUser = userService.findById(id);
		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		
		if( user.getName() != null && user.getName().length() > 0 && !user.getName().equals(currentUser.getName()) ) {
			if (userService.isUserExist(user)) {
				logger.error("Unable to update. A User with name {} already exist", user.getName());
				return new ResponseEntity(new CustomErrorType("Unable to update. A User with name " + 
				user.getName() + " already exist."), HttpStatus.CONFLICT);
			}
			currentUser.setName(user.getName());
		}
		if(user.getPosition() != null) {
			currentUser.setPosition(user.getPosition());
		}
		currentUser.setSalary(user.getSalary());
		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	/**
	 * Delete a user
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	@Override
	public String getErrorPath() {
		return "End poind not found in this server.";
	}
	
}