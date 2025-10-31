package com.gerardo.test.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * 
 * User data manipulation
 *
 */
@Component("userDao")
public class UserDao {

	private JdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new JdbcTemplate(jdbc);
	}

	public List<User> findAllUsers() {
		return jdbc.query("select * from users", new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return mapRowUser(rs, rowNum);
			}
		});
	}
	
	public User findByName(String name) {
		List<User> userArray = jdbc.query("select * from users where Name=?", new Object[] { name },
				new RowMapper<User>() {
					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						return mapRowUser(rs, rowNum);
					}
				});
		if (userArray != null && userArray.size() > 0) {
			return userArray.get(0);
		}
		return null;
	}

	public int saveUser(User user) {
		System.out.println("saving:_" + user);
		return jdbc.update("INSERT INTO users(IdUser, Name, Salary, Position) VALUES (?, ?, ?, ?)",
				new Object[] { null, user.getName(), user.getSalary(), user.getPosition() });
	}

	public int updateUser(User user) {
		System.out.println("updating: " + user);
		return jdbc.update("UPDATE users SET Name=?, Salary=?, Position=? WHERE IdUser=?",
				new Object[] { user.getName(), user.getSalary(), user.getPosition(), user.getId() });
	}

	public int deleteUser(long id) {
		return jdbc.update("DELETE FROM users WHERE IdUser=?", new Object[] { id });
	}

	public User findUser(long id) {
		List<User> userArray = jdbc.query("select * from users where IdUser=?", new Object[] { id },
				new RowMapper<User>() {
					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						return mapRowUser(rs, rowNum);
					}
				});
		if (userArray != null && userArray.size() > 0) {
			return userArray.get(0);
		}
		return null;
	}

	private User mapRowUser(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("IdUser"));
		user.setName(rs.getString("Name"));
		user.setSalary(rs.getFloat("Salary"));
		user.setPosition(rs.getString("Position"));
		return user;
	}

}
