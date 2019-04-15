import java.util.ArrayList;
import java.util.Scanner;

public class SecondLargest_NRV {
	ArrayList<Integer> numbers; // Store the numbers
	
	/**
	 * Constructor
	 * @param nums : the list of numbers
	 */
	public SecondLargest_NRV(ArrayList<Integer> nums) {
		numbers = nums;
		
		// remove the largest number
		removeLargest();
		// get the largest number after all the largest number that have been removed
		System.out.println("The second largest number is " + getLargest() + "" + ".");
	}
	
	/**
	 * find the largest number
	 * @return the largest number in this list
	 */
	private int getLargest() {
		int max = 0;
		for (int i = 0; i < numbers.size(); i++)
			max = Math.max(max, numbers.get(i));
		return max;
	}
	
	/**
	 * get the index of first largest number
	 * @return index;
	 */
	private int getMaxIndex() {
		int index = 0, max = 0;
		for (int i = 0; i < numbers.size(); i++)
			if (numbers.get(i) > max) {
				index = i;
				max = numbers.get(i);
			}
		return index;
	}
	
	/**
	 * remove all the largest numbers
	 */
	private void removeLargest() {
		int max = getLargest();
		do {
			int maxIndex = getMaxIndex();
			numbers.remove(maxIndex);
		} while (max == getLargest());
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
		
		SecondLargest_NRV sl = new SecondLargest_NRV(list);
	}
}
