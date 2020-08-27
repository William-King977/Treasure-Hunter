/**
 * An enum holding the different types of enemies.
 * @author William King
 */
public enum EnemyType {
	/** A straight line enemy. Goes in a single direction and goes the 
	 * opposite direction when they hit a wall. */
	STRAIGHT,
	
	/** A wall following enemy. Follows the wall - just like following a maze with
	 * your hand on the wall at all times. */
	WALL,
	
	/** A dumb following enemy. Moves directly towards the player. They don't
	 * consider obstacles in the way and can easily get stuck. */
	DUMB,
	
	/** A smart following enemy. Moves towards the player by finding the shortest
	 * path (and doesn't get stuck). If no path is possible, then it will move
	 * as if it were a dumb targeting enemy. */
	SMART,

}
