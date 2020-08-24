/**
 * Models an enemy in the game.
 * @author William King
 */
public class Enemy {
	/** The x-coordinate location of the enemy. */
	private int enemyX;
	
	/** The y-coordinate location of the enemy. */
	private int enemyY;
	
	/** Specifies the type of enemy this is. */
	private EnemyType type;
	
	/** The direction that the enemy moves towards (only for straight enemies). */
	private String moveDirection;
	
	/**
	 * Constructor for the Enemy class.
	 * @param enemyX The x-coordinate location of the enemy.
	 * @param enemyY The y-coordinate location of the enemy.
	 * @param type The type of enemy this is.
	 * @param moveDirection The direction that the enemy moves towards.
	 */
	public Enemy(int enemyX, int enemyY, EnemyType type, String moveDirection) {
		this.enemyX = enemyX;
		this.enemyY = enemyY;
		this.type = type;
		this.moveDirection = moveDirection;
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
	 * Gets the enemy's type (Straight, dumb, smart, etc.).
	 * @return The enemy type as an enum.
	 */
	public EnemyType getType() {
		return type;
	}
	
	/**
	 * Gets the direction that the enemy moves towards (only for straight enemies).
	 * @return The movement direction as a string.
	 */
	public String getDirection() {
		return moveDirection;
	}
	
	/**
	 * Sets the direction that the enemy moves towards.
	 * @param moveDirection The direction as a string. 
	 */
	public void setDirection(String moveDirection) {
		this.moveDirection = moveDirection;
	}
	
	/**
	 * Determines the straight enemy's move based on its current location.
	 * @param level An array holding all the elements of the level.
	 */
	public void moveStraightEnemy(String[][] level) {
		// Set them as the previous values. Only one needs to change.
		int newX = enemyX;
		int newY = enemyY;
		
		// Clear the enemy from it's previous position.
		level[newY][newX] = " ";
		
		switch (moveDirection) {
			case "UP":
				newY--;
				break;
			case "DOWN":
				newY++;
				break;
			case "LEFT":
				newX--;
				break;
			case "RIGHT":
				newX++;
				break;
		}
		
		// Check if they've hit a wall/object. 
		String object = level[newY][newX];
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
				// Change direction if they hit an object.
				switch (moveDirection) {
					case "UP":
						moveDirection = "DOWN";
						break;
					case "DOWN":
						moveDirection = "UP";
						break;
					case "LEFT":
						moveDirection = "RIGHT";
						break;
					case "RIGHT":
						moveDirection = "LEFT";
						break;
				}
				// Run the method again with the changed direction.
				// With this method, the code will run indefinitely if the 
				// enemy is placed in a place where it's impossible to move.
				moveStraightEnemy(level);
				break;
			// Move to the new position.
			default:
				enemyX = newX;
				enemyY = newY;
				level[newY][newX] = "E";
		}
	}
}
