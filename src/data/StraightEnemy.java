package data;

/**
 * Models a Straight Line Enemy in the game.  Goes in a single direction and 
 * goes the opposite direction when they hit a wall.
 * @author William King
 */
public class StraightEnemy extends Enemy {
	
	/**
	 * Constructor for the Straight Line Enemy.
	 * @param The x-coordinate location of the enemy.
	 * @param enemyY The y-coordinate location of the enemy.
	 * @param moveDirection The direction that the enemy moves towards.
	 */
	public StraightEnemy(int enemyX, int enemyY, String moveDirection) {
		super(enemyX, enemyY, moveDirection);
	}
	
	/**
	 * Determines a straight enemy's move based on its current location.
	 * @param levelElements An array holding all the elements in the level.
	 */
	public void move(String[][] levelElements) {
		// Set them as the previous values. Only one needs to change.
		int newX = enemyX;
		int newY = enemyY;
		
		// Clear the enemy from it's previous position.
		levelElements[newY][newX] = " ";
		
		// Used to check if there's anything behind the enemy.
		int backX = enemyX;
		int backY = enemyY;
		
		// Calculate indexes based on the enemy's direction.
		switch (moveDirection) {
			case "UP":
				newY--;
				backY++;
				break;
			case "DOWN":
				newY++;
				backY--;
				break;
			case "LEFT":
				newX--;
				backX++;
				break;
			case "RIGHT":
				newX++;
				backX--;
				break;
		}
		
		// Check if there's an object behind the enemy.
		String backObject = levelElements[backY][backX];
		boolean isBackObject = isObject(backObject);
		
		// Check if the enemy has hit an object.
		String object = levelElements[newY][newX];
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
				// Enemy doesn't move if it's trapped (objects in between it).
				if (isBackObject) {
					levelElements[enemyY][enemyX] = "E";
				} else {
					// Change to opposite direction if they hit an object.
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
					move(levelElements);
				}
				break;
			// Move to the new position.
			default:
				enemyX = newX;
				enemyY = newY;
				levelElements[enemyY][enemyX] = "E";
		}
	}
}
