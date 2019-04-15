import java.util.Scanner;

public class DieSimulation {
	// instance fields private Die die; 
	private Counter one; 
	private Counter two; 
	private Counter three; 
	private Counter four; 
	private Counter five; 
	private Counter six;
	
	// initialize all the instance fields
	public DieSimulation() {
		this.one = new Counter("One");
		this.two = new Counter("Two");
		this.three = new Counter("Three");
		this.four = new Counter("Four");
		this.five = new Counter("Five");
		this.six = new Counter("Six");
	}
	
	// Receive input from the user for how many times to roll the die.
	// Using the Counter objects, record the outcome of each roll.
	// Return all the counts of all the Counters
	public String run() {
		Scanner scan = new Scanner(System.in); // A new Scanner.
		
		// Receive input from the user for how many times to roll the die.
		System.out.print("How many times do you want to roll the die: ");
		int t;
		t = scan.nextInt();
		
		// Make a new random die.
		Die newDie = new Die();
		
		// run t times of die.
		for (int i = 0; i < t; i++) {
			int dieNum = newDie.roll();
			switch (dieNum) {
				case 1 : one.increment(); break;
				case 2 : two.increment(); break;
				case 3 : three.increment(); break;
				case 4 : four.increment(); break;
				case 5 : five.increment(); break;
				case 6 : six.increment(); break;
			}
		}
		
		// Count all cases.
		String answer = one.toString() + "\n";
		answer += two.toString() + "\n";
		answer += three.toString() + "\n";
		answer += four.toString() + "\n";
		answer += five.toString() + "\n";
		answer += six.toString() + "\n";
		
		// return all cases.
		return answer;
	}
	
	public static void main(String[] args) {
		// test case
		DieSimulation d = new DieSimulation();
		System.out.println(d.run());
	}
}
