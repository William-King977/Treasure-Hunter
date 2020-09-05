package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import data.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller for the Leaderboard page.
 * @author William King
 */
public class LeaderboardController {
	/** Title for the main menu. */
	private final String MAIN_MENU_TITLE = "Main Menu";
	
	/** Holds the completion times for level 1. */
	private LinkedHashMap<String, LeaderboardTime> level1Times;
	/** Holds the completion times for level 2. */
	private LinkedHashMap<String, LeaderboardTime> level2Times;
	/** Holds the completion times for level 3. */
	private LinkedHashMap<String, LeaderboardTime> level3Times;
	/** Holds the completion times for level 4. */
	private LinkedHashMap<String, LeaderboardTime> level4Times;
	/** Holds the completion times for level 5. */
	private LinkedHashMap<String, LeaderboardTime> level5Times;
	/** Holds the completion times for the game (from every player). */
	private LinkedHashMap<String, LeaderboardTime> gameTimes;
	
	/** Holds the completion times for level 1 in an array list. */
	private ArrayList<LeaderboardTime> level1TimesAL;
	/** Holds the completion times for level 2 in an array list. */
	private ArrayList<LeaderboardTime> level2TimesAL;
	/** Holds the completion times for level 3 in an array list. */
	private ArrayList<LeaderboardTime> level3TimesAL;
	/** Holds the completion times for level 4 in an array list. */
	private ArrayList<LeaderboardTime> level4TimesAL;
	/** Holds the completion times for level 5 in an array list. */
	private ArrayList<LeaderboardTime> level5TimesAL;
	/** Holds the completion times for the game in an array list (from every player). */
	private ArrayList<LeaderboardTime> gameTimesAL;
	
	/** The table view used to hold each player's time. */
	@FXML private TableView<LeaderboardTime> tblLeaderboard;
	/** The table column to hold each player's rank. */
	@FXML private TableColumn colRank;
	/** The table column to hold each player's username. */
	@FXML private TableColumn colPlayer;
	/** The table column to hold each player's completion time. */
	@FXML private TableColumn colTime;
	/** A check box used to only show the level 1 times. */
	@FXML private CheckBox cbLevel1;
	/** A check box used to only show the level 2 times. */
	@FXML private CheckBox cbLevel2;
	/** A check box used to only show the level 3 times. */
	@FXML private CheckBox cbLevel3;
	/** A check box used to only show the level 4 times. */
	@FXML private CheckBox cbLevel4;
	/** A check box used to only show the level 5 times. */
	@FXML private CheckBox cbLevel5;
	/** A check box used to only show the game completion time. */
	@FXML private CheckBox cbWholeGame;
	/** The back button for the page. */
	@FXML private Button btnBack;
	
	/**
	 * Gets the completion times of each level and the whole game, then
	 * displays the times for level 1. 
	 * This method runs automatically.
	 */
	public void initialize() {
		// Fetch the completion times.
		level1Times = FileHandling.getLevelTimes(1);
		level2Times = FileHandling.getLevelTimes(2);
		level3Times = FileHandling.getLevelTimes(3);
		level4Times = FileHandling.getLevelTimes(4);
		level5Times = FileHandling.getLevelTimes(5);
		gameTimes = FileHandling.getTotalGameTimes();
		
		// Set property values for each column in the table view.
		colRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
		colPlayer.setCellValueFactory(new PropertyValueFactory<>("username"));
		colTime.setCellValueFactory(new PropertyValueFactory<>("formattedTime"));
		
		// Convert each linked hashmap into an array list (allows it to be sorted).
		// Then sort each array list in descending order of completion time.
		level1TimesAL = new ArrayList<LeaderboardTime>(level1Times.values());
		level2TimesAL = new ArrayList<LeaderboardTime>(level2Times.values());
		level3TimesAL = new ArrayList<LeaderboardTime>(level3Times.values());
		level4TimesAL = new ArrayList<LeaderboardTime>(level4Times.values());
		level5TimesAL = new ArrayList<LeaderboardTime>(level5Times.values());
		gameTimesAL = new ArrayList<LeaderboardTime>(gameTimes.values());
		
		Collections.sort(level1TimesAL);
		Collections.sort(level2TimesAL);
		Collections.sort(level3TimesAL);
		Collections.sort(level4TimesAL);
		Collections.sort(level5TimesAL);
		Collections.sort(gameTimesAL);
		
		// Display the times from level 1 by default.
		// Rank is set for visual purposes.
		int rank = 1;
		for (LeaderboardTime time : level1TimesAL) {
			time.setRank(rank);
			tblLeaderboard.getItems().add(time);
			rank++;
		}
		
		// Set the ranks for the other level times.
		rank = 1;
		for (LeaderboardTime time : level2TimesAL) {
			time.setRank(rank);
			rank++;
		}
		rank = 1;
		for (LeaderboardTime time : level3TimesAL) {
			time.setRank(rank);
			rank++;
		}
		rank = 1;
		for (LeaderboardTime time : level4TimesAL) {
			time.setRank(rank);
			rank++;
		}
		rank = 1;
		for (LeaderboardTime time : level5TimesAL) {
			time.setRank(rank);
			rank++;
		}
		rank = 1;
		for (LeaderboardTime time : gameTimesAL) {
			time.setRank(rank);
			rank++;
		}
	}
	
