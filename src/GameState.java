/**
 * This class models a state in the game i.e. a saved game.
 * @author William King
 */
public class GameState {
	/** The username of the player who created the saved state. */
	private String username;
	
	/** The description of the game state that was given by the player. */
	private String description;
	
	/** The level that was saved in its current state. */
	private Level level;
	
	/**
	 * Constructor for the Game State class.
	 * @param username The username of the player who created the saved state.
	 * @param description The description of the game state that was given by the player.
	 * @param level 
	 */
	public GameState(String username, String description, Level level) {
		this.username = username;
		this.description = description;
		this.level = level;
	}
	
	/**
	 * Gets the username of the player who created the saved state.
	 * @return The username of the player as a string.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets description of the game state that was given by the player.
	 * @return The game state's description as a string.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Gets the level that was saved in its current state.
	 * @return The level in the state it was saved.
	 */
	public Level getLevel() {
		return level;
	}
}
