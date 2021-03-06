package data;

import java.util.concurrent.TimeUnit;

/**
 * Models a leader board time object holding the completion time
 * of a level or overall time.
 * @author William King
 */
public class LeaderboardTime implements Comparable<LeaderboardTime> {
	/** One minute in milliseconds. */
	private final long ONE_MINUTE_MILLIS = 60000;
	/** One hour in milliseconds. */
	private final long ONE_HOUR_MILLIS = 3600000;
	
	/** Username of the player who's time was recorded. */
	private String username;
	
	/** Completion time of the level (or overall game time) in milliseconds. */
	private long completionTime;
	
	/** The completion time formatted into a readable format. */
	private String formattedTime;
	
	/** The player's rank on the leaderboard. */
	private int rank;
	
	/**
	 * Constructor for the LeaderboardTime class.
	 * @param username Username of the player who's time was recorded
	 * @param completionTime Completion time of the level (or overall game time).
	 */
	public LeaderboardTime(String username, long completionTime) {
		this.username = username;
		this.completionTime = completionTime;
		formatCompletionTime(); // Format the completion time.
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
	
	/**
	 * Gets the formatted completion time.
	 * @return The formatted time as a string.
	 */
	public String getFormattedTime() {
		return formattedTime;
	}
	
	/**
	 * Gets the player's rank for their completion time.
	 * @return The rank as an integer.
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Sets the player's rank on the leaderboard.
	 * @param rank The rank to be set as an integer.
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	/**
	 * Converts the completion time from milliseconds to a readable format 
	 * depending on the amount of time recorded. 
	 */
	private void formatCompletionTime() {
		// For now, it assumes it doesn't take more than 24 hours to complete
		// the whole game (or a level...).
		// Divide the time into hours, minutes and seconds.
		long hours = TimeUnit.MILLISECONDS.toHours(completionTime) % 24;
		long minutes = TimeUnit.MILLISECONDS.toMinutes(completionTime) % 60;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(completionTime) % 60;
		
		if (completionTime >= ONE_HOUR_MILLIS) {
			formattedTime = String.format("%d hr : %d min : %d sec", 
					hours, minutes, seconds);
		} else if (completionTime >= ONE_MINUTE_MILLIS) {
			formattedTime = String.format("%d min : %d sec", minutes, seconds);
		} else {
			formattedTime = String.format("%d sec", seconds);
		}
	}
	
	/**
	 * Comparison method used to sort the leaderboard times in ascending order.
	 * @param otherTime The other LeaderboardTime object being compared with 
	 *                  this object.
	 * @return A positive integer if this > otherTime, otherwise a negative integer.
	 */
	public int compareTo(LeaderboardTime otherTime) {
		return (int) (this.getTime() - otherTime.getTime());
	}
}