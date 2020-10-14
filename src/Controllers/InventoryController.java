package controllers;

import java.io.File;

import data.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controller for the Inventory window.
 * Allows the user to browse through their current inventory
 * and equip any items they have.
 * @author William King
 */
public class InventoryController {
	/** File location of the player sprites. */
	private final static String PLAYER_FILE_PATH = "DataFiles/Graphics/Player Sprites/";
	/** File location of the items. */
	private final static String ITEM_FILE_PATH = "DataFiles/Graphics/Items/";
	
	/** Player in the game (to control its status). */
	private Player player;
	/** Global storage of the player's inventory. */
	private String[] inventory;
	
	// Loaded images.
	// Default with items.
	private Image playerDefault;
	private Image playerOrangeKey;
	private Image playerYellowKey;
	private Image playerPurpleKey;
	
	// Flippers with items.
	private Image playerFlippers;
	private Image flippersOrangeKey;
	private Image flippersYellowKey;
	private Image flippersPurpleKey;
	
	
	// Fire boots with items.
	private Image playerFireBoots;
	private Image fireBootsOrangeKey;
	private Image fireBootsYellowKey;
	private Image fireBootsPurpleKey;
	
	// Each individual item.
	private Image fireBoots;
	private Image flippers;
	private Image orangeKey;
	private Image yellowKey;
	private Image purpleKey;
	
	
	/** Holds the player's sprite. */
	private Image playerSprite;
	
	/** A list view to show the player's inventory. */
	@FXML private ListView<String> lstInventory;
	
	/** An image view to show the player sprite. */
	@FXML private ImageView imgViewPlayer; 
	/** An image view to show the apparel equipped. */
	@FXML private ImageView imgViewApparel; 
	/** An image view to show the item equipped. */
	@FXML private ImageView imgViewItem; 
	
	/** The allows the user to equip a selected item. */
	@FXML private Button btnEquip;
	/** The back button for the page. */
	@FXML private Button btnBack;
	
	/**
	 * Loads the player sprites that are equipped with items (and apparel).
	 */
	public void initialize() {
		// Load the sprites holding the items.
		playerDefault = new Image(new File (PLAYER_FILE_PATH + "Default.png").toURI().toString());
		playerOrangeKey = new Image(new File (PLAYER_FILE_PATH + "DefaultOrangeKey.png").toURI().toString());
		playerYellowKey = new Image(new File (PLAYER_FILE_PATH + "DefaultYellowKey.png").toURI().toString());
		playerPurpleKey = new Image(new File (PLAYER_FILE_PATH + "DefaultPurpleKey.png").toURI().toString());
		
		
		playerFlippers = new Image(new File (PLAYER_FILE_PATH + "Flippers.png").toURI().toString());
		flippersOrangeKey = new Image(new File (PLAYER_FILE_PATH + "FlippersOrangeKey.png").toURI().toString());
		flippersYellowKey = new Image(new File (PLAYER_FILE_PATH + "FlippersYellowKey.png").toURI().toString());
		flippersPurpleKey = new Image(new File (PLAYER_FILE_PATH + "FlippersPurpleKey.png").toURI().toString());
		
		playerFireBoots = new Image(new File (PLAYER_FILE_PATH + "FireBoots.png").toURI().toString());
		fireBootsOrangeKey = new Image(new File (PLAYER_FILE_PATH + "FireBootsOrangeKey.png").toURI().toString());
		fireBootsYellowKey = new Image(new File (PLAYER_FILE_PATH + "FireBootsYellowKey.png").toURI().toString());
		fireBootsPurpleKey = new Image(new File (PLAYER_FILE_PATH + "FireBootsPurpleKey.png").toURI().toString());
		
		// Load the individual items.
		flippers = new Image(new File (ITEM_FILE_PATH + "Flippers.png").toURI().toString());
		fireBoots = new Image(new File (ITEM_FILE_PATH + "FireBoots.png").toURI().toString());
		orangeKey = new Image(new File (ITEM_FILE_PATH + "OrangeKey.png").toURI().toString());
		yellowKey = new Image(new File (ITEM_FILE_PATH + "YellowKey.png").toURI().toString());
		purpleKey = new Image(new File (ITEM_FILE_PATH + "purpleKey.png").toURI().toString());
	}
	
