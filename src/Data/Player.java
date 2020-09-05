package Data;

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
	private String[] inventory = {};
	
	/** The items the player is currently equipped with. 
	 * Index 0 holds apparel and index. */
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
		this.numTokens = 0;
		
		// Initialise it with empty spaces.
		this.equippedItems = new String[NUM_POSSIBLE_EQUIPPED];
		for (int i = 0; i < NUM_POSSIBLE_EQUIPPED; i++) {
			this.equippedItems[i] = " ";
		}
	}
	
	/**
	 * Gets a string of the Player's full details for file saving.
	 * @return A string of the Player's full details.
	 */
	public String toStringDetail() {
		// Ensures inventory items are in format of 'item;item;item...'
		String strInventory = "";
		for (int i = 0; i < inventory.length; i++) {
			if (i == 0) {
				strInventory = inventory[0];
			} else {
				strInventory = strInventory + ";" + inventory[i];
			}
		}
		
		// Ensures equipped items are in format of 'item;item...'
		String strEquippedItems = "";
		for (int i = 0; i < equippedItems.length; i++) {
			if (i == 0) {
				strEquippedItems = equippedItems[0];
			} else {
				strEquippedItems = strEquippedItems + ";" + equippedItems[i];
			}
		}
		
		String strPlayer = playerX + ":" + playerY + ":" + strInventory + 
				":" + strEquippedItems + ":" + numTokens + ",";
		return strPlayer;
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
	 * Sets the player's current inventory when loading a game state.
	 * @param The player's inventory as a string array.
	 */
	public void setInventory(String[] inventory) {
		this.inventory = inventory;
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
		updatedInventory[numItems] = newItem;
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
	 * Sets the player's currently equipped items when loading a game state.
	 * @param The player's equipped items as a string array.
	 */
	public void setEquippedItems(String[] equippedItems) {
		this.equippedItems = equippedItems;
	}
	
	/**
	 * Adds a new item to the user's currently equipped items.
	 * @param equippedItems The equipped items to be set.
	 */
	public void addEquipped(String newItem) {
		// It replaces them...
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
	 * Consumes the item the player uses.
	 * This removes that item from the user's equipped items and inventory.
	 * @param item The item that was used.
	 */
	public void useItem(String item) {
		// Remove from equipped items.
		equippedItems[1] = " ";
		
		// Remove from the inventory.
		int inventoryLength = inventory.length;
		boolean itemFound = false;
		
		String[] updatedInventory = new String[inventoryLength - 1];
		
		// Used so that the items are added correctly, while
		// being able to go through all of the items in the old inventory.
		int locIndex = 0;
		for (int i = 0; i < inventoryLength; i++) {
			if (!itemFound && inventory[i].equals(item)) {
				// Skips the first occurrence of the used item,
				// so it doesn't get added to the updated list.
				itemFound = true;
			} else {
				updatedInventory[locIndex] = inventory[i];
				locIndex++;
			}
		}
		
		inventory = updatedInventory;
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
