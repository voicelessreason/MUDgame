
/**
 * @author arstr001 mihan001
 *
 */
public class Item {
	private String name;
	private String description;
	protected boolean usable;
	
	/**
	 * A constructor that takes two strings and creates an Item object.
	 * @param n The name of the item.
	 * @param d The item's description.
	 */
	public Item(String n, String d){
		name = n;
		description = d;
		usable = false;
	}
	
	/**
	 * A public method to get the name of an item.
	 * @return The name of the item.
	 */
	public String getName(){
		return name;
	}
	/**
	 * A public method to get the description of an item.
	 * @return The description of an item.
	 */
	public String getDescription(){
		return description;
	}
	/** Method to test if item is usable
	 * @return whether its usable value is true.
	 */
	public boolean canUse(){
		return usable;
	}
}
