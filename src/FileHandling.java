import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * The FileReader class holds all file reading related methods. 
 * @author William King
 */
public class FileHandling {
	/** File location of the data files. */
	private final static String DATA_FILE_PATH = "DataFiles/";
	/** File location of the level text files. */
	private final static String LEVEL_FILE_PATH = "DataFiles/Levels/";
	
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
	    	//Opens the file for reading
			in = new Scanner (inputFile);
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    currentUser = in.next();
		return currentUser;
	}
	
	/**
	 * Fetches all the current users in the system and stores
	 * them in a Linked Hashmap.
	 * @return LinkedHashMap of all users in the system.
	 */
	public static LinkedHashMap<String, User> getUsers() {
		String filePath = DATA_FILE_PATH + "User.txt";
		File inputFile = new File(filePath);
		Scanner in = null;
	    try {
	    	//Opens the file for reading
			in = new Scanner (inputFile);
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
	 * Fetches a single specified level from the game.
	 * @param levelNum Indicates which level to get as an integer.
	 * @return The level the user specifies.
	 */
	public static Level getLevel(int levelNum) {
		String filePath = LEVEL_FILE_PATH + "Level " + levelNum + ".txt";
		File inputFile = new File(filePath);
		Scanner in = null;
	    try {
	    	//Opens the file for reading
			in = new Scanner (inputFile);
		// Catch an exception if the file does not exist and exit the program.
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open " + filePath);
			System.exit(-1);
		}
	    
	    in.useDelimiter(",");
	    
	    // Read the level's height and width first.
	    int levelWidth = in.nextInt();
    	int levelHeight = in.nextInt();
	    in.nextLine();
	    
	    // Then read the level elements.
	    String[][] levelElements = new String[levelWidth][levelHeight];
	    
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
		    		Apparel newApparel = new Apparel(apparelX, apparelY, apparelType);
		    		apparels[apparelY][apparelX] = newApparel;
		    		break;
		    	case "ITEM":
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
		    		Item newItem = new Item(itemX, itemY, itemType);
		    		items[itemY][itemX] = newItem;
		    		break;
		    	case "DOOR":
		    		// Read doors, construct them and add them to a list.
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
		    		Door newDoor = new Door(doorX, doorY, doorType, numTokens);
		    		doors[doorY][doorX] = newDoor;
		    		break;
		    	case "ENEMY":
		    		// Read enemies, construct them and add them to a list.
		    		break;
		    }
		    in.nextLine();
	    }
	    in.close();
	    
	    // Construct the level.
	    Level newLevel = new Level(levelElements, levelNum, player, doors, apparels, items);
		return newLevel;
	}
	
	/**
	 * A new user is created by adding their details at the end
	 * of their respective textfile.
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
}
