import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
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
	/** File location of the items. */
	private final static String ITEM_FILE_PATH = "DataFiles/Items/";
	/** File location of the doors. */
	private final static String DOOR_FILE_PATH = "DataFiles/Doors/";
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
	/** An array holding all the doors in a level. */
	private Door[][] doors;
	
	/** Holds the User to adjust the levels they can play. */
	private User currentUser;
	/** The current level that the player is on. */
	private Level currentLevel;
	/** Player in the game (to control its status). */
	private Player player;
	/** The number of the level selected. */
	private int levelNum;
	
	// Loaded images
	// SPRITES
	private Image playerDefault;
	private Image playerFlippers;
	private Image playerFireBoots;
	
	// ITEMS
	private Image fireBoots;
	private Image flippers;
	private Image orangeKey;
	private Image yellowKey;
	private Image purpleKey;
	private Image token;
	
	// DOORS.
	private Image yellowDoor;
	private Image orangeDoor;
	private Image purpleDoor;
	private Image tokenDoor;
	
	// ENVIRONMENT
	private Image playerSprite; // The current player sprite.
	private Image floor;
	private Image wall;
	private Image goal;
	private Image water;
	private Image fire;
	
	/** The inventory button for the game. */
	@FXML private Button btnInventory;
	/** The quit button for the game. */
	@FXML private Button btnQuit;
	/** An label to show the token count. */
	@FXML private Label lblToken; 
	/** An image view to show the token icon. */
	@FXML private ImageView imgViewToken; 
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
		// Player Sprites.
		playerDefault = new Image(new File (PLAYER_FILE_PATH + "Default.png").toURI().toString());
		playerFlippers = new Image(new File (PLAYER_FILE_PATH + "Flippers.png").toURI().toString());
		playerFireBoots = new Image(new File (PLAYER_FILE_PATH + "FireBoots.png").toURI().toString());
		
		// Items
		fireBoots = new Image(new File (ITEM_FILE_PATH + "FireBoots.png").toURI().toString());
		flippers = new Image(new File (ITEM_FILE_PATH + "Flippers.png").toURI().toString());
		orangeKey = new Image(new File (ITEM_FILE_PATH + "OrangeKey.png").toURI().toString());
		yellowKey = new Image(new File (ITEM_FILE_PATH + "YellowKey.png").toURI().toString());
		purpleKey = new Image(new File (ITEM_FILE_PATH + "purpleKey.png").toURI().toString());
		token = new Image(new File (ITEM_FILE_PATH + "Token.png").toURI().toString());
		
		// Doors
		yellowDoor  = new Image(new File (DOOR_FILE_PATH + "YellowDoor.png").toURI().toString());
		orangeDoor  = new Image(new File (DOOR_FILE_PATH + "OrangeDoor.png").toURI().toString());
		purpleDoor  = new Image(new File (DOOR_FILE_PATH + "PurpleDoor.png").toURI().toString());
		tokenDoor  = new Image(new File (DOOR_FILE_PATH + "TokenDoor.png").toURI().toString());
		
		// Environment.
		floor = new Image(new File (TEXTURE_FILE_PATH + "Floor.png").toURI().toString());
		wall = new Image(new File (TEXTURE_FILE_PATH + "StoneWall.png").toURI().toString());
		water = new Image(new File (TEXTURE_FILE_PATH + "Water.png").toURI().toString());
		fire = new Image(new File (TEXTURE_FILE_PATH + "Fire.png").toURI().toString());
		goal = new Image(new File (TEXTURE_FILE_PATH + "Treasure Chest.png").toURI().toString());
		
		playerSprite = playerDefault;
		imgViewToken.setImage(token);
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
				playerNewY = player.getY() - 1;
	        	isMoveValid(player.getX(), playerNewY);
				break;
			case DOWN:
				playerNewY = player.getY() + 1;
				isMoveValid(player.getX(), playerNewY);
	        	break;
			case LEFT:
		    	playerNewX = player.getX() - 1;
		    	isMoveValid(playerNewX, player.getY());
	        	break;
		    case RIGHT:
		    	playerNewX = player.getX() + 1;
		    	isMoveValid(playerNewX, player.getY());
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
		String[] equippedItems =  player.getEquippedItems();
		String apparel = equippedItems[0]; // For water/fire hazards.
		String item = equippedItems[1]; // For coloured doors.
		switch (element) {
			case "W":
				// No nothing.
				break;
			case "G":
				// Level Complete
				loadNewLevel();
				break;
			// APPAREL.
			case "FLP":
				player.setX(newX);
				player.setY(newY);
				player.addInventory("Flippers");
				levelElements[newY][newX] = " "; // Make it disappear.
				break;
			case "FB":
				player.setX(newX);
				player.setY(newY);
				player.addInventory("Fire Boots");
				levelElements[newY][newX] = " ";
				break;
			// ITEMS.
			case "OK":
				player.setX(newX);
				player.setY(newY);
				player.addInventory("Orange Key");
				levelElements[newY][newX] = " "; 
				break;
			case "YK":
				player.setX(newX);
				player.setY(newY);
				player.addInventory("Yellow Key");
				levelElements[newY][newX] = " ";
				break;
			case "PK":
				player.setX(newX);
				player.setY(newY);
				player.addInventory("Purple Key");
				levelElements[newY][newX] = " ";
				break;
			case "T":
				player.setX(newX);
				player.setY(newY);
				// Increment token count and show it on screen.
				int newTokenCount = player.getNumTokens() + 1;
				player.setNumTokens(newTokenCount);
				lblToken.setText(newTokenCount + "");
				levelElements[newY][newX] = " ";
				break;
			// DOOR
			case "D":
				Door door = doors[newY][newX];
				switch (door.getType()) {
					case YELLOW:
						if (item.equals("Yellow Key")) {
							player.useItem(item);
							levelElements[newY][newX] = " ";
						}
						break;
					case ORANGE:
						if (item.equals("Orange Key")) {
							player.useItem(item);
							levelElements[newY][newX] = " ";
						}
						break;
					case PURPLE:
						if (item.equals("Purple Key")) {
							player.useItem(item);
							levelElements[newY][newX] = " ";
						}
						break;
					case TOKEN:
						int doorCost = door.getNumTokens();
						int currentTokens = player.getNumTokens();
						// Checks if the player has enough tokens.
						if (currentTokens >= doorCost) {
							int newTokens = currentTokens - doorCost;
							player.setNumTokens(newTokens);
							lblToken.setText(newTokens + "");
							levelElements[newY][newX] = " ";
						} 
						break;
				}
				break;
			// HAZARDS.
			case "F":
				switch (apparel) {
					case "Fire Boots":
						player.setX(newX);
						player.setY(newY);
						break;
					default:
						// DEATH.
						restartLevel();
				}
				break;
			case "WTR":
				switch (apparel) {
					case "Flippers":
						player.setX(newX);
						player.setY(newY);
						break;
					default:
						// DEATH.
						restartLevel();
				}
				break;
			case "E":
				// DEATH.
				restartLevel();
				break;
			default:
				player.setX(newX);
				player.setY(newY);
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
		int playerX = player.getX();
		int playerY = player.getY();
		
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
			xLeftBoundChange = true; // It needs to reset in the for loop.
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
	    		drawElements(element, tempCol, tempRow, row, col);
	    		tempCol++;
	    	}
	    	
	    	tempCol = 0;
	    	if (xLeftBoundChange) {
	    		tempCol = (playerX - GAME_BOUNDS) * -1;
	    	}
	    	
	    	tempRow++;
	    }
	
		// Draw player sprite (after using an item or moving).
		drawPlayerSprite();
	}
	
	/**
	 * Draws each element in their respective cells on the canvas.
	 * @param element The acronym of the current element.
	 * @param tempCol The local column the element is in.
	 * @param tempRow The local row the element is in.
	 * @param row The actual row the element is in.
	 * @param col The actual column the element is in.
	 */
	public void drawElements(String element, int tempCol, int tempRow, int row, int col) {
		// Draw the floor first (as a base).
		gc.drawImage(floor, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
		switch(element) {
			case "W":
				gc.drawImage(wall, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;
			case "G":
				gc.drawImage(goal, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;
			// APPAREL.
			case "FLP":
				gc.drawImage(flippers, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;
			case "FB":
				gc.drawImage(fireBoots, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;
			// ITEMS.
			case "OK":
				gc.drawImage(orangeKey, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;
			case "YK":
				gc.drawImage(yellowKey, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;
			case "PK":
				gc.drawImage(purpleKey, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;
			case "T":
				gc.drawImage(token, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;
			// DOOR.
			case "D":
				// Fetch the correct door and draw it.
				Door door = doors[row][col];
				switch (door.getType()) {
					case YELLOW:
						gc.drawImage(yellowDoor, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
						break;
					case ORANGE:
						gc.drawImage(orangeDoor, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
						break;
					case PURPLE:
						gc.drawImage(purpleDoor, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
						break;
					case TOKEN:
						gc.drawImage(tokenDoor, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
						break;
				}
				break;
			// HAZARDS.
			case "WTR":
				gc.drawImage(water, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;
			case "F":
				gc.drawImage(fire, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;
		}
	}
	
	/**
	 * Redraws the player sprite after an action is performed (movement or using an item).
	 */
	public void drawPlayerSprite() {
		String[] equippedItems = player.getEquippedItems();
		String apparel = equippedItems[0];
		String item = equippedItems[1];
		
		// Change the sprite if an item has been used.
		// Otherwise the sprite stays the same.
		switch (apparel) {
			case "Flippers":
				switch (item) {
					case " ":
						playerSprite = playerFlippers;
						break;
				}
				break;
			case "Fire Boots":
				switch (item) {
					case " ":
						playerSprite = playerFireBoots;
						break;
				}
				break;
			default:
				switch (item) {
					case " ":
						playerSprite = playerDefault;
						break;
				}
		}
		// Draw the sprite.
		gc.drawImage(playerSprite, GAME_BOUNDS * GRID_CELL_WIDTH, GAME_BOUNDS * GRID_CELL_HEIGHT);
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
			playerSprite = playerDefault;
			doors = currentLevel.getDoors();
			lblToken.setText("0");
			drawLevel();
		}
	}
	
	/**
	 * Resets the elements for the current level if the player has died.
	 */
	public void restartLevel() {
		currentLevel = FileHandling.getLevel(levelNum);
		levelElements = currentLevel.getLevelElements();
		player = currentLevel.getPlayer();
		playerSprite = playerDefault;
		doors = currentLevel.getDoors(); // Not really needed, but just in case...
		lblToken.setText("0");
		drawLevel();
	}
	
	/**
	 * Opens the player's inventory screen.
	 */
	public void openInventory() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource("FXMLFiles/InventoryWindow.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			
			// Gets the controller for the FXML file.
			InventoryController inventoryWindow = fxmlLoader.<InventoryController> getController();
			
			// Pass down the player.
			inventoryWindow.setPlayerDetails(player, playerSprite);
			inventoryWindow.showSprites();
			inventoryWindow.showInventory();
			
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			// primaryStage.setTitle(CREATE_PROFILE_PICTURE_TITLE);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
            
            // Update the player sprite.
            playerSprite = inventoryWindow.getPlayerSprite();
            gc.drawImage(floor, GAME_BOUNDS * GRID_CELL_WIDTH, GAME_BOUNDS * GRID_CELL_HEIGHT);
            gc.drawImage(playerSprite, GAME_BOUNDS * GRID_CELL_WIDTH, GAME_BOUNDS * GRID_CELL_HEIGHT);
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
            // file is not found.
            e.printStackTrace();
            System.exit(-1);
		}
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
