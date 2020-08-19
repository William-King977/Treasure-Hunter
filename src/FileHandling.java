import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * The FileReader class holds all file reading related methods. 
 * @author William King
 */
public class FileHandling {
	/** File location of the data files. */
	private final static String DATA_FILE_PATH = "DataFiles/";
	
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
	 * Fetches all the levels of the game
	 * @return ArrayList holding all of the levels.
	 */
	public static ArrayList<Level> getLevels() {
		ArrayList<Level> levels = new ArrayList<>();
		String levelFilePath = DATA_FILE_PATH + "Levels";
		int numberOfLevels = new File(levelFilePath).list().length;
		
		// Read each level file.
		for (int levelNum = 1; levelNum <= numberOfLevels; levelNum++) {
			String filePath = levelFilePath + "/Level " + levelNum + ".txt";
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
		    
		    String[][] levelElements = new String[levelWidth][levelHeight];
		    
		    // Then read the level elements.
		    for (int row = 0; row < levelHeight; row++) {
		    	for (int col = 0; col < levelWidth; col++) {
		    		String element = in.next();
		    		levelElements[row][col] = element;
		    	}
		    	in.nextLine(); // Needed if you change delimiter.
		    }
		    
		    // Read miscinalious game objects.
		    Player player = null;
		    
		    while(in.hasNextLine()) {
			    String elementType = in.next();
			    switch(elementType) {
			    	case "START":
			    		// Read the player's start position.
					    int playerX = in.nextInt();
					    int playerY = in.nextInt();
					    // Construct the player.
					    player = new Player(playerX, playerY);
					    in.nextLine();
					    break;
			    	case "ENEMY":
			    		// Read enemies, construct them and add them to a list.
			    		in.nextLine();
			    		break;
			    	case "DOOR":
			    		// Read doors, construct them and add them to a list.
			    		in.nextLine();
			    		break;
			    }
		    }
		    
		    // Construct a level, then add it to the list.
		    Level newLevel = new Level(levelElements, levelNum, player);
		    levels.add(newLevel);
		    in.close();
		}
		return levels;
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
