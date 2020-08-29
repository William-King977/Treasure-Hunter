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
	
	/** A list of apparels. Accessed by their location on the level. */
	private Apparel[][] apparels;
	
	/** A list of items. Accessed by their location on the level. */
	private Item[][] items;
	
	/** A list of hazards. Accessed by their location on the level. */
	private Hazard[][] hazards;
	
	/** A list of portals. Accessed by their location on the level. */
	private Portal[][] portals;
	
	/** A list of enemies. */
	private Enemy[] enemies;
	
	/**
	 * Constructor for the Level class.
	 * @param levelElements Array holding each element needed to draw the level.
	 * @param levelNumber The number of the level being constructed.
	 * @param player The player in the level.
	 * @param doors A list of all doors in the level.
	 * @param apparels A list of all apparels in the level.
	 * @param items A list of all items in the level.
	 * @param hazards A list of all hazards in the level.
	 * @param enemies A list of all enemies in the level.
	 */
	public Level(String[][] levelElements, int levelNumber, Player player, 
			Door[][] doors, Apparel[][] apparels, Item[][] items, Hazard[][] hazards, 
			Portal[][] portals, Enemy[] enemies) {
		this.levelElements = levelElements;
		this.levelNumber = levelNumber;
		this.player = player;
		this.doors = doors;
		this.apparels = apparels;
		this.items = items;
		this.hazards = hazards;
		this.portals = portals;
		this.enemies = enemies;
		
		// Height and width will be fixed, so this is fine.
		levelHeight = levelElements.length;
		levelWidth = levelElements[0].length; 
	}
	
	/**
	 * Gets a string of the Level's full details for file saving.
	 * @return A string of the Level's full details in its current state.
	 */
	public String toStringDetail() {
		String strLevel = levelNumber + "," + player.toStringDetail() + ",";
		
		// Convert doors to a string, then concatenate them to strLevel.
		for (int i = 0; i < doors.length; i++) {
			for (int j = 0; j < doors[0].length; i++) {
				if (doors[i][j] != null) {
					strLevel = strLevel + doors[i][j].toStringDetail();
				}
			}
		}
		
		// Convert apparels to a string, then concatenate them to strLevel.
		for (int i = 0; i < apparels.length; i++) {
			for (int j = 0; j < apparels[0].length; i++) {
				if (apparels[i][j] != null) {
					strLevel = strLevel + apparels[i][j].toStringDetail();
				}
			}
		}
		
		// Convert items to a string, then concatenate them to strLevel.
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items[0].length; i++) {
				if (items[i][j] != null) {
					strLevel = strLevel + items[i][j].toStringDetail();
				}
			}
		}
		
		// Convert hazards to a string, then concatenate them to strLevel.
		for (int i = 0; i < hazards.length; i++) {
			for (int j = 0; j < hazards[0].length; i++) {
				if (hazards[i][j] != null) {
					strLevel = strLevel + hazards[i][j].toStringDetail();
				}
			}
		}
		
		// Convert portals to a string, then concatenate them to strLevel.
		for (int i = 0; i < portals.length; i++) {
			for (int j = 0; j < portals[0].length; i++) {
				if (portals[i][j] != null) {
					strLevel = strLevel + portals[i][j].toStringDetail();
				}
			}
		}
		
		// Convert enemies to a string, then concatenate them to strLevel.
		for (Enemy elem : enemies) {
			strLevel = strLevel + elem.toStringDetail();
		}
		
		return strLevel;
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
	 * Gets a list of all the doors in the level.
	 * @return All of the doors as an array.
	 */
	public Door[][] getDoors() {
		return doors;
	}
	
	/**
	 * Gets a list of all the apparels in the level.
	 * @return All of the apparels as an array.
	 */
	public Apparel[][] getApparels() {
		return apparels;
	}
	
	/**
	 * Gets a list of all the items in the level.
	 * @return All of the items as an array.
	 */
	public Item[][] getItems() {
		return items;
	}
	
	/**
	 * Gets a list of all the hazards in the level.
	 * @return All of the hazards as an array.
	 */
	public Hazard[][] getHazards() {
		return hazards;
	}
	
	/**
	 * Gets a list of all the portals in the level.
	 * @return All of the portals as an array.
	 */
	public Portal[][] getPortals() {
		return portals;
	}
	
	/**
	 * Gets a list of all the enemies in the level.
	 * @return All of the enemies as an array.
	 */
	public Enemy[] getEnemies() {
		return enemies;
	}
}
