import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReconstructString {
	String str;
	
	public ReconstructString() throws FileNotFoundException {
		File f = new File("rosalind_ba3b.txt");
		Scanner s = new Scanner(f);
		
		String tempStr = s.nextLine();
		str = tempStr.substring(0, tempStr.length() - 1);
		while (s.hasNextLine()) {
			str += tempStr.charAt(tempStr.length() - 1);
			tempStr = s.nextLine();
		}
		str += tempStr.charAt(tempStr.length() - 1);
		
		System.out.println(str);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		ReconstructString r = new ReconstructString();
	}

}
