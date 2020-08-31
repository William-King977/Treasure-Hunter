/**
 * This class models a state in the game i.e. a saved game.
 * @author William King
 */
public class GameState {
	/** The username of the player who created the saved state. */
	private String username;
	
	/** The description of the game state that was given by the player. */
	private String description;
	
	/** The current amount of time spent on the level. */
	private long currentLevelTime;
	
	/** The current time spent playing the game. */
	private long currentGameTime;
	
	/** If the player's total game time should be saved or not. */
	private boolean timeValid;
	
	/** The level that was saved in its current state. */
	private Level level;
	
	/**
	 * Constructor for the Game State class.
	 * @param username The username of the player who created the saved state.
	 * @param description The description of the game state that was given by the player.
	 * @param currentLevelTime The current amount of time spent on the level.
	 * @param currentGameTime The current time spent playing the game.
	 * @param timeValid If the player's total game time should be saved or not.
	 * @param level The level that was saved in its current state.
	 */
	public GameState(String username, String description, long currentLevelTime, 
			long currentGameTime, boolean timeValid, Level level) {
		this.username = username;
		this.description = description;
		this.currentLevelTime = currentLevelTime;
		this.currentGameTime = currentGameTime;
		this.timeValid = timeValid;
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
	 * Gets the current amount of time spent on the level.
	 * @return The level time as a long value (milliseconds).
	 */
	public long getCurrentLevelTime() {
		return currentLevelTime;
	}
	
	/**
	 * The current amount of time spent playing the game.
	 * @return The game time as a long value (milliseconds).
	 */
	public long getCurrentGameTime() {
		return currentGameTime;
	}
	
	/**
	 * If the player's total game time is valid or not.
	 * @return True if it's valid, otherwise false.
	 */
	public boolean getTimeValid() {
		return timeValid;
	}
	
	/**
	 * Gets the level that was saved in its current state.
	 * @return The level in the state it was saved.
	 */
	public Level getLevel() {
		return level;
	}
}
