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
	 * @param currentLevel The current level the user is on.
	 */
	public User(String username, int currentLevel) {
		this.username = username;
		this.currentLevel = currentLevel;
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
