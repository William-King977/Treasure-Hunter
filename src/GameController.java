import java.io.File;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Controller for the Game screen.
 * Controls what is shown as the player moves and interacts with objects.
 * @author William King
 */
public class GameController {
	/** File location of the player sprites. */
	private final static String PLAYER_FILE_PATH = "DataFiles/Player Sprites/";
	/** File location of the textures. */
	private final static String TEXTURE_FILE_PATH = "DataFiles/Textures/";
	/** Size of the cell width. */
	private static int GRID_CELL_WIDTH = 50;
	/** Size of the cell height. */
	private static int GRID_CELL_HEIGHT = 50;
	
	/** An arraylist holding all the levels. */
	private ArrayList<Level> levels;
	
	/** Holds the User to adjust the levels they can play. */
	private User currentUser;
	/** The number of the level selected. */
	private int levelNum;
	
	// Loaded images
	private Image player;
	private Image dirt;
	private Image wall;
	
	/** The quit button for the game. */
	@FXML private Button btnQuit;
	/* The canvas in the GUI. */ 
	@FXML private Canvas canvas;
	/** The graphic context of the canvas. */
	private GraphicsContext gc;
	
	/**
	 * Sets up the level array list. 
	 * This method will run automatically.
	 */
	public void initialize() {
		levels = FileHandling.getLevels();
		
		// Load the graphics.
		player = new Image(new File (PLAYER_FILE_PATH + "Default.png").toURI().toString());
		dirt = new Image(new File (TEXTURE_FILE_PATH + "Dirt.png").toURI().toString());
		wall = new Image(new File (TEXTURE_FILE_PATH + "StoneWall.png").toURI().toString());
		
		drawLevel();
	}
	
	/**
	 * Draw the game on the canvas.
	 */
	public void drawLevel() {
		// Get the Graphic Context of the canvas. This is what we draw on.
		gc = canvas.getGraphicsContext2D();
		
		// Clear canvas.
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		Level currentLevel = levels.get(levelNum);
		String[][] levelElements = currentLevel.getLevelElements();
		int levelHeight = currentLevel.getLevelHeight();
		int levelWidth = currentLevel.getLevelWidth();
		
		// Get player position.
		Player thisPlayer = currentLevel.getPlayer();
		int playerX = thisPlayer.getPlayerX();
		int playerY = thisPlayer.getPlayerY();
		
		for (int row = 0; row < levelHeight; row++) {
	    	for (int col = 0; col < levelWidth; col++) {
	    		String element = levelElements[row][col];
	    		switch(element) {
	    			case "W":
	    				gc.drawImage(wall, row * GRID_CELL_WIDTH, col * GRID_CELL_HEIGHT);
	    				break;
	    			default:
	    				gc.drawImage(dirt, row * GRID_CELL_WIDTH, col * GRID_CELL_HEIGHT);
	    				break;
	    		}
	    	}
	    }
		
		// Draw player at current location
		gc.drawImage(player, playerX * GRID_CELL_WIDTH, playerY * GRID_CELL_HEIGHT);
	}
	
	/**
	 * Sets the level the player has selected.
	 * @param levelNum The level number as an integer.
	 */
	public void setLevelNumber(int levelNum) {
		this.levelNum = levelNum;
	}
	
	/**
	 * Saves the current state of the game, then closes the game window.
	 */
	public void quitButtonAction() {
		// Closes the window.
		Stage stage = (Stage) btnQuit.getScene().getWindow();
		stage.close();
	}
}
