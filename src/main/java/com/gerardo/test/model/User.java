package com.gerardo.test.model;

/**
 * 
 * User object
 *
 */
public class User {

	private long id;
	
	private String name;
	
	public User(){
		id = -1;
	}
	
	private double salary;

	private String position;
	
	public User(long id, String name, double salary, String position){
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.position = position;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", position=" + position
				+ ", salary=" + salary + "]";
	}


}
