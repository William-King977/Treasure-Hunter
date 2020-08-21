import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for the Main Menu.
 * The user can access the main features of the game here.
 * @author William King
 */
public class MainMenuController {
	
	/** Title for the Login page. */
	private final String LOGIN_TITLE = "The Game";
	
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
	 * Loads the user's latest accessed state.
	 */
	public void continueButtonAction() {
	}
	
	/**
	 * Loads the first level.
	 */
	public void newGameButtonAction() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource("FXMLFiles/GameWindow.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			
			// Gets the controller for the FXML file.
			GameController gameWindow = fxmlLoader.<GameController> getController();
			
			// Adjust the level number parameter.
			// NOTE: Restart level basically loads the level AFTER
			// the level number is set. The Initialize method runs first, which
			// causes the level number to be always 0.
			gameWindow.setLevelNumber(1);
			gameWindow.restartLevel();
			
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			// primaryStage.setTitle(CREATE_PROFILE_PICTURE_TITLE);
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
	}
	
	/**
	 * Displays a menu of levels that are available for the user.
	 */
	public void levelSelectButtonAction() {
	}
	
	/**
	 * Displays a menu of leaderboards to select. They show the top 3
	 * best times for each level 
	 */
	public void leaderboardsButtonAction() {
	}
	
	/**
	 * Deletes the user's profile and any related statistics (and states).
	 */
	public void deleteProfileButtonAction() {
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
					.getResource("FXMLFiles/Login.fxml"));
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
