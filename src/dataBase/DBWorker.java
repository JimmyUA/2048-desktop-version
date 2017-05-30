package dataBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import playField.Score;
import user.User2048;

/**
 * Class works with MYSQL database
 * 
 * @author Sergey
 * v.6.33.4
 */
public class DBWorker {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;

	public DBWorker() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			FileNotFoundException {
		
		// Using XStream library to get connection parameters from .xml file
		XStream xsm = new XStream(new DomDriver());
		FileInputStream fis = new FileInputStream("dataBaseConfig.xml");
		DataConfigConteiner conteiner = new DataConfigConteiner();
		xsm.fromXML(fis, conteiner);
		
		// Connecting to the database
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		Connection conn = DriverManager
				.getConnection("jdbc:mysql://" + conteiner.getHost() + "/" + conteiner.getDatabase() + "?" + "user="
						+ conteiner.getUser() + "&password=" + conteiner.getPassword());
		st = conn.createStatement();

	}

	/**
	 * Closes one by one result set, statement and connection
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		rs.close();
		st.close();
		conn.close();
	}

	/**
	 * Method inserts into the DB current field representation
	 * 
	 * @param field instanse
	 * @throws SQLException
	 */
	public void savePlayField(User2048 user) throws SQLException {
		int size = user.getField().getSize(); // size of play field

		// Getting list of fields we need to update
		String columns = "";
		rs = st.executeQuery("SHOW COLUMNS FROM saved" + size + "x" + size);
		while (rs.next()) {
			columns += rs.getString(1) + ", ";
		}
		columns = columns.substring(0, columns.length() - 2);

		// Creating query to updat field
		String query = "REPLACE saved" + size + "x" + size + " (" + columns + ") VALUES (" + getUserID(user) + ", "
				+ user.getField().getPlayFieldString() + ", " + user.getScore() + ")";
		st.execute(query);
		rs.close();
	}
	
	/**
	 * Method deletes stored play field with current size for current user
	 * @param user
	 * @throws SQLException
	 */
	public void deletePlayField(User2048 user) throws SQLException {
		int size = user.getField().getSize(); // size of play field

		
		// Creating query to updat field	
		String query = "DELETE FROM saved" + size + "x" + size + " WHERE id = " + getUserID(user);
		
		st.execute(query);
		
	}

	/**
	 * Method saves current play field
	 * 
	 * @param user - user object
	 * @throws SQLException
	 */
	public void saveRecord(User2048 user) throws SQLException {
		String query = "REPLACE records (id, record) VALUES(" + getUserID(user) + ", " + user.getField().getScore() + ")";

		if (!hasRecord(user)) {
			st.execute(query);
		}

		if (getRecord(user) < user.getField().getScore().getScore()) {
			st.execute(query);
		}
	}

	/**
	 * Method updates user in a table with current user instance
	 * 
	 * @param user object
	 * @throws SQLException
	 */
	public void saveLastUser(User2048 user) throws SQLException {

		st.execute("UPDATE last_user SET id = " + getUserID(user) + " WHERE id = " + getLastUserID());
	}

	/**
	 * Method saves user in case user with the same login not exist and returns
	 * true if user was added and false in other case
	 * 
	 * @param user object
	 * @return
	 * @throws SQLException
	 */
	public boolean saveUser(User2048 user) throws SQLException {
		boolean isAdded = false;
		rs = st.executeQuery("SELECT id FROM users WHERE login = '" + user.getLogin() + "'");
		if (!rs.next()) {
			st.execute("INSERT INTO users (name, login, password, email) VALUES('" + user.getName() + "', '"
					+ user.getLogin() + "', '" + user.getPassword() + "', '" + user.getEmail() + "')");
			isAdded = true;

		}
		rs.close();
		return isAdded;
	}

	/**
	 * Method gets user ID for current user object
	 * 
	 * @param use - user object
	 * @return string with current user object ID
	 * @throws SQLException
	 */
	private String getUserID(User2048 user) throws SQLException {
		// Getting current user id
		String login = user.getLogin();
		rs = st.executeQuery("SELECT id FROM users WHERE login = '" + login + "'");
		String id = null;
		if (rs.next()) {
			id = rs.getString(1);
		}
		rs.close();
		return id;
	}

	/**
	 * Mthod gets record for current user object
	 * 
	 * @param user - user object
	 * @return integer represents current user record
	 * @throws SQLException
	 */
	public int getRecord(User2048 user) throws SQLException {
		int record = 0;
		rs = st.executeQuery("SELECT record FROM records WHERE id = " + getUserID(user));
		if (rs.next()) {
			record = Integer.parseInt(rs.getString(1));
		}
		rs.close();
		return record;
	}

