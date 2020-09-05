package data;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * The main class starts the program and executes the 
 * login page for use.
 * @author William King
 */
public class Main extends Application {
	/** The file location of the FXML files. */
	public static final String FXML_FILE_PATH = "../fxml/";
	/** The title given to the stage. */
	private static final String STAGE_TITLE = "The Game";
	/** Width of the stage. */
	private static final int STAGE_WIDTH = 360;
	/** Height of the stage. */
	private static final int STAGE_HEIGHT = 164;
	
	/**
	 * Performs any pre-launch tasks and then launches the system.
	 * @param args Takes a string of arguments.
	 */
	public static void main(String[] args) {
		// Run the program.
		launch(args);
	}
	
	/**
	 * The primary stage hosts the application and provides a UI for the user.
	 * @param primaryStage The stage.
	 */
	@Override 
	public void start(Stage primaryStage) {
		try {
			// Load the main scene.
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource(FXML_FILE_PATH + "Login.fxml"));
			Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
			
			// Place the main scene on stage and show it.
			primaryStage.setScene(scene);
			primaryStage.setTitle(STAGE_TITLE);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
