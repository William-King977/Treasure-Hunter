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
	/** An array holding the elements for a level. */
	private String[][] levelElements;
	
	/** Holds the User to adjust the levels they can play. */
	private User currentUser;
	/** The current level that the player is on. */
	private Level currentLevel;
	/** Player in the game (to control its status). */
	private Player player;
	/** The number of the level selected. */
	private int levelNum;
	
	// Loaded images
	private Image playerSprite;
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
		// Set up the level and its elements.
		levels = FileHandling.getLevels();
		currentLevel = levels.get(levelNum);
		levelElements = currentLevel.getLevelElements();
		player = currentLevel.getPlayer();
		
		// Load the graphics.
		playerSprite = new Image(new File (PLAYER_FILE_PATH + "Default.png").toURI().toString());
		dirt = new Image(new File (TEXTURE_FILE_PATH + "Dirt.png").toURI().toString());
		wall = new Image(new File (TEXTURE_FILE_PATH + "StoneWall.png").toURI().toString());
		
		// Draw the level as the game opens.
		drawLevel();
	}
	
	/**
	 * Process a key event due to a key being pressed, e.g., to the player.
	 * @param event The key event that was pressed.
	 */
	public void processMovement(KeyEvent event) {
		// New x,y variables if the player has moved.
		int playerNewX;
		int playerNewY;
		
		// Because the level is basically stored in an array.
		// Going UP needs to lower the index and vice versa.
		switch (event.getCode()) {
			case UP:
				playerNewY = player.getPlayerY() - 1;
	        	isMoveValid(player.getPlayerX(), playerNewY);
				break;
			case DOWN:
				playerNewY = player.getPlayerY() + 1;
				isMoveValid(player.getPlayerX(), playerNewY);
	        	break;
			case LEFT:
		    	playerNewX = player.getPlayerX() - 1;
		    	isMoveValid(playerNewX, player.getPlayerY());
	        	break;
		    case RIGHT:
		    	playerNewX = player.getPlayerX() + 1;
		    	isMoveValid(playerNewX, player.getPlayerY());
	        	break;	        
	        default:
	        	// Do nothing
	        	break;
		}
		
		// Redraw game as the player may have moved.
		drawLevel();
		
		// Consume the event. This means we mark it as dealt with. 
		// This stops other GUI nodes (buttons etc.) responding to it.
		event.consume();
	}
	
	/**
	 * Checks if the move made is valid and determine action
	 * depending on the surrounding elements.
	 * @param newX The new x-coordinate.
	 * @param newY The new y-coordinate.
	 */
	public void isMoveValid(int newX, int newY) {
		String element = levelElements[newX][newY];
		switch (element) {
			case "W":
				// No nothing.
				break;
			case "F":
				// DEATH.
				break;
			case "WTR":
				// DEATH.
				break;
			case "E":
				// DEATH.
				break;
			default:
				player.setPlayerX(newX);
				player.setPlayerY(newY);
		}
	}
	
	/**
	 * Draw the game on the canvas.
	 */
	public void drawLevel() {
		// Get the Graphic Context of the canvas. This is what we draw on.
		gc = canvas.getGraphicsContext2D();
		
		// Clear canvas.
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		int levelHeight = currentLevel.getLevelHeight();
		int levelWidth = currentLevel.getLevelWidth();
		
		// Get player position.
		int playerX = player.getPlayerX();
		int playerY = player.getPlayerY();
		
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
		gc.drawImage(playerSprite, playerX * GRID_CELL_WIDTH, playerY * GRID_CELL_HEIGHT);
	}
	
	/**
	 * Sets the level the player has selected.
	 * @param levelNum The level number as an integer.
	 */
	public void setLevelNumber(int levelNum) {
		this.levelNum = levelNum;
	}
	
	/**
	 * Sets up the elements for the next level.
	 */
	public void loadNewLevel() {
		// Set up the level and its elements.
		currentLevel = levels.get(levelNum);
		levelElements = currentLevel.getLevelElements();
		player = currentLevel.getPlayer();
		drawLevel();
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
