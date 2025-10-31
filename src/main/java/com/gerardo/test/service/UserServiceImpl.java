package com.gerardo.test.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gerardo.test.model.User;
import com.gerardo.test.model.UserDao;

/**
 * 
 * User actions implementations
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	private ApplicationContext context;
	private UserDao usrDao;
	
	@Autowired
	public void context(ApplicationContext context) { 
		this.context = context; 
		usrDao = context.getBean(UserDao.class);
	}
	
	public List<User> findAllUsers() {
		return usrDao.findAllUsers();
	}
	
	public User findById(long id) {
		return usrDao.findUser(id);
	}
	
	public User findByName(String name) {
		return usrDao.findByName(name);
	}
	
	public boolean saveUser(User user) {
		if(usrDao.saveUser(user) > 0) {
			User newUser = findByName(user.getName());
			user.setId(newUser.getId());
			return true;
		}
		return false;
	}

	public boolean updateUser(User user) {
		return (usrDao.updateUser(user) > 0);
	}

	public boolean deleteUserById(long id) {
		return (usrDao.deleteUser(id) > 0);
	}

	public boolean isUserExist(User user) {
		return findByName(user.getName()) != null;
	}
	
	public void deleteAllUsers(){
		
	}
}
