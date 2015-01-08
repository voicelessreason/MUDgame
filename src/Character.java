import java.util.ArrayList;
import java.util.List;

/**
 * The character superclass that contains the framework and basic methods for both MOBs and Players.
 * @author arstr001 mihan001
 */
public class Character {

	//attributes
	private String name;
	private String description;
	private ArrayList<Item> inventory;
	private Room location;
	private List<CharListener> listeners = new ArrayList<CharListener>();
	private Coin coin;
	protected boolean alive;
	
	//constructor
	/**
	 * The Character class constructor.
	 * @param n The character's name
	 * @param d The character's description
	 * @param l The character's current location
	 */
	public Character(String n, String d, Room l){
		name = n;
		description = d;
		location = l;
		location.addCharacter(this);
		inventory = new ArrayList<Item>();
		alive = true;
		coin = new Coin(0);
	}

	//methods
	/**
	 * A method to move a player from one room to another.
	 * @param e The exit to move to.
	 */
	public void move(Exit e){
		location.removeCharacter(this);
		location = e.getDestination();
		location.addCharacter(this);
		fireEvent(this);
	}
	
	/**
	 * Tests if a player has an item in their inventory.
	 * @param i the item to test
	 * @return true if player's inventory contains the item, false otherwise.
	 */
	public boolean hasItem(Item i){
		if (inventory.contains(i)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * A method for a character to pick up a specified item.
	 * @param i The item to be added.
	 */
	public void get(Item i){
		inventory.add(i);
		location.removeItem(i);
		fireEvent(this, "picked up " + i.getName() + ".");
	}
	
	/**
	 * A method for a character to drop a specified item.
	 * @param i the item to be dropped
	 */
	public void drop(Item i){
		inventory.remove(i);
		location.addItem(i);
		fireEvent(this, "dropped " + i.getName() + ".");
	}
	
	/**
	 * This method deletes an item from the characters inventory.
	 * It is called when a key is used to open a treasure chest.
	 * @param i The Item to be deleted.
	 */
	public void deleteItem(Item i){
		inventory.remove(i);
		fireEvent(this, "opened a treasure chest.");
	}
	/**
	 * A method to get the room the character is currently in.
	 * @return the character's current location
	 */
	public Room getLoc(){
		return location;
	}
	/**
	 * A method to get the character's name.
	 * @return The character's name.
	 */
	public String getName(){
		return name;
	}
	/**
	 * A method to get the character's description.
	 * @return The character's description
	 */
	public String getDescription(){
		return description;
	}
	// Item Methods
	/**
	 * A method to get the names of all items in a character's inventory.
	 * @return An ArrayList of the character's inventory
	 */
	public ArrayList<String> getItemNames(){
		ArrayList<String> dir = new ArrayList<String>();
		for(Item i : inventory){
			dir.add(i.getName());
		}
		return dir;
	}
	/**
	 * A method to get the index of an item with a given name. This method can also be used to test if the item is in the inventory because the return result is negative if the item does not exist.
	 * @param s The name of the item.
	 * @return The index of the item that matches the given name.
	 */
	public int itemIndex(String s){
		for(int i = 0; i < inventory.size(); i ++){
			if(s.equalsIgnoreCase(inventory.get(i).getName())){
				return i;
			}
		}
		return -1;
	}
	/**
	 * A method to get an item given that item's index.
	 * @param i the index of the item in character's inventory
	 * @return the item at index i
	 */
	public Item getItem(Integer i){
		return inventory.get(i);
	}
	/**
	 * Gets the number of coins the character has.
	 * @return The number of coins the character has.
	 */
	public int getCoins(){
		return coin.getAmount();
	}
	/**
	 * This method adds coins to the character's coin purse.
	 * @param a The number of coins to add.
	 */
	public void addCoins(int a){
		coin.addCoins(a);
	}
	/**
	 * This method removes coins from the character's coin purse.
	 * @param a The number of coins to remove.
	 */
	public void removeCoins(int a){
		coin.removeCoins(a);
	}
	// Event Stuff
	// Note: Change MOBListeners to CharacterListeners.
	/**
	 * This method adds a CharListener to the array of listeners
	 * @param me The listener to add.
	 */
	public void addListener(CharListener me){
		listeners.add(me);
	}
	/**
	 * This method removes a CharListener from the array of listeners
	 * @param me The listener to remove.
	 */
	public void removeListener(CharListener me){
		listeners.remove(me);
	}
	
	/**
	 * The method to fire an event when an action is taken.
	 * This method is overloaded so if no string is provided then it is re-called with an empty string.
	 * @param c The character firing the event (used to add names).
	 * @param s A string describing the event to be passed to the UI.
	 */
	public void fireEvent(Character c, String s){
		for(CharListener cl : listeners){
			if(s != ""){
				cl.roomEntered(c.getName() + " " + s);
			} else {
				cl.roomEntered();
			}
		}
	}
	public void fireEvent(Character c){
		fireEvent(c, "");
	}
}

