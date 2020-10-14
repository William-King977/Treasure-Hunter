package data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
	
	/** The date that the save state was made (dd/MM/yyyy). */
	private String saveDate;
	
	/** The time that the save state was made (hh:mm). */
	private String saveTime;
	
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
		
		// Get the date and time that the save state was made.
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now().withSecond(0).withNano(0);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.saveDate = date.format(formatter);
		this.saveTime = time.toString();
	}
	
	/**
	 * Gets a string of the game state's full details for file saving.
	 * @return A string of the game state's full details.
	 */
	public String toStringDetail() {
		String strGameState = username + "," + description + "," + currentLevelTime + 
				"," + currentGameTime + "," + timeValid + "," + saveDate + 
				"," + saveTime + "," + level.toStringDetail();
		return strGameState;
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
	 * Gets the date that the save state was made.
	 * @return The date in the format of dd/MM/yyyy.
	 */
	public String getSaveDate() {
		return saveDate;
	}
	
	/**
	 * Sets the date that the save state was made when loading it in.
	 * @param saveDate The date to be set (dd/MM/yyyy). 
	 */
	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}
	
	/**
	 * Gets the time that the save state was made.
	 * @return The time in the format of hh:mm.
	 */
	public String getSaveTime() {
		return saveTime;
	}
	
	/**
	 * Sets the time that the save state was made when loading it in.
	 * @param saveTime The time to be set (hh:mm).
	 */
	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}
	
	/**
	 * Gets the level that was saved in its current state.
	 * @return The level in the state it was saved.
	 */
	public Level getLevel() {
		return level;
	}
}