package dataBase;

/**
 * Class holding information to get connection with database
 * @author Sergey
 * v.6.33.4
 */
public class DataConfigConteiner {
	private String host;
	private String database;
	private String user;
	private String password;
	
	public DataConfigConteiner(){}
	
	public DataConfigConteiner(String host, String database, String user,
			String password){
		this.host = host;
		this.database = database;
		this.password = password;
		this.user = user;
	}
	
	/**
	 * Method sets the host
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * Method sets the database
	 * @param database 
	 */
	public void setDatabase(String database) {
		this.database = database;
	}
	
	/**
	 * Method sets user
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Method sets password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Method returns host
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * Method returns database name
	 * @return the database name
	 */
	public String getDatabase() {
		return database;
	}
	
	/**
	 * Method returns user name for database
	 * @return the user name
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Method returns password to get access to database
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	

}
