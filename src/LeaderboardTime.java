/**
 * Models a leader board time object holding the completion time
 * of a level or overall time.
 * @author William King
 */
public class LeaderboardTime {
	/** Username of the player who's time was recorded. */
	private String username;
	
	/** Completion time of the level (or overall game time) in milliseconds. */
	private long completionTime;
	
	/**
	 * Constructor for the LeaderboardTime class.
	 * @param username Username of the player who's time was recorded
	 * @param completionTime Completion time of the level (or overall game time).
	 */
	public LeaderboardTime(String username, long completionTime) {
		this.username = username;
		this.completionTime = completionTime;
	}
	
	/**
	 * Gets a string of the Leaderboard Time's full details for file saving.
	 * @return A string of the Leaderboard Time's full details.
	 */
	public String toStringDetail() {
		String strTime = username + "," + completionTime + ",";
		return strTime;
	}
	
	/**
	 * Gets the username of the player who's time was recorded.
	 * @return The username as a string.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the completion time of the level (or overall game time).
	 * @return The completion time as a long value (milliseconds).
	 */
	public long getTime() {
		return completionTime;
	}
}
