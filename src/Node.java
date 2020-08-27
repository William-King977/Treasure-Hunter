/**
 * This class models a node to hold the details of a position of a level.
 * Used for the smart enemy path finding.
 * @author William King
 */
public class Node {
	/** The x-coordinate location of the cell that this node represents. */
	private int nodeX;
	
	/** The y-coordinate location of the cell that this node represents. */
	private int nodeY;
	
	/** The cost of this node. */
	private int cost;
	
	/**The heuristic value to determine how close this node is to the solution. */
	private int heuristicValue;
	
	/** The ancestor node of this node. */
	private Node previousNode;
	
	/**
	 * Constructor for the Node class.
	 * @param nodeX The x-coordinate location of the cell that this node represents.
	 * @param nodeY The y-coordinate location of the cell that this node represents.
	 * @param cost The cost of this node. 
	 * @param previousNode The ancestor node of this node.
	 */
	public Node(int nodeX, int nodeY, int cost, Node previousNode) {
		this.nodeX = nodeX;
		this.nodeY = nodeY;
		this.cost = cost;
		this.previousNode = previousNode;
	}

	/**
	 * Gets the x-coordinate location of the cell that this node represents.
	 * @return The x-coordinate as an integer.
	 */
	public int getX() {
		return nodeX;
	}

	/**
	 * Gets the x-coordinate location of the cell that this node represents.
	 * @return The y-coordinate as an integer.
	 */
	public int getY() {
		return nodeY;
	}
	
	/**
	 * Gets the cost of this node.
	 * @return The cost as an integer.
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Gets the ancestor node of this node.
	 * @return The ancestor node as a node.
	 */
	public Node getPreviousNode() {
		return previousNode;
	}
	
	/**
	 * Gets the heuristic value of this node.
	 * @return The heuristic value as an integer.
	 */
	public int getHeuristicValue() {
		return heuristicValue;
	}
	
	/**
	 * Sets the heuristic value of this node.
	 * @param heuristicValue The heuristic value to be set.
	 */
	public void setHeuristicValue(int heuristicValue) {
		this.heuristicValue = heuristicValue;
	}
}
