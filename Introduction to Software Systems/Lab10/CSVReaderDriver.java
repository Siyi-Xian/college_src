import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVReaderDriver {
	public static void main (String[] args) throws FileNotFoundException {
		// set a new scanner and let user to enter the file name and path
		Scanner in = new Scanner(System.in);
		System.out.print("Please give the path and name of the CSV file: ");
		// set a new CSVReader
		CSVReader csv = new CSVReader(in.next());
		
		// display the the overall data of this CSV file
		System.out.println(csv.numberOfRows() + "");
		System.out.println(csv.numberOfFields() + "");
		
		// display some count result
		System.out.println("Max of column 3 is: " + csv.max(3) + "");
		System.out.println("Min of column 4 is: " + csv.min(4) + "");
		System.out.println("Average of column 5 is: " + csv.avg(5) + "");
		
		// close the scanner
		in.close();
	}
}
