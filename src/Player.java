/**
 * This class constructs a Player model in the game and keeps
 * track of its current status.
 * @author William King
 */
public class Player {
	/** The number of possible total items that the user can 
	 * equip at one time. */
	private final int NUM_POSSIBLE_EQUIPPED = 2; 
	
	/** The x-coordinate of the player's current position. */
	private int playerX;
	
	/** The y-coordinate of the player's current position. */
	private int playerY;
	
	/** The items that the player is currently holding. */
	private String[] inventory;
	
	/** The items the player is currently equipped with. 
	 * Index 0 holds apparel and index*/
	private String[] equippedItems;
	
	/** The number of tokens the player has currently collected. */
	private int numTokens;
	
	/**
	 * Constructor for the Player class.
	 * @param playerX The x-coordinate of the player's start position.
	 * @param playerY The y-coordinate of the player's start position.
	 */
	public Player(int playerX, int playerY) {
		this.setX(playerX);
		this.setY(playerY);
		
		// Set to one item for now. It will change as new items are added.
		this.inventory = new String[1]; 
		this.equippedItems = new String[NUM_POSSIBLE_EQUIPPED];
		this.numTokens = 0;
	}

	/**
	 * Gets the player's current x-coordinate position.
	 * @return The x-coordinate as an integer.
	 */
	public int getX() {
		return playerX;
	}
	
	/**
	 * Sets the player's x-coordinate position.
	 * @param playerX The x-coordinate as an integer.
	 */
	public void setX(int playerX) {
		this.playerX = playerX;
	}
	
	/**
	 * Gets the player's current y-coordinate position.
	 * @return The y-coordinate as an integer.
	 */
	public int getY() {
		return playerY;
	}
	
	/**
	 * Sets the player's y-coordinate position.
	 * @param playerY The y-coordinate as an integer.
	 */
	public void setY(int playerY) {
		this.playerY = playerY;
	}

	/**
	 * Gets the player's current inventory.
	 * @return The player's current inventory as a string array.
	 */
	public String[] getInventory() {
		return inventory;
	}

	/**
	 * Adds a new item to the player's inventory.
	 * @param newItem The new item that was picked up (as a string).
	 */
	public void addInventory(String newItem) {
		// Checks how many items there are in the current inventory.
		int numItems = 0; 
		for (String items : inventory) {
			numItems++;
		}
		
		// Populate the new inventory.
		String[] updatedInventory = new String[numItems + 1];
		for (int i = 0; i < numItems; i++) {
			updatedInventory[i] = inventory[i];
		}
		
		// Add the new item at the end of the array.
		updatedInventory[numItems + 1] = newItem;
		inventory = updatedInventory;
	}
	
	/**
	 * Get the items that the user is currently equipped with.
	 * @return The player's equipped items as a string array.
	 */
	public String[] getEquippedItems() {
		return equippedItems;
	}

	/**
	 * Adds a new item to the user's currently equipped items.
	 * @param equippedItems The equipped items to be set.
	 */
	public void addEquipped(String newItem) {
		switch (newItem) {
			// Check for apparel.
			case "Flippers":
			case "Fire Boots":
				equippedItems[0] = newItem;
				break;
			// Assume it's a key.
			default:
				equippedItems[1] = newItem;
		}
	}

	/**
	 * Gets the number of tokens the player currently has.
	 * @return The number of tokens as an integer.
	 */
	public int getNumTokens() {
		return numTokens;
	}

	/**
	 * Sets the number of tokens the player has.
	 * @param numTokens The number of tokens to be set.
	 */
	public void setNumTokens(int numTokens) {
		this.numTokens = numTokens;
	}	
}
