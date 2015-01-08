
/**
 * @author arstr001
 *
 */
public class Exit {

	//attributes
	private Room destination;
	private String direction;
	
	//constructor
	/**
	 * This constructor takes a direction string and a destination room to create an exit object.
	 * @param d The direction the exit.
	 * @param r The destination room.
	 */
	public Exit(String d, Room r) {
		destination = r;
		direction = d;
	}
	
	//methods		
	/**
	 * A method to get the exit's destination.
	 * @return Returns the destination room
	 */
	public Room getDestination() {
		return destination;
	}
	/**
	 * A method to get the exit's direction.
	 * @return Returns the exit's
	 */
	public String getDirection() {
		return direction;
	}	
}