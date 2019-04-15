import java.util.ArrayList;
import java.util.Scanner;

public class SecondLargest_RV {	
	public int s; // store the second largest number
	
	/**
	 * Constructor
	 * @param num : the array list of the numbers
	 */
	public SecondLargest_RV (ArrayList<Integer> num) {
		s = findSecondLargest(num, 0, 0); // do recursive
	}
	
	/**
	 * recursive part
	 * @param arr : the list that need to find largest number
	 * @param first : store the exist largest number
	 * @param second : store the exist second largest number
	 * @return the final second largest number
	 */
	private int findSecondLargest(ArrayList<Integer> arr, int first, int second) {
		// return the second if the list is empty
		if (arr.size() == 0)
			return second;
		
		// the last value of this list
		int last = arr.get(arr.size() - 1);
		
		// compare the largest number in this list and the largest numbers exist
		if (last > first) {
			// put in the first, if max larger than  first
			second = first;
			first = last;
		}
		else if (last > second && last != first)
			second = last; // if the max is between the first and second, put it after the first
		
		// move the last number to continue recursive
		arr.remove(arr.size() - 1);
		return findSecondLargest(arr, first, second);
	}
	
	public static void main (String[] args) {
		// ask user to enter a list of number
		Scanner in = new Scanner(System.in);
		System.out.print("Please entern the array that you want to find the second largest: ");
		ArrayList<Integer> list = new ArrayList<Integer>(); // store the list of number
		String l = in.nextLine(); // get all numbers
		// convert into array list
		String[] temp = l.split(" ");
		for (int i = 0; i < temp.length; i++)
			list.add(Integer.parseInt(temp[i]));
		in.close();
		
		// get the second large number
		SecondLargest_RV sl = new SecondLargest_RV(list);
		// Display that
		System.out.println("The second largest number is " + sl.s + "" + ".");
	}
}
