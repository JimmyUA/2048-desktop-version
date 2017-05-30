package playField;


import user.User2048;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import dataBase.DBWorker;

/**
 * This method shows the record from Data Base
 * 
 * v.6.33.4
 * 
 * @author Sergey All rights reserved
 */
public class Record {

	private int record;

	{
		record = 0;
	}

	/**
	 * Method sets user record from DataBase
	 * @param user
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws FileNotFoundException 
	 */
	public void setRecord(User2048 user) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException{
		
		record = new DBWorker().getRecord(user);
	}
	
	public int getRecord(){
		return record;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + record;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + record;
		return result;
	}

	/* (non-Javadoc)
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
		Record other = (Record) obj;
		if (record != other.record)
			return false;
		return true;
	}
}
