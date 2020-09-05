package Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

// NOTE: In a 2-D Array sense, Y is row and X is the column. So array[Y][X].

/**
 * The FileReader class holds all file reading related methods. 
 * @author William King
 */
public class FileHandling {
	/** File location of the data files. */
	private final static String DATA_FILE_PATH = "DataFiles/";
	/** File location of the level text files. */
	private final static String LEVEL_FILE_PATH = "DataFiles/Levels/";
	/** File location of the leaderboard times text files. */
	private final static String TIME_FILE_PATH = "DataFiles/Level Times/";
	/** The number of levels in the game i.e. the highest level in the game. */
	private final static int MAX_LEVEL = 5;
	
	/**
	 * Saves the username of the currently logged in user to a text file.
	 * @param currentUser Username of the current user.
	 */
	public static void setCurrentUser(String currentUser) {
		String filePath = DATA_FILE_PATH + "CurrentUser.txt";
		
		try {
			FileWriter myWriter = new FileWriter(filePath);
			myWriter.write(currentUser);
		    myWriter.close();
		} catch (IOException e) {
		      System.out.println("Cannot write to " + filePath);
		      System.exit(-1);
		}
	}
	
	/**
	 * Fetches the username of the currently logged in user.
	 * @return The current user's username.
	 */
	public static String getCurrentUser() {
		String currentUser = "";
		String filePath = DATA_FILE_PATH + "CurrentUser.txt";
		File inputFile = new File(filePath);
		Scanner in = null;
		
	    try {
	    	// Opens the file for reading
			in = new Scanner(inputFile);
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    currentUser = in.next();
		return currentUser;
	}
	
	/**
	 * Fetches all the current users in the system and stores them in a Linked Hashmap.
	 * @return LinkedHashMap of all users in the system.
	 */
	public static LinkedHashMap<String, User> getUsers() {
		String filePath = DATA_FILE_PATH + "User.txt";
		File inputFile = new File(filePath);
		Scanner in = null;
		
	    try {
	    	// Opens the file for reading
			in = new Scanner(inputFile);
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    in.useDelimiter(",");
	    // Read each user and store them in a linked hashmap.
	    // The key is the username.
	    LinkedHashMap<String, User> users = new LinkedHashMap<>();
	    while (in.hasNextLine()) {
	    	String username = in.next();
	    	int currentLevel = in.nextInt();
	    	User user = new User(username); 
	    	
	    	// Set any required values.
	    	user.setCurrentLevel(currentLevel); 
	    	users.put(username, user);
	    	in.nextLine(); // Needed if you change delimiter.
	    }
	    in.close();
		return users;
	}
	
	/**
	 * Fetches all the completion times of a specified level.
	 * @param levelNum The specified level number.
	 * @return LinkedHashMap of all the times.
	 */
	public static LinkedHashMap<String, LeaderboardTime> getLevelTimes(int levelNum) {
		String filePath = TIME_FILE_PATH + "Level " + levelNum + ".txt";
		File inputFile = new File(filePath);
		Scanner in = null;
		
	    try {
	    	// Opens the file for reading
			in = new Scanner(inputFile);
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    in.useDelimiter(",");
	    // Read each time and store them in a linked hashmap.
	    // The key is the username.
	    LinkedHashMap<String, LeaderboardTime> times = new LinkedHashMap<>();
	    while (in.hasNextLine()) {
	    	String username = in.next();
	    	long completionTime = in.nextInt();
	    	LeaderboardTime newTime = new LeaderboardTime(username, completionTime); 
	    	
	    	times.put(username, newTime);
	    	in.nextLine(); // Needed if you change delimiter.
	    }
	    in.close();
		return times;
	}
	
	/**
	 * Fetches all the total game completion times from each user.
	 * @return LinkedHashMap of all the times.
	 */
	public static LinkedHashMap<String, LeaderboardTime> getTotalGameTimes() {
		String filePath = TIME_FILE_PATH + "Total Time.txt";
		File inputFile = new File(filePath);
		Scanner in = null;
		
	    try {
	    	// Opens the file for reading
			in = new Scanner(inputFile);
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    in.useDelimiter(",");
	    // Read each time and store them in a linked hashmap.
	    // The key is the username.
	    LinkedHashMap<String, LeaderboardTime> times = new LinkedHashMap<>();
	    while (in.hasNextLine()) {
	    	String username = in.next();
	    	long completionTime = in.nextInt();
	    	LeaderboardTime newTime = new LeaderboardTime(username, completionTime); 
	    	
	    	times.put(username, newTime);
	    	in.nextLine(); // Needed if you change delimiter.
	    }
	    in.close();
		return times;
	}
	
	/**
	 * Fetches a single specified level from the game.
	 * @param levelNum Indicates which level to get as an integer.
	 * @return The level the user specifies.
	 */
	public static Level getLevel(int levelNum) {
		String filePath = LEVEL_FILE_PATH + "Level " + levelNum + ".txt";
		File inputFile = new File(filePath);
		Scanner in = null;
		
	    try {
	    	// Opens the file for reading
			in = new Scanner(inputFile);
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    in.useDelimiter(",");
	    
	    // Read the level's height and width first.
	    int levelHeight = in.nextInt();
    	int levelWidth = in.nextInt();
	    in.nextLine();
	    
	    // Then read the level elements.
	    String[][] levelElements = new String[levelHeight][levelWidth];
	    
	    for (int row = 0; row < levelHeight; row++) {
	    	for (int col = 0; col < levelWidth; col++) {
	    		String element = in.next();
	    		levelElements[row][col] = element;
	    	}
	    	in.nextLine(); // Needed if you change delimiter.
	    }
	    
	    // Read miscellaneous game objects (that require more details).
	    Player player = null;
	    Door[][] doors = new Door[levelHeight][levelWidth];
	    Apparel[][] apparels = new Apparel[levelHeight][levelWidth];
	    Item[][] items = new Item[levelHeight][levelWidth];
	    Hazard[][] hazards = new Hazard[levelHeight][levelWidth];
	    Portal[][] portals = new Portal[levelHeight][levelWidth];
	    ArrayList<Enemy> alEnemies = new ArrayList<Enemy>(); // Will be converted to an array.
	    
	    while(in.hasNextLine()) {
		    String elementType = in.next();
		    switch(elementType) {
		    	case "START":
		    		// Read the player's start position.
				    int playerX = in.nextInt();
				    int playerY = in.nextInt();
				    // Construct the player.
				    player = new Player(playerX, playerY);
				    break;
		    	case "APPAREL":
		    		Apparel newApparel = readApparel(in.next());
		    		int apparelX = newApparel.getX();
		    		int apparelY = newApparel.getY();
		    		apparels[apparelY][apparelX] = newApparel;
		    		break;
		    	case "ITEM":
		    		Item newItem = readItem(in.next());
		    		int itemX = newItem.getX();
		    		int itemY = newItem.getY();
		    		items[itemY][itemX] = newItem;
		    		break;
		    	case "DOOR":
		    		Door newDoor = readDoor(in.next());
	    			int doorX = newDoor.getX();
	    			int doorY = newDoor.getY();
		    		doors[doorY][doorX] = newDoor;
		    		break;
		    	case "HAZARD":
		    		Hazard newHazard = readHazard(in.next());
		    		int hazardX = newHazard.getX();
		    		int hazardY = newHazard.getY();
		    		hazards[hazardY][hazardX] = newHazard;
		    		break;
		    	case "PORTAL":
		    		Portal newPortal = readPortal(in.next());
		    		int portalX = newPortal.getX();
		    		int portalY = newPortal.getY();
		    		portals[portalY][portalX] = newPortal;
		    		break;
		    	case "ENEMY":
		    		Enemy newEnemy = readEnemy(in.next());
		    		alEnemies.add(newEnemy);
		    		break;
		    }
		    in.nextLine();
	    }
	    in.close();
	    
	    // Convert enemies array list to an array.
	    Enemy[] enemies = new Enemy[alEnemies.size()];
	    enemies = alEnemies.toArray(enemies);
	    
	    // Construct the level.
	    Level newLevel = new Level(levelElements, levelNum, player, doors, 
	    		apparels, items, hazards, portals, enemies);
		return newLevel;
	}
	
	/**
	 * Reads in the base of a specified level (only walls and goal elements).
	 * @param levelNum The specified level as an integer.
	 * @return A 2D array of level elements.
	 */
	private static String[][] readLevelBase(int levelNum) {
		// Read in the level text file (the level base).
		String filePath = LEVEL_FILE_PATH + "Base Level " + levelNum + ".txt";
		File inputFile = new File(filePath);
		Scanner in = null;
		
	    try {
	    	// Opens the file for reading
			in = new Scanner(inputFile);
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    // Read the level's details.
	    in.useDelimiter(",");
	    int levelHeight = in.nextInt();
    	int levelWidth = in.nextInt();
    	in.nextLine();
    	
    	String[][] levelElements = new String[levelHeight][levelWidth];
    	
    	for (int row = 0; row < levelHeight; row++) {
	    	for (int col = 0; col < levelWidth; col++) {
	    		String element = in.next();
	    		levelElements[row][col] = element;
	    	}
	    	in.nextLine(); // Needed if you change delimiter.
	    }
    	
    	in.close();
    	return levelElements;
	}
	
	/**
	 * A new user is created by adding their details at the end
	 * of their respective text file.
	 * @param newUser Details of the new registered user.
	 */
	public static void createUser(String newUser) {
		String filePath = DATA_FILE_PATH + "User.txt";
		File file = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter = null;
		PrintWriter printWriter = null;
		
		try { 
			file = new File(filePath);
			fileWriter = new FileWriter(file, true);
			buffWriter = new BufferedWriter(fileWriter);
			printWriter = new PrintWriter(buffWriter);
			
			// Writes the user then adds a new line. 
			printWriter.print(newUser);
			printWriter.println("");
			printWriter.close();
        } catch (IOException e) { 
            System.out.println("Cannot write to " + filePath); 
            System.exit(-1);
        } 
	}
	
	/**
	 * Writes in a new leaderboard time for a specified level.
	 * @param newTime The new leaderboard time as a string.
	 * @param levelNum The specified level number.
	 */
	public static void addNewLevelTime(String newTime, int levelNum) {
		String filePath = TIME_FILE_PATH + "Level " + levelNum + ".txt";
		File file = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter = null;
		PrintWriter printWriter = null;
		
		try { 
			file = new File(filePath);
			fileWriter = new FileWriter(file, true);
			buffWriter = new BufferedWriter(fileWriter);
			printWriter = new PrintWriter(buffWriter);
			
			// Writes in the completion time then adds a new line. 
			printWriter.print(newTime);
			printWriter.println("");
			printWriter.close();
        } catch (IOException e) { 
            System.out.println("Cannot write to " + filePath); 
            System.exit(-1);
        }
	}
	
	/**
	 * Writes in a new total game completion time.
	 * @param newTime The new leaderboard time as a string.
	 */
	public static void addNewGameTime(String newTime) {
		String filePath = TIME_FILE_PATH + "Total Time.txt";
		File file = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter = null;
		PrintWriter printWriter = null;
		
		try { 
			file = new File(filePath);
			fileWriter = new FileWriter(file, true);
			buffWriter = new BufferedWriter(fileWriter);
			printWriter = new PrintWriter(buffWriter);
			
			// Writes in the completion time then adds a new line. 
			printWriter.print(newTime);
			printWriter.println("");
			printWriter.close();
        } catch (IOException e) { 
            System.out.println("Cannot write to " + filePath); 
            System.exit(-1);
        }
	}
	
	/**
	 * A user is edited by replacing the details of the previous user with the new one.
	 * @param oldUser The string details of the old user.
	 * @param newUser The string details of the edited user.
	 */
	public static void editUser(String oldUser, String newUser) {
		String filePath = DATA_FILE_PATH + "User.txt";
		File inputFile = new File(filePath);
		BufferedReader reader = null;
		FileWriter writer = null;
		String oldContent = "";
		
	    try {
			reader = new BufferedReader(new FileReader(inputFile));
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    try {
		    // Uses buffer to write old file contents to a string.
		    String line = reader.readLine();
		    while (line != null) {
		    	oldContent = oldContent + line + System.lineSeparator();
		        line = reader.readLine();
		    }
		    // Replace old user with the new one within the text file.
		    String newContent = oldContent.replace(oldUser, newUser);
		    writer = new FileWriter(filePath);
		    writer.write(newContent);
		    reader.close();
		    writer.flush();
		    writer.close();
	    } catch (IOException e) {
	    	// Catches an IO exception when the file can't 
	    	// be written.
            e.printStackTrace();
            System.exit(-1);
	    }
	}
	
	/**
	 * Edits a player's completion time by replacing the old time with the new one.
	 * @param oldTime The string details of the old time.
	 * @param newTime The string details of the new time.
	 * @param levelNum The specified level number.
	 */
	public static void editLevelTime(String oldTime, String newTime, int levelNum) {
		String filePath = TIME_FILE_PATH + "Level " + levelNum + ".txt";
		File inputFile = new File(filePath);
		BufferedReader reader = null;
		FileWriter writer = null;
		String oldContent = "";
		
	    try {
			reader = new BufferedReader(new FileReader(inputFile));
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    try {
		    // Uses buffer to write old file contents to a string.
		    String line = reader.readLine();
		    while (line != null) {
		    	oldContent = oldContent + line + System.lineSeparator();
		        line = reader.readLine();
		    }
		    // Replace old time with the new one within the text file.
		    String newContent = oldContent.replace(oldTime, newTime);
		    writer = new FileWriter(filePath);
		    writer.write(newContent);
		    reader.close();
		    writer.flush();
		    writer.close();
	    } catch (IOException e) {
	    	// Catches an IO exception when the file can't 
	    	// be written.
            e.printStackTrace();
            System.exit(-1);
	    }
	}
	
	/**
	 * Edits a player's total completion time by replacing the 
	 * old time with the new one.
	 * @param oldTime The string details of the old time.
	 * @param newTime The string details of the new time.
	 */
	public static void editGameTime(String oldTime, String newTime) {
		String filePath = TIME_FILE_PATH + "Total Time.txt";
		File inputFile = new File(filePath);
		BufferedReader reader = null;
		FileWriter writer = null;
		String oldContent = "";
		
	    try {
			reader = new BufferedReader(new FileReader(inputFile));
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    try {
		    // Uses buffer to write old file contents to a string.
		    String line = reader.readLine();
		    while (line != null) {
		    	oldContent = oldContent + line + System.lineSeparator();
		        line = reader.readLine();
		    }
		    // Replace old time with the new one within the text file.
		    String newContent = oldContent.replace(oldTime, newTime);
		    writer = new FileWriter(filePath);
		    writer.write(newContent);
		    reader.close();
		    writer.flush();
		    writer.close();
	    } catch (IOException e) {
	    	// Catches an IO exception when the file can't 
	    	// be written.
            e.printStackTrace();
            System.exit(-1);
	    }
	}
	
	/**
	 * A new game state is created by adding their details at the end
	 * of their respective text file.
	 * @param newGameState Details of the saved game state.
	 */
	public static void saveGameState(String newGameState) {
		String filePath = DATA_FILE_PATH + "SavedGameStates.txt";
		File file = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter = null;
		PrintWriter printWriter = null;
		
		try { 
			file = new File(filePath);
			fileWriter = new FileWriter(file, true);
			buffWriter = new BufferedWriter(fileWriter);
			printWriter = new PrintWriter(buffWriter);
			
			// Writes the game state then adds a new line. 
			printWriter.print(newGameState);
			printWriter.println("");
			printWriter.close();
        } catch (IOException e) { 
            System.out.println("Cannot write to " + filePath); 
            System.exit(-1);
        } 
	}
	
	/**
	 * Fetches the saved game states that the player has made.
	 * @param username The username of the player.
	 * @return An array list of saved game states. 
	 */
	public static ArrayList<GameState> getGameStates(String username) {
		// Read in the game states text file.
		String filePath = DATA_FILE_PATH + "SavedGameStates.txt";
		File inputFile = new File(filePath);
		Scanner in = null;
		
	    try {
	    	// Opens the file for reading
			in = new Scanner(inputFile);
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    in.useDelimiter(",");
	    // Store states in an array list. 
	    ArrayList<GameState> gameStates = new ArrayList<>();
	    
	    // Read each saved state made by the user.
	    while (in.hasNextLine()) {
	    	// Fetch the line (game state as a string) and 
	    	// read it using a separate scanner.
	    	String strGameState = in.nextLine();
	    	Scanner readState = new Scanner(strGameState);
	    	readState.useDelimiter(",");
	    	
	    	// Check if it's the user's save state.
	    	String user = readState.next();
	    	if (!user.equals(username)) {
	    		continue;
	    	}
	    	
	    	// Read the game state details.
	    	String description = readState.next();
	    	long currentLevelTime = readState.nextLong();
	    	long currentGameTime = readState.nextLong();
	    	boolean timeValid = readState.nextBoolean();
	    	String saveDate = readState.next();
	    	String saveTime = readState.next();
	    	int levelNum = readState.nextInt();
	   
	    	// Construct the player.
	    	Player player = readPlayer(readState.next());
	    	
	    	// Fetch the level base.
	    	String[][] levelElements = readLevelBase(levelNum);
	    	int levelHeight = levelElements.length;
	    	int levelWidth = levelElements[0].length;
	    	
	    	// Read miscellaneous game objects (that require more details).
		    Door[][] doors = new Door[levelHeight][levelWidth];
		    Apparel[][] apparels = new Apparel[levelHeight][levelWidth];
		    Item[][] items = new Item[levelHeight][levelWidth];
		    Hazard[][] hazards = new Hazard[levelHeight][levelWidth];
		    Portal[][] portals = new Portal[levelHeight][levelWidth];
		    ArrayList<Enemy> alEnemies = new ArrayList<Enemy>(); // Will be converted to an array.
	    	
	    	// Read the elements of the level (from its current state).
	    	while (readState.hasNext()) {
	    		String element = readState.next();
	    		switch (element) {
		    		case "DOOR":
		    			Door newDoor = readDoor(readState.next());
		    			int doorX = newDoor.getX();
		    			int doorY = newDoor.getY();
			    		doors[doorY][doorX] = newDoor;
			    		levelElements[doorY][doorX] = "D";
		    			break;
		    		case "APPAREL":
			    		Apparel newApparel = readApparel(readState.next());
			    		int apparelX = newApparel.getX();
			    		int apparelY = newApparel.getY();
			    		apparels[apparelY][apparelX] = newApparel;
			    		levelElements[apparelY][apparelX] = "A";
			    		break;
			    	case "ITEM":
			    		Item newItem = readItem(readState.next());
			    		int itemX = newItem.getX();
			    		int itemY = newItem.getY();
			    		items[itemY][itemX] = newItem;
			    		levelElements[itemY][itemX] = "I";
			    		break;
			    	case "HAZARD":
			    		Hazard newHazard = readHazard(readState.next());
			    		int hazardX = newHazard.getX();
			    		int hazardY = newHazard.getY();
			    		hazards[hazardY][hazardX] = newHazard;
			    		levelElements[hazardY][hazardX] = "H";
			    		break;
			    	case "PORTAL":
			    		Portal newPortal = readPortal(readState.next());
			    		int portalX = newPortal.getX();
			    		int portalY = newPortal.getY();
			    		portals[portalY][portalX] = newPortal;
			    		levelElements[portalY][portalX] = "P";
			    		break;
			    	case "ENEMY":
			    		Enemy newEnemy = readEnemy(readState.next());
			    		int enemyX = newEnemy.getX();
			    		int enemyY = newEnemy.getY();
			    		alEnemies.add(newEnemy);
			    		levelElements[enemyY][enemyX] = "E";
		    			break;
		    		case "TOKEN":
		    			int tokenX = readState.nextInt();
		    			int tokenY = readState.nextInt();
		    			levelElements[tokenY][tokenX] = "T";
		    			break;
	    		}
	    	}
	    	
	    	 // Convert enemies array list to an array.
		    Enemy[] enemies = new Enemy[alEnemies.size()];
		    enemies = alEnemies.toArray(enemies);
		    
		    // Construct the level.
		    Level newLevel = new Level(levelElements, levelNum, player, doors, 
		    apparels, items, hazards, portals, enemies);
		    
		    // Construct the game state.
		    GameState newState = new GameState(username, description, currentLevelTime, 
		    		currentGameTime, timeValid, newLevel);
		    // Overwrite the date and time (made in constructor) with the actual ones.
		    newState.setSaveDate(saveDate);
		    newState.setSaveTime(saveTime);
	    	gameStates.add(newState);
	    	
	    	// Close the scanner for the current line.
	    	readState.close();
	    }
	    
	    in.close();
	    return gameStates;
	}
	
	/**
	 * Reads in and constructs the player based on the string elements passed in.
	 * Only used for reading the player from a saved state.
	 * @param strPlayer A string holding the player's details.
	 * @return A player object.
	 */
	private static Player readPlayer(String strPlayer) {
		// Scanner to read the player's details.
	    Scanner in = new Scanner(strPlayer);
	    in.useDelimiter(":");
	    
	    int playerX = in.nextInt();
    	int playerY = in.nextInt();
    	String strInventory = in.next();
    	String strEquipped = in.next();
    	int numPlayerTokens = in.nextInt();
    	String[] inventory = {};
    	String[] equippedItems = {};
    	
    	// Construct the player.
    	Player newPlayer = new Player(playerX, playerY);
    	newPlayer.setNumTokens(numPlayerTokens);
    	
    	// If it's not empty, then populate the inventory/equipped items.
    	if (!strInventory.isEmpty()) {
    		inventory = addItems(strInventory);
    		newPlayer.setInventory(inventory);
    	}
    	
    	if (!strEquipped.isEmpty()) {
    		equippedItems = addItems(strEquipped);
    		newPlayer.setEquippedItems(equippedItems);
    	}
    	
    	in.close();
    	return newPlayer;
	}
	
	/**
	 * Reads in and constructs a door based on the string elements passed in.
	 * @param strDoor A string holding the door's details.
	 * @return A door object.
	 */
	private static Door readDoor(String strDoor) {
		// Scanner to read the door's details.
	    Scanner in = new Scanner(strDoor);
	    in.useDelimiter(":");
	    
		int doorX = in.nextInt();
		int doorY = in.nextInt();
		String dType = in.next();
		int numTokens = in.nextInt();
		DoorType doorType = null;
		switch (dType) {
    		case "YELLOW":
    			doorType = DoorType.YELLOW;
    			break;
    		case "ORANGE":
    			doorType = DoorType.ORANGE;
    			break;
    		case "PURPLE":
    			doorType = DoorType.PURPLE;
    			break;
    		case "TOKEN":
    			doorType = DoorType.TOKEN;
    			break;
		}
		
		in.close();
		Door newDoor = new Door(doorX, doorY, doorType, numTokens);
		return newDoor;
	}
	
	/**
	 * Reads in and constructs an apparel based on the string elements passed in.
	 * @param strApparel A string holding the apparel's details.
	 * @return An apparel object.
	 */
	private static Apparel readApparel(String strApparel) {
		// Scanner to read the apparel's details.
	    Scanner in = new Scanner(strApparel);
	    in.useDelimiter(":");
	    
		int apparelX = in.nextInt();
		int apparelY = in.nextInt();
		String aType = in.next();
		ApparelType apparelType = null;
		switch (aType) {
    		case "FLIPPERS":
    			apparelType = ApparelType.FLIPPERS;
    			break;
    		case "FIREBOOTS":
    			apparelType = ApparelType.FIREBOOTS;
    			break;
		}
		
		in.close();
		Apparel newApparel = new Apparel(apparelX, apparelY, apparelType);
		return newApparel;
	}
	
	/**
	 * Reads in and constructs an item based on the string elements passed in.
	 * @param strItem A string holding the item's details.
	 * @return An item object.
	 */
	private static Item readItem(String strItem) {
		// Scanner to read the item's details.
	    Scanner in = new Scanner(strItem);
	    in.useDelimiter(":");
	    
		int itemX = in.nextInt();
		int itemY = in.nextInt();
		String iType = in.next();
		ItemType itemType = null;
		switch (iType) {
    		case "YELLOWKEY":
    			itemType = ItemType.YELLOWKEY;
    			break;
    		case "ORANGEKEY":
    			itemType = ItemType.ORANGEKEY;
    			break;
    		case "PURPLEKEY":
    			itemType = ItemType.PURPLEKEY;
    			break;
		}
		
		in.close();
		Item newItem = new Item(itemX, itemY, itemType);
		return newItem;
	}
	
	/**
	 * Reads in and constructs a hazard based on the string elements passed in.
	 * @param strHazard A string holding the hazard's details.
	 * @return A hazard object.
	 */
	private static Hazard readHazard(String strHazard) {
		// Scanner to read the hazard's details.
	    Scanner in = new Scanner(strHazard);
	    in.useDelimiter(":");
	    
		int hazardX = in.nextInt();
		int hazardY = in.nextInt();
		String hType = in.next();
		HazardType hazardType = null;
		switch (hType) {
    		case "WATER":
    			hazardType = HazardType.WATER;
    			break;
    		case "FIRE":
    			hazardType = HazardType.FIRE;
    			break;
		}
		
		in.close();
		Hazard newHazard = new Hazard(hazardX, hazardY, hazardType);
		return newHazard;
	}
	
	/**
	 * Reads in and constructs a portal based on the string elements passed in.
	 * @param strPortal A string holding the portal's details.
	 * @return A portal object.
	 */
	private static Portal readPortal(String strPortal) {
		// Scanner to read the portal's details.
	    Scanner in = new Scanner(strPortal);
	    in.useDelimiter(":");
	    
		int portalX = in.nextInt();
		int portalY = in.nextInt();
		int destX = in.nextInt();
		int destY = in.nextInt();
		in.close();
		
		Portal newPortal = new Portal(portalX, portalY, destX, destY);
		return newPortal;
	}
	
	/**
	 * Reads in and constructs an enemy based on the string elements passed in.
	 * @param strEnemy A string holding the enemy's details.
	 * @return An enemy object.
	 */
	private static Enemy readEnemy(String strEnemy) {
		// Scanner to read the enemy's details.
	    Scanner in = new Scanner(strEnemy);
	    in.useDelimiter(":");
	    
	    int enemyX = in.nextInt();
		int enemyY = in.nextInt();
		String eType = in.next();
		String moveDirection = in.next();
		EnemyType enemyType = null;
		switch (eType) {
    		case "STRAIGHT":
    			enemyType = EnemyType.STRAIGHT;
    			break;
    		case "WALL":
    			enemyType = EnemyType.WALL;
    			break;
    		case "DUMB":
    			enemyType = EnemyType.DUMB;
    			break;
    		case "SMART":
    			enemyType = EnemyType.SMART;
    			break;
		}
		
		in.close();
		Enemy newEnemy = new Enemy(enemyX, enemyY, enemyType, moveDirection);
		return newEnemy;
	}
	
	/**
	 * Reads the individual items from a string that are separated by a delimiter.
	 * Used to read the player's inventory/equipped items when loading a save state.
	 * @param itemSet The single string of items.
	 * @return A string array of items.
	 */
	private static String[] addItems(String itemSet) {
		// Scanner to read individual items.
	    Scanner in = new Scanner(itemSet);
		int numItems = 0;
		in.useDelimiter(";");
	    
	    // Reads through each item to check how many there are.
	    while (in.hasNext()) {
	    	numItems++;
	    	in.next();
	    }
	    
	    // Closes the file stream after reading all the items.
	    in.close();
	    
	    String[] itemsArray = new String[numItems];
	    
	    // Scanner 'in' is redeclared so that its contents can be inserted 
	    // into the array. (.next() was used on it earlier).
	    in = new Scanner(itemSet);
	    in.useDelimiter(";");
	    
	    // Insert the items into the array.
	    for (int i = 0; i < numItems; i++) {
	    	String item = in.next();
	    	itemsArray[i] = item;
	    }
	    
	    in.close();
	    return itemsArray;
	}
	
	/**
	 * Deletes the specified save state from the text file.
	 * @param strSaveState The save state to be deleted as a string.
	 */
	public static void deleteSaveState(String strSaveState) {
		String filePath = DATA_FILE_PATH + "SavedGameStates.txt";
		File inputFile = new File(filePath);
		BufferedReader reader = null;
		FileWriter writer = null;
		String newContent = "";
		
	    try {
			reader = new BufferedReader(new FileReader(inputFile));
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    try {
		    // Uses buffer to write the old contents to a string.
	    	// The contents will exclude the save state to be deleted.
		    String line = reader.readLine();
		    while (line != null) {
		    	// Exclude the passed down save state.
		    	if (!line.equals(strSaveState)) {
		    		newContent = newContent + line + System.lineSeparator();
		    	}
		        line = reader.readLine();
		    }
		    // Write the updated contents to the text file.
		    writer = new FileWriter(filePath);
		    writer.write(newContent);
		    reader.close();
		    writer.flush();
		    writer.close();
	    } catch (IOException e) {
	    	// Catches an IO exception when the file can't 
	    	// be written.
            e.printStackTrace();
            System.exit(-1);
	    }
	}
	
	/**
	 * Deletes the user's profile and any associated data.
	 * @param username The username of the user to be deleted.
	 */
	public static void deleteProfile(String username) {
		// Delete the user's level times from each level.
		for (int i = 1; i <= MAX_LEVEL; i++) {
			deleteLevelTime(username, i);
		}
		
		deleteGameTime(username);
		deleteGameStates(username);
		deleteUserProfile(username);
	}
	
	/**
	 * Deletes the user's profile on the text file.
	 * @param username The user's username.
	 */
	private static void deleteUserProfile(String username) {
		String filePath = DATA_FILE_PATH + "User.txt";
		File inputFile = new File(filePath);
		BufferedReader reader = null;
		FileWriter writer = null;
		String newContent = "";
		
	    try {
			reader = new BufferedReader(new FileReader(inputFile));
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    try {
		    // Uses buffer to write the old contents to a string.
	    	// The contents will exclude the user.
		    String line = reader.readLine();
		    while (line != null) {
		    	// Exclude user.
		    	if (!line.contains(username)) {
		    		newContent = newContent + line + System.lineSeparator();
		    	}
		        line = reader.readLine();
		    }
		    // Write the updated contents to the text file.
		    writer = new FileWriter(filePath);
		    writer.write(newContent);
		    reader.close();
		    writer.flush();
		    writer.close();
	    } catch (IOException e) {
	    	// Catches an IO exception when the file can't 
	    	// be written.
            e.printStackTrace();
            System.exit(-1);
	    }
	}
	
	/**
	 * Deletes the user's completion time on the specified level.
	 * @param username The user's username.
	 * @param levelNum The level number.
	 */
	private static void deleteLevelTime(String username, int levelNum) {
		String filePath = TIME_FILE_PATH + "Level " + levelNum + ".txt";
		File inputFile = new File(filePath);
		BufferedReader reader = null;
		FileWriter writer = null;
		String newContent = "";
		
	    try {
			reader = new BufferedReader(new FileReader(inputFile));
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    try {
		    // Uses buffer to write the old contents to a string.
	    	// The contents will exclude the user's time.
		    String line = reader.readLine();
		    while (line != null) {
		    	// Exclude user's time.
		    	if (!line.contains(username)) {
		    		newContent = newContent + line + System.lineSeparator();
		    	}
		        line = reader.readLine();
		    }
		    // Write the updated contents to the text file.
		    writer = new FileWriter(filePath);
		    writer.write(newContent);
		    reader.close();
		    writer.flush();
		    writer.close();
	    } catch (IOException e) {
	    	// Catches an IO exception when the file can't 
	    	// be written.
            e.printStackTrace();
            System.exit(-1);
	    }
	}
	
	/**
	 * Deletes the user's game completion time.
	 * @param username The user's username.
	 */
	private static void deleteGameTime(String username) {
		String filePath = TIME_FILE_PATH + "Total Time.txt";
		File inputFile = new File(filePath);
		BufferedReader reader = null;
		FileWriter writer = null;
		String newContent = "";
		
	    try {
			reader = new BufferedReader(new FileReader(inputFile));
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    try {
		    // Uses buffer to write the old contents to a string.
	    	// The contents will exclude the user's time.
		    String line = reader.readLine();
		    while (line != null) {
		    	// Exclude user's time.
		    	if (!line.contains(username)) {
		    		newContent = newContent + line + System.lineSeparator();
		    	}
		        line = reader.readLine();
		    }
		    // Write the updated contents to the text file.
		    writer = new FileWriter(filePath);
		    writer.write(newContent);
		    reader.close();
		    writer.flush();
		    writer.close();
	    } catch (IOException e) {
	    	// Catches an IO exception when the file can't 
	    	// be written.
            e.printStackTrace();
            System.exit(-1);
	    }
	}
	
	/**
	 * Deletes all of the user's saved game states.
	 * @param username The user's username.
	 */
	private static void deleteGameStates(String username) {
		String filePath = DATA_FILE_PATH + "SavedGameStates.txt";
		File inputFile = new File(filePath);
		BufferedReader reader = null;
		FileWriter writer = null;
		String newContent = "";
		
	    try {
			reader = new BufferedReader(new FileReader(inputFile));
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    try {
		    // Uses buffer to write the old contents to a string.
	    	// The contents will exclude the user's save states.
		    String line = reader.readLine();
		    while (line != null) {
		    	// Exclude user's save states.
		    	if (!line.contains(username)) {
		    		newContent = newContent + line + System.lineSeparator();
		    	}
		        line = reader.readLine();
		    }
		    // Write the updated contents to the text file.
		    writer = new FileWriter(filePath);
		    writer.write(newContent);
		    reader.close();
		    writer.flush();
		    writer.close();
	    } catch (IOException e) {
	    	// Catches an IO exception when the file can't 
	    	// be written.
            e.printStackTrace();
            System.exit(-1);
	    }
	}
}
