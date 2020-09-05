package data;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A class to hold all alert messages in one place.
 * @author William King
 */
public class Alerts {
	
	/**
	 * An alert pop-up that tells the user that the username entered
	 * does not exist in the system.
	 * @param username The entered username which doesn't exist in the system.
	 */
	public static void userNotExist(String username) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error: User Does Not Exist.");
		alert.setHeaderText(null);
		alert.setContentText("The username '" + username + "' does not exist "
				+ "in the system. Please try again.");	
		alert.showAndWait();
	}
	
	/**
	 * An alert pop-up that tells the user that there is no input in the 
	 * username field.
	 */
	public static void usernameNotEntered() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error: Username Not Entered.");
		alert.setHeaderText(null);
		alert.setContentText("Please enter a username.");	
		alert.showAndWait();
	}
	
	/**
	 * An alert pop-up that tells the user that
	 * the username entered already exists in the system.
	 */
	public static void usernameExists() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error: This Username Exists.");
		alert.setHeaderText(null);
		alert.setContentText("An existing user has the same "
				+ "username, please enter a different one.");
		alert.showAndWait();
    	return;
	}
	
	/**
	 * An alert pop-up that tells the user that there is no input in the 
	 * description field when saving their game.
	 */
	public static void descriptionNotEntered() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error: Description Not Entered.");
		alert.setHeaderText(null);
		alert.setContentText("Please enter a description for your saved game.");	
		alert.showAndWait();
	}
	
	/**
	 * An alert pop-up that tells the user that the their profile has 
	 * been created successfully.
	 */
	public static void userCreated() {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User Created Successfully.");
		alert.setHeaderText(null);
		alert.setContentText("This user has been created successfully.");
		alert.showAndWait();
	}
	
	/**
	 * An alert pop-up that tells the user that their game has been saved.
	 */
	public static void gameSaved() {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game Saved Successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Your game has been saved successfully.");
		alert.showAndWait();
	}
}
