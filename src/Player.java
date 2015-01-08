

/**
 * @author arstr001 mihan001
 *
 */
public class Player extends Character {

	/**
	 * A constructor for creating a player character.
	 * @param n The player's name.
	 * @param d The player's description.
	 * @param l The player's starting location.
	 */
	
	public Player(String n, String d, Room l) {
		super(n, d, l);
	}
	
	public void collectCoins(){
		int i = this.getLoc().getCoins();
		this.addCoins(i);
		this.getLoc().removeCoins(i);
		this.fireEvent(this, "picked up " + i + " coins.");
	}
	
}
