import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the Save Game prompt.
 * @author William King
 */
public class SaveGameController {
	/** The character limit for the description text field. */
	private final int DESCRIPTION_CHAR_LIMIT = 30;
	/** Holds the player's username. */
	private String username;
	/** The current state of the level to be saved. */
	private Level level;
	
	/** The text field used to hold the save state description. */
	@FXML private TextField txtDescription;
	/** The quit button for the page. */
	@FXML private Button btnBack;
	
	/**
	 * Saves the current state of the game with the inputted description.
	 */
	public void saveButtonAction() {
		String description = txtDescription.getText().trim();
		// If a description wasn't entered.
		if (description.isEmpty()) {
			Alerts.descriptionNotEntered();
			return;
		}
		
		// Save the game.
		String strSaveState = username + "," + description + "," + level.toStringDetail();
		FileHandling.saveGameState(strSaveState);
		Alerts.gameSaved();
		backButtonAction(); // Then close the page.
	}
	
	/**
	 * Sets a hard character limit to the description text field. 
	 */
	public void textFieldLimit() {
		txtDescription.setOnKeyTyped(event -> {
	        if (txtDescription.getText().length() > DESCRIPTION_CHAR_LIMIT) {
	        	event.consume();
	        }
	    });
	}
	
	/**
	 * Sets the username of the player saving the game.
	 * @param username The player's username as a string.
	 */
	public void setPlayerUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Sets the current state of the level to be saved.
	 * @param level The level to be set.
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	
	/**
	 * Closes this page.
	 */
	public void backButtonAction() {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		stage.close();
	}
}
