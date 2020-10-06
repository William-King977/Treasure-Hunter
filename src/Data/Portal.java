package data;

/**
 * Models a portal in the level.
 * This transports the player from the portal to another portal
 * that it corresponds to.
 * @author William King
 */
public class Portal {
	/** The x-coordinate location of the portal. */
	private int portalX;
	
	/** The y-coordinate location of the portal. */
	private int portalY;
	
	/** The x-coordinate location of the destination portal. */
	private int destX;
	
	/** The y-coordinate location of the destination portal. */
	private int destY;
	
	/**
	 * Constructor for the Portal class.
	 * @param portalX The x-coordinate location of the portal.
	 * @param portalY The y-coordinate location of the portal.
	 * @param destX The x-coordinate location of the destination portal.
	 * @param destY The y-coordinate location of the destination portal.
	 */
	public Portal(int portalX, int portalY, int destX, int destY) {
		this.portalX = portalX;
		this.portalY = portalY;
		this.destX = destX;
		this.destY = destY;
	}
	
	/**
	 * Gets a string of the Portal's full details for file saving.
	 * @return A string of the Portal's full details.
	 */
	public String toStringDetail() {
		String strPortal = "PORTAL," + portalX + ":" + portalY + ":" + destX + ":" + destY + ",";
		return strPortal;
	}
	
	/**
	 * Gets the x-coordinate location of the portal.
	 * @return The x-coordinate as an integer.
	 */
	public int getX() {	
		return portalX;
	}
	
	/**
	 * Gets the y-coordinate location of the portal.
	 * @return The y-coordinate as an integer.
	 */
	public int getY() {	
		return portalY;
	}
	
	/**
	 * Gets the x-coordinate location of the destination portal.
	 * @return The x-coordinate as an integer.
	 */
	public int getDestX() {	
		return destX;
	}
	
	/**
	 * Gets the y-coordinate location of the destination portal.
	 * @return The y-coordinate as an integer.
	 */
	public int getDestY() {	
		return destY;
	}
	
	/**
	 * Moves the player to the destination portal.
	 * @param levelElements An array holding all the elements in the level.
	 * @param player The player to be moved.
	 * @param direction The direction the player moved towards.
	 */
	public void movePlayer(String[][] levelElements, Player player, String direction) {
		// The new co-ordinates for the player to move in.
		int newX = -1;
		int newY = -1;
		
		// Indexes of the left/right/front/back of the destination portal.
		int leftX = destX - 1;
		int leftY = destY;
		int rightX = destX + 1;
		int rightY = destY;
		
		int frontX = destX;
		int frontY = destY - 1;
		int backX = destX;
		int backY = destY + 1;
		
		// Attempt to move the player adjacent to the destination portal's direction.
		String adjacentObject = "";
		switch (direction) {
			case "UP":
				adjacentObject = levelElements[frontY][frontX];
				newX = frontX;
				newY = frontY;
				// Change direction clockwise (used if the method runs again). 
				direction = "RIGHT";
				break;
			case "DOWN":
				adjacentObject = levelElements[backY][backX];
				newX = backX;
				newY = backY;
				direction = "LEFT";
				break;
			case "LEFT":
				adjacentObject = levelElements[leftY][leftX];
				newX = leftX;
				newY = leftY;
				direction = "UP";
				break;
			case "RIGHT":
				adjacentObject = levelElements[rightY][rightX];
				newX = rightX;
				newY = rightY;
				direction = "DOWN";
				break;
		}
		
		boolean isAdjacentObject = isObject(adjacentObject);
		
		if (isAdjacentObject) {
			// Run the method again with the changed direction.
			// Infinite recursion happens IF the destination portal is
			// surrounded by walls. Or if you typed the portal co-ordinates
			// wrong in the file...
			movePlayer(levelElements, player, direction);
		// If the player lands into an enemy.
		} else if (adjacentObject.equals("E")) {
			player.setDead(true);
		// Otherwise, move the player.
		} else {
			player.setX(newX);
			player.setY(newY);
		}
	}
	
	/**
	 * Checks if the passed in string is an object or a floor.
	 * @param object The level object as a string. 
	 * @return True if its an object, otherwise false (if it's clear).
	 */
	private boolean isObject(String object) {
		switch (object) {
			case "W":
			// case "G":
			// case "A":
			// case "I":
			// case "D":
			// case "T":
			// case "P":
			// case "H":
			// case "E":
				return true;
			default:
				return false;
		}
	}
}