	/**
	 * Method checks if there is stored record in database for curent user
	 * @param user object
	 * @return true if in database there is record for 
	 * current user and false otherwise
	 * @throws SQLException
	 */
	private boolean hasRecord(User2048 user) throws SQLException {
		boolean result = false;
		rs = st.executeQuery("SELECT record FROM records WHERE id = " + getUserID(user));
		if (rs.next()) {
			result = true;
		}
		return result;
	}

	/**
	 * Method gets information about last stored game for every field size
	 * separately and returns it like a twoDimentional array of integers,
	 * represents tile values.
	 * 
	 * @param user
	 * @return twoDimentional array of integers, represents tile values
	 * @throws SQLException
	 */
	public int[][] getStoredField(User2048 user) throws SQLException {
		int size = user.getField().getSize();

		int[][] playField = new int[size][size];

		rs = st.executeQuery("SELECT score FROM saved" + +size + "x" + size + " WHERE id = " + getUserID(user));
		if (rs.next()) {
			int score = rs.getInt(1);
			Score scoreObj = user.getScore();
			scoreObj.setScore(score);
		}

		String query = "SELECT * FROM saved" + size + "x" + size + " WHERE id = " + getUserID(user);
		try {
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			return playField;
		}

		int count = 1;
		if (rs.next()) {
			while (count < size * size + 2) {
				if (count != 1) {
					if (count < size + 2) {
						playField[0][count - 2] = rs.getInt(count);
					} else if (count < 2 * size + 2) {
						playField[1][count - 2 - size] = rs.getInt(count);
					} else if (count < 3 * size + 2) {
						playField[2][count - 2 - (2 * size)] = rs.getInt(count);
					} else if (count < 4 * size + 2) {
						playField[3][count - 2 - (3 * size)] = rs.getInt(count);
						if (count == (4 * size + 2) - 1 && size == 4) {
							return playField;
						}
					} else if (count < 5 * size + 2) {
						playField[4][count - 2 - (4 * size)] = rs.getInt(count);
						if (count == (5 * size + 2) - 1 && size == 5) {
							return playField;
						}
					} else if (count < 6 * size + 2) {
						playField[5][count - 2 - (5 * size)] = rs.getInt(count);
						if (count == (6 * size + 2) - 1 && size == 6) {
							return playField;
						}
					} else if (count < 7 * size + 2) {
						playField[6][count - 2 - (6 * size)] = rs.getInt(count);
					} else {
						playField[7][count - 2 - (7 * size)] = rs.getInt(count);
					}

				}
				count++;
			}
		}
		
		rs.close();
		return playField;
	}

	/**
	 * Method returns last user ID from table wich stores 
	 * @return last user ID 
	 * @throws SQLException
	 */
	private String getLastUserID() throws SQLException {
		String lastID = "";

		rs = st.executeQuery("SELECT * FROM last_user");
		if (rs.next()){
		lastID = rs.getString(1);
		}
		rs.close();
		return lastID;
	}

	/**
	 * Method returns user inctance with parameters stored in a database
	 * @return user object
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public User2048 getLastUser()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		User2048 user = null;

		rs = st.executeQuery("SELECT name, login, password, email FROM users WHERE id = " + getLastUserID());
		if (rs.next()){
		user = new User2048(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
		}
		rs.close();
		return user;
	}

	/**
	 * Method returns user object saved in database if login and password matched
	 * @param login
	 * @param password
	 * @return user object
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public User2048 getUser(String login, String password)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		User2048 user = null;

		rs = st.executeQuery("SELECT name, login, password, email FROM users " + "WHERE login = '" + login
				+ "' AND password = '" + password + "'");
		if (rs.next()) {
			user = new User2048(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
		}
		rs.close();
		return user;
	}

	/**
	 * Method checks if user with entered login exist
	 * @param login
	 * @return true if in database there are user with entered login and false otherwise
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public boolean isUserExsist(String login)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		rs = st.executeQuery("SELECT name, login, password, email FROM users WHERE login = '" + login + "'");
		if (rs.next()) {
			return true;
		}
		rs.close();
		return false;
	}

	/**
	 * Method checks if there is stored field in database
	 * @param user
	 * @return true if there is stored field for current user, false otherwise
	 * @throws SQLException
	 */
	public boolean isStoredFieldExist(User2048 user, int size) throws SQLException {
		
		rs = st.executeQuery("SELECT first FROM saved" + size + "x" + size + " WHERE id = " + getUserID(user));
		if (rs.next()){
			return true;
		}
		
		rs.close();
		return false;
	}
}
