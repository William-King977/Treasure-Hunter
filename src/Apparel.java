/**
 * Models an apparel that can be picked up in a level.
 * @author William King
 */
public class Apparel {
	/** The x-coordinate location of the apparel. */
	private int apparelX;
	
	/** The x-coordinate location of the apparel. */
	private int apparelY;
	
	/** Specifies the type of apparel this is. */
	private ApparelType type;
	
	/**
	 * Constructor for the Apparel class.
	 * @param apparelX The x-coordinate location of the apparel.
	 * @param apparelY The y-coordinate location of the apparel.
	 * @param type The type of apparel this is.
	 */
	public Apparel(int apparelX, int apparelY, ApparelType type) {
		this.apparelX = apparelX;
		this.apparelY = apparelY;
		this.type = type;
	}

	/**
	 * Gets the x-coordinate location of the apparel.
	 * @return The x-coordinate as an integer.
	 */
	public int getApparelX() {
		return apparelX;
	}

	/**
	 * Gets the y-coordinate location of the apparel.
	 * @return The y-coordinate as an integer.
	 */
	public int getApparelY() {
		return apparelY;
	}

	/**
	 * Gets the apparel's type (flippers, fire boots, etc.).
	 * @return The apparel type as an ApparelType enum.
	 */
	public ApparelType getType() {
		return type;
	}
}
