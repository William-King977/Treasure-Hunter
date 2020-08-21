import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controller for the Inventory window.
 * Allows the user to browse through their current inventory
 * and equip any items they have.
 * @author William King
 */
public class InventoryController {
	
	/** A list view to show the player's inventory */
	@FXML private ListView<String> lstInventory;
	
	/** An image view to show the player sprite. */
	@FXML private ImageView imgPlayer; 
	/** An image view to show the apparel equipped. */
	@FXML private ImageView imgApparel; 
	/** An image view to show the item equipped. */
	@FXML private ImageView imgItem; 
	
	/** The allows the user to equip a selected item. */
	@FXML private Button btnEquip;
	/** The back button for the page. */
	@FXML private Button btnBack;
	
	/**
	 * Equips the item selected by the player.
	 */
	public void equipButtonAction() {
		
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
