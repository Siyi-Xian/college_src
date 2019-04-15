import java.util.Random;

public class Die {
	private Random rand; // instance field
	
	// initialize a new Random object in the Constructor
	public Die() {
		this.rand = new Random();
	}
	
	// returns a random number between [1,6]
	// Look at the nextInt(int bound) method of Random
	public int roll() {
		return rand.nextInt(6) + 1;
	}
}
