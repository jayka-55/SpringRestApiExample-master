package com.gerardo.test.service;


import java.util.List;

import com.gerardo.test.model.User;

/**
 * 
 * User actions interface
 *
 */
public interface UserService {
	
	User findById(long id);
	
	User findByName(String name);
	
	boolean saveUser(User user);
	
	boolean updateUser(User user);
	
	boolean deleteUserById(long id);

	List<User> findAllUsers();
	
	boolean isUserExist(User user);
	
}
