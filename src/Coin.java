
public class Coin{

	private int amount;
	
	/**
	 * Constructor for Coin class
	 * @param a The coin's amount
	 */
	public Coin(int a) {
		amount = a;
	}
	/**
	 * Method to add coins to amount.
	 * @param a the amount to be added
	 */
	public void addCoins(int a){
		amount += a;
	}
	/**
	 * Method to remove coins from amount.
	 * @param a the amount to be removed
	 */
	public void removeCoins(int a){
		if (!(amount < a)){
			amount -= a;
		}
	}
	/**
	 * Method that sets amount to 0.
	 */
	public void removeAll(){
		amount = 0;
	}

	/**
	 * Method that returns amount.
	 * @return The coin's amount.
	 */
	public int getAmount(){
		return amount;
	}
}
