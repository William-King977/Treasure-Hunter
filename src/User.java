/**
 * This models a user profile in the game system.
 * @author William King
 */
public class User {
	/** A unique name for the user. */
	String username;
	
	/** The current level the user is on. */
	int currentLevel;
	
	/**
	 * Constructor for the User class.
	 * @param username A unique name for the user.
	 */
	public User(String username) {
		this.username = username;
		this.currentLevel = 1; // Levels start at 1.
	}
	
	/**
	 * Gets a string of the User's full details for file saving.
	 * @return String of the User's full details.
	 */
	public String toStringDetail() {
		String strUser = username + "," + currentLevel + ",";
		return strUser;
	}
	
	/**
	 * Gets the username of the user.
	 * @return The user's username as a string.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the current level that the user is on.
	 * @return The current level as an integer.
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	/**
	 * Sets the user's current level.
	 * @param currentLevel The current level as an integer.
	 */
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
}
