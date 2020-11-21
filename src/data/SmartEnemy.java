package data;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Models a Smart Targeting enemy in the game.
 * @author William King
 */
public class SmartEnemy extends Enemy {
	
	/**
	 * Constructor for the Smart Targeting Enemy.
	 * @param The x-coordinate location of the enemy.
	 * @param enemyY The y-coordinate location of the enemy.
	 * @param moveDirection The starting direction the enemy.
	 */
	public SmartEnemy(int enemyX, int enemyY, String moveDirection) {
		super(enemyX, enemyY, moveDirection);
	}
	
	/**
	 * Determines a smart enemy's move based on its current location and the player's location.
	 * @param levelElements An array holding all the elements in the level.
	 * @param playerX The x-coordinate location of the player.
	 * @param playerY The y-coordinate location of the player.
	 */
	public void move(String[][] levelElements, int playerX, int playerY) {
		// Make a (hard) copy of the level elements array.
		int levelHeight = levelElements.length;
		int levelWidth = levelElements[0].length; 
		String[][] lvlElementsCopy = new String[levelHeight][levelWidth];
		
		for (int i = 0; i < levelHeight; i++) {
			for (int j = 0; j < levelWidth; j++) {
				lvlElementsCopy[i][j] = levelElements[i][j];
			}
		}
		
		// Use an arraylist to find a path (i.e. check if the player is reachable).
		ArrayList<Node> currentCells = new ArrayList<Node>();
		ArrayList<Integer> nodeScores = new ArrayList<Integer>();
		boolean playerReachable = false;
		Node solutionNode = null; // Holds the last node
		
		// Add the enemy position first (as a node).
		Node startNode = new Node(enemyX, enemyY, 0, null);
		currentCells.add(startNode);
		lvlElementsCopy[enemyY][enemyX] = "V"; // Set as visited.
		
		// Enter a loop until a decision has been deduced.
		while (!playerReachable) {
			// If the player is unreachable.
			if (currentCells.size() == 0) {
				break;
			}
			
			// Get the scores for each current node.
			for (Node elem : currentCells) {
				int heuristicScore = getEuclideanHeuristic(elem, playerX, playerY);
				nodeScores.add(heuristicScore + elem.getCost());
			}
			
			// Get the current node with the smallest cost.
			int minIndex = nodeScores.indexOf(Collections.min(nodeScores));
			Node currentNode = currentCells.remove(minIndex);
			int nodeX = currentNode.getX();
			int nodeY = currentNode.getY();
			
			// If the top item is the player position.
			if ((nodeX == playerX) && (nodeY == playerY)) {
				playerReachable = true;
				solutionNode = currentNode;
				continue;
			} 
			
			// Check if there's an object in front, behind or adjacent to the enemy. 
			int leftX = nodeX - 1;
			int leftY = nodeY;
			int rightX = nodeX + 1;
			int rightY = nodeY;
			
			int frontX = nodeX;
			int frontY = nodeY - 1;
			int backX = nodeX;
			int backY = nodeY + 1;
			
			String leftObject = lvlElementsCopy[leftY][leftX];
			String rightObject = lvlElementsCopy[rightY][rightX];
			String frontObject = lvlElementsCopy[frontY][frontX];
			String backObject = lvlElementsCopy[backY][backX];
			
			boolean isLeftObject = isObject(leftObject);
			boolean isRightObject = isObject(rightObject);
			boolean isFrontObject = isObject(frontObject);
			boolean isBackObject = isObject(backObject);
			
			// If the right is clear and not visited.
			if (!isRightObject) {
				Node newNode = new Node(rightX, rightY, currentNode.getCost() + 1, currentNode);
				currentCells.add(newNode);
				lvlElementsCopy[rightY][rightX] = "V";
			}
			// If the left is clear and not visited.
			if (!isLeftObject) {
				Node newNode = new Node(leftX, leftY, currentNode.getCost() + 1, currentNode);
				currentCells.add(newNode);
				lvlElementsCopy[leftY][leftX] = "V";
			}
			// If the front is clear and not visited.
			if (!isFrontObject) {
				Node newNode = new Node(frontX, frontY, currentNode.getCost() + 1, currentNode);
				currentCells.add(newNode);
				lvlElementsCopy[frontY][frontX] = "V";
			}
			// If the back is clear and not visited.
			if (!isBackObject) {
				Node newNode = new Node(backX, backY, currentNode.getCost() + 1, currentNode);
				currentCells.add(newNode);
				lvlElementsCopy[backY][backX] = "V";
			}
		}
		
		// Move the smart enemy if the player is reachable.
		if (playerReachable) {
			// Hold the path of nodes in an arraylist.
			ArrayList<Node> solutionPath = new ArrayList<Node>();
			while (!(solutionNode.getAncestorNode() == null)) {
				solutionPath.add(solutionNode);
				solutionNode = solutionNode.getAncestorNode();
			}
			// The next move to be made is the last item in the arraylist.
			Node nextMove = solutionPath.get(solutionPath.size() - 1);
			levelElements[enemyY][enemyX] = " ";
			enemyX = nextMove.getX();
			enemyY = nextMove.getY();
			levelElements[enemyY][enemyX] = "E";
		// Otherwise, move it as if it were a dumb enemy.
		} else {
			moveDumbEnemy(levelElements, playerX, playerY);
		}
	}
	
	/**
	 * Gets the Euclidean distance between the node position and the player's
	 * position, and uses this as the heuristic.
	 * @param currentNode The node that the heuristic is calculated for.
	 * @param playerX The x-coordinate location of the player.
	 * @param playerY The y-coordinate location of the player.
	 * @return The calculated heuristic as an integer.
	 */
	private int getEuclideanHeuristic(Node currentNode, int playerX, int playerY) {
		int nodeX = currentNode.getX();
		int nodeY = currentNode.getY();
		
		// Calculate differences, then the Euclidean distance.
		int xSqrdDiff = (nodeX - playerX) * (nodeX - playerX);
		int ySqrdDiff = (nodeY - playerY) * (nodeY - playerY);
		int euclidDist = (int) Math.sqrt(xSqrdDiff + ySqrdDiff);
		
		return euclidDist;
	}
}
