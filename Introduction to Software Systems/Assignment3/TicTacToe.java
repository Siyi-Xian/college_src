import java.util.Scanner;

public class TicTacToe {
	private String[][] players;
	private static final String[][] canvas = new String[][] {{" ", " ", " "},
															 {" ", " ", " "},
															 {" ", " ", " "}};
	
	/**
	 * Constructor
	 */
	public TicTacToe () {
		this.players = canvas;
	}
	
	/**
	 * Print the play table
	 */
	private void display () {
		System.out.println("-------------");
		for (int i = 0; i < 3; i++) {
			System.out.print("|");
			for (int j = 0; j < 3; j++)
				System.out.print(" " + players[i][j] + " |");
			System.out.println();
			System.out.println("-------------");
		}
	}
	
	/**
	 * To check if the input is over the border or is owner by others.
	 * If not give this one to the given user.
	 * @param x : x-coordination typed
	 * @param y : y-coordination typed
	 * @param who : the user.
	 */
	private void checkExist(int x, int y, String who) {
		if (x > 2 || x < 0 || y > 2 || y < 0) {  // Print warning message if the point is out of border
			System.out.println("This position is out of border. Please re-enter one.");
			getPos(who);
		}
		else if (players[x][y] != " ") { // Print warning message if the point has already been owned by someone
			System.out.println("The position  is owned by others. Please re-enter one.");
			getPos(who);
		}
		else if (who.equals("One")) // give this position to specific player
			players[x][y] = "O";
		else
			players[x][y] = "X";
	}
	
	/**
	 * Get players position which they want
	 * @param who : the player
	 */
	private void getPos (String who) {
		Scanner scan = new Scanner(System.in);  // New scanner
		
		System.out.print("Dear " + who + ", please enter the coordination that you want to own: "); // Promote player to enter point
		
		checkExist(scan.nextInt(), scan.nextInt(), who); // get point
	}
	
	/**
	 * Print the winner
	 * @param p : check who is the user
	 * @return true if there is a winner, or return false.
	 */
	private boolean printWinner (String p) {
		switch (p) { // Print specific winner
			case "O" : {System.out.println("Congratulation, player One!"); return true;}
			case "X" : {System.out.println("Congratulation, player Two!"); return true;}
			default :return false;
		}
	}
	
	/**
	 * Check if there is one player who win the game
	 * @return true if there is a winner
	 */
	private boolean checkWin () {
		for (int i = 0; i < 3; i++) // check if anyone get a vertical line
			if (players[i][0] == players[i][1] && players[i][1] == players[i][2])
				if (printWinner (players[i][0]))
					return false;
		
		for (int i = 0; i < 3; i++) // check if anyone get a horizontal line
			if (players[0][i] == players[1][i] && players[1][i] == players[2][i])
				if (printWinner (players[0][i]))
					return false;
		
		//c check if anyone get a diagonal line.
		if (players[0][0] == players[1][1] && players[1][1] == players[2][2])
			if (printWinner (players[0][0]))
				return false;
		
		if (players[2][0] == players[1][1] && players[1][1] == players[0][2])
			if (printWinner (players[0][0]))
				return false;
		
		return true;
	}
	
	/**
	 * Check if the play table is full
	 * @return true if it is full, or return false
	 */
	private boolean checkFull () {
		// check if the table is full
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (players[i][j] == " ") {
					return false;
				}
		System.out.println("Games Over! The box is full!"); // print if the table is full
		return true;
	}
	
	/**
	 * Let each player play the game
	 * @param p : the player
	 * @return true if the play table is full
	 */
	private boolean playEach (String p) {
		getPos(p); // get point
		display(); // print play table
		return checkFull(); // check if it is full
	}
	
	/**
	 * Play the game
	 */
	public void play () {
		display(); // print play table
		while (checkWin()) {
			if (playEach("One")) break; // Player One
			if (playEach("Two")) break; // Player Two
		}
	}
	
	/**
	 * Start a game
	 * @param args
	 */
	public static void main (String[] args) {
		TicTacToe t = new TicTacToe();
		t.play();
	}
}
