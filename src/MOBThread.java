
/**
 * The MOBThread class extends the Thread class and allows us to create a thread for our MOBs so they can act autonomously.
 * @author mihan001 arstt001
 *
 */
public class MOBThread extends Thread {
	private MOB mob;
	private int delay;
	
	/**
	 * The constructor for a MOBThread. This initializes the thread.
	 * @param m The MOB to create the thread for.
	 * @param d The delay (in 4 second intervals) in between MOB movements.
	 */
	public MOBThread(MOB m, int d){
		mob = m;
		delay = d;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		while (mob.alive){
			mob.autoMove(delay);
		}
		
	}
}
