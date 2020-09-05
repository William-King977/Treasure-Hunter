package Data;

/**
 * Models a hazard in a level.
 * @author William King
 */
public class Hazard {
	/** The x-coordinate location of the hazard. */
	public int hazardX;
	
	/** The y-coordinate location of the hazard. */
	public int hazardY;
	
	/** Specifies the type of hazard this is. */
	public HazardType type;
	
	/**
	 * Constructor for the Hazard class.
	 * @param hazardX The x-coordinate location of the hazard.
	 * @param hazardY The y-coordinate location of the hazard.
	 * @param type The type of hazard this is.
	 */
	public Hazard(int hazardX, int hazardY, HazardType type) {
		this.hazardX = hazardX;
		this.hazardY = hazardY;
		this.type = type;
	}
	
	/**
	 * Gets a string of the Hazard's full details for file saving.
	 * @return A string of the Hazard's full details.
	 */
	public String toStringDetail() {
		String strHazard = "HAZARD," + hazardX + ":" + hazardY + ":" + type + ",";
		return strHazard;
	}
	
	/**
	 * Gets the x-coordinate location of the hazard.
	 * @return The x-coordinate as an integer.
	 */
	public int getX() {
		return hazardX;
	}

	/**
	 * Gets the y-coordinate location of the hazard.
	 * @return The y-coordinate as an integer.
	 */
	public int getY() {
		return hazardY;
	}

	/**
	 * Gets the hazard's type (water, fire, etc.).
	 * @return The hazard type as a HazardType enum.
	 */
	public HazardType getType() {
		return type;
	}
}
