package Data;

/**
 * Models an item that can be picked up in a level.
 * @author William King
 */
public class Item {
	/** The x-coordinate location of the item. */
	private int itemX;
	
	/** The x-coordinate location of the item. */
	private int itemY;
	
	/** Specifies the type of item this is. */
	private ItemType type;
	
	/**
	 * Constructor for the Item class.
	 * @param itemX The x-coordinate location of the item.
	 * @param itemY The x-coordinate location of the item.
	 * @param type The type of item this is.
	 */
	public Item(int itemX, int itemY, ItemType type) {
		this.itemX = itemX;
		this.itemY = itemY;
		this.type = type;
	}
	
	/**
	 * Gets a string of the Item's full details for file saving.
	 * @return A string of the Item's full details.
	 */
	public String toStringDetail() {
		String strItem = "ITEM," + itemX + ":" + itemY + ":" + type + ",";
		return strItem;
	}

	/**
	 * Gets the x-coordinate location of the item.
	 * @return The x-coordinate as an integer.
	 */
	public int getX() {
		return itemX;
	}

	/**
	 * Gets the y-coordinate location of the item.
	 * @return The y-coordinate as an integer.
	 */
	public int getY() {
		return itemY;
	}

	/**
	 * Gets the item's type (yellow key, etc.).
	 * @return The item type as an enum.
	 */
	public ItemType getType() {
		return type;
	}
}
