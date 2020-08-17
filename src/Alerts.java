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
}
