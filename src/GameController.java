import java.util.ArrayList;

/**
 * Controller for the Game screen.
 * Controls what is shown as the player moves and interacts with objects.
 * @author William King
 */
public class GameController {
	
	/** An arraylist holding all the levels. */
	private ArrayList<Level> levels;
	
	/**
	 * Sets up the level array list. 
	 * This method will run automatically.
	 */
	public void initialize() {
		levels = FileHandling.getLevels();
	}
	
	// Draw level?
}
