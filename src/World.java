import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Timer;
/**
 * The world class is responsible for creating and containing all of the elements within the game world.
 * We use the world as a static field in the UI to create one persistent world across play instances.
 * @author arstr001 mihan001
 */
public class World {
	// ATTRIBUTES
	private ArrayList<Player> players;
	private ArrayList<MOB> mobs;
	private ArrayList<Room> rooms;
	protected static int timeLeft;
	protected static Timer uiTimer;
	
	
	/**
	 * Method that creates the world.
	 * This includes the creation of all rooms, exits, MOBs, items, and keys.
	 */
	public void createWorld(){
		//Make Player list
		this.players = new ArrayList<Player>();
    	// Make rooms
		rooms = new ArrayList<Room>();
		ArrayList<String> roomNames = new ArrayList<String>();
    	
    	for(int i = 0; i < 20; i++){
    		String s = "Room " + (i+1);
    		roomNames.add(s);
    	}
    	
    	for (int i = 0; i < 20; i ++){
    		Room room = new Room(roomNames.get(i),new ImageIcon("src/images/" + i + ".jpg"));
    		rooms.add(room);
    	}
   
    	// Make Exits
    	for (int i = 0; i < rooms.size(); i++){
    		if (i-5 >= 0){
    			Exit e = new Exit("North", rooms.get(i-5));
    			rooms.get(i).addExit(e);
    		}
    		if (i-1 >= 0 && i % 5 != 0){
    			Exit e = new Exit("West", rooms.get(i-1));
    			rooms.get(i).addExit(e);
    		}
    		if (i+1 < rooms.size() && (i+1) % 5 != 0){
    			Exit e = new Exit("East", rooms.get(i+1));
    			rooms.get(i).addExit(e);
    		}
    		if (i+5 < rooms.size()){
    			Exit e = new Exit("South", rooms.get(i+5));
    			rooms.get(i).addExit(e);
    		}
    	}
    	
    	Random r = new Random();
    	
    	// Make Items
    	String[] iNames = {
    			"Book of Spells",
    			"Sword",
    			"Sheild",
    			"Flask",
    			"Dusty Amulet",
    			"Fake Key",
    			"Skeleton",
    			"Dr. Zimmerman",
    			"D&D Manual",
    			"Bagel with Cream Cheese",
    			"<<Compile Error>>",
    			"Fake Treasure Chest",
    			"Spook of Bells",
    			"Michael Jackson's 'Thriller'",
    			"Trinket"
    		};
    	String[] iDesc = {
    			"An old book about how to spell magic",
    			"A sword with a blunt edge",
    			"A shield with a hole in the middle",
    			"A flask full of some mysterious liquid",
    			"An amulet that's probably worthless anyhow",
    			"A fake key",
    			"A pile of bones. Spooky",
    			"The Lord of the Dungeon",
    			"Garage sale fodder",
    			"The breakfast of champions",
    			"[ERROR] /home/smartmatic/jboss-osgi-1.1.1/example/src/main/java/org/jboss/test/osgi/RepositorySupport.java:[63,45] cannot access org.jboss.modules.ModuleIdentifier n class file for org.jboss.modules.ModuleIdentifier not found Requirement req = XRequirementBuilder.createArtifactRequirement(MavenCoordinates.parse(coordinates));",
    			"It's really pretty self-explanatory, isn't it?",
    			"An old spook about how to bell magic",
    			"A legendary piece of art",
    			"It looks like it does something, probably"
    		};
    	for(int i = 0; i < 15; i++){
    		int roomNumber = r.nextInt(rooms.size()-1);
    		Item it = new Item(iNames[i], iDesc[i]);
    		if (rooms.get(roomNumber).getItemNames().size() < 3){
    			rooms.get(r.nextInt(rooms.size()-1)).addItem(it);
    		}
    	}
    	Item clock = new Item("Clock", "A clock.");
    	Item box = new Item("Box", "A box.");
    	rooms.get(r.nextInt(rooms.size()-1)).addItem(clock);
    	rooms.get(r.nextInt(rooms.size()-1)).addItem(box);
    	
    	// Key Creation
    	for (int i = 0; i < 7; i++){
    		Key key = new Key("Key", "An old key that looks like it will break after one use.");
    		rooms.get(r.nextInt(rooms.size()-1)).addItem(key);
    	}
    	
    	// Make MOBS
    	mobs = new ArrayList<MOB>();
    	MOB g1 = new MOB("Rat-Tooth", "A goblin with poor dental hygiene", rooms.get(0));
    	MOB g2 = new MOB("Snake-Skin", "A goblin with bad complexion.", rooms.get(3));
    	MOB g3 = new MOB("Chad Stever", "A programming goblin", rooms.get(2));
    	MOB t1 = new MOB("Igor", "A slow-moving troll", rooms.get(19));
    	MOB v1 = new MOB("Dracula", "An emotionally unstable vampire", rooms.get(14));
    	MOBThread g1Thread = new MOBThread(g1, 4);
    	MOBThread g2Thread = new MOBThread(g2, 4);
    	MOBThread g3Thread = new MOBThread(g3, 2);
    	MOBThread t1Thread = new MOBThread(t1, 5);
    	MOBThread z1Thread = new MOBThread(v1, 3);
    	g1Thread.start();
    	g2Thread.start();
    	g3Thread.start();
    	t1Thread.start();
    	z1Thread.start();
    	mobs.add(g1);
    	mobs.add(g2);
    	mobs.add(g3);
    	mobs.add(t1);
    	mobs.add(v1);
    	
    	//Timer
    	 timeLeft = 500;
		 uiTimer = new Timer(1000, null);
    }
	/**
	 * A method to create a sample player in the given world.
	 * @param n The Player's Name
	 * @param s The Player's Description
	 */
	public void createPlayer(String n, String s){
		// Create Player
		Random r = new Random();
		int i = r.nextInt(rooms.size());
		Player p = new Player(n, s, rooms.get(i));
		players.add(p);
	}
	/**
	 * A method to return the list of players currently in the world.
	 * @return The list of players currently in the world.
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	/**
	 * A method to return the list of MOBs currently in the world.
	 * @return The list of MOBs in the world.
	 */
	public ArrayList<MOB> getMOBs() {
		return mobs;
	}
}
