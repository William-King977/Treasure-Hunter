import java.util.Random;

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
	
	/** The direction that the enemy moves towards. */
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
	public void setDirection(String moveDirection) {
		this.moveDirection = moveDirection;
	}
	
	/**
	 * Determines a straight enemy's move based on its current location.
	 * @param levelElements An array holding all the elements in the level.
	 */
	public void moveStraightEnemy(String[][] levelElements) {
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
		boolean isBackObject = false;
		switch (backObject) {
			case "W":
			case "G":
			case "A":
			case "I":
			case "D":
			case "T":
			case "P":
			case "H":
			case "E":
				isBackObject = true;
				break;
		}
		
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
					moveStraightEnemy(levelElements);
				}
				break;
			// Move to the new position.
			default:
				enemyX = newX;
				enemyY = newY;
				levelElements[enemyY][enemyX] = "E";
		}
	}
	
	/**
	 * Determines a wall enemy's move based on its current location.
	 * @param levelElements An array holding all the elements in the level.
	 */
	public void moveWallEnemy(String[][] levelElements) {
		// Set them as the previous values. Only one needs to change.
		int newX = enemyX;
		int newY = enemyY;
		
		// Indexes of the left/right/front/back of the enemy (before moving).
		// Used to check if there's a wall next to it, in front or behind it.
		// Set it with the original values.
		int leftX = enemyX;
		int leftY = enemyY;
		int rightX = enemyX;
		int rightY = enemyY;
		
		int frontX = enemyX;
		int frontY = enemyY;
		int backX = enemyX;
		int backY = enemyY;
		
		// Clear the enemy from it's previous position.
		levelElements[enemyY][enemyX] = " ";
		
		// Calculate indexes based on the enemy's current position.
		switch (moveDirection) {
			case "UP":
				newY--;
				leftX--;
				rightX++;
				frontY = newY;
				backY++;
				break;
			case "DOWN":
				newY++;
				leftX--;
				rightX++;
				frontY = newY;
				backY--;
				break;
			case "LEFT":
				newX--;
				leftY++;
				rightY--;
				frontX = newX;
				backX++;
				break;
			case "RIGHT":
				newX++;
				leftY--;
				rightY++;
				frontX = newX;
				backX--;
				break;
		}
		
		// Check if there's an object in front, behind or adjacent to the enemy. 
		String leftObject = levelElements[leftY][leftX];
		String rightObject = levelElements[rightY][rightX];
		String frontObject = levelElements[frontY][frontX];
		String backObject = levelElements[backY][backX];
		
		boolean isLeftObject = false;
		boolean isRightObject = false;
		boolean isFrontObject = false;
		boolean isBackObject = false;
		
		switch (leftObject) {
			case "W":
			case "G":
			case "A":
			case "I":
			case "D":
			case "T":
			case "P":
			case "H":
			case "E":
				isLeftObject = true;
				break;
		}
		switch (rightObject) {
			case "W":
			case "G":
			case "A":
			case "I":
			case "D":
			case "T":
			case "P":
			case "H":
			case "E":
				isRightObject = true;
				break;
		}
		switch (frontObject) {
			case "W":
			case "G":
			case "A":
			case "I":
			case "D":
			case "T":
			case "P":
			case "H":
			case "E":
				isFrontObject = true;
				break;
		}
		switch (backObject) {
			case "W":
			case "G":
			case "A":
			case "I":
			case "D":
			case "T":
			case "P":
			case "H":
			case "E":
				isBackObject = true;
				break;
		}
		
		// This set of IF conditions change the enemy's direction depending
		// how they are blocked. If the enemy isn't blocked, then it will move.
		// If the enemy is trapped.
		if (isLeftObject && isRightObject && isFrontObject && isBackObject) {
			// Add the 'E' back to the enemy's position (doesn't move).
			levelElements[enemyY][enemyX] = "E";
		// Change to opposite direction if they hit a dead end.
		} else if (isLeftObject && isRightObject && isFrontObject) {
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
			moveWallEnemy(levelElements);
		// If they hit a left corner.
		} else if (isFrontObject && isLeftObject) {
			switch (moveDirection) {
			case "UP":
				moveDirection = "RIGHT";
				break;
			case "DOWN":
				moveDirection = "RIGHT";
				break;
			case "LEFT":
				moveDirection = "UP";
				break;
			case "RIGHT":
				moveDirection = "DOWN";
				break;
			}
			moveWallEnemy(levelElements);
		// If they hit a right corner.
		} else if (isFrontObject && isRightObject) {
			switch (moveDirection) {
				case "UP":
					moveDirection = "LEFT";
					break;
				case "DOWN":
					moveDirection = "LEFT";
					break;
				case "LEFT":
					moveDirection = "DOWN";
					break;
				case "RIGHT":
					moveDirection = "UP";
					break;
			}
			moveWallEnemy(levelElements);
		// If there's an object in front (and nothing else).
		} else if (isFrontObject) {
			// Used to generate a random index to make the enemy turn in
			// 2 possible directions. This avoids the wall enemy from becoming 
			// a straight enemy in certain cases.
			Random rand  = new Random();
			String[] possibleDirections = {"UP", "DOWN", "LEFT", "RIGHT"};
			int randomNum;
			
			switch (moveDirection) {
				case "UP":
					randomNum = 2 + rand.nextInt(2); // Move left or right.
					moveDirection = possibleDirections[randomNum];
					break;
				case "DOWN":
					randomNum = 2 + rand.nextInt(2);
					moveDirection = possibleDirections[randomNum];
					break;
				case "LEFT":
					randomNum = rand.nextInt(2); // Move up or down.
					moveDirection = possibleDirections[randomNum];
					break;
				case "RIGHT":
					randomNum = rand.nextInt(2);
					moveDirection = possibleDirections[randomNum];
					break;
			}
			moveWallEnemy(levelElements);
		// If it's a clear path.
		} else {
			enemyX = newX;
			enemyY = newY;
			levelElements[enemyY][enemyX] = "E";
		}
	}
	
	/**
	 * Determines a dumb enemy's move based on its current location and the player's location.
	 * @param levelElements An array holding all the elements in the level.
	 * @param playerX The x-coordinate location of the player.
	 * @param playerY The y-coordinate location of the player.
	 */
	public void moveDumbEnemy(String[][] levelElements, int playerX, int playerY) {
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
