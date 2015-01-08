import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.*;
/**
 * The UI class is responsible for creating the world, starting the game and managing the input and output of players.
 * @author arstr001 mihan001
 */
@SuppressWarnings("serial")
public class UI extends JFrame implements ActionListener, CharListener {
	// ATTRIBUTES
	private static World w;
	private Player p;
	private JPanel mainP;
	private JLabel room_i;
	private JLabel room_move;
	private JLabel room_cont;
	private JLabel room_coin;
	private JLabel player_coin;
	private JList items;
	private JLabel output;
	private JLabel image;
	private JLabel gameClock;
	private JTextField input;

	
	/**
	 * The constructor for UI.
	 * @param world A world for the new UI to use.
	 */
	public UI(World world){
		w = world;
	}
	
	/**
	 * The main method for the UI class. It initializes the world, creates two UI windows, and begins the game.
	 * @param arg
	 */
	public static void main(String[] arg){
		World theWorld = new World();
		theWorld.createWorld();
		theWorld.createPlayer("Joey", "Player 1");
		theWorld.createPlayer("Tommy", "Player 2");
		UI pUI = new UI(theWorld);
		UI p2UI = new UI(theWorld);
		pUI.setPlayer(theWorld.getPlayers().get(0));
		p2UI.setPlayer(theWorld.getPlayers().get(1));
		pUI.makeUI();
		p2UI.makeUI();
	}
	
	// METHODS
	/**
	 * A method to generate the current status of the player's current room.
	 * This method updates the fields and fills them with the most current information.
	 */
	public void statusReport(){
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		
		String exitList = "You can move";
	    for(String s : p.getLoc().getDirections()){
	    	exitList = exitList + " " + s;
	    }
	    String loc = p.getLoc().getDescription();
	    String itemList = "The room contains:";
	    if (p.getLoc().getItemNames().isEmpty() && p.getLoc().getCharacters().isEmpty()){
	    	itemList = "The room contains nothing";
	    }else{
		    for(String s : p.getLoc().getItemNames()){
		    	if (itemList == "The room contains:"){
		    		itemList = itemList + " " + s;	
		    	} else {
		    		itemList = itemList + ", " + s;
		    	}
		    }
		    for(String s : p.getLoc().getCharacters()){
		    	if (itemList == "The room contains:"){
		    		itemList = itemList + " " + s;	
		    	} else {
		    		itemList = itemList + ", " + s;
		    	}
		    	
		    }
	    }
	    String roomCoins = "There are " + p.getLoc().getCoins() + " coins in the room.";
	    if(p.getLoc().hasTreasure()){
	    	roomCoins = roomCoins + " This room contains a treasure chest.";
	    }
	    String playerCoins = "You have " + p.getCoins() + " coins.";
	    
	    String[] inventory = new String[p.getItemNames().size()];
		  for (int i = 0; i < p.getItemNames().size(); i++){
			  inventory[i] = p.getItemNames().get(i);
		  }  
	    lock.unlock();
	    
	    image.setIcon(p.getLoc().getImage());
	    
	    room_i.setText("You (" + p.getName() +  ") are in " + loc + ".");
	    room_move.setText(exitList + ".");
	    room_cont.setText(itemList + ".");
	    room_coin.setText(roomCoins);
	    player_coin.setText(playerCoins);
	    items.setListData(inventory);
	    gameClock.setText(World.timeLeft + " seconds remaining.");
	}
	
	public void setPlayer(Player pl) {
		p = pl;
	}
	
	/**
	 * This method create the UI window. It is used to organize the layout and initialize the fields we use to populate our UI.
	 */
	public void makeUI(){
		  mainP = new JPanel();
		  output = new JLabel();
		  input = new JTextField(20);
		  image = new JLabel(p.getLoc().getImage());
		  items = new JList();
		  
		  room_i = new JLabel();
		  room_move = new JLabel();
		  room_cont = new JLabel();
		  room_coin = new JLabel();
		  player_coin = new JLabel();
		  gameClock = new JLabel();
		  
		  JPanel top = new JPanel();
		  JPanel stat = new JPanel();
		  JPanel play = new JPanel();
		  JPanel inv = new JPanel();
		  
		  if (!this.isVisible()){
			  this.statusReport();
			  
			  // Add Listeners
			  for (int i = 0; i < w.getMOBs().size(); i++){
				  w.getMOBs().get(i).addListener(this);
			  }
			  for (int i = 0; i < w.getPlayers().size(); i++){
				  w.getPlayers().get(i).addListener(this);
			  }
			  input.addActionListener(this);
			  World.uiTimer.addActionListener(this);
			  
			  if(!World.uiTimer.isRunning()){
					World.uiTimer.start();
			  }

			  String[] inventory = new String[p.getItemNames().size()];
			  for (int i = 0; i < p.getItemNames().size(); i++){
				  inventory[i] = p.getItemNames().get(i);
			  }
			  items.setListData(inventory);
			  
			  mainP.setLayout(new BoxLayout(mainP, BoxLayout.Y_AXIS));
			  stat.setLayout(new BoxLayout(stat, BoxLayout.Y_AXIS));
			  stat.add(gameClock);
			  stat.add(room_i);
			  stat.add(room_move);
			  stat.add(room_cont);
			  stat.add(room_coin);
			  stat.add(player_coin);
			  inv.add(items);
			  
			  play.add(output, BorderLayout.NORTH);
			  play.add(input, BorderLayout.SOUTH);
			  
			  top.add(image);
			  top.add(stat);
			  mainP.add(top);
			  mainP.add(play, BorderLayout.SOUTH);
			  mainP.add(inv, BorderLayout.SOUTH);
			  
			  this.add(mainP);
			  this.setSize(800, 300);
			  
			  this.setVisible(true);
		  }
	}
		
