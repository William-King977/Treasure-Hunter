package data;

/**
 * Models a Dumb Targeting Enemy in the game. Moves directly towards the 
 * player. They don't consider obstacles in the way and can easily get stuck.
 * @author William King
 */
public class DumbEnemy extends Enemy {
	
	/**
	 * Constructor for the Dumb Targeting Enemy.
	 * @param The x-coordinate location of the enemy.
	 * @param enemyY The y-coordinate location of the enemy.
	 * @param moveDirection The starting direction the enemy.
	 */
	public DumbEnemy(int enemyX, int enemyY, String moveDirection) {
		super(enemyX, enemyY, moveDirection);
	}
	
	/**
	 * Determines a dumb enemy's move based on its current location and the player's location.
	 * @param levelElements An array holding all the elements in the level.
	 * @param playerX The x-coordinate location of the player.
	 * @param playerY The y-coordinate location of the player.
	 */
	public void move(String[][] levelElements, int playerX, int playerY) {
		// Calculate difference between the x and y.
		// Move the smallest one (move left/right if x is smaller etc.).
		// If it's blocked, move by the other axis.
		// Otherwise, it doesn't move.
		
		int diffX = enemyX - playerX;
		int diffY = enemyY - playerY;
		
		int newX = enemyX;
		int newY = enemyY;
		
		boolean xChanged = false;
		boolean yChanged = false;
		
		// Clear the enemy from it's previous position.
		levelElements[enemyY][enemyX] = " ";
		
		// If an index is the same (as the player), then change the other.
		// NOTE: If the axis difference is 0, then it doesn't need to be checked (for an object).
		if (diffY == 0) {
			if (diffX < 0) {
				newX++;
			} else {
				newX--;
			}
		} else if (diffX == 0) {
			if (diffY < 0) {
				newY++;
			} else {
				newY--;
			}
		// Otherwise, change index that's closer to 0 first.
		} else if ((diffX <= diffY) && !(diffX == 0)) {
			// If it's negative.
			if (diffX < 0) {
				newX++;
			} else {
				newX--;
			}
			// Used to switch to the other move if this one is blocked.
			xChanged = true;
		} else if ((diffY <= diffX) && !(diffY == 0)) {
			if (diffY < 0) {
				newY++;
			} else {
				newY--;
			}
			yChanged = true;
		}
		
		// Check if an object is blocking the enemy.
		String object = levelElements[newY][newX];
		boolean isObject = false;
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
				// Check the other axis if the intended one is blocked.
				if (xChanged) {
					newX = enemyX;
					if (diffY < 0) {
						newY++;
					} else {
						newY--;
					}
					isObject = true;
				} else if (yChanged) {
					newY = enemyY;
					if (diffX < 0) {
						newX++;
					} else {
						newX--;
					}
					isObject = true;
				} 
				
				// If the intended axis is blocked, check the other one.
				if (isObject) {
					String newObject = levelElements[newY][newX];
					switch (newObject) {
						case "W":
						case "G":
						case "A":
						case "I":
						case "D":
						case "T":
						case "P":
						case "H":
						case "E":
							// The enemy is blocked in both directions if it gets to here.
							// Add the 'E' back to the enemy's position (doesn't move).
							levelElements[enemyY][enemyX] = "E";
							break;
					// If the other axis is clear.
					default:
						enemyX = newX;
						enemyY = newY;
						levelElements[enemyY][enemyX] = "E";
					}
				// If the intended axis is blocked, but the other axis difference is 0.
				} else {
					levelElements[enemyY][enemyX] = "E";
				}
				break;
			// If the intended axis is clear.
			default:
				enemyX = newX;
				enemyY = newY;
				levelElements[enemyY][enemyX] = "E";
				break;
		}
	}
}
