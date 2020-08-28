import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
	/** File location of the enemy sprites. */
	private final static String ENEMY_FILE_PATH = "DataFiles/Enemies/";
	/** Title for the Main Menu. */
	private final String MAIN_MENU_TITLE = "Main Menu";
	/** The number marking the highest level in the game. */
	private final static int MAX_LEVEL = 2;
	/** Size of the cell width. */
	private static int GRID_CELL_WIDTH = 50;
	/** Size of the cell height. */
	private static int GRID_CELL_HEIGHT = 50;
	/** The number of square cells shown on the screen. */
	private final static int GAME_BOUNDS = 3;
	
	// Message Prompts.
	private final String YELLOW_KEY_MSG = "\nYou picked up a yellow key.";
	private final String ORANGE_KEY_MSG = "\nYou picked up an orange key.";
	private final String PURPLE_KEY_MSG = "\nYou picked up a purple key.";
	private final String FLIPPERS_MSG = "\nYou picked up a pair of flippers.";
	private final String FIRE_BOOTS_MSG = "\nYou picked up a pair of fire boots.";
	private final String TOKEN_MSG = "\nYou picked up 1 token.";
	private final String PORTAL_MSG = "\nYou went through a portal, taking "
			+ "you to a different part of the level.";
	
	private final String COLOUR_DOOR_LOCKED = "\nThis door is locked. Perhaps a key of the same colour "
			+ "would open it?";
	private final String TOKEN_DOOR_LOCKED = "\nThis door requires tokens to unlock "
			+ "and you don't have enough. Tokens Required: ";
	
	private final String WATER_DEATH_MSG = "\nYou fell into a deep pond. Without wearing the "
			+ "appropriate equipment, you drowned. Restarting level...\n";
	private final String FIRE_DEATH_MSG = "\nWithout any protection, you burned to a "
			+ "crisp as you walked into the fire pit. Restarting level...\n";
	private final String ENEMY_DEATH_MSG = "\nAn enemy ripped you into two, putting an "
			+ "end to your adventure. Restarting level...\n";

	/** An array holding the elements for a level. */
	private String[][] levelElements;
	/** An array holding all the doors in a level. */
	private Door[][] doors;
	/** An array holding all the apparels in a level. */
	private Apparel[][] apparels;
	/** An array holding all the items in a level. */
	private Item[][] items;
	/** An array holding all the hazards in a level. */
	private Hazard[][] hazards;
	/** An array holding all the portals in a level. */
	private Portal[][] portals;
	/** An array holding all the enemies in a level. */
	private Enemy[] enemies;
	
	/** Holds the User to adjust the levels they can play. */
	private User currentUser;
	/** The current level that the player is on. */
	private Level currentLevel;
	/** Player in the game (to control its status). */
	private Player player;
	/** The number of the level selected. */
	private int levelNum;
	/** Used to check if an enemy has moved to the same position as the player. */
	private boolean enemyOnPlayer;
	
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
	private Image portal;
	
	// ENEMIES
	private Image straightEnemy;
	private Image wallEnemy;
	private Image dumbEnemy;
	private Image smartEnemy;
	
	/** The inventory button for the game. */
	@FXML private Button btnInventory;
	/** The quit button for the game. */
	@FXML private Button btnQuit;
	/** An label to show the token count. */
	@FXML private Label lblToken; 
	/** A text area to prompt the user of their actions and the level status. */
	@FXML private TextArea txtLevelPrompt; 
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
		portal = new Image(new File (TEXTURE_FILE_PATH + "Portal.png").toURI().toString());
		
		// Enemies
		straightEnemy = new Image(new File (ENEMY_FILE_PATH + "Straight.png").toURI().toString());
		wallEnemy = new Image(new File (ENEMY_FILE_PATH + "Wall.png").toURI().toString());
		dumbEnemy = new Image(new File (ENEMY_FILE_PATH + "Dumb.png").toURI().toString());
		smartEnemy = new Image(new File (ENEMY_FILE_PATH + "Smart.png").toURI().toString());
		
		enemyOnPlayer = false;
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
			case W:
				playerNewY = player.getY() - 1;
	        	isMoveValid(player.getX(), playerNewY);
				break;
			case DOWN:
			case S:
				playerNewY = player.getY() + 1;
				isMoveValid(player.getX(), playerNewY);
	        	break;
			case LEFT:
			case A:
		    	playerNewX = player.getX() - 1;
		    	isMoveValid(playerNewX, player.getY());
	        	break;
		    case RIGHT:
		    case D:
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
		// Fetches the element the player moves into.
		String element = levelElements[newY][newX];
		String[] equippedItems =  player.getEquippedItems();
		String apparelEquipped = equippedItems[0]; // For water/fire hazards.
		String itemEquipped = equippedItems[1]; // For coloured doors.
		switch (element) {
			case "W":
				break;
			case "G":
				// Level Complete
				txtLevelPrompt.appendText("\nLevel " + levelNum + " completed!\n");
				loadNewLevel();
				break;
			// APPAREL.
			case "A":
				Apparel apparel = apparels[newY][newX];
				switch (apparel.getType()) {
				case FLIPPERS:
					txtLevelPrompt.appendText(FLIPPERS_MSG);
					player.addInventory("Flippers");
					break;
				case FIREBOOTS:
					txtLevelPrompt.appendText(FIRE_BOOTS_MSG);
					player.addInventory("Fire Boots");
					break;
				}
				player.setX(newX);
				player.setY(newY);
				levelElements[newY][newX] = " "; // Make it disappear.
				break;
			// ITEMS.
			case "I":
				Item item = items[newY][newX];
				switch (item.getType()) {
					case YELLOWKEY:
						txtLevelPrompt.appendText(YELLOW_KEY_MSG);
						player.addInventory("Yellow Key");
						break;
					case ORANGEKEY:
						txtLevelPrompt.appendText(ORANGE_KEY_MSG);
						player.addInventory("Orange Key");
						break;
					case PURPLEKEY:
						txtLevelPrompt.appendText(PURPLE_KEY_MSG);
						player.addInventory("Purple Key");
						break;
				}
				player.setX(newX);
				player.setY(newY);
				levelElements[newY][newX] = " "; // Make it disappear.
				break;
			// TOKENS.
			case "T":
				txtLevelPrompt.appendText(TOKEN_MSG);
				// Increment token count and show it on screen.
				int newTokenCount = player.getNumTokens() + 1;
				player.setNumTokens(newTokenCount);
				lblToken.setText(newTokenCount + "");
				levelElements[newY][newX] = " ";
				player.setX(newX);
				player.setY(newY);
				break;
			// DOOR
			case "D":
				Door door = doors[newY][newX];
				switch (door.getType()) {
					case YELLOW:
						if (itemEquipped.equals("Yellow Key")) {
							player.useItem(itemEquipped);
							levelElements[newY][newX] = " ";
						} else {
							txtLevelPrompt.appendText(COLOUR_DOOR_LOCKED);
						}
						break;
					case ORANGE:
						if (itemEquipped.equals("Orange Key")) {
							player.useItem(itemEquipped);
							levelElements[newY][newX] = " ";
						} else {
							txtLevelPrompt.appendText(COLOUR_DOOR_LOCKED);
						}
						break;
					case PURPLE:
						if (itemEquipped.equals("Purple Key")) {
							player.useItem(itemEquipped);
							levelElements[newY][newX] = " ";
						} else {
							txtLevelPrompt.appendText(COLOUR_DOOR_LOCKED);
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
						} else {
							txtLevelPrompt.appendText(TOKEN_DOOR_LOCKED + doorCost);
						}
						break;
				}
				break;
			// HAZARDS.
			case "H":
				Hazard hazard = hazards[newY][newX];
				switch (hazard.getType()) {
					case WATER:
						switch (apparelEquipped) {
							case "Flippers":
								player.setX(newX);
								player.setY(newY);
								moveEnemies();
								break;
							default:
								// DEATH.
								txtLevelPrompt.appendText(WATER_DEATH_MSG);
								restartLevel();
								return;
						}
						break;
					case FIRE:
						switch (apparelEquipped) {
							case "Fire Boots":
								player.setX(newX);
								player.setY(newY);
								moveEnemies();
								break;
							default:
								// DEATH.
								txtLevelPrompt.appendText(FIRE_DEATH_MSG);
								restartLevel();
								return;
						}
						break;
				}
				break;
			case "P":
				txtLevelPrompt.appendText(PORTAL_MSG);
				Portal portal = portals[newY][newX];
				int destX = portal.getDestX();
				int destY = portal.getDestY();
				player.setX(destX);
				player.setY(destY);
				break;
			// If the player moves into an enemy.
			case "E":
				// DEATH.
				txtLevelPrompt.appendText(ENEMY_DEATH_MSG);
				restartLevel();
				return;
			default:
				player.setX(newX);
				player.setY(newY);
		}
		// Moves the enemies and check if the any of them 
		// have moved on the player's position.
		moveEnemies();
		if (enemyOnPlayer) {
			enemyOnPlayer = false;
			txtLevelPrompt.appendText(ENEMY_DEATH_MSG);
			restartLevel();
			return;
		}
	}
	
	/**
	 * Moves every enemy in the level after the player has made a move.
	 */
	public void moveEnemies() {
		for (int i = 0; i < enemies.length; i++) {
			Enemy enemy = enemies[i];
			EnemyType type = enemy.getType();
			
			// Move enemies, then check if they 'landed' on the player.
			switch (type) {
				case STRAIGHT:
					enemy.moveStraightEnemy(levelElements);
					if ((enemy.getX() == player.getX()) && (enemy.getY() == player.getY())) {
						enemyOnPlayer = true;
					}
					break;
				case WALL:
					enemy.moveWallEnemy(levelElements);
					if ((enemy.getX() == player.getX()) && (enemy.getY() == player.getY())) {
						enemyOnPlayer = true;
					}
					break;
				case DUMB:
					enemy.moveDumbEnemy(levelElements, player.getX(), player.getY());
					if ((enemy.getX() == player.getX()) && (enemy.getY() == player.getY())) {
						enemyOnPlayer = true;
					}
					break;
				case SMART:
					enemy.moveSmartEnemy(levelElements, player.getX(), player.getY());
					if ((enemy.getX() == player.getX()) && (enemy.getY() == player.getY())) {
						enemyOnPlayer = true;
					}
					break;
			}
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
		if (xRightBound > levelWidth) {
			xRightBound = levelWidth;
		} 	
	    if (yDownBound > levelHeight) {
	    	yDownBound = levelHeight;
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
			case "A":
				Apparel apparel = apparels[row][col];
				switch (apparel.getType()) {
				case FLIPPERS:
					gc.drawImage(flippers, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
					break;
				case FIREBOOTS:
					gc.drawImage(fireBoots, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
					break;
				}
				break;
			// ITEMS.
			case "I":
				Item item = items[row][col];
				switch (item.getType()) {
					case YELLOWKEY:
						gc.drawImage(yellowKey, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
						break;
					case ORANGEKEY:
						gc.drawImage(orangeKey, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
						break;
					case PURPLEKEY:
						gc.drawImage(purpleKey, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
						break;
				}
				break;
			// TOKEN.
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
			case "H":
				Hazard hazard = hazards[row][col];
				switch (hazard.getType()) {
					case WATER:
						gc.drawImage(water, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
						break;
					case FIRE:
						gc.drawImage(fire, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
						break;
				}
				break;
			case "P":
				gc.drawImage(portal, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
				break;	
			case "E":
				// DRAW ENEMY.
				for (Enemy elem : enemies) {
					if (elem.getX() == col && elem.getY() == row) {
						switch (elem.getType()) {
							case STRAIGHT:
								gc.drawImage(straightEnemy, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
								break;
							case WALL:
								gc.drawImage(wallEnemy, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
								break;
							case DUMB:
								gc.drawImage(dumbEnemy, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
								break;
							case SMART:
								gc.drawImage(smartEnemy, tempCol * GRID_CELL_WIDTH, tempRow * GRID_CELL_HEIGHT);
								break;
						}
						break;
					}
				}
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
	 * Sets up the elements for the next level.
	 */
	public void loadNewLevel() {
		// Set up the level and its elements.
		if (MAX_LEVEL == levelNum) {
			// GAME COMPLETE!
		} else {
			levelNum++;
			// Change if the highest level the user can access is less than 
			// the current value. NOTE: It's possible to complete the whole
			// game and wanting to play again via New Game.
			int userCurrentLevel = currentUser.getCurrentLevel();
			if (levelNum > userCurrentLevel) {
				// Edit the user's level number and apply the change to the text file.
				String strOldUser = currentUser.toStringDetail();
				currentUser.setCurrentLevel(levelNum);
				String strNewUser = currentUser.toStringDetail();
				FileHandling.editUser(strOldUser, strNewUser);
			} 
			
			currentLevel = FileHandling.getLevel(levelNum);
			levelElements = currentLevel.getLevelElements();
			player = currentLevel.getPlayer();
			playerSprite = playerDefault;
			
			doors = currentLevel.getDoors();
			apparels = currentLevel.getApparels();
			items = currentLevel.getItems();
			hazards = currentLevel.getHazards();
			portals = currentLevel.getPortals();
			enemies = currentLevel.getEnemies();
			
			lblToken.setText("0");
			txtLevelPrompt.appendText("Welcome to Level " + levelNum + ".");
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
		
		doors = currentLevel.getDoors(); 
		apparels = currentLevel.getApparels();
		items = currentLevel.getItems();
		hazards = currentLevel.getHazards();
		portals = currentLevel.getPortals();
		enemies = currentLevel.getEnemies();
		
		lblToken.setText("0");
		txtLevelPrompt.appendText("Welcome to Level " + levelNum + ".");
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
            
            // Update the level with the changed sprite.
            playerSprite = inventoryWindow.getPlayerSprite();
            drawLevel();
		} catch (IOException e) {
			// Catches an IO exception such as that where the FXML
            // file is not found.
            e.printStackTrace();
            System.exit(-1);
		}
	}
	
	/**
	 * Sets the level the player has selected.
	 * @param levelNum The level number as an integer.
	 */
	public void setLevelNumber(int levelNum) {
		this.levelNum = levelNum;
	}
	
	/**
	 * Sets the user who is playing the game.
	 * @param user The user to be set.
	 */
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
	
	/**
	 * Closes the game window, then opens the main menu.
	 */
	public void quitButtonAction() {
		// Closes the window.
		Stage stage = (Stage) btnQuit.getScene().getWindow();
		stage.close();
		
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
