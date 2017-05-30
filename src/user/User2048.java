package user;

import java.sql.SQLException;

import playField.PlayField;
import playField.Record;
import playField.Score;

/**
 * This class holds information about 2048 game user and all game obects
 * 
 * @author Sergey
 * v.6.33.4
 * All rights reserved
 */

public class User2048 {
	private String name;
	private String login;
	private String password;
	private String email;
	private Record record;
	private PlayField field;
	private Score score;

	// no argument constructor
	public User2048() {
	}

	public User2048(String name, String login, String password, String email)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		this.name = name;
		this.login = login;
		this.password = password;
		this.email = email;
		record = new Record();
		score = new Score();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User2048 other = (User2048) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User2048 [login=" + login + "]";
	}

	/**
	 * Method sets record for current user instance
	 * 
	 * @param record
	 *            the record to set
	 */
	public void setRecord(Record record) {
		this.record = record;
	}

	/**
	 * Method sets name for current user instance
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method sets login for current user instance
	 * 
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Method sets pssword for current user instance
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Method sets PlayField instance for current user instance
	 * 
	 * @param field
	 */
	public void setField(PlayField field) {
		this.field = field;
	}

	/**
	 * Method sets email for current user instance
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Score score) {
		this.score = score;
	}

	/**
	 * Method returns record of current user instance
	 * 
	 * @return the record
	 */

	public Record getRecord() {
		return record;
	}

	/**
	 * Method returns name of current user instance
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method returns login of current user instance
	 * 
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Method returns password of current user instance
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method returns email of current user instance
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method returns PlayField object of current user instance
	 * 
	 * @return the field
	 */
	public PlayField getField() {
		return field;
	}

	/**
	 * Method returns Score object of current user instance
	 * 
	 * @return the score
	 */
	public Score getScore() {
		return score;
	}

}
