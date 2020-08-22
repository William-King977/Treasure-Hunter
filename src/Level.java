/**
 * This class models a single level in the game.
 * @author William King
 */
public class Level {
	/** Holds each cell element of the level in their corresponding 
	 * positions in the list.  */
	private String[][] levelElements;
	
	/** The height of the level in cells. */
	private int levelHeight;
	
	/** The width of the level in cells. */
	private int levelWidth;
	
	/** Specifies which level this level is e.g. level 1, 2,... */
	private int levelNumber;
	
	/** The player in the level. */
	private Player player;
	
	/** A list of doors. Accessed by their location on the level. */
	private Door[][] doors;
	
	/**
	 * Constructor for the Level class.
	 * @param levelElements Array holding each element needed to draw the level.
	 * @param levelNumber The number of the level being constructed.
	 * @param player The player in the level.
	 * @param doors The list of all doors in the level.
	 */
	public Level(String[][] levelElements, int levelNumber, Player player, Door[][] doors) {
		this.levelElements = levelElements;
		this.levelNumber = levelNumber;
		this.player = player;
		this.doors = doors;
		
		// Height and width will be fixed, so this is fine.
		levelHeight = levelElements.length;
		levelWidth = levelElements[0].length; 
	}
	
	/**
	 * Gets the elements (and their position) used for the level. 
	 * @return An array of elements for the level.
	 */
	public String[][] getLevelElements() {
		return levelElements;
	}
	
	/**
	 * Get the height of the level in number of cells.
	 * @return The height of the level.
	 */
	public int getLevelHeight() {
		return levelHeight;
	}
	
	/**
	 * Get the width of the level in number of cells.
	 * @return The width of the level.
	 */
	public int getLevelWidth() {
		return levelWidth;
	}
	
	/**
	 * Get the level number of the level e.g. level 1,2,...
	 * @return The level number as an integer.
	 */
	public int getLevelNumber() {
		return levelNumber;
	}

	/**
	 * Gets the player in the level.
	 * @return the player 
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player after adjusting their status.
	 * @param player The player to be set.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Gets the a list of all the doors in the level.
	 * @return The all the doors as an array.
	 */
	public Door[][] getDoors() {
		return doors;
	}
}
