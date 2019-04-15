import java.util.Scanner;

public class PartOne {
	// Checks whether a string is a palindrome.
	public static boolean isPalindrome(String s) {
		int str_len = s.length(); // store the length of the given string 
		int front_index = 0, back_index = str_len - 1;
		
		// check every index of the given string
		while (back_index - front_index >= 1)
			if (s.charAt(front_index++) != s.charAt(back_index--))
				return false;
		return true;
	}
	
	// Checks whether a string is in alphabetical order.
	public static boolean isSorted(String s) {
		int str_len = s.length(); // store the length of given string
		
		// check given string ordered or not
		for (int i = 0; i < str_len - 1; i++)
			if (s.charAt(i) > s.charAt(i + 1))
				return false;
		return true;
	}
	
	// Print a n*n square where the border is filled with asterisks and the center is blank.
	public static void border(int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				if (i == 0 || i == n - 1 || j == 0 || j == n - 1)
					System.out.print("*");
				else
					System.out.print(" ");
			System.out.print("\n");
			}
	}
	
	// Print a T of asterisks.
	public static void T(int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				if (i == 0 || j == n / 2)
					System.out.print("*");
				else
					System.out.print(" ");
			System.out.print("\n");
			}
	}
	
	// Test case
	public static void main(String[] args) {
		Scanner scan1 = new Scanner(System.in); //set up a new scanner
		
		// test case for isPalindrome method
		// prompt user to enter number
		System.out.print("Please enter a number to check if this is a Palindrome number: ");
		String palin = scan1.nextLine(); // get next number
		// display result
		if (isPalindrome(palin))
			System.out.println(palin + " is a Palindrome number.");
		else
			System.out.println(palin + " is not a Palindrome number.");
		
		// test case for isSorted method
		// prompt user to enter number
		System.out.print("Please enter a string to check if this is in alphabetical order: ");
		String sort = scan1.nextLine(); // get next string
		// display result
		if (isSorted(sort))
			System.out.println(sort + " is in alphabetical order.");
		else
			System.out.println(sort + " is not in alphabetical order.");
		
		// test case for border method
		// prompt user to enter number
		System.out.print("Please enter a number that you want the side length be: ");
		int n_bor = scan1.nextInt(); // get next string
		// display result
		border(n_bor);
		
		// test case for T method
		// prompt user to enter number
		System.out.print("Please enter a number that you want the side length be (better to be an odd number): ");
		int n_T = scan1.nextInt(); // get next string
		// display result
		T(n_T);
	}
}