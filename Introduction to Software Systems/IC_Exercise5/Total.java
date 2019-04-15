import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
   This program reads a file with numbers, and writes the numbers to another
   file, lined up in a column and followed by their total. 
*/
public class Total
{
   public static void main(String[] args) throws FileNotFoundException
   {
      // Prompt for the input and output file names

      Scanner console = new Scanner(System.in);
      System.out.print("Input file: ");
      String inputFileName = console.next();

      // Construct the Scanner and PrintWriter objects for reading and writing

      File inputFile = new File(inputFileName);
      Scanner in = new Scanner(inputFile);

      // Read the input and write the output
      
      int lineTotal = 0;  // count the total lines
      int charTotal = 0;  // count the total characters
      int intTotal = 0;   // count the total integers
      
      // count all line by line
      while (in.hasNextLine()) {
    	  String thisLine = in.nextLine();  // temporary store this line
    	  lineTotal++;                      // add one to the line number once get a line
    	  
    	  // count the characters in this line
    	  for (int i = 0; i < thisLine.length(); i++)
    		  if (thisLine.charAt(i) != ' ') // avoid to count SPACE
    			  charTotal++;
    	  
    	  // count the numbers in this line
    	  for (int i = 0; i < thisLine.length(); i++)
    		  if (thisLine.charAt(i) >= '0' && thisLine.charAt(i) <= '9') {
    			  // make sure to get a whole number
    			  while (thisLine.charAt(i) >= '0' && thisLine.charAt(i) <= '9' && i < thisLine.length() - 1)
    				  i++;
    			  intTotal++;
    		  }
      }
      
      // print answer on the screen.
      System.out.println("Total lines: " + lineTotal + "");
      System.out.println("Total characters: " + charTotal + "");
      System.out.println("Total integers: " + intTotal + "");
      
      // close the scanner
      in.close();
      console.close();
   }
}
