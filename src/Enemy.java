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
				// With this method, the code will run indefinitely if the 
				// enemy is placed in a place where it's impossible to move.
				moveStraightEnemy(levelElements);
				break;
			// Move to the new position.
			default:
				enemyX = newX;
				enemyY = newY;
				levelElements[newY][newX] = "E";
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
		
		// Indexes of the left/right/front of the enemy (before moving).
		// Used to check if there's a wall next to it or in front of it.
		// Set it with the original values.
		int leftX = enemyX;
		int leftY = enemyY;
		int rightX = enemyX;
		int rightY = enemyY;
		int frontX = enemyX;
		int frontY = enemyY;
		
		// Clear the enemy from it's previous position.
		levelElements[enemyY][enemyX] = " ";
		
		// Calculate indexes based on the enemy's current position.
		switch (moveDirection) {
			case "UP":
				newY--;
				leftX--;
				rightX++;
				frontY = newY;
				break;
			case "DOWN":
				newY++;
				leftX--;
				rightX++;
				frontY = newY;
				break;
			case "LEFT":
				newX--;
				leftY++;
				rightY--;
				frontX = newX;
				break;
			case "RIGHT":
				newX++;
				leftY--;
				rightY++;
				frontX = newX;
				break;
		}
		
		// Check if there's an object in front or adjacent to the enemy. 
		String leftObject = levelElements[leftY][leftX];
		String rightObject = levelElements[rightY][rightX];
		String frontObject = levelElements[frontY][frontX];
		
		boolean isLeftObject = false;
		boolean isRightObject = false;
		boolean isFrontObject = false;
		
		switch (leftObject) {
			case "W":
			case "G":
			case "A":
			case "I":
			case "D":
			case "T":
			case "P":
			case "H":
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
				isFrontObject = true;
				break;
		}
		
		// This set of IF conditions change the enemy's direction depending
		// how they are blocked. If the enemy isn't blocked, then it will move.
		// Change to opposite direction if they hit a dead end.
		if (isLeftObject && isRightObject && isFrontObject) {
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
		// If there's an object in front (but nothing else).
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
}
