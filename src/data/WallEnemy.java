package data;

import java.util.Random;

/**
 * Models a wall following enemy. Follows the wall - just like following 
 * a maze with your hand on the wall at all times.
 * @author William King
 */
public class WallEnemy extends Enemy {
	
	/**
	 * Constructor for the Wall Following Enemy.
	 * @param The x-coordinate location of the enemy.
	 * @param enemyY The y-coordinate location of the enemy.
	 * @param moveDirection The direction that the enemy moves towards.
	 */
	public WallEnemy(int enemyX, int enemyY, String moveDirection) {
		super(enemyX, enemyY, moveDirection);
	}
	
	/**
	 * Determines a wall enemy's move based on its current location.
	 * @param levelElements An array holding all the elements in the level.
	 */
	public void move(String[][] levelElements) {
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
		
		boolean isLeftObject = isObject(leftObject);
		boolean isRightObject = isObject(rightObject);
		boolean isFrontObject = isObject(frontObject);
		boolean isBackObject = isObject(backObject);
		
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
			move(levelElements);
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
			move(levelElements);
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
			move(levelElements);
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
			move(levelElements);
		// If it's a clear path.
		} else {
			enemyX = newX;
			enemyY = newY;
			levelElements[enemyY][enemyX] = "E";
		}
	}
}