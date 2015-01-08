import java.util.*;

public class MOB extends Character {
	
	/**
	 * The MOB class constructor
	 * @param n The MOB's name.
	 * @param d The MOB's description
	 * @param l The character's location.
	 */
	public MOB(String n, String d, Room l) {
		super(n, d, l);
	}
	
 	/** A method that moves MOB's randomly from room to room after a period of time.
 	 * @param delay an integer that determines the amount of time a MOB waits before moving again.
 	 */
 	public void autoMove(int delay){
		Random r = new Random();
		int exitI = r.nextInt(this.getLoc().getDirections().size());
		this.move(this.getLoc().getExit(exitI));
		autoExchange();
		
		try {
			Thread.sleep(delay * 4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
 	/**
 	 * A method that automatically exchanges coins between a room and a MOB
 	 */
 	public void autoExchange(){
 		Random r = new Random();
 		// Adding
		if (this.getLoc().getCoins() > 0){
			int pickup = r.nextInt(this.getLoc().getCoins());
			this.addCoins(pickup);
			this.getLoc().removeCoins(pickup);
		}
 		
 		// Dropping
		if (this.getCoins() > 0){
	 		int drop = r.nextInt(this.getCoins());
	 		this.removeCoins(drop);
	 		this.getLoc().addCoins(drop);
		}
 	}
 }

