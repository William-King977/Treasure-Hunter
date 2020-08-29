import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Controller for the Load Game window.
 * @author William King
 */
public class LoadGameController {
	/** Title for the main menu. */
	private final String MAIN_MENU_TITLE = "Main Menu";
	/** The username of the player. */
	private String username;
	/** An array holding the save states the user has made. */
	private GameState[] gameStates;
	
	/** A list view to show the save states. */
	@FXML private ListView<String> lstGameStates;
	/** The load button opens the selected game state. */
	@FXML private Button btnLoad;
	/** The back button for the page. */
	@FXML private Button btnBack;
	
	/**
	 * Sets up the array of game states that the user has made.
	 * This method runs automatically.
	 */
	public void initialize() {
		// Get save states made by the user.
		username = FileHandling.getCurrentUser();
		gameStates = FileHandling.getGameStates(username);
		
		for (GameState elem : gameStates) {
			lstGameStates.getItems().add(elem.getDescription());
		}
	}
	
	/**
	 * Loads the selected save state and opens the game.
	 */
	public void loadButtonAction() {
		
	}
	
	/**
	 * Closes this page, then opens the main menu. 
	 */
	public void backButtonAction() {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		stage.close();
		
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass()
					.getResource("FXMLFiles/MainMenu.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(MAIN_MENU_TITLE);
			primaryStage.show(); // Displays the new stage.
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
            // file is not found.
            e.printStackTrace();
            System.exit(-1);
		}
	}
}
