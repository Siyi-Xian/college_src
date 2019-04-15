import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {
	private ArrayList<String[]> textList;
	
	private int numberOfRows;
	private int numberOfFields;
	
	/**
	 * Constructor
	 * @param textName : the name and path of CSV file
	 * @throws FileNotFoundException
	 */
	public CSVReader (String textName) throws FileNotFoundException {
		// Declare new variables
		textList = new ArrayList<String[]>();
		numberOfRows = 0;
		numberOfFields = 0;
		
		File CSVFile = new File(textName);
		Scanner in = new Scanner(CSVFile);
		
		// scan all text into the arry list
		String temp;
		do {
			temp = in.nextLine();
			String[] temp2 = temp.split(",");
			if (temp2.length == 0)
				break;
			textList.add(temp2);
		} while (in.hasNextLine());
		
		// get the number of rows and fields
		numberOfRows = getRows();
		numberOfFields = getFields();
		
		// close the scanner
		in.close();
	}
	
	/**
	 * get the row number count begin at 0
	 * @return row number
	 */
	private int getRows() {
		return textList.size() - 1;
	}
	
	/**
	 * get the fields number count begin at 0
	 * @return fields number
	 */
	private int getFields() {
		return textList.get(0).length - 1;
	}
	
	/**
	 * get the row number count begin at 1
	 * @return row number
	 */
	public int numberOfRows() {
		return numberOfRows + 1;
	}
	
	/**
	 * get the fields number count begin at 1
	 * @return fields number
	 */
	public int numberOfFields() {
		return numberOfFields + 1;
	}
	
	/**
	 * get the contents at exactly place
	 * @param row : row number count begin at 1
	 * @param column : column number count begin at 1
	 * @return the contents
	 */
	public String fields(int row, int column) {
		return textList.get(row - 1)[column - 1];
	}
	
	/**
	 * get the max number of a column
	 * @param num : column number count begin with 1
	 * @return the max value
	 */
	public int max(int num) {
		int max = 0;
		for (int i = 2; i <= numberOfRows(); i++)
			max = Math.max(max, Integer.parseInt(fields(i, num)));
		return max;
	}
	
	/**
	 * get the min number of a column
	 * @param num : column number count begin with 1
	 * @return the min value
	 */
	public int min(int num) {
		int min = 0xfffffff;
		for (int i = 2; i <= numberOfRows(); i++)
			min = Math.min(min, Integer.parseInt(fields(i, num)));
		return min;
	}
	
	/**
	 * get the sum of a column
	 * @param num : column number count begin with 1
	 * @return the sum value
	 */
	public int sum(int num) {
		int sum = 0;
		for (int i = 2; i <= numberOfRows(); i++)
			sum += Integer.parseInt(fields(i, num));
		return sum;
	}
	
	/**
	 * get the total number of this column that needs to be count
	 * @param num : column number begin with 1
	 * @return the count number
	 */
	public int count(int num) { 
		return numberOfRows() - 1;
	}
	
	/**
	 * get the average value of a column
	 * @param num : column number begin with 1
	 * @return the average value
	 */
	public double avg(int num) {
		return (double) sum(num) / (double) count(num);
	}
}
