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
}
