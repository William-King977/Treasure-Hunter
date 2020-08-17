import java.io.IOException;
import java.util.LinkedHashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for the Login page.
 * Handles actions performed by the login page.
 * This includes checking users and setting up any children windows.
 * @author William King
 */
public class LoginController {
	/** Title for the New User page. */
	private final String NEW_USER_TITLE = "Create Profile";
	/** Title for the Main Menu. */
	private final String MAIN_MENU_TITLE = "Main Menu";
	
	/** A linked hashmap to hold all current users. */
	LinkedHashMap<String, User> users;
	
	/** fxID for the username text field */
	@FXML private TextField txtUsername;
	
	/** A button that opens the main menu. */
	@FXML private Button btnLogin;
	
	/**
	 * Sets up the linked hashmap holding all the users.
	 */
	public void initialize() {
		users = FileHandling.getUsers();
	}
	
	/**
	 * Handles a button event for the login button e.g. button press.
	 */
	public void loginButtonAction() {
		// Checks if the username exists in the system.
		String username = txtUsername.getText().trim();
		if (users.containsKey(username)) {
			FileHandling.setCurrentUser(username);
			showMainMenu();
		} else if (username.isEmpty()) {
			Alerts.usernameNotEntered();
		} else {
			// Alert shown when the username doesn't exist.
			Alerts.userNotExist(username);
		}
	}
	
	/**
	 * Displays a page that allows the user to create a new profile.
	 */
	public void createNewProfileButtonAction() {
		try {	
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource("FXMLFiles/NewUser.fxml"));
				
			// Sets a new border pane.
			BorderPane newRoot = fxmlLoader.load();
				
			// Gets the controller for the FXML file loaded.
			NewUserController createUser = fxmlLoader.<NewUserController> getController();
			
			// Pass down the user linked hashmap for local changes.
			createUser.setUserHashMap(users);
			
	        // Sets the scene.
	        Scene newScene = new Scene(newRoot); 
	        // Creates a new stage.
	        Stage newStage = new Stage();
	        // Sets the scene to the stage.
	        newStage.setScene(newScene);
	        newStage.setTitle(NEW_USER_TITLE);
	        newStage.initModality(Modality.APPLICATION_MODAL);
	        newStage.showAndWait();
        } catch (IOException e) {
        	// Catches an IO exception such as that where the FXML
            // file is not found.
            e.printStackTrace();
            System.exit(-1);
        }
	}
	
	/**
	 * Displays the main menu.
	 */
	public void showMainMenu() {
		Stage curStage = (Stage) btnLogin.getScene().getWindow();
		curStage.close(); 
		
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
