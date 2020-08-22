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
	 * @return the type
	 */
	public ItemType getType() {
		return type;
	}
}
