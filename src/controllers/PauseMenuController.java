package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import data.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PauseMenuController {
	/** Stores the details of the logged in user. */
	private User currentUser;
	/** An array list holding the save states that the user has made. */
	private ArrayList<GameState> gameStates;
	
	/** The current amount of time spent on the level (milliseconds). */
	private long currentLevelTime;
	/** The current time spent playing the game (milliseconds). */
	private long currentGameTime;
	/** If the player's total game time should be saved or not. */
	private boolean timeValid;
	/** The current state of the level. */
	private Level currentLevel;
	/** The instance of the game window. */
	private GameController gameWindow;
	/** The instance of the pause menu. */
	private PauseMenuController pauseMenu;
	
	/** The resume button for the menu. */
	@FXML private Button btnResume;
	/** The save game button for the menu. */
	@FXML private Button btnSaveGame;
	/** The load game button for the menu. */
	@FXML private Button btnLoadGame;
	/** The quit button for the menu. */
	@FXML private Button btnQuit;
	
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
			btnLoadGame.setDisable(false);
		}
	}
	
	/**
	 * Sets the elements required for saving a game state.
	 * @param level The current state of the level being passed in.
	 * @param username The player's username.
	 * @param currentLevelTime The current amount of time spent on the level (milliseconds).
	 * @param currentGameTime The current time spent playing the game (milliseconds).
	 * @param timeValid If the player's total game time should be saved or not.
	 * @param gameWindow The instance of the game window.
	 * @param pauseMenu The instance of the pause menu.
	 */
	public void setCurrentGameStatus(Level level, long currentLevelTime, 
			long currentGameTime, boolean timeValid, GameController gameWindow, 
			PauseMenuController pauseMenu) {
		this.currentLevel = level;
		this.currentLevelTime = currentLevelTime;
		this.currentGameTime = currentGameTime;
		this.timeValid = timeValid;
		this.gameWindow = gameWindow;
		this.pauseMenu = pauseMenu;
	}
	
	/**
	 * Closes the menu to resume the game.
	 */
	public void resumeButtonAction() {
		// Closes the window.
		Stage stage = (Stage) btnResume.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Opens a window that allows the user to save the current state of the game.
	 */
	public void saveGameButtonAction() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource(Main.FXML_FILE_PATH + "SaveGame.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			
			// Gets the controller for the FXML file.
			SaveGameController saveGameWindow = fxmlLoader.<SaveGameController> getController();
			
			// Pass down the player's username and the level.
			saveGameWindow.setLevel(currentLevel);
			saveGameWindow.setPlayerUsername(currentUser.getUsername());
			
			// Pass down the time variables.
			saveGameWindow.setTimes(currentLevelTime, currentGameTime);
			saveGameWindow.setTimeValid(timeValid);
			
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			// primaryStage.setTitle(SAVE_GAME_TITLE);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
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
			loadGame.setPauseElements(pauseMenu);
			loadGame.showGameStates();
			
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			// primaryStage.setTitle(LOAD_GAME_TITLE);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
			
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
            // file is not found.
            e.printStackTrace();
            System.exit(-1);
		}
	}
	
	/** 
	 * Closes this window and the game window.
	 */
	public void closePreviousWindows() {
		// Closes the window.
		Stage stage = (Stage) btnQuit.getScene().getWindow();
		stage.close();
		
		// Closes the game window.
		gameWindow.closeGameWindow();
	}
	
	/**
	 * Closes this window and the game window, then opens the main menu.
	 */
	public void quitButtonAction() {
		// Closes the window.
		Stage stage = (Stage) btnQuit.getScene().getWindow();
		stage.close();
		
		// Closes the game window, then opens the main menu.
		gameWindow.closeGameWindow();
		gameWindow.openMainMenu();
	}
}
