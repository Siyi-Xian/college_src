import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HammingDistance {
	
	String dnaOne, dnaTwo;
	int hamming;
	
	public HammingDistance() throws FileNotFoundException {
		File f = new File("rosalind_ba1g.txt");
		Scanner s = new Scanner(f);
		
		dnaOne = s.nextLine();
		dnaTwo = s.nextLine();
		
		hamming = 0;
		
		s.close();
	}
	
	public void run() {
		int l = dnaOne.length();
		for (int i = 0; i < l; i++)
			if (dnaOne.charAt(i) != dnaTwo.charAt(i))
				hamming++;
		
		System.out.println(hamming);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		HammingDistance h = new HammingDistance();
		h.run();
	}

}
