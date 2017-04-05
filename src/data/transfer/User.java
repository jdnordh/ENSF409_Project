package data.transfer;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String firstName;
	private String lastName;
	private Date birthday;
	private boolean admin;
	
	/** Construct an empty user */
	public User(){}
	
	/** Construct a new user */
	public User(String fn, String ln, Date b){
		firstName = fn;
		lastName = ln;
		birthday = b;
	}
	
	/**
	 * Check if two users are equal
	 * @param u User to check
	 * @return True if equal
	 */
	public boolean equals(User u){
		if (this == u) return true;
		if (!this.firstName.equals(u.getFirstName())) return false;
		if (!this.lastName.equals(u.getLastName())) return false;
		if (!this.birthday.equals(u.getBirthday())) return false;
		if (admin != u.isAdmin()) return false;
		return true;
	}
	
	// Set and get
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
