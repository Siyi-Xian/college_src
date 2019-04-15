import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 *                               _oo0oo_
 *                              o8888888o
 *                              88"   "88
 *                              (| -_- |)
 *                              0\  =  /0
 *                            ___/'---'\___
 *                          .' \\|     |// '.
 *                         / \\|||  :  |||// \
 *                        / _||||| -:- |||||- \
 *                       |   | \\\  -  /// |   |
 *                       | \_|  ''\---/''  |_/ |
 *                       \  .-\__  '-'  __/-.  /
 *                     ___'. .'  /--.--\  '. .'___
 *                  ."" '<  '.___\_<|>_/___.' >' "".
 *                 | | :  '- \'.;'\ _ /';.'/ - ' : | |
 *                 \  \ '_.   \_ __\ /__ _/   .-' /  /
 *             ====='-.____'.___ \_____/___.-'___.-'=====
 *                               '=---='
 *             
 *             
 *           ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *                               BUG FREE
 *
 */


public class HtmlConverter {
	ArrayList<String> text; // store the all text
	
	// new file, scanner, and printer
	File textFile;
	Scanner textScanner;
	PrintWriter htmlFile;
	
	/**
	 * Constructor
	 * @throws FileNotFoundException
	 */
	public HtmlConverter() throws FileNotFoundException {
		// Declare all variables
		textFile = new File("myfile.txt");
		textScanner = new Scanner(textFile);
		htmlFile = new PrintWriter("myfile.html");
		
		text = new ArrayList<String>();
		
		// run the converter
		run();
		
		// close the scanner and printer
		textScanner.close();
		htmlFile.close();
	}
	
	/**
	 * get all text from the input files
	 */
	private void getText () {
		while (textScanner.hasNextLine())
			text.add(textScanner.nextLine());
	}
	
	/**
	 * print the header of this HTML file
	 */
	private void write_header () {
		htmlFile.println("<html>\n<title>\nThis is my Java html converter, Siyi Xian\n</title>\n<body>\n");
	}
	
	/**
	 * print the footer of this HTML file
	 */
	private void write_footer () {
		htmlFile.println("\n</body>\n</html>");
	}
	
	/**
	 * print the contents of this HTML file
	 */
	private void write_text () {
		for (int i = 0; i < text.size(); i++)
			htmlFile.println(text.get(i) + " <br>");
	}
	
	/**
	 * print all items that should be in the HTML file
	 */
	private void printText () {
		write_header();
		write_text();
		write_footer();
	}
	
	/**
	 * run the converter
	 */
	private void run () {
		getText();
		printText();
	}
	
	public static void main (String[] args) throws IOException, FileNotFoundException {
		HtmlConverter html = new HtmlConverter();
	}
}
