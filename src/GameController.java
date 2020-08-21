import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

// NOTE: In a 2-D Array sense, Y is row and X is the column. So array[Y][X].

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
	/** The number marking the highest level in the game. */
	private final static int MAX_LEVEL = 2;
	/** Size of the cell width. */
	private static int GRID_CELL_WIDTH = 50;
	/** Size of the cell height. */
	private static int GRID_CELL_HEIGHT = 50;
	/** The number of square cells shown on the screen. */
	private final static int GAME_BOUNDS = 3;

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
	private Image playerDefault;
	private Image playerDeathWater;
	private Image playerDeathFire;
	
	private Image playerSprite;
	private Image floor;
	private Image wall;
	private Image goal;
	private Image water;
	private Image fire;
	
	/** The quit button for the game. */
	@FXML private Button btnQuit;
	/* The canvas in the GUI. */ 
	@FXML private Canvas canvas;
	/** The graphic context of the canvas. */
	private GraphicsContext gc;
	
	/**
	 * Sets up the graphics. 
	 * This method will run automatically.
	 */
	public void initialize() {
		// Load the graphics.
		playerDefault = new Image(new File (PLAYER_FILE_PATH + "Default.png").toURI().toString());
		
		playerSprite = playerDefault;
		floor = new Image(new File (TEXTURE_FILE_PATH + "Floor.png").toURI().toString());
		wall = new Image(new File (TEXTURE_FILE_PATH + "StoneWall.png").toURI().toString());
		goal = new Image(new File (TEXTURE_FILE_PATH + "Treasure Chest.png").toURI().toString());
		water = new Image(new File (TEXTURE_FILE_PATH + "Water.png").toURI().toString());
		fire = new Image(new File (TEXTURE_FILE_PATH + "Fire.png").toURI().toString());
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
		String element = levelElements[newY][newX];
		switch (element) {
			case "W":
				// No nothing.
				break;
			case "G":
				// Level Complete
				loadNewLevel();
				break;
			// HAZARDS.
			case "F":
				// Death.
				restartLevel();
				break;
			case "WTR":
				// Death.
				restartLevel();
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
		
		// Based on the index.
		int levelHeight = currentLevel.getLevelHeight() - 1;
		int levelWidth = currentLevel.getLevelWidth() - 1;
		
		// Get player position.
		int playerX = player.getPlayerX();
		int playerY = player.getPlayerY();
		
		// Set the bounds of the game shown based on the player's position.
		int xLeftBound = playerX - GAME_BOUNDS;
		int xRightBound = playerX + GAME_BOUNDS;
		int yUpBound = playerY - GAME_BOUNDS;
		int yDownBound = playerY + GAME_BOUNDS;
		
		// Used to show applicable items on the canvas.
		int tempRow = 0;
		int tempCol = 0;
		boolean xLeftBoundChange = false;
		
		// Adjust elements if out of bounds.
		if (xLeftBound < 0) {
			tempCol = xLeftBound * -1;
			xLeftBoundChange = true; // Used cos it needs to reset in the for loop.
			xLeftBound = 0;
		}
		if (xRightBound > levelHeight) {
			xRightBound = levelHeight;
		} 	
	    if (yDownBound > levelWidth) {
	    	yDownBound = levelWidth;
		} 
	    if (yUpBound < 0) {
	    	tempRow = yUpBound * -1;
			yUpBound = 0;
		}
		
	    // Show elements based on the bounds.
		for (int row = yUpBound; row <= yDownBound; row++) {
	    	for (int col = xLeftBound; col <= xRightBound; col++) {
	    		String element = levelElements[row][col];
	    		// Draw the floor first (as a base).
	    		gc.drawImage(floor, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
	    		switch(element) {
	    			case "W":
	    				gc.drawImage(wall, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
	    				break;
	    			case "G":
	    				gc.drawImage(goal, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
	    				break;
	    			case "WTR":
	    				gc.drawImage(water, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
	    				break;
	    			case "F":
	    				gc.drawImage(fire, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
	    				break;
	    		}
	    		tempCol++;
	    	}
	    	
	    	tempCol = 0;
	    	if (xLeftBoundChange) {
	    		tempCol = (playerX - GAME_BOUNDS) * -1;
	    	}
	    	
	    	tempRow++;
	    }
	
		// Draw player at current location (always in the middle of the canvas (locally)).
		playerX = GAME_BOUNDS;
	    playerY = GAME_BOUNDS;
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
		if (MAX_LEVEL == levelNum) {
			// GAME COMPLETE!
		} else {
			levelNum++;
			currentLevel = FileHandling.getLevel(levelNum);
			levelElements = currentLevel.getLevelElements();
			player = currentLevel.getPlayer();
			drawLevel();
		}
	}
	
	/**
	 * Resets the elements for the current level if the player
	 * has died.
	 */
	public void restartLevel() {
		currentLevel = FileHandling.getLevel(levelNum);
		levelElements = currentLevel.getLevelElements();
		player = currentLevel.getPlayer();
		playerSprite = playerDefault;
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
