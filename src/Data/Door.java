package Data;

/**
 * Models a Door in a game level.
 * @author William King
 */
public class Door {
	/** The x-coordinate location of the door. */
	private int doorX;
	
	/** The x-coordinate location of the door. */
	private int doorY;
	
	/** Specifies the type of door this is. */
	private DoorType type;
	
	/** The number of tokens required to open the door (token doors only). */
	private int numTokens;
	
	/**
	 * Constructor for the Door class.
	 * @param doorX The x-coordinate location of the door.
	 * @param doorY The y-coordinate location of the door.
	 * @param type Specifies the type of door this is.
	 * @param numTokens The number of tokens required to open the door (token doors only).
	 */
	public Door(int doorX, int doorY, DoorType type, int numTokens) {
		this.doorX = doorX;
		this.doorY = doorY;
		this.type = type;
		this.numTokens = numTokens;
	}
	
	/**
	 * Gets a string of the Door's full details for file saving.
	 * @return A string of the Door's full details.
	 */
	public String toStringDetail() {
		String strDoor = "DOOR," + doorX + ":" + doorY + ":" + type + ":" + numTokens + ",";
		return strDoor;
	}

	/**
	 * Gets the x-coordinate location of the door.
	 * @return The x-coordinate as an integer.
	 */
	public int getX() {
		return doorX;
	}

	/**
	 * Gets the y-coordinate location of the door.
	 * @return The y-coordinate as an integer.
	 */
	public int getY() {
		return doorY;
	}
	
	/**
	 * Gets the door's type (yellow, orange, token, etc.).
	 * @return The door type as a DoorType enum.
	 */
	public DoorType getType() {
		return type;
	}

	/**
	 * Gets the number of tokens required to open the (token) door.
	 * @return The number of tokens as an integer.
	 */
	public int getNumTokens() {
		return numTokens;
	}
}
