package data;

/**
 * Models an enemy in the game.
 * @author William King
 */
abstract public class Enemy {
	/** The x-coordinate location of the enemy. */
	protected int enemyX;
	
	/** The y-coordinate location of the enemy. */
	protected int enemyY;
	
	/** Specifies the type of enemy this is. */
	protected String enemyType;
	
	/** The direction that the enemy moves towards. */
	protected String moveDirection;
	
	/**
	 * Constructor for the Enemy class.
	 * @param enemyX The x-coordinate location of the enemy.
	 * @param enemyY The y-coordinate location of the enemy.
	 * @param type The type of enemy this is.
	 * @param moveDirection The direction that the enemy moves towards/starting position.
	 */
	public Enemy(int enemyX, int enemyY, String moveDirection) {
		this.enemyX = enemyX;
		this.enemyY = enemyY;
		this.moveDirection = moveDirection;
		this.enemyType = this.getClass().getSimpleName();
	}
	
	/**
	 * Gets a string of the Enemy's full details for file saving.
	 * @return A string of the Enemy's full details.
	 */
	public String toStringDetail() {
		String strType = "";
		switch (enemyType) {
			case "StraightEnemy":
				strType = "STRAIGHT";
				break;
			case "WallEnemy":
				strType = "WALL";
				break;
			case "DumbEnemy":
				strType = "DUMB";
				break;
			case "SmartEnemy":
				strType = "SMART";
				break;
		}
		String strEnemy = "ENEMY," + enemyX + ":" + enemyY + ":" + strType + ":" + moveDirection + ",";
		return strEnemy;
	}
	
	/**
	 * Gets the enemy's current x-coordinate location.
	 * @return The x-coordinate as an integer.
	 */
	public int getX() {
		return enemyX;
	}
	
	/**
	 * Sets the x-coordinate location of the enemy.
	 * @param enemyX The x-coordinate to be set.
	 */
	public void setX(int enemyX) {
		this.enemyX = enemyX;
	}
	
	/**
	 * Gets the enemy's current y-coordinate location.
	 * @return The y-coordinate as an integer.
	 */
	public int getY() {
		return enemyY;
	}
	
	/**
	 * Sets the y-coordinate location of the enemy.
	 * @param enemyY The y-coordinate to be set.
	 */
	public void setY(int enemyY) {
		this.enemyY = enemyY;
	}
	
	/**
	 * Gets the enemy's type (StraightEnemy, DumbEnemy, etc.).
	 * @return The enemy type as a string.
	 */
	public String getType() {
		return enemyType;
	}
	
	/**
	 * Gets the direction that the enemy moves towards.
	 * @return The movement direction as a string.
	 */
	public String getDirection() {
		return moveDirection;
	}
	
	/**
	 * Sets the direction that the enemy moves towards.
	 * @param moveDirection The direction as a string. 
	 */
	protected void setDirection(String moveDirection) {
		this.moveDirection = moveDirection;
	}
	
	/**
	 * Determines an enemy's move based on its current location.
	 * Used by Straight and Wall enemies.
	 * @param levelElements An array holding all the elements in the level.
	 */
	public void move(String[][] levelElements) {
		System.out.println("MOVE FUNCTION HAS NOT BEEN OVERRIDDEN!");
	}
	
	/**
	 * Determines an enemy's move based on it's current location and the player's location.
	 * An alternative version used by Dumb and Smart enemies.
	 * @param levelElements An array holding all the elements in the level.
	 */
	public void move(String[][] levelElements, int playerX, int playerY) {
		System.out.println("MOVE FUNCTION HAS NOT BEEN OVERRIDDEN!");
	}
	
	/**
	 * Checks if the passed in string is an object or a floor.
	 * @param object The level object as a string. 
	 * @return True if its an object, otherwise false (if it's clear).
	 */
	protected boolean isObject(String object) {
		switch (object) {
			case "W":
			case "G":
			case "A":
			case "I":
			case "D":
			case "T":
			case "P":
			case "H":
			case "E":
			case "V": // Represents a visited cell (path finding).
				return true;
			default:
				return false;
		}
	}
}