	/**
	 * Determines when the Equip button is enabled.
	 * The player must select an item first.
	 */
	public void equipmentSelectAction() {
		int selectedIndex = lstInventory.getSelectionModel().getSelectedIndex();
		
		// If the list view was clicked on (or if there are no items).
		if (selectedIndex < 0) {
			return;
		}
		btnEquip.setDisable(false);
	}
	
	/**
	 * Equips the item selected by the player.
	 */
	public void equipButtonAction() {
		int selectedIndex = lstInventory.getSelectionModel().getSelectedIndex();
		String selectedItem = inventory[selectedIndex];
		player.addEquipped(selectedItem);
		
		// Refresh the sprites if the player has changed their equipped items.
		showSprites();
	}
	
	/**
	 * Displays the player's inventory in the list view.
	 */
	public void showInventory() {
		inventory = player.getInventory();
		for (String item : inventory) {
			lstInventory.getItems().add(item);
		}
	}
	
	/** 
	 * Show the appropriate sprites on screen based
	 * on the player's equipped items.
	 */
	public void showSprites() {
		String[] equippedItems = player.getEquippedItems();
		String apparel = equippedItems[0];
		String item = equippedItems[1];
		Image imgApparel = null;
		Image imgItem = null;
		
		// Checks apparel then checks for the item (key).
		switch (apparel) {
			case "Flippers":
				imgApparel = flippers;
				switch (item) {
					case "Orange Key":
						playerSprite = flippersOrangeKey;
						imgItem = orangeKey;
						break;
					case "Yellow Key":
						playerSprite = flippersYellowKey;
						imgItem = yellowKey;
						break;
					case "Purple Key":
						playerSprite = flippersPurpleKey;
						imgItem = purpleKey;
						break;
					default:
						playerSprite = playerFlippers;
						break;
				}
				break;
			case "Fire Boots":
				imgApparel = fireBoots;
				switch (item) {
					case "Orange Key":
						playerSprite = fireBootsOrangeKey;
						imgItem = orangeKey;
						break;
					case "Yellow Key":
						playerSprite = fireBootsYellowKey;
						imgItem = yellowKey;
						break;
					case "Purple Key":
						playerSprite = fireBootsPurpleKey;
						imgItem = purpleKey;
						break;
					default:
						playerSprite = playerFireBoots;
						break;
				}
				break;
			default:
				switch (item) {
				case "Orange Key":
					playerSprite = playerOrangeKey;
					imgItem = orangeKey;
					break;
				case "Yellow Key":
					playerSprite = playerYellowKey;
					imgItem = yellowKey;
					break;
				case "Purple Key":
					playerSprite = playerPurpleKey;
					imgItem = purpleKey;
					break;
				default:
					playerSprite = playerDefault;
					break;
				}
		}
		
		// Set the images on the image view.
		// If the images are still 'null' then it just clears the image view.
		imgViewApparel.setImage(imgApparel);
		imgViewItem.setImage(imgItem);
		imgViewPlayer.setImage(playerSprite);
	}
	
	/**
	 * Set the player to show its their inventory.
	 * @param currentPlayer The player of the game.
	 * @param playerSprite The player's current sprite.
	 */
	public void setPlayerDetails(Player currentPlayer, Image playerSprite) {
		this.player = currentPlayer;
		this.playerSprite = playerSprite;
	}
	
	/**
	 * Gets the updated player sprite after closing this page.
	 */
	public Image getPlayerSprite() {
		return playerSprite;
	}
	
	/**
	 * Closes the inventory page.
	 */
	public void backButtonAction() {
		// Closes the window.
		Stage stage = (Stage) btnBack.getScene().getWindow();
		stage.close();
	}
}