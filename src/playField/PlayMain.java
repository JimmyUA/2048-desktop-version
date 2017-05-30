package playField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import GUI.*;

/**
 * Main method - game engine
 * @author Sergey
 * v.6.33.4
 */
public class PlayMain {

	public static void main(String[] args) {

		Logger logger = Logger.getLogger(GUI2048.class.getName());
		try {
			FileHandler handler = new FileHandler("log.txt");
			logger.addHandler(handler);
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, "Can't create file due to security settings");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Can't create file due to IO problems");
		}

		try {
			try {
				new FirstWindow();
			} catch (InstantiationException e) {
				logger.log(Level.SEVERE, "Exception: ", e);
			} catch (IllegalAccessException e) {
				logger.log(Level.SEVERE, "Exception: ", e);
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE, "Exception: ", e);
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Exception: ", e);
			}
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		}

	}

}
