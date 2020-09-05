package Controllers;

import java.util.LinkedHashMap;

import Data.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the New User page.
 * The user can create a new profile.
 * @author William King
 */
public class NewUserController {
	/** A linked hashmap holding all the users. */
	private LinkedHashMap<String, User> users;
	/** fxID for the username text field */
	@FXML private TextField txtUsername;
	/** The back button for the page. */
	@FXML private Button btnBack;
	
	/**
	 * Sets the user linked hashmap.
	 * @param users The linked hashmap of users.
	 */
	public void setUserHashMap(LinkedHashMap<String, User> users) {
		this.users = users;
	}
	
	/**
	 * Creates the new user's profile.
	 */
	public void createProfileButtonAction() {
		// Checks if the username exists in the system.
		String username = txtUsername.getText().trim();
		if (users.containsKey(username)) {
			Alerts.usernameExists();
		// If there's no input.
		} else if (username.isEmpty()) {
			Alerts.usernameNotEntered();
		} else {
			// Save User locally and in the file.
			User newUser = new User(username);
			users.put(username, newUser);
			
			String strNewUser = newUser.toStringDetail();
			FileHandling.createUser(strNewUser);
			Alerts.userCreated();
			backButtonAction(); // Close the page.
		}
	}
	
	/**
     * Closes the current page.
     */
    public void backButtonAction() {
		Stage curStage = (Stage) btnBack.getScene().getWindow(); 
		curStage.close(); 
    }
}
