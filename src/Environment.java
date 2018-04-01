import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Environment. Contains users' names and passwords.
 * 
 * @author Gerard Wypych / G.Wypych@stud.elka.pw.edu.pl
 *
 */
public class Environment {
	private String name;
	private TreeMap<String, String> users;

	/**
	 * Constructor.
	 */
	public Environment() {
		name = "";
		users = new TreeMap<>();
	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            name of the environment
	 */
	public Environment(String name) {
		this.name = name;
		users = new TreeMap<>();
	}

	/**
	 * Adds user to the environment.
	 * 
	 * @param name
	 *            username
	 * @param password
	 *            password
	 * @return false if user exists, true if not
	 */
	public boolean addUser(String name, String password) {
		if (users.containsKey(name))
			return false;

		users.put(name, password);
		return true;
	}

	/**
	 * Sets password for the user.
	 * 
	 * @param name
	 *            user's name
	 * @param password
	 *            new password
	 * @return true if user exists, false if not
	 */
	public boolean setUserPassword(String name, String password) {
		if (!users.containsKey(name))
			return false;

		users.put(name, password);
		return true;
	}

	/**
	 * Deletes user.
	 * 
	 * @param name
	 *            username
	 * @return true if user removed successfully, false if not
	 */
	public boolean deleteUser(String name) {
		if (users.remove(name) == null)
			return false;

		return true;
	}

	/**
	 * Check if password is correct.
	 * 
	 * @param name
	 *            username
	 * @param password
	 *            password
	 * @return true if password is correct
	 */
	public boolean checkUserPassword(String name, String password) {
		if (users.containsKey(name) && users.get(name).equals(password))
			return true;

		return false;
	}

	/**
	 * Sets name of the environment.
	 * 
	 * @param name
	 *            name of the environment
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets name of the environment.
	 * 
	 * @return name of the environment
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets list of the users.
	 * 
	 * @return list of the users
	 */
	public ObservableList<String> getUsers() {
		return FXCollections.observableArrayList(users.keySet());
	}

	@Override
	public String toString() {
		return this.name;
	}
}