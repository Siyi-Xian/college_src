import java.util.Scanner;


/**
 * Name : Siyi Xian
 * User Name : siyixian
 * 
 * Draw asterisks lines with tab. The number of lines is typed buy users. This number is larger than 6.
 */
public class PrintingAsterisks {
	
	// Get the number, and check if the number is larger than 6. 
	public static int GetLineNum(){
		Scanner in = new Scanner(System.in);
		// Get input
		int num = in.nextInt();
		
		// Check if the number is larger than 6.
		if (num < 7) {
			System.out.print("The number should not be less than 7. Please re-enter it: ");
			return PrintingAsterisks.GetLineNum();
		}
		
		return num;
	}
	
	// Display the asterisk lines.
	public static void PrintLine(int num){
		
		// Store the number of the tab at the front of the line.
		int TabNumber = 0;
		
		while (num != 0) {
			// Display the tab at the front of the line.
			for (int i = 0; i < TabNumber; i++) {
				System.out.print("\t");
			}
			
			//Display the asterisk lines.
			System.out.println("*************************");
			
			num--;
			TabNumber++;
		}
	}
	
	public static void main(String[] args){
		//Prompt the user to enter number.
		System.out.print("Number of lines to print: ");
		
		// test program
		// Get the number from user.
		int num = PrintingAsterisks.GetLineNum();
		
		// Print the asterisk line out.
		PrintingAsterisks.PrintLine(num);
	}
}