	/**
	 * Sets the status of the level 1 check box and makes the 
     * appropriate changes to the leaderboard when selected.
	 */
	public void cbLevel1Action() {
		// Clear the other checkboxes.
		cbLevel2.setSelected(false);
		cbLevel3.setSelected(false);
		cbLevel4.setSelected(false);
		cbLevel5.setSelected(false);
		cbWholeGame.setSelected(false);
		
		if (cbLevel1.isSelected()) {
			// Clear the table and add the new items.
			tblLeaderboard.getItems().clear();
			for (LeaderboardTime time : level1TimesAL) {
				tblLeaderboard.getItems().add(time);
			}
		// Keep it selected if it's clicked on again.
		} else {
			cbLevel1.setSelected(true);
		}
	}
	
	/**
	 * Sets the status of the level 2 check box and makes the 
     * appropriate changes to the leaderboard when selected.
	 */
	public void cbLevel2Action() {
		// Clear the other checkboxes.
		cbLevel1.setSelected(false);
		cbLevel3.setSelected(false);
		cbLevel4.setSelected(false);
		cbLevel5.setSelected(false);
		cbWholeGame.setSelected(false);
		
		if (cbLevel2.isSelected()) {
			// Clear the table and add the new items.
			tblLeaderboard.getItems().clear();
			for (LeaderboardTime time : level2TimesAL) {
				tblLeaderboard.getItems().add(time);
			}
		// Keep it selected if it's clicked on again.
		} else {
			cbLevel2.setSelected(true);
		}
	}
	
	/**
	 * Sets the status of the level 3 check box and makes the 
     * appropriate changes to the leaderboard when selected.
	 */
	public void cbLevel3Action() {
		// Clear the other checkboxes.
		cbLevel1.setSelected(false);
		cbLevel2.setSelected(false);
		cbLevel4.setSelected(false);
		cbLevel5.setSelected(false);
		cbWholeGame.setSelected(false);
		
		if (cbLevel3.isSelected()) {
			// Clear the table and add the new items.
			tblLeaderboard.getItems().clear();
			for (LeaderboardTime time : level3TimesAL) {
				tblLeaderboard.getItems().add(time);
			}
		// Keep it selected if it's clicked on again.
		} else {
			cbLevel3.setSelected(true);
		}
	}
	
	/**
	 * Sets the status of the level 4 check box and makes the 
     * appropriate changes to the leaderboard when selected.
	 */
	public void cbLevel4Action() {
		// Clear the other checkboxes.
		cbLevel1.setSelected(false);
		cbLevel2.setSelected(false);
		cbLevel3.setSelected(false);
		cbLevel5.setSelected(false);
		cbWholeGame.setSelected(false);
		
		if (cbLevel4.isSelected()) {
			// Clear the table and add the new items.
			tblLeaderboard.getItems().clear();
			for (LeaderboardTime time : level4TimesAL) {
				tblLeaderboard.getItems().add(time);
			}
		// Keep it selected if it's clicked on again.
		} else {
			cbLevel4.setSelected(true);
		}
	}
	
	/**
	 * Sets the status of the level 5 check box and makes the 
     * appropriate changes to the leaderboard when selected.
	 */
	public void cbLevel5Action() {
		// Clear the other checkboxes.
		cbLevel1.setSelected(false);
		cbLevel2.setSelected(false);
		cbLevel3.setSelected(false);
		cbLevel4.setSelected(false);
		cbWholeGame.setSelected(false);
		
		if (cbLevel5.isSelected()) {
			// Clear the table and add the new items.
			tblLeaderboard.getItems().clear();
			for (LeaderboardTime time : level5TimesAL) {
				tblLeaderboard.getItems().add(time);
			}
		// Keep it selected if it's clicked on again.
		} else {
			cbLevel5.setSelected(true);
		}
	}
	
	/**
	 * Sets the status of the whole game check box and makes the 
     * appropriate changes to the leaderboard when selected.
	 */
	public void cbWholeGameAction() {
		// Clear other checkboxes.
		cbLevel1.setSelected(false);
		cbLevel2.setSelected(false);
		cbLevel3.setSelected(false);
		cbLevel4.setSelected(false);
		cbLevel5.setSelected(false);
		
		if (cbWholeGame.isSelected()) {
			// Clear the table and add the new items.
			tblLeaderboard.getItems().clear();
			for (LeaderboardTime time : gameTimesAL) {
				tblLeaderboard.getItems().add(time);
			}
		// Keep it selected if it's clicked on again.
		} else {
			cbWholeGame.setSelected(true);
		}
	}
	
	/**
	 * Closes this page, then opens the main menu. 
	 */
	public void backButtonAction() {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		stage.close();
		
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass()
					.getResource(Main.FXML_FILE_PATH + "MainMenu.fxml"));
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
