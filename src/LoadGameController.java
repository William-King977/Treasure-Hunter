import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	/** An array list holding the save states that the user has made. */
	private ArrayList<GameState> gameStates;
	
	/** A list view to show the save states. */
	@FXML private ListView<String> lstGameStates;
	/** A text field that shows the game level of a save state. */
	@FXML private TextField txtLevel;
	/** A text field that shows date that the save state was made. */
	@FXML private TextField txtDate;
	/** A text field that shows time that the save state was made. */
	@FXML private TextField txtTime;
	/** The load button deletes the selected game state. */
	@FXML private Button btnDelete;
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
		
		// Fetch the selected game state.
		GameState selectedState = gameStates.get(selectedIndex);
		
		// Enable the load and delete button, then show additional information 
		// about the save state.
		btnLoad.setDisable(false);
		btnDelete.setDisable(false);
		txtLevel.setText(selectedState.getLevel().getLevelNumber() + "");
		txtDate.setText(selectedState.getSaveDate());
		txtTime.setText(selectedState.getSaveTime());
	}
	
	/**
	 * Loads the selected save state and opens the game.
	 */
	public void loadButtonAction() {
		// Fetch the selected game state from the array.
		int selectedIndex = lstGameStates.getSelectionModel().getSelectedIndex();
		GameState selectedState = gameStates.get(selectedIndex);
		
		// Get the elements required to start the game.
		Level level = selectedState.getLevel();
		long currentLevelTime = selectedState.getCurrentLevelTime();
		long currentGameTime = selectedState.getCurrentGameTime();
		boolean timeValid = selectedState.getTimeValid();
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource("FXMLFiles/GameWindow.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			
			// Gets the controller for the FXML file.
			GameController gameWindow = fxmlLoader.<GameController> getController();
			
			// Pass down the user, level and time elements from the save state.
			gameWindow.setCurrentUser(currentUser);
			gameWindow.loadGameState(level, currentLevelTime, currentGameTime);
			gameWindow.setTotalTimeValid(timeValid);
			
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
	 * Deletes a selected save state.
	 */
	public void deleteButtonAction() {
		// Alert message for confirmation.
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Save State.");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete this save state?");
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		
		// Handles the button press event (Yes, No).
		alert.showAndWait().ifPresent(e -> {
			if (e == ButtonType.YES) {
				// Get the selected save state, then delete it.
				int selectedIndex = lstGameStates.getSelectionModel().getSelectedIndex();
				GameState selectedState = gameStates.get(selectedIndex);
				
				FileHandling.deleteSaveState(selectedState.toStringDetail());
				// Delete it locally as well.
				gameStates.remove(selectedIndex);
				lstGameStates.getItems().remove(selectedIndex);
				refresh();
			}
		});
	}
	
	/**
	 * Refreshes the load game page after deleting a game state.
	 */
	public void refresh() {
		// Clear the text fields and disable both buttons.
		txtLevel.clear();
		txtDate.clear();
		txtTime.clear();
		
		btnLoad.setDisable(true);
		btnDelete.setDisable(true);
	}
	
	/**
	 * Displays the description of each saved game state on the list view.
	 */
	public void showGameStates() {
		String username = currentUser.getUsername();
		gameStates = FileHandling.getGameStates(username);
		
		for (int i = 0; i < gameStates.size(); i++) {
			lstGameStates.getItems().add((i + 1) + ": " + gameStates.get(i).getDescription());
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
