import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class FrequencyCounter {
	final private ArrayList<String> text;     // Store whole article
	final private String name;                // Store the name of the file
	final private int lineNum;                // Store the line number
	private int totalWords;                   // Store the number of total words
	
	//private ArrayList<String> dis;            // Keep track of the distinct strings
	private FrequencyList frequencies; // Keep track of the distinct strings and the number of times each one occurs
	
	
	class FrequencyList extends ArrayList<Frequency>{
		transient Frequency[] elementData;
		public int indexOf(String o) {
			if (o == null) {
	            for (int i = 0; i < super.size(); i++)
	                if (this.elementData[i]==null)
	                    return i;
	        } else {
	            for (int i = 0; i < super.size(); i++)
	                if (o.compareTo(elementData[i].getWord()) == 0)
	                    return i;
	        }
	        return -1;
		}
	}
	
	/**
	 * Input all value in the given text.
	 * @param file : the name of the input file
	 * @return temp : text from the given file as ArrayList
	 */
	private ArrayList<String> getText (String file) {
		ArrayList<String> temp = new ArrayList<String>(){
			public int indexOf(String o) {
				if (o == null) {
		            for (int i = 0; i < super.size(); i++)
		                if (elementData[i]==null)
		                    return i;
		        } else {
		            for (int i = 0; i < super.size(); i++)
		                if (o.compareTo(elementData[i].getWord()) == 0)
		                    return i;
		        }
		        return -1;
			}
		}; // Store text temporarily
		
		try {
			// create new File object and pass to the scanner
			Scanner scan = new Scanner(new File(file));
			// Use Scanner methods have you normally have to process the file 
			while (scan.hasNextLine())
				temp.add(scan.nextLine());
			// Gather all the data you need in the try block, and store in instance 
			// fields
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			// create new File object and pass to the scanner
			Scanner scan = new Scanner(new File(file));
			// Use Scanner methods have you normally have to process the file 
			while (scan.hasNext())
				this.frequencies.add(new Frequency(scan.next(), 0));
			// Gather all the data you need in the try block, and store in instance 
			// fields
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return temp;
	}
	
	/**
	 * Constructor, setter method
	 * @param file : the name of the input file
	 */
	public FrequencyCounter (String file) {
		this.frequencies = new FrequencyList();
		this.text = getText(file);
		this.lineNum = text.size();
		this.totalWords = 0;
		this.name = file;
		//this.dis = new ArrayList<String>();
		
	}
	
	/**
	 * Add the given word to the dis and counter ArrayList if it does not exist,
	 * or add one to the counter's time value.
	 * @param temp : the given word that need to be count
	 */
	private void countStr (String temp) {
		if (frequencies.contains(temp)) { // If the given word is exist, add one to its frequency
			int index = frequencies.indexOf(temp);
			frequencies.set(index, new Frequency(temp, frequencies.get(index).getFrequency() + 1));
		}
		else { // If the given word is not exist, add a new frequency value
			//dis.add(temp);
			frequencies.add(new Frequency(temp, 1));
		}
	}
	
	/**
	 * Get each word from the text ArrayList and count the times of it.
	 */
	public void countText () {
		for (int i = 0; i < lineNum; i++) {             // Count line by line
			String[] strTemp = text.get(i).split(" ");  // Get each word in this line
			int length = strTemp.length;                // The length of this line
			
			for (int j = 0; j < length; j++)            // Count each word
				if (strTemp[j].length() > 0) {          // If this word has value, count it
					countStr(strTemp[j]);               
					totalWords++;                       // Add one the the total word value
				}
		}
	}
	
	/**
	 * Display the result.
	 */
	public void printAnswer () {
		int ansLength = frequencies.size();
		System.out.println(name + " has " + (ansLength + "") + " distinct words.");   // Display step A 
		System.out.println(name + " has " + (lineNum + "") + " number of lines.");    // Display step B 
		System.out.println(name + " has " + (totalWords + "") + " number of words."); // Display step C 
		for (int i = 0; i < ansLength; i++)                                           // Display step D 
			System.out.println(frequencies.get(i).toString());
	}
	
	/**
	 * Test case
	 * Use tale.txt as input
	 * @param args
	 */
	public static void main (String[] args) {
		FrequencyCounter tale = new FrequencyCounter("tale.txt");  // Construct a new Frequency Counter
		tale.countText();                                          // Count this new text
		tale.printAnswer();                                        // Display the answer
	}
}