	/**
	 * This is the primary method used, it analyzes written commands and calls the appropriate methods.
	 * @param s The text inputed by the user
	 */
	public void comSelection(String s){
		StringTokenizer st = new StringTokenizer(s);
		String s1 = st.nextToken();
		String s2 = "";
		String outP = "";
		while(st.hasMoreTokens()){
			s2 = s2 + st.nextToken() + " ";
		}
		s2 = s2.trim();
		
		// NEW COMMANDS
		// Drop Items
		if(s1.equalsIgnoreCase("drop")){
			Integer idx = p.itemIndex(s2);
			if(idx >= 0){
				p.drop(p.getItem(idx));
				outP ="Dropped " + s2;
			} else {
				outP = "No " + s2 + " in inventory.";
			}
		}
		// Get Items
		else if(s1.equalsIgnoreCase("get")){
			Integer idx = p.getLoc().itemIndex(s2);
			Integer charIdx = p.getLoc().charIndex(s2);
			if (s2.equalsIgnoreCase("coins")){
				if(!(p.getLoc().getCoins() == 0)){
					outP = "Picked up " + p.getLoc().getCoins() + " coins.";
					p.collectCoins();
				}else{
					outP = "There are no coins in the room.";
				}
			}
			else if(idx >= 0){
				p.get(p.getLoc().getItem(idx));
				outP = "Picked up " + s2;
			} else if (charIdx >= 0) {
				outP = "You can't pick up " + p.getLoc().getCharType(charIdx) + "s!";
			} else {
				outP = "No " + s2 + " in room.";
			}
		}
		// Movement
		else if(s1.equalsIgnoreCase("move") || s1.equalsIgnoreCase("go")){
			Integer idx = p.getLoc().directionIndex(s2);
			if(idx >= 0){
				p.move(p.getLoc().getExit(idx));
			} else{
				outP = "You can't get there from here!";
			}
		}
		else if(s1.equalsIgnoreCase("use")){
			Integer idx = p.itemIndex(s2);
			if(idx >= 0){
				Item i = p.getItem(idx);
				if(i.canUse()){
					if(p.getLoc().hasTreasure()){
						p.getLoc().unlockTreasure();
						p.deleteItem(i);
						outP = "Unlocked treasure chest. Your key crumbled!";
					} else {
						outP = "There's no treasure here.";
					}
				} else {
					outP = "You can't use that.";
				}
			} else {
				outP = "You can't use that!";
			}
		} else if(s1.equalsIgnoreCase("examine")){
			Integer idx = p.getLoc().itemIndex(s2);
			Integer charIdx = p.getLoc().charIndex(s2);
			if (idx >= 0){
				outP = p.getLoc().getItem(idx).getDescription();
			} else if(charIdx >= 0 ){
				if(p.getLoc().getChar(charIdx) == p){
					outP = "Our intrepid hero.";
				} else {
					outP = p.getLoc().getChar(charIdx).getDescription();
				}
			} else {
				outP = "There's nothing to see here!";
			}
		}
		// Exit
		else if(s.equalsIgnoreCase("exit")){
			outP = "Goodbye!";
			this.exitGame();
		}
		// Catch All
		else {
			outP = "Invalid command.";
		}
		output.setText(outP);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public synchronized void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == World.uiTimer){
			if (World.timeLeft == 0){
				this.gameOver();
			}else{
				World.timeLeft--;
			}
		} else {
			JTextField s = (JTextField) source;
			comSelection(s.getText());
			
			s.setText(null);
		}
		this.statusReport();
	}
	
	/* (non-Javadoc)
	 * @see CharListener#roomEntered()
	 */
	@Override
	public void roomEntered() {
		this.statusReport();
	}
	public void roomEntered(String s){
		this.statusReport();
		output.setText(s);
	}
	
	/**
	 * The gameOver method is called when the game clock reaches zero.
	 * It clears the UI, determines the winner and displays an appropriate message to the player.
	 */
	public void gameOver() {
		JLabel winner = new JLabel();
		for (Player play : w.getPlayers()){
			int coins = play.getCoins();
			if (play != p){
				if(coins > p.getCoins()){
					winner.setText("You Lose. :(");
				} else if (coins == p.getCoins()){
					winner.setText("You Tie. =|");
				} else {
					winner.setText("You Win! :)");
				}
				
			}
		}
		mainP.removeAll();
		JLabel end = new JLabel("Game Over!");
		mainP.add(winner, BorderLayout.NORTH);
		mainP.add(end, BorderLayout.CENTER);
		mainP.updateUI();
	}
	
	/**
	 * This function kills all of the MOBs and then disposes the game.
	 */
	public void exitGame() {
		for (MOB m : w.getMOBs()){
			m.alive = false;
		}
		try{
			Thread.sleep(3000);
		}
		catch(Exception e){
			
		}
		this.dispose();
	}

}
