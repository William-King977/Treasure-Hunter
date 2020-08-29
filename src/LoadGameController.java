import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controller for the Load Game window.
 * @author William King
 */
public class LoadGameController {
	/** Title for the main menu. */
	private final String MAIN_MENU_TITLE = "Main Menu";
	/** Stores the details of the logged in user. */
	private User currentUser;
	/** An array holding the save states that the user has made. */
	private GameState[] gameStates;
	
	/** A list view to show the save states. */
	@FXML private ListView<String> lstGameStates;
	/** The load button opens the selected game state. */
	@FXML private Button btnLoad;
	/** The back button for the page. */
	@FXML private Button btnBack;
	
	/**
	 * Determines when the Load button is enabled.
	 * The player must select a save state first.
	 */
	public void gameStateSelectAction() {
		int selectedIndex = lstGameStates.getSelectionModel().getSelectedIndex();
		
		// If the list view was clicked on (or if there are no items).
		if (selectedIndex < 0) {
			return;
		}
		btnLoad.setDisable(false);
	}
	
	/**
	 * Loads the selected save state and opens the game.
	 */
	public void loadButtonAction() {
		// Fetch the selected game state from the array.
		int selectedIndex = lstGameStates.getSelectionModel().getSelectedIndex();
		GameState selectedState = gameStates[selectedIndex];
		Level level = selectedState.getLevel(); // Get the level.
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource("FXMLFiles/GameWindow.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			
			// Gets the controller for the FXML file.
			GameController gameWindow = fxmlLoader.<GameController> getController();
			
			// Pass down the user and the level from the save state.
			gameWindow.setCurrentUser(currentUser);
			gameWindow.loadGameState(level);
			
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			// primaryStage.setTitle(THE_GAME_TITLE);
			primaryStage.show();
			
			// Close the load game window.
			Stage stage = (Stage) btnLoad.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
            // file is not found.
            e.printStackTrace();
            System.exit(-1);
		}
	}
	
	/**
	 * Displays the description of each saved game state on the list view.
	 */
	public void showGameStates() {
		String username = currentUser.getUsername();
		gameStates = FileHandling.getGameStates(username);
		
		for (int i = 0; i < gameStates.length; i++) {
			lstGameStates.getItems().add((i + 1) + ": " + gameStates[i].getDescription());
		}
	}
	
	/**
	 * Sets the user who is playing the game.
	 * @param user The user to be set.
	 */
	public void setCurrentUser(User user) {
		this.currentUser = user;
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
