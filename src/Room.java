import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
/**
 * @author arstr001 mihan001
 *
 */
public class Room {
	// ATTRIBUTES
	private ArrayList<Item> items;
	private ArrayList<Character> characters;
	private ArrayList<Exit> exits;
	private String description;
	private ImageIcon image;
	private Coin coin;
	private Coin treasure;
	
	// CONSTRUCTOR
	/**
	 * This constructor initializes a room. It only takes the room's description but it creates empty arrays of exits, items and characters for later use.
	 * @param d The room's description.
	 */
	public Room(String d, ImageIcon i){
		description = d;
		image = i;
		exits = new ArrayList<Exit>();
		items = new ArrayList<Item>();
		characters = new ArrayList<Character>();
		Random r = new Random();
		coin = new Coin(r.nextInt(50));
		int i1 = r.nextInt(2);
		treasure = new Coin(r.nextInt(50)*i1);
	}
	
	// METHODS
	/**
	 * A method to add an exit to a room.
	 * @param e The exit to add.
	 */
	public void addExit(Exit e){
		exits.add(e);
	}
	
	/**
	 * A method to test if a room contains a given item. Returns a boolean.
	 * @param i The item to test for.
	 * @return True if the room contains the item, otherwise false.
	 */
	public boolean hasItem(Item i){
		if (items.contains(i)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * A method to add an item to a room. Called when a player drops an item.
	 * @param i The item to add.
	 */
	public void addItem(Item i){
		items.add(i);
	}
	/**
	 * A method to remove an item from a room. Called when a player picks up an item.
	 * @param i The item to remove.
	 */
	public void removeItem(Item i){
		if (this.hasItem(i)) {
			items.remove(i);
		}
	}
	// Character Methods
	/**
	 * A method to add a character to a room. Used during movement or when a character is added to the game.
	 * @param c The character to add.
	 */
	public void addCharacter(Character c){
		characters.add(c);
	}
	/**
	 * A method to remove a character to a room. Used during movement.
	 * @param c The character to remove.
	 */
	public void removeCharacter(Character c){
		characters.remove(c);
	}
	
	/**
	 * Get's the room's description.
	 * @return The room's description.
	 */
	public String getDescription(){
		return description;
	}
	// Exit Methods
	/**
	 * This method returns the possible directions a character can move from a room.
	 * @return An ArrayList of directions that a player can move to.
	 */
	public ArrayList<String> getDirections(){
		ArrayList<String> dir = new ArrayList<String>();
		for(Exit e : exits){
			dir.add(e.getDirection());
		}
		return dir;
	}
	public int directionIndex(String s){
		for(int i = 0; i < exits.size(); i ++){
			if(s.equalsIgnoreCase(exits.get(i).getDirection())){
				return i;
			}
		}
		return -1;
	}
	/**
	 * A method to get an exit based on a given index.
	 * @param i The index of a given exit.
	 * @return The exit.
	 */
	public Exit getExit(Integer i){
		return exits.get(i);
	}
	// Item Methods
	/**
	 * This method returns an array of the items in a room.
	 * @return Returns an ArrayList of the names of items.
	 */
	public ArrayList<String> getItemNames(){
		ArrayList<String> dir = new ArrayList<String>();
		for(Item i : items){
			dir.add(i.getName());
		}
		return dir;
	}
	/**
	 * A method to get the index of an item with a given name. This method can also be used to test if the item is in the room because the return result is negative if the item does not exist.
	 * @param s The name of the item.
	 * @return The index of the item that matches the given name.
	 */
	public int itemIndex(String s){
		for(int i = 0; i < items.size(); i ++){
			if(s.equalsIgnoreCase(items.get(i).getName())){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * A method to get an ArrayList of characters currently in the room.
	 * This method is used by the UI class to determine who is in the room.
	 * @return An ArrayList of character names (in string form).
	 */
	public ArrayList<String> getCharacters(){
		ArrayList<String> dir = new ArrayList<String>();
		for(Character i : characters){
			dir.add(i.getName());
		}
		return dir;
	}
	/**
	 * A method to get the index of a character with a given name.
	 * @param s The character's name to match
	 * @return If the character is in the room: the index of the character in the room's character array, if not: -1.
	 */
	public int charIndex(String s){
		for(int i = 0; i < characters.size(); i ++){
			if(s.equalsIgnoreCase(characters.get(i).getName())){
				return i;
			}
		}
		return -1;
	}
	
	// 
	// 
	/**
	 * Returns the class of the character at a given index.
	 * Used when player are attempting to pick up other characters.
	 * @param i The index of the character to be selected
	 * @return A string of the given character's class.
	 */
	public String getCharType(Integer i){
		String s = characters.get(i).getClass().toString();
		String[] c = s.split(" ");
		return c[1];
	}
	
	/**
	 * This method gets and returns the room's image.
	 * @return The image associated with the room.
	 */
	public ImageIcon getImage(){
		return image;
	}
	
	/**
	 * A method to return the item at a given index.
	 * @param i The index of a given item.
	 * @return The given item.
	 */
	public Item getItem(Integer i){
		return items.get(i);
	}
	/**
	 * A method to get and return the character object at a given index
	 * @param i The index of the character to retrieve.
	 * @return The character that was requested.
	 */
	public Character getChar(Integer i){
		return characters.get(i);
	}
	
	// COIN METHODS
	/**
	 * A method that returns the the amount of coins in the room.
	 * @return The amount of coins in the room
	 */
	public int getCoins(){
		return coin.getAmount();
	}
	/**
	 * A method to add coins to a room.
	 * @param a The amount of coins to be added.
	 */
	public void addCoins(int a){
		coin.addCoins(a);
	}
	/**
	 * A method to remove coins from a room.
	 * @param a The amount of coins to be removed.
	 */
	public void removeCoins(int a){
		coin.removeCoins(a);
	}
	/**
	 * A method which transfers coins in a room's treasure chest to that room.
	 */
	public void unlockTreasure(){
		this.addCoins(treasure.getAmount());
		treasure.removeAll();
	}
	/**
	 * A method to check if a room has treasure.
	 * @return True if a room has no treasure. Otherwise false.
	 */
	public boolean hasTreasure(){
		if(treasure.getAmount() > 0){
			return true;
		}
		return false;
	}
}
