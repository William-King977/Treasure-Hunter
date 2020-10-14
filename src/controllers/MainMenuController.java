package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import data.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controller for the Main Menu.
 * The user can access the main features of the game here.
 * @author William King
 */
public class MainMenuController {
	/** Title for the Login page. */
	private final String LOGIN_TITLE = "The Game";
	/** Stores the details of the logged in user. */
	private User currentUser;
	/** An array list holding the save states that the user has made. */
	private ArrayList<GameState> gameStates;
	
	/** A button that opens the latest state of the game. */
	@FXML private Button btnContinue;
	/** A button that opens a state starting at level 1. */
	@FXML private Button btnNewGame;
	/** A button that opens a menu of saved states. */
	@FXML private Button btnLoadGame;
	/** A button that opens a menu of levels. */
	@FXML private Button btnLevelSelect; 
	/** A button that opens the leaderboard menu. */
	@FXML private Button btnLeaderboards;
	/** A button that deletes the user's profile. */
	@FXML private Button btnDeleteProfile;
	/** The logout button for the main menu. */
	@FXML private Button btnLogout;
	
	/**
	 * Fetches the details of the logged in user, their game states
	 * and adjusts the accessibility of certain buttons.
	 * This method will run automatically.
	 */
	public void initialize() {
		String currentUsername = FileHandling.getCurrentUser();
		LinkedHashMap<String, User> users = FileHandling.getUsers();
		currentUser = users.get(currentUsername);
		gameStates = FileHandling.getGameStates(currentUsername);
		
		// Enable the continue and load game buttons if the user
		// has any existing save states.
		if (gameStates.size() > 0) {
			btnContinue.setDisable(false);
			btnLoadGame.setDisable(false);
		}
	}
	
	/**
	 * Loads the user's last saved state.
	 */
	public void continueButtonAction() {
		// Fetch the user's latest game state.
		GameState selectedState = gameStates.get(gameStates.size() - 1);
		
		// Get the elements required to start the game.
		Level level = selectedState.getLevel();
		long currentLevelTime = selectedState.getCurrentLevelTime();
		long currentGameTime = selectedState.getCurrentGameTime();
		boolean timeValid = selectedState.getTimeValid();
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource(Main.FXML_FILE_PATH + "GameWindow.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			
			// Gets the controller for the FXML file.
			GameController gameWindow = fxmlLoader.<GameController> getController();
			
			// Pass down the user, level and time elements from the save state.
			gameWindow.setCurrentUser(currentUser);
			gameWindow.loadGameState(level, currentLevelTime, currentGameTime);
			gameWindow.setTotalTimeValid(timeValid);
			gameWindow.setGameController(gameWindow);
			
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			// primaryStage.setTitle(THE_GAME_TITLE);
			primaryStage.show();
			
			// Close the load game window.
			Stage stage = (Stage) btnContinue.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
			// file is not found.
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	/**
	 * Loads the first level.
	 */
	public void newGameButtonAction() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource(Main.FXML_FILE_PATH + "GameWindow.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			
			// Gets the controller for the FXML file.
			GameController gameWindow = fxmlLoader.<GameController> getController();
			
			// Adjust the level number parameter.
			// NOTE: Restart level basically loads the level AFTER
			// the level number is set. The Initialize method runs first, which
			// causes the level number to be always 0.
			gameWindow.setCurrentUser(currentUser);
			gameWindow.setLevelNumber(1);
			gameWindow.setTotalTimeValid(true);
			gameWindow.setGameController(gameWindow);
			gameWindow.startGame();
			
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			// primaryStage.setTitle(THE_GAME_TITLE);
			primaryStage.show();
			
			// Close the main menu.
			Stage stage = (Stage) btnNewGame.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
			// file is not found.
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Displays a menu of saved states that the user can continue from.
	 */
	public void loadGameButtonAction() {	
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource(Main.FXML_FILE_PATH + "LoadGameWindow.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			
			// Gets the controller for the FXML file.
			LoadGameController loadGame = fxmlLoader.<LoadGameController> getController();
			// Pass down the user.
			loadGame.setCurrentUser(currentUser);
			loadGame.setGameStates(gameStates);
			loadGame.showGameStates();
			
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			// primaryStage.setTitle(LOAD_GAME_TITLE);
			primaryStage.show();
			
			// Close the main menu.
			Stage stage = (Stage) btnLoadGame.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
			// file is not found.
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Displays a menu of levels that are available for the user to play.
	 */
	public void levelSelectButtonAction() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource(Main.FXML_FILE_PATH + "LevelSelect.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			
			// Gets the controller for the FXML file.
			LevelSelectController levelSelectMenu = fxmlLoader.<LevelSelectController> getController();
			// Pass down the user, then adjust the available levels.
			levelSelectMenu.setCurrentUser(currentUser);
			levelSelectMenu.adjustAvailableLevels();
			
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			// primaryStage.setTitle(LEVEL_SELECT_TITLE);
			primaryStage.show();
			
			// Close the main menu.
			Stage stage = (Stage) btnLevelSelect.getScene().getWindow();
			stage.close();
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
			// file is not found.
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Displays the leaderboard showing the fastest completion times for each
	 * level and the game.
	 */
	public void leaderboardsButtonAction() {
		try {
			// Close the main menu.
			Stage stage = (Stage) btnLevelSelect.getScene().getWindow();
			stage.close();
			
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass()
					.getResource(Main.FXML_FILE_PATH + "Leaderboard.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			// primaryStage.setTitle(LEADERBOARD_TITLE);
			primaryStage.show();
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
			// file is not found.
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Deletes the user's profile and any data associated with the profile.
	 */
	public void deleteProfileButtonAction() {
		// Alert message for confirmation.
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Profile.");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete your profile?");
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		
		// Handles the button press event (Yes, No).
		alert.showAndWait().ifPresent(e -> {
			if (e == ButtonType.YES) {
				FileHandling.deleteProfile(currentUser.getUsername());
				logoutButtonAction();
			}
		});
	}
	
	/**
	 * Goes back to the login page.
	 */
	public void logoutButtonAction() {
		// Closes the window.
		Stage stage = (Stage) btnLogout.getScene().getWindow();
		stage.close();
		
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass()
					.getResource(Main.FXML_FILE_PATH + "Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(LOGIN_TITLE);
			primaryStage.show(); // Displays the new stage.
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
			// file is not found.
			e.printStackTrace();
			System.exit(-1);
		}	
	}
}