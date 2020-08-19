/**
 * This class constructs a Player model in the game and keeps
 * track of its current status.
 * @author William King
 */
public class Player {
	/** The x-coordinate of the player's current position. */
	private int playerX;
	
	/** The y-coordinate of the player's current position. */
	private int playerY;
	
	/**
	 * Constructor for the Player class.
	 * @param playerX The x-coordinate of the player's start position.
	 * @param playerY The y-coordinate of the player's start position.
	 */
	public Player(int playerX, int playerY) {
		this.setPlayerX(playerX);
		this.setPlayerY(playerY);
	}

	/**
	 * Gets the player's current x-coordinate position.
	 * @return The x-coordinate as an integer.
	 */
	public int getPlayerX() {
		return playerX;
	}
	
	/**
	 * Sets the player's x-coordinate position.
	 * @param playerX The x-coordinate as an integer.
	 */
	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}
	
	/**
	 * Gets the player's current y-coordinate position.
	 * @return The y-coordinate as an integer.
	 */
	public int getPlayerY() {
		return playerY;
	}
	
	/**
	 * Sets the player's y-coordinate position.
	 * @param playerY The y-coordinate as an integer.
	 */
	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}	
}
