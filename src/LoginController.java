import java.util.LinkedHashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * Controller for the Login page.
 * Handles actions performed by the login page.
 * This includes checking users and setting up any children windows.
 * @author William King
 */
public class LoginController {
	/** A linked hashmap to hold all current users. */
	LinkedHashMap<String, User> users;
	
	/** fxID for the username text field */
	@FXML private TextField txtUsername;
	
	/**
	 * Sets up the linked hashmap holding all the users.
	 */
	public void initialize() {
		users = FileHandling.getUsers();
	}
	
	/**
	 * Handles a button event for the login button e.g. button press.
	 */
	public void handleLoginButtonAction() {
		// Checks if the username exists in the system.
		String username = txtUsername.getText().trim();
		if (users.containsKey(username)) {
			showMainMenu();
		} else {
			// Alert shown when the username doesn't exist.
			Alerts.userNotExist(username);
		}
	}
	
	/**
	 * Displays a page that allows the user to create a new profile.
	 */
	public void handleCreateNewProfileButtonAction() {
		
	}
	
	/**
	 * Displays the main menu.
	 */
	public void showMainMenu() {
		
	}
